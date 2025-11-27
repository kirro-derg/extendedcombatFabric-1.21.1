package dev.kirro.mixin.item;

import dev.kirro.ExtendedCombat;
import dev.kirro.extendedcombat.item.ModDataComponentTypes;
import dev.kirro.extendedcombat.item.custom.WoolArmorItem;
import net.minecraft.client.render.VertexConsumerProvider;
import net.minecraft.client.render.entity.feature.ArmorFeatureRenderer;
import net.minecraft.client.render.entity.feature.FeatureRenderer;
import net.minecraft.client.render.entity.feature.FeatureRendererContext;
import net.minecraft.client.render.entity.model.BipedEntityModel;
import net.minecraft.client.util.math.MatrixStack;
import net.minecraft.component.DataComponentTypes;
import net.minecraft.component.type.DyedColorComponent;
import net.minecraft.entity.EquipmentSlot;
import net.minecraft.entity.LivingEntity;
import net.minecraft.item.ArmorMaterial;
import net.minecraft.item.ItemStack;
import net.minecraft.item.trim.ArmorTrim;
import net.minecraft.registry.entry.RegistryEntry;
import net.minecraft.registry.tag.ItemTags;
import net.minecraft.util.Identifier;
import net.minecraft.util.math.ColorHelper;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.Unique;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

@Mixin(ArmorFeatureRenderer.class)
public abstract class ArmorFeatureRendererMixin<T extends LivingEntity, M extends BipedEntityModel<T>, A extends BipedEntityModel<T>> extends FeatureRenderer<T, M> {

    public ArmorFeatureRendererMixin(FeatureRendererContext<T, M> context) {
        super(context);
    }

    @Shadow
    protected abstract boolean usesInnerModel(EquipmentSlot slot);

    @Shadow
    protected abstract void renderArmorParts(MatrixStack matrices, VertexConsumerProvider vertexConsumers, int light, A model, int i, Identifier identifier);

    @Shadow
    protected abstract void renderTrim(RegistryEntry<ArmorMaterial> armorMaterial, MatrixStack matrices, VertexConsumerProvider vertexConsumers, int light, ArmorTrim trim, A model, boolean leggings);

    @Shadow
    protected abstract void renderGlint(MatrixStack matrices, VertexConsumerProvider vertexConsumers, int light, A model);

    @Inject(method = "renderArmor", at = @At(value = "HEAD"), cancellable = true)
    private void getTexture(MatrixStack matrices, VertexConsumerProvider vertexConsumers, T entity, EquipmentSlot armorSlot, int light, A model, CallbackInfo ci) {
        ItemStack stack = entity.getEquippedStack(armorSlot);
        if (stack.getItem() instanceof WoolArmorItem armorItem) {
            ci.cancel();
            if (armorItem.getSlotType() == armorSlot) {
                this.getContextModel().copyBipedStateTo(model);
                this.setVisible(model, armorSlot, stack);
                boolean bl = this.usesInnerModel(armorSlot);
                Identifier texture = ExtendedCombat.id("textures/models/armor/wool" + "_layer_" + (bl ? 2 : 1) + ".png");
                ArmorMaterial armorMaterial = armorItem.getMaterial().value();
                int i = stack.isIn(ItemTags.DYEABLE) ? ColorHelper.Argb.fullAlpha(DyedColorComponent.getColor(stack, -6265536)) : -1;

                for (ArmorMaterial.Layer layer : armorMaterial.layers()) {
                    int j = layer.isDyeable() ? i : -1;
                    this.renderArmorParts(matrices, vertexConsumers, light, model, j, texture);
                }

                ArmorTrim armorTrim = stack.get(DataComponentTypes.TRIM);
                if (armorTrim != null) {
                    this.renderTrim(armorItem.getMaterial(), matrices, vertexConsumers, light, armorTrim, model, bl);
                }

                if (stack.hasGlint()) {
                    this.renderGlint(matrices, vertexConsumers, light, model);
                }
            }
        }

    }

    @Unique
    private void setVisible(A bipedModel, EquipmentSlot slot, ItemStack stack) {
        bipedModel.setVisible(false);
        switch (slot) {
            case HEAD -> {
                if (!stack.getOrDefault(ModDataComponentTypes.HIDDEN, false)) {
                    bipedModel.head.visible = true;
                }
            }
            case CHEST -> {
                if (!stack.getOrDefault(ModDataComponentTypes.HIDDEN, false)) {
                    bipedModel.hat.visible = true;
                }
                bipedModel.body.visible = true;
                bipedModel.rightArm.visible = true;
                bipedModel.leftArm.visible = true;
            }
            case LEGS -> {
                bipedModel.body.visible = true;
                bipedModel.rightLeg.visible = true;
                bipedModel.leftLeg.visible = true;
            }
            case FEET -> {
                bipedModel.rightLeg.visible = true;
                bipedModel.leftLeg.visible = true;
            }
        }
    }
}
