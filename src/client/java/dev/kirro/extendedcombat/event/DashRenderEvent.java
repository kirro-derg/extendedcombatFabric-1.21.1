package dev.kirro.extendedcombat.event;

import com.mojang.blaze3d.systems.RenderSystem;
import dev.kirro.ExtendedCombat;
import dev.kirro.extendedcombat.entity.components.ModEntityComponents;
import net.fabricmc.fabric.api.client.rendering.v1.HudRenderCallback;
import net.minecraft.client.MinecraftClient;
import net.minecraft.client.gui.DrawContext;
import net.minecraft.client.render.RenderTickCounter;
import net.minecraft.util.Identifier;

public class DashRenderEvent implements HudRenderCallback {
    private static final Identifier BACKGROUND_TEXTURE = Identifier.of(ExtendedCombat.MOD_ID, "hud/dash_background");
    private static final Identifier PROGRESS_TEXTURE = Identifier.of(ExtendedCombat.MOD_ID, "hud/dash_progress");

    @Override
    public void onHudRender(DrawContext drawContext, RenderTickCounter tickCounter) {
        ModEntityComponents.DASH.maybeGet(MinecraftClient.getInstance().cameraEntity).ifPresent(dashBehavior -> {
            if (dashBehavior.hasDash() && dashBehavior.getCooldown() > 0) {
                RenderSystem.enableBlend();
                int x = drawContext.getScaledWindowWidth() / 2 - 7, y = drawContext.getScaledWindowHeight() / 2 - 14;
                drawContext.drawGuiTexture(BACKGROUND_TEXTURE, x, y, 15, 6);
                if (dashBehavior.getCooldown() < dashBehavior.getLastCooldown()) {
                    drawContext.drawGuiTexture(PROGRESS_TEXTURE, 15, 6, 0, 0, x, y, (int) (16 - (dashBehavior.getCooldown() / (float) dashBehavior.getLastCooldown()) * 15), 6);
                }
            }
        });
    }
}
