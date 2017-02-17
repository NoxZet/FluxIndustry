package noxzet.fluxindustry.core.item;

import net.minecraftforge.fml.common.registry.GameRegistry;

public class FluxItems {

	public static ItemFlux ingotBasic, nuggetBasic, powderBasic, materialBasic, batteryBasic, wrenchBrass, treetap, cableUninsulated, cableBasic;
	
	public static void init()
	{
		ingotBasic = register(new ItemFluxMulti("ingot_basic", "ingot_", new String[] {"copper", "tin", "zinc", "aluminum", "lead", "bronze", "brass"}));
		nuggetBasic = register(new ItemFluxMulti("nugget_basic", "nugget_", new String[] {"copper", "tin", "zinc", "aluminum", "lead", "bronze", "brass"}));
		powderBasic = register(new ItemFluxMulti("powder_basic", "powder_", new String[] {"copper", "tin", "zinc", "aluminum", "lead", "bronze", "brass", "uranium", "iron", "gold", "granite", "diorite", "andesite", "stone"}));
		materialBasic = register(new ItemFluxMulti("material_basic", "", new String[] {"rubbertree_sap", "rubber_plate", "pellets_lithium", "salt_sodium", "salt_potassium", "metal_lithium", "metal_sodium", "metal_potassium", "powder_sulfur"}));
		batteryBasic = register(ItemFluxBattery.itemFluxBatteryBasic("battery_basic", "battery_"));
		wrenchBrass = register(new ItemFluxWrench("wrench_brass"));
		treetap = register(new ItemTreetap("treetap"));
		cableUninsulated = register(new ItemFluxMulti("cable_uninsulated", "cable_uninsulated_", new String[] {"copper", "tin", "aluminum", "bronze", "iron", "gold"}));
		cableBasic = register(new ItemFluxCable("cable_basic"));
	}
	
	public static void postInit()
	{
		ingotBasic.setCreativeTab(FluxCreativeTabs.FIMATERIALS);
		nuggetBasic.setCreativeTab(FluxCreativeTabs.FIMATERIALS);
		powderBasic.setCreativeTab(FluxCreativeTabs.FIMATERIALS);
		materialBasic.setCreativeTab(FluxCreativeTabs.FIMATERIALS);
		batteryBasic.setCreativeTab(FluxCreativeTabs.FIMACHINES);
		wrenchBrass.setCreativeTab(FluxCreativeTabs.FIMACHINES);
		treetap.setCreativeTab(FluxCreativeTabs.FIBLOCKS);
		cableUninsulated.setCreativeTab(FluxCreativeTabs.FIMATERIALS);
		cableBasic.setCreativeTab(FluxCreativeTabs.FIMACHINES);
	}
	
	public static ItemFlux register(ItemFlux item)
	{
		GameRegistry.register(item);
		item.registerItemModel();
		return item;
	}
	
}
