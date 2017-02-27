package noxzet.fluxindustry.core.fluid;

import net.minecraft.nbt.NBTTagCompound;
import net.minecraftforge.fluids.FluidStack;
import net.minecraftforge.fluids.capability.IFluidHandler;
import net.minecraftforge.fluids.capability.IFluidTankProperties;

public class FluxFluidContainer implements IFluidHandler {
	
	public FluidStack stack;
	private int capacity;
	
	public FluxFluidContainer(int capacity)
	{
		this.stack = null;
		this.capacity = capacity;
	}
	
	@Override
	public IFluidTankProperties[] getTankProperties()
	{
		return new FluxItemFluidProperties[]{new FluxItemFluidProperties(stack, capacity)};
	}

	@Override
	public int fill(FluidStack fluid, boolean doFill)
	{
		int filledAmount = 0;
		if (stack == null)
		{
			filledAmount = Math.min(fluid.amount, capacity);
			if (doFill)
				stack = new FluidStack(fluid.getFluid(), fluid.amount);
		}
		else if (stack.isFluidEqual(fluid))
		{
			filledAmount = Math.min(fluid.amount, capacity - stack.amount);
			if (doFill)
				stack.amount += filledAmount;
		}
		return filledAmount;
	}

	@Override
	public FluidStack drain(FluidStack fluid, boolean doDrain)
	{
		if (stack != null && stack.isFluidEqual(fluid))
			return drain(fluid.amount, doDrain);
		else
			return null;
	}

	@Override
	public FluidStack drain(int maxDrain, boolean doDrain)
	{
		if (stack.amount == 0)
			stack = null;
		int drainedAmount = 0;
		if (stack == null)
			drainedAmount = 0;
		else
		{
			drainedAmount = Math.min(maxDrain, stack.amount);
			if (doDrain)
				stack.amount -= drainedAmount;
		}
		return new FluidStack(stack.getFluid(), drainedAmount);
	}
	
	public int getCapacity()
	{
		return this.capacity;
	}
	
	public void readFromNBT(NBTTagCompound compound)
	{
		if (compound.hasKey("FluidName") && compound.hasKey("Amount"))
		{
			stack = FluidStack.loadFluidStackFromNBT(compound);
			if (stack != null && stack.amount > capacity)
				stack.amount = capacity;
		}
	}
	
	public NBTTagCompound writeToNBT()
	{
		return writeToNBT(new NBTTagCompound());
	}
	
	public NBTTagCompound writeToNBT(NBTTagCompound compound)
	{
		return stack.writeToNBT(compound);
	}

}
