package dev.kirro.extendedcombat.entity.render.model;

import com.google.common.collect.Maps;
import dev.kirro.ExtendedCombat;
import dev.kirro.extendedcombat.item.custom.HunterMaskItem;
import dev.kirro.extendedcombat.item.custom.WoolArmorItem;
import net.fabricmc.api.EnvType;
import net.fabricmc.api.Environment;
import net.minecraft.client.render.*;
import net.minecraft.client.render.entity.feature.FeatureRenderer;
import net.minecraft.client.render.entity.feature.FeatureRendererContext;
import net.minecraft.client.render.entity.model.BipedEntityModel;
import net.minecraft.client.render.model.BakedModelManager;
import net.minecraft.client.texture.Sprite;
import net.minecraft.client.texture.SpriteAtlasTexture;
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
import net.minecraft.util.math.ColorHelper.Argb;

import java.util.Map;

@Environment(EnvType.CLIENT)
public class CloakFeatureRenderer<T extends LivingEntity, M extends BipedEntityModel<T>, A extends BipedEntityModel<T>> extends FeatureRenderer<T, M> {
    private static final Map<String, Identifier> ARMOR_TEXTURE_CACHE = Maps.<String, Identifier>newHashMap();
    private final A innerModel;
    private final A outerModel;
    private final SpriteAtlasTexture armorTrimsAtlas;

    public CloakFeatureRenderer(FeatureRendererContext<T, M> context, A innerModel, A outerModel, BakedModelManager bakery) {
        super(context);
        this.innerModel = innerModel;
        this.outerModel = outerModel;
        this.armorTrimsAtlas = bakery.getAtlas(TexturedRenderLayers.ARMOR_TRIMS_ATLAS_TEXTURE);
    }

    public void render(
            MatrixStack matrixStack, VertexConsumerProvider vertexConsumerProvider, int i, T livingEntity, float f, float g, float h, float j, float k, float l
    ) {
        this.renderArmor(matrixStack, vertexConsumerProvider, livingEntity, EquipmentSlot.CHEST, i, this.getModel(EquipmentSlot.CHEST));
        this.renderArmor(matrixStack, vertexConsumerProvider, livingEntity, EquipmentSlot.LEGS, i, this.getModel(EquipmentSlot.LEGS));
        this.renderArmor(matrixStack, vertexConsumerProvider, livingEntity, EquipmentSlot.FEET, i, this.getModel(EquipmentSlot.FEET));
        this.renderArmor(matrixStack, vertexConsumerProvider, livingEntity, EquipmentSlot.HEAD, i, this.getModel(EquipmentSlot.HEAD));
    }

    private void renderArmor(MatrixStack matrices, VertexConsumerProvider vertexConsumers, T entity, EquipmentSlot armorSlot, int light, A model) {
        ItemStack itemStack = entity.getEquippedStack(armorSlot);
        if (itemStack.getItem() instanceof WoolArmorItem armorItem) {
            if (armorItem.getSlotType() == armorSlot) {
                this.getContextModel().copyBipedStateTo(model);
                this.setVisible(model, armorSlot, itemStack);
                boolean bl = this.usesInnerModel(armorSlot);
                ArmorMaterial armorMaterial = armorItem.getMaterial().value();
                int i = itemStack.isIn(ItemTags.DYEABLE) ? Argb.fullAlpha(DyedColorComponent.getColor(itemStack, -6265536)) : -1;

                for (ArmorMaterial.Layer layer : armorMaterial.layers()) {
                    int j = layer.isDyeable() ? i : -1;
                    this.renderArmorParts(matrices, vertexConsumers, light, model, j, getTextureId(itemStack, bl));
                }

                ArmorTrim armorTrim = itemStack.get(DataComponentTypes.TRIM);
                if (armorTrim != null) {
                    this.renderTrim(armorItem.getMaterial(), matrices, vertexConsumers, light, armorTrim, model, bl);
                }

                if (itemStack.hasGlint()) {
                    this.renderGlint(matrices, vertexConsumers, light, model);
                }
            }
        }
    }

    private Identifier getTextureId(ItemStack stack, boolean secondLayer) {
        if (stack.getItem() instanceof WoolArmorItem) {
            return ExtendedCombat.id("textures/models/armor/wool" + "_layer_" + (secondLayer ? 2 : 1) + ".png");
        }
        return Identifier.of("hi :3");
    }

    protected void setVisible(A bipedModel, EquipmentSlot slot, ItemStack stack) {
        bipedModel.setVisible(false);
        switch (slot) {
            case HEAD -> {
                if (!HunterMaskItem.isHidden(stack)) {
                    bipedModel.head.visible = true;
                }
            }
            case CHEST -> {
                if (!WoolArmorItem.isHidden(stack)) {
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

    private void renderArmorParts(MatrixStack matrices, VertexConsumerProvider vertexConsumers, int light, A model, int color, Identifier identifier) {
        VertexConsumer vertexConsumer = vertexConsumers.getBuffer(RenderLayer.getArmorCutoutNoCull(identifier));
        model.render(matrices, vertexConsumer, light, OverlayTexture.DEFAULT_UV, color);
    }

    private void renderTrim(
            RegistryEntry<ArmorMaterial> armorMaterial,
            MatrixStack matrices,
            VertexConsumerProvider vertexConsumers,
            int light,
            ArmorTrim trim,
            A model,
            boolean leggings
    ) {
        Sprite sprite = this.armorTrimsAtlas.getSprite(leggings ? trim.getLeggingsModelId(armorMaterial) : trim.getGenericModelId(armorMaterial));
        VertexConsumer vertexConsumer = sprite.getTextureSpecificVertexConsumer(
                vertexConsumers.getBuffer(TexturedRenderLayers.getArmorTrims(trim.getPattern().value().decal()))
        );
        model.render(matrices, vertexConsumer, light, OverlayTexture.DEFAULT_UV);
    }

    private void renderGlint(MatrixStack matrices, VertexConsumerProvider vertexConsumers, int light, A model) {
        model.render(matrices, vertexConsumers.getBuffer(RenderLayer.getArmorEntityGlint()), light, OverlayTexture.DEFAULT_UV);
    }

    private A getModel(EquipmentSlot slot) {
        return this.usesInnerModel(slot) ? this.innerModel : this.outerModel;
    }

    private boolean usesInnerModel(EquipmentSlot slot) {
        return slot == EquipmentSlot.LEGS;
    }
}

