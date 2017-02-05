package noxzet.fluxindustry.core.energy;

import net.darkhax.tesla.api.ITeslaProducer;

public class FluxEnergyProducer extends FluxEnergyHolder implements ITeslaProducer {

	public FluxEnergyProducer (FluxEnergyContainer container)
	{
		super(container);
	}
	
	public FluxEnergyProducer (FluxEnergyHolder energyHolder)
	{
		super(energyHolder.getContainer());
	}
	
	// Tesla API Energy
	
	@Override
	public long takePower(long Tesla, boolean simulated)
	{
		return super.getContainer().takePower(Tesla, simulated);
	}
	
	// Minecraft Forge Energy
	
	@Override
	public int extractEnergy(int Tesla, boolean simulated)
	{
		return (int) super.getContainer().takePower(Tesla, simulated);
	}
	
	@Override
	public boolean canExtract()
	{
		return true;
	}
	
}
