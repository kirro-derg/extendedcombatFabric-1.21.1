package dev.kirro.mixin.enchantment.obscurity;

import dev.kirro.extendedcombat.enchantment.ModEnchantmentEffectComponentTypes;
import dev.kirro.extendedcombat.item.ModDataComponentTypes;
import net.minecraft.client.render.entity.LivingEntityRenderer;
import net.minecraft.enchantment.EnchantmentHelper;
import net.minecraft.entity.EquipmentSlot;
import net.minecraft.entity.LivingEntity;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.item.ItemStack;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;

@Mixin(LivingEntityRenderer.class)
public class LivingEntityRendererMixin<T extends LivingEntity> {
    @Inject(method = "hasLabel*", at = @At("HEAD"), cancellable = true)
    public void hasLabel(T entity, CallbackInfoReturnable<Boolean> cir) {
        if (entity instanceof PlayerEntity player) {
            ItemStack stack = player.getEquippedStack(EquipmentSlot.HEAD);
            if (EnchantmentHelper.hasAnyEnchantmentsWith(stack, ModEnchantmentEffectComponentTypes.OBSCURITY)) {
                boolean hidden = stack.getOrDefault(ModDataComponentTypes.HIDDEN, false);
                if (!hidden) {
                    cir.setReturnValue(false);
                }
            }
        }
    }
}
