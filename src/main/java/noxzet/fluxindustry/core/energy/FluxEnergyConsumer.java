package noxzet.fluxindustry.core.energy;

import net.darkhax.tesla.api.ITeslaConsumer;

public class FluxEnergyConsumer extends FluxEnergyHolder implements ITeslaConsumer {

	public FluxEnergyConsumer (FluxEnergyContainer container)
	{
		super(container);
	}
	
	public FluxEnergyConsumer (FluxEnergyHolder energyHolder)
	{
		super(energyHolder.getContainer());
	}
	
	// Tesla API Energy
	
	@Override
	public long givePower(long Tesla, boolean simulated)
	{
		return super.getContainer().givePower(Tesla, simulated);
	}
	
	// Minecraft Forge Energy
	
	@Override
	public int receiveEnergy(int Tesla, boolean simulated)
	{
		return (int) super.getContainer().givePower(Tesla, simulated);
	}
	
	@Override
	public boolean canReceive()
	{
		return true;
	}
	
}
