package noxzet.fluxindustry.core.energy;

import net.darkhax.tesla.capability.TeslaCapabilities;
import net.minecraft.item.ItemStack;
import net.minecraft.util.EnumFacing;
import net.minecraftforge.common.capabilities.Capability;
import net.minecraftforge.common.capabilities.ICapabilityProvider;
import net.minecraftforge.energy.CapabilityEnergy;

public class FluxBatteryProvider implements ICapabilityProvider {

	private final ItemStack stack;
	private final long capacity;
	private final boolean chargeable;
	
	public FluxBatteryProvider(ItemStack stack, long capacity, boolean chargeable)
	{
		this.stack = stack;
		this.capacity = capacity;
		this.chargeable = chargeable;
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
			return (T) new FluxBatteryContainer(this.stack, capacity, chargeable);
		else
			return null;
	}
	
}
