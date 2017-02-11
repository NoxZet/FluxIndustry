package noxzet.fluxindustry.core.item;

import net.minecraft.item.ItemStack;
import noxzet.fluxindustry.api.IFluxIndustryWrench;

public class ItemFluxWrench extends ItemFlux implements IFluxIndustryWrench {

	public ItemFluxWrench(String unlocalizedName)
	{
		super(unlocalizedName);
		this.setMaxStackSize(1);
	}
	
	public boolean onFluxIndustryWrenchUse(ItemStack stack)
	{
		return true;
	}

}
