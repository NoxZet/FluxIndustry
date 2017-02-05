package noxzet.fluxindustry.core.energy;

import net.darkhax.tesla.api.ITeslaHolder;
import net.minecraftforge.energy.IEnergyStorage;

public class FluxEnergyHolder implements ITeslaHolder, IEnergyStorage {

	private FluxEnergyContainer container;
	
	public FluxEnergyHolder (FluxEnergyContainer container)
	{
		this.container = container;
	}
	
	public FluxEnergyContainer getContainer()
	{
		return container;
	}
	
	// Tesla API Energy

	@Override
	public long getStoredPower()
	{
		return getContainer().getStoredPower();
	}

	@Override
	public long getCapacity()
	{
		return getContainer().getCapacity();
	}
	
	// Minecraft Forge Energy
	
	@Override
	public int receiveEnergy(int maxReceive, boolean simulate)
	{
		return 0;
	}
	
	@Override
	public int extractEnergy(int maxExtract, boolean simulate)
	{
		return 0;
	}
	
	@Override
	public int getEnergyStored()
	{
		return (int) getContainer().getStoredPower();
	}
	
	@Override
	public int getMaxEnergyStored()
	{
		return (int) getContainer().getCapacity();
	}
	
	@Override
	public boolean canExtract()
	{
		return false;
	}
	
	@Override
	public boolean canReceive()
	{
		return false;
	}
	
}
