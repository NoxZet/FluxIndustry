package noxzet.fluxindustry.core.tileentity;

import net.minecraft.item.ItemStack;
import net.minecraft.item.crafting.FurnaceRecipes;

public class TileElectricFurnace extends TileElectricMachine {

	public TileElectricFurnace()
	{
		super(0, 2000, 40, 0, 3);
		super.setTeslaPerTick(14);
		super.setBurnTimeNeeded(120);
		this.fluxName = "electric_furnace";
	}
	
	@Override
	public ItemStack[] getResult(ItemStack stack)
	{
		ItemStack[] result = new ItemStack[]{getSmeltingResult(stack)};
		super.neededCount = 1;
		return result;
	}
	
	public static ItemStack getSmeltingResult(ItemStack stack)
	{
		if (stack.isEmpty())
			return ItemStack.EMPTY;
		else
			return FurnaceRecipes.instance().getSmeltingResult(stack).copy();
	}
	
}
