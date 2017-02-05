package noxzet.fluxindustry.core.block;

import net.minecraft.block.SoundType;
import net.minecraft.block.material.Material;
import net.minecraft.block.properties.PropertyEnum;
import net.minecraft.block.state.BlockStateContainer;
import net.minecraft.block.state.IBlockState;
import net.minecraft.creativetab.CreativeTabs;
import net.minecraft.item.Item;
import net.minecraft.item.ItemBlock;
import net.minecraft.item.ItemStack;
import net.minecraft.util.IStringSerializable;
import net.minecraft.util.NonNullList;
import noxzet.fluxindustry.core.FluxIndustry;

public class BlockMetalBasic extends BlockFlux implements IBlockFluxMulti {

	public final static PropertyEnum<MetalVariant> VARIANT = PropertyEnum.<MetalVariant>create("variant", MetalVariant.class);
	//public final static PropertyBoolean
	
	public BlockMetalBasic(String unlocalizedName)
	{
		super(Material.IRON, unlocalizedName);
		this.setSoundType(SoundType.METAL);
	}
	
	@Override
	public void registerItemModel(ItemBlock itemBlock) {
		int i, len = MetalVariant.values().length;
		for(i=0; i<len; i++)
		{
			FluxIndustry.proxy.registerItemRenderer(itemBlock, i, "variant="+MetalVariant.fromMeta(i).getName());
		}
	}
	
	@Override
	public void getSubBlocks(Item item, CreativeTabs tab, NonNullList<ItemStack> list)
	{
		int i, len = MetalVariant.values().length;
		for(i=0; i<len; i++)
		{
			list.add(new ItemStack(item, 1, i));
		}
	}
	
	@Override
	public int damageDropped(IBlockState state)
	{
		return state.getValue(VARIANT).getMeta();
	}
	
	@Override
	public String getHarvestTool(IBlockState state)
	{
		return "pickaxe";
	}
	
	@Override
	public int getHarvestLevel(IBlockState state)
	{
		return state.getValue(VARIANT).getLevel();
	}
	
	public String getUnlocalizedNameFromMeta(int meta)
	{
		return "tile." + FluxIndustry.MODID + "." + MetalVariant.fromMeta(meta).getUnlocalizedName();
	}
	
	@Override
	public BlockStateContainer createBlockState()
	{
		return new BlockStateContainer(this, VARIANT);
	}
	
	@Override
	public IBlockState getStateFromMeta(int meta)
	{
		return this.getDefaultState().withProperty(VARIANT, MetalVariant.fromMeta(meta));
	}
	
	@Override
	public int getMetaFromState(IBlockState state)
	{
		return state.getValue(VARIANT).getMeta();
	}
	
	public static enum MetalVariant implements IStringSerializable
	{
		//Meta, name, harvest level
		COPPER(0, "copper", 1),
		TIN(1, "tin", 1),
		ZINC(2, "zinc", 1),
		ALUMINUM(3, "aluminum", 2),
		LEAD(4, "lead", 2),
		BRONZE(5, "bronze", 1),
		BRASS(6, "brass", 1);
		
		private static final MetalVariant[] META = new MetalVariant[values().length];
		private final int meta;
		private final String name;
		private final String unlocalizedName;
		private final int level;

		private MetalVariant(int meta, String name, int level) {
			this(meta, name, "block_"+name, level);
		}
		private MetalVariant(int meta, String name, String unlocalizedName, int level) {
			this.meta = meta;
			this.name = name;
			this.unlocalizedName = unlocalizedName;
			this.level = level;
		}
		public static MetalVariant fromMeta(int meta)
		{
			if (meta >= 0 && meta < META.length)
				return META[meta];
			else
				return META[0];
		}
		public int getMeta()
		{
			return this.meta;
		}
		public String getName()
		{
			return this.name;
		}
		public String getUnlocalizedName()
		{
			return this.unlocalizedName;
		}
		public int getLevel()
		{
			return this.level;
		}
		static
		{
			for (MetalVariant metalvariant : values())
			{
				META[metalvariant.getMeta()] = metalvariant;
			}
		}
	}
	
}
