package dev.kirro.extendedcombat.item.payload;

import dev.kirro.ExtendedCombat;
import dev.kirro.extendedcombat.behavior.item.HideWoolHoodBehavior;
import dev.kirro.extendedcombat.enchantment.payload.AirJumpPayload;
import dev.kirro.extendedcombat.entity.components.ModEntityComponents;
import net.fabricmc.fabric.api.client.networking.v1.ClientPlayNetworking;
import net.fabricmc.fabric.api.networking.v1.PlayerLookup;
import net.fabricmc.fabric.api.networking.v1.ServerPlayNetworking;
import net.minecraft.network.PacketByteBuf;
import net.minecraft.network.codec.PacketCodec;
import net.minecraft.network.packet.CustomPayload;
import net.minecraft.util.Identifier;

public record HideHoodClientPayload() implements CustomPayload {
    public static final CustomPayload.Id<HideHoodClientPayload> ID = new Id<>(Identifier.of(ExtendedCombat.MOD_ID, "hide_hood"));
    public static final PacketCodec<PacketByteBuf, HideHoodClientPayload> CODEC = PacketCodec.unit(new HideHoodClientPayload());

    @Override
    public Id<? extends CustomPayload> getId() {
        return ID;
    }

    public static void send() {
        ClientPlayNetworking.send(new AirJumpPayload());
    }

    public static class Reciever implements ServerPlayNetworking.PlayPayloadHandler<HideHoodClientPayload> {
        @Override
        public void receive(HideHoodClientPayload payload, ServerPlayNetworking.Context context) {
            HideWoolHoodBehavior hood = ModEntityComponents.HIDE_HOOD.get(context.player());
            if (hood.isHoodUsed()) hood.useHood();
            else if (hood.isMaskUsed()) hood.useMask();
            PlayerLookup.tracking(context.player()).forEach(foundPlayer -> HideHoodServerPayload.send(foundPlayer, context.player().getId()));
        }
    }
}
