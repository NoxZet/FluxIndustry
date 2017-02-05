package noxzet.fluxindustry.core.block.electric;

import net.minecraft.block.material.Material;
import net.minecraft.block.state.IBlockState;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;
import net.minecraftforge.items.ItemStackHandler;
import noxzet.fluxindustry.core.tileentity.TileElectricInventory;

public class BlockElectricInventory extends BlockElectric {

	public BlockElectricInventory(Material material, String unlocalizedName)
	{
		super(material, unlocalizedName);
	}
	
	@Override
	public Class getTileEntityClass()
	{
		return TileElectricInventory.class;
	}
	
	@Override
	public TileEntity createNewTileEntity(World worldIn, int meta)
	{
		return new TileElectricInventory();
	}
	
	public void breakBlock(World world, BlockPos pos, IBlockState state)
	{
		TileEntity tile = world.getTileEntity(pos);
		if (tile instanceof TileElectricInventory)
		{
			TileElectricInventory electric = (TileElectricInventory) tile;
			ItemStackHandler inventory = electric.getStackHandler();
			int slots = inventory.getSlots();
			for(int i = 0; i < slots; i++)
			{
				spawnAsEntity(world, pos, inventory.getStackInSlot(i).copy());
			}
		}
		super.breakBlock(world, pos, state);
	}

}
