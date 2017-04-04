package noxzet.fluxindustry.core.crafting;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

import net.minecraft.item.ItemStack;
import net.minecraftforge.fluids.Fluid;
import net.minecraftforge.fluids.FluidStack;

public class FluxFluidRecipes {

	public static int neededCount = 1;
	private static final FluxFluidRecipes INSTANCE = new FluxFluidRecipes();
	private static final Map<String, FluxFluidRecipe> oreDictItemRecipes = new HashMap<>();
	private static final Map<ItemStack, FluxFluidRecipe> nonDictItemRecipes = new HashMap<>();
	private static final Map<FluidStack, FluxFluidRecipe> fluidRecipes = new HashMap<>();
	private static final Map<Fluid, ArrayList<FluxFluidRecipe>> searchRecipes = new HashMap<>();
	
	public static FluxFluidRecipes instance()
	{
		return INSTANCE;
	}
	
	public static FluxFluidRecipe searchRecipe(FluidStack sourceFluid, ItemStack sourceItem, FluidStack target)
	{
		if (target == null)
			return null;
		Fluid fluid = target.getFluid();
		/*for (Entry<Fluid, ArrayList<FluxFluidRecipe>> entry : searchRecipes.entrySet())
		 *	  if (fluid == entry.getKey())
		 *	  {
		 *		  for (FluxFluidRecipe thisRecipe : entry.getValue())
		 */
		ArrayList<FluxFluidRecipe> recipeList = searchRecipes.get(target.getFluid());
		if (recipeList != null)
		{
			for (FluxFluidRecipe thisRecipe : recipeList)
			{
				if (thisRecipe.getIsSourceItem() && sourceItem != null)
				{
					if (target.amount == thisRecipe.getTargetFluid().amount &&
							ItemStack.areItemsEqual(sourceItem, thisRecipe.getSourceItem()) && 
							sourceItem.getCount() >= thisRecipe.getSourceItem().getCount())
					{
						return thisRecipe;
					}
				}
				else if (!thisRecipe.getIsSourceItem() && sourceFluid != null)
				{
					if (target.amount == thisRecipe.getTargetFluid().amount &&
							sourceFluid.getFluid() == thisRecipe.getSourceFluid().getFluid() &&
							sourceFluid.amount >= thisRecipe.getSourceFluid().amount)
					{
						return thisRecipe;
					}
				}
			}
			//}
		}
		return null;
	}
	
	public static void addRecipe(FluxFluidRecipe recipe)
	{
		// Search list
		Fluid fluid = recipe.getTargetFluid().getFluid();
		ArrayList<FluxFluidRecipe> recipeList;
		if (searchRecipes.containsKey(fluid))
			recipeList = searchRecipes.get(fluid);
		else
			searchRecipes.put(fluid, (recipeList = new ArrayList<>()));
		recipeList.add(recipe);
		
		// Specific lists
		if (recipe.getIsSourceItem())
			nonDictItemRecipes.put(recipe.getSourceItem(), recipe);
		else
			fluidRecipes.put(recipe.getSourceFluid(), recipe);
	}
	
}
