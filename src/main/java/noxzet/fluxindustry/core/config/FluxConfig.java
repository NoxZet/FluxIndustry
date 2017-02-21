package noxzet.fluxindustry.core.config;

import net.minecraftforge.common.config.Configuration;
import noxzet.fluxindustry.core.FluxIndustry;
import org.apache.logging.log4j.Level;

import static noxzet.fluxindustry.core.FluxIndustry.logger;

public class FluxConfig {
    // general
    public static final String ENERGY_UNIT;

    public static final String CATEGORY_OREGEN = "oregen";
    public static final boolean DO_ORE_GEN;
    public static final boolean DO_ORE_GEN_COPPER;
    public static final boolean DO_ORE_GEN_TIN;
    public static final boolean DO_ORE_GEN_ZINC;
    public static final boolean DO_ORE_GEN_ALUMINUM;
    public static final boolean DO_ORE_GEN_LEAD;
    public static final boolean DO_ORE_GEN_URANIUM;

    static {
        Configuration cfg = FluxIndustry.cfg;
        try {
            cfg.load();
        } catch (Exception e) {
            logger.log(Level.ERROR, "Unable to load config!");
        }

        cfg.addCustomCategoryComment(Configuration.CATEGORY_GENERAL, "General configuration");
        ENERGY_UNIT = cfg.getString("EnergyUnit", Configuration.CATEGORY_GENERAL, "T", "Energy Unit to be used in tooltips, does not affect API comparability");

        cfg.addCustomCategoryComment(CATEGORY_OREGEN, "Ore generation configuration");
        DO_ORE_GEN = cfg.getBoolean("allDoOreGen", CATEGORY_OREGEN, true, "Should ore generate at all? Good for modpacks that have multiple ores. When set to false this option overrides individual options.");
        DO_ORE_GEN_COPPER = cfg.getBoolean("doCopperOreGen", CATEGORY_OREGEN, true, "Should Copper ore generate? Good for modpacks that have multiple ores");
        DO_ORE_GEN_TIN = cfg.getBoolean("doTinOreGen", CATEGORY_OREGEN, true, "Should Tin ore generate? Good for modpacks that have multiple ores");
        DO_ORE_GEN_ZINC = cfg.getBoolean("doZincOreGen", CATEGORY_OREGEN, true, "Should Zinc ore generate? Good for modpacks that have multiple ores");
        DO_ORE_GEN_ALUMINUM = cfg.getBoolean("doAluminumOreGen", CATEGORY_OREGEN, true, "Should Aluminum ore generate? Good for modpacks that have multiple ores");
        DO_ORE_GEN_LEAD = cfg.getBoolean("doLeadOreGen", CATEGORY_OREGEN, true, "Should Lead ore generate? Good for modpacks that have multiple ores");
        DO_ORE_GEN_URANIUM = cfg.getBoolean("doUraniumOreGen", CATEGORY_OREGEN, true, "Should Uranium ore generate? Good for modpacks that have multiple ores");


        if (cfg.hasChanged())
            cfg.save();
    }
}
