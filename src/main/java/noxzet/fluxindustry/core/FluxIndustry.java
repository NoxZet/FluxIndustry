package noxzet.fluxindustry.core;

import java.io.File;

import org.apache.logging.log4j.Logger;

import net.minecraftforge.common.config.Configuration;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.common.SidedProxy;
import net.minecraftforge.fml.common.event.FMLInitializationEvent;
import net.minecraftforge.fml.common.event.FMLPostInitializationEvent;
import net.minecraftforge.fml.common.event.FMLPreInitializationEvent;
import net.minecraftforge.fml.common.network.NetworkRegistry;
import net.minecraftforge.fml.common.registry.GameRegistry;
import noxzet.fluxindustry.core.block.FluxBlocks;
import noxzet.fluxindustry.core.config.FluxConfig;
import noxzet.fluxindustry.core.container.FluxGUI;
import noxzet.fluxindustry.core.crafting.FluxCrafting;
import noxzet.fluxindustry.core.item.FluxCreativeTabs;
import noxzet.fluxindustry.core.item.FluxItems;
import noxzet.fluxindustry.core.proxy.CommonProxy;
import noxzet.fluxindustry.core.world.FluxGenOverworld;

@Mod(modid = FluxIndustry.MODID, version = FluxIndustry.VERSION, dependencies = "required-after:tesla")
public class FluxIndustry
{
    public static final String MODID = "fluxindustry";
    public static final String VERSION = "0.0.1";
	@SidedProxy(clientSide="noxzet.fluxindustry.core.proxy.ClientProxy", serverSide="noxzet.fluxindustry.core.proxy.ServerProxy")
	public static CommonProxy proxy;
	public static FluxIndustry INSTANCE;
	public static String unit;
    public static Logger logger;
    public static Configuration cfg;
    
    @Mod.EventHandler
    public void preInit(FMLPreInitializationEvent event)
    {
    	INSTANCE = this;
    	logger = event.getModLog();
        cfg = new Configuration(new File(event.getModConfigurationDirectory(), "FluxIndustry.cfg"));
        unit = FluxConfig.ENERGY_UNIT;
        FluxCreativeTabs.init();
        FluxItems.init();
        FluxBlocks.init();
    	GameRegistry.registerWorldGenerator(new FluxGenOverworld(), 3);
    }
    
    @Mod.EventHandler
    public void init(FMLInitializationEvent event)
    {
    	FluxItems.postInit();
    	FluxBlocks.postInit();
    	FluxCrafting.init();
    	NetworkRegistry.INSTANCE.registerGuiHandler(INSTANCE, new FluxGUI());
    }
    
    @Mod.EventHandler
    public void postInit(FMLPostInitializationEvent event)
    {
    	FluxCrafting.postInit();
    }
    
}
