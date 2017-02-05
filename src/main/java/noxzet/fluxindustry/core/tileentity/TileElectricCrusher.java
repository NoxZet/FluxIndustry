package noxzet.fluxindustry.core.tileentity;

import net.minecraft.item.ItemStack;
import noxzet.fluxindustry.core.crafting.FluxCrusherRecipes;

public class TileElectricCrusher extends TileElectricMachine {

	
	public TileElectricCrusher()
	{
		super(0, 3000, 18, 0);
		super.setTeslaPerTick(14);
		super.setBurnTimeNeeded(180);
	}
	
	@Override
	public ItemStack getResult(ItemStack stack)
	{
		ItemStack result = getCrushingResult(stack);
		super.neededCount = FluxCrusherRecipes.instance().neededCount;
		return result;
	}
	
	public static ItemStack getCrushingResult(ItemStack stack)
	{
		if (stack.isEmpty())
			return ItemStack.EMPTY;
		else
			return FluxCrusherRecipes.instance().getCrushingResult(stack).copy();
	}
	
}
