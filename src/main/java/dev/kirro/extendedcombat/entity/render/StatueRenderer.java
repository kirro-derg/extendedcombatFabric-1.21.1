package dev.kirro.extendedcombat.entity.render;

import dev.kirro.ExtendedCombat;
import dev.kirro.extendedcombat.entity.client.BasicStatueModel;
import dev.kirro.extendedcombat.entity.client.StatueModel;
import dev.kirro.extendedcombat.entity.custom.ModEntityModelLayers;
import dev.kirro.extendedcombat.entity.custom.StatueEntity;
import dev.kirro.extendedcombat.entity.render.model.CloakFeatureRenderer;
import dev.kirro.extendedcombat.entity.render.model.SleeveFeatureRenderer;
import dev.kirro.extendedcombat.entity.render.model.ModElytraFeatureRenderer;
import net.minecraft.client.render.RenderLayer;
import net.minecraft.client.render.VertexConsumerProvider;
import net.minecraft.client.render.entity.EntityRendererFactory;
import net.minecraft.client.render.entity.LivingEntityRenderer;
import net.minecraft.client.render.entity.feature.ArmorFeatureRenderer;
import net.minecraft.client.render.entity.feature.ElytraFeatureRenderer;
import net.minecraft.client.render.entity.feature.HeldItemFeatureRenderer;
import net.minecraft.client.util.math.MatrixStack;
import net.minecraft.util.Identifier;
import net.minecraft.util.math.MathHelper;
import net.minecraft.util.math.RotationAxis;
import org.jetbrains.annotations.Nullable;

public class StatueRenderer extends LivingEntityRenderer<StatueEntity, BasicStatueModel> {

    public StatueRenderer(EntityRendererFactory.Context context) {
        super(context, new StatueModel(context.getPart(ModEntityModelLayers.STATUE)), 0.0f);
        this.addFeature(new ArmorFeatureRenderer<>(this,
                new BasicStatueModel(context.getPart(ModEntityModelLayers.STATUE_INNER_ARMOR)),
                new BasicStatueModel(context.getPart(ModEntityModelLayers.STATUE_OUTER_ARMOR)),
                context.getModelManager()
        ));
        this.addFeature(new SleeveFeatureRenderer<>(this,
                new BasicStatueModel(context.getPart(ModEntityModelLayers.PLAYER_SLEEVES))));
        this.addFeature(new CloakFeatureRenderer<>(this,
                new BasicStatueModel(context.getPart(ModEntityModelLayers.CLOAK_INNER)),
                new BasicStatueModel(context.getPart(ModEntityModelLayers.CLOAK_OUTER))));
        this.addFeature(new HeldItemFeatureRenderer<>(this, context.getHeldItemRenderer()));
        this.addFeature(new ElytraFeatureRenderer<>(this, context.getModelLoader()));
        this.addFeature(new ModElytraFeatureRenderer<>(this, context.getModelLoader()));
    }

    @Override
    public Identifier getTexture(StatueEntity entity) {
        return Identifier.of(ExtendedCombat.MOD_ID, "textures/entity/statue/statue.png");
    }


    protected void setupTransforms(StatueEntity statueEntity, MatrixStack matrixStack, float tickDelta, float yaw, float animationProgress, float scale) {
        matrixStack.multiply(RotationAxis.POSITIVE_Y.rotationDegrees(0.0f - statueEntity.getYaw()));
        float lastHitTime = (float)(statueEntity.getWorld().getTime() - statueEntity.lastHitTime) + animationProgress;
        if (lastHitTime < 5.0F) {
            matrixStack.multiply(RotationAxis.POSITIVE_Y.rotationDegrees(MathHelper.sin(lastHitTime / 1.5F * (float) Math.PI) * 3.0F));
        }
    }

    protected boolean hasLabel(StatueEntity statueEntity) {
        double d = this.dispatcher.getSquaredDistanceToCamera(statueEntity);
        float f = statueEntity.isInSneakingPose() ? 32.0F : 64.0F;
        return d >= f * f ? false : statueEntity.isCustomNameVisible();
    }

    @Override
    public void render(StatueEntity livingEntity, float f, float g, MatrixStack matrixStack,
                       VertexConsumerProvider vertexConsumerProvider, int i) {
        super.render(livingEntity, f, g, matrixStack, vertexConsumerProvider, i);
    }

    @Nullable
    protected RenderLayer getRenderLayer(StatueEntity statueEntity, boolean showBody, boolean Translucent, boolean showOutline) {
        if (!statueEntity.isMarker()) {
            return super.getRenderLayer(statueEntity, showBody, Translucent, showOutline);
        } else {
            Identifier identifier = this.getTexture(statueEntity);
            if (Translucent) {
                return RenderLayer.getEntityTranslucent(identifier, false);
            } else {
                return showBody ? RenderLayer.getEntityCutoutNoCull(identifier, false) : null;
            }
        }
    }
}
