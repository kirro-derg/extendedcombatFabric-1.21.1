package dev.kirro.extendedcombat.entity.client;

import dev.kirro.ExtendedCombat;
import dev.kirro.extendedcombat.entity.custom.ModEntityModelLayers;
import dev.kirro.extendedcombat.entity.custom.StatueEntity;
import net.minecraft.client.render.RenderLayer;
import net.minecraft.client.render.VertexConsumerProvider;
import net.minecraft.client.render.entity.EntityRendererFactory;
import net.minecraft.client.util.math.MatrixStack;
import net.minecraft.util.Identifier;
import net.minecraft.util.math.MathHelper;
import net.minecraft.util.math.RotationAxis;
import org.jetbrains.annotations.Nullable;

public class StatueRenderer extends AbstractRenderer<StatueEntity> {

    public StatueRenderer(EntityRendererFactory.Context context) {
        super(context, new StatueModel(context.getPart(ModEntityModelLayers.STATUE)),
                new BasicStatueModel<>(context.getPart(ModEntityModelLayers.STATUE_INNER_ARMOR)),
                new BasicStatueModel<>(context.getPart(ModEntityModelLayers.STATUE_OUTER_ARMOR)));
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
