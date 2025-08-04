package dev.kirro.extendedcombat.enchantment.payload;

import dev.kirro.ExtendedCombat;
import dev.kirro.extendedcombat.behavior.enchantment.BlinkBehavior;
import dev.kirro.extendedcombat.entity.components.ModEntityComponents;
import net.fabricmc.fabric.api.client.networking.v1.ClientPlayNetworking;
import net.fabricmc.fabric.api.networking.v1.PlayerLookup;
import net.fabricmc.fabric.api.networking.v1.ServerPlayNetworking;
import net.minecraft.client.MinecraftClient;
import net.minecraft.entity.Entity;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.network.PacketByteBuf;
import net.minecraft.network.codec.PacketCodec;
import net.minecraft.network.codec.PacketCodecs;
import net.minecraft.network.packet.CustomPayload;
import net.minecraft.server.network.ServerPlayerEntity;
import net.minecraft.util.Identifier;

public record BlinkSyncPayload(int entityId, boolean invisible, int duration) implements CustomPayload {
    public static final Id<BlinkSyncPayload> ID = new Id<>(Identifier.of(ExtendedCombat.MOD_ID, "blink_sync"));
    public static final PacketCodec<PacketByteBuf, BlinkSyncPayload> CODEC = PacketCodec.tuple(
            PacketCodecs.VAR_INT, BlinkSyncPayload::entityId,
            PacketCodecs.BOOL, BlinkSyncPayload::invisible,
            PacketCodecs.VAR_INT, BlinkSyncPayload::duration,
            BlinkSyncPayload::new
    );

    @Override
    public Id<? extends CustomPayload> getId() {
        return ID;
    }

    public static void send(ServerPlayerEntity target, int entityId, boolean invisible, int duration) {
        CustomPayload payload = new BlinkSyncPayload(entityId, invisible, duration);
        ServerPlayNetworking.send(target, payload);
    }

    public static void handle(BlinkSyncPayload payload, ClientPlayNetworking.Context context) {
        MinecraftClient client = MinecraftClient.getInstance();
        client.execute( () -> {
            Entity entity = client.world.getEntityById(payload.entityId());
            if (entity instanceof PlayerEntity player) {
                BlinkBehavior blink = ModEntityComponents.BLINK.get(player);
                blink.setInvisible(payload.invisible());
                blink.setDuration(payload.duration());
            }
        });
    }

    public static void broadcast(ServerPlayerEntity source, boolean invisible, int duration) {
        int entityId = source.getId();
        for (ServerPlayerEntity tracking : PlayerLookup.tracking(source)) {
            send(tracking, entityId, invisible, duration);
        }
    }
}
