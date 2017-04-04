package noxzet.fluxindustry.core.container;

import net.minecraft.util.ResourceLocation;
import noxzet.fluxindustry.core.FluxIndustry;
import noxzet.fluxindustry.core.tileentity.TileEnergyStorage;

public class EnergyStorageBasicContainerGui extends FluxContainerGui {

	private EnergyStorageBasicContainer container;
	private TileEnergyStorage tile;
	private static final ResourceLocation background = new ResourceLocation(FluxIndustry.MODID, "textures/gui/energy_storage_basic.png");
	
	public EnergyStorageBasicContainerGui(TileEnergyStorage tile, EnergyStorageBasicContainer container)
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
		mc.getTextureManager().bindTexture(background);
		drawTexturedModalRect(guiLeft, guiTop, 0, 0, xSize, ySize);
		if (storedPower>=capacity)
		{
			drawTexturedModalRect(guiLeft+96, guiTop+23, 176, 0, 22, 43);
		}
		else
		{
			int energyHeight = (int) Math.round(((float)storedPower)/((float)capacity)*40);
			drawTexturedModalRect(guiLeft+96, guiTop+65-energyHeight, 176, 42-energyHeight, 22, energyHeight);
		}
	}
	
	@Override
	protected void drawGuiContainerForegroundLayer(int mouseX, int mouseY)
	{
		if (mouseX >= guiLeft+96 && mouseY >= guiTop+23 && mouseX < guiLeft+118 && mouseY < guiTop+66)
			this.drawEnergy(mouseX, mouseY, (int) tile.getFluxStoredPower(), (int) tile.getFluxMaxStoredPower());
	}
	
}
