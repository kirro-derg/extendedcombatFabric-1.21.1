package dev.kirro.extendedcombat.entity.client;

import dev.kirro.extendedcombat.entity.custom.StatueEntity;
import net.fabricmc.api.EnvType;
import net.fabricmc.api.Environment;
import net.minecraft.client.model.*;
import net.minecraft.client.render.entity.model.BipedEntityModel;
import net.minecraft.client.render.entity.model.EntityModelPartNames;

@Environment(EnvType.CLIENT)
public class BasicStatueModel extends BipedEntityModel<StatueEntity> {

    public BasicStatueModel(ModelPart root) {
        super(root);
    }

    public static TexturedModelData getTexturedModelData(Dilation dilation) {
        ModelData modelData = BipedEntityModel.getModelData(dilation, 0.0F);
        ModelPartData modelPartData = modelData.getRoot();
        modelPartData.addChild(
                EntityModelPartNames.HEAD,
                ModelPartBuilder.create().uv(0, 0).cuboid(-4.0F, -9.0F, -4.0F, 8.0F, 8.0F, 8.0F, dilation),
                ModelTransform.pivot(0.0F, 1.0F, 0.0F)
        );
        modelPartData.addChild(
                EntityModelPartNames.HAT,
                ModelPartBuilder.create().uv(32, 0).cuboid(-4.0F, -9.0F, -4.0F, 8.0F, 8.0F, 8.0F, dilation.add(0.5F)),
                ModelTransform.pivot(0.0F, 1.0F, 0.0F)
        );
        modelPartData.addChild(
                EntityModelPartNames.RIGHT_LEG,
                ModelPartBuilder.create().uv(0, 16).cuboid(-2.0F, 0.0F, -2.0F, 4.0F, 12.0F, 4.0F, dilation.add(-0.1F)),
                ModelTransform.pivot(-1.9F, 11.0F, 0.0F)
        );
        modelPartData.addChild(
                EntityModelPartNames.LEFT_LEG,
                ModelPartBuilder.create().uv(0, 16).mirrored().cuboid(-2.0F, 0.0F, -2.0F, 4.0F, 12.0F, 4.0F, dilation.add(-0.1F)),
                ModelTransform.pivot(1.9F, 11.0F, 0.0F)
        );
        return TexturedModelData.of(modelData, 64, 32);
    }

    public void setAngles(StatueEntity statueEntity, float f, float g, float h, float i, float j) {
        this.head.pitch = (float) (Math.PI / 180.0) * statueEntity.getHeadRotation().getPitch();
        this.head.yaw = (float) (Math.PI / 180.0) * statueEntity.getHeadRotation().getYaw();
        this.head.roll = (float) (Math.PI / 180.0) * statueEntity.getHeadRotation().getRoll();
        this.body.pitch = (float) (Math.PI / 180.0) * statueEntity.getBodyRotation().getPitch();
        this.body.yaw = (float) (Math.PI / 180.0) * statueEntity.getBodyRotation().getYaw();
        this.body.roll = (float) (Math.PI / 180.0) * statueEntity.getBodyRotation().getRoll();
        this.leftArm.pitch = (float) (Math.PI / 180.0) * statueEntity.getLeftArmRotation().getPitch();
        this.leftArm.yaw = (float) (Math.PI / 180.0) * statueEntity.getLeftArmRotation().getYaw();
        this.leftArm.roll = (float) (Math.PI / 180.0) * statueEntity.getLeftArmRotation().getRoll();
        this.rightArm.pitch = (float) (Math.PI / 180.0) * statueEntity.getRightArmRotation().getPitch();
        this.rightArm.yaw = (float) (Math.PI / 180.0) * statueEntity.getRightArmRotation().getYaw();
        this.rightArm.roll = (float) (Math.PI / 180.0) * statueEntity.getRightArmRotation().getRoll();
        this.hat.copyTransform(this.head);
    }
}
