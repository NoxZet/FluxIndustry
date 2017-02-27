package noxzet.fluxindustry.core.tileentity;

import java.util.ArrayList;

import net.darkhax.tesla.api.ITeslaConsumer;
import net.darkhax.tesla.capability.TeslaCapabilities;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.EnumFacing;
import net.minecraft.util.ITickable;
import net.minecraft.world.World;
import net.minecraftforge.common.capabilities.Capability;
import net.minecraftforge.common.capabilities.ICapabilityProvider;
import net.minecraftforge.energy.CapabilityEnergy;
import net.minecraftforge.energy.IEnergyStorage;
import noxzet.fluxindustry.api.energy.IFluxIndustryAPI;
import noxzet.fluxindustry.core.block.electric.BlockElectric;
import noxzet.fluxindustry.core.energy.EnumEnergyHandling;
import noxzet.fluxindustry.core.energy.FluxEnergyConsumer;
import noxzet.fluxindustry.core.energy.FluxEnergyContainer;
import noxzet.fluxindustry.core.energy.FluxEnergyHolder;
import noxzet.fluxindustry.core.energy.FluxEnergyProducer;

public class TileElectric extends TileEntityFlux implements ICapabilityProvider, ITickable, IFluxIndustryAPI, cofh.api.energy.IEnergyProvider, cofh.api.energy.IEnergyReceiver {

	protected FluxEnergyContainer container;
	protected FluxEnergyHolder containerHolder;
	protected FluxEnergyProducer containerProducer;
	protected FluxEnergyConsumer containerConsumer;
	private boolean[] doesSideProduce = {false, false, false, false, false, false};
	private boolean[] doesSideConsume = {false, false, false, false, false, false};
	protected boolean isLitHold = false, isLit = false;
	protected String fluxName = "unknown";
	protected boolean fluxIsGenerator = false, fluxIsFuelPowered = false, fluxIsMachine = false;
	protected int fluxCriticalTemperature = -1;
	
	public TileElectric()
	{
		this(0);
	}
	
	public TileElectric(long capacity)
	{
		this(capacity, 0);
	}
	
	public TileElectric(long capacity, long rate)
	{
		this(capacity, rate, rate);
	}
	
	public TileElectric(long capacity, long inputRate, long outputRate)
	{
		this(0, capacity, inputRate, outputRate);
	}
	
	public TileElectric(long stored, long capacity, long inputRate, long outputRate)
	{
		container = new FluxEnergyContainer(stored, capacity, inputRate, outputRate);
		containerHolder = new FluxEnergyHolder(container);
		containerProducer = new FluxEnergyProducer(container);
		containerConsumer = new FluxEnergyConsumer(container);
	}
	
	@Override
	public void update()
	{
		if (!world.isRemote)
		{
			if (world.getBlockState(pos).getValue(BlockElectric.LIT)!=isLit)
			{
				this.dontRefresh();
				world.setBlockState(pos, world.getBlockState(pos).withProperty(BlockElectric.LIT, isLit), 2);
				world.markBlockRangeForRenderUpdate(pos, pos);
			}
			container.update();
			EnumFacing dir, dirOpposite;
			TileEntity tile;
			long maxOutput;
			for(int i = 0; i < 6; i++)
			{
				if (doesSideProduce[i])
				{
					maxOutput = container.getMaxOutputTick(true);
					if (maxOutput>0)
					{
						dir = EnumFacing.getFront(i);
						dirOpposite = dir.getOpposite();
						tile = world.getTileEntity(pos.offset(dir));
						if (tile != null)
						{
							if (tile.hasCapability(TeslaCapabilities.CAPABILITY_CONSUMER, dirOpposite))
							{
								ITeslaConsumer consumer = (ITeslaConsumer)tile.getCapability(TeslaCapabilities.CAPABILITY_CONSUMER, dirOpposite);
								container.takePower(consumer.givePower(maxOutput, false), false, true);
							}
							else if (tile.hasCapability(CapabilityEnergy.ENERGY, dirOpposite))
							{
								IEnergyStorage consumer = (IEnergyStorage)tile.getCapability(CapabilityEnergy.ENERGY, dirOpposite);
								if (consumer.canReceive())
								{
									container.takePower((long)consumer.receiveEnergy((int)maxOutput, false), false, true);
								}
							}
							else if (tile instanceof cofh.api.energy.IEnergyReceiver)
							{
								cofh.api.energy.IEnergyReceiver rftile = (cofh.api.energy.IEnergyReceiver)tile;
								container.takePower((long)rftile.receiveEnergy(dirOpposite, (int)maxOutput, false), false, true);
							}
						}
					}
				}
			}
		}
	}
	
	public boolean getIsLit()
	{
		return isLit;
	}
	
	@Override
	public void readFromNBT(NBTTagCompound compound)
	{
		super.readFromNBT(compound);
		if (compound.hasKey("TeslaContainer"))
		{
			if (container == null)
				container = new FluxEnergyContainer((NBTTagCompound)compound.getTag("TeslaContainer"));
			else
				container.fromNBT((NBTTagCompound)compound.getTag("TeslaContainer"));
		}
	}
	
	@Override
	public NBTTagCompound writeToNBT (NBTTagCompound compound)
	{
		super.writeToNBT(compound);
		if (container != null)
			compound.setTag("TeslaContainer", container.toNBT());
		return compound;
	}
	
	// Tesla API and Forge Energy connectivity
	
