package noxzet.fluxindustry.core.block.electric;

import net.minecraft.block.material.Material;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.world.World;
import noxzet.fluxindustry.core.tileentity.TileEnergyStorage;

public class BlockEnergyStorage extends BlockElectricInventory {

	public BlockEnergyStorage(String unlocalizedName)
	{
		super(Material.IRON, unlocalizedName);
		this.setHorizontalOnly(false);
	}
	
	@Override
	public Class getTileEntityClass()
	{
		return TileEnergyStorage.class;
	}
	
	@Override
	public TileEntity createNewTileEntity(World worldIn, int meta)
	{
		return new TileEnergyStorage();
	}
	
}
