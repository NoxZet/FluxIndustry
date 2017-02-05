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
import net.minecraft.util.text.translation.I18n;
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

	public NBTTagCompound[] itemTag = new NBTTagCompound[6];
	public NBTTagCompound[] itemCableTag = new NBTTagCompound[6];
	public ItemFluxCable(String unlocalizedName)
	{
		super(unlocalizedName);
		super.setHasSubtypes(true);
		for(int i = 0; i < itemTag.length; i++)
		{
			itemTag[i] = new NBTTagCompound();
			itemCableTag[i] = new NBTTagCompound();
		}
		// Copper wire
		itemCableTag[0].setLong("TeslaCapacity", 80);
		itemCableTag[0].setLong("TeslaMinPower", 20);
		itemCableTag[0].setFloat("TeslaLoss", 0.5F);		
		itemTag[0].setTag("teslaCable", itemCableTag[0]);
		// Tin wire
		itemCableTag[1].setLong("TeslaCapacity", 20);
		itemCableTag[1].setLong("TeslaMinPower", 8);
		itemCableTag[1].setFloat("TeslaLoss", 0.1F);
		itemTag[1].setTag("teslaCable", itemCableTag[1]);
		// Aluminum wire
		itemCableTag[2].setLong("TeslaCapacity", 160);
		itemCableTag[2].setLong("TeslaMinPower", 60);
		itemCableTag[2].setFloat("TeslaLoss", 0.7F);
		itemTag[2].setTag("teslaCable", itemCableTag[2]);
		// Bronze wire
		itemCableTag[3].setLong("TeslaCapacity", 320);
		itemCableTag[3].setLong("TeslaMinPower", 80);
		itemCableTag[3].setFloat("TeslaLoss", 0.9F);
		itemTag[3].setTag("teslaCable", itemCableTag[3]);
		// Iron wire
		itemCableTag[4].setLong("TeslaCapacity", 640);
		itemCableTag[4].setLong("TeslaMinPower", 240);
		itemCableTag[4].setFloat("TeslaLoss", 2.5F);
		itemTag[4].setTag("teslaCable", itemCableTag[4]);
		// Iron wire
		itemCableTag[5].setLong("TeslaCapacity", 1280);
		itemCableTag[5].setLong("TeslaMinPower", 320);
		itemCableTag[5].setFloat("TeslaLoss", 2.1F);
		itemTag[5].setTag("teslaCable", itemCableTag[5]);
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
		ItemStack currentCable;
		int i, len = CableVariant.values().length;
		for(i = 0; i < len; i++)
		{
			currentCable = new ItemStack(item, 1, i);
			currentCable.setTagCompound(itemTag[i]);
			subItems.add(currentCable);
		}
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
    public void addInformation(ItemStack itemstack, EntityPlayer playerIn, List<String> tooltip, boolean advanced)
    {
		NBTTagCompound itemTag = itemstack.getTagCompound();
		if (itemTag != null && itemTag.hasKey("teslaCable", NBT.TAG_COMPOUND))
		{
			NBTTagCompound itemCableTag = itemTag.getCompoundTag("teslaCable");
			if (itemCableTag.hasKey("TeslaCapacity", NBT.TAG_LONG))
				tooltip.add(I18n.translateToLocal("item.fluxindustry.tip.max") + " " + itemCableTag.getLong("TeslaCapacity") + " T/t");
			if (itemCableTag.hasKey("TeslaMinPower", NBT.TAG_LONG))
				tooltip.add(I18n.translateToLocal("item.fluxindustry.tip.min") + " " + itemCableTag.getLong("TeslaMinPower") + " T/t");
			if (itemCableTag.hasKey("TeslaLoss", NBT.TAG_FLOAT))
				tooltip.add(I18n.translateToLocal("item.fluxindustry.tip.loss") + " " + itemCableTag.getFloat("TeslaLoss") + " T/m");
		}
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
