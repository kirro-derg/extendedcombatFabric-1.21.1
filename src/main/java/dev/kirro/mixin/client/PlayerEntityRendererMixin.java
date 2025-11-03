package dev.kirro.mixin.client;

import dev.kirro.ExtendedCombat;
import dev.kirro.ModConfig;
import dev.kirro.extendedcombat.entity.custom.ModEntityModelLayers;
import dev.kirro.extendedcombat.entity.render.model.CloakFeatureRenderer;
import dev.kirro.extendedcombat.entity.render.model.MaskFeatureRenderer;
import dev.kirro.extendedcombat.entity.render.model.ModElytraFeatureRenderer;
import dev.kirro.extendedcombat.entity.render.model.SleeveFeatureRenderer;
import dev.kirro.extendedcombat.tags.ModItemTags;
import net.minecraft.client.model.ModelPart;
import net.minecraft.client.network.AbstractClientPlayerEntity;
import net.minecraft.client.render.OverlayTexture;
import net.minecraft.client.render.RenderLayer;
import net.minecraft.client.render.VertexConsumerProvider;
import net.minecraft.client.render.entity.EntityRendererFactory;
import net.minecraft.client.render.entity.LivingEntityRenderer;
import net.minecraft.client.render.entity.PlayerEntityRenderer;
import net.minecraft.client.render.entity.model.ArmorEntityModel;
import net.minecraft.client.render.entity.model.PlayerEntityModel;
import net.minecraft.client.util.math.MatrixStack;
import net.minecraft.component.type.DyedColorComponent;
import net.minecraft.entity.EquipmentSlot;
import net.minecraft.entity.LivingEntity;
import net.minecraft.item.ItemStack;
import net.minecraft.registry.Registries;
import net.minecraft.registry.tag.ItemTags;
import net.minecraft.util.Identifier;
import net.minecraft.util.math.ColorHelper;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.Unique;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

@Mixin(PlayerEntityRenderer.class)
public abstract class PlayerEntityRendererMixin extends LivingEntityRenderer<AbstractClientPlayerEntity, PlayerEntityModel<AbstractClientPlayerEntity>> {

    @Shadow
    protected abstract void setModelPose(AbstractClientPlayerEntity player);

    public PlayerEntityRendererMixin(EntityRendererFactory.Context ctx, PlayerEntityModel<AbstractClientPlayerEntity> model, float shadowRadius) {
        super(ctx, model, shadowRadius);
    }


    @Inject(method = "<init>", at = @At("TAIL"))
    public void extendedcombat$armorSleeves(EntityRendererFactory.Context ctx, boolean slim, CallbackInfo ci) {
        this.addFeature(new SleeveFeatureRenderer<>(this,
                new ArmorEntityModel<>(ctx.getPart(slim ? ModEntityModelLayers.PLAYER_SLIM_SLEEVES : ModEntityModelLayers.PLAYER_SLEEVES))));
        this.addFeature(new ModElytraFeatureRenderer<>(this,
                ctx.getModelLoader()));
        this.addFeature(new CloakFeatureRenderer<>(this,
                new ArmorEntityModel<>(ctx.getPart(slim ? ModEntityModelLayers.CLOAK_INNER_SLIM : ModEntityModelLayers.CLOAK_INNER)),
                new ArmorEntityModel<>(ctx.getPart(ModEntityModelLayers.CLOAK_OUTER))));
        this.addFeature(new MaskFeatureRenderer<>(this,
                new ArmorEntityModel<>(ctx.getPart(ModEntityModelLayers.MASK))));
    }

    @Inject(method = "renderArm", at = @At("HEAD"), cancellable = true)
    private void renderArm(MatrixStack matrices, VertexConsumerProvider vertexConsumers, int light, AbstractClientPlayerEntity player,
                           ModelPart arm, ModelPart sleeve, CallbackInfo ci) {
        ItemStack stack = getArmor(player);
        int i = stack.isIn(ItemTags.DYEABLE) ? ColorHelper.Argb.fullAlpha(DyedColorComponent.getColor(stack, -6265536)) : -1;
        if (getArmor(player).isIn(ModItemTags.SLEEVED_ARMOR) && ModConfig.showArmorSleeves) {
            ci.cancel();
            PlayerEntityModel<AbstractClientPlayerEntity> playerEntityModel = this.getModel();
            this.setModelPose(player);
            playerEntityModel.handSwingProgress = 0.0F;
            playerEntityModel.sneaking = false;
            playerEntityModel.leaningPitch = 0.0F;
            playerEntityModel.setAngles(player, 0.0F, 0.0F, 0.0F, 0.0F, 0.0F);
            arm.pitch = 0.0F;
            Identifier identifier = getTextureId(getArmor(player));
            arm.render(matrices, vertexConsumers.getBuffer(RenderLayer.getEntityTranslucent(player.getSkinTextures().texture())), light, OverlayTexture.DEFAULT_UV);
            //arm.render(matrices, vertexConsumers.getBuffer(RenderLayer.getEntityTranslucent(identifier)), light, OverlayTexture.DEFAULT_UV);
            sleeve.pitch = 0.0F;
            sleeve.render(matrices, vertexConsumers.getBuffer(RenderLayer.getEntityTranslucent(identifier)), light, OverlayTexture.DEFAULT_UV, i);
        } else if (getArmor(player).isIn(ModItemTags.CLOAK)) {
            ci.cancel();
            PlayerEntityModel<AbstractClientPlayerEntity> playerEntityModel = this.getModel();
            this.setModelPose(player);
            playerEntityModel.handSwingProgress = 0.0F;
            playerEntityModel.sneaking = false;
            playerEntityModel.leaningPitch = 0.0F;
            playerEntityModel.setAngles(player, 0.0F, 0.0F, 0.0F, 0.0F, 0.0F);
            arm.pitch = 0.0F;
            Identifier identifier = ExtendedCombat.id("textures/models/armor/wool.png");
            arm.render(matrices, vertexConsumers.getBuffer(RenderLayer.getEntityTranslucent(player.getSkinTextures().texture())), light, OverlayTexture.DEFAULT_UV);
            arm.render(matrices, vertexConsumers.getBuffer(RenderLayer.getEntityTranslucent(identifier)), light, OverlayTexture.DEFAULT_UV);
        }
    }

    @Unique
    private Identifier getTextureId(ItemStack stack) {
        Identifier texturePath = Registries.ITEM.getId(stack.getItem());
        Identifier truncatedPath = Identifier.of(texturePath.getPath().replace("_chestplate", ""));

        return ExtendedCombat.id("textures/models/armor/" + truncatedPath.getPath() + "_sleeve_overlay" + ".png");
    }

    @Unique
    private ItemStack getArmor(LivingEntity entity) {
        return entity.getEquippedStack(EquipmentSlot.CHEST);
    }


}
