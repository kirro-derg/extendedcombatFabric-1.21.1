package dev.kirro.extendedcombat.enchantment.payload;

import dev.kirro.ExtendedCombat;
import dev.kirro.extendedcombat.behavior.enchantment.AirJumpBehavior;
import dev.kirro.extendedcombat.entity.components.ModEntityComponents;
import net.fabricmc.fabric.api.client.networking.v1.ClientPlayNetworking;
import net.fabricmc.fabric.api.networking.v1.PlayerLookup;
import net.fabricmc.fabric.api.networking.v1.ServerPlayNetworking;
import net.minecraft.network.PacketByteBuf;
import net.minecraft.network.codec.PacketCodec;
import net.minecraft.network.packet.CustomPayload;
import net.minecraft.util.Identifier;

public record AirJumpPayload() implements CustomPayload {
    public static final CustomPayload.Id<AirJumpPayload> ID = new Id<>(Identifier.of(ExtendedCombat.MOD_ID, "air_jump"));
    public static final PacketCodec<PacketByteBuf, AirJumpPayload> CODEC = PacketCodec.unit(new AirJumpPayload());

    @Override
    public Id<? extends CustomPayload> getId() {
        return ID;
    }

    public static void send() {
        ClientPlayNetworking.send(new AirJumpPayload());
    }

    public static class Reciever implements ServerPlayNetworking.PlayPayloadHandler<AirJumpPayload> {
        @Override
        public void receive(AirJumpPayload payload, ServerPlayNetworking.Context context) {
            AirJumpBehavior airJump = ModEntityComponents.AIR_JUMP.get(context.player());
            if (airJump.getCanUse() && airJump.canUse()) {
                airJump.use();
                PlayerLookup.tracking(context.player()).forEach(foundPlayer -> AirJumpParticlePayload.send(foundPlayer, context.player().getId()));
            }
        }
    }
}
