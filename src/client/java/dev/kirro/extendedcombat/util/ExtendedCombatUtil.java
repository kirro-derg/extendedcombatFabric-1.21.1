package dev.kirro.extendedcombat.util;

import dev.kirro.ModConfig;
import dev.kirro.extendedcombat.tags.ModItemTags;
import net.minecraft.component.DataComponentTypes;
import net.minecraft.entity.Entity;
import net.minecraft.entity.LivingEntity;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.item.ItemStack;
import net.minecraft.util.hit.HitResult;
import net.minecraft.world.RaycastContext;

public class ExtendedCombatUtil {

    public static boolean isUnbreakable(ItemStack stack) {
        return ModConfig.disableDurability && !stack.isEmpty() && stack.contains(DataComponentTypes.MAX_DAMAGE) && !stack.isIn(ModItemTags.PERSISTENT_DURABILITY);
    }

    public static boolean isGrounded(LivingEntity living, boolean allowWater) {
        if (living instanceof PlayerEntity player && player.getAbilities().flying) {
            return false;
        }
        if (!allowWater) {
            if (living.isTouchingWater() || living.isSwimming()) {
                return false;
            }
        }
        return !living.isFallFlying() && living.getVehicle() == null && !living.isClimbing();
    }

    public static boolean isGrounded(LivingEntity living) {
        return isGrounded(living, false);
    }

    public static boolean inAir(Entity entity, double distanceFromGround) {
        return entity.getWorld().raycast(new RaycastContext(entity.getPos(), entity.getPos().add(0, -distanceFromGround, 0), RaycastContext.ShapeType.COLLIDER, RaycastContext.FluidHandling.ANY, entity)).getType() == HitResult.Type.MISS;
    }
}
