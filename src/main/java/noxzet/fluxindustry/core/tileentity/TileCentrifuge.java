package noxzet.fluxindustry.core.tileentity;

import net.minecraft.item.ItemStack;
import noxzet.fluxindustry.core.crafting.FluxCentrifugeRecipe;
import noxzet.fluxindustry.core.crafting.FluxCentrifugeRecipes;

public class TileCentrifuge extends TileElectricMachine {
	
	public TileCentrifuge()
	{
		super(0, 2000, 40, 0, 5);
		super.setTeslaPerTick(16);
		super.setBurnTimeNeeded(200);
		this.fluxName = "centrifuge";
	}
	
	@Override
	public ItemStack[] getResult(ItemStack stack)
	{
		FluxCentrifugeRecipe result = getCentrifugeResult(stack);
		if (result == null)
			return new ItemStack[]{ItemStack.EMPTY, ItemStack.EMPTY, ItemStack.EMPTY};
		if (this.chosenResult == -1)
			chosenResult = result.getOutputRandomNumber();
		return result.getOutputStacks();
	}
	
	public static FluxCentrifugeRecipe getCentrifugeResult(ItemStack stack)
	{
		if (stack.isEmpty())
			return null;
		else
			return FluxCentrifugeRecipes.getCentrifugeResult(stack);
	}
	
}
