package dev.kirro.extendedcombat.util;

import dev.kirro.ModConfig;
import net.minecraft.client.option.KeyBinding;
import net.minecraft.entity.Entity;
import net.minecraft.entity.player.PlayerEntity;

import java.util.HashSet;
import java.util.Set;
import java.util.UUID;

public class ExtendedCombatClientUtil {
    public static final Set<KeyBinding> MOVEMENT = new HashSet<>();
    public static final Set<UUID> BLINKING_PLAYERS = new HashSet<>();

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

    public static boolean isThirdPerson(Entity entity) {
        return true; //MinecraftClient.getInstance().gameRenderer.getCamera().isThirdPerson() || entity != MinecraftClient.getInstance().getCameraEntity();
    }

    public static void setBlinking(UUID playerId, boolean blinking, int duration) {
        if (blinking && duration > 0) BLINKING_PLAYERS.add(playerId);
        else BLINKING_PLAYERS.remove(playerId);
    }

    public static boolean shouldHideArmor(PlayerEntity entity) {
        return BLINKING_PLAYERS.contains(entity.getUuid());
    }
}
