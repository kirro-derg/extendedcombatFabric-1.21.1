package dev.kirro.extendedcombat.enchantment.payload;

import dev.kirro.ExtendedCombat;
import dev.kirro.extendedcombat.behavior.enchantment.DashBehavior;
import dev.kirro.extendedcombat.entity.components.ModEntityComponents;
import net.fabricmc.fabric.api.client.networking.v1.ClientPlayNetworking;
import net.fabricmc.fabric.api.networking.v1.PlayerLookup;
import net.fabricmc.fabric.api.networking.v1.ServerPlayNetworking;
import net.minecraft.network.PacketByteBuf;
import net.minecraft.network.codec.PacketCodec;
import net.minecraft.network.codec.PacketCodecs;
import net.minecraft.network.packet.CustomPayload;
import net.minecraft.util.Identifier;
import net.minecraft.util.math.Vec3d;

public record DashPayload() implements CustomPayload {
    public static final Id<DashPayload> ID = new Id<>(Identifier.of(ExtendedCombat.MOD_ID, "dash"));
    public static final PacketCodec<PacketByteBuf, DashPayload> CODEC = PacketCodec.unit(new DashPayload());

    @Override
    public Id<? extends CustomPayload> getId() {
        return ID;
    }

    public static void send() {
        ClientPlayNetworking.send(new DashPayload());
    }

    public static class Reciever implements ServerPlayNetworking.PlayPayloadHandler<DashPayload> {
        @Override
        public void receive(DashPayload payload, ServerPlayNetworking.Context context) {
            DashBehavior dash = ModEntityComponents.DASH.get(context.player());
            if (dash.hasDash() && dash.canUse()) {
                dash.use();
                PlayerLookup.tracking(context.player()).forEach(foundPlayer -> DashParticlePayload.send(foundPlayer, context.player().getId()));
            }
        }
    }
}
