package dev.kirro.extendedcombat.item.custom;

import dev.kirro.extendedcombat.block.ModBlocks;
import dev.kirro.extendedcombat.item.ModItems;
import net.fabricmc.fabric.api.item.v1.DefaultItemComponentEvents;
import net.minecraft.block.BlockState;
import net.minecraft.block.Blocks;
import net.minecraft.component.DataComponentTypes;
import net.minecraft.component.type.AttributeModifierSlot;
import net.minecraft.component.type.AttributeModifiersComponent;
import net.minecraft.component.type.ToolComponent;
import net.minecraft.entity.attribute.EntityAttributeModifier;
import net.minecraft.entity.attribute.EntityAttributes;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.item.ToolMaterial;
import net.minecraft.registry.tag.BlockTags;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;

import java.util.List;
import java.util.function.Predicate;

public class AxeSwordItem extends GreatswordItem {
    public AxeSwordItem(ToolMaterial material, Settings settings) {
        super(material, settings);
        DefaultItemComponentEvents.MODIFY.register(context -> context.modify(
                Predicate.isEqual(this),
                ((builder, item) -> builder.add(DataComponentTypes.TOOL, createToolComponent()))
        ));

    }

    private static ToolComponent createToolComponent() {
        return new ToolComponent(
                List.of(ToolComponent.Rule.ofAlwaysDropping(List.of(Blocks.COBWEB, ModBlocks.SEAT), 6.5f),
                        ToolComponent.Rule.ofAlwaysDropping(BlockTags.AXE_MINEABLE, 6.5f),
                        ToolComponent.Rule.of(BlockTags.SWORD_EFFICIENT, 6.5f),
                        ToolComponent.Rule.of(BlockTags.HOE_MINEABLE, 6.5f),
                        ToolComponent.Rule.of(BlockTags.AXE_MINEABLE, 6.5f),
                        ToolComponent.Rule.of(BlockTags.WOOL, 15f)), 1.0f, 2
        );
    }

    public static AttributeModifiersComponent createAttributeModifiers(ToolMaterial material, int baseAttackDamage, float attackSpeed) {
        return AttributeModifiersComponent.builder()
                .add(
                        EntityAttributes.GENERIC_ATTACK_DAMAGE,
                        new EntityAttributeModifier(BASE_ATTACK_DAMAGE_MODIFIER_ID, baseAttackDamage + material.getAttackDamage(), EntityAttributeModifier.Operation.ADD_VALUE),
                        AttributeModifierSlot.MAINHAND
                )
                .add(
                        EntityAttributes.GENERIC_ATTACK_SPEED,
                        new EntityAttributeModifier(BASE_ATTACK_SPEED_MODIFIER_ID, attackSpeed, EntityAttributeModifier.Operation.ADD_VALUE),
                        AttributeModifierSlot.MAINHAND
                )
                .build();
    }
}
