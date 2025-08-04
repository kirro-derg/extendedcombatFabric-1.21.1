package dev.kirro.extendedcombat.event;

import dev.kirro.ModConfig;
import dev.kirro.extendedcombat.behavior.enchantment.AirMobilityBehavior;
import dev.kirro.extendedcombat.entity.components.ModEntityComponents;
import net.minecraft.entity.LivingEntity;
import net.minecraft.world.World;

public class AirMobilityEvent implements MultiplyMovementSpeedEvent{
    @Override
    public float multiply(float currentMultiplier, World world, LivingEntity living) {
        if (ModConfig.airMobilityActive && living.isOnGround()) {
            AirMobilityBehavior mobility = ModEntityComponents.AIR_MOBILITY.getNullable(living);
            if (mobility != null && mobility.getTicksInAir() > 10) {
                return currentMultiplier * 1.5f;
            }
        }
        return currentMultiplier;
    }

    @Override
    public int getPriority() {
        return 1001;
    }
}
