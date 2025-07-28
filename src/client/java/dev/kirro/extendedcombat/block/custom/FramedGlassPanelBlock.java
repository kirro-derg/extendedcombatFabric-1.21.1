package dev.kirro.extendedcombat.block.custom;

import com.mojang.serialization.MapCodec;
import net.minecraft.block.*;
import net.minecraft.entity.LivingEntity;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.fluid.FluidState;
import net.minecraft.fluid.Fluids;
import net.minecraft.item.ItemPlacementContext;
import net.minecraft.item.ItemStack;
import net.minecraft.state.StateManager;
import net.minecraft.state.property.BooleanProperty;
import net.minecraft.state.property.DirectionProperty;
import net.minecraft.state.property.Properties;
import net.minecraft.util.Util;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.Direction;
import net.minecraft.util.shape.VoxelShape;
import net.minecraft.util.shape.VoxelShapes;
import net.minecraft.world.BlockView;
import net.minecraft.world.World;
import net.minecraft.world.WorldAccess;
import net.minecraft.world.WorldView;

import java.util.EnumMap;
import java.util.Map;

public class FramedGlassPanelBlock extends Block implements Waterloggable {

    public static final BooleanProperty WATERLOGGED = Properties.WATERLOGGED;
    public static final DirectionProperty FACING = Properties.FACING;

    private static final Map<Direction, VoxelShape> BASE = new EnumMap<>(Direction.class);
    private static final Map<Direction, VoxelShape> SPECIAL = new EnumMap<>(Direction.class);

    static {
        BASE.put(Direction.NORTH, VoxelShapes.cuboid(0.0f, 0.0f, 0.0f, 1.0f, 1.0f, 1.0f / 16.0f));
        BASE.put(Direction.SOUTH, VoxelShapes.cuboid(0.0f, 0.0f, 15.0f / 16.0f, 1.0f, 1.0f, 1.0f));
        BASE.put(Direction.EAST, VoxelShapes.cuboid(15.0f / 16.0f, 0.0f, 0.0f, 1.0f, 1.0f, 1.0f));
        BASE.put(Direction.WEST, VoxelShapes.cuboid(0.0f, 0.0f, 0.0f, 1.0f / 16.0f, 1.0f, 1.0f));
        BASE.put(Direction.UP, VoxelShapes.cuboid(0.0f, 15.0f / 16.0f, 0.0f, 1.0f, 1.0f, 1.0f));
        BASE.put(Direction.DOWN, VoxelShapes.cuboid(0.0f, 0.0f, 0.0f, 1.0f, 1.0f / 16.0f, 1.0f));

        SPECIAL.put(Direction.NORTH, VoxelShapes.cuboid(0.0f, 0.0f, 0.0f, 1.0f, 1.0f, 8.0f / 16.0f));
        SPECIAL.put(Direction.SOUTH, VoxelShapes.cuboid(0.0f, 0.0f, 8.0 / 16.0f, 1.0f, 1.0f, 1.0f));
        SPECIAL.put(Direction.EAST, VoxelShapes.cuboid(8.0f / 16.0f, 0.0f, 0.0f, 1.0f, 1.0f, 1.0f));
        SPECIAL.put(Direction.WEST, VoxelShapes.cuboid(0.0f, 0.0f, 0.0f, 8.0f / 16.0f, 1.0f, 1.0f));
        SPECIAL.put(Direction.UP, VoxelShapes.cuboid(0.0f, 8.0f / 16.0f, 0.0f, 1.0f, 1.0f, 1.0f));
        SPECIAL.put(Direction.DOWN, VoxelShapes.cuboid(0.0f, 0.0f, 0.0f, 1.0f, 8.0f / 16.0f, 1.0f));
    }


    public FramedGlassPanelBlock(Settings settings) {
        super(settings);
        setDefaultState(getDefaultState().with(FACING, Direction.NORTH)
                .with(WATERLOGGED, false));
    }

    @Override
    protected void appendProperties(StateManager.Builder<Block, BlockState> builder) {
        builder.add(FACING, WATERLOGGED);
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
        Direction side = ctx.getSide().getOpposite();
        BlockPos pos = ctx.getBlockPos();
        World world = ctx.getWorld();
        PlayerEntity player = ctx.getPlayer();
        BlockState clickedState = world.getBlockState(pos.offset(side));
        boolean sneaking = player != null && player.isSneaking();

        if (sneaking && !player.isCreative() && world.getBlockState(pos).isOf(Blocks.WATER) && world.getFluidState(pos).isOf(Fluids.WATER)) {
            return this.getDefaultState().with(WATERLOGGED, true).with(FACING, side);
        } else {
            if (!sneaking && clickedState.getBlock() instanceof FramedGlassPanelBlock && !clickedState.get(FACING).getAxis().equals(side.getAxis())) {
                side = clickedState.get(FACING);
            }
            FluidState state = world.getFluidState(pos);
            return this.getDefaultState()
                    .with(WATERLOGGED, state.getFluid() == Fluids.WATER)
                    .with(FACING, side);
        }
    }

    @Override
    public boolean isSideInvisible(BlockState state, BlockState adjacentState, Direction direction) {
        if (adjacentState.isOf(this)) {
            Direction adjacentFacing = adjacentState.get(FACING);
            Direction currentFacing = state.get(FACING);
            return adjacentFacing == direction && currentFacing == direction.getOpposite()
                    || adjacentFacing == direction.getOpposite() && currentFacing == direction
                    || adjacentFacing == currentFacing;
        }
        return super.isSideInvisible(state, adjacentState, direction);
    }

    @Override
    public boolean hasSidedTransparency(BlockState state) {
        return false;
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
}
