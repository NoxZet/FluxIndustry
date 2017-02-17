package noxzet.fluxindustry.core.crafting;

import java.util.HashMap;
import java.util.Map;

import net.minecraft.item.ItemStack;
import net.minecraft.util.NonNullList;
import net.minecraftforge.oredict.OreDictionary;

public class FluxCentrifugeRecipes {

	public static int neededCount = 1;
	private static final FluxCentrifugeRecipes INSTANCE = new FluxCentrifugeRecipes();
	private static final Map<String, FluxCentrifugeRecipe> oreDictCentrifugeRecipes = new HashMap<>();
	private static final Map<ItemStack, FluxCentrifugeRecipe> nonDictCentrifugeRecipes = new HashMap<>();
	private static final Map<ItemStack, FluxCentrifugeRecipe> centrifugeRecipes = new HashMap<>();
	
	public static FluxCentrifugeRecipes instance()
	{
		return INSTANCE;
	}

	// Be careful not to modify output
	public static Map<ItemStack, FluxCentrifugeRecipe> getCentrifugeRecipes()
	{
		return centrifugeRecipes;
	}
	
	public static Map<ItemStack, FluxCentrifugeRecipe> getNonDictCentrifugeRecipes()
	{
		return nonDictCentrifugeRecipes;
	}
	
	public static Map<String, FluxCentrifugeRecipe> getOreDictCentrifugeRecipes()
	{
		return oreDictCentrifugeRecipes;
	}
	
	public static FluxCentrifugeRecipe getCentrifugeResult(ItemStack stack)
	{
		for (Map.Entry<ItemStack, FluxCentrifugeRecipe> entry : centrifugeRecipes.entrySet())
		{
			if (ItemStack.areItemsEqual(entry.getKey(), stack) && stack.getCount()>=entry.getKey().getCount())
			{
				neededCount = entry.getKey().getCount();
				return entry.getValue();
			}
		}
		return null;
	}
	
	public void registerRecipe(ItemStack input, FluxCentrifugeRecipe result, boolean isItemDict)
	{
		if (!isItemDict)
			instance().nonDictCentrifugeRecipes.put(input, result);
		instance().centrifugeRecipes.put(input, result);
	}
	
	public void registerOreDictRecipe(String oreDictName, FluxCentrifugeRecipe result)
	{
		if (!oreDictName.isEmpty())
		{
			ItemStack input = ItemStack.EMPTY;
			NonNullList<ItemStack> inputList = OreDictionary.getOres(oreDictName);
			instance().oreDictCentrifugeRecipes.put(oreDictName, result);
			int listSize = inputList.size();
			for(ItemStack entry : inputList)
			{
				input = entry;
				if (!input.isEmpty())
					registerRecipe(input, result, true);
			}
		}
	}
	
}
