package noxzet.fluxindustry.core.container.gui;

import net.minecraft.util.ResourceLocation;
import noxzet.fluxindustry.core.FluxIndustry;
import noxzet.fluxindustry.core.container.ElectricFurnaceContainer;
import noxzet.fluxindustry.core.tileentity.TileElectricFurnace;

public class ElectricFurnaceContainerGui extends FluxContainerGui {

	private ElectricFurnaceContainer container;
	private TileElectricFurnace tile;
	private static final ResourceLocation background = new ResourceLocation(FluxIndustry.MODID, "textures/gui/electric_furnace.png");
	
	public ElectricFurnaceContainerGui(TileElectricFurnace tile, ElectricFurnaceContainer container)
	{
		super(tile, container);
		this.container = container;
		this.tile = tile;
	}
	
	@Override
	protected void drawGuiContainerBackgroundLayer(float partialTicks, int mouseX, int mouseY)
	{
		long storedPower = tile.getFluxStoredPower();
		long capacity = tile.getFluxMaxStoredPower();
		int progress = tile.getBurnProgress();
		int progressMax = tile.getBurnTimeNeeded();
		mc.getTextureManager().bindTexture(background);
		drawTexturedModalRect(guiLeft, guiTop, 0, 0, xSize, ySize);
		if (storedPower>0)
		{
			int energyHeight = (int) Math.round(((float)storedPower)/((float)capacity)*14);
			drawTexturedModalRect(guiLeft+57, guiTop+50-energyHeight, 176, 14-energyHeight, 14, energyHeight);
		}
		if (progress>0)
		{
			int progressPercent = (int) Math.ceil(((float)progress)/((float)progressMax)*24);
			drawTexturedModalRect(guiLeft+79, guiTop+34, 176, 14, progressPercent, 17);
		}
	}
	
}
