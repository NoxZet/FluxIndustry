package noxzet.fluxindustry.core.item;

import net.minecraftforge.fml.common.registry.GameRegistry;
import noxzet.fluxindustry.core.FluxIndustry;

public class FluxItems {

    public static final ItemFluxMulti ingotBasic;
    public static final ItemFluxMulti nuggetBasic;
    public static final ItemFluxMulti powderBasic;
    public static final ItemFluxMulti materialBasic;
    public static final ItemFluxWrench wrenchBrass;
    public static final ItemTreetap treetap;
    public static final ItemFluxMulti cableUninsulated;
    public static final ItemFluxCable cableBasic;
    public static final ItemFluxBattery batteryBasic;
    public static final ItemFluidContainer fluidContainer;

    public static void init() {

    }

    static {
        ingotBasic = register(new ItemFluxMulti("ingot_basic", "ingot_", new String[]{"copper", "tin", "zinc", "aluminum", "lead", "bronze", "brass"}));
        nuggetBasic = register(new ItemFluxMulti("nugget_basic", "nugget_", new String[]{"copper", "tin", "zinc", "aluminum", "lead", "bronze", "brass"}));
        powderBasic = register(new ItemFluxMulti("powder_basic", "powder_", new String[]{"copper", "tin", "zinc", "aluminum", "lead", "bronze", "brass", "uranium", "iron", "gold", "granite", "diorite", "andesite", "stone"}));
        materialBasic = register(new ItemFluxMulti("material_basic", "", new String[]{"rubbertree_sap", "rubber_plate", "pellets_lithium", "salt_sodium", "salt_potassium", "metal_lithium", "metal_sodium", "metal_potassium", "powder_sulfur"}));
        wrenchBrass = register(new ItemFluxWrench("wrench_brass"));
        treetap = register(new ItemTreetap("treetap"));
        cableUninsulated = register(new ItemFluxMulti("cable_uninsulated", "cable_uninsulated_", new String[]{"copper", "tin", "aluminum", "bronze", "iron", "gold"}));
        cableBasic = register(new ItemFluxCable("cable_basic"));
        batteryBasic = register(ItemFluxBattery.itemFluxBatteryBasic("battery_basic", "battery_"));
        fluidContainer = register(new ItemFluidContainer("fluid_container"));

        fluidContainer.registerAllFluids();
        ingotBasic.setCreativeTab(FluxCreativeTabs.FIMATERIALS);
        nuggetBasic.setCreativeTab(FluxCreativeTabs.FIMATERIALS);
        powderBasic.setCreativeTab(FluxCreativeTabs.FIMATERIALS);
        materialBasic.setCreativeTab(FluxCreativeTabs.FIMATERIALS);
        batteryBasic.setCreativeTab(FluxCreativeTabs.FIMACHINES);
        wrenchBrass.setCreativeTab(FluxCreativeTabs.FIMACHINES);
        treetap.setCreativeTab(FluxCreativeTabs.FIBLOCKS);
        cableUninsulated.setCreativeTab(FluxCreativeTabs.FIMATERIALS);
        cableBasic.setCreativeTab(FluxCreativeTabs.FIMACHINES);
        fluidContainer.setCreativeTab(FluxCreativeTabs.FIMATERIALS);
    }

    public static <T extends ItemFlux> T register(T item) {
        GameRegistry.register(item);
        item.registerItemModel();
        return item;
    }

    public static void postInit() {
        FluxIndustry.proxy.registerColorHandlers();
    }
}
