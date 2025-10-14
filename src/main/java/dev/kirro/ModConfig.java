package dev.kirro;

import com.google.common.collect.Lists;
import dev.kirro.extendedcombat.item.ModItems;
import dev.kirro.extendedcombat.util.DuplicateKeyBindsMode;
import dev.kirro.extendedcombat.util.ItemEntryIterator;
import eu.midnightdust.lib.config.MidnightConfig;
import net.minecraft.util.Identifier;

import java.util.List;

public class ModConfig extends MidnightConfig {
    public static final String client = "client";
    public static final String model_system = "model_system";

    // common
    @Entry
    public static boolean disableDurability = false;

    @Entry(min = 5, max = 100)
    public static int wardingStoneActiveRadius = 55;

    @Entry
    public static boolean wardingStonePacifiesMobs = false;

    @Entry
    public static boolean xpRepairActive = true;

    @Entry
    public static boolean airMovementActive = true;

    // client
    @Entry(category = client)
    public static boolean showArmorSleeves = true;

    @Entry(category = client)
    public static DuplicateKeyBindsMode allowDuplicateKeybinds = DuplicateKeyBindsMode.MOVEMENT;

    // model system
    @Comment(category = model_system, centered = true)
    public static Comment acceptableItemsDescription;
    @Entry(category = model_system)
    public static List<Identifier> acceptableItems = Lists.newArrayList(
            ItemEntryIterator.getItemId(ModItems.WOODEN_GREATSWORD.getDefaultStack()),
            ItemEntryIterator.getItemId(ModItems.STONE_GREATSWORD.getDefaultStack()),
            ItemEntryIterator.getItemId(ModItems.IRON_GREATSWORD.getDefaultStack()),
            ItemEntryIterator.getItemId(ModItems.GOLDEN_GREATSWORD.getDefaultStack()),
            ItemEntryIterator.getItemId(ModItems.DIAMOND_GREATSWORD.getDefaultStack()),
            ItemEntryIterator.getItemId(ModItems.NETHERITE_GREATSWORD.getDefaultStack()),
            ItemEntryIterator.getItemId(ModItems.NETHER_STEEL_GREATSWORD.getDefaultStack()),
            ItemEntryIterator.getItemId(ModItems.ECHO_STEEL_GREATSWORD.getDefaultStack())
    );

    static {
        MidnightConfig.init(ExtendedCombat.MOD_ID, ModConfig.class);
    }
}
