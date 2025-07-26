package dev.kirro.extendedcombat.entity.client;

import com.google.common.collect.ImmutableList;
import com.google.common.collect.Iterables;
import dev.kirro.extendedcombat.entity.custom.StatueEntity;
import net.minecraft.client.model.*;
import net.minecraft.client.render.VertexConsumer;
import net.minecraft.client.render.entity.model.BipedEntityModel;
import net.minecraft.client.util.math.MatrixStack;
import net.minecraft.util.Arm;
import net.minecraft.util.math.MathHelper;

public class StatueModel extends BasicStatueModel<StatueEntity> {


	public final ModelPart statue;
	private final ModelPart rightArm;
	private final ModelPart leftArm;
	private final ModelPart basePlate;
	public StatueModel(ModelPart root) {
		super(root);
        this.statue = root.getChild("statue");
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

		ModelPartData rightArm = statue.addChild("rightArm", ModelPartBuilder.create().uv(32, 0).cuboid(-12.0F, -23.0F, -6.0F, 4.0F, 12.0F, 4.0F), ModelTransform.pivot(0.0F, 0.0F, 0.0F));

		ModelPartData leftArm = statue.addChild("leftArm", ModelPartBuilder.create().uv(24, 16).cuboid(8.0F, -23.0F, -6.0F, 4.0F, 12.0F, 4.0F), ModelTransform.pivot(0.0F, 0.0F, 0.0F));

		ModelPartData basePlate = statue.addChild("basePlate", ModelPartBuilder.create().uv(8, 32).cuboid(-4.0F, -12.0F, -2.0F, 8.0F, 12.0F, 4.0F, new Dilation(0.0F))
				.uv(0, 48).cuboid(-6.0F, -2.0F, -6.0F, 12.0F, 2.0F, 12.0F, new Dilation(0.0F)), ModelTransform.NONE);
		return TexturedModelData.of(modelData, 64, 64);
	}

	@Override
	public void animateModel(StatueEntity statueEntity, float f, float g, float h) {
		this.basePlate.pitch = 0.0F;
		this.basePlate.yaw = (float) (Math.PI / 180.0) * -MathHelper.lerpAngleDegrees(h, statueEntity.prevYaw, statueEntity.getYaw());
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
	}

	@Override
	protected Iterable<ModelPart> getBodyParts() {
		return Iterables.concat(super.getBodyParts(), ImmutableList.of(this.body, this.basePlate));
	}

	/*@Override
	public void setArmAngle(Arm arm, MatrixStack matrices) {
		ModelPart modelPart = this.getArm(arm);
		boolean bl = modelPart.visible;
		modelPart.visible = true;
		super.setArmAngle(arm, matrices);
		modelPart.visible = bl;
	}*/

	@Override
	public void render(MatrixStack matrices, VertexConsumer vertexConsumer, int light, int overlay, int color) {
		statue.render(matrices, vertexConsumer, light, overlay, color);
	}
}