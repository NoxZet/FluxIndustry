package noxzet.fluxindustry.core.energy;

import net.darkhax.tesla.api.ITeslaConsumer;
import net.darkhax.tesla.api.ITeslaHolder;
import net.darkhax.tesla.api.ITeslaProducer;
import net.darkhax.tesla.capability.TeslaCapabilities;
import net.minecraft.item.ItemStack;
import net.minecraft.util.EnumFacing;
import net.minecraftforge.common.capabilities.Capability;
import net.minecraftforge.common.capabilities.ICapabilityProvider;
import net.minecraftforge.energy.CapabilityEnergy;
import net.minecraftforge.energy.IEnergyStorage;

public class FluxBatteryContainer implements ICapabilityProvider, ITeslaConsumer, ITeslaHolder, ITeslaProducer, IEnergyStorage {

	private final ItemStack stack;
	private long capacity;
	private boolean chargeable;
	
	public FluxBatteryContainer(ItemStack stack, long capacity, boolean chargeable)
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
			return (T) this;
		else
			return null;
	}
	
	public long givePower (long Tesla, boolean simulated)
	{
		if (stack.getCount()==1)
		{
			if (chargeable)
			{
				long stored = getStoredPower();
				long acceptedTesla = Math.min(this.capacity - stored, Tesla);
				if (!simulated)
					stack.getTagCompound().setLong("Tesla", stored + acceptedTesla);
				return acceptedTesla;
			}
		}
		return 0;
	}
	
	public long takePower (long Tesla, boolean simulated)
	{
		if (stack.getCount()==1)
		{
			long stored = getStoredPower();
			long removedTesla = Math.min(stored, Tesla);
			if (!simulated)
				stack.getTagCompound().setLong("Tesla", stored - removedTesla);
			return removedTesla;
		}
		return 0;
	}
	
	public void setCapacity(long capacity)
	{
		this.capacity = Math.max(0, capacity);
	}

	@Override
	public long getStoredPower()
	{
		return stack.getTagCompound().getLong("Tesla");
	}

	@Override
	public long getCapacity()
	{
		return capacity;
	}

	@Override
	public int receiveEnergy(int maxReceive, boolean simulate)
	{
		return (int)givePower((long)maxReceive, simulate);
	}

	@Override
	public int extractEnergy(int maxExtract, boolean simulate)
	{
		return (int)takePower((long)maxExtract, simulate);
	}

	@Override
	public int getEnergyStored() {
		return (int)getStoredPower();
	}

	@Override
	public int getMaxEnergyStored()
	{
		return (int)getCapacity();
	}

	@Override
	public boolean canReceive()
	{
		return chargeable;
	}

	@Override
	public boolean canExtract()
	{
		return true;
	}
	
}
