package noxzet.fluxindustry.core.block.electric;

import net.minecraft.block.SoundType;
import net.minecraft.block.material.Material;
import net.minecraft.block.state.IBlockState;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.EnumFacing;
import net.minecraft.util.EnumHand;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.IBlockAccess;
import net.minecraft.world.World;
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
	public TileEntity createTileEntity(World worldIn, IBlockState state)
	{
		return createNewTileEntity(worldIn, this.getMetaFromState(state));
	}
	
	@Override
	public TileEntity createNewTileEntity(World worldIn, int meta)
	{
		return new TileElectric();
	}
	
	@Override
	public boolean onBlockActivated(World worldIn, BlockPos pos, IBlockState state, EntityPlayer player, EnumHand hand, EnumFacing facing, float side, float hitX, float hitY)
	{
		TileEntity entity = getTileEntity(worldIn, pos);
		if (entity instanceof TileElectric)
			return ((TileElectric)entity).onActivated(worldIn, player, facing);
		else
			return false;
	}
	
	@Override
	public void onBlockClicked(World worldIn, BlockPos pos, EntityPlayer player)
	{
		TileEntity entity = getTileEntity(worldIn, pos);
		if (entity instanceof TileElectric)
			((TileElectric)entity).onClicked(worldIn, player);
	}
	
}
