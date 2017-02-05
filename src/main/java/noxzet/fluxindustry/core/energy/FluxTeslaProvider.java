package noxzet.fluxindustry.core.energy;

import net.darkhax.tesla.capability.TeslaCapabilities;
import net.minecraft.util.EnumFacing;
import net.minecraftforge.common.capabilities.Capability;
import net.minecraftforge.common.capabilities.ICapabilityProvider;
import net.minecraftforge.energy.CapabilityEnergy;

public class FluxTeslaProvider implements ICapabilityProvider {

	private final FluxEnergyContainer container;
	
	public FluxTeslaProvider(FluxEnergyContainer container)
	{
		this.container = container;
	}
	
	@Override
	public boolean hasCapability (Capability<?> capability, EnumFacing facing)
	{
		if (capability == TeslaCapabilities.CAPABILITY_CONSUMER ||
				capability == TeslaCapabilities.CAPABILITY_PRODUCER ||
				capability == TeslaCapabilities.CAPABILITY_HOLDER ||
				capability == CapabilityEnergy.ENERGY)
			return true;
		else
			return false;
	}
	
	@Override
	public <T> T getCapability (Capability<T> capability, EnumFacing facing)
	{
		if (capability == TeslaCapabilities.CAPABILITY_CONSUMER ||
				capability == TeslaCapabilities.CAPABILITY_PRODUCER ||
				capability == TeslaCapabilities.CAPABILITY_HOLDER ||
				capability == CapabilityEnergy.ENERGY)
			return (T) this.container;
		else
			return null;
	}
	
}
