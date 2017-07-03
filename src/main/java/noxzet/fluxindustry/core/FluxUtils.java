package noxzet.fluxindustry.core;

import net.minecraft.block.Block;
import net.minecraftforge.fluids.Fluid;
import net.minecraftforge.fluids.FluidRegistry;

public class FluxUtils {

	public static short getEnergyNumGrade(int number)
	{
		return (short) Math.max(0, Math.floor(((Math.floor(Math.log10(number)))-1)/3));
	}
	
	public static String getEnergyGrade(short grade)
	{
		if (grade > 0)
			return "kMGTPEZY".substring(grade-1, grade);
		return "";
	}
	
	public static String getEnergyString(int energy, int capacity, short grade)
	{
		String breaker;
        if (grade < 1)
            return energy + "/" + capacity + FluxIndustry.unit;
        else
        {
            int floatPlacesAmount = 1;
            double divider = Math.pow(1000, grade);
            double shownAmount = energy / divider;
            double shownCapacity = capacity / divider;
            if (shownCapacity < 10)
                floatPlacesAmount = 2;
            return String.format("%." + floatPlacesAmount + "f/%." + floatPlacesAmount + "f " + FluxUtils.getEnergyGrade(grade) + FluxIndustry.unit, shownAmount, shownCapacity);
        }
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
				return fluid.getColor() | 0xFF000000;
			Block fluidBlock = fluid.getBlock();
			if (fluidBlock != null)
				return fluidBlock.getMapColor(fluidBlock.getDefaultState()).colorValue | 0xFF000000;
		}
		return 0xFFFFFFFF;
	}
	
	public static String byteArrayToString(byte[] bytes)
	{
		char[] chars = new char[bytes.length >> 1];
		for (int i = 0; i < chars.length; i++)
		{
			int pos = i << 1;
			chars[i] = (char)(((bytes[pos]&0x00FF)<<8) + ((bytes[pos+1]&0x00FF)));
		}
		return new String(chars);
	}
	
	public static byte[] stringToByteArray(String string)
	{
		char[] chars = string.toCharArray();
		byte[] bytes = new byte[chars.length << 1];
		for (int i = 0; i < chars.length; i++)
		{
			int pos = i << 1;
			bytes[pos] = (byte) ((chars[i]&0xFF00)>>8);
			bytes[pos+1] = (byte) ((chars[i]&0x00FF));
		}
		return bytes;
	}
	
	public static int byteArrayToInt(byte[] bytes)
	{
		return (int) ((bytes[0]<<24)&0xFF000000) + ((bytes[1]<<16)&0x00FF0000) + ((bytes[2]<<8)&0x0000FF00) + ((bytes[3])&0x000000FF);
	}
	
	public static byte[] intToByteArray(int integer)
	{
		return new byte[]{(byte)((integer&0xFF000000)>>24), (byte)((integer&0x00FF0000)>>16), (byte)((integer&0x0000FF00)>>8), (byte)((integer&0x000000FF))};
	}
	
}
