package noxzet.fluxindustry.core.block.electric;

import net.minecraft.block.SoundType;
import net.minecraft.block.material.Material;
import net.minecraft.block.state.IBlockState;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.ItemStack;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.EnumFacing;
import net.minecraft.util.EnumHand;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.IBlockAccess;
import net.minecraft.world.World;
import noxzet.fluxindustry.api.IFluxIndustryWrench;
import noxzet.fluxindustry.core.block.BlockDirection;
import noxzet.fluxindustry.core.block.ITileEntityClassProvider;
import noxzet.fluxindustry.core.tileentity.TileElectric;

public class BlockElectric extends BlockDirection implements ITileEntityClassProvider {
	
	public BlockElectric(Material material, String unlocalizedName)
	{
		super(material, unlocalizedName);
		this.setSoundType(SoundType.METAL);
		this.setHorizontalOnly(true);
		this.setHarvestLevel("pickaxe", 1);
	}
	
	public Class getTileEntityClass()
	{
		return TileElectric.class;
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
	public TileEntity createTileEntity(World world, IBlockState state)
	{
		return createNewTileEntity(world, this.getMetaFromState(state));
	}
	
	@Override
	public TileEntity createNewTileEntity(World world, int meta)
	{
		return new TileElectric();
	}
	
	@Override
	public boolean onBlockActivated(World world, BlockPos pos, IBlockState state, EntityPlayer player, EnumHand hand, EnumFacing facing, float side, float hitX, float hitY)
	{
		if (!world.isRemote)
		{
			int uuid = world.rand.nextInt(1000);
			if (player.getHeldItem(hand).getItem() instanceof IFluxIndustryWrench)
			{
				ItemStack stack = player.getHeldItem(hand);
				if (((IFluxIndustryWrench)stack.getItem()).onFluxIndustryWrenchUse(stack))
				{
					((TileElectric)world.getTileEntity(pos)).dontRefresh();
					world.setBlockState(pos, state.withProperty(FACING, getFacing(pos, player)), 2);
					return true;
				}
			}
			TileEntity entity = getTileEntity(world, pos);
			if (entity instanceof TileElectric)
				return ((TileElectric)entity).onActivated(world, player, facing);
			return false;
		}
		return true;
	}
	
	@Override
	public void onBlockClicked(World world, BlockPos pos, EntityPlayer player)
	{
		if (!world.isRemote)
		{
			TileEntity entity = getTileEntity(world, pos);
			if (entity instanceof TileElectric)
				((TileElectric)entity).onClicked(world, player);
		}
	}
	
}
