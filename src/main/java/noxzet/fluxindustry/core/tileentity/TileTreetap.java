package noxzet.fluxindustry.core.tileentity;

import net.minecraft.block.Block;
import net.minecraft.block.BlockOldLog;
import net.minecraft.block.BlockPlanks;
import net.minecraft.block.state.IBlockState;
import net.minecraft.init.Blocks;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.util.EnumFacing;
import net.minecraft.util.ITickable;
import net.minecraftforge.common.util.Constants.NBT;
import net.minecraftforge.oredict.OreDictionary;
import noxzet.fluxindustry.core.block.BlockTreetap;
import noxzet.fluxindustry.core.item.FluxItems;

public class TileTreetap extends TileEntityFlux implements ITickable {

	private int level;
	private final int oneLevel;
	
	public TileTreetap()
	{
		level = 0;
		oneLevel = 12000;
	}

	@Override
	public void update()
	{
		EnumFacing facing = world.getBlockState(pos).getValue(BlockTreetap.FACING);
		IBlockState attachedToState = world.getBlockState(pos.offset(facing.getOpposite()));
		Block attachedToBlock = attachedToState.getBlock();
		if (attachedToBlock == Blocks.AIR)
		{
			world.setBlockState(pos, Blocks.AIR.getDefaultState());
			Block.spawnAsEntity(world, pos, new ItemStack(FluxItems.treetap));
			return;
		}
		Item attachedToItemBlock = Item.getItemFromBlock(attachedToBlock);
		if (isValidLog(attachedToItemBlock))
		{
			if (level<oneLevel*6)
			{
				if (attachedToBlock instanceof BlockOldLog && attachedToState.getValue(BlockOldLog.VARIANT)==BlockPlanks.EnumType.JUNGLE)
					level=Math.min(oneLevel*6,level+3);
				else
					level=Math.min(oneLevel*6,level+2);
				this.markDirty();
				world.markBlockRangeForRenderUpdate(pos, pos);
			}
		}
		else
		{
			world.setBlockState(pos, Blocks.AIR.getDefaultState());
			Block.spawnAsEntity(world, pos, new ItemStack(FluxItems.treetap, 1, 0));
			return;
		}
	}
	
	@Override
	public void readFromNBT(NBTTagCompound compound)
	{
		super.readFromNBT(compound);
		if (compound.hasKey("sapLevel", NBT.TAG_INT))
			level = compound.getInteger("sapLevel");
	}
	
	@Override
	public NBTTagCompound writeToNBT (NBTTagCompound compound)
	{
		super.writeToNBT(compound);
		compound.setInteger("sapLevel", level);
		return compound;
	}
	
	public boolean takeBucket(boolean simulate)
	{
		if (level > oneLevel*2)
		{
			if (!simulate)
			{
				level -= oneLevel*2;
				this.markDirty();
				world.markBlockRangeForRenderUpdate(pos, pos);
			}
			return true;
		}
		return false;
	}
	
	public int getLevel()
	{
		return Math.floorDiv(level, oneLevel);
	}
	
	public static boolean isValidLog(Item itemblock)
	{
		int[] attachedToIds = OreDictionary.getOreIDs(new ItemStack(itemblock));
		boolean isValidLog = false;
		for (int thisId : attachedToIds)
		{
			if (OreDictionary.getOreName(thisId) == "logWood")
			{
				isValidLog = true;
				break;
			}
		}
		return isValidLog;
	}
	
}
