package noxzet.fluxindustry.core.tileentity;

import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraftforge.fluids.FluidStack;
import noxzet.fluxindustry.api.fluid.IFluxIndustryFluidItem;
import noxzet.fluxindustry.core.crafting.FluxFluidRecipe;
import noxzet.fluxindustry.core.crafting.FluxFluidRecipes;
import noxzet.fluxindustry.core.network.FluxNetworkWrapper;
import noxzet.fluxindustry.core.network.RequestPacket;

public class TileLiquidFiller extends TileElectricFluid {

	protected int leftTime = 0;
	protected int leftProgress = 0;
	protected int rightTime = 0;
	protected int rightProgress = 0;
	public static final int PROGRESS_LEFT_TIME = 5;
	public static final int PROGRESS_LEFT_PROGRESS = 6;
	public static final int PROGRESS_RIGHT_TIME = 7;
	public static final int PROGRESS_RIGHT_PROGRESS = 8;
	
	public TileLiquidFiller()
	{
		super(0, 2000, 40, 0, 3, 4000);
		this.fluxName = "liquid_filler";
	}
	
	@Override
	public void update()
	{
		super.update();
		if (!world.isRemote)
		{
			ItemStack leftStack = inventory.getStackInSlot(0).copy();
			if (!leftStack.isEmpty())
			{
				if (leftStack.getItem() instanceof IFluxIndustryFluidItem)
				{
					IFluxIndustryFluidItem fluidItem = (IFluxIndustryFluidItem) leftStack.getItem();
					if (fluidContainer.stack == null || fluidContainer.stack.amount == 0)
					{
						if (fluidContainer.fill(fluidItem.fluxFluidDrain(leftStack, fluidContainer.getCapacity(), false), true) > 0)
							inventory.setStackInSlot(0, leftStack);
					}
					else if (fluidContainer.fill(fluidItem.fluxFluidDrain(leftStack, new FluidStack(fluidContainer.stack.getFluid(), fluidContainer.getCapacity() - fluidContainer.stack.amount), false), true) > 0)
					{
						inventory.setStackInSlot(0, leftStack);
					}
					else
					{
						FluxFluidRecipe recipe = FluxFluidRecipes.searchRecipe(fluidItem.fluxFluidDrain(leftStack, fluidItem.fluxFluidCapacity(leftStack), true), fluidContainer.stack);
						FluidStack recipeTarget = recipe.getTargetFluid();
						FluidStack recipeSource = recipe.getSourceFluid();
						FluidStack recipeOutput = recipe.getOutputFluid();
						int multiplier = recipeTarget.amount / fluidContainer.stack.amount;
						if (fluidContainer.getCapacity() >= recipeOutput.amount * multiplier)
						{
							fluidItem.fluxFluidDrain(leftStack, recipeSource.amount * multiplier, false);
							inventory.setStackInSlot(0, leftStack);
							fluidContainer.stack = recipeOutput.copy();
							fluidContainer.stack.amount *= multiplier;
						}
					}
				}
				else
				{
					FluxFluidRecipe recipe = FluxFluidRecipes.searchRecipe(leftStack, fluidContainer.stack);
					FluidStack recipeTarget = recipe.getTargetFluid();
					ItemStack recipeSource = recipe.getSourceItem();
					FluidStack recipeOutput = recipe.getOutputFluid();
					int multiplier = recipeTarget.amount / fluidContainer.stack.amount;
					if (fluidContainer.getCapacity() >= recipeOutput.amount * multiplier)
					{
						leftStack.shrink(recipeSource.getCount() * multiplier);
						inventory.setStackInSlot(0, leftStack);
						fluidContainer.stack = recipeOutput.copy();
						fluidContainer.stack.amount *= multiplier;
					}
				}
			}
		}
		else if (isOpen)
		{
			FluxNetworkWrapper.INSTANCE.sendToServer(new RequestPacket(pos, 0));
		}
	}
	
	@Override
	public int getField(int id)
	{
		switch (id)
		{
			case PROGRESS_LEFT_TIME: return leftTime;
			case PROGRESS_LEFT_PROGRESS: return leftProgress;
			case PROGRESS_RIGHT_TIME: return rightTime;
			case PROGRESS_RIGHT_PROGRESS: return rightProgress;
			default: return super.getField(id);
		}
	}
	
	@Override
	public void setField(int id, int data)
	{
		switch (id)
		{
			case PROGRESS_LEFT_TIME: this.leftTime = data;
			case PROGRESS_LEFT_PROGRESS: this.leftProgress = data;
			case PROGRESS_RIGHT_TIME: this.rightTime = data;
			case PROGRESS_RIGHT_PROGRESS: this.rightProgress = data;
			default: super.setField(id, data);
		}
	}
	
	@Override
	public void readFromNBT(NBTTagCompound compound)
	{
		super.readFromNBT(compound);
		if (compound.hasKey("leftTime"))
			this.leftTime = compound.getInteger("leftTime");
		if (compound.hasKey("leftProgress"))
			this.leftProgress = compound.getInteger("leftProgress");
		if (compound.hasKey("rightTime"))
			this.rightTime = compound.getInteger("rightTime");
		if (compound.hasKey("rightProgress"))
			this.rightProgress = compound.getInteger("rightProgress");
	}

	@Override
	public NBTTagCompound writeToNBT(NBTTagCompound compound)
	{
		super.writeToNBT(compound);
		compound.setInteger("leftTime", this.leftTime);
		compound.setInteger("leftProgress", this.leftProgress);
		compound.setInteger("rightTime", this.rightTime);
		compound.setInteger("rightProgress", this.rightProgress);
		return compound;
	}

	public int getLeftProgress()
	{
		return leftProgress;
	}
	
	public int getLeftTime()
	{
		return leftTime;
	}
	
	public int getRightProgress()
	{
		return rightProgress;
	}
	
	public int getRightTime()
	{
		return rightTime;
	}
	
}
