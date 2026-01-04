package dev.kirro.extendedcombat.block;

import dev.kirro.ExtendedCombat;
import dev.kirro.extendedcombat.block.custom.*;
import net.fabricmc.fabric.api.itemgroup.v1.ItemGroupEvents;
import net.minecraft.block.*;
import net.minecraft.block.piston.PistonBehavior;
import net.minecraft.item.BlockItem;
import net.minecraft.item.Item;
import net.minecraft.item.ItemGroups;
import net.minecraft.item.ItemStack;
import net.minecraft.item.tooltip.TooltipType;
import net.minecraft.registry.Registries;
import net.minecraft.registry.Registry;
import net.minecraft.sound.BlockSoundGroup;
import net.minecraft.text.Text;
import net.minecraft.util.Identifier;

import java.util.List;

public interface ModBlocks {

    Block NETHER_STEEL_BLOCK = registerBlockWithItem("nether_steel_block",
            new Block(AbstractBlock.Settings.create().strength(100f, 1200f)
                    .requiresTool().sounds(BlockSoundGroup.NETHERITE)));
    Block ECHO_STEEL_BLOCK = registerBlockWithItem("echo_steel_block",
            new Block(AbstractBlock.Settings.create().strength(100f, 1200f)
                    .requiresTool().sounds(BlockSoundGroup.SCULK_CATALYST)));

    Block WARDING_STONE = registerBlockWithItem("warding_stone",
            new WardingStoneBlock(AbstractBlock.Settings.create().strength(6f)
                    .sounds(BlockSoundGroup.DEEPSLATE_BRICKS).nonOpaque().luminance(state -> 4)) {
                @Override
                public void appendTooltip(ItemStack stack, Item.TooltipContext context, List<Text> tooltip, TooltipType options) {
                    tooltip.add(Text.translatable("tooltip.extendedcombat.warding_stone_ln1"));
                    tooltip.add(Text.translatable("tooltip.extendedcombat.warding_stone_ln2"));
                    tooltip.add(Text.translatable("tooltip.extendedcombat.warding_stone_ln3"));
                    super.appendTooltip(stack, context, tooltip, options);
                }
            });

    Block FLAT_BLOCK = registerBlockWithItem("flat_block",
            new FlatBlock(AbstractBlock.Settings.copy(Blocks.STONE).nonOpaque().luminance(state -> 15)));
    Block FRAMED_GLASS_PANEL = registerBlockWithItem("framed_glass_panel",
            new FramedGlassPanelBlock(AbstractBlock.Settings.copy(Blocks.GLASS).nonOpaque()));
    Block _STAINED_GLASS_PANEL = registerBlockWithItem("_stained_glass_panel",
            new FramedGlassPanelBlock(AbstractBlock.Settings.copy(Blocks.GLASS).nonOpaque()));
    Block SEAT = registerBlockWithItem("seat_block",
            new SeatBlock(AbstractBlock.Settings.create().strength(2f).nonOpaque()
                    .sounds(BlockSoundGroup.WOOD)));

    Block BLACK_APPLE_BUSH = registerBlockWithoutItem("black_apple_bush",
            new BlackAppleBushBlock(AbstractBlock.Settings.create().strength(0.05f).sounds(BlockSoundGroup.SWEET_BERRY_BUSH).ticksRandomly().noCollision().pistonBehavior(PistonBehavior.DESTROY)));

    private static Block registerBlockWithItem(String name, Block block) {
        registerBlockItem(name, block);
        return Registry.register(Registries.BLOCK, ExtendedCombat.id(name), block);
    }

    private static Block registerBlockWithoutItem(String name, Block block) {
        return Registry.register(Registries.BLOCK, ExtendedCombat.id(name), block);
    }

    private static void registerBlockItem(String name, Block block) {
        Registry.register(Registries.ITEM, Identifier.of(ExtendedCombat.MOD_ID, name),
                new BlockItem(block, new Item.Settings()));
    }

    static void registerModBlocks() {
        ExtendedCombat.LOGGER.info("Registering blocks for" + ExtendedCombat.MOD_ID);

        ItemGroupEvents.modifyEntriesEvent(ItemGroups.BUILDING_BLOCKS).register(entries -> {
            entries.add(NETHER_STEEL_BLOCK);
            entries.add(ECHO_STEEL_BLOCK);
            entries.add(FRAMED_GLASS_PANEL);
            entries.add(FLAT_BLOCK);
            entries.add(SEAT);
        });
        ItemGroupEvents.modifyEntriesEvent(ItemGroups.FUNCTIONAL).register(entries -> {
            entries.add(WARDING_STONE);
            entries.add(SEAT);
        });
    }
}
