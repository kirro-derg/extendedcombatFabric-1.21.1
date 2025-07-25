package dev.kirro.extendedcombat.block.entity;

import net.minecraft.block.BlockState;
import net.minecraft.block.entity.BlockEntity;
import net.minecraft.block.entity.BlockEntityType;
import net.minecraft.util.math.BlockPos;

public class FlatBlockEntity extends BlockEntity {
    public FlatBlockEntity(BlockPos pos, BlockState state) {
        super(ModBlockEntityTypes.FLAT_BLOCK_ENTITY, pos, state);
    }
}
