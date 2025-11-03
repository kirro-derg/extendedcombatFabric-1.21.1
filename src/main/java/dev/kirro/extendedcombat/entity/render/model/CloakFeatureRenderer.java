package dev.kirro.extendedcombat.entity.render.model;

import dev.kirro.ExtendedCombat;
import dev.kirro.extendedcombat.item.ModDataComponentTypes;
import dev.kirro.extendedcombat.item.custom.WoolArmorItem;
import net.minecraft.client.render.OverlayTexture;
import net.minecraft.client.render.RenderLayer;
import net.minecraft.client.render.VertexConsumer;
import net.minecraft.client.render.VertexConsumerProvider;
import net.minecraft.client.render.entity.feature.FeatureRenderer;
import net.minecraft.client.render.entity.feature.FeatureRendererContext;
import net.minecraft.client.render.entity.model.BipedEntityModel;
import net.minecraft.client.util.math.MatrixStack;
import net.minecraft.component.type.DyedColorComponent;
import net.minecraft.entity.EquipmentSlot;
import net.minecraft.entity.LivingEntity;
import net.minecraft.item.ItemStack;
import net.minecraft.registry.Registries;
import net.minecraft.registry.tag.ItemTags;
import net.minecraft.util.Identifier;
import net.minecraft.util.math.ColorHelper;

public class CloakFeatureRenderer<T extends LivingEntity, M extends BipedEntityModel<T>, A extends BipedEntityModel<T>> extends FeatureRenderer<T, M> {
    private final A outerModel;
    private final A innerModel;

    public CloakFeatureRenderer(FeatureRendererContext<T, M> context, A innerModel, A outerModel) {
        super(context);
        this.outerModel = outerModel;
        this.innerModel = innerModel;
    }

    @Override
    public void render(MatrixStack matrices, VertexConsumerProvider vertexConsumers, int light, T entity, float limbAngle, float limbDistance, float tickDelta, float animationProgress, float headYaw, float headPitch) {
        this.renderArmor(matrices, vertexConsumers, entity, EquipmentSlot.CHEST, light, this.getModel(EquipmentSlot.CHEST));
        this.renderArmor(matrices, vertexConsumers, entity, EquipmentSlot.CHEST, light, this.getModel(EquipmentSlot.HEAD));
    }

    private void renderArmor(MatrixStack matrices, VertexConsumerProvider vertexConsumers, T entity, EquipmentSlot armorSlot, int light, A model) {
        ItemStack itemStack = entity.getEquippedStack(armorSlot);
        if (itemStack.getItem() instanceof WoolArmorItem armorItem) {
            if (armorItem.getSlotType() == armorSlot) {
                this.getContextModel().copyBipedStateTo(model);
                boolean hidden = itemStack.getOrDefault(ModDataComponentTypes.HIDDEN, false);
                this.setVisible(model, armorSlot, hidden);
                int i = itemStack.isIn(ItemTags.DYEABLE) ? ColorHelper.Argb.fullAlpha(DyedColorComponent.getColor(itemStack, -6265536)) : -1;

                this.renderArmorParts(matrices, vertexConsumers, light, model, i, getTextureId(itemStack));

                if (itemStack.hasGlint()) {
                    this.renderGlint(matrices, vertexConsumers, light, model);
                }
            }
        }
    }

    private Identifier getTextureId(ItemStack stack) {
        Identifier texturePath = Registries.ITEM.getId(stack.getItem());
        Identifier truncatedPath = Identifier.of(texturePath.getPath().replace("_chestplate", ""));
        if (stack.getItem() instanceof WoolArmorItem) {
            return ExtendedCombat.id("textures/models/armor/wool.png");
        }

        return ExtendedCombat.id("textures/models/armor/" + truncatedPath.getPath() + ".png");
    }

    protected void setVisible(A bipedModel, EquipmentSlot slot, boolean hidden) {
        bipedModel.setVisible(false);
        switch (slot) {
            case CHEST -> {
                if (bipedModel == this.outerModel) {
                    if (!hidden) {
                        bipedModel.head.visible = true;
                        bipedModel.hat.visible = true;
                    }
                } else {
                    bipedModel.body.visible = true;
                    bipedModel.rightArm.visible = true;
                    bipedModel.leftArm.visible = true;
                    bipedModel.rightLeg.visible = true;
                    bipedModel.leftLeg.visible = true;
                }
            }
        }
    }

    private void renderArmorParts(MatrixStack matrices, VertexConsumerProvider vertexConsumers, int light, A model, int i, Identifier identifier) {
        VertexConsumer vertexConsumer = vertexConsumers.getBuffer(RenderLayer.getArmorCutoutNoCull(identifier));
        model.render(matrices, vertexConsumer, light, OverlayTexture.DEFAULT_UV, i);
    }

    private void renderGlint(MatrixStack matrices, VertexConsumerProvider vertexConsumers, int light, A model) {
        model.render(matrices, vertexConsumers.getBuffer(RenderLayer.getArmorEntityGlint()), light, OverlayTexture.DEFAULT_UV);
    }

    private A getModel(EquipmentSlot slot) {
        return this.usesOuterModel(slot) ? this.outerModel : this.innerModel;
    }

    private boolean usesOuterModel(EquipmentSlot slot) {
        return slot == EquipmentSlot.HEAD;
    }
}
