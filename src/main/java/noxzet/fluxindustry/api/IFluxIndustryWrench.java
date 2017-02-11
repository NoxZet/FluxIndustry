package noxzet.fluxindustry.api;

import net.minecraft.item.ItemStack;

public interface IFluxIndustryWrench {
	
	// This function should handle things like item damaging and return true if it should rotate block
	public boolean onFluxIndustryWrenchUse(ItemStack stack);
}
