package noxzet.fluxindustry.core.tileentity;

import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.util.EnumFacing;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;
import net.minecraftforge.common.capabilities.Capability;
import net.minecraftforge.items.CapabilityItemHandler;
import net.minecraftforge.items.ItemStackHandler;
import noxzet.fluxindustry.core.FluxIndustry;

public class TileElectricInventory extends TileElectric {

	private ItemStackHandler inventory;
	
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
	
	@Override
	public void readFromNBT(NBTTagCompound compound)
	{
		super.readFromNBT(compound);
		if (compound.hasKey("items"))
			inventory.deserializeNBT((NBTTagCompound) compound.getTag("items"));
	}
	
	@Override
	public NBTTagCompound writeToNBT(NBTTagCompound compound)
	{
		super.writeToNBT(compound);
		compound.setTag("items", inventory.serializeNBT());
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
