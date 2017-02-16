package noxzet.fluxindustry.core.energy;

import net.darkhax.tesla.api.ITeslaConsumer;
import net.darkhax.tesla.api.ITeslaProducer;
import net.darkhax.tesla.capability.TeslaCapabilities;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.EnumFacing;
import net.minecraftforge.energy.CapabilityEnergy;
import net.minecraftforge.energy.IEnergyStorage;

public class FluxCableConsumer implements ITeslaConsumer, IEnergyStorage {

	private FluxCableContainer container;
	private TileEntity entity;
	private EnumFacing facing;
	private int lastEnergy = 0;
	
	public FluxCableConsumer(FluxCableContainer container, TileEntity entity, EnumFacing facing)
	{
		this.container = container;
		this.entity = entity;
		this.facing = facing;
	}
	
	public TileEntity getSource()
	{
		return entity.getWorld().getTileEntity(entity.getPos().offset(facing));
	}
	
	public long givePower(int mode, long Tesla, boolean simulated)
	{
		if (!simulated)
		{
			if (Tesla == lastEnergy && Tesla>=container.getChargeMin())
			{
				FluxUniversalProducer producer;
				if (simulated)
					producer = null;
				else
				{
					producer = new FluxUniversalProducer(mode, entity.getWorld(), entity.getPos().offset(facing), facing);
					container.giveEnergyCable(Tesla, Tesla, simulated, producer, facing.getIndex());
				}
			}
			lastEnergy = (int) Tesla;
		}
		return 0;
	}
	
	// Tesla API Energy
	
	@Override
	public long givePower(long Tesla, boolean simulated)
	{
		return givePower(FluxUniversalProducer.TESLA, Tesla, simulated);
	}
	
	// Minecraft Forge Energy
	
	@Override
	public int receiveEnergy(int Tesla, boolean simulated)
	{
		return (int)givePower(FluxUniversalProducer.FORGE, Tesla, simulated);
	}
	
	@Override
	public int extractEnergy(int maxExtract, boolean simulate)
	{
		return 0;
	}
	
	@Override
	public boolean canReceive()
	{
		return true;
	}

	@Override
	public boolean canExtract()
	{
		return false;
	}

	@Override
	public int getEnergyStored()
	{
		return 0;
	}

	@Override
	public int getMaxEnergyStored()
	{
		return (int)container.getCapacity();
	}
	
}
