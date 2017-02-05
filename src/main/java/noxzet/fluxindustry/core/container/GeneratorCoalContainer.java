package noxzet.fluxindustry.core.container;

import net.minecraft.inventory.IInventory;
import net.minecraftforge.items.IItemHandler;
import noxzet.fluxindustry.core.tileentity.TileGeneratorCoal;

public class GeneratorCoalContainer extends FluxContainer {

	private TileGeneratorCoal tile;
	
	public GeneratorCoalContainer(IInventory playerInventory, TileGeneratorCoal tile)
	{
		super(playerInventory, tile);
		this.tile = tile;
		addContainerSlots();
	}
	
	public void addContainerSlots()
	{
		this.addSlotToContainer(new SlotHandlerFlux((IItemHandler) tile.getStackHandler(), 0, 60, 17, SlotHandlerFlux.BATTERY));
		this.addSlotToContainer(new SlotHandlerFlux((IItemHandler) tile.getStackHandler(), 1, 60, 53, SlotHandlerFlux.COAL));
	}
	
}
