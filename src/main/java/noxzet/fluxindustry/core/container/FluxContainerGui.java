package noxzet.fluxindustry.core.container;

import net.minecraft.client.gui.inventory.GuiContainer;
import net.minecraft.util.ResourceLocation;
import noxzet.fluxindustry.core.FluxIndustry;
import noxzet.fluxindustry.core.tileentity.TileElectricInventory;

public class FluxContainerGui extends GuiContainer {

	public static final int WIDTH = 176;
	public static final int HEIGHT = 166;
	
	private static final ResourceLocation background = new ResourceLocation(FluxIndustry.MODID, "textures/gui/container.png");
	
	public FluxContainerGui(TileElectricInventory tile, FluxContainer container)
	{
		super(container);
		xSize = WIDTH;
		ySize = HEIGHT;
	}
	
	@Override
	protected void drawGuiContainerBackgroundLayer(float partialTicks, int mouseX, int mouseY)
	{
		mc.getTextureManager().bindTexture(background);
		drawTexturedModalRect(guiLeft, guiTop, 0, 0, xSize, ySize);
	}
	
}
