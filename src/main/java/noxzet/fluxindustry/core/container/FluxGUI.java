package noxzet.fluxindustry.core.container;

import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;
import net.minecraftforge.fml.common.network.IGuiHandler;
import noxzet.fluxindustry.core.container.gui.CentrifugeContainerGui;
import noxzet.fluxindustry.core.container.gui.ElectricCrusherContainerGui;
import noxzet.fluxindustry.core.container.gui.ElectricFurnaceContainerGui;
import noxzet.fluxindustry.core.container.gui.EnergyStorageBasicContainerGui;
import noxzet.fluxindustry.core.container.gui.FluxContainerGui;
import noxzet.fluxindustry.core.container.gui.GeneratorCoalContainerGui;
import noxzet.fluxindustry.core.container.gui.LiquidFillerContainerGui;
import noxzet.fluxindustry.core.tileentity.TileCentrifuge;
import noxzet.fluxindustry.core.tileentity.TileElectricCrusher;
import noxzet.fluxindustry.core.tileentity.TileElectricFurnace;
import noxzet.fluxindustry.core.tileentity.TileElectricInventory;
import noxzet.fluxindustry.core.tileentity.TileEnergyStorage;
import noxzet.fluxindustry.core.tileentity.TileGeneratorCoal;
import noxzet.fluxindustry.core.tileentity.TileLiquidFiller;

public class FluxGUI implements IGuiHandler {

	@Override
	public Object getServerGuiElement(int ID, EntityPlayer player, World world, int x, int y, int z) {
		TileEntity tile = world.getTileEntity(new BlockPos(x, y, z));
		if (tile instanceof TileGeneratorCoal) {
			return new GeneratorCoalContainer(player.inventory, (TileGeneratorCoal) tile);
		}
		if (tile instanceof TileElectricFurnace) {
			return new ElectricFurnaceContainer(player.inventory, (TileElectricFurnace) tile);
		}
		if (tile instanceof TileElectricCrusher) {
			return new ElectricCrusherContainer(player.inventory, (TileElectricCrusher) tile);
		}
		if (tile instanceof TileCentrifuge) {
			return new CentrifugeContainer(player.inventory, (TileCentrifuge) tile);
		}
		if (tile instanceof TileLiquidFiller) {
			return new LiquidFillerContainer(player.inventory, (TileLiquidFiller) tile);
		}
		if (tile instanceof TileEnergyStorage) {
			if (((TileEnergyStorage)tile).getStorageType() == TileEnergyStorage.TYPE_BASIC);
				return new EnergyStorageBasicContainer(player.inventory, (TileEnergyStorage) tile);
		}
		if (tile instanceof TileElectricInventory) {
			return new FluxContainer(player.inventory, (TileElectricInventory) tile);
		}
		return null;
	}

	@Override
	public Object getClientGuiElement(int ID, EntityPlayer player, World world, int x, int y, int z) {
		TileEntity tile = world.getTileEntity(new BlockPos(x, y, z));
		if (tile instanceof TileGeneratorCoal) {
			TileGeneratorCoal tileElectric = (TileGeneratorCoal) tile;
			return new GeneratorCoalContainerGui(tileElectric, new GeneratorCoalContainer(player.inventory, tileElectric));
		}
		if (tile instanceof TileElectricFurnace) {
			TileElectricFurnace tileElectric = (TileElectricFurnace) tile;
			return new ElectricFurnaceContainerGui(tileElectric, new ElectricFurnaceContainer(player.inventory, tileElectric));
		}
		if (tile instanceof TileElectricCrusher) {
			TileElectricCrusher tileElectric = (TileElectricCrusher) tile;
			return new ElectricCrusherContainerGui(tileElectric, new ElectricCrusherContainer(player.inventory, tileElectric));
		}
		if (tile instanceof TileCentrifuge) {
			TileCentrifuge tileElectric = (TileCentrifuge) tile;
			return new CentrifugeContainerGui(tileElectric, new CentrifugeContainer(player.inventory, tileElectric));
		}
		if (tile instanceof TileLiquidFiller) {
			TileLiquidFiller tileElectric = (TileLiquidFiller) tile;
			return new LiquidFillerContainerGui(tileElectric, new LiquidFillerContainer(player.inventory, tileElectric));
		}
		if (tile instanceof TileEnergyStorage) {
			TileEnergyStorage tileElectric = (TileEnergyStorage) tile;
			if (tileElectric.getStorageType() == TileEnergyStorage.TYPE_BASIC);
				return new EnergyStorageBasicContainerGui(tileElectric, new EnergyStorageBasicContainer(player.inventory, tileElectric));
		}
		if (tile instanceof TileElectricInventory) {
			TileElectricInventory tileElectric = (TileElectricInventory) tile;
			return new FluxContainerGui(tileElectric, new FluxContainer(player.inventory, tileElectric));
		}
		return null;
	}

}
