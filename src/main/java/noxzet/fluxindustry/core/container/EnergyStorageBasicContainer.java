package noxzet.fluxindustry.core.container;

import net.minecraft.inventory.IInventory;
import net.minecraftforge.items.IItemHandler;
import noxzet.fluxindustry.core.tileentity.TileEnergyStorage;

public class EnergyStorageBasicContainer extends FluxContainer {

	private TileEnergyStorage tile;
	
	public EnergyStorageBasicContainer(IInventory playerInventory, TileEnergyStorage tile)
	{
		super(playerInventory, tile);
		this.tile = tile;
		this.values = new int[]{0, 0};
		addContainerSlots();
	}
	
	public void addContainerSlots()
	{
		this.addSlotToContainer(new SlotHandlerFlux((IItemHandler) tile.getStackHandler(), 0, 60, 17, SlotHandlerFlux.BATTERY));
		this.addSlotToContainer(new SlotHandlerFlux((IItemHandler) tile.getStackHandler(), 1, 60, 53, SlotHandlerFlux.BATTERY));
	}
	
}
