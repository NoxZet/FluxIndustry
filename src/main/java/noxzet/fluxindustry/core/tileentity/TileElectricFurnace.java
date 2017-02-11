package noxzet.fluxindustry.core.tileentity;

import net.minecraft.item.ItemStack;
import net.minecraft.item.crafting.FurnaceRecipes;

public class TileElectricFurnace extends TileElectricMachine {

	
	public TileElectricFurnace()
	{
		super(0, 2000, 40, 0);
		super.setTeslaPerTick(14);
		super.setBurnTimeNeeded(120);
	}
	
	@Override
	public ItemStack getResult(ItemStack stack)
	{
		super.neededCount = 1;
		return getSmeltingResult(stack);
	}
	
	public static ItemStack getSmeltingResult(ItemStack stack)
	{
		if (stack.isEmpty())
			return ItemStack.EMPTY;
		else
			return FurnaceRecipes.instance().getSmeltingResult(stack).copy();
	}
	
}
