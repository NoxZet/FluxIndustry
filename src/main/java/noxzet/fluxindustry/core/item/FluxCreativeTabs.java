package noxzet.fluxindustry.core.item;

import net.minecraft.creativetab.CreativeTabs;
import net.minecraft.item.ItemStack;
import noxzet.fluxindustry.core.FluxIndustry;
import noxzet.fluxindustry.core.block.FluxBlocks;

public class FluxCreativeTabs extends CreativeTabs {
	
	private ItemStack icon;
	public static FluxCreativeTabs FIBLOCKS;
	public static FluxCreativeTabs FIMACHINES;
	public static FluxCreativeTabs FIMATERIALS;
	
	public static void init()
	{
		FIBLOCKS = new FluxCreativeTabs("blocks", new ItemStack(FluxBlocks.blockMetal, 1, 5));
		FIMACHINES = new FluxCreativeTabs("machines", new ItemStack(FluxBlocks.generatorCoal, 1));
		FIMATERIALS = new FluxCreativeTabs("materials", new ItemStack(FluxItems.ingotBasic, 1, 5));
	}
	
	public FluxCreativeTabs(String label, ItemStack icon)
	{
		super(FluxIndustry.MODID + "." + label);
		this.icon = icon;
	}

	@Override
	public ItemStack getTabIconItem()
	{
		return icon;
	}
	
}
