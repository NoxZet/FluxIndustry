package noxzet.fluxindustry.core.container;

import java.util.ArrayList;

import net.minecraft.client.gui.inventory.GuiContainer;
import net.minecraft.client.renderer.GlStateManager;
import net.minecraft.util.ResourceLocation;
import net.minecraft.util.text.translation.I18n;
import noxzet.fluxindustry.core.FluxIndustry;
import noxzet.fluxindustry.core.FluxUtils;
import noxzet.fluxindustry.core.tileentity.TileElectricInventory;

public class FluxContainerGui extends GuiContainer {

	public static final int WIDTH = 176;
	public static final int HEIGHT = 166;
	private TileElectricInventory tile;
	
	private static final ResourceLocation background = new ResourceLocation(FluxIndustry.MODID, "textures/gui/container.png");
	
	public FluxContainerGui(TileElectricInventory tile, FluxContainer container)
	{
		super(container);
		this.tile = tile;
		xSize = WIDTH;
		ySize = HEIGHT;
	}
	
	public static void fixGlState()
	{
		GlStateManager.color(1.0F, 1.0F, 1.0F, 1.0F);
		GlStateManager.disableLighting();
		GlStateManager.enableAlpha();
		GlStateManager.enableBlend();
	}
	
	public void drawEnergy(int x, int y, int energy, int capacity)
	{
		drawHoveringText(FluxUtils.getEnergyString(energy, capacity, FluxUtils.getEnergyNumGrade(capacity)), x-guiLeft-4, y-guiTop);
	}
	
	public void drawFluid(int x, int y, int amount, int capacity, String name)
	{
		ArrayList<String> lines = new ArrayList<>();
		lines.add(I18n.translateToLocal(name));
		lines.add(amount + "/" + capacity + " mB");
		drawHoveringText(lines, x-guiLeft-4, y-guiTop);
	}
	
	@Override
	protected void drawGuiContainerBackgroundLayer(float partialTicks, int mouseX, int mouseY)
	{
		mc.getTextureManager().bindTexture(background);
		drawTexturedModalRect(guiLeft, guiTop, 0, 0, xSize, ySize);
	}
	
	@Override
	public void initGui()
	{
		super.initGui();
		tile.isOpen = true;
	}
	
	@Override
	public void onGuiClosed()
	{
		super.onGuiClosed();
		tile.isOpen = false;
	}
	
}
