package dev.kirro.extendedcombat.item;

import dev.kirro.ExtendedCombat;
import dev.kirro.extendedcombat.block.ModBlocks;
import net.fabricmc.fabric.api.itemgroup.v1.FabricItemGroup;
import net.minecraft.item.ItemGroup;
import net.minecraft.item.ItemStack;
import net.minecraft.registry.Registries;
import net.minecraft.registry.Registry;
import net.minecraft.text.Text;
import net.minecraft.util.Identifier;

public class ModItemGroups {
    public static final ItemGroup EXTENDEDCOMBAT_ITEM_GROUP = Registry.register(Registries.ITEM_GROUP,
            Identifier.of(ExtendedCombat.MOD_ID, "extended_combat_items"),
            FabricItemGroup.builder().icon(() -> new ItemStack(ModItems.NETHER_STEEL_GREATSWORD))
                    .displayName(Text.translatable("itemgroup.extended_combat_items"))
                    .entries((displayContext, entries) -> {
                        entries.add(ModItems.NETHER_STEEL_INGOT);
                        entries.add(ModItems.ECHO_STEEL_INGOT);
                        entries.add(ModItems.WOODEN_HANDLE);
                        entries.add(ModItems.NETHER_STEEL_HANDLE);
                        entries.add(ModItems.NETHER_STEEL_UPGRADE);
                        entries.add(ModItems.ECHO_STEEL_UPGRADE);
                        entries.add(ModItems.POISON_DAGGER);

                        entries.add(ModBlocks.NETHER_STEEL_BLOCK);
                        entries.add(ModBlocks.WARDING_STONE);
                        entries.add(ModBlocks.FLAT_BLOCK);
                        entries.add(ModBlocks.FRAMED_GLASS_PANEL);
                        entries.add(ModBlocks.HEAVY_DOOR);
                        entries.add(ModItems.STATUE);

                        entries.add(ModItems.WOODEN_GREATSWORD);
                        entries.add(ModItems.STONE_GREATSWORD);
                        entries.add(ModItems.IRON_GREATSWORD);
                        entries.add(ModItems.GOLDEN_GREATSWORD);
                        entries.add(ModItems.DIAMOND_GREATSWORD);
                        entries.add(ModItems.NETHERITE_GREATSWORD);
                        entries.add(ModItems.NETHER_STEEL_GREATSWORD);
                        entries.add(ModItems.ECHO_STEEL_GREATSWORD);

                        entries.add(ModItems.NETHER_STEEL_PICKAXE);

                        entries.add(ModItems.WOODEN_HAMMER);
                        entries.add(ModItems.STONE_HAMMER);
                        entries.add(ModItems.IRON_HAMMER);
                        entries.add(ModItems.GOLDEN_HAMMER);
                        entries.add(ModItems.DIAMOND_HAMMER);
                        entries.add(ModItems.NETHERITE_HAMMER);
                        entries.add(ModItems.NETHER_STEEL_HAMMER);
                        entries.add(ModItems.ECHO_STEEL_HAMMER);

                        entries.add(ModItems.NETHER_STEEL_HELMET);
                        entries.add(ModItems.NETHER_STEEL_CHESTPLATE);
                        entries.add(ModItems.NETHER_STEEL_LEGGINGS);
                        entries.add(ModItems.NETHER_STEEL_BOOTS);
                        entries.add(ModItems.ECHO_STEEL_HELMET);
                        entries.add(ModItems.ECHO_STEEL_CHESTPLATE);
                        entries.add(ModItems.ECHO_STEEL_LEGGINGS);
                        entries.add(ModItems.ECHO_STEEL_BOOTS);
                        entries.add(ModItems.ECHO_REINFORCED_ELYTRA);

                        entries.add(ModItems.BLACK_APPLE);
                        entries.add(ModItems.GOLDEN_STEAK);
                        entries.add(ModItems.HONEY_BREAD);

                    }).build());

    public static void registerItemGroups() {
        ExtendedCombat.LOGGER.info("registering Item Groups for " + ExtendedCombat.MOD_ID);
    }
}
