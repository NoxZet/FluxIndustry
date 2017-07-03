package noxzet.fluxindustry.core.container.gui;

import net.minecraft.util.ResourceLocation;
import net.minecraft.util.text.translation.I18n;
import net.minecraftforge.fluids.Fluid;
import net.minecraftforge.fluids.FluidRegistry;
import noxzet.fluxindustry.core.FluxIndustry;
import noxzet.fluxindustry.core.container.LiquidFillerContainer;
import noxzet.fluxindustry.core.tileentity.TileLiquidFiller;

public class LiquidFillerContainerGui extends FluxContainerGui {

	private LiquidFillerContainer container;
	private TileLiquidFiller tile;
	public static final ResourceLocation background = new ResourceLocation(FluxIndustry.MODID, "textures/gui/liquid_filler.png");
	
	public LiquidFillerContainerGui(TileLiquidFiller tile, LiquidFillerContainer container)
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
		int progress = tile.getLeftProgress();
		int progressMax = tile.getLeftTime();
		int progress2 = tile.getRightProgress();
		int progressMax2 = tile.getRightTime();
		mc.getTextureManager().bindTexture(background);
		drawTexturedModalRect(guiLeft, guiTop, 0, 0, xSize, ySize);
		if (storedPower>0)
		{
			int energyHeight = (int) Math.round(((float)storedPower)/((float)capacity)*14);
			drawTexturedModalRect(guiLeft+32, guiTop+50-energyHeight, 176, 14-energyHeight, 14, energyHeight);
		}
		if (progress>0 && progressMax>0)
		{
			int progressPercent = (int) Math.ceil(((float)progress)/((float)progressMax)*18);
			drawTexturedModalRect(guiLeft+53, guiTop+34, 176, 62, 16, progressPercent);
		}
		if (progress2>0 && progressMax2>0)
		{
			int progressPercent2 = (int) Math.ceil(((float)progress2)/((float)progressMax2)*18);
			drawTexturedModalRect(guiLeft+101, guiTop+34, 176, 62, 16, progressPercent2);
		}
		if (tile.fluidAmount>0)
		{
			int color = tile.fluidColor;
			int liquidAmount = tile.fluidAmount;
			int liquidMax = tile.fluidCapacity;
			int liquidHeight = (int) Math.floor((((float)liquidAmount)/((float)liquidMax))*48);
			drawRect(guiLeft+80, guiTop+67-liquidHeight, guiLeft+96, guiTop+67, color | 0xFF000000);
			fixGlState();
			drawTexturedModalRect(guiLeft+80, guiTop+67-liquidHeight, 176, 62-liquidHeight, 16, liquidHeight);
		}
		mc.getTextureManager().bindTexture(background);
		drawTexturedModalRect(guiLeft+80, guiTop+19, 192, 14, 16, 48);
	}
	
	@Override
	protected void drawGuiContainerForegroundLayer(int mouseX, int mouseY)
	{
		if (mouseX >= guiLeft+80 && mouseY >= guiTop+19 && mouseX < guiLeft+95 && mouseY < guiTop+66)
		{
			this.drawFluid(mouseX, mouseY, tile.fluidAmount, tile.fluidCapacity, tile.fluidLocalizedName);
		}
	}
	
}
