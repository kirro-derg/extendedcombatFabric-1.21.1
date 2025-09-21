package dev.kirro.extendedcombat.entity.components;

import dev.kirro.ExtendedCombat;
import dev.kirro.extendedcombat.behavior.enchantment.*;
import net.minecraft.util.Identifier;
import org.ladysnake.cca.api.v3.component.ComponentKey;
import org.ladysnake.cca.api.v3.component.ComponentRegistry;
import org.ladysnake.cca.api.v3.entity.EntityComponentFactoryRegistry;
import org.ladysnake.cca.api.v3.entity.EntityComponentInitializer;
import org.ladysnake.cca.api.v3.entity.RespawnCopyStrategy;

public class ModEntityComponents implements EntityComponentInitializer {

    public static final ComponentKey<AirJumpBehavior> AIR_JUMP = ComponentRegistry.getOrCreate(Identifier.of(ExtendedCombat.MOD_ID, "air_jump"), AirJumpBehavior.class);
    public static final ComponentKey<AirMovementBehavior> AIR_MOVEMENT = ComponentRegistry.getOrCreate(Identifier.of(ExtendedCombat.MOD_ID, "air_movement"), AirMovementBehavior.class);
    public static final ComponentKey<DashBehavior> DASH = ComponentRegistry.getOrCreate(Identifier.of(ExtendedCombat.MOD_ID, "dash"), DashBehavior.class);
    public static final ComponentKey<BlinkBehavior> BLINK = ComponentRegistry.getOrCreate(Identifier.of(ExtendedCombat.MOD_ID, "blink"), BlinkBehavior.class);

    @Override
    public void registerEntityComponentFactories(EntityComponentFactoryRegistry registry) {
        registry.registerForPlayers(AIR_MOVEMENT, AirMovementBehavior::new, RespawnCopyStrategy.NEVER_COPY);
        registry.registerForPlayers(AIR_JUMP, AirJumpBehavior::new, RespawnCopyStrategy.LOSSLESS_ONLY);
        registry.registerForPlayers(DASH, DashBehavior::new, RespawnCopyStrategy.LOSSLESS_ONLY);
        registry.registerForPlayers(BLINK, BlinkBehavior::new, RespawnCopyStrategy.LOSSLESS_ONLY);
    }
}
