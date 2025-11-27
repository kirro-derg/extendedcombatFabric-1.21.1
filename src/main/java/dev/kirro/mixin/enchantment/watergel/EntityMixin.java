package dev.kirro.mixin.enchantment.watergel;

import dev.kirro.extendedcombat.enchantment.ModEnchantmentEffectComponentTypes;
import net.minecraft.enchantment.EnchantmentHelper;
import net.minecraft.entity.Entity;
import net.minecraft.entity.EquipmentSlot;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.item.ItemStack;
import net.minecraft.item.TridentItem;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;

@Mixin(Entity.class)
public class EntityMixin {
    @Inject(method = "isTouchingWaterOrRain", at = @At("RETURN"), cancellable = true)
    private void isWatergelled(CallbackInfoReturnable<Boolean> cir) {
        if ((Object) this instanceof PlayerEntity player) {
            ItemStack stack = player.getEquippedStack(EquipmentSlot.LEGS);
            if (EnchantmentHelper.hasAnyEnchantmentsWith(stack, ModEnchantmentEffectComponentTypes.WATERGEL)) {
                if (player.getMainHandStack().getItem() instanceof TridentItem || player.getOffHandStack().getItem() instanceof TridentItem) {
                    cir.setReturnValue(true);
                }
            }
        }
    }
}
