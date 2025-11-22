package dev.kirro.mixin.enchantment.swiftness;

import dev.kirro.extendedcombat.enchantment.ModEnchantmentEffectComponentTypes;
import net.minecraft.block.Block;
import net.minecraft.client.MinecraftClient;
import net.minecraft.enchantment.EnchantmentHelper;
import net.minecraft.entity.EquipmentSlot;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;

@Mixin(Block.class)
public abstract class BlocksMixin {

    @Inject(method = "getVelocityMultiplier", at = @At("HEAD"), cancellable = true)
    private void test(CallbackInfoReturnable<Float> cir) {
        MinecraftClient client = MinecraftClient.getInstance();
        if (client != null) {
            if (client.player != null) {
                if (EnchantmentHelper.hasAnyEnchantmentsWith(client.player.getEquippedStack(EquipmentSlot.LEGS), ModEnchantmentEffectComponentTypes.SWIFTNESS)) {
                    cir.setReturnValue(1.0f);
                }
            }
        }
    }
}
