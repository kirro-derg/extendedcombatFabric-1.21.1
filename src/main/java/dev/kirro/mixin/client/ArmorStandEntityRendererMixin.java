package dev.kirro.mixin.client;

import dev.kirro.extendedcombat.entity.custom.ModEntityModelLayers;
import dev.kirro.extendedcombat.entity.render.model.CloakFeatureRenderer;
import dev.kirro.extendedcombat.entity.render.model.SleeveFeatureRenderer;
import dev.kirro.extendedcombat.entity.render.model.ModElytraFeatureRenderer;
import net.minecraft.client.render.entity.ArmorStandEntityRenderer;
import net.minecraft.client.render.entity.EntityRendererFactory;
import net.minecraft.client.render.entity.LivingEntityRenderer;
import net.minecraft.client.render.entity.model.ArmorEntityModel;
import net.minecraft.client.render.entity.model.ArmorStandArmorEntityModel;
import net.minecraft.entity.decoration.ArmorStandEntity;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

@Mixin(ArmorStandEntityRenderer.class)
public abstract class ArmorStandEntityRendererMixin extends LivingEntityRenderer<ArmorStandEntity, ArmorStandArmorEntityModel> {
    public ArmorStandEntityRendererMixin(EntityRendererFactory.Context ctx, ArmorStandArmorEntityModel model, float shadowRadius) {
        super(ctx, model, shadowRadius);
    }

    @Inject(method = "<init>", at = @At("TAIL"))
    public void extendedcombat$armorSleeves(EntityRendererFactory.Context context, CallbackInfo ci) {
        this.addFeature(new SleeveFeatureRenderer<>(this,
                new ArmorEntityModel<>(context.getPart(ModEntityModelLayers.PLAYER_SLEEVES))));
        this.addFeature(new ModElytraFeatureRenderer<>(this,
                context.getModelLoader()));
        this.addFeature(new CloakFeatureRenderer<>(this,
                new ArmorEntityModel<>(context.getPart(ModEntityModelLayers.CLOAK_INNER)),
                new ArmorEntityModel<>(context.getPart(ModEntityModelLayers.CLOAK_OUTER)),
                context.getModelManager()));
    }
}
