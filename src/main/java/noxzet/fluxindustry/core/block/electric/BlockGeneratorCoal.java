package noxzet.fluxindustry.core.block.electric;

import net.minecraft.block.material.Material;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.world.World;
import noxzet.fluxindustry.core.tileentity.TileGeneratorCoal;

public class BlockGeneratorCoal extends BlockElectricInventory {

	public BlockGeneratorCoal(String unlocalizedName)
	{
		super(Material.IRON, unlocalizedName);
	}
	
	@Override
	public Class getTileEntityClass()
	{
		return TileGeneratorCoal.class;
	}
	
	@Override
	public TileEntity createNewTileEntity(World worldIn, int meta)
	{
		return new TileGeneratorCoal();
	}
	
}
