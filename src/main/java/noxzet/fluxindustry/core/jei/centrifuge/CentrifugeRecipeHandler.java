package noxzet.fluxindustry.core.jei.centrifuge;

import javax.annotation.Nonnull;

import mezz.jei.api.IJeiHelpers;
import mezz.jei.api.recipe.IRecipeHandler;
import mezz.jei.api.recipe.IRecipeWrapper;
import noxzet.fluxindustry.core.jei.FluxCategoryUids;

public class CentrifugeRecipeHandler implements IRecipeHandler<CentrifugeRecipe> {

	@Nonnull
	private final IJeiHelpers jeiHelpers;
	
	public CentrifugeRecipeHandler(@Nonnull IJeiHelpers jeiHelpers)
	{
		this.jeiHelpers = jeiHelpers;
	}
	
	@Override
	public Class<CentrifugeRecipe> getRecipeClass()
	{
		return CentrifugeRecipe.class;
	}

	@Override
	public String getRecipeCategoryUid(CentrifugeRecipe recipe)
	{
		return FluxCategoryUids.CENTRIFUGE;
	}

	@Override
	public IRecipeWrapper getRecipeWrapper(CentrifugeRecipe recipe)
	{
		return new CentrifugeRecipeWrapper(jeiHelpers, recipe);
	}

	@Override
	public boolean isRecipeValid(CentrifugeRecipe recipe)
	{
		return true;
	}

	
	
}
