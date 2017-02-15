package noxzet.fluxindustry.core.block;

import net.minecraft.block.SoundType;
import net.minecraft.block.material.Material;
import net.minecraft.block.properties.PropertyInteger;

public class BlockMachine extends BlockFluxMulti implements IBlockFluxMulti {

	public static int BASIC = 0;
	public static int PURPLE = 1;
	public static String[] variant = {"basic", "purple"};
	public static PropertyInteger VARIANT = PropertyInteger.create("variant", 0, variant.length-1);
	
	public BlockMachine(String unlocalizedName)
	{
		super(Material.IRON, unlocalizedName);
		this.setSoundType(SoundType.METAL);
		setVariants(variant);
		setGroupName("block_machine_");
		this.setHarvestLevel("pickaxe", 1);
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
	
}
