package noxzet.fluxindustry.core.jei.crusher;

import javax.annotation.Nonnull;

import mezz.jei.api.IGuiHelper;
import mezz.jei.api.gui.IDrawable;
import mezz.jei.api.gui.IGuiItemStackGroup;
import mezz.jei.api.gui.IRecipeLayout;
import mezz.jei.api.ingredients.IIngredients;
import mezz.jei.api.recipe.BlankRecipeCategory;
import net.minecraft.item.ItemStack;
import net.minecraft.util.text.translation.I18n;
import noxzet.fluxindustry.core.container.ElectricCrusherContainerGui;
import noxzet.fluxindustry.core.jei.FluxCategoryUids;

public class CrusherRecipeCategory extends BlankRecipeCategory<CrusherRecipeWrapper> {

	private static final int[] INPUT_SLOTS = { 0 };
	private static final int[] OUTPUT_SLOTS = { 1 };
	
	private final IDrawable background;
	private final String title;
	
	public CrusherRecipeCategory(IGuiHelper guiHelper)
	{
		background = guiHelper.createDrawable(ElectricCrusherContainerGui.background, 49, 16, 90, 54);
		title = "tile.fluxindustry.electric_crusher.name";
	}
	
	@Override
	public String getUid()
	{
		return FluxCategoryUids.CRUSHER;
	}
	
	@Override
	public String getTitle()
	{
		return I18n.translateToLocal(title);
	}
	
	@Override
	public IDrawable getBackground()
	{
		return background;
	}
	
	@Override
	public void setRecipe(@Nonnull IRecipeLayout recipeLayout, @Nonnull CrusherRecipeWrapper recipeWrapper, @Nonnull IIngredients ingredients)
	{
		IGuiItemStackGroup guiItemStacks = recipeLayout.getItemStacks();
		guiItemStacks.init(INPUT_SLOTS[0], true, 0, 0);
		guiItemStacks.init(OUTPUT_SLOTS[0], false, 68, 18);
		guiItemStacks.set(INPUT_SLOTS[0], ingredients.getInputs(ItemStack.class).get(0));
		guiItemStacks.set(OUTPUT_SLOTS[0], ingredients.getOutputs(ItemStack.class).get(0));
	}
	
}