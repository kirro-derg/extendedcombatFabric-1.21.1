package dev.kirro.mixin.enchantment.obscurity;

import dev.kirro.extendedcombat.enchantment.ModEnchantmentEffectComponentTypes;
import net.minecraft.client.render.entity.LivingEntityRenderer;
import net.minecraft.enchantment.EnchantmentHelper;
import net.minecraft.entity.EquipmentSlot;
import net.minecraft.entity.LivingEntity;
import net.minecraft.entity.player.PlayerEntity;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;

@Mixin(LivingEntityRenderer.class)
public class LivingEntityRendererMixin<T extends LivingEntity> {
    @Inject(method = "hasLabel*", at = @At("HEAD"), cancellable = true)
    public void hasLabel(T entity, CallbackInfoReturnable<Boolean> cir) {
        if (entity instanceof PlayerEntity player) {
            if (EnchantmentHelper.hasAnyEnchantmentsWith(player.getEquippedStack(EquipmentSlot.HEAD), ModEnchantmentEffectComponentTypes.OBSCURITY)) {
                cir.setReturnValue(false);
            }
        }
    }
}
