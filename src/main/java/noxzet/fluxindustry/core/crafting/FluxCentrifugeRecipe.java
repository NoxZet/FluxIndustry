package noxzet.fluxindustry.core.crafting;

import java.util.Random;

import net.minecraft.item.ItemStack;

public class FluxCentrifugeRecipe {

	private ItemStack inputStack;
	private ItemStack[] outputStacks;
	private int range;
	private int[] chance;
	
	public FluxCentrifugeRecipe(ItemStack inputStack, ItemStack[] outputStacks, int[] chance)
	{
		this.inputStack = inputStack;
		this.outputStacks = outputStacks;
		this.chance = chance;
		this.range = 0;
		for (int value : chance)
			range += value;
	}
	
	public ItemStack[] getOutputStacks()
	{
		return outputStacks;
	}
	
	public ItemStack getOutputStack(int number)
	{
		return outputStacks[number];
	}
	
	public int getOutputRandomNumber()
	{
		Random rand = new Random();
		int generated = rand.nextInt(range);
		if (chance[2] < generated)
			return 2;
		else if (chance[1] < generated)
			return 1;
		else
			return 0;
	}
	
	public int[] getOutputChances()
	{
		return new int[]{getOutputChanceForNumber(0), getOutputChanceForNumber(1), getOutputChanceForNumber(2)};
	}
	
	public int getOutputChanceForNumber(int number)
	{
		return Math.round(((float)chance[number])/((float)range)*100);
	}
	
}
