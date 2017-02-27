package noxzet.fluxindustry.core.crafting;

import net.minecraft.item.ItemStack;
import net.minecraftforge.fluids.FluidStack;

public class FluxFluidRecipe {

	private boolean isSourceItem;
	private FluidStack sourceFluid;
	private ItemStack sourceItem;
	private FluidStack targetFluid;
	private FluidStack outputFluid;
	private int energy = 0;
	
	public FluxFluidRecipe(FluidStack source, FluidStack target, FluidStack output)
	{
		this.isSourceItem = false;
		this.sourceFluid = source;
		this.sourceItem = ItemStack.EMPTY;
		this.targetFluid = target;
		this.outputFluid = output;
	}
	
	public FluxFluidRecipe(ItemStack source, FluidStack target, FluidStack output)
	{
		this.isSourceItem = true;
		this.sourceFluid = null;
		this.sourceItem = source;
		this.targetFluid = target;
		this.outputFluid = output;
	}
	
	public boolean getIsSourceItem()
	{
		return this.isSourceItem;
	}
	
	public FluidStack getSourceFluid()
	{
		return this.sourceFluid;
	}
	
	public ItemStack getSourceItem()
	{
		return this.sourceItem;
	}
	
	public FluidStack getTargetFluid()
	{
		return this.targetFluid;
	}
	
	public FluidStack getOutputFluid()
	{
		return this.outputFluid;
	}
	
	public FluxFluidRecipe setEnergy(int energy)
	{
		this.energy = energy;
		return this;
	}
	
	public int getEnergy()
	{
		return this.energy;
	}
	
}
