package noxzet.fluxindustry.core.container;

import net.darkhax.tesla.capability.TeslaCapabilities;
import net.minecraft.item.ItemStack;
import net.minecraftforge.energy.CapabilityEnergy;
import net.minecraftforge.fluids.capability.CapabilityFluidHandler;
import net.minecraftforge.items.IItemHandler;
import net.minecraftforge.items.SlotItemHandler;
import noxzet.fluxindustry.core.tileentity.TileCentrifuge;
import noxzet.fluxindustry.core.tileentity.TileElectricCrusher;
import noxzet.fluxindustry.core.tileentity.TileElectricFurnace;
import noxzet.fluxindustry.core.tileentity.TileGeneratorCoal;

public class SlotHandlerFlux extends SlotItemHandler {

	public static final int NORMAL = 0;
	public static final int COAL = 1;
	public static final int BATTERY = 2;
	public static final int FLUIDHANDLER = 3;
	public static final int NONE = 4;
	public static final int SMELTABLE = 5;
	public static final int CRUSHABLE = 6;
	public static final int CENTRIFUGE = 7;
	private int type;
	
	public SlotHandlerFlux (IItemHandler itemHandler, int index, int xPosition, int yPosition)
	{
		this(itemHandler, index, xPosition, yPosition, 0);
	}
	
	public SlotHandlerFlux (IItemHandler itemHandler, int index, int xPosition, int yPosition, int type)
	{
		super(itemHandler, index, xPosition, yPosition);
		this.type = type;
	}
	
	@Override
	public boolean isItemValid(ItemStack stack)
	{
		ItemStack copy;
		switch (type)
		{
			case NONE: return false;
			case COAL:
				if (TileGeneratorCoal.getFuelBurnTime(stack)>0)
					return true;
				else
					return false;
			case BATTERY:
				if (stack.hasCapability(TeslaCapabilities.CAPABILITY_HOLDER, null) ||
						stack.hasCapability(CapabilityEnergy.ENERGY, null) ||
						stack.getItem() instanceof cofh.api.energy.IEnergyStorage )
				{
					return true;
				}
				else
					return false;
			case FLUIDHANDLER:
				if (stack.hasCapability(CapabilityFluidHandler.FLUID_HANDLER_ITEM_CAPABILITY, null))
					return true;
				else
					return false;
			case SMELTABLE:
				copy = stack.copy();
				copy.setCount(copy.getMaxStackSize());
				return !(TileElectricFurnace.getSmeltingResult(copy).isEmpty());
			case CRUSHABLE:
				copy = stack.copy();
				copy.setCount(copy.getMaxStackSize());
				return !(TileElectricCrusher.getCrushingResult(copy).isEmpty());
			case CENTRIFUGE:
				copy = stack.copy();
				copy.setCount(copy.getMaxStackSize());
				return !(TileCentrifuge.getCentrifugeResult(copy)==null);
			default: return true;
		}
	}
	
}
