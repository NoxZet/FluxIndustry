package noxzet.fluxindustry.core.block;

import net.minecraft.block.ITileEntityProvider;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.IBlockAccess;

public interface ITileEntityClassProvider extends ITileEntityProvider {

	public Class getTileEntityClass();
	public TileEntity getTileEntity (IBlockAccess world, BlockPos pos);
	
}
