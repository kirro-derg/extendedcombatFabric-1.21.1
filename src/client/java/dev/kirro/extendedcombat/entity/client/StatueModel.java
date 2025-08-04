package dev.kirro.extendedcombat.entity.client;

import com.google.common.collect.ImmutableList;
import com.google.common.collect.Iterables;
import dev.kirro.extendedcombat.entity.custom.StatueEntity;
import net.minecraft.client.model.*;
import net.minecraft.client.render.entity.model.BipedEntityModel;
import net.minecraft.client.render.entity.model.EntityModelPartNames;
import net.minecraft.client.util.math.MatrixStack;
import net.minecraft.util.Arm;

public class StatueModel extends BasicStatueModel {

	private final ModelPart body;
	public final ModelPart head;
	private final ModelPart rightArm;
	private final ModelPart leftArm;
	private final ModelPart basePlate;
	public StatueModel(ModelPart root) {
		super(root);
		this.body = root.getChild(EntityModelPartNames.BODY);
		this.head = root.getChild(EntityModelPartNames.HEAD);
		this.rightArm = root.getChild(EntityModelPartNames.RIGHT_ARM);
		this.leftArm = root.getChild(EntityModelPartNames.LEFT_ARM);
		this.basePlate = root.getChild("basePlate");
	}
	public static TexturedModelData getTexturedModelData() {
		ModelData modelData = BipedEntityModel.getModelData(Dilation.NONE, 0.0f);
		ModelPartData modelPartData = modelData.getRoot();

		modelPartData.addChild(EntityModelPartNames.BODY, ModelPartBuilder.create().uv(0, 16).cuboid(-4.0F, 0.0F, -2.0F, 8.0F, 12.0F, 4.0F), ModelTransform.NONE);

		modelPartData.addChild(EntityModelPartNames.HEAD, ModelPartBuilder.create().uv(0, 0).cuboid(-4.0F, -9.0F, -4.0F, 8.0F, 8.0F, 8.0F), ModelTransform.pivot(0.0F, 1.0F, 0.0F));

		modelPartData.addChild(EntityModelPartNames.RIGHT_ARM, ModelPartBuilder.create().uv(40, 16).cuboid(-3.0F, -2.0F, -2.0F, 4.0F, 12.0F, 4.0F), ModelTransform.pivot(-5.0F, 2.0F, 0.0F));

		modelPartData.addChild(EntityModelPartNames.LEFT_ARM, ModelPartBuilder.create().uv(24, 16).mirrored().cuboid(-1.0F, -2.0F, -2.0F, 4.0F, 12.0F, 4.0F), ModelTransform.pivot(5.0F, 2.0F, 0.0F));

		modelPartData.addChild("basePlate", ModelPartBuilder.create().uv(0, 48).cuboid(-6.0F, 10.0F, -6.0F, 12.0F, 2.0F, 12.0F, new Dilation(0.0F)), ModelTransform.pivot(0.0f, 12.0f, 0.0f));
		return TexturedModelData.of(modelData, 64, 64);
	}

	@Override
	public void animateModel(StatueEntity statueEntity, float f, float g, float h) {
		this.basePlate.pitch = 0.0F;
		this.basePlate.yaw = 0.0F;
		this.basePlate.roll = 0.0F;
	}

	@Override
	public void setAngles(StatueEntity statueEntity, float f, float g, float h, float i, float j) {
		super.setAngles(statueEntity, f, g, h, i, j);
		this.leftArm.visible = statueEntity.shouldShowArms();
		this.rightArm.visible = statueEntity.shouldShowArms();
		this.basePlate.visible = !statueEntity.shouldHideBasePlate();
		this.body.pitch = (float) (Math.PI / 180.0) * statueEntity.getBodyRotation().getPitch();
		this.body.yaw = (float) (Math.PI / 180.0) * statueEntity.getBodyRotation().getYaw();
		this.body.roll = (float) (Math.PI / 180.0) * statueEntity.getBodyRotation().getRoll();
		this.head.pitch = (float) (Math.PI / 180.0) * statueEntity.getHeadRotation().getPitch();
		this.head.pitch = (float) (Math.PI / 180.0) * statueEntity.getHeadRotation().getYaw();
		this.head.pitch = (float) (Math.PI / 180.0) * statueEntity.getHeadRotation().getRoll();
	}

	@Override
	protected Iterable<ModelPart> getBodyParts() {
		return Iterables.concat(super.getBodyParts(), ImmutableList.of(this.head, this.body, this.basePlate, this.rightArm, this.leftArm));
	}

	@Override
	public void setArmAngle(Arm arm, MatrixStack matrices) {
		ModelPart modelPart = this.getArm(arm);
		boolean bl = modelPart.visible;
		modelPart.visible = true;
		super.setArmAngle(arm, matrices);
		modelPart.visible = bl;
	}
}