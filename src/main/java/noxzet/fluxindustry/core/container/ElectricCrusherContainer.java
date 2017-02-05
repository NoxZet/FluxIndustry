package noxzet.fluxindustry.core.container;

import net.minecraft.inventory.IInventory;
import net.minecraftforge.items.IItemHandler;
import noxzet.fluxindustry.core.tileentity.TileElectricCrusher;

public class ElectricCrusherContainer extends FluxContainer {

	private TileElectricCrusher tile;
	
	public ElectricCrusherContainer(IInventory playerInventory, TileElectricCrusher tile)
	{
		super(playerInventory, tile);
		this.tile = tile;
		addContainerSlots();
	}
	
	public void addContainerSlots()
	{
		this.addSlotToContainer(new SlotHandlerFlux((IItemHandler) tile.getStackHandler(), 0, 50, 17, SlotHandlerFlux.CRUSHABLE));
		this.addSlotToContainer(new SlotHandlerFlux((IItemHandler) tile.getStackHandler(), 1, 50, 53, SlotHandlerFlux.BATTERY));
		this.addSlotToContainer(new SlotHandlerFlux((IItemHandler) tile.getStackHandler(), 2, 118, 35, SlotHandlerFlux.NONE));
	}
	
}
