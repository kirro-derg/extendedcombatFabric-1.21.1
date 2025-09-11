package dev.kirro.extendedcombat.mixin.potion.misc;

import dev.kirro.extendedcombat.effects.ModStatusEffects;
import net.minecraft.enchantment.EnchantmentHelper;
import net.minecraft.entity.EquipmentSlot;
import net.minecraft.entity.LivingEntity;
import net.minecraft.item.ItemStack;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

@Mixin(EnchantmentHelper.class)
public class EnchantmentHelperMixin {
    @Inject(
            method = "forEachEnchantment(Lnet/minecraft/item/ItemStack;Lnet/minecraft/enchantment/EnchantmentHelper$Consumer;)V",
            at = @At("HEAD"),
            cancellable = true
    )
    private static void forEachEnchantment(ItemStack stack, EnchantmentHelper.Consumer consumer, CallbackInfo ci) {
        if (stack.getHolder() instanceof LivingEntity living && living.hasStatusEffect(ModStatusEffects.VULNERABILITY)) {
            ci.cancel();
        }
    }

    @Inject(
            method = "forEachEnchantment(Lnet/minecraft/entity/LivingEntity;Lnet/minecraft/enchantment/EnchantmentHelper$ContextAwareConsumer;)V",
            at = @At("HEAD"),
            cancellable = true
    )
    private static void forEachEnchantment(LivingEntity entity, EnchantmentHelper.ContextAwareConsumer contextAwareConsumer, CallbackInfo ci) {
        if (entity.hasStatusEffect(ModStatusEffects.VULNERABILITY)) {
            ci.cancel();
        }
    }

    @Inject(
            method = "forEachEnchantment(Lnet/minecraft/item/ItemStack;Lnet/minecraft/entity/EquipmentSlot;Lnet/minecraft/entity/LivingEntity;Lnet/minecraft/enchantment/EnchantmentHelper$ContextAwareConsumer;)V",
            at = @At("HEAD"),
            cancellable = true
    )
    private static void forEachEnchantment(ItemStack stack, EquipmentSlot slot, LivingEntity entity, EnchantmentHelper.ContextAwareConsumer contextAwareConsumer, CallbackInfo ci) {
        if (entity.hasStatusEffect(ModStatusEffects.VULNERABILITY)) {
            ci.cancel();
        }
    }
}
