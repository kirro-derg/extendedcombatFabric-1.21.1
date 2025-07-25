package dev.kirro;

import eu.midnightdust.lib.config.MidnightConfig;

public class ModConfig extends MidnightConfig {
    @Entry
    public static boolean disableDurability = true;

    static {
        MidnightConfig.init(ExtendedCombat.MOD_ID, ModConfig.class);
    }
}
