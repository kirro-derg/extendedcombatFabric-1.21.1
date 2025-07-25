package dev.kirro.extendedcombat.screen.widget;

import com.mojang.blaze3d.systems.RenderSystem;
import net.minecraft.client.MinecraftClient;
import net.minecraft.client.Mouse;
import net.minecraft.client.gui.DrawContext;
import net.minecraft.client.gui.screen.narration.NarrationMessageBuilder;
import net.minecraft.client.gui.widget.ClickableWidget;
import net.minecraft.client.render.GameRenderer;
import net.minecraft.text.Text;
import net.minecraft.util.Identifier;
import net.minecraft.util.math.MathHelper;

public class ScrollBar extends ClickableWidget {

    private double scroll;
    private int maxScroll;
    private boolean dragging;
    private final Identifier texture;

    public ScrollBar(int x, int y, int width, int height, Identifier texture) {
        super(x, y, width, height, Text.literal(""));
        this.texture = texture;
        this.scroll = 0;
        this.maxScroll = 100;
        this.dragging = false;
    }

    @Override
    public void onClick(double mouseX, double mouseY) {
        this.dragging = true;
    }

    @Override
    public boolean mouseReleased(double mouseX, double mouseY, int button) {
        this.dragging = false;
        return true;
    }

    @Override
    public boolean mouseScrolled(double mouseX, double mouseY, double horizontalAmount, double verticalAmount) {
        scroll = MathHelper.clamp(scroll - verticalAmount * 10, 0, maxScroll);
        return true;
    }

    public void tick() {
        if (dragging) {
            Mouse mouse = MinecraftClient.getInstance().mouse;

            double relativeY = mouse.getY() / (double) MinecraftClient.getInstance().getWindow().getScaledHeight();
            int mouseY = (int) (relativeY * MinecraftClient.getInstance().getWindow().getHeight());
            scroll = MathHelper.clamp((mouseY - this.getY()) / (double)(this.height - 15) * maxScroll, 0, maxScroll);
        }
    }

    @Override
    public void renderWidget(DrawContext context, int mouseX, int mouseY, float delta) {
        RenderSystem.setShader(GameRenderer::getPositionTexProgram);
        RenderSystem.setShaderTexture(0, texture);
        RenderSystem.setShaderColor(1f, 1f, 1f, this.alpha);

        float scrollRatio = (float) (scroll / maxScroll);
        int barY = this.getY() + Math.round(scrollRatio * (this.height - 15));

        context.drawTexture(texture, this.getX() + 1, barY, 0, 0, 6, 15, 16, 16);
    }

    public void setMaxScroll(int value) {
        this.maxScroll = Math.max(0, value);
    }

    public double getScroll() {
        return scroll;
    }

    public float getScrollPercentage() {
        return maxScroll > 0 ? (float)(scroll/ maxScroll) : 0f;
    }

    @Override
    protected void appendClickableNarrations(NarrationMessageBuilder builder) {

    }
}
