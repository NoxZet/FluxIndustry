package noxzet.fluxindustry.core.container;

import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.inventory.Container;
import net.minecraft.inventory.IInventory;
import net.minecraft.inventory.Slot;
import net.minecraft.item.ItemStack;
import noxzet.fluxindustry.core.tileentity.TileElectricInventory;

public class FluxContainer extends Container {

	private TileElectricInventory tile;
	
	public FluxContainer(IInventory playerInventory, TileElectricInventory tile)
	{
		this.tile = tile;
		addPlayerSlots(playerInventory);
	}
	
	public TileElectricInventory getTile()
	{
		return tile;
	}
	
	public void addPlayerSlots(IInventory playerInventory)
	{
        for (int row = 0; row < 3; ++row) {
            for (int col = 0; col < 9; ++col) {
                int x = col * 18 + 8;
                int y = row * 18 + 84;
                this.addSlotToContainer(new Slot(playerInventory, col + row * 9 + 9, x, y));
            }
        }
        for (int row = 0; row < 9; ++row) {
            int x = row * 18 + 8;
            int y = 142;
            this.addSlotToContainer(new Slot(playerInventory, row, x, y));
        }
	}
	
	/*
	 * Tries to put source into target
	 * returns whether source has changed
	 */
	public boolean mergeItemStacks(ItemStack source, ItemStack target)
	{
		if (!target.isEmpty())
		{
			if (target.isStackable())
			{
				int targetLimit = target.getMaxStackSize();
				int targetCount = target.getCount();
				int targetSpace = targetLimit-targetCount;
				if (targetSpace>0)
				{
					if (ItemStack.areItemsEqual(target, source) && ItemStack.areItemStackTagsEqual(target, source))
					{
						int sourceCount = source.getCount();
						if (sourceCount <= targetSpace)
						{
							target.grow(sourceCount);
							source.shrink(sourceCount);
						}
						else
						{
							target.grow(targetSpace);
							source.shrink(targetSpace);
						}
						return true;
					}
				}
			}
		}
		return false;
	}
	
	@Override
	public ItemStack transferStackInSlot(EntityPlayer player, int index)
	{

		if (index<36)
		{
			ItemStack sourceStack, targetStack;
			ItemStack sourceTest, targetTest;
			Slot targetSlot, sourceSlot = this.inventorySlots.get(index);
			if (sourceSlot.getHasStack())
			{
				sourceStack = sourceSlot.getStack();
				for(int i = 36; i < inventorySlots.size(); i++)
				{
					targetSlot = this.inventorySlots.get(i);
					if (targetSlot.isItemValid(sourceStack))
					{
						sourceTest = sourceSlot.getStack().copy();
						targetTest = targetSlot.getStack().copy();
						if (targetTest.isEmpty())
						{
							targetSlot.putStack(sourceTest);
							sourceSlot.putStack(ItemStack.EMPTY);
						}
						else if (this.mergeItemStacks(sourceTest, targetTest))
						{
							targetSlot.putStack(targetTest);
							if (sourceTest.getCount() == 0)
							{
								sourceSlot.putStack(ItemStack.EMPTY);
								break;
							}
							else
								sourceSlot.putStack(sourceTest);
						}
					}
				}
			}
			return ItemStack.EMPTY;
		}
		else
		{
			ItemStack sourceStack, targetStack;
			ItemStack sourceTest, targetTest;
			Slot targetSlot, sourceSlot = this.inventorySlots.get(index);
			if (sourceSlot != null && sourceSlot.getHasStack())
			{
				sourceStack = sourceSlot.getStack();
				if (sourceStack.isStackable())
				{
					int i;
					for (i = 0; i < 36; i++)
					{
						targetSlot = this.inventorySlots.get(i);
						if (targetSlot.getHasStack())
						{
							sourceTest = sourceSlot.getStack().copy();
							targetTest = targetSlot.getStack().copy();
							if (mergeItemStacks(sourceTest, targetTest))
							{
								targetSlot.putStack(targetTest);
								if (sourceTest.getCount() == 0)
								{
									sourceSlot.putStack(ItemStack.EMPTY);
									break;
								}
								else
									sourceSlot.putStack(sourceTest);
							}
						}
					}
					if (i==36)
					{
						for (i = 35; i >= 0; i--)
						{
							targetSlot = this.inventorySlots.get(i);
							if (!targetSlot.getHasStack())
							{
								targetSlot.putStack(sourceSlot.getStack().copy());
								sourceSlot.putStack(ItemStack.EMPTY);
								break;
							}
						}
					}
				}
			}
		}
		return ItemStack.EMPTY;
	}
	
	@Override
	public boolean canInteractWith(EntityPlayer player)
	{
		return tile.canInteractWith(player);
	}
	
}
