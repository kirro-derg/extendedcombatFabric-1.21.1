package dev.kirro.extendedcombat.screen.widget;

import net.minecraft.client.gui.widget.SliderWidget;
import net.minecraft.text.Text;
import net.minecraft.util.math.MathHelper;

import java.util.function.Consumer;

public class PoseSlider extends SliderWidget {
    private final Consumer<Float> onChange;
    private final float min;
    private final float max;

    private float normalize(float val) {
        return MathHelper.clamp((val - min) / (max - min), 0.0f, 1.0f);
    }

    public PoseSlider(int x, int y, int width, int height, Text text, float min, float max, float value, Consumer<Float> onChange) {
        super(x, y, width, height, text, (value - min) / (max - min));
        this.onChange = onChange;
        this.min = min;
        this.max = max;
        updateMessage();
    }

    @Override
    protected void updateMessage() {
        setMessage(Text.literal(String.format("%s: %.1f", getMessage().getString(), getValue())));
    }

    @Override
    protected void applyValue() {
        onChange.accept(getValue());
    }

    public void setValue(float val) {
        this.value = MathHelper.clamp((val - min) / (max - min), 0.0f, 1.0f);
        updateMessage();
        applyValue();
    }

    public float getValue() {
        return (float) (min + (value * (max - min)));
    }


}
