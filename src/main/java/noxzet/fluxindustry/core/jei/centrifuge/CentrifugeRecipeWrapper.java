package noxzet.fluxindustry.core.jei.centrifuge;

import javax.annotation.Nonnull;

import mezz.jei.api.IGuiHelper;
import mezz.jei.api.IJeiHelpers;
import mezz.jei.api.gui.IDrawableAnimated;
import mezz.jei.api.gui.IDrawableStatic;
import net.minecraft.client.Minecraft;
import noxzet.fluxindustry.core.container.CentrifugeContainerGui;
import noxzet.fluxindustry.core.jei.FluxRecipeWrapper;

public class CentrifugeRecipeWrapper extends FluxRecipeWrapper<CentrifugeRecipe> {

	private final IDrawableAnimated progress, energy;
	private final String[] chance = new String[3];
	
	public CentrifugeRecipeWrapper(@Nonnull IJeiHelpers jeiHelpers, @Nonnull CentrifugeRecipe baseRecipe)
	{
		super(baseRecipe);
		IGuiHelper guiHelper = jeiHelpers.getGuiHelper();
		IDrawableStatic progressStatic = guiHelper.createDrawable(CentrifugeContainerGui.background, 176, 14, 16, 18);
		this.progress = guiHelper.createAnimatedDrawable(progressStatic, 80, IDrawableAnimated.StartDirection.TOP, false);
		IDrawableStatic energyStatic = guiHelper.createDrawable(CentrifugeContainerGui.background, 176, 0, 14, 14);
		this.energy = guiHelper.createAnimatedDrawable(energyStatic, 95, IDrawableAnimated.StartDirection.TOP, true);
		for (int i = 0; i <= 2; i++)
			this.chance[i] = baseRecipe.getOutputChance(i)+"%";
	}
	
	public void drawInfo(@Nonnull Minecraft minecraft, int recipeWidth, int recipeHeight, int mouseX, int mouseY)
	{
		super.drawInfo(minecraft, recipeWidth, recipeHeight, mouseX, mouseY);
		progress.draw(minecraft, 31, 18);
		energy.draw(minecraft, 2, 20);
		for (int i = 0; i <= 2; i++)
			minecraft.fontRenderer.drawString(chance[i], 84, 5+18*i, 0x333333);
	}
	
}
