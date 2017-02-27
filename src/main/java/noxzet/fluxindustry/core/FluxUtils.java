package noxzet.fluxindustry.core;

import net.minecraft.block.Block;
import net.minecraftforge.fluids.Fluid;
import net.minecraftforge.fluids.FluidRegistry;
import scala.Char;

public class FluxUtils {

	public static String getEnergyGrade(int grade)
	{
		if (grade > 0)
			return "kMGTPEZY".substring(grade, grade);
		return "";
	}
	
	public static int getFluidColor(String fluidName)
	{
		return getFluidColor(FluidRegistry.getFluid(fluidName));
	}
	
	public static int getFluidColor(Fluid fluid)
	{
		if (fluid != null)
		{
			if (fluid.getColor() != 0xFFFFFF && fluid.getColor() != -1)
				return fluid.getColor();
			Block fluidBlock = fluid.getBlock();
			if (fluidBlock != null)
				return fluidBlock.getMapColor(fluidBlock.getDefaultState()).colorValue;
		}
		return 0xFFFFFF;
	}
	
}
