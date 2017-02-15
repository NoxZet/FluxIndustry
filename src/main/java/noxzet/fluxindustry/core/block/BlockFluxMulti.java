package noxzet.fluxindustry.core.block;

import net.minecraft.block.material.Material;
import net.minecraft.block.properties.PropertyInteger;
import net.minecraft.block.state.BlockStateContainer;
import net.minecraft.block.state.IBlockState;
import net.minecraft.creativetab.CreativeTabs;
import net.minecraft.item.Item;
import net.minecraft.item.ItemBlock;
import net.minecraft.item.ItemStack;
import net.minecraft.util.NonNullList;
import noxzet.fluxindustry.core.FluxIndustry;

public abstract class BlockFluxMulti extends BlockFlux implements IBlockFluxMulti {

	public String[] variant = {"unknown"};
	public String group = "unknown_";
	
	public BlockFluxMulti(Material material, String unlocalizedName)
	{
		super(material, unlocalizedName);
	}
	
	public abstract PropertyInteger getVariantProperty();
	public abstract int getVariantMaxValue();
	
	@Override
	public void registerItemModel(ItemBlock itemblock)
	{
		int i, len = getVariantMaxValue();
		for(i = 0; i < len; i++)
			FluxIndustry.proxy.registerItemRenderer(itemblock, i, "variant=" + i);
	}

	@Override
	public void getSubBlocks(Item item, CreativeTabs tab, NonNullList<ItemStack> list)
	{
		int i, len = getVariantMaxValue();
		for(i = 0; i < len; i++)
			list.add(new ItemStack(item, 1, i));
	}
	
	@Override
	public int damageDropped(IBlockState state)
	{
		return state.getValue(getVariantProperty());
	}

	@Override
	public String getUnlocalizedNameFromMeta(int meta)
	{
		return "tile." + FluxIndustry.MODID + "." + group + variant[getMetaWithBounds(meta, getVariantMaxValue())];
	}
	
	@Override
	public BlockStateContainer createBlockState()
	{
		return new BlockStateContainer(this, getVariantProperty());
	}

	@Override
	public IBlockState getStateFromMeta(int meta)
	{
		return this.getDefaultState().withProperty(getVariantProperty(), meta);
	}
	
	@Override
	public int getMetaFromState(IBlockState state)
	{
		return state.getValue(getVariantProperty());
	}
	
	public void setVariants(String[] variant)
	{
		this.variant = variant;
	}
	
	public void setGroupName(String group)
	{
		this.group = group;
	}

}
