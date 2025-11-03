package dev.kirro.mixin.enchantment.fluidWalker;

import com.mojang.blaze3d.systems.RenderSystem;
import dev.kirro.extendedcombat.enchantment.ModEnchantmentEffectComponentTypes;
import dev.kirro.extendedcombat.util.ExtendedCombatUtil;
import net.minecraft.block.enums.CameraSubmersionType;
import net.minecraft.client.render.BackgroundRenderer;
import net.minecraft.client.render.Camera;
import net.minecraft.client.render.FogShape;
import net.minecraft.enchantment.EnchantmentHelper;
import net.minecraft.entity.Entity;
import net.minecraft.entity.EquipmentSlot;
import net.minecraft.entity.LivingEntity;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

@Mixin(BackgroundRenderer.class)
public class BackgroundRendererMixin {
    @Inject(method = "applyFog", at = @At("HEAD"), cancellable = true)
    private static void applyFog(Camera camera, BackgroundRenderer.FogType fogType, float viewDistance, boolean thickFog, float tickDelta, CallbackInfo ci) {
        CameraSubmersionType type = camera.getSubmersionType();
        Entity entity = camera.getFocusedEntity();
        if (entity instanceof LivingEntity living) {
            if (ExtendedCombatUtil.isSubmergedFully(living)
                    && EnchantmentHelper.hasAnyEnchantmentsWith(living.getEquippedStack(EquipmentSlot.FEET), ModEnchantmentEffectComponentTypes.FLUID_WALKER)
            && type == CameraSubmersionType.LAVA) {
                RenderSystem.setShaderFogStart(0.0f);
                RenderSystem.setShaderFogEnd(viewDistance * 0.025f);
                RenderSystem.setShaderFogShape(FogShape.SPHERE);
                ci.cancel();
            }
        }
    }
}
