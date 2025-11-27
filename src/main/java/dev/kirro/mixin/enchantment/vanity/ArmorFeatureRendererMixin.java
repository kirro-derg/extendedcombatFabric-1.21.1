package dev.kirro.mixin.enchantment.vanity;

import dev.kirro.extendedcombat.enchantment.ModEnchantmentEffectComponentTypes;
import dev.kirro.extendedcombat.tags.ModItemTags;
import net.minecraft.client.render.VertexConsumerProvider;
import net.minecraft.client.render.entity.feature.ArmorFeatureRenderer;
import net.minecraft.client.render.entity.model.BipedEntityModel;
import net.minecraft.client.util.math.MatrixStack;
import net.minecraft.enchantment.EnchantmentHelper;
import net.minecraft.entity.EquipmentSlot;
import net.minecraft.entity.LivingEntity;
import net.minecraft.item.ItemStack;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

@Mixin(ArmorFeatureRenderer.class)
public class ArmorFeatureRendererMixin<T extends LivingEntity, A extends BipedEntityModel<T>> {
    @Inject(method = "renderArmor", at = @At(value = "INVOKE_ASSIGN", target = "Lnet/minecraft/entity/LivingEntity;getEquippedStack(Lnet/minecraft/entity/EquipmentSlot;)Lnet/minecraft/item/ItemStack;"), cancellable = true)
    private void renderArmor(MatrixStack matrices, VertexConsumerProvider vertexConsumers, T entity,
                               EquipmentSlot armorSlot, int light, A model, CallbackInfo ci) {
            ItemStack stack = entity.getEquippedStack(armorSlot);
            boolean hasVanity = EnchantmentHelper.hasAnyEnchantmentsWith(stack, ModEnchantmentEffectComponentTypes.VANITY);
            if (hasVanity /*|| stack.getItem() instanceof WoolArmorItem*/) {
                ci.cancel();
            }
    }

    //@Inject(method = "render(Lnet/minecraft/client/util/math/MatrixStack;Lnet/minecraft/client/render/VertexConsumerProvider;ILnet/minecraft/entity/LivingEntity;FFFFFF)V", at = @At("HEAD"), cancellable = true)
    private void render(MatrixStack matrixStack, VertexConsumerProvider vertexConsumerProvider, int i, T livingEntity, float f, float g, float h, float j, float k, float l, CallbackInfo ci) {
        ItemStack cloak = livingEntity.getEquippedStack(EquipmentSlot.CHEST);
        if (cloak.isIn(ModItemTags.CLOAK)) {
            ci.cancel();
        }

    }
}
