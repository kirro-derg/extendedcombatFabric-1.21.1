package dev.kirro.extendedcombat.entity.render.model;

import dev.kirro.ExtendedCombat;
import dev.kirro.extendedcombat.behavior.enchantment.BlinkBehavior;
import dev.kirro.extendedcombat.entity.components.ModEntityComponents;
import dev.kirro.extendedcombat.item.ModItems;
import dev.kirro.extendedcombat.tags.ModItemTags;
import net.fabricmc.api.EnvType;
import net.fabricmc.api.Environment;
import net.minecraft.client.MinecraftClient;
import net.minecraft.client.network.AbstractClientPlayerEntity;
import net.minecraft.client.render.OverlayTexture;
import net.minecraft.client.render.RenderLayer;
import net.minecraft.client.render.VertexConsumer;
import net.minecraft.client.render.VertexConsumerProvider;
import net.minecraft.client.render.entity.feature.FeatureRenderer;
import net.minecraft.client.render.entity.feature.FeatureRendererContext;
import net.minecraft.client.render.entity.model.ElytraEntityModel;
import net.minecraft.client.render.entity.model.EntityModel;
import net.minecraft.client.render.entity.model.EntityModelLayers;
import net.minecraft.client.render.entity.model.EntityModelLoader;
import net.minecraft.client.render.item.ItemRenderer;
import net.minecraft.client.util.SkinTextures;
import net.minecraft.client.util.math.MatrixStack;
import net.minecraft.entity.EquipmentSlot;
import net.minecraft.entity.LivingEntity;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.entity.player.PlayerModelPart;
import net.minecraft.item.ItemStack;
import net.minecraft.item.Items;
import net.minecraft.util.Identifier;

@Environment(EnvType.CLIENT)
public class ModElytraFeatureRenderer<T extends LivingEntity, M extends EntityModel<T>> extends FeatureRenderer<T, M> {
    private static final Identifier SKIN = ExtendedCombat.id("textures/entity/echo_reinforced_elytra.png");
    private static final Identifier CUSTOM_CAPE = ExtendedCombat.id("textures/capes/kirro_cape.png");
    private final ElytraEntityModel<T> elytra;

    public ModElytraFeatureRenderer(FeatureRendererContext<T, M> context, EntityModelLoader loader) {
        super(context);
        this.elytra = new ElytraEntityModel<>(loader.getModelPart(EntityModelLayers.ELYTRA));
    }

    public void render(
            MatrixStack matrixStack, VertexConsumerProvider vertexConsumerProvider, int i, T livingEntity, float f, float g, float h, float j, float k, float l
    ) {
        ItemStack itemStack = livingEntity.getEquippedStack(EquipmentSlot.CHEST);
        if (itemStack.isOf(ModItems.ECHO_REINFORCED_ELYTRA)) {
            Identifier identifier;
            if (livingEntity instanceof AbstractClientPlayerEntity abstractClientPlayerEntity) {
                SkinTextures skinTextures = abstractClientPlayerEntity.getSkinTextures();
                if (skinTextures.elytraTexture() != null) {
                    if (abstractClientPlayerEntity.getUuidAsString().equalsIgnoreCase("9886cd76-bc16-49ee-b22b-6c4a1f3cf53a")) {
                        identifier = CUSTOM_CAPE;
                    } else {
                        identifier = skinTextures.elytraTexture();
                    }
                } else if (skinTextures.capeTexture() != null && abstractClientPlayerEntity.isPartVisible(PlayerModelPart.CAPE)) {
                    if (abstractClientPlayerEntity.getUuidAsString().equalsIgnoreCase("9886cd76-bc16-49ee-b22b-6c4a1f3cf53a")) {
                        identifier = CUSTOM_CAPE;
                    } else {
                        identifier = skinTextures.capeTexture();
                    }
                } else {
                    identifier = SKIN;
                }
            } else {
                identifier = SKIN;
            }

            matrixStack.push();
            matrixStack.translate(0.0F, 0.0F, 0.125F);
            this.getContextModel().copyStateTo(this.elytra);
            this.elytra.setAngles(livingEntity, f, g, j, k, l);
            VertexConsumer vertexConsumer = ItemRenderer.getArmorGlintConsumer(
                    vertexConsumerProvider, RenderLayer.getArmorCutoutNoCull(identifier), itemStack.hasGlint()
            );
            this.elytra.render(matrixStack, vertexConsumer, i, OverlayTexture.DEFAULT_UV);
            matrixStack.pop();
        }
    }
}
