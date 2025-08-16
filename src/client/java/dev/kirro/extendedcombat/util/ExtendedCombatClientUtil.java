package dev.kirro.extendedcombat.util;

import dev.kirro.ExtendedcombatClient;
import dev.kirro.ModConfig;
import net.minecraft.client.MinecraftClient;
import net.minecraft.client.option.KeyBinding;
import net.minecraft.entity.Entity;
import net.minecraft.entity.EquipmentSlot;
import net.minecraft.entity.player.PlayerEntity;

import java.util.HashSet;
import java.util.List;
import java.util.Set;
import java.util.UUID;

public class ExtendedCombatClientUtil {
    public static final Set<KeyBinding> MOVEMENT = new HashSet<>();
    public static final Set<UUID> blinkingPlayers = new HashSet<>();
    public static final Set<UUID> crawlingPlayers = new HashSet<>();
    public static final Set<UUID> sitting_players = new HashSet<>();
    public static final Set<UUID> synced_sitting_players = new HashSet<>();

    public static boolean allowDuplicateKeybinds(KeyBinding keyBinding) {
        if (keyBinding == null) {
            return false;
        }
        return switch (ModConfig.allowDuplicateKeybinds) {
            case NONE -> false;
            case MOVEMENT -> MOVEMENT.contains(keyBinding);
            case ALL -> true;
        };
    }

    public static boolean shouldAddParticles(Entity entity) {
        return MinecraftClient.getInstance().gameRenderer.getCamera().isThirdPerson() || entity != MinecraftClient.getInstance().getCameraEntity();
    }

    public static void setCrawling(UUID playerId, boolean crawling) {
        if (crawling) crawlingPlayers.add(playerId);
        else crawlingPlayers.remove(playerId);
    }

    public static void setBlinking(UUID playerId, boolean blinking, int duration) {
        if (blinking && duration > 0) blinkingPlayers.add(playerId);
        else blinkingPlayers.remove(playerId);
    }

    public static void setSitting(UUID playerId, boolean sitting) {
        if (sitting) sitting_players.add(playerId);
        else sitting_players.remove(playerId);
    }

    public static boolean shouldHideArmor(Entity entity) {
        return blinkingPlayers.contains(entity.getUuid());
    }
}
