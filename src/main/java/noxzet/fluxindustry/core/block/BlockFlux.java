package noxzet.fluxindustry.core.block;

import net.minecraft.block.Block;
import net.minecraft.block.material.Material;
import net.minecraft.block.state.IBlockState;
import net.minecraft.creativetab.CreativeTabs;
import net.minecraft.item.ItemBlock;
import net.minecraft.util.EnumFacing;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.IBlockAccess;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;
import noxzet.fluxindustry.core.FluxIndustry;

public class BlockFlux extends Block {

	protected String unlocalizedName;
	private boolean isCube;

	public BlockFlux(Material material, String unlocalizedName)
	{
		super(material);
		this.setUnlocalizedName(unlocalizedName);
		this.setRegistryName(unlocalizedName);
		this.isCube = true;
	}

	public void registerItemModel(ItemBlock itemBlock) {
		FluxIndustry.proxy.registerItemRenderer(itemBlock, 0, "normal");
	}
	
	@Override
	public Block setUnlocalizedName(String name)
	{
		this.unlocalizedName = name;
		return this;
	}
	
	@Override
	public BlockFlux setCreativeTab(CreativeTabs tab)
	{
		super.setCreativeTab(tab);
		return this;
	}
	
	public String getUnlocalizedId()
	{
		return this.unlocalizedName;
	}
	
	@Override
	public String getUnlocalizedName()
	{
		return "tile." + FluxIndustry.MODID + "." + this.unlocalizedName;
	}
	
	public void setIsCube(boolean isCube)
	{
		this.isCube = isCube;
	}
	
	@Override
    public boolean isOpaqueCube(IBlockState state)
    {
        return isCube;
    }

	@Override
    public boolean isFullCube(IBlockState state)
    {
        return isCube;
    }
	
	@Override
    @SideOnly(Side.CLIENT)
    public boolean shouldSideBeRendered(IBlockState blockState, IBlockAccess blockAccess, BlockPos pos, EnumFacing side)
    {
		if (!isCube)
			return true;
        return super.shouldSideBeRendered(blockState, blockAccess, pos, side);
    }

}
