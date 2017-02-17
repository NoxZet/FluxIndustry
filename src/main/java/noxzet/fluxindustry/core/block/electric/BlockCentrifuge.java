package noxzet.fluxindustry.core.block.electric;

import net.minecraft.block.material.Material;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.world.World;
import noxzet.fluxindustry.core.tileentity.TileCentrifuge;

public class BlockCentrifuge extends BlockElectricInventory {

	public BlockCentrifuge(String unlocalizedName)
	{
		super(Material.IRON, unlocalizedName);
	}
	
	@Override
	public Class getTileEntityClass()
	{
		return TileCentrifuge.class;
	}
	
	@Override
	public TileEntity createNewTileEntity(World worldIn, int meta)
	{
		return new TileCentrifuge();
	}
	
}
