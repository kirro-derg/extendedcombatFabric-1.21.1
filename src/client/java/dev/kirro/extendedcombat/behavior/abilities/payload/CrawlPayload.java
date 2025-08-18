package dev.kirro.extendedcombat.behavior.abilities.payload;

import dev.kirro.ExtendedCombat;
import dev.kirro.extendedcombat.behavior.abilities.CrawlBehavior;
import dev.kirro.extendedcombat.enchantment.payload.BlinkPayload;
import dev.kirro.extendedcombat.entity.components.ModEntityComponents;
import dev.kirro.extendedcombat.util.ExtendedCombatClientUtil;
import net.fabricmc.fabric.api.client.networking.v1.ClientPlayNetworking;
import net.fabricmc.fabric.api.networking.v1.PlayerLookup;
import net.fabricmc.fabric.api.networking.v1.ServerPlayNetworking;
import net.minecraft.network.PacketByteBuf;
import net.minecraft.network.codec.PacketCodec;
import net.minecraft.network.codec.PacketCodecs;
import net.minecraft.network.packet.CustomPayload;
import net.minecraft.util.Identifier;

public record CrawlPayload() implements CustomPayload {
    public static final Id<CrawlPayload> ID = new Id<>(Identifier.of(ExtendedCombat.MOD_ID, "crawl"));
    public static final PacketCodec<PacketByteBuf, CrawlPayload> CODEC = PacketCodec.unit(new CrawlPayload());

    @Override
    public Id<? extends CustomPayload> getId() {
        return ID;
    }

    public static void send() {
        ClientPlayNetworking.send(new CrawlPayload());
    }

    public static class Reciever implements ServerPlayNetworking.PlayPayloadHandler<CrawlPayload> {
        @Override
        public void receive(CrawlPayload payload, ServerPlayNetworking.Context context) {
            CrawlBehavior crawl = ModEntityComponents.CRAWL.get(context.player());
            if (crawl.isCrawling()) {
                crawl.use();

                CrawlSyncPayload.broadcast(context.player(), crawl.isCrawling());
                ExtendedCombatClientUtil.setCrawling(context.player().getUuid(), crawl.isCrawling());

                PlayerLookup.tracking(context.player()).forEach(foundplayer -> CrawlSyncPayload.send(foundplayer, context.player().getId(), crawl.isCrawling()));
            }
        }
    }
}
