package noxzet.fluxindustry.core.tileentity;

import java.util.ArrayList;

import net.minecraft.block.Block;
import net.minecraft.init.Blocks;
import net.minecraft.init.Items;
import net.minecraft.item.Item;
import net.minecraft.item.ItemBlock;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraftforge.items.ItemStackHandler;
import noxzet.fluxindustry.core.energy.EnumEnergyHandling;
import noxzet.fluxindustry.core.energy.FluxEnergyContainer;

public class TileGeneratorCoal extends TileElectricInventory {
	
	private int fuelLevel, fuelMax;
	private int powerPerTick;
	private static final int FIELD_FUEL_MAX = 0;
	private static final int FIELD_FUEL_LEVEL = 1;
	private static final int FIELD_ENERGY_CAPACITY = 2;
	private static final int FIELD_ENERGY_STORED = 3;
	
	public TileGeneratorCoal()
	{
		this(0, 20000, 0, 40);
	}
	
	public TileGeneratorCoal(long stored, long capacity, long inputRate, long outputRate)
	{
		super(stored, capacity, inputRate, outputRate, 2);
		fuelLevel = 0;
		fuelMax = 0;
		powerPerTick = 30;
		this.fluxIsGenerator = true;
		this.fluxIsFuelPowered = true;
		this.setAllEnergyHandling(EnumEnergyHandling.OUTPUT);
	}
	
	@Override
	public void update()
	{
		super.update();
		if (!world.isRemote)
		{
			if (fuelLevel>0)
			{
				fuelLevel--;
				container.changePower(30);
				this.markDirty();
			}
			if (fuelLevel<=0 && this.getStoredPower()<this.getCapacity())
			{
				int fuel = getFuelBurnTime(inventory.getStackInSlot(1));
				if (fuel>0)
				{
					fuelLevel += fuel;
					fuelMax = fuel;
					ItemStack stack = (inventory.getStackInSlot(1)).copy();
					stack.shrink(1);
					if (stack.getCount() == 0)
						stack = ItemStack.EMPTY;
					inventory.setStackInSlot(1, stack);
				}
			}
			long energy = this.slotGiveEnergy(0, container.getMaxOutputTick(false), false);
			if (energy>0)
			{
				container.changePower(-energy);
				this.markDirty();
			}
		}
	}
	
	public static int getFuelBurnTime(ItemStack stack)
	{
		Item item = stack.getItem();
		if (item == Items.COAL)
		{
			return 640;
		}
		else if (item instanceof ItemBlock)
		{
			//Block block = Block.getBlockFromItem(item);
			if (Block.getBlockFromItem(item)==Blocks.COAL_BLOCK)
				return 640*9;
		}
		return 0;
	}
	
	public int getFuelLevel()
	{
		return fuelLevel;
	}
	
	public int getFuelMax()
	{
		return fuelMax;
	}
	
	@Override
	public int getField(int id)
	{
		switch (id)
		{
			case FIELD_FUEL_LEVEL: return this.fuelLevel;
			case FIELD_FUEL_MAX: return this.fuelMax;
			case FIELD_ENERGY_STORED: return (int)container.getStoredPower();
			case FIELD_ENERGY_CAPACITY: return (int)container.getCapacity();
			default: return 0;
		}
	}
	
	@Override
	public void setField(int id, int data)
	{
		switch (id)
		{
			case FIELD_FUEL_LEVEL: this.fuelLevel = data; break;
			case FIELD_FUEL_MAX: this.fuelMax = data; break;
			case FIELD_ENERGY_STORED: container.setStoredPower(data); break;
			case FIELD_ENERGY_CAPACITY: container.setCapacity(data); break;
		}
	}
	
	@Override
	public void readFromNBT(NBTTagCompound compound)
	{
		super.readFromNBT(compound);
		if (compound.hasKey("fuelLevel"))
			fuelLevel = compound.getInteger("fuelLevel");
		if (compound.hasKey("fuelMax"))
			fuelMax = compound.getInteger("fuelMax");
	}
	
	@Override
	public NBTTagCompound writeToNBT(NBTTagCompound compound)
	{
		super.writeToNBT(compound);
		compound.setInteger("fuelLevel", fuelLevel);
		compound.setInteger("fuelMax", fuelMax);
		return compound;
	}
	
	@Override
	public boolean getFluxIsGeneratorRunning()
	{
		if (fuelLevel>0)
			return true;
		else
			return false;
	}
	
	@Override
	public long getFluxGeneratedPerTick()
	{
		return this.powerPerTick;
	}
	
	@Override
	public int getFluxFuelRemaining()
	{
		ItemStack fuelStack = inventory.getStackInSlot(1);
		return fuelLevel+getFuelBurnTime(fuelStack)*fuelStack.getCount();
	}
	
	@Override
	public ArrayList<ItemStack> getFluxInputStacks()
	{
		ArrayList<ItemStack> outputStack = new ArrayList<ItemStack>();
		outputStack.add(inventory.getStackInSlot(1));
		return outputStack;
	}

}
