package dev.kirro.extendedcombat.util;

import dev.kirro.ModConfig;
import dev.kirro.extendedcombat.effects.ModStatusEffects;
import dev.kirro.extendedcombat.enchantment.ModEnchantmentEffectComponentTypes;
import dev.kirro.extendedcombat.tags.ModItemTags;
import net.fabricmc.fabric.api.tag.convention.v2.ConventionalFluidTags;
import net.minecraft.block.BlockState;
import net.minecraft.block.Blocks;
import net.minecraft.component.DataComponentTypes;
import net.minecraft.enchantment.EnchantmentHelper;
import net.minecraft.entity.Entity;
import net.minecraft.entity.EquipmentSlot;
import net.minecraft.entity.LivingEntity;
import net.minecraft.entity.mob.MobEntity;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.fluid.FluidState;
import net.minecraft.item.ItemStack;
import net.minecraft.registry.tag.FluidTags;
import net.minecraft.state.property.Properties;
import net.minecraft.util.hit.HitResult;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.MathHelper;
import net.minecraft.world.RaycastContext;
import virtuoel.pehkui.api.ScaleTypes;

public class ExtendedCombatUtil {

    public static boolean isUnbreakable(ItemStack stack) {
        return ModConfig.disableDurability && !stack.isEmpty() && stack.contains(DataComponentTypes.MAX_DAMAGE) && !stack.isIn(ModItemTags.ALWAYS_HAS_DURABILITY) || EnchantmentHelper.hasAnyEnchantmentsWith(stack, ModEnchantmentEffectComponentTypes.KEEPSAKE);
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

    public static boolean isGroundedElytra(LivingEntity living, boolean allowWater) {
        if (living instanceof PlayerEntity player && player.getAbilities().flying) {
            return false;
        }
        if (!allowWater) {
            if (living.isTouchingWater() || living.isSwimming()) {
                return false;
            }
        }
        return living.isFallFlying() && living.getVehicle() == null && !living.isClimbing();
    }

    public static boolean isGrounded(LivingEntity living) {
        return isGrounded(living, false);
    }

    public static boolean isGroundedElytra(LivingEntity living) {
        return isGroundedElytra(living, true);
    }

    public static boolean inAir(Entity entity, double altitude) {
        return entity.getWorld().raycast(new RaycastContext(entity.getPos(), entity.getPos().add(0, -altitude, 0), RaycastContext.ShapeType.COLLIDER, RaycastContext.FluidHandling.ANY, entity)).getType() == HitResult.Type.MISS;
    }

    private static boolean crouching(Entity entity) {
        if (entity.isSneaking()) {
            return true;
        }
        return entity instanceof MobEntity && entity.getControllingPassenger() instanceof PlayerEntity player && player.isSneaking();
    }

    public static boolean canWalkOn(LivingEntity entity) {
        return !crouching(entity)
                && !isSubmerged(entity)
                && EnchantmentHelper.hasAnyEnchantmentsWith(entity.getEquippedStack(EquipmentSlot.FEET), ModEnchantmentEffectComponentTypes.FLUID_WALKER)
                && !entity.isSubmergedInWater();
    }

    public static boolean isSubmerged(Entity entity) {
        for (float i = 0.5f; i < entity.getHeight(); i+= 0.1f) {
            FluidState state = entity.getWorld().getFluidState(BlockPos.ofFloored(entity.getPos().add(0, i, 0)));
            if (!state.isEmpty()) {
                return true;
            }
        }
        return false;
    }

    public static boolean isFlameResistant(LivingEntity entity) {
        ItemStack helmet = entity.getEquippedStack(EquipmentSlot.HEAD);
        ItemStack chestplate = entity.getEquippedStack(EquipmentSlot.CHEST);
        ItemStack leggings = entity.getEquippedStack(EquipmentSlot.LEGS);
        ItemStack boots = entity.getEquippedStack(EquipmentSlot.FEET);

        return helmet.isIn(ModItemTags.FLAME_RESISTANT_ARMOR)
                && chestplate.isIn(ModItemTags.FLAME_RESISTANT_ARMOR)
                && leggings.isIn(ModItemTags.FLAME_RESISTANT_ARMOR)
                && boots.isIn(ModItemTags.FLAME_RESISTANT_ARMOR);
    }

    public static void removeEffect(LivingEntity entity) {
        if (!entity.hasStatusEffect(ModStatusEffects.SHRINKING) && !isFlameResistant(entity)) {
            ScaleTypes.BASE.getScaleData(entity).setTargetScale(1.0f);
        } else if (!entity.hasStatusEffect(ModStatusEffects.SHRINKING) && isFlameResistant(entity)) {
            ScaleTypes.BASE.getScaleData(entity).setTargetScale(1.25f);
        }
    }
}
