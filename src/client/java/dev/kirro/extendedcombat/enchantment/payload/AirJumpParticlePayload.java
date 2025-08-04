package dev.kirro.extendedcombat.enchantment.payload;

import dev.kirro.ExtendedCombat;
import dev.kirro.extendedcombat.util.ExtendedCombatClientUtil;
import net.fabricmc.fabric.api.client.networking.v1.ClientPlayNetworking;
import net.fabricmc.fabric.api.networking.v1.ServerPlayNetworking;
import net.minecraft.entity.Entity;
import net.minecraft.network.PacketByteBuf;
import net.minecraft.network.codec.PacketCodec;
import net.minecraft.network.codec.PacketCodecs;
import net.minecraft.network.packet.CustomPayload;
import net.minecraft.particle.ParticleTypes;
import net.minecraft.server.network.ServerPlayerEntity;
import net.minecraft.util.Identifier;

public record AirJumpParticlePayload(int entityId) implements CustomPayload {
    public static final CustomPayload.Id<AirJumpParticlePayload> ID = new Id<>(Identifier.of(ExtendedCombat.MOD_ID, "air_jump_particles"));
    public static final PacketCodec<PacketByteBuf, AirJumpParticlePayload> CODEC = PacketCodec.tuple(PacketCodecs.VAR_INT, AirJumpParticlePayload::entityId, AirJumpParticlePayload::new);

    @Override
    public Id<? extends CustomPayload> getId() {
        return ID;
    }

    public static void send(ServerPlayerEntity player, int id) {
        ServerPlayNetworking.send(player, new AirJumpParticlePayload(id));
    }

    public static void addParticles(Entity entity) {
        if (ExtendedCombatClientUtil.shouldAddParticles(entity)) {
            for (int i = 0; i < 64; i++) {
                entity.getWorld().addParticle(ParticleTypes.TRIAL_SPAWNER_DETECTION_OMINOUS, entity.getParticleX(1), entity.getY(), entity.getParticleZ(1), 0, 0, 0);
            }
        }
    }

    public static class Reciever implements ClientPlayNetworking.PlayPayloadHandler<AirJumpParticlePayload> {
        @Override
        public void receive(AirJumpParticlePayload payload, ClientPlayNetworking.Context context) {
            Entity entity = context.player().getWorld().getEntityById(payload.entityId());
            if (entity != null) {
                addParticles(entity);
            }
        }
    }
}
