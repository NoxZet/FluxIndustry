package noxzet.fluxindustry.core.jei;

import java.util.ArrayList;
import java.util.List;

import net.minecraft.item.ItemStack;

public class FluxRecipe {

	public ArrayList<ArrayList<ItemStack>> inputs;
	private ArrayList<ItemStack> outputs;
	
	public FluxRecipe()
	{
		inputs = new ArrayList<>();
		outputs = new ArrayList<>();
	}
	
	public void addInput(ArrayList<ItemStack> stack)
	{
		inputs.add(stack);
	}
	
	public void addOutput(ItemStack stack)
	{
		outputs.add(stack);
	}
	
	public int getInputsSize()
	{
		return inputs.size();
	}
	
	public int getOutputsSize()
	{
		return outputs.size();
	}
	
	public ArrayList<ItemStack> getInput(int i)
	{
		return inputs.get(i);
	}
	
	public ItemStack getOutput(int i)
	{
		return outputs.get(i).copy();
	}
	
	public List<ArrayList<ItemStack>> getInputs()
	{
		return inputs;
	}
	
	public ArrayList<ItemStack> getOutputs()
	{
		return outputs;
	}
}
