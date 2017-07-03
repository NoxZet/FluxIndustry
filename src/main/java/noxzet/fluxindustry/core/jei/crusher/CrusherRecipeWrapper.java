package noxzet.fluxindustry.core.jei.crusher;

import javax.annotation.Nonnull;

import mezz.jei.api.IGuiHelper;
import mezz.jei.api.IJeiHelpers;
import mezz.jei.api.gui.IDrawableAnimated;
import mezz.jei.api.gui.IDrawableStatic;
import net.minecraft.client.Minecraft;
import noxzet.fluxindustry.core.container.gui.ElectricCrusherContainerGui;
import noxzet.fluxindustry.core.jei.FluxRecipeWrapper;

public class CrusherRecipeWrapper extends FluxRecipeWrapper<CrusherRecipe> {

	private final IDrawableAnimated progress, energy;
	
	public CrusherRecipeWrapper(@Nonnull IJeiHelpers jeiHelpers, @Nonnull CrusherRecipe baseRecipe)
	{
		super(baseRecipe);
		IGuiHelper guiHelper = jeiHelpers.getGuiHelper();
		IDrawableStatic progressStatic = guiHelper.createDrawable(ElectricCrusherContainerGui.background, 176, 14, 38, 16);
		this.progress = guiHelper.createAnimatedDrawable(progressStatic, 80, IDrawableAnimated.StartDirection.LEFT, false);
		IDrawableStatic energyStatic = guiHelper.createDrawable(ElectricCrusherContainerGui.background, 176, 0, 14, 14);
		this.energy = guiHelper.createAnimatedDrawable(energyStatic, 95, IDrawableAnimated.StartDirection.TOP, true);
	}
	
	public void drawInfo(@Nonnull Minecraft minecraft, int recipeWidth, int recipeHeight, int mouseX, int mouseY)
	{
		super.drawInfo(minecraft, recipeWidth, recipeHeight, mouseX, mouseY);
		progress.draw(minecraft, 22, 18);
		energy.draw(minecraft, 2, 20);
	}
	
}
