package noxzet.fluxindustry.core.block.electric;

import net.minecraft.block.material.Material;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.world.World;
import noxzet.fluxindustry.core.tileentity.TileLiquidFiller;

public class BlockLiquidFiller extends BlockElectricInventory {

	public BlockLiquidFiller(String unlocalizedName)
	{
		super(Material.IRON, unlocalizedName);
	}
	
	@Override
	public Class getTileEntityClass()
	{
		return TileLiquidFiller.class;
	}
	
	@Override
	public TileEntity createNewTileEntity(World worldIn, int meta)
	{
		return new TileLiquidFiller();
	}
	
}
