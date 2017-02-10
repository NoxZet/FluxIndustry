package noxzet.fluxindustry.core.tileentity;

import java.util.ArrayList;

import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraftforge.items.ItemStackHandler;
import noxzet.fluxindustry.core.energy.EnumEnergyHandling;
import noxzet.fluxindustry.core.energy.FluxEnergyContainer;

public class TileElectricMachine extends TileElectricInventory {

	private int burnProgress;
	private ItemStack previousStack = ItemStack.EMPTY;
	private ItemStack previousResultStack = ItemStack.EMPTY;
	private int teslaPerTick = 14;
	private int burnTimeNeeded = 120;
	public int neededCount = 1;
	
	public TileElectricMachine()
	{
		this(0, 2000, 16, 0);
	}
	
	public TileElectricMachine(long stored, long capacity, long inputRate, long outputRate)
	{
		super(stored, capacity, inputRate, outputRate, 3);
		burnProgress = 0;
		this.fluxIsMachine = true;
		this.setAllEnergyHandling(EnumEnergyHandling.INPUT);
	}
	
	@Override
	public void readFromNBT(NBTTagCompound compound)
	{
		super.readFromNBT(compound);
		if (compound.hasKey("burnProgress"))
			burnProgress = compound.getInteger("burnProgress");
		if (compound.hasKey("previousStack"))
			previousStack = new ItemStack((NBTTagCompound)compound.getTag("previousStack"));
		if (compound.hasKey("previousResultStack"))
			previousResultStack = new ItemStack((NBTTagCompound)compound.getTag("previousResultStack"));
	}
	
	@Override
	public NBTTagCompound writeToNBT(NBTTagCompound compound)
	{
		super.writeToNBT(compound);
		compound.setInteger("burnProgress", burnProgress);
		compound.setTag("previousStack", previousStack.serializeNBT());
		compound.setTag("previousResultStack", previousResultStack.serializeNBT());
		return compound;
	}
	
	@Override
	public void update()
	{
		super.update();
		ItemStack thisStack = inventory.getStackInSlot(0).copy();
		long energy = this.slotTakeEnergy(1, container.getMaxInputTick(false), false);
		if (energy>0)
		{
			container.changePower(energy);
			this.markDirty();
		}
		if (!thisStack.isEmpty())
		{
			ItemStack resultStack = getResult(thisStack);
			ItemStack rightStack = inventory.getStackInSlot(2).copy();
			if (!resultStack.isEmpty())
			{
				if (rightStack.isEmpty() || (ItemStack.areItemsEqual(resultStack, rightStack)
						&& rightStack.getCount()+resultStack.getCount()<=rightStack.getMaxStackSize()))
				{
					if (!(ItemStack.areItemsEqual(thisStack, previousStack) && ItemStack.areItemsEqual(resultStack, previousResultStack)))
						burnProgress = 0;
					else if (burnProgress>=burnTimeNeeded)
					{
						if (!rightStack.isEmpty())
							resultStack.grow(rightStack.getCount());
						thisStack.shrink(neededCount);
						inventory.setStackInSlot(2, resultStack);
						inventory.setStackInSlot(0, thisStack);
						burnProgress = 0;
					}
					if (container.getStoredPower()>=teslaPerTick)
					{
						container.changePower(-teslaPerTick);
						burnProgress++;
					}
				}
				else
					burnProgress = 0;
			}
			else
				burnProgress = 0;
			previousStack = thisStack;
			previousResultStack = resultStack;
		}
		else
		{
			burnProgress = 0;
			previousStack = ItemStack.EMPTY;
			previousResultStack = ItemStack.EMPTY;
		}
		this.markDirty();
	}
	
	public ItemStack getResult(ItemStack stack)
	{
		return ItemStack.EMPTY;
	}
	
	public int getBurnProgress()
	{
		return this.burnProgress;
	}
	
	public void setTeslaPerTick(int tpt)
	{
		this.teslaPerTick = tpt;
	}
	
	public void setBurnTimeNeeded(int btn)
	{
		this.burnTimeNeeded = btn;
	}
	
	public int getTeslaPerTick()
	{
		return this.teslaPerTick;
	}
	
	public int getBurnTimeNeeded()
	{
		return this.burnTimeNeeded;
	}
	
	// Flux Industry API
	
	@Override
	public long getFluxAcceptedPerTick()
	{
		return this.teslaPerTick;
	}
	
	@Override
	public double getFluxProgress()
	{
		return ((double)this.burnProgress)/((double)this.burnTimeNeeded);
	}
	
	@Override
	public ArrayList<ItemStack> getFluxInputStacks()
	{
		ArrayList<ItemStack> outputStack = new ArrayList<ItemStack>();
		outputStack.add(inventory.getStackInSlot(0));
		return outputStack;
	}
	
	@Override
	public ArrayList<ItemStack> getFluxOutputStacks()
	{
		ArrayList<ItemStack> outputStack = new ArrayList<ItemStack>();
		outputStack.add(inventory.getStackInSlot(2));
		return outputStack;
	}
		
}
