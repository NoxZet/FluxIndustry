package noxzet.fluxindustry.core.fluid;

import net.minecraftforge.fluids.FluidStack;
import net.minecraftforge.fluids.capability.IFluidTankProperties;

public class FluxItemFluidProperties implements IFluidTankProperties {

	private FluidStack stack;
	private int capacity;
	
	public FluxItemFluidProperties(FluidStack stack, int capacity)
	{
		this.stack = stack;
		this.capacity = capacity;
	}
	
	@Override
	public FluidStack getContents()
	{
		if (stack == null)
			return null;
		else
			return stack.copy();
	}

	@Override
	public int getCapacity()
	{
		return capacity;
	}

	@Override
	public boolean canFill()
	{
		return true;
	}

	@Override
	public boolean canDrain()
	{
		return true;
	}

	@Override
	public boolean canFillFluidType(FluidStack fluidStack)
	{
		return true;
	}

	@Override
	public boolean canDrainFluidType(FluidStack fluidStack)
	{
		return true;
	}
	
}
