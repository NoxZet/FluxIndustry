package noxzet.fluxindustry.core.tileentity;

import net.darkhax.tesla.api.ITeslaConsumer;
import net.darkhax.tesla.api.ITeslaProducer;
import net.darkhax.tesla.capability.TeslaCapabilities;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.util.EnumFacing;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;
import net.minecraftforge.common.capabilities.Capability;
import net.minecraftforge.energy.CapabilityEnergy;
import net.minecraftforge.energy.IEnergyStorage;
import net.minecraftforge.items.CapabilityItemHandler;
import net.minecraftforge.items.ItemStackHandler;
import noxzet.fluxindustry.core.FluxIndustry;

public class TileElectricInventory extends TileElectric {

	protected ItemStackHandler inventory;
	
	public TileElectricInventory()
	{
		this(0, 0, 0, 0, 0);
	}
	
	public TileElectricInventory(long stored, long capacity, long inputRate, long outputRate, int slots)
	{
		super(stored, capacity, inputRate, outputRate);
		inventory = new ItemStackHandler(slots)
		{
			@Override
			protected void onContentsChanged(int slot)
			{
				TileElectricInventory.this.markDirty();
			}
		};
	}
	
	public ItemStackHandler getStackHandler()
	{
		return inventory;
	}
	
	public int getField(int id)
	{
		return 0;
	}
	
	public void setField(int id, int data)
	{
	}
	
	@Override
	public void readFromNBT(NBTTagCompound compound)
	{
		super.readFromNBT(compound);
		if (compound.hasKey("Inventory"))
			inventory.deserializeNBT((NBTTagCompound) compound.getTag("Inventory"));
	}
	
	@Override
	public NBTTagCompound writeToNBT(NBTTagCompound compound)
	{
		super.writeToNBT(compound);
		compound.setTag("Inventory", inventory.serializeNBT());
		return compound;
	}
	
	@Override
	public boolean hasCapability (Capability<?> capability, EnumFacing facing)
	{
		if (capability == CapabilityItemHandler.ITEM_HANDLER_CAPABILITY)
			return true;
		else
			return super.hasCapability(capability, facing);
	}
	
	@Override
	public <T> T getCapability (Capability<T> capability, EnumFacing facing)
	{
		if (capability == CapabilityItemHandler.ITEM_HANDLER_CAPABILITY)
			return (T)inventory;
		else
			return super.getCapability(capability, facing);
	}
	
	public long slotGiveEnergy(int slot, long Tesla, boolean simulated)
	{
		long amount = 0;
		if (Tesla > 0 && slot < inventory.getSlots())
		{
			ItemStack stack = inventory.getStackInSlot(slot);
			if (!stack.isEmpty())
			{
				if (stack.hasCapability(TeslaCapabilities.CAPABILITY_CONSUMER, null))
				{
					stack = stack.copy();
					ITeslaConsumer capability = (ITeslaConsumer)stack.getCapability(TeslaCapabilities.CAPABILITY_CONSUMER, null);
					amount = capability.givePower(Tesla, simulated);
					if (!simulated)
						inventory.setStackInSlot(slot, stack);
				}
				else if (stack.hasCapability(CapabilityEnergy.ENERGY, null))
				{
					stack = stack.copy();
					IEnergyStorage capability = (IEnergyStorage)stack.getCapability(CapabilityEnergy.ENERGY, null);
					amount = capability.receiveEnergy((int)Tesla, simulated);
					if (!simulated)
						inventory.setStackInSlot(slot, stack);
				}
				else if (stack.getItem() instanceof cofh.api.energy.IEnergyContainerItem)
				{
					stack = stack.copy();
					cofh.api.energy.IEnergyContainerItem container = (cofh.api.energy.IEnergyContainerItem) stack.getItem();
					amount = container.receiveEnergy(stack, (int)Tesla, simulated);
					if (!simulated)
						inventory.setStackInSlot(slot, stack);
				}
			}
		}
		if (amount > 0)
		{
			container.changePower(-amount);
			this.markDirty();
		}
		return amount;
	}
	
	public long slotTakeEnergy(int slot, long Tesla, boolean simulated)
	{
		long amount = 0;
		if (Tesla > 0 && slot < inventory.getSlots())
		{
			ItemStack stack = inventory.getStackInSlot(slot);
			if (!stack.isEmpty())
			{
				if (stack.hasCapability(TeslaCapabilities.CAPABILITY_PRODUCER, null))
				{
					stack = stack.copy();
					ITeslaProducer capability = (ITeslaProducer)stack.getCapability(TeslaCapabilities.CAPABILITY_PRODUCER, null);
					amount = capability.takePower(Tesla, simulated);
					if (!simulated)
						inventory.setStackInSlot(slot, stack);
				}
				else if (stack.hasCapability(CapabilityEnergy.ENERGY, null))
				{
					stack = stack.copy();
					IEnergyStorage capability = (IEnergyStorage)stack.getCapability(CapabilityEnergy.ENERGY, null);
					amount = capability.extractEnergy((int)Tesla, simulated);
					if (!simulated)
						inventory.setStackInSlot(slot, stack);
				}
				else if (stack.getItem() instanceof cofh.api.energy.IEnergyContainerItem)
				{
					stack = stack.copy();
					cofh.api.energy.IEnergyContainerItem container = (cofh.api.energy.IEnergyContainerItem) stack.getItem();
					amount = container.extractEnergy(stack, (int)Tesla, simulated);
					if (!simulated)
						inventory.setStackInSlot(slot, stack);
				}
			}
		}
		if (amount > 0)
		{
			container.changePower(amount);
			this.markDirty();
		}
		return amount;
	}
	
	@Override
	public boolean onActivated(World world, EntityPlayer player, EnumFacing facing)
	{
		if (super.onActivated(world, player, facing))
			return true;
		else
		{
			if(player.isSneaking())
			{
				return false;
			}
			else
			{
				BlockPos pos = super.getPos();
				player.openGui(FluxIndustry.INSTANCE, 0, world, pos.getX(), pos.getY(), pos.getZ());
				return true;
			}
		}
	}
	
	public boolean canInteractWith(EntityPlayer player)
	{
		return true;
	}
	
}
