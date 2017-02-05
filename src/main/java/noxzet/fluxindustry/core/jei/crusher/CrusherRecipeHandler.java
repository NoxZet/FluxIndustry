package noxzet.fluxindustry.core.jei.crusher;

import javax.annotation.Nonnull;

import mezz.jei.api.IJeiHelpers;
import mezz.jei.api.recipe.IRecipeHandler;
import mezz.jei.api.recipe.IRecipeWrapper;
import noxzet.fluxindustry.core.jei.FluxCategoryUids;

public class CrusherRecipeHandler implements IRecipeHandler<CrusherRecipe> {

	@Nonnull
	private final IJeiHelpers jeiHelpers;
	
	public CrusherRecipeHandler(@Nonnull IJeiHelpers jeiHelpers)
	{
		this.jeiHelpers = jeiHelpers;
	}
	
	@Override
	public Class<CrusherRecipe> getRecipeClass()
	{
		return CrusherRecipe.class;
	}

	@Override
	public String getRecipeCategoryUid(CrusherRecipe recipe)
	{
		return FluxCategoryUids.CRUSHER;
	}

	@Override
	public IRecipeWrapper getRecipeWrapper(CrusherRecipe recipe)
	{
		return new CrusherRecipeWrapper(jeiHelpers, recipe);
	}

	@Override
	public boolean isRecipeValid(CrusherRecipe recipe)
	{
		return true;
	}

	
	
}
