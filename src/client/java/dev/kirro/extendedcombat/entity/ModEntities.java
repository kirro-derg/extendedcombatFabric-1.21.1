package dev.kirro.extendedcombat.entity;

import dev.kirro.ExtendedCombat;
import dev.kirro.extendedcombat.entity.custom.ChairEntity;
import dev.kirro.extendedcombat.entity.custom.StatueEntity;
import net.minecraft.entity.EntityType;
import net.minecraft.entity.SpawnGroup;
import net.minecraft.registry.Registries;
import net.minecraft.registry.Registry;
import net.minecraft.util.Identifier;

public class ModEntities {
    public static final EntityType<StatueEntity> STATUE = Registry.register(Registries.ENTITY_TYPE,
            Identifier.of(ExtendedCombat.MOD_ID, "statue"),
            EntityType.Builder.create(StatueEntity::new, SpawnGroup.MISC)
                    .dimensions(0.5f, 1.975f).eyeHeight(1.7775F).maxTrackingRange(10).build());
    public static final EntityType<ChairEntity> CHAIR = Registry.register(Registries.ENTITY_TYPE,
            Identifier.of(ExtendedCombat.MOD_ID, "chair_entity"),
            EntityType.Builder.create(ChairEntity::new, SpawnGroup.MISC)
                    .dimensions(0.5f, 0.5f).build());

    public static void registerModEntities() {
        ExtendedCombat.LOGGER.info("registering entities for " + ExtendedCombat.MOD_ID);
    }
}
