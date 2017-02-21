package noxzet.fluxindustry.core.tileentity;

import net.minecraft.util.EnumFacing;
import net.minecraftforge.common.capabilities.Capability;
import net.minecraftforge.fluids.capability.CapabilityFluidHandler;
import noxzet.fluxindustry.core.fluid.FluxFluidContainer;

public class TileElectricFluid extends TileElectricInventory {

	FluxFluidContainer fluidContainer;
	
	public TileElectricFluid()
	{
		this(0, 0, 0, 0, 0, 0);
	}
	
	public TileElectricFluid(long stored, long capacity, long inputRate, long outputRate, int slots, int fluid)
	{
		super(stored, capacity, inputRate, outputRate, slots);
		this.fluidContainer = new FluxFluidContainer(fluid);
	}
	
	@Override
	public boolean hasCapability (Capability<?> capability, EnumFacing facing)
	{
		if (capability == CapabilityFluidHandler.FLUID_HANDLER_CAPABILITY)
			return true;
		else
			return super.hasCapability(capability, facing);
	}
	
	@Override
	public <T> T getCapability (Capability<T> capability, EnumFacing facing)
	{
		if (capability == CapabilityFluidHandler.FLUID_HANDLER_CAPABILITY)
			return (T)fluidContainer;
		else
			return super.getCapability(capability, facing);
	}

}
