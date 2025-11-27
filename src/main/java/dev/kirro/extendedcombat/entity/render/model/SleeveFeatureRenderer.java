package dev.kirro.extendedcombat.entity.render.model;

import dev.kirro.ExtendedCombat;
import dev.kirro.extendedcombat.behavior.enchantment.BlinkBehavior;
import dev.kirro.extendedcombat.entity.components.ModEntityComponents;
import dev.kirro.extendedcombat.item.custom.WoolArmorItem;
import dev.kirro.extendedcombat.tags.ModItemTags;
import net.fabricmc.api.EnvType;
import net.fabricmc.api.Environment;
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
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.item.ArmorItem;
import net.minecraft.item.ItemStack;
import net.minecraft.registry.Registries;
import net.minecraft.registry.tag.ItemTags;
import net.minecraft.util.Identifier;
import net.minecraft.util.math.ColorHelper;

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
        this.renderArmor(matrixStack, vertexConsumerProvider, entity, i, this.sleeveModel);
    }

    private void renderArmor(MatrixStack matrices, VertexConsumerProvider vertexConsumers, T entity, int light, A model) {
        ItemStack stack = entity.getEquippedStack(EquipmentSlot.CHEST);
        if (stack.isIn(ModItemTags.SLEEVED_ARMOR) && stack.getItem() instanceof ArmorItem && !(entity instanceof PlayerEntity)) {
            this.getContextModel().copyBipedStateTo(model);
            this.setVisible(model);
            int i = stack.isIn(ItemTags.DYEABLE) ? ColorHelper.Argb.fullAlpha(DyedColorComponent.getColor(stack, -6265536)) : -1;
            this.renderSleeves(matrices, vertexConsumers, light, model, i, getTextureId(stack));
            if (stack.hasGlint()) {
                this.renderGlint(matrices, vertexConsumers, light, model);
            }
        } else if (stack.isIn(ModItemTags.SLEEVED_ARMOR) && stack.getItem() instanceof ArmorItem && (entity instanceof PlayerEntity player)) {
            BlinkBehavior blink = ModEntityComponents.BLINK.get(player);
            if (!blink.isInvisible() && blink.getDuration() == 0) {
                this.getContextModel().copyBipedStateTo(model);
                this.setVisible(model);
                int i = stack.isIn(ItemTags.DYEABLE) ? ColorHelper.Argb.fullAlpha(DyedColorComponent.getColor(stack, -6265536)) : -1;
                this.renderSleeves(matrices, vertexConsumers, light, model, i, getTextureId(stack));
                if (stack.hasGlint()) {
                    this.renderGlint(matrices, vertexConsumers, light, model);
                }
            }
        }
    }

    private Identifier getTextureId(ItemStack stack) {
        Identifier texturePath = Registries.ITEM.getId(stack.getItem());
        Identifier truncatedPath = Identifier.of(texturePath.getPath().replace("_chestplate", ""));

        if (!(stack.getItem() instanceof WoolArmorItem)) {
            return ExtendedCombat.id("textures/models/armor/" + truncatedPath.getPath() + "_sleeve_overlay" + ".png");
        } else {
            return ExtendedCombat.id("textures/models/armor/wool.png");
        }
    }

    protected void setVisible(A bipedModel) {
        bipedModel.setVisible(false);
        bipedModel.rightArm.visible = true;
        bipedModel.leftArm.visible = true;
        bipedModel.body.visible = false;
    }

    private void renderSleeves(MatrixStack matrices, VertexConsumerProvider vertexConsumers, int light, A model, int i, Identifier identifier) {
        VertexConsumer vertexConsumer = vertexConsumers.getBuffer(RenderLayer.getArmorCutoutNoCull(identifier));
        model.render(matrices, vertexConsumer, light, OverlayTexture.DEFAULT_UV, i);
    }

    private void renderGlint(MatrixStack matrices, VertexConsumerProvider vertexConsumers, int light, A model) {
        model.render(matrices, vertexConsumers.getBuffer(RenderLayer.getArmorEntityGlint()), light, OverlayTexture.DEFAULT_UV);
    }
}
