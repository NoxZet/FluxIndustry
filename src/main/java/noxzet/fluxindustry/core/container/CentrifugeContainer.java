package noxzet.fluxindustry.core.container;

import net.minecraft.inventory.IInventory;
import net.minecraftforge.items.IItemHandler;
import noxzet.fluxindustry.core.tileentity.TileCentrifuge;

public class CentrifugeContainer extends FluxContainer {

	private TileCentrifuge tile;
	
	public CentrifugeContainer(IInventory playerInventory, TileCentrifuge tile)
	{
		super(playerInventory, tile);
		this.tile = tile;
		this.values = new int[]{0, 0, 0, 0};
		addContainerSlots();
	}
	
	public void addContainerSlots()
	{
		this.addSlotToContainer(new SlotHandlerFlux((IItemHandler) tile.getStackHandler(), 0, 47, 17, SlotHandlerFlux.CENTRIFUGE));
		this.addSlotToContainer(new SlotHandlerFlux((IItemHandler) tile.getStackHandler(), 1, 47, 53, SlotHandlerFlux.BATTERY));
		this.addSlotToContainer(new SlotHandlerFlux((IItemHandler) tile.getStackHandler(), 2, 113, 17, SlotHandlerFlux.NONE));
		this.addSlotToContainer(new SlotHandlerFlux((IItemHandler) tile.getStackHandler(), 3, 113, 35, SlotHandlerFlux.NONE));
		this.addSlotToContainer(new SlotHandlerFlux((IItemHandler) tile.getStackHandler(), 4, 113, 53, SlotHandlerFlux.NONE));
	}
	
}
