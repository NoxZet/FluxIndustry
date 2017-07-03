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
	
	public static FluxFluidRecipe searchRecipe(FluidStack sourceFluid, FluidStack target)
	{
		return searchRecipe(sourceFluid, null, target);
	}
	
	public static FluxFluidRecipe searchRecipe(ItemStack sourceItem, FluidStack target)
	{
		return searchRecipe(null, sourceItem, target);
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
				FluidStack recipeTarget = thisRecipe.getTargetFluid();
				if (target.amount >= recipeTarget.amount && (recipeTarget.amount % target.amount) == 0)
				{
					int multiplier = (recipeTarget.amount / target.amount);
					if (thisRecipe.getIsSourceItem() && sourceItem != null)
					{
						ItemStack recipeSourceItem = thisRecipe.getSourceItem();
						if (ItemStack.areItemsEqual(sourceItem, recipeSourceItem))
						{
							int itemCount = multiplier * recipeSourceItem.getCount();
							if (sourceItem.getCount() >= itemCount && sourceItem.getMaxStackSize() >= itemCount) {
								return thisRecipe;
							}
						}
					}
					else if (!thisRecipe.getIsSourceItem() && sourceFluid != null)
					{
						FluidStack recipeSourceFluid = thisRecipe.getSourceFluid();
						if (sourceFluid.isFluidEqual(recipeSourceFluid))
						{
							int fluidAmount = multiplier * recipeSourceFluid.amount;
							if (sourceFluid.amount >= fluidAmount) {
								return thisRecipe;
							}
						}
					}
				}
			}
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
