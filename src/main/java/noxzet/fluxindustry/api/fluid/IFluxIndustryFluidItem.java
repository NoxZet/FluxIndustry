package noxzet.fluxindustry.api.fluid;

import net.minecraft.item.ItemStack;
import net.minecraftforge.fluids.FluidStack;

public interface IFluxIndustryFluidItem {

	public int fluxFluidFill(ItemStack itemstack, FluidStack fluid, boolean simulate);
	public FluidStack fluxFluidDrain(ItemStack itemstack, FluidStack fluid, boolean simulate);
	public FluidStack fluxFluidDrain(ItemStack itemstack, int fluid, boolean simulate);
	public int fluxFluidCapacity(ItemStack itemstack);
	
}
