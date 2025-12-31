package dev.kirro.mixin;

import dev.kirro.extendedcombat.util.ExtendedCombatUtil;
import net.minecraft.client.MinecraftClient;
import net.minecraft.client.gui.hud.InGameOverlayRenderer;
import net.minecraft.client.util.math.MatrixStack;
import net.minecraft.entity.LivingEntity;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

@Mixin(InGameOverlayRenderer.class)
public abstract class EntityMixin {
    @Inject(method = "renderFireOverlay", at = @At("HEAD"), cancellable = true)
    private static void isOnFire(MinecraftClient client, MatrixStack matrices, CallbackInfo ci) {
        if (client.player instanceof LivingEntity entity) {
            if (ExtendedCombatUtil.isFlameResistant(entity)) {
                ci.cancel();
            }
        }
    }
}
