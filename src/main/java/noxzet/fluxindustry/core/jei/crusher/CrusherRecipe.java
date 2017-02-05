package noxzet.fluxindustry.core.jei.crusher;

import java.util.ArrayList;

import net.minecraft.item.ItemStack;
import net.minecraft.util.NonNullList;
import net.minecraftforge.oredict.OreDictionary;
import noxzet.fluxindustry.core.jei.FluxRecipe;

public class CrusherRecipe extends FluxRecipe {

	public CrusherRecipe(ItemStack input, ItemStack output)
	{
		super();
		ArrayList<ItemStack> inputList = new ArrayList<>();
		inputList.add(input);
		addInput(inputList);
		addOutput(output);
	}
	
	public CrusherRecipe(String input, ItemStack output)
	{
		super();
		NonNullList<ItemStack> inputs = OreDictionary.getOres(input);
		ArrayList<ItemStack> inputList = new ArrayList<>();
		for(ItemStack entry: inputs)
		{
			if (!entry.isEmpty())
				inputList.add(entry);
		}
		addInput(inputList);
		addOutput(output);
	}
	
}
