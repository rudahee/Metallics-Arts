package net.rudahee.metallics_arts.util;

import net.minecraftforge.common.ForgeConfigSpec;
import net.minecraftforge.fml.config.ModConfig;
import net.rudahee.metallics_arts.modules.powers.MetallicsPowersConfig;

public class MetalicsArtsConfig {
    public static final ForgeConfigSpec COMMON_CONFIG;
    public static final ForgeConfigSpec CLIENT_CONFIG;

    static {
        ForgeConfigSpec.Builder COMMON_BUILDER = new ForgeConfigSpec.Builder();
        ForgeConfigSpec.Builder CLIENT_BUILDER = new ForgeConfigSpec.Builder();
        //MaterialsConfig.init(COMMON_BUILDER, CLIENT_BUILDER);
        MetallicsPowersConfig.init(COMMON_BUILDER, CLIENT_BUILDER);

        COMMON_CONFIG = COMMON_BUILDER.build();
        CLIENT_CONFIG = CLIENT_BUILDER.build();
    }
    public static void onReload(final ModConfig.Reloading e) {
        MetallicsPowersConfig.refresh(e);
    }


    public static void onLoad(final ModConfig.Loading e) {
        MetallicsPowersConfig.load_whitelist(e);
    }
}
