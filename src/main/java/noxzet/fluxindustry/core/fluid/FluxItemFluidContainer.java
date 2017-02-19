package noxzet.fluxindustry.core.fluid;

import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.util.EnumFacing;
import net.minecraftforge.common.capabilities.Capability;
import net.minecraftforge.common.capabilities.ICapabilityProvider;
import net.minecraftforge.fluids.FluidStack;
import net.minecraftforge.fluids.capability.CapabilityFluidHandler;
import net.minecraftforge.fluids.capability.IFluidHandlerItem;
import net.minecraftforge.fluids.capability.IFluidTankProperties;

public class FluxItemFluidContainer implements ICapabilityProvider, IFluidHandlerItem {
	
	private final ItemStack itemstack;
	private int capacity;
	private ItemStack container;
	
	public FluxItemFluidContainer(ItemStack itemstack, int capacity, ItemStack container)
	{
		this.itemstack = itemstack;
		this.capacity = capacity;
		this.container = container;
	}
	
	@Override
	public boolean hasCapability (Capability<?> capability, EnumFacing facing)
	{
		if (capability == CapabilityFluidHandler.FLUID_HANDLER_ITEM_CAPABILITY)
			return true;
		else
			return false;
	}
	
	@Override
	public <T> T getCapability (Capability<T> capability, EnumFacing facing)
	{
		if (capability == CapabilityFluidHandler.FLUID_HANDLER_ITEM_CAPABILITY)
			return (T) this;
		else
			return null;
	}
	
	@Override
	public IFluidTankProperties[] getTankProperties()
	{
		FluidStack stack = loadFromItem();
		return new FluxItemFluidProperties[]{new FluxItemFluidProperties(stack, capacity)};
	}
	
	private FluidStack loadFromItem()
	{
		if (itemstack.hasTagCompound())
		{
			NBTTagCompound compound = itemstack.getTagCompound();
			if (compound.hasKey("FluidStack"))
			{
				return FluidStack.loadFluidStackFromNBT((NBTTagCompound)compound.getTag("FluidStack"));
			}
		}
		else
			itemstack.setTagCompound(new NBTTagCompound());
		return null;
	}
	
	private void saveToItem(FluidStack stack)
	{
		if (stack == null || stack.amount == 0)
			itemstack.getTagCompound().removeTag("FluidStack");
		else
			itemstack.getTagCompound().setTag("FluidStack", stack.writeToNBT(new NBTTagCompound()));
	}

	@Override
	public int fill(FluidStack fluid, boolean doFill)
	{
		FluidStack stack = loadFromItem();
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
		if (filledAmount > 0)
			saveToItem(stack);
		return filledAmount;
	}

	@Override
	public FluidStack drain(FluidStack fluid, boolean doDrain)
	{
		FluidStack stack = loadFromItem();
		if (stack != null && stack.isFluidEqual(fluid))
			return drain(fluid.amount, doDrain);
		else
			return null;
	}

	@Override
	public FluidStack drain(int maxDrain, boolean doDrain)
	{
		FluidStack stack = loadFromItem();
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
		saveToItem(stack);
		return new FluidStack(stack.getFluid(), drainedAmount);
	}

	@Override
	public ItemStack getContainer()
	{
		return container;
	}

}
