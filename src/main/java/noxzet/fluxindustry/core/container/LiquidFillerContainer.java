package noxzet.fluxindustry.core.container;

import net.minecraft.inventory.IInventory;
import net.minecraftforge.items.IItemHandler;
import noxzet.fluxindustry.core.tileentity.TileLiquidFiller;

public class LiquidFillerContainer extends FluxContainer {

	private TileLiquidFiller tile;
	
	public LiquidFillerContainer(IInventory playerInventory, TileLiquidFiller tile)
	{
		super(playerInventory, tile);
		this.tile = tile;
		this.values = new int[]{0, 0, 0, 0, 0, 0, 0, 0, 0};
		addContainerSlots();
	}
	
	public void addContainerSlots()
	{
		this.addSlotToContainer(new SlotHandlerFlux((IItemHandler) tile.getStackHandler(), 0, 31, 17, SlotHandlerFlux.NORMAL));
		this.addSlotToContainer(new SlotHandlerFlux((IItemHandler) tile.getStackHandler(), 1, 31, 53, SlotHandlerFlux.BATTERY));
		this.addSlotToContainer(new SlotHandlerFlux((IItemHandler) tile.getStackHandler(), 2, 129, 35, SlotHandlerFlux.FLUIDHANDLER));
	}
	
}
