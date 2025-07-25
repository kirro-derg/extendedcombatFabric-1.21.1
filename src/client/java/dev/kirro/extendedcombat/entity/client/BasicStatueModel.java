package dev.kirro.extendedcombat.entity.client;

import dev.kirro.extendedcombat.entity.custom.StatueEntity;
import net.minecraft.client.model.Dilation;
import net.minecraft.client.model.ModelData;
import net.minecraft.client.model.ModelPart;
import net.minecraft.client.model.TexturedModelData;
import net.minecraft.client.render.entity.model.BipedEntityModel;

public class BasicStatueModel<T extends StatueEntity> extends BipedEntityModel<T> {

    public BasicStatueModel(ModelPart root) {
        super(root);
    }

    public static TexturedModelData getTexturedModelData(Dilation dilation) {
        ModelData modelData = BipedEntityModel.getModelData(dilation, 0.0f);
        return TexturedModelData.of(modelData, 64, 64);
    }

    public void setAngles(StatueEntity statueEntity, float f, float g, float h, float i, float j) {
        this.head.pitch = (float) (Math.PI / 180.0f) * statueEntity.getHeadRotation().getPitch();
        this.head.yaw = (float) (Math.PI / 180.0f) * statueEntity.getHeadRotation().getYaw();
        this.head.roll = (float) (Math.PI / 180.0f) * statueEntity.getHeadRotation().getRoll();
        this.body.pitch = (float) (Math.PI / 180.0f) * statueEntity.getBodyRotation().getPitch();
        this.body.yaw = (float) (Math.PI / 180.0f) * statueEntity.getBodyRotation().getYaw();
        this.body.roll = (float) (Math.PI / 180.0f) * statueEntity.getBodyRotation().getRoll();
        this.leftArm.pitch = (float) (Math.PI / 180.0f) * statueEntity.getLeftArmRotation().getPitch();
        this.leftArm.yaw = (float) (Math.PI / 180.0f) * statueEntity.getLeftArmRotation().getYaw();
        this.leftArm.roll = (float) (Math.PI / 180.0f) * statueEntity.getLeftArmRotation().getRoll();
        this.rightArm.pitch = (float) (Math.PI / 180.0f) * statueEntity.getRightArmRotation().getPitch();
        this.rightArm.yaw = (float) (Math.PI / 180.0f) * statueEntity.getRightArmRotation().getYaw();
        this.rightArm.roll = (float) (Math.PI / 180.0f) * statueEntity.getRightArmRotation().getRoll();
        this.hat.copyTransform(this.head);

        this.leftLeg.visible = true;
        this.rightLeg.visible = true;
    }
}
