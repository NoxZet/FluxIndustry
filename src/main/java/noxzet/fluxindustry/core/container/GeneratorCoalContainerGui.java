package noxzet.fluxindustry.core.container;

import net.minecraft.util.ResourceLocation;
import noxzet.fluxindustry.core.FluxIndustry;
import noxzet.fluxindustry.core.tileentity.TileGeneratorCoal;

public class GeneratorCoalContainerGui extends FluxContainerGui {

	private GeneratorCoalContainer container;
	private TileGeneratorCoal tile;
	private static final ResourceLocation background = new ResourceLocation(FluxIndustry.MODID, "textures/gui/generator_coal.png");
	
	public GeneratorCoalContainerGui(TileGeneratorCoal tile, GeneratorCoalContainer container)
	{
		super(tile, container);
		this.container = container;
		this.tile = tile;
	}
	
	@Override
	protected void drawGuiContainerBackgroundLayer(float partialTicks, int mouseX, int mouseY)
	{
		int fuelLevel = tile.getFuelLevel();
		int fuelMax = tile.getFuelMax();
		long storedPower = tile.getStoredPower();
		long capacity = tile.getCapacity();
		mc.getTextureManager().bindTexture(background);
		drawTexturedModalRect(guiLeft, guiTop, 0, 0, xSize, ySize);
		if (fuelLevel>0)
		{
			int burnHeight = (int) Math.ceil(((float)fuelLevel)/((float)fuelMax)*14);
			drawTexturedModalRect(guiLeft+61, guiTop+51-burnHeight, 176, 14-burnHeight, 14, burnHeight);
		}
		if (storedPower>=capacity)
		{
			drawTexturedModalRect(guiLeft+94, guiTop+23, 176, 14, 22, 43);
		}
		else
		{
			int energyHeight = (int) Math.ceil(((float)storedPower)/((float)capacity)*40);
			drawTexturedModalRect(guiLeft+94, guiTop+65-energyHeight, 176, 56-energyHeight, 22, energyHeight);
		}
	}
	
}