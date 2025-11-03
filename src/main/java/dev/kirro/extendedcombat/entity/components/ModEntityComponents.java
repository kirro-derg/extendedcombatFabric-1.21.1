package dev.kirro.extendedcombat.entity.components;

import dev.kirro.ExtendedCombat;
import dev.kirro.extendedcombat.behavior.enchantment.*;
import dev.kirro.extendedcombat.behavior.item.HideWoolHoodBehavior;
import org.ladysnake.cca.api.v3.component.ComponentKey;
import org.ladysnake.cca.api.v3.component.ComponentRegistry;
import org.ladysnake.cca.api.v3.entity.EntityComponentFactoryRegistry;
import org.ladysnake.cca.api.v3.entity.EntityComponentInitializer;
import org.ladysnake.cca.api.v3.entity.RespawnCopyStrategy;

public class ModEntityComponents implements EntityComponentInitializer {

    public static final ComponentKey<AirJumpBehavior> AIR_JUMP = ComponentRegistry.getOrCreate(ExtendedCombat.id( "air_jump"), AirJumpBehavior.class);
    public static final ComponentKey<AirMovementBehavior> AIR_MOVEMENT = ComponentRegistry.getOrCreate(ExtendedCombat.id( "air_movement"), AirMovementBehavior.class);
    public static final ComponentKey<DashBehavior> DASH = ComponentRegistry.getOrCreate(ExtendedCombat.id( "dash"), DashBehavior.class);
    public static final ComponentKey<BlinkBehavior> BLINK = ComponentRegistry.getOrCreate(ExtendedCombat.id( "blink"), BlinkBehavior.class);
    public static final ComponentKey<HideWoolHoodBehavior> HIDE_HOOD = ComponentRegistry.getOrCreate(ExtendedCombat.id("hide_hood"), HideWoolHoodBehavior.class);
    public static final ComponentKey<FluidMovementBehavior> FLUID_MOVEMENT = ComponentRegistry.getOrCreate(ExtendedCombat.id("fluid_movement"), FluidMovementBehavior.class);

    @Override
    public void registerEntityComponentFactories(EntityComponentFactoryRegistry registry) {
        registry.registerForPlayers(AIR_MOVEMENT, AirMovementBehavior::new, RespawnCopyStrategy.NEVER_COPY);
        registry.registerForPlayers(AIR_JUMP, AirJumpBehavior::new, RespawnCopyStrategy.LOSSLESS_ONLY);
        registry.registerForPlayers(DASH, DashBehavior::new, RespawnCopyStrategy.LOSSLESS_ONLY);
        registry.registerForPlayers(BLINK, BlinkBehavior::new, RespawnCopyStrategy.LOSSLESS_ONLY);
        registry.registerForPlayers(HIDE_HOOD, HideWoolHoodBehavior::new, RespawnCopyStrategy.NEVER_COPY);
        registry.registerForPlayers(FLUID_MOVEMENT, FluidMovementBehavior::new, RespawnCopyStrategy.NEVER_COPY);
    }
}
