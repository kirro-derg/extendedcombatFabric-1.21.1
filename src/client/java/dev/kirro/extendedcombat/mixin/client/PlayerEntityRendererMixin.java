package dev.kirro.extendedcombat.mixin.client;

import dev.kirro.ModConfig;
import dev.kirro.extendedcombat.entity.custom.ModEntityModelLayers;
import dev.kirro.extendedcombat.entity.render.model.*;
import dev.kirro.extendedcombat.tags.ModItemTags;
import net.minecraft.client.network.AbstractClientPlayerEntity;
import net.minecraft.client.render.entity.EntityRendererFactory;
import net.minecraft.client.render.entity.LivingEntityRenderer;
import net.minecraft.client.render.entity.PlayerEntityRenderer;
import net.minecraft.client.render.entity.model.ArmorEntityModel;
import net.minecraft.client.render.entity.model.PlayerEntityModel;
import net.minecraft.entity.EquipmentSlot;
import net.minecraft.entity.player.PlayerModelPart;
import net.minecraft.item.ItemStack;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

@Mixin(PlayerEntityRenderer.class)
public abstract class PlayerEntityRendererMixin extends LivingEntityRenderer<AbstractClientPlayerEntity, PlayerEntityModel<AbstractClientPlayerEntity>> {

    public PlayerEntityRendererMixin(EntityRendererFactory.Context ctx, PlayerEntityModel<AbstractClientPlayerEntity> model, float shadowRadius) {
        super(ctx, model, shadowRadius);
    }


    @Inject(method = "<init>", at = @At("TAIL"))
    public void extendedcombat$armorSleeves(EntityRendererFactory.Context ctx, boolean slim, CallbackInfo ci) {
        this.addFeature(new SleeveFeatureRenderer<>(this,
                new ArmorEntityModel<>(ctx.getPart(slim ? ModEntityModelLayers.PLAYER_SLIM_SLEEVES : ModEntityModelLayers.PLAYER_SLEEVES))));
        this.addFeature(new ModElytraFeatureRenderer<>(this,
                ctx.getModelLoader()));
    }

    @Inject(method = "setModelPose", at = @At("HEAD"))
    private void setModelPose(AbstractClientPlayerEntity player, CallbackInfo ci) {
        PlayerEntityModel<AbstractClientPlayerEntity> playerEntityModel = this.getModel();
        ItemStack stack = player.getEquippedStack(EquipmentSlot.CHEST);
        if (stack.isIn(ModItemTags.SLEEVED_ARMOR) && ModConfig.showArmorSleeves) {
            playerEntityModel.setVisible(true);
            playerEntityModel.hat.visible = false;
            playerEntityModel.jacket.visible = false;
            playerEntityModel.leftPants.visible = player.isPartVisible(PlayerModelPart.LEFT_PANTS_LEG);
            playerEntityModel.rightPants.visible = player.isPartVisible(PlayerModelPart.RIGHT_PANTS_LEG);
            playerEntityModel.leftSleeve.visible = false;
            playerEntityModel.rightSleeve.visible = false;
        }
    }
}
