package dev.kirro.extendedcombat.item.custom;

import net.minecraft.entity.LivingEntity;
import net.minecraft.item.*;
import net.minecraft.server.network.ServerPlayerEntity;
import net.minecraft.server.world.ServerWorld;
import net.minecraft.sound.SoundCategory;
import net.minecraft.sound.SoundEvents;

public class GreatswordItem extends SwordItem implements ScaledItem {
    public GreatswordItem(ToolMaterial toolMaterial, Settings settings) {
        super(toolMaterial, settings);
    }

    @Override
    public boolean postHit(ItemStack stack, LivingEntity target, LivingEntity attacker) {
        if (attacker instanceof ServerPlayerEntity player) {
            ServerWorld world = (ServerWorld) attacker.getWorld();
            if (stack.getItem() instanceof ToolItem item) {
                ToolMaterial material = item.getMaterial();
                if (material == ToolMaterials.WOOD) {
                    world.playSound(null, player.getX(), player.getY(), player.getZ(), SoundEvents.BLOCK_WOOD_BREAK, SoundCategory.PLAYERS, 1.0f, (float) (1.0f + player.getRandom().nextGaussian() / 10f));
                } else if (material == ToolMaterials.STONE) {
                    world.playSound(null, player.getX(), player.getY(), player.getZ(), SoundEvents.BLOCK_STONE_BREAK, SoundCategory.PLAYERS, 1.0f, (float) (1.0f + player.getRandom().nextGaussian() / 10f));
                } else if (material == ToolMaterials.IRON || material == ToolMaterials.GOLD || material == ToolMaterials.DIAMOND) {
                    world.playSound(null, player.getX(), player.getY(), player.getZ(), SoundEvents.BLOCK_METAL_BREAK, SoundCategory.PLAYERS, 1.0f, (float) (1.0f + player.getRandom().nextGaussian() / 10f));
                } else if (material == ToolMaterials.NETHERITE || material == ModToolMaterials.NETHER_STEEL) {
                    world.playSound(null, player.getX(), player.getY(), player.getZ(), SoundEvents.BLOCK_NETHERITE_BLOCK_BREAK, SoundCategory.PLAYERS, 1.0f, (float) (1.0f + player.getRandom().nextGaussian() / 10f));
                } else if (material == ModToolMaterials.ECHO_STEEL) {
                    world.playSound(null, player.getX(), player.getY(), player.getZ(), SoundEvents.BLOCK_SCULK_BREAK, SoundCategory.PLAYERS, 1.0f, (float) (1.0f + player.getRandom().nextGaussian() / 10f));
                }
            }
        }
        return true;
    }
}