	@Override
	public boolean hasCapability (Capability<?> capability, EnumFacing facing)
	{
		if (capability == TeslaCapabilities.CAPABILITY_HOLDER)
			return true;
		else if (capability == TeslaCapabilities.CAPABILITY_PRODUCER)
			return doesSideProduce[facing.getIndex()];
		else if (capability == TeslaCapabilities.CAPABILITY_CONSUMER)
			return doesSideConsume[facing.getIndex()];
		else if (capability == CapabilityEnergy.ENERGY)
			return true;
		return super.hasCapability(capability, facing);
	}
	
	@Override
	public <T> T getCapability (Capability<T> capability, EnumFacing facing)
	{
		// Tesla API
		if (capability == TeslaCapabilities.CAPABILITY_HOLDER)
		{
			return (T) this.containerHolder;
		}
		else if (capability == TeslaCapabilities.CAPABILITY_PRODUCER && facing != null)
		{
			if (doesSideProduce[facing.getIndex()])
				return (T) this.containerProducer;
			else
				return null;
		}
		else if (capability == TeslaCapabilities.CAPABILITY_CONSUMER && facing != null)
		{
			if (doesSideConsume[facing.getIndex()])
				return (T) this.containerConsumer;
			else
				return null;
		}
		// Forge Energy
		else if (capability == CapabilityEnergy.ENERGY)
		{
			if (doesSideProduce[facing.getIndex()])
				return (T) this.containerProducer;
			else if (doesSideConsume[facing.getIndex()])
				return (T) this.containerConsumer;
			else
				return (T) this.containerHolder;
		}
		return super.getCapability(capability, facing);
	}
	
	// CoFH RF API connectivity

	@Override
	public int extractEnergy(EnumFacing facing, int Tesla, boolean simulated)
	{
		if (doesSideProduce[facing.getIndex()])
			return (int)container.takePower(Tesla, simulated);
		else
			return 0;
	}
	
	@Override
	public int receiveEnergy(EnumFacing facing, int Tesla, boolean simulated)
	{
		if (doesSideConsume[facing.getIndex()])
			return (int)container.givePower(Tesla, simulated);
		else
			return 0;
	}
	
	@Override
	public boolean canConnectEnergy(EnumFacing facing)
	{
		return doesSideProduce[facing.getIndex()] || doesSideConsume[facing.getIndex()];
	}
	
	@Override
	public int getEnergyStored(EnumFacing facing)
	{
		return (int) getStoredPower();
	}
	
	@Override
	public int getMaxEnergyStored(EnumFacing facing)
	{
		return (int) getCapacity();
	}
	
	@Deprecated
	public FluxEnergyContainer getTeslaContainer()
	{
		return container;
	}
	
	public void setCapacity(long capacity)
	{
		container.setCapacity(capacity);
	}
	
	public void setStoredPower(long stored)
	{
		container.setStoredPower(stored);
	}
	
	public void setInputRate(long inputRate)
	{
		container.setInputRate(inputRate);
	}
	
	public void setOutputRate(long inputRate)
	{
		container.setOutputRate(inputRate);
	}
	
	public void setSideEnergyHandling(EnumFacing facing, EnumEnergyHandling energyHandling)
	{
		doesSideProduce[facing.getIndex()] = energyHandling.hasOutput();
		doesSideConsume[facing.getIndex()] = energyHandling.hasInput();
	}
	
	public void setAllEnergyHandling(EnumEnergyHandling energyHandling)
	{
		for(int i = 0; i < 6; i++)
		{
			doesSideProduce[i] = energyHandling.hasOutput();
			doesSideConsume[i] = energyHandling.hasInput();
		}
	}
	
	public long getCapacity()
	{
		return container.getCapacity();
	}

	public long getStoredPower()
	{
		return container.getStoredPower();
	}
	
	public long getInputRate()
	{
		return container.getInputRate();
	}
	
	public long getOutputRate()
	{
		return container.getOutputRate();
	}

	public boolean onActivated(World world, EntityPlayer player, EnumFacing facing) { return false; }
	public void onClicked(World world, EntityPlayer player) {}
	public void onRotated() {};

	@Override
	public String getFluxName() {
		return fluxName;
	}

	@Override
	public long getFluxStoredPower() {
		return container.getStoredPower();
	}

	@Override
	public long getFluxMaxStoredPower() {
		return container.getCapacity();
	}

	// Flux Industry API
	
	@Override
	public boolean getFluxIsGenerator() {
		return this.fluxIsGenerator;
	}

	@Override
	public boolean getFluxIsGeneratorRunning() {
		return false;
	}

	@Override
	public long getFluxGeneratedPerTick() {
		return 0;
	}

	@Override
	public boolean getFluxIsFuelPowered() {
		return this.fluxIsFuelPowered;
	}

	@Override
	public int getFluxFuelRemaining() {
		return 0;
	}

	@Override
	public boolean getFluxIsMachine() {
		return this.fluxIsMachine;
	}

	@Override
	public boolean getFluxIsMachineRunning() {
		return false;
	}

	@Override
	public long getFluxAcceptedPerTick() {
		return 0;
	}

	@Override
	public double getFluxProgress() {
		return 0;
	}

	@Override
	public ArrayList<ItemStack> getFluxInputStacks() {
		return new ArrayList<ItemStack>();
	}

	@Override
	public ArrayList<ItemStack> getFluxOutputStacks() {
		return new ArrayList<ItemStack>();
	}

	@Override
	public int getFluxTemperature() {
		return 0;
	}

	@Override
	public int getFluxCriticalTemperature() {
		return this.fluxCriticalTemperature;
	}
	
}
