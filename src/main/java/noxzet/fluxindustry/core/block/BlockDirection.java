package noxzet.fluxindustry.core.block;

import net.minecraft.block.material.Material;
import net.minecraft.block.properties.PropertyDirection;
import net.minecraft.block.state.BlockStateContainer;
import net.minecraft.block.state.IBlockState;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.item.ItemBlock;
import net.minecraft.util.EnumFacing;
import net.minecraft.util.EnumHand;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;
import noxzet.fluxindustry.core.FluxIndustry;

public class BlockDirection extends BlockFlux {

	public final static PropertyDirection FACING = PropertyDirection.create("facing");
	private boolean horizontalOnly;
	
	public BlockDirection(Material material, String unlocalizedName)
	{
		super(material, unlocalizedName);
		this.setResistance(30);
		this.setHardness(5);
		this.setDefaultState(this.blockState.getBaseState().withProperty(FACING, EnumFacing.NORTH));
		this.horizontalOnly = false;
	}

	public void registerItemModel(ItemBlock itemBlock)
	{
		FluxIndustry.proxy.registerItemRenderer(itemBlock, 0, "facing=north");
	}
	
	@Override
	public IBlockState getStateForPlacement(World world, BlockPos pos, EnumFacing facing, float hitX, float hitY, float hitZ, int meta, EntityLivingBase placer, EnumHand hand)
	{
		return this.getDefaultState().withProperty(FACING, getFacing(pos, placer));
	}
	
	public EnumFacing getFacing(BlockPos pos, EntityLivingBase player)
	{
		if (this.getHorizontalOnly())
			return player.getHorizontalFacing().getOpposite();
		else
			return EnumFacing.getDirectionFromEntityLiving(pos, player);
	}
	
    @Override
    protected BlockStateContainer createBlockState()
    {
        return new BlockStateContainer(this, FACING);
    }
	
	@Override
	public IBlockState getStateFromMeta(int meta)
	{
		EnumFacing enumfacing = EnumFacing.getFront(meta);
		return this.getDefaultState().withProperty(FACING, enumfacing);
	}
	
	@Override
	public int getMetaFromState(IBlockState state)
	{
		return ((EnumFacing)state.getValue(FACING)).getIndex();
	}
	
	public IBlockState getActualState(IBlockState state)
	{
		return state;
	}
	
	public void setHorizontalOnly(boolean horizontalOnly)
	{
		this.horizontalOnly = horizontalOnly;
	}
	
	public boolean getHorizontalOnly()
	{
		return this.horizontalOnly;
	}
	
}
