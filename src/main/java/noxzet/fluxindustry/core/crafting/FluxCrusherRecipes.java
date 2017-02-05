package noxzet.fluxindustry.core.crafting;

import java.util.HashMap;
import java.util.Map;

import net.minecraft.item.ItemStack;
import net.minecraft.util.NonNullList;
import net.minecraftforge.oredict.OreDictionary;

public class FluxCrusherRecipes {

	public static int neededCount = 1;
	private static final FluxCrusherRecipes INSTANCE = new FluxCrusherRecipes();
	private static final Map<String, ItemStack> oreDictCrushingRecipes = new HashMap<String, ItemStack>();
	private static final Map<ItemStack, ItemStack> nonDictCrushingRecipes = new HashMap<ItemStack, ItemStack>();
	private static final Map<ItemStack, ItemStack> crushingRecipes = new HashMap<ItemStack, ItemStack>();

	public static FluxCrusherRecipes instance()
	{
		return INSTANCE;
	}
	
	// Be careful not to modify output
	public static Map<ItemStack, ItemStack> getCrushingRecipes()
	{
		return crushingRecipes;
	}
	
	public static Map<ItemStack, ItemStack> getNonDictCrushingRecipes()
	{
		return nonDictCrushingRecipes;
	}
	
	public static Map<String, ItemStack> getOreDictCrushingRecipes()
	{
		return oreDictCrushingRecipes;
	}
	
	public static ItemStack getCrushingResult(ItemStack stack)
	{
		for (Map.Entry<ItemStack, ItemStack> entry : crushingRecipes.entrySet())
		{
			if (ItemStack.areItemsEqual(entry.getKey(), stack) && stack.getCount()>=entry.getKey().getCount())
			{
				neededCount = entry.getKey().getCount();
				return entry.getValue();
			}
		}
		return ItemStack.EMPTY;
	}
	
	public void registerRecipe(ItemStack input, ItemStack result)
	{
		registerRecipe(input, result, true);
	}
	
	public void registerRecipe(ItemStack input, ItemStack result, boolean isItemDict)
	{
		if (!isItemDict)
			instance().nonDictCrushingRecipes.put(input, result);
		instance().crushingRecipes.put(input, result);
	}
	
	public void registerOreDictRecipe(String oreDictName, ItemStack result)
	{
		if (!result.isEmpty() && !oreDictName.isEmpty())
		{
			ItemStack input = ItemStack.EMPTY;
			NonNullList<ItemStack> inputList = OreDictionary.getOres(oreDictName);
			instance().oreDictCrushingRecipes.put(oreDictName, result);
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
