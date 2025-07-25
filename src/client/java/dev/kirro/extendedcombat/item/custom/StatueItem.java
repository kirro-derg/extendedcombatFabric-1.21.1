package dev.kirro.extendedcombat.item.custom;

import dev.kirro.extendedcombat.entity.ModEntities;
import dev.kirro.extendedcombat.entity.custom.StatueEntity;
import net.minecraft.entity.EntityType;
import net.minecraft.entity.SpawnReason;
import net.minecraft.entity.decoration.ArmorStandEntity;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.item.Item;
import net.minecraft.item.ItemPlacementContext;
import net.minecraft.item.ItemStack;
import net.minecraft.item.ItemUsageContext;
import net.minecraft.server.world.ServerWorld;
import net.minecraft.sound.SoundCategory;
import net.minecraft.sound.SoundEvents;
import net.minecraft.util.ActionResult;
import net.minecraft.util.math.*;
import net.minecraft.world.World;
import net.minecraft.world.event.GameEvent;

import java.util.function.Consumer;

public class StatueItem extends Item {
    public StatueItem(Item.Settings settings) {
        super(settings);
    }

    /*@Override
    public ActionResult useOnBlock(ItemUsageContext context) {
        Direction direction = context.getSide();
        if (direction == Direction.DOWN) {
            return ActionResult.FAIL;
        } else {
            World world = context.getWorld();
            ItemPlacementContext itemPlacementContext = new ItemPlacementContext(context);
            BlockPos blockPos = itemPlacementContext.getBlockPos();
            ItemStack itemStack = context.getStack();
            Vec3d vec3d = Vec3d.ofBottomCenter(blockPos);
            Box box = ModEntities.STATUE.getDimensions().getBoxAt(vec3d.getX(), vec3d.getY(), vec3d.getZ());
            if (world.isSpaceEmpty(null, box) && world.getOtherEntities(null, box).isEmpty()) {
                if (world instanceof ServerWorld serverWorld) {
                    Consumer<StatueEntity> consumer = EntityType.copier(serverWorld, itemStack, context.getPlayer());
                    StatueEntity statueEntity = ModEntities.STATUE.create(serverWorld, consumer, blockPos, SpawnReason.SPAWN_EGG, true, true);
                    if (statueEntity == null) {
                        return ActionResult.FAIL;
                    }

                    float f = MathHelper.floor((MathHelper.wrapDegrees(context.getPlayerYaw() - 180.0F) + 22.5F) / 45.0F) * 45.0F;
                    statueEntity.refreshPositionAndAngles(statueEntity.getX(), statueEntity.getY(), statueEntity.getZ(), f, 0.0F);
                    serverWorld.spawnEntityAndPassengers(statueEntity);
                    world.playSound(
                            null, statueEntity.getX(), statueEntity.getY(), statueEntity.getZ(), SoundEvents.ENTITY_ARMOR_STAND_PLACE, SoundCategory.BLOCKS, 0.75F, 0.8F
                    );
                    statueEntity.emitGameEvent(GameEvent.ENTITY_PLACE, context.getPlayer());
                }

                itemStack.decrement(1);
                return ActionResult.success(world.isClient);
            } else {
                return ActionResult.FAIL;
            }
        }
    }*/

    @Override
    public ActionResult useOnBlock(ItemUsageContext context) {
        World world = context.getWorld();
        BlockPos pos = context.getBlockPos();
        float placementYaw = context.getPlayerYaw();
        Direction facing = context.getSide();
        ItemStack itemStack = context.getStack();
        if (world instanceof ServerWorld serverWorld) {
            Consumer<StatueEntity> consumer = EntityType.copier(serverWorld, itemStack, context.getPlayer());
            StatueEntity statueEntity = ModEntities.STATUE.create(serverWorld, consumer, pos, SpawnReason.SPAWN_EGG, true, true);

            if (!world.isClient) {
                StatueEntity statue = new StatueEntity(ModEntities.STATUE, world);
                //statue.setFacingDirection(Direction.fromRotation(placementYaw));
                statue.setYaw(placementYaw);
                statue.setBodyYaw(placementYaw);
                statue.refreshPositionAndAngles(pos.getX() + 0.5, pos.getY() + 1.0, pos.getZ() + 0.5, placementYaw, 0.0f);
                world.spawnEntity(statue);
                world.playSound(
                        null, statueEntity.getX(), statueEntity.getY(), statueEntity.getZ(), SoundEvents.ENTITY_ARMOR_STAND_PLACE, SoundCategory.BLOCKS, 0.75F, 0.8F
                );
            }
        }
        itemStack.decrement(1);
        return ActionResult.SUCCESS;
    }
}
