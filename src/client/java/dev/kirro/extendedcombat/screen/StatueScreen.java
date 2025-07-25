package dev.kirro.extendedcombat.screen;

import dev.kirro.ExtendedCombat;
import dev.kirro.extendedcombat.entity.custom.StatueEntity;
import dev.kirro.extendedcombat.screen.widget.PoseSlider;
import dev.kirro.extendedcombat.screen.widget.ScrollBar;
import net.minecraft.client.gui.DrawContext;
import net.minecraft.client.gui.screen.Screen;
import net.minecraft.client.gui.widget.ButtonWidget;
import net.minecraft.client.gui.widget.SliderWidget;
import net.minecraft.client.toast.SystemToast;
import net.minecraft.text.Text;
import net.minecraft.util.Identifier;
import net.minecraft.util.math.EulerAngle;

/*public class StatueScreen extends Screen {
    private static final Identifier TEXTURE = Identifier.of(ExtendedCombat.MOD_ID, "textures/gui/statue_editor.png");

    private final StatueEntity statue;
    private PoseSlider headPitchSlider, headYawSlider;
    private PoseSlider leftArmSlider, rightArmSlider;
    private ButtonWidget resetButton;
    private ScrollBar xScroll, yScroll, zScroll;

    public StatueScreen(StatueEntity statue) {
        super(Text.literal("Pose Editor"));
        this.statue = statue;
    }

    @Override
    protected void init() {
        int centerX = this.width / 2;
        int centerY = this.height / 2;

        this.headPitchSlider = new PoseSlider(centerX - 90, centerY - 60, 180, 20, Text.literal("head_pitch"), -90, 90, statue.getHeadRotation(), value -> {
            statue.setHeadRotation(value);
        });

        this.headYawSlider = new PoseSlider(centerX - 90, centerY - 30, 180, 20, Text.literal("head_yaw"), -90, 90, statue.getHeadYaw(), statue::setHeadYaw);

        this.resetButton = ButtonWidget.builder(Text.literal("reset"), button -> resetPose()).dimensions(centerX - 40, centerY + 60, 80, 20).build();

        this.addDrawableChild(headPitchSlider);
        this.addDrawableChild(headYawSlider);
        this.addDrawableChild(resetButton);
    }

    private void resetPose() {
        EulerAngle current = statue.getHeadRotation();
        EulerAngle currentLA = statue.getLeftArmRotation();
        EulerAngle currentRA = statue.getRightArmRotation();
        statue.setHeadRotation(new EulerAngle(current.getRoll(), current.getPitch(), current.getYaw()));
        statue.setHeadYaw(0);
        statue.setLeftArmRotation(new EulerAngle(currentLA.getRoll(), currentLA.getPitch(), currentLA.getYaw()));
        statue.setRightArmRotation(new EulerAngle(currentRA.getRoll(), currentRA.getPitch(), currentRA.getYaw()));
        headPitchSlider.setValue(0);
        headYawSlider.setValue(0);
    }

    @Override
    public void render(DrawContext context, int mouseX, int mouseY, float delta) {
        this.renderBackground(context, mouseX, mouseY, delta);
        context.drawCenteredTextWithShadow(this.textRenderer, this.title, this.width / 2, 20, 0xFFFFFF);
        super.render(context, mouseX, mouseY, delta);
    }
}*/
