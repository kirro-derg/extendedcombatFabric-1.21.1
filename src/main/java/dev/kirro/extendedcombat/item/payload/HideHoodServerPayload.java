package dev.kirro.extendedcombat.item.payload;

import dev.kirro.ExtendedCombat;
import net.fabricmc.fabric.api.client.networking.v1.ClientPlayNetworking;
import net.fabricmc.fabric.api.networking.v1.ServerPlayNetworking;
import net.minecraft.network.PacketByteBuf;
import net.minecraft.network.codec.PacketCodec;
import net.minecraft.network.codec.PacketCodecs;
import net.minecraft.network.packet.CustomPayload;
import net.minecraft.server.network.ServerPlayerEntity;
import net.minecraft.util.Identifier;

public record HideHoodServerPayload(int entityId) implements CustomPayload {
    public static final CustomPayload.Id<HideHoodServerPayload> ID = new CustomPayload.Id<>(Identifier.of(ExtendedCombat.MOD_ID, "hide_hood_server"));
    public static final PacketCodec<PacketByteBuf, HideHoodServerPayload> CODEC = PacketCodec.tuple(PacketCodecs.VAR_INT, HideHoodServerPayload::entityId, HideHoodServerPayload::new);

    @Override
    public Id<? extends CustomPayload> getId() {
        return ID;
    }

    public static void send(ServerPlayerEntity player, int id) {
        ServerPlayNetworking.send(player, new HideHoodServerPayload(id));
    }

    public static class Receiver implements ClientPlayNetworking.PlayPayloadHandler<HideHoodServerPayload> {
        @Override
        public void receive(HideHoodServerPayload payload, ClientPlayNetworking.Context context) {
        }
    }
}
