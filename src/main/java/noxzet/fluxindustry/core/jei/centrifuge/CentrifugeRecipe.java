package noxzet.fluxindustry.core.jei.centrifuge;

import java.util.ArrayList;

import net.minecraft.item.ItemStack;
import net.minecraft.util.NonNullList;
import net.minecraftforge.oredict.OreDictionary;
import noxzet.fluxindustry.core.crafting.FluxCentrifugeRecipe;
import noxzet.fluxindustry.core.jei.FluxRecipe;

public class CentrifugeRecipe extends FluxRecipe {

	private int[] chance;
	
	public CentrifugeRecipe(ItemStack input, FluxCentrifugeRecipe output)
	{
		super();
		ArrayList<ItemStack> inputList = new ArrayList<>();
		inputList.add(input);
		addInput(inputList);
		for (ItemStack entry : output.getOutputStacks())
			addOutput(entry);
		chance = output.getOutputChances();
	}
	
	public CentrifugeRecipe(String input, FluxCentrifugeRecipe output)
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
		for (ItemStack entry : output.getOutputStacks())
			addOutput(entry);
		chance = output.getOutputChances();
	}
	
	public int[] getOutputChances()
	{
		return chance;
	}
	
	public int getOutputChance(int number)
	{
		return chance[number];
	}
	
}
