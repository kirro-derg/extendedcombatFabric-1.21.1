package dev.kirro.extendedcombat.entity.client;

import com.google.common.collect.ImmutableList;
import dev.kirro.ExtendedCombat;
import dev.kirro.extendedcombat.entity.custom.StatueEntity;
import net.minecraft.client.model.*;
import net.minecraft.client.render.VertexConsumer;
import net.minecraft.client.render.entity.model.BipedEntityModel;
import net.minecraft.client.render.entity.model.EntityModelLayer;
import net.minecraft.client.util.math.MatrixStack;
import net.minecraft.util.Identifier;

import java.util.Collections;

public class StatueModel extends BasicStatueModel<StatueEntity> {


	public final ModelPart statue;
	private final ModelPart body;
	private final ModelPart head;
	private final ModelPart rightArm;
	private final ModelPart leftArm;
	private final ModelPart basePlate;
	public StatueModel(ModelPart root) {
		super(root);
        this.statue = root.getChild("statue");
		this.body = this.statue.getChild("body");
		this.head = this.statue.getChild("head");
		this.rightArm = this.statue.getChild("rightArm");
		this.leftArm = this.statue.getChild("leftArm");
		this.basePlate = this.statue.getChild("basePlate");
	}
	public static TexturedModelData getTexturedModelData() {
		ModelData modelData = BipedEntityModel.getModelData(Dilation.NONE, 0.0f);
		ModelPartData modelPartData = modelData.getRoot();
		ModelPartData statue = modelPartData.addChild("statue", ModelPartBuilder.create(), ModelTransform.pivot(0.0F, 24.0F, 0.0F));

		ModelPartData body = statue.addChild("body", ModelPartBuilder.create().uv(0, 16).cuboid(-4.0F, -24.0F, -2.0F, 8.0F, 12.0F, 4.0F), ModelTransform.pivot(0.0F, 0.0F, 0.0F));

		ModelPartData head = statue.addChild("head", ModelPartBuilder.create().uv(0, 0).cuboid(-4.0F, -33.0F, -4.0F, 8.0F, 8.0F, 8.0F), ModelTransform.pivot(0.0F, 1.0F, 0.0F));

		ModelPartData rightArm = statue.addChild("rightArm", ModelPartBuilder.create().uv(32, 0).cuboid(-8.0F, -24.0F, -2.0F, 4.0F, 12.0F, 4.0F), ModelTransform.pivot(0.0F, 0.0F, 0.0F));

		ModelPartData leftArm = statue.addChild("leftArm", ModelPartBuilder.create().uv(24, 16).cuboid(4.0F, -24.0F, -2.0F, 4.0F, 12.0F, 4.0F), ModelTransform.pivot(0.0F, 0.0F, 0.0F));

		ModelPartData basePlate = statue.addChild("basePlate", ModelPartBuilder.create().uv(8, 32).cuboid(-4.0F, -12.0F, -2.0F, 8.0F, 12.0F, 4.0F, new Dilation(0.0F))
				.uv(0, 48).cuboid(-6.0F, -2.0F, -6.0F, 12.0F, 2.0F, 12.0F, new Dilation(0.0F)), ModelTransform.NONE);
		return TexturedModelData.of(modelData, 64, 64);
	}

	/*public void setAngles(StatueEntity statueEntity, float f, float g, float h, float i, float j) {
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

		this.leftLeg.visible = false;
		this.rightLeg.visible = false;
	}*/

	@Override
	public void render(MatrixStack matrices, VertexConsumer vertexConsumer, int light, int overlay, int color) {
		statue.render(matrices, vertexConsumer, light, overlay, color);
	}
}