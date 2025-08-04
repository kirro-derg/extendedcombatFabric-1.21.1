package dev.kirro.extendedcombat.event;

import com.mojang.blaze3d.systems.RenderSystem;
import dev.kirro.ExtendedCombat;
import dev.kirro.extendedcombat.behavior.enchantment.AirJumpBehavior;
import dev.kirro.extendedcombat.entity.components.ModEntityComponents;
import net.fabricmc.fabric.api.client.rendering.v1.HudRenderCallback;
import net.minecraft.client.MinecraftClient;
import net.minecraft.client.gui.DrawContext;
import net.minecraft.client.render.RenderTickCounter;
import net.minecraft.util.Identifier;

public class AirJumpRenderEvent implements HudRenderCallback {
    private static final Identifier[] TEXTURES = new Identifier[11];

    static {
        for (int i = 0; i < TEXTURES.length; i++) {
            TEXTURES[i] = Identifier.of(ExtendedCombat.MOD_ID, "hud/air_jump_" + i);
        }
    }

    @Override
    public void onHudRender(DrawContext drawContext, RenderTickCounter tickCounter) {
        ModEntityComponents.AIR_JUMP.maybeGet(MinecraftClient.getInstance().cameraEntity).ifPresent(airJump -> {
            if (airJump.getCanUse()) {
                int jumpAmount = airJump.getJumpAmount();
                if (jumpAmount < airJump.getMaxJumps()) {
                    RenderSystem.enableBlend();
                    Identifier first = getTexture(jumpAmount + 1);
                    Identifier second = getTexture(jumpAmount);
                    int x = drawContext.getScaledWindowWidth() / 2 - 7, y = drawContext.getScaledWindowHeight() / 2 + 14;
                    if (airJump.getCooldown() < airJump.getLastCooldown()) {
                        drawContext.drawGuiTexture(first, x, y, 15, 6);
                        drawContext.drawGuiTexture(second, 15, 6, 0, 0, x, y, (int) ((airJump.getCooldown() / (float) airJump.getLastCooldown()) * 15), 6);
                    } else {
                        drawContext.drawGuiTexture(second, x, y, 15, 6);
                    }
                    drawContext.setShaderColor(1, 1, 1, 1);
                    RenderSystem.disableBlend();
                }
            }
        });
    }

    private static Identifier getTexture(int i) {
        i %= TEXTURES.length;
        if (i < 0) {
            i += TEXTURES.length;
        }
        return TEXTURES[i];
    }
}
