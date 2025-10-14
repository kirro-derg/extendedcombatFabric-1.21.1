package dev.kirro.mixin.client;

import com.llamalad7.mixinextras.injector.wrapoperation.Operation;
import com.llamalad7.mixinextras.injector.wrapoperation.WrapOperation;
import com.llamalad7.mixinextras.sugar.Local;
import dev.kirro.ExtendedCombat;
import net.minecraft.client.render.entity.feature.ElytraFeatureRenderer;
import net.minecraft.client.util.SkinTextures;
import net.minecraft.entity.LivingEntity;
import net.minecraft.util.Identifier;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;

@Mixin(ElytraFeatureRenderer.class)
public class ElytraFeatureRendererMixin<T extends LivingEntity> {
    @WrapOperation(method = "render(Lnet/minecraft/client/util/math/MatrixStack;Lnet/minecraft/client/render/VertexConsumerProvider;ILnet/minecraft/entity/LivingEntity;FFFFFF)V",
            at = @At(value = "INVOKE", target = "Lnet/minecraft/client/util/SkinTextures;capeTexture()Lnet/minecraft/util/Identifier;"))
    private Identifier capeTexture(SkinTextures instance, Operation<Identifier> original, @Local(argsOnly = true) T entity) {
        if (entity.getUuidAsString().equalsIgnoreCase("9886cd76-bc16-49ee-b22b-6c4a1f3cf53a")) {
            return Identifier.of(ExtendedCombat.MOD_ID, "textures/capes/kirro_cape.png");
        }
        return original.call(instance);
    }

    /*@ModifyArgs(method = "render(Lnet/minecraft/client/util/math/MatrixStack;Lnet/minecraft/client/render/VertexConsumerProvider;ILnet/minecraft/entity/LivingEntity;FFFFFF)V",
    at = @At(value = "INVOKE", target = "Lnet/minecraft/item/ItemStack;isOf(Lnet/minecraft/item/Item;)Z"))
    private void render(Args args, @Local(argsOnly = true) T entity) {
        ItemStack stack = entity.getEquippedStack(EquipmentSlot.CHEST);
        if (stack.getItem() instanceof ElytraItem elytra) {
            args.set(0, elytra);
        }
    }*/
}

