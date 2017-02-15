package noxzet.fluxindustry.core.item;

import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.ItemStack;
import net.minecraft.util.EnumActionResult;
import net.minecraft.util.EnumFacing;
import net.minecraft.util.EnumHand;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;
import noxzet.fluxindustry.core.block.BlockTreetap;
import noxzet.fluxindustry.core.block.FluxBlocks;

public class ItemTreetap extends ItemFlux {

	public ItemTreetap(String unlocalizedName)
	{
		super(unlocalizedName);
	}
	
    public EnumActionResult onItemUse(EntityPlayer player, World world, BlockPos pos, EnumHand hand, EnumFacing facing, float hitX, float hitY, float hitZ)
    {
    	ItemStack itemstack = player.getHeldItem(hand);
    	pos = pos.offset(facing);
    	if (player.canPlayerEdit(pos, facing, itemstack) && FluxBlocks.treetapBlock.canPlaceBlockAt(world, pos))
	    {
    		world.setBlockState(pos, FluxBlocks.treetapBlock.getDefaultState()
    				.withProperty(BlockTreetap.FACING, facing), 11);
	    	itemstack.shrink(1);
	    	return EnumActionResult.SUCCESS;
    	}
    	return EnumActionResult.FAIL;
    }
	
}
