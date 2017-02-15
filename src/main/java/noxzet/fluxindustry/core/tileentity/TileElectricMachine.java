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
	public static final int FIELD_ENERGY_CAPACITY = 0;
	public static final int FIELD_ENERGY_STORED = 1;
	public static final int FIELD_BURN_TIME = 2;
	public static final int FIELD_BURN_PROGRESS = 3;
	
	public TileElectricMachine()
	{
		this(0, 2000, 40, 0);
	}
	
	public TileElectricMachine(long stored, long capacity, long inputRate, long outputRate)
	{
		super(stored, capacity, inputRate, outputRate, 3);
		burnProgress = 0;
		this.fluxIsMachine = true;
		this.setAllEnergyHandling(EnumEnergyHandling.INPUT);
	}
	
	@Override
	public void update()
	{
		if (!world.isRemote)
		{
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
			// Render
			if (burnProgress>0 && container.getStoredPower()>=teslaPerTick)
			{
				isLitHold = true;
				isLit = true;
			}
			if (isLitHold==true)
				isLitHold = false;
			else
				isLit = false;
		}
		super.update();
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
