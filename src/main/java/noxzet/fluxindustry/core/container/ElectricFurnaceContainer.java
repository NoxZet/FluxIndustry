package noxzet.fluxindustry.core.container;

import net.minecraft.inventory.IInventory;
import net.minecraftforge.items.IItemHandler;
import noxzet.fluxindustry.core.tileentity.TileElectricFurnace;

public class ElectricFurnaceContainer extends FluxContainer {

	private TileElectricFurnace tile;
	
	public ElectricFurnaceContainer(IInventory playerInventory, TileElectricFurnace tile)
	{
		super(playerInventory, tile);
		this.tile = tile;
		addContainerSlots();
	}
	
	public void addContainerSlots()
	{
		this.addSlotToContainer(new SlotHandlerFlux((IItemHandler) tile.getStackHandler(), 0, 56, 17, SlotHandlerFlux.SMELTABLE));
		this.addSlotToContainer(new SlotHandlerFlux((IItemHandler) tile.getStackHandler(), 1, 56, 53, SlotHandlerFlux.BATTERY));
		this.addSlotToContainer(new SlotHandlerFlux((IItemHandler) tile.getStackHandler(), 2, 116, 35, SlotHandlerFlux.NONE));
	}
	
}
