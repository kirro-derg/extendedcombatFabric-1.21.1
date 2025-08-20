package dev.kirro.extendedcombat.behavior.abilities.payload;

import dev.kirro.ExtendedCombat;
import dev.kirro.extendedcombat.behavior.abilities.CrawlBehavior;
import dev.kirro.extendedcombat.entity.components.ModEntityComponents;
import dev.kirro.extendedcombat.util.ExtendedCombatClientUtil;
import net.fabricmc.fabric.api.client.networking.v1.ClientPlayNetworking;
import net.fabricmc.fabric.api.networking.v1.PlayerLookup;
import net.fabricmc.fabric.api.networking.v1.ServerPlayNetworking;
import net.minecraft.client.MinecraftClient;
import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityPose;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.network.PacketByteBuf;
import net.minecraft.network.codec.PacketCodec;
import net.minecraft.network.codec.PacketCodecs;
import net.minecraft.network.packet.CustomPayload;
import net.minecraft.server.network.ServerPlayerEntity;
import net.minecraft.util.Identifier;

public record CrawlSyncPayload(int entityId, boolean crawling) implements CustomPayload {
    public static final Id<CrawlSyncPayload> ID = new Id<>(Identifier.of(ExtendedCombat.MOD_ID, "crawl_sync"));
    public static final PacketCodec<PacketByteBuf, CrawlSyncPayload> CODEC = PacketCodec.tuple(PacketCodecs.VAR_INT,
            CrawlSyncPayload::entityId, PacketCodecs.BOOL, CrawlSyncPayload::crawling, CrawlSyncPayload::new);

    @Override
    public Id<? extends CustomPayload> getId() {
        return ID;
    }

    public static void send(ServerPlayerEntity target, int entityId, boolean crawling) {
        CustomPayload payload = new CrawlSyncPayload(entityId, crawling);
        ServerPlayNetworking.send(target, payload);
    }

    public static void handle(CrawlSyncPayload payload, ClientPlayNetworking.Context context) {
        MinecraftClient client = MinecraftClient.getInstance();
        client.execute(() -> {
            Entity entity = client.world.getEntityById(payload.entityId());
            if (entity instanceof PlayerEntity player) {
                CrawlBehavior crawl = ModEntityComponents.CRAWL.get(player);
                crawl.setCrawling(payload.crawling());
                player.setPose(payload.crawling() ? EntityPose.SWIMMING : EntityPose.STANDING);
                player.setSwimming(payload.crawling());
            }
        });
    }

    public static void setPlayerPose(Entity entity) {
        CrawlBehavior crawl = ModEntityComponents.CRAWL.get(entity);
        if (crawl.isCrawling()) {
            entity.setPose(EntityPose.SWIMMING);
        }
    }

    public static void broadcast(ServerPlayerEntity source, boolean crawling) {
        int entityId = source.getId();
        for (ServerPlayerEntity tracking : PlayerLookup.tracking(source)) {
            send(tracking, entityId, crawling);
        }
    }

    public static class Reciever implements ClientPlayNetworking.PlayPayloadHandler<CrawlSyncPayload> {
        @Override
        public void receive(CrawlSyncPayload payload, ClientPlayNetworking.Context context) {
            Entity entity = context.player().getWorld().getEntityById(payload.entityId());
            if (entity != null) {
                setPlayerPose(entity);
                handle(payload, context);
            }
        }
    }
}
