package dev.kirro.extendedcombat.block.entity;

import dev.kirro.ExtendedCombat;
import dev.kirro.extendedcombat.block.ModBlocks;
import net.minecraft.block.entity.BlockEntityType;
import net.minecraft.registry.Registries;
import net.minecraft.registry.Registry;
import net.minecraft.util.Identifier;

public class ModBlockEntityTypes {
    public static <T extends BlockEntityType<?>> T register(String path, T type) {
        return Registry.register(Registries.BLOCK_ENTITY_TYPE, Identifier.of(ExtendedCombat.MOD_ID, path), type);
    }

    public static final BlockEntityType<FlatBlockEntity> FLAT_BLOCK = register("flat_block",
            BlockEntityType.Builder.create(FlatBlockEntity::new, ModBlocks.FLAT_BLOCK).build(null));

    public static void registerBlockEntityTypes() {
        ExtendedCombat.LOGGER.info("registering Block Entities for " + ExtendedCombat.MOD_ID);
    }
}
