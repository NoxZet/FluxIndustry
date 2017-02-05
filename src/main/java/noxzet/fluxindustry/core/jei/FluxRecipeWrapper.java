package noxzet.fluxindustry.core.jei;

import java.util.ArrayList;
import java.util.List;

import javax.annotation.Nonnull;

import mezz.jei.api.ingredients.IIngredients;
import mezz.jei.api.recipe.BlankRecipeWrapper;
import net.minecraft.item.ItemStack;

public abstract class FluxRecipeWrapper<T extends FluxRecipe> extends BlankRecipeWrapper {

	protected final T fluxRecipe;
	@Nonnull
	private final List<List<ItemStack>> inputs;
	
	public FluxRecipeWrapper(T fluxRecipe)
	{
		this.fluxRecipe = fluxRecipe;
		inputs = new ArrayList<>();
	}
	
	@Override
	public void getIngredients(@Nonnull IIngredients ingredients)
	{
		ingredients.setInputs(ItemStack.class, fluxRecipe.getInputs().get(0));
		ingredients.setOutputs(ItemStack.class, fluxRecipe.getOutputs());
	}
	
}
