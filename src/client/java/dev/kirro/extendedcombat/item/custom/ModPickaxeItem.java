package dev.kirro.extendedcombat.item.custom;

import dev.kirro.extendedcombat.block.ModBlocks;
import dev.kirro.extendedcombat.item.ModItems;
import net.fabricmc.fabric.api.item.v1.DefaultItemComponentEvents;
import net.minecraft.block.BlockState;
import net.minecraft.component.DataComponentTypes;
import net.minecraft.component.type.ToolComponent;
import net.minecraft.entity.EquipmentSlot;
import net.minecraft.entity.LivingEntity;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.item.PickaxeItem;
import net.minecraft.item.ToolMaterial;
import net.minecraft.registry.tag.BlockTags;
import net.minecraft.util.ActionResult;
import net.minecraft.util.Hand;
import net.minecraft.util.hit.BlockHitResult;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;

import java.util.List;
import java.util.function.Predicate;

public class ModPickaxeItem extends PickaxeItem {
    public ModPickaxeItem(ToolMaterial material, Item.Settings settings) {
        super(material, settings);
        DefaultItemComponentEvents.MODIFY.register(context -> context.modify(
                Predicate.isEqual(ModItems.NETHER_STEEL_PICKAXE),
                (((builder, item) -> builder.add(DataComponentTypes.TOOL, createToolComponent())))
        ));
    }

    private static ToolComponent createToolComponent() {
        return new ToolComponent(
                List.of(ToolComponent.Rule.ofAlwaysDropping(List.of(ModBlocks.NETHER_STEEL_BLOCK, ModBlocks.WARDING_STONE), 25.0f),
                        ToolComponent.Rule.ofAlwaysDropping(BlockTags.PICKAXE_MINEABLE, 25.0f),
                        ToolComponent.Rule.of(BlockTags.PICKAXE_MINEABLE, 25.0f)), 1.0f, 2
        );
    }
}
