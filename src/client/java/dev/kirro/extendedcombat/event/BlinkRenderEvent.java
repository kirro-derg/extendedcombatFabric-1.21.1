package dev.kirro.extendedcombat.event;

import com.mojang.blaze3d.systems.RenderSystem;
import dev.kirro.ExtendedCombat;
import dev.kirro.extendedcombat.entity.components.ModEntityComponents;
import net.fabricmc.fabric.api.client.rendering.v1.HudRenderCallback;
import net.minecraft.client.MinecraftClient;
import net.minecraft.client.gui.DrawContext;
import net.minecraft.client.render.RenderTickCounter;
import net.minecraft.util.Identifier;

public class BlinkRenderEvent implements HudRenderCallback {
    private static final Identifier BACKGROUND_TEXTURE = Identifier.of(ExtendedCombat.MOD_ID, "hud/blink_background");
    private static final Identifier PROGRESS_TEXTURE = Identifier.of(ExtendedCombat.MOD_ID, "hud/blink_progress");

    @Override
    public void onHudRender(DrawContext drawContext, RenderTickCounter tickCounter) {
        ModEntityComponents.BLINK.maybeGet(MinecraftClient.getInstance().cameraEntity).ifPresent(blinkBehavior -> {
            if (blinkBehavior.hasBlink() && blinkBehavior.getCooldown() > 0) {
                RenderSystem.enableBlend();
                int x = drawContext.getScaledWindowWidth() / 2 - 14, y = drawContext.getScaledWindowHeight() / 2 - 7;
                drawContext.drawGuiTexture(PROGRESS_TEXTURE, x, y, 6, 15);
                if (blinkBehavior.getCooldown() < blinkBehavior.getLastCooldown()) {

                    drawContext.drawGuiTexture(BACKGROUND_TEXTURE, 6, 15, 0, 0, x, y, 6, (int) ((blinkBehavior.getCooldown() / (float) blinkBehavior.getLastCooldown()) * 15));
                } else {
                    drawContext.drawGuiTexture(BACKGROUND_TEXTURE, x, y, 6 ,15);
                }
                drawContext.setShaderColor(1, 1, 1, 1);
                RenderSystem.disableBlend();
            }
        });
    }
}
