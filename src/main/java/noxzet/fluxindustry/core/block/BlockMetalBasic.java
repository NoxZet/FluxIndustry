package noxzet.fluxindustry.core.block;

import net.minecraft.block.SoundType;
import net.minecraft.block.material.Material;
import net.minecraft.block.properties.PropertyInteger;
import net.minecraft.block.state.IBlockState;

public class BlockMetalBasic extends BlockFluxMulti implements IBlockFluxMulti {

	public static int COPPER = 0;
	public static int TIN = 1;
	public static int ZINC = 2;
	public static int ALUMINUM = 3;
	public static int LEAD = 4;
	public static int BRONZE = 5;
	public static int BRASS = 6;
	public static int[] level = {1, 1, 1, 2, 2, 1, 1};
	public static String[] variant = {"copper", "tin", "zinc", "aluminum", "lead", "bronze", "brass"};
	public static PropertyInteger VARIANT = PropertyInteger.create("variant", 0, variant.length-1);
	
	public BlockMetalBasic(String unlocalizedName)
	{
		super(Material.IRON, unlocalizedName);
		this.setSoundType(SoundType.METAL);
		setVariants(variant);
		setGroupName("block_");
	}

	@Override
	public PropertyInteger getVariantProperty()
	{
		return VARIANT;
	}
	
	@Override
	public int getVariantMaxValue()
	{
		return variant.length;
	}
	
	@Override
	public String getHarvestTool(IBlockState state)
	{
		return "pickaxe";
	}
	
	@Override
	public int getHarvestLevel(IBlockState state)
	{
		return level[getMetaWithBounds(state.getValue(VARIANT), variant.length)];
	}
	
}
