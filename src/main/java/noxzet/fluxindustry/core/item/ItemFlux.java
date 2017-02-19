package noxzet.fluxindustry.core.item;

import net.minecraft.creativetab.CreativeTabs;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;
import noxzet.fluxindustry.core.FluxIndustry;

public class ItemFlux extends Item {

	protected String unlocalizedName;
	
	public ItemFlux(String unlocalizedName)
	{
		this.unlocalizedName = unlocalizedName;
		this.setUnlocalizedName(unlocalizedName);
		this.setRegistryName(unlocalizedName);
	}
	
	public void registerItemModel()
	{
		FluxIndustry.proxy.registerItemRenderer(this, 0, "normal");
	}
	
	public void registerColorHandler()
	{
		FluxIndustry.proxy.prepareColorHandler(this);
	}
	
	@Override
	public Item setUnlocalizedName(String unlocalizedName)
	{
		super.setUnlocalizedName(unlocalizedName);
		this.unlocalizedName = unlocalizedName;
		return this;
	}
	
	@Override
	public ItemFlux setCreativeTab(CreativeTabs tab)
	{
		super.setCreativeTab(tab);
		return this;
	}
	
	public String getUnlocalizedId()
	{
		return this.unlocalizedName;
	}
	
	@Override
	public String getUnlocalizedName()
	{
		return "item." + FluxIndustry.MODID + "." + this.unlocalizedName;
	}
	
	@Override
	public String getUnlocalizedName(ItemStack stack)
	{
		return "item." + FluxIndustry.MODID + "." + this.unlocalizedName;
	}
	
	@SideOnly(Side.CLIENT)
	public int getColor(ItemStack stack, int tintIndex)
	{
		return 0xFFFFFF;
	}
	
}
