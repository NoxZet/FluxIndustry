package noxzet.fluxindustry.core.block;

import net.minecraft.block.SoundType;
import net.minecraft.block.material.Material;
import net.minecraft.block.properties.PropertyEnum;
import net.minecraft.block.properties.PropertyInteger;
import net.minecraft.block.state.BlockStateContainer;
import net.minecraft.block.state.IBlockState;
import net.minecraft.creativetab.CreativeTabs;
import net.minecraft.item.Item;
import net.minecraft.item.ItemBlock;
import net.minecraft.item.ItemStack;
import net.minecraft.util.IStringSerializable;
import net.minecraft.util.NonNullList;
import noxzet.fluxindustry.core.FluxIndustry;

public class BlockOreBasic extends BlockFluxMulti implements IBlockFluxMulti {

	public static int COPPER = 0;
	public static int TIN = 1;
	public static int ZINC = 2;
	public static int ALUMINUM = 3;
	public static int LEAD = 4;
	public static int URANIUM = 5;
	public static int[] level = {1, 1, 1, 2, 2, 2};
	public static boolean[] smeltable = {true, true, true, true, true, false};
	public static float[] experience = {0.6F, 0.6F, 0.7F, 0.8F, 1.0F, 0.0F};
	public static String[] variant = {"copper", "tin", "zinc", "aluminum", "lead", "uranium"};
	public static PropertyInteger VARIANT = PropertyInteger.create("variant", 0, variant.length-1);
	
	public BlockOreBasic(String unlocalizedName)
	{
		super(Material.ROCK, unlocalizedName);
		this.setSoundType(SoundType.STONE);
		setVariants(variant);
		setGroupName("ore_");
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
