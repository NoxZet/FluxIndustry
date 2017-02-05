package noxzet.fluxindustry.core.block;

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

public class BlockOreBasic extends BlockOre implements IBlockFluxMulti {

	public final static PropertyEnum<OreVariant> VARIANT = PropertyEnum.<OreVariant>create("variant", OreVariant.class);
	
	public BlockOreBasic(String unlocalizedName)
	{
		super(unlocalizedName);
	}
	
	@Override
	public void registerItemModel(ItemBlock itemBlock) {
		int i, len = OreVariant.values().length;
		for(i=0; i<len; i++)
		{
			FluxIndustry.proxy.registerItemRenderer(itemBlock, i, "variant="+OreVariant.fromMeta(i).getName());
		}
	}
	
	@Override
	public void getSubBlocks(Item item, CreativeTabs tab, NonNullList<ItemStack> list)
	{
		int i, len = OreVariant.values().length;
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
		return "tile." + FluxIndustry.MODID + "." + OreVariant.fromMeta(meta).getUnlocalizedName();
	}
	
	@Override
	public BlockStateContainer createBlockState()
	{
		return new BlockStateContainer(this, VARIANT);
	}
	
	@Override
	public IBlockState getStateFromMeta(int meta)
	{
		return this.getDefaultState().withProperty(VARIANT, OreVariant.fromMeta(meta));
	}
	
	@Override
	public int getMetaFromState(IBlockState state)
	{
		return state.getValue(VARIANT).getMeta();
	}
	
	public static enum OreVariant implements IStringSerializable
	{
		//Meta, name, harvest level
		COPPER(0, "copper", 1, true, 0.6F),
		TIN(1, "tin", 1, true, 0.6F),
		ZINC(2, "zinc", 1, true, 0.7F),
		ALUMINUM(3, "aluminum", 2, true, 0.8F),
		LEAD(4, "lead", 2, true, 1.0F),
		URANIUM(5, "uranium", 2, false, 0.0F);
		
		private static final OreVariant[] META = new OreVariant[values().length];
		private final int meta;
		private final String name;
		private final String unlocalizedName;
		private final int level;
		private final boolean smeltable;
		private final float experience;

		private OreVariant(int meta, String name, int level, boolean smeltable, float exp) {
			this(meta, name, "ore_"+name, level, smeltable, exp);
		}
		private OreVariant(int meta, String name, String unlocalizedName, int level, boolean smeltable, float exp) {
			this.meta = meta;
			this.name = name;
			this.unlocalizedName = unlocalizedName;
			this.level = level;
			this.smeltable = smeltable;
			this.experience = exp;
		}
		public static OreVariant fromMeta(int meta)
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
		public boolean isSmeltable()
		{
			return this.smeltable;
		}
		public float getExperience()
		{
			return this.experience;
		}
		static
		{
			for (OreVariant orevariant : values())
			{
				META[orevariant.getMeta()] = orevariant;
			}
		}
	}
	
}
