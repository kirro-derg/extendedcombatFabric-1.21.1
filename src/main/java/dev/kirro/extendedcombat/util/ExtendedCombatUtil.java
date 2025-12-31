package dev.kirro.extendedcombat.util;

import dev.kirro.ModConfig;
import dev.kirro.extendedcombat.enchantment.ModEnchantmentEffectComponentTypes;
import dev.kirro.extendedcombat.tags.ModItemTags;
import net.minecraft.component.ComponentType;
import net.minecraft.component.DataComponentTypes;
import net.minecraft.enchantment.EnchantmentHelper;
import net.minecraft.entity.Entity;
import net.minecraft.entity.EquipmentSlot;
import net.minecraft.entity.LivingEntity;
import net.minecraft.entity.effect.StatusEffectCategory;
import net.minecraft.entity.effect.StatusEffectInstance;
import net.minecraft.entity.mob.MobEntity;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.fluid.Fluid;
import net.minecraft.fluid.FluidState;
import net.minecraft.fluid.Fluids;
import net.minecraft.item.ItemStack;
import net.minecraft.registry.tag.TagKey;
import net.minecraft.util.hit.HitResult;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.RaycastContext;

import java.util.Iterator;

public class ExtendedCombatUtil {

    public static boolean isUnbreakable(ItemStack stack) {
        return ModConfig.disableDurability && !stack.isEmpty() && stack.contains(DataComponentTypes.MAX_DAMAGE) && !stack.isIn(ModItemTags.ALWAYS_HAS_DURABILITY) || EnchantmentHelper.hasAnyEnchantmentsWith(stack, ModEnchantmentEffectComponentTypes.KEEPSAKE);
    }

    public static int clampLoop(int input, int start, int end) {
        if (start - end == 0) {
            return start;
        }
        if (end < start) {
            int temp = start;
            start = end;
            end = temp;
        }
        if (input < start) {
            return end - ((start - input) % (end - start));
        }
        return start + ((input - start) % (end - start));
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
                && !isSubmergedPartial(entity)
                && EnchantmentHelper.hasAnyEnchantmentsWith(entity.getEquippedStack(EquipmentSlot.FEET), ModEnchantmentEffectComponentTypes.FLUID_WALKER)
                && !entity.isSubmergedInWater();
    }

    public static boolean isSubmergedPartial(Entity entity) {
        for (float i = 0.5f; i < entity.getHeight(); i+= 0.1f) {
            FluidState state = entity.getWorld().getFluidState(BlockPos.ofFloored(entity.getPos().add(0, i, 0)));
            if (!state.isEmpty() && !state.isOf(Fluids.EMPTY)) {
                return true;
            }
        }
        return false;
    }

    public static boolean isSubmergedFully(Entity entity) {
        for (float i = entity.getHeight() - 0.1f; i < entity.getHeight(); i+= 0.1f) {
            FluidState state = entity.getWorld().getFluidState(BlockPos.ofFloored(entity.getPos().add(0, i, 0)));
            if (!state.isEmpty() && !state.isOf(Fluids.EMPTY)) {
                return true;
            }
        }
        return false;
    }

    public static boolean isTouchingFluid(Entity entity) {
        for (float i = 0.0f; i < entity.getHeight(); i+= 0.1f) {
            FluidState state = entity.getWorld().getFluidState(BlockPos.ofFloored(entity.getPos().add(0, i, 0)));
            if (!state.isEmpty() && !state.isOf(Fluids.EMPTY)) {
                return true;
            }
        }
        return false;
    }

    public static boolean isTouchingFluidOfType(Entity entity, TagKey<Fluid> tag) {
        for (float i = 0.0f; i < entity.getHeight(); i+= 0.1f) {
            FluidState state = entity.getWorld().getFluidState(BlockPos.ofFloored(entity.getPos().add(0, i, 0)));
            if (!state.isEmpty() && state.isIn(tag)) {
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

    public static boolean hasEnchantmentOfType(Entity entity, ComponentType<?> type) {
        if (entity instanceof LivingEntity living) {
            for (ItemStack stack : living.getArmorItems()) {
                if (EnchantmentHelper.hasAnyEnchantmentsWith(stack, type)) {
                    return true;
                }
            }
        }
        return false;
    }

    public static void removeHarmfulEffects(LivingEntity living) {
        for (StatusEffectInstance effect : living.getStatusEffects()) {
            if (effect.getEffectType().value().getCategory().equals(StatusEffectCategory.HARMFUL)) {
                living.removeStatusEffect(effect.getEffectType());
            }
        }
    }

    public static void removeBeneficialEffects(LivingEntity living) {
        for (StatusEffectInstance effect : living.getStatusEffects()) {
            if (effect.getEffectType().value().getCategory().equals(StatusEffectCategory.BENEFICIAL)) {
                living.removeStatusEffect(effect.getEffectType());
            }
        }
    }

    public static void removeEffectOfType(LivingEntity living, StatusEffectCategory category) {
        Iterator<StatusEffectInstance> iterator = living.getActiveStatusEffects().values().iterator();
        iterator.forEachRemaining(instance -> {
            if (instance.getEffectType().value().getCategory() == category) {
                iterator.remove();
                living.onStatusEffectRemoved(instance);
            }
        });
    }
}
