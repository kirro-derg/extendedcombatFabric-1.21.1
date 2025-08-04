package dev.kirro;

import dev.kirro.extendedcombat.util.DuplicateKeyBindsMode;
import eu.midnightdust.lib.config.MidnightConfig;

public class ModConfig extends MidnightConfig {
    @Entry
    public static boolean disableDurability = true;

    @Entry
    public static boolean airMobilityActive = true;

    @Entry(category = "client")
    public static DuplicateKeyBindsMode allowDuplicateKeybinds = DuplicateKeyBindsMode.MOVEMENT;

    static {
        MidnightConfig.init(ExtendedCombat.MOD_ID, ModConfig.class);
    }
}
