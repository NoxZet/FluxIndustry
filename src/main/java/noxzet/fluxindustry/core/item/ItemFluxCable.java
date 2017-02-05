package noxzet.fluxindustry.core.item;

import java.util.List;

import net.minecraft.creativetab.CreativeTabs;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.util.EnumActionResult;
import net.minecraft.util.EnumFacing;
import net.minecraft.util.EnumHand;
import net.minecraft.util.NonNullList;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;
import net.minecraftforge.common.util.Constants.NBT;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;
import noxzet.fluxindustry.core.FluxIndustry;
import noxzet.fluxindustry.core.block.FluxBlocks;
import noxzet.fluxindustry.core.block.electric.BlockFluxCable;
import noxzet.fluxindustry.core.block.electric.BlockFluxCable.CableVariant;
import noxzet.fluxindustry.core.tileentity.TileFluxCable;

public class ItemFluxCable extends ItemFlux {

	public ItemFluxCable(String unlocalizedName)
	{
		super(unlocalizedName);
		super.setHasSubtypes(true);
	}
	
	@Override
	public void registerItemModel()
	{
		int i, len = CableVariant.values().length;
		for (i = 0; i < len; i++)
			FluxIndustry.proxy.registerItemRenderer(this, i, "variant=" + CableVariant.fromMeta(i));
	}
	
	@Override
    public void getSubItems(Item item, CreativeTabs tab, NonNullList<ItemStack> subItems)
    {
		int i, len = CableVariant.values().length;
		for(i = 0; i < len; i++)
			subItems.add(new ItemStack(item, 1, i));
    }
	
	@Override
	public String getUnlocalizedName(ItemStack stack)
	{
		int meta = stack.getMetadata();
		if (meta < 0 || meta >= CableVariant.values().length)
			meta = 0;
		return "item." + FluxIndustry.MODID + ".cable_" + CableVariant.fromMeta(meta).getName();
	}
	
	@Override
    @SideOnly(Side.CLIENT)
    public void addInformation(ItemStack stack, EntityPlayer playerIn, List<String> tooltip, boolean advanced)
    {
		tooltip.add("% T/t");
		tooltip.add("Loss: ");
    }
	
    public EnumActionResult onItemUse(EntityPlayer player, World world, BlockPos pos, EnumHand hand, EnumFacing facing, float hitX, float hitY, float hitZ)
    {
    	ItemStack itemstack = player.getHeldItem(hand);
    	pos = pos.offset(facing);
    	if (player.canPlayerEdit(pos, facing, itemstack) && FluxBlocks.fluxCable.canPlaceBlockAt(world, pos))
	    {
    		world.setBlockState(pos, FluxBlocks.fluxCable.getDefaultState()
    				.withProperty(BlockFluxCable.VARIANT, CableVariant.fromMeta(itemstack.getMetadata())), 11);
    		TileFluxCable entity = (TileFluxCable)world.getTileEntity(pos);
    		// Setting cable properties from itemstack's NBT
        	NBTTagCompound itemTag = itemstack.getTagCompound();
        	if (itemTag != null)
        	{
	        	NBTTagCompound totalCompound = new NBTTagCompound();
	        	if (itemTag.hasKey("teslaCable", NBT.TAG_COMPOUND))
	        	{
	        		NBTTagCompound itemCableTag = (NBTTagCompound)itemTag.getTag("teslaCable");
		    		NBTTagCompound cableContainerCompound = new NBTTagCompound();
		    		cableContainerCompound.setLong("TeslaPower", 0);
		    		if (itemCableTag.hasKey("TeslaCapacity", NBT.TAG_LONG))
			    		cableContainerCompound.setLong("TeslaCapacity", itemCableTag.getLong("TeslaCapacity"));
		    		if (itemCableTag.hasKey("TeslaMinPower", NBT.TAG_LONG))
			    		cableContainerCompound.setLong("TeslaMinPower", itemCableTag.getLong("TeslaMinPower"));
		    		if (itemCableTag.hasKey("TeslaLoss", NBT.TAG_FLOAT))
			    		cableContainerCompound.setFloat("TeslaLoss", itemCableTag.getFloat("TeslaLoss"));
		    		totalCompound.setTag("teslaCable", cableContainerCompound);
	        	}
	        	else
	        		totalCompound.setTag("teslaCable", new NBTTagCompound());
	        	totalCompound.setInteger("x", pos.getX());
	        	totalCompound.setInteger("y", pos.getY());
	        	totalCompound.setInteger("z", pos.getZ());
	    		entity.readFromNBT(totalCompound);
        	}
	    	itemstack.shrink(1);
	    	return EnumActionResult.SUCCESS;
    	}
    	return EnumActionResult.FAIL;
    }
	
}
