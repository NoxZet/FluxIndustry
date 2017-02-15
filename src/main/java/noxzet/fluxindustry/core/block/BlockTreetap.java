package noxzet.fluxindustry.core.block;

import java.util.ArrayList;
import java.util.List;

import net.minecraft.block.SoundType;
import net.minecraft.block.material.Material;
import net.minecraft.block.properties.PropertyInteger;
import net.minecraft.block.state.BlockStateContainer;
import net.minecraft.block.state.IBlockState;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.init.Items;
import net.minecraft.item.ItemBlock;
import net.minecraft.item.ItemStack;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.EnumFacing;
import net.minecraft.util.EnumHand;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.RayTraceResult;
import net.minecraft.world.IBlockAccess;
import net.minecraft.world.World;
import noxzet.fluxindustry.core.item.FluxItems;
import noxzet.fluxindustry.core.tileentity.TileTreetap;

public class BlockTreetap extends BlockDirection implements ITileEntityClassProvider {

	public final static PropertyInteger LEVEL = PropertyInteger.create("level", 0, 6);
	
	public BlockTreetap(String unlocalizedName)
	{
		super(Material.WOOD, unlocalizedName);
		this.setSoundType(SoundType.WOOD);
		this.setResistance(15);
		this.setHardness(3);
		this.setHorizontalOnly(true);
		this.setIsCube(false);
	}
	
    @Override
    protected BlockStateContainer createBlockState()
    {
        return new BlockStateContainer(this, FACING, LEVEL);
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
	
	public IBlockState getActualState(IBlockState state, IBlockAccess world, BlockPos pos)
	{
		return state.withProperty(LEVEL, ((TileTreetap)world.getTileEntity(pos)).getLevel());
	}

	@Override
	public void registerItemModel(ItemBlock itemBlock)
	{
	}
	
	public Class getTileEntityClass()
	{
		return TileTreetap.class;
	}
	
	public TileEntity getTileEntity (IBlockAccess world, BlockPos pos)
	{
		return (TileEntity)world.getTileEntity(pos);
	}

	@Override
	public boolean hasTileEntity(IBlockState state)
	{
		return true;
	}
	
	@Override
	public TileEntity createTileEntity(World worldIn, IBlockState state)
	{
		return createNewTileEntity(worldIn, this.getMetaFromState(state));
	}
	
	@Override
	public TileEntity createNewTileEntity(World worldIn, int meta)
	{
		return new TileTreetap();
	}
	
	@Override
	public boolean onBlockActivated(World world, BlockPos pos, IBlockState state, EntityPlayer player, EnumHand hand, EnumFacing facing, float side, float hitX, float hitY)
	{
		TileTreetap tapEntity = (TileTreetap)world.getTileEntity(pos);
		if (tapEntity.takeBucket(true))
		{
			ItemStack heldstack = player.getHeldItem(hand);
			if (heldstack.getItem() == Items.BOWL)
			{
				if (!player.isCreative() && heldstack.getCount() == 1)
				{
					player.setHeldItem(hand, new ItemStack(FluxItems.materialBasic, 1, 0));
					tapEntity.takeBucket(false);
					return true;
				}
				else if (player.inventory.addItemStackToInventory(new ItemStack(FluxItems.materialBasic, 1, 0)))
				{
					tapEntity.takeBucket(false);
					if (!player.isCreative())
						heldstack.shrink(1);
					return true;
				}
			}
		}
		return false;
	}
	
	@Override
	public List<ItemStack> getDrops(IBlockAccess world, BlockPos pos, IBlockState state, int fortune)
	{
		List<ItemStack> list = new ArrayList<ItemStack>();
		list.add(new ItemStack(FluxItems.treetap, 1, 0));
		return list;
	}
	
	@Override
	public ItemStack getPickBlock(IBlockState state, RayTraceResult target, World world, BlockPos pos, EntityPlayer player)
	{
		return new ItemStack(FluxItems.treetap, 1, 0);
	}
	
}
