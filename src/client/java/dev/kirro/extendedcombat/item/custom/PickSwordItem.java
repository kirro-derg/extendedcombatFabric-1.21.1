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
import net.minecraft.entity.EquipmentSlot;
import net.minecraft.entity.LivingEntity;
import net.minecraft.entity.attribute.EntityAttributeModifier;
import net.minecraft.entity.attribute.EntityAttributes;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.item.*;
import net.minecraft.registry.tag.BlockTags;
import net.minecraft.server.network.ServerPlayerEntity;
import net.minecraft.server.world.ServerWorld;
import net.minecraft.sound.SoundCategory;
import net.minecraft.sound.SoundEvents;
import net.minecraft.util.ActionResult;
import net.minecraft.util.Hand;
import net.minecraft.util.Identifier;
import net.minecraft.util.hit.BlockHitResult;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;

import java.util.List;
import java.util.function.Predicate;

public class PickSwordItem extends GreatswordItem {
    public PickSwordItem(ToolMaterial material, Item.Settings settings) {
        super(material, settings);
        DefaultItemComponentEvents.MODIFY.register(context -> context.modify(
                Predicate.isEqual(ModItems.NETHER_STEEL_GREATSWORD),
                ((builder, item) -> builder.add(DataComponentTypes.TOOL, createToolComponent()))
        ));

    }

    private static ToolComponent createToolComponent() {
        return new ToolComponent(
                List.of(ToolComponent.Rule.ofAlwaysDropping(List.of(Blocks.COBWEB, ModBlocks.NETHER_STEEL_BLOCK, ModBlocks.WARDING_STONE), 6.5f),
                        ToolComponent.Rule.ofAlwaysDropping(BlockTags.PICKAXE_MINEABLE, 6.5f),
                        ToolComponent.Rule.of(BlockTags.SWORD_EFFICIENT, 1.5f),
                        ToolComponent.Rule.of(BlockTags.PICKAXE_MINEABLE, 6.5f),
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

    @Override
    public boolean canMine(BlockState state, World world, BlockPos pos, PlayerEntity miner) {
        return true;
    }

    //@Override
    //public boolean postHit(ItemStack stack, LivingEntity target, LivingEntity attacker) {
    //    if (attacker instanceof ServerPlayerEntity player) {
    //        ServerWorld world = (ServerWorld) attacker.getWorld();
    //        world.playSound(null, player.getX(), player.getY(), player.getZ(), SoundEvents.BLOCK_NETHERITE_BLOCK_BREAK, SoundCategory.PLAYERS, 1.0f, (float) (1.0f + player.getRandom().nextGaussian() / 10f));
    //    }
    //    return true;
    //}
}
