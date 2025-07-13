package dev.kirro.extendedcombat.block.custom;

import com.mojang.serialization.MapCodec;
import net.minecraft.block.*;
import net.minecraft.entity.LivingEntity;
import net.minecraft.fluid.FluidState;
import net.minecraft.fluid.Fluids;
import net.minecraft.item.ItemPlacementContext;
import net.minecraft.item.ItemStack;
import net.minecraft.state.StateManager;
import net.minecraft.state.property.BooleanProperty;
import net.minecraft.state.property.Properties;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.Direction;
import net.minecraft.util.shape.VoxelShape;
import net.minecraft.util.shape.VoxelShapes;
import net.minecraft.world.BlockView;
import net.minecraft.world.WorldAccess;

import java.util.EnumMap;
import java.util.Map;

public class FramedGlassPanelBlock extends FacingBlock implements Waterloggable {

    public static final BooleanProperty WATERLOGGED = Properties.WATERLOGGED;

    private static final Map<Direction, VoxelShape> BASE = new EnumMap<>(Direction.class);
    private static final Map<Direction, VoxelShape> SPECIAL = new EnumMap<>(Direction.class);

    static {
        BASE.put(Direction.NORTH, VoxelShapes.cuboid(0.0f, 0.0f, 0.0f, 1.0f, 1.0f, 1.0f / 16.0f));
        BASE.put(Direction.SOUTH, VoxelShapes.cuboid(0.0f, 0.0f, 15.0f / 16.0f, 1.0f, 1.0f, 1.0f));
        BASE.put(Direction.EAST, VoxelShapes.cuboid(15.0f / 16.0f, 0.0f, 0.0f, 1.0f, 1.0f, 1.0f));
        BASE.put(Direction.WEST, VoxelShapes.cuboid(0.0f, 0.0f, 0.0f, 1.0f / 16.0f, 1.0f, 1.0f));
        BASE.put(Direction.UP, VoxelShapes.cuboid(0.0f, 15.0f / 16.0f, 0.0f, 1.0f, 1.0f, 1.0f));
        BASE.put(Direction.DOWN, VoxelShapes.cuboid(0.0f, 0.0f, 0.0f, 1.0f, 1.0f / 16.0f, 1.0f));

        SPECIAL.put(Direction.NORTH, VoxelShapes.cuboid(0.0f, 0.0f, 0.0f, 1.0f, 1.0f, 5.0f / 16.0f));
        SPECIAL.put(Direction.SOUTH, VoxelShapes.cuboid(0.0f, 0.0f, 11.0 / 16.0f, 1.0f, 1.0f, 1.0f));
        SPECIAL.put(Direction.EAST, VoxelShapes.cuboid(11.0f / 16.0f, 0.0f, 0.0f, 1.0f, 1.0f, 1.0f));
        SPECIAL.put(Direction.WEST, VoxelShapes.cuboid(0.0f, 0.0f, 0.0f, 5.0f / 16.0f, 1.0f, 1.0f));
        SPECIAL.put(Direction.UP, VoxelShapes.cuboid(0.0f, 11.0f / 16.0f, 0.0f, 1.0f, 1.0f, 1.0f));
        SPECIAL.put(Direction.DOWN, VoxelShapes.cuboid(0.0f, 0.0f, 0.0f, 1.0f, 5.0f / 16.0f, 1.0f));
    }


    public FramedGlassPanelBlock(Settings settings) {
        super(settings);
        setDefaultState(getDefaultState().with(Properties.FACING, Direction.NORTH)
                .with(WATERLOGGED, false));
    }

    @Override
    protected void appendProperties(StateManager.Builder<Block, BlockState> builder) {
        builder.add(Properties.FACING, WATERLOGGED);
    }

    @Override
    public VoxelShape getOutlineShape(BlockState state, BlockView world, BlockPos pos, ShapeContext ctx) {
        Direction dir = state.get(FACING);

        boolean isHoldingBlock = false;

        if (ctx instanceof EntityShapeContext entityCtx && entityCtx.getEntity() instanceof LivingEntity living) {
            ItemStack main = living.getMainHandStack();
            ItemStack off = living.getOffHandStack();
            isHoldingBlock = main.isOf(this.asItem()) || off.isOf(this.asItem());
        }

        return  isHoldingBlock ? SPECIAL.get(dir) : BASE.get(dir);


    }

    @Override
    public BlockState getPlacementState(ItemPlacementContext ctx) {
        return super.getPlacementState(ctx).with(Properties.FACING, ctx.getPlayerLookDirection())
                .with(WATERLOGGED, ctx.getWorld().getFluidState(ctx.getBlockPos()).isOf(Fluids.WATER));
    }

    @Override
    public FluidState getFluidState(BlockState state) {
        return state.get(WATERLOGGED) ? Fluids.WATER.getStill(false) : super.getFluidState(state);
    }

    @Override
    public BlockState getStateForNeighborUpdate(BlockState state, Direction direction, BlockState neighborState, WorldAccess world, BlockPos pos, BlockPos neighborPos) {
        if (state.get(WATERLOGGED)) {
            world.scheduleFluidTick(pos, Fluids.WATER, Fluids.WATER.getTickRate(world));
        }
        return super.getStateForNeighborUpdate(state, direction, neighborState, world, pos, neighborPos);
    }

    @Override
    protected MapCodec<? extends FacingBlock> getCodec() {
        return null;
    }
}
