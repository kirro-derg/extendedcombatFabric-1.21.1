package dev.kirro.extendedcombat.entity.render.model;

import dev.kirro.ExtendedCombat;
import dev.kirro.ModConfig;
import dev.kirro.extendedcombat.behavior.enchantment.BlinkBehavior;
import dev.kirro.extendedcombat.entity.components.ModEntityComponents;
import dev.kirro.extendedcombat.tags.ModItemTags;
import net.fabricmc.api.EnvType;
import net.fabricmc.api.Environment;
import net.minecraft.client.render.*;
import net.minecraft.client.render.entity.feature.FeatureRenderer;
import net.minecraft.client.render.entity.feature.FeatureRendererContext;
import net.minecraft.client.render.entity.model.BipedEntityModel;
import net.minecraft.client.render.entity.model.PlayerEntityModel;
import net.minecraft.client.util.math.MatrixStack;
import net.minecraft.component.type.DyedColorComponent;
import net.minecraft.entity.EquipmentSlot;
import net.minecraft.entity.LivingEntity;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.item.ArmorItem;
import net.minecraft.item.ItemStack;
import net.minecraft.registry.Registries;
import net.minecraft.registry.tag.ItemTags;
import net.minecraft.util.Identifier;
import net.minecraft.util.math.ColorHelper;
import org.jetbrains.annotations.Nullable;

@Environment(EnvType.CLIENT)
public class SleeveFeatureRenderer<T extends LivingEntity, M extends BipedEntityModel<T>, A extends BipedEntityModel<T>> extends FeatureRenderer<T, M> {
    private final A sleeveModel;

    public SleeveFeatureRenderer(FeatureRendererContext<T, M> context, A sleeveModel) {
        super(context);
        this.sleeveModel = sleeveModel;
    }

    public void render(
            MatrixStack matrixStack, VertexConsumerProvider vertexConsumerProvider, int i, T entity, float f, float g, float h, float j, float k, float l
    ) {
        this.renderArmor(matrixStack, vertexConsumerProvider, entity, EquipmentSlot.CHEST, i, this.sleeveModel);
    }

    private void renderArmor(MatrixStack matrices, VertexConsumerProvider vertexConsumers, T entity, EquipmentSlot armorSlot, int light, A model) {
        ItemStack stack = entity.getEquippedStack(EquipmentSlot.CHEST);
        if (stack.isIn(ModItemTags.SLEEVED_ARMOR) && stack.getItem() instanceof ArmorItem armorItem && ModConfig.showArmorSleeves && !(entity instanceof PlayerEntity)) {
            if (armorItem.getSlotType() == armorSlot) {
                this.getContextModel().copyBipedStateTo(model);
                this.setVisible(model, armorSlot);
                int i = stack.isIn(ItemTags.DYEABLE) ? ColorHelper.Argb.fullAlpha(DyedColorComponent.getColor(stack, -6265536)) : -1;
                this.renderSleeves(matrices, vertexConsumers, light, model, i, getTextureId(stack));
                if (stack.hasGlint()) {
                    this.renderGlint(matrices, vertexConsumers, light, model);
                }
            }
        } else if (stack.isIn(ModItemTags.SLEEVED_ARMOR) && stack.getItem() instanceof ArmorItem armorItem && ModConfig.showArmorSleeves && (entity instanceof PlayerEntity player)) {
            BlinkBehavior blink = ModEntityComponents.BLINK.get(player);
            if (!blink.isInvisible() && blink.getDuration() == 0) {
                if (armorItem.getSlotType() == armorSlot) {
                    this.getContextModel().copyBipedStateTo(model);
                    this.setVisible(model, armorSlot);
                    int i = stack.isIn(ItemTags.DYEABLE) ? ColorHelper.Argb.fullAlpha(DyedColorComponent.getColor(stack, -6265536)) : -1;
                    this.renderSleeves(matrices, vertexConsumers, light, model, i, getTextureId(stack));
                    if (stack.hasGlint()) {
                        this.renderGlint(matrices, vertexConsumers, light, model);
                    }
                }
            }
        }
    }

    private Identifier getTextureId(ItemStack stack) {
        Identifier texturePath = Registries.ITEM.getId(stack.getItem());
        Identifier truncatedPath = Identifier.of(texturePath.getPath().replace("_chestplate", ""));

        return ExtendedCombat.id("textures/models/armor/" + truncatedPath.getPath() + "_sleeves" + ".png");
    }

    protected void setVisible(A bipedModel, EquipmentSlot slot) {
        bipedModel.setVisible(false);
        if (slot == EquipmentSlot.CHEST) {
            bipedModel.rightArm.visible = true;
            bipedModel.leftArm.visible = true;
            bipedModel.body.visible = false;
        }
    }

    private void renderSleeves(MatrixStack matrices, VertexConsumerProvider vertexConsumers, int light, A model, int i, Identifier identifier) {
        VertexConsumer vertexConsumer = vertexConsumers.getBuffer(RenderLayer.getArmorCutoutNoCull(identifier));
        model.render(matrices, vertexConsumer, light, OverlayTexture.DEFAULT_UV, i);
    }

    private void renderGlint(MatrixStack matrices, VertexConsumerProvider vertexConsumers, int light, A model) {
        model.render(matrices, vertexConsumers.getBuffer(RenderLayer.getArmorEntityGlint()), light, OverlayTexture.DEFAULT_UV);
    }
}
