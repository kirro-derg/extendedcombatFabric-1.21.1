package dev.kirro.extendedcombat.enchantment.payload;

import dev.kirro.ExtendedCombat;
import dev.kirro.extendedcombat.behavior.enchantment.BlinkBehavior;
import dev.kirro.extendedcombat.behavior.enchantment.DashBehavior;
import dev.kirro.extendedcombat.entity.components.ModEntityComponents;
import dev.kirro.extendedcombat.util.ExtendedCombatClientUtil;
import net.fabricmc.fabric.api.client.networking.v1.ClientPlayNetworking;
import net.fabricmc.fabric.api.networking.v1.PacketByteBufs;
import net.fabricmc.fabric.api.networking.v1.PacketSender;
import net.fabricmc.fabric.api.networking.v1.PlayerLookup;
import net.fabricmc.fabric.api.networking.v1.ServerPlayNetworking;
import net.minecraft.client.MinecraftClient;
import net.minecraft.client.network.ClientPlayNetworkHandler;
import net.minecraft.entity.Entity;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.network.PacketByteBuf;
import net.minecraft.network.codec.PacketCodec;
import net.minecraft.network.packet.CustomPayload;
import net.minecraft.particle.ParticleTypes;
import net.minecraft.server.network.ServerPlayerEntity;
import net.minecraft.util.Identifier;

import java.util.UUID;

public record BlinkPayload() implements CustomPayload {
    public static final Id<BlinkPayload> ID = new Id<>(Identifier.of(ExtendedCombat.MOD_ID, "blink"));
    public static final PacketCodec<PacketByteBuf, BlinkPayload> CODEC = PacketCodec.unit(new BlinkPayload());

    @Override
    public Id<? extends CustomPayload> getId() {
        return ID;
    }

    public static void send() {
        ClientPlayNetworking.send(new BlinkPayload());
    }

    public static class Reciever implements ServerPlayNetworking.PlayPayloadHandler<BlinkPayload> {
        @Override
        public void receive(BlinkPayload payload, ServerPlayNetworking.Context context) {
            BlinkBehavior blink = ModEntityComponents.BLINK.get(context.player());
            if (blink.hasBlink() && blink.canUse()) {
                blink.use();

                BlinkSyncPayload.broadcast(context.player(), blink.isInvisible(), blink.getDuration());
                ExtendedCombatClientUtil.setBlinking(context.player().getUuid(), blink.isInvisible(), blink.getDuration());

                PlayerLookup.tracking(context.player()).forEach(foundPlayer -> BlinkParticlePayload.send(foundPlayer, context.player().getId()));
            }
        }
    }
}
