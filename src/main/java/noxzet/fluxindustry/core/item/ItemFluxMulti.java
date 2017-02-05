package noxzet.fluxindustry.core.item;

import net.minecraft.creativetab.CreativeTabs;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.util.NonNullList;
import noxzet.fluxindustry.core.FluxIndustry;

public class ItemFluxMulti extends ItemFlux {
	
	public String[] NAME;
	public String GROUPNAME;
	
	public ItemFluxMulti(String unlocalizedName, String groupName, String[] name)
	{
		super(unlocalizedName);
		super.setHasSubtypes(true);
		this.NAME = name;
		this.GROUPNAME = groupName;
	}
	
	@Override
	public void registerItemModel() {
		int i, len = NAME.length;
		for(i = 0; i < len; i++)
			FluxIndustry.proxy.registerItemRenderer(this, i, "variant="+NAME[i]);
	}
	
	@Override
    public void getSubItems(Item item, CreativeTabs tab, NonNullList<ItemStack> subItems)
    {
		int i, len = NAME.length;
		for(i = 0; i < len; i++)
			subItems.add(new ItemStack(item, 1, i));
    }
	
	@Override
	public String getUnlocalizedName(ItemStack stack)
	{
		int meta = stack.getMetadata();
		if (meta < 0 || meta >= NAME.length)
			meta = 0;
		return "item." + FluxIndustry.MODID + "." + GROUPNAME + NAME[meta];
	}
	
}
