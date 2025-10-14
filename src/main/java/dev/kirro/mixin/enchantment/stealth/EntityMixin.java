package dev.kirro.mixin.enchantment.stealth;

import dev.kirro.extendedcombat.enchantment.ModEnchantmentEffectComponentTypes;
import net.minecraft.enchantment.EnchantmentHelper;
import net.minecraft.entity.Entity;
import net.minecraft.entity.EquipmentSlot;
import net.minecraft.entity.player.PlayerEntity;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;

@Mixin(Entity.class)
public class EntityMixin {
    @Inject(method = "shouldSpawnSprintingParticles", at = @At("HEAD"),cancellable = true)
    public void shouldSpawnSprintingParticles(CallbackInfoReturnable<Boolean> cir) {
        if ((Object) this instanceof PlayerEntity player) {
            if (EnchantmentHelper.hasAnyEnchantmentsWith(player.getEquippedStack(EquipmentSlot.CHEST), ModEnchantmentEffectComponentTypes.STEALTH)) {
                cir.setReturnValue(false);
            }
        }
    }
}
