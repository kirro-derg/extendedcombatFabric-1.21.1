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
                        entries.add(ModItems.HANDLE);
                        entries.add(ModItems.NETHER_STEEL_UPGRADE);

                        entries.add(ModBlocks.NETHER_STEEL_BLOCK);
                        entries.add(ModBlocks.WARDING_STONE);
                        entries.add(ModBlocks.FLAT_BLOCK);
                        entries.add(ModBlocks.FRAMED_GLASS_PANEL);
                        entries.add(ModBlocks.HEAVY_DOOR);
                        entries.add(ModItems.STATUE);

                        entries.add(ModItems.NETHER_STEEL_GREATSWORD);
                        entries.add(ModItems.NETHER_STEEL_PICKAXE);

                        entries.add(ModItems.NETHER_STEEL_HELMET);
                        entries.add(ModItems.NETHER_STEEL_CHESTPLATE);
                        entries.add(ModItems.NETHER_STEEL_LEGGINGS);
                        entries.add(ModItems.NETHER_STEEL_BOOTS);




                    }).build());

    public static void registerItemGroups() {
        ExtendedCombat.LOGGER.info("registering Item Groups for " + ExtendedCombat.MOD_ID);
    }
}
