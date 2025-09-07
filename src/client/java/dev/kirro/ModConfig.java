package dev.kirro;

import dev.kirro.extendedcombat.util.DuplicateKeyBindsMode;
import eu.midnightdust.lib.config.MidnightConfig;

public class ModConfig extends MidnightConfig {
    // common
    @Entry
    public static boolean disableDurability = false;

    @Entry(min = 5, max = 100)
    public static int wardingStoneActiveRadius = 55;

    @Entry
    public static boolean xpRepairActive = true;

    @Entry
    public static boolean airMobilityActive = true;

    // client
    @Entry(category = "client")
    public static boolean showArmorSleeves = true;

    @Entry(category = "client")
    public static DuplicateKeyBindsMode allowDuplicateKeybinds = DuplicateKeyBindsMode.MOVEMENT;

    static {
        MidnightConfig.init(ExtendedCombat.MOD_ID, ModConfig.class);
    }
}
