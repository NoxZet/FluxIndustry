package noxzet.fluxindustry.core.tileentity;

import java.util.ArrayList;

import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NBTTagCompound;
import noxzet.fluxindustry.core.energy.EnumEnergyHandling;

public class TileElectricMachine extends TileElectricInventory {

	protected int burnProgress, outputs;
	protected ItemStack previousStack;
	protected ItemStack[] previousResultStack;
	protected int teslaPerTick = 14;
	protected int burnTimeNeeded = 120;
	public int neededCount = 1;
	public int chosenResult = -1;
	public static final int FIELD_ENERGY_CAPACITY = 0;
	public static final int FIELD_ENERGY_STORED = 1;
	public static final int FIELD_BURN_TIME = 2;
	public static final int FIELD_BURN_PROGRESS = 3;
	
	public TileElectricMachine()
	{
		this(0, 2000, 40, 0, 3);
	}
		
	public TileElectricMachine(long stored, long capacity, long inputRate, long outputRate, int slots)
	{
		super(stored, capacity, inputRate, outputRate, slots);
		burnProgress = 0;
		outputs = slots-2;
		this.previousResultStack = new ItemStack[outputs];
		this.setPreviousEmpty();
		this.fluxIsMachine = true;
		this.setAllEnergyHandling(EnumEnergyHandling.INPUT);
	}
	
	@Override
	public void update()
	{
		if (!world.isRemote)
		{
			isLit = false;
			this.slotTakeEnergy(1, container.getMaxInputTick(false), false);
			ItemStack thisStack = inventory.getStackInSlot(0).copy();
			if (!thisStack.isEmpty())
			{
				ItemStack[] resultStack = getResult(thisStack);
				ItemStack[] rightStack = new ItemStack[outputs];
				for (int i = 0; i < outputs; i++)
					rightStack[i] = inventory.getStackInSlot(2+i).copy();
				if (!resultStack[0].isEmpty())
				{
					boolean reset = !ItemStack.areItemsEqual(thisStack, previousStack);
					for (int i = 0; i < outputs && reset == false; i++)
						if (!rightStack[i].isEmpty() && !ItemStack.areItemsEqual(resultStack[i], previousResultStack[i]))
							reset = true;
					if (reset)
						burnProgress = 0;
					else if (burnProgress>=burnTimeNeeded)
					{
						if (chosenResult < 0)
							chosenResult++;
						boolean process = true;
						for (int i = 0; i < outputs && reset == false; i++)
							if (!rightStack[i].isEmpty() && ItemStack.areItemsEqual(rightStack[i], resultStack[i]) && rightStack[i].getCount()+resultStack[i].getCount() > rightStack[i].getMaxStackSize())
								process = false;
						if (process)
						{
							thisStack.shrink(neededCount);
							inventory.setStackInSlot(0, thisStack);
							if (chosenResult >= 0)
							{
								if (rightStack[chosenResult].isEmpty())
									inventory.setStackInSlot(2+chosenResult, resultStack[chosenResult].copy());
								else
								{
									rightStack[chosenResult].grow(resultStack[chosenResult].getCount());
									inventory.setStackInSlot(2+chosenResult, rightStack[chosenResult]);
								}
							}
							// TODO add to all output slots if (chosenResult == -1)
							burnProgress = 0;
							chosenResult = -1;
						}
					}
					if (burnProgress < burnTimeNeeded && container.getStoredPower()>=teslaPerTick)
					{
						container.changePower(-teslaPerTick);
						burnProgress++;
						isLit = true;
					}
				}
				else
					burnProgress = 0;
				this.setPrevious(thisStack, resultStack);
			}
			else
			{
				burnProgress = 0;
				this.setPreviousEmpty();
			}
			this.markDirty();
		}
		super.update();
	}
	
	protected void updateElectric()
	{
		super.update();
	}
	
	protected void setPrevious(ItemStack stack, ItemStack[] resultStack)
	{
		this.previousStack = stack;
		this.setPreviousResult(resultStack);
	}
	
	protected void setPreviousResult(ItemStack[] resultStack)
	{
		int limit = Math.min(resultStack.length, outputs);
		for (int i = 0; i < limit; i++)
			this.previousResultStack[i] = resultStack[i].copy();
	}
	
	protected void setPreviousEmpty()
	{
		previousStack = ItemStack.EMPTY;
		this.setPreviousResultEmpty();
	}
	
	protected void setPreviousResultEmpty()
	{
		for (int i = 0; i < outputs; i++)
			this.previousResultStack[i] = ItemStack.EMPTY;
	}
	
	@Override
	public int getField(int id)
	{
		switch (id)
		{
			case FIELD_ENERGY_STORED: return (int)container.getStoredPower();
			case FIELD_ENERGY_CAPACITY: return (int)container.getCapacity();
			case FIELD_BURN_TIME: return this.burnTimeNeeded;
			case FIELD_BURN_PROGRESS: return this.burnProgress;
			default: return 0;
		}
	}
	
	@Override
	public void setField(int id, int data)
	{
		switch (id)
		{
			case FIELD_ENERGY_STORED: container.setStoredPower(data); break;
			case FIELD_ENERGY_CAPACITY: container.setCapacity(data); break;
			case FIELD_BURN_TIME: this.burnTimeNeeded = data; break;
			case FIELD_BURN_PROGRESS: this.burnProgress = data; break;
		}
	}
	
	@Override
	public void readFromNBT(NBTTagCompound compound)
	{
		super.readFromNBT(compound);
		if (compound.hasKey("burnProgress"))
			burnProgress = compound.getInteger("burnProgress");
		if (compound.hasKey("previousStack"))
			previousStack = new ItemStack((NBTTagCompound)compound.getTag("previousStack"));
		for (int i = 0; i < outputs; i++)
			if (compound.hasKey("previousResultStack"+i))
				previousResultStack[i] = new ItemStack((NBTTagCompound)compound.getTag("previousResultStack"+i));
	}
	
	@Override
	public NBTTagCompound writeToNBT(NBTTagCompound compound)
	{
		super.writeToNBT(compound);
		compound.setInteger("burnProgress", burnProgress);
		compound.setTag("previousStack", previousStack.serializeNBT());
		for (int i = 0; i < outputs; i++)
			compound.setTag("previousResultStack"+i, previousResultStack[i].serializeNBT());
		return compound;
	}
	
	public ItemStack[] getResult(ItemStack stack)
	{
		return new ItemStack[]{ItemStack.EMPTY};
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
