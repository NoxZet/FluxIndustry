package noxzet.fluxindustry.core.energy;

import net.darkhax.tesla.api.ITeslaConsumer;
import net.darkhax.tesla.api.ITeslaProducer;
import net.darkhax.tesla.capability.TeslaCapabilities;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.EnumFacing;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;
import net.minecraftforge.common.util.Constants.NBT;
import net.minecraftforge.energy.CapabilityEnergy;
import net.minecraftforge.energy.IEnergyStorage;

public class FluxCableContainer {

	private long capacity, charge, chargeMin;
	private float capacityPassing, chargePassing, chargeLoss, chargeFloat;
	private int sourceLastDirection;
	private ITeslaProducer sourcePassing;
	private TileEntity entity;
	private int passCount;
	
	public FluxCableContainer(long capacity, float chargeLoss, long chargeMin, TileEntity entity)
	{
		this.capacity = capacity;
		this.chargeLoss = chargeLoss;
		this.chargeMin = chargeMin;
		this.entity = entity;
		charge = 0;
		chargePassing = 0;
		sourceLastDirection = -1;
		passCount = 20;
	}
	
	public void fromNBT(NBTTagCompound compound)
	{
		if (compound.hasKey("TeslaPower", NBT.TAG_LONG))
			this.charge = compound.getLong("TeslaPower");
		if (compound.hasKey("TeslaCapacity", NBT.TAG_LONG))
			this.capacity = compound.getLong("TeslaCapacity");
		if (compound.hasKey("TeslaMinPower", NBT.TAG_LONG))
			this.chargeMin = compound.getLong("TeslaMinPower");
		if (compound.hasKey("TeslaLoss", NBT.TAG_FLOAT))
			this.chargeLoss = compound.getFloat("TeslaLoss");
	}
	
	public NBTTagCompound toNBT()
	{
		return toNBT(new NBTTagCompound());
	}
	
	public NBTTagCompound toNBT(NBTTagCompound compound)
	{
		compound.setLong("TeslaPower", charge);
		compound.setLong("TeslaCapacity", capacity);
		compound.setLong("TeslaMinPower", chargeMin);
		compound.setFloat("TeslaLoss", chargeLoss);
		return compound;
	}
	
	public void update()
	{
		update(false);
	}
	
	public void update(boolean onlyPass)
	{
		World world = entity.getWorld();
		BlockPos pos = entity.getPos();
		FluxCableContainer cable;
		ITeslaConsumer consumer;
		IEnergyStorage forgeEnergy;
		EnumFacing facing;
		TileEntity target;
		long energyAvailable;
		for(int i = 0; i < 6; i++)
		{
			if (i != sourceLastDirection)
			{
				energyAvailable = getEnergyAvailable();
				if (energyAvailable<=0)
					break;
				facing = EnumFacing.getFront(i);
				target = world.getTileEntity(pos.offset(facing));
				facing = facing.getOpposite();
				if (target != null)
				{
					if (target instanceof IFluxCable)
					{
						cable = ((IFluxCable)target).getFluxCableContainer();
						cable.giveEnergyCable(chargePassing, capacityPassing, false, sourcePassing, facing.getIndex());
					}
					else if (onlyPass==false && target.hasCapability(TeslaCapabilities.CAPABILITY_CONSUMER, facing))
					{
						consumer = target.getCapability(TeslaCapabilities.CAPABILITY_CONSUMER, facing);
						takeEnergy(consumer.givePower(energyAvailable, false), false);
					}
					else if (onlyPass==false && target.hasCapability(CapabilityEnergy.ENERGY, facing))
					{
						forgeEnergy = target.getCapability(CapabilityEnergy.ENERGY, facing);
						if (forgeEnergy.canReceive()) {
							takeEnergy(forgeEnergy.receiveEnergy((int)energyAvailable, false), false);
						}
					}
					else if (onlyPass==false && target instanceof cofh.api.energy.IEnergyReceiver)
					{
						cofh.api.energy.IEnergyReceiver rftile = (cofh.api.energy.IEnergyReceiver)target;
						takeEnergy(rftile.receiveEnergy(facing, (int)energyAvailable, false), false);
					}
				}
			}
		}
	}
	
	public long getEnergyAvailable()
	{
		if (this.charge > 0)
			return this.charge;
		else if (chargePassing <= 0)
			return destroyPassingCharge();
		else if (this.sourcePassing == null)
			return destroyPassingCharge();
		else if (this.sourcePassing.takePower((long)this.capacityPassing, true)>=capacityPassing)
			return (long)Math.floor(this.chargePassing);
		else if (passCount>0)
			passCount--;
		else
			destroyPassingCharge();
		return 0;
	}
	
	public long getCapacity()
	{
		return this.capacity;
	}
	
	public long getChargeMin()
	{
		return this.chargeMin;
	}
	
	public float giveEnergyCable(float Tesla, float capacity, boolean simulate, ITeslaProducer source, int facing)
	{
		if (this.chargePassing <= 0 && Tesla >= (float)this.chargeMin)
		{
			Tesla = Math.min(Tesla, (float)this.capacity);
			if (!simulate)
			{
				sourceLastDirection = facing;
				this.chargePassing = Tesla - chargeLoss;
				this.capacityPassing = Math.min(capacity, (float)this.capacity);
				this.sourcePassing = source;
				this.passCount = 20;
			}
			return Tesla;
		}
		return 0;
	}
	
	public long takeEnergy(long Tesla, boolean simulate)
	{
		long energyAvailable = getEnergyAvailable();
		if (energyAvailable > 0)
		{
			long usedTesla = Math.min(Tesla, energyAvailable);
			if (!simulate)
			{
				if (this.charge == 0)
					loadPassingCharge();
				this.charge -= usedTesla;
			}
			return usedTesla;
		}
		return 0;
	}
	
	// Only call after checking source for energy
	private void loadPassingCharge()
	{
		if (this.charge==0)
		{
			this.sourcePassing.takePower((long)this.capacityPassing, false);
			this.charge = (long)Math.floor(this.chargePassing);
			this.chargePassing = 0;
		}
	}
	
	private int destroyPassingCharge()
	{
		chargePassing = 0;
		sourcePassing = null;
		return 0;
	}
	
}
