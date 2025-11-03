package dev.kirro.extendedcombat.item.custom;

import dev.kirro.extendedcombat.util.ExtendedCombatUtil;
import net.minecraft.advancement.criterion.Criteria;
import net.minecraft.entity.LivingEntity;
import net.minecraft.entity.effect.StatusEffectCategory;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.item.ItemUsage;
import net.minecraft.item.Items;
import net.minecraft.item.tooltip.TooltipData;
import net.minecraft.item.tooltip.TooltipType;
import net.minecraft.server.network.ServerPlayerEntity;
import net.minecraft.sound.SoundEvent;
import net.minecraft.sound.SoundEvents;
import net.minecraft.stat.Stats;
import net.minecraft.text.Text;
import net.minecraft.util.*;
import net.minecraft.world.World;

import java.util.List;
import java.util.Optional;

public class MilkBottleItem extends Item {
    private static final int MAX_USE_TIME = 32;
    protected final MilkType type;

    public MilkBottleItem(Item.Settings settings, MilkType type) {
        super(settings);
        this.type = type;
    }

    @Override
    public ItemStack finishUsing(ItemStack stack, World world, LivingEntity user) {
        super.finishUsing(stack, world, user);
        if (user instanceof ServerPlayerEntity serverPlayerEntity) {
            Criteria.CONSUME_ITEM.trigger(serverPlayerEntity, stack);
            serverPlayerEntity.incrementStat(Stats.USED.getOrCreateStat(this));
        }

        if (!world.isClient) {
            switch (type) {
                case PLAIN ->
                        ExtendedCombatUtil.removeEffectOfType(user, StatusEffectCategory.NEUTRAL);
                case SWEET_BERRY ->
                        ExtendedCombatUtil.removeEffectOfType(user, StatusEffectCategory.HARMFUL);
                case CHOCOLATE ->
                        ExtendedCombatUtil.removeEffectOfType(user, StatusEffectCategory.BENEFICIAL);
            }
        }

        if (user instanceof PlayerEntity player) {
            return ItemUsage.exchangeStack(stack, player, new ItemStack(Items.GLASS_BOTTLE), false);
        } else {
            stack.decrementUnlessCreative(1, user);
            return stack;
        }
    }

    @Override
    public void appendTooltip(ItemStack stack, TooltipContext context, List<Text> tooltip, TooltipType type) {
        MilkType milkType = this.type;
        switch (milkType) {
            case PLAIN -> tooltip.add(Text.translatable("tooltip.milk_bottle.milk").formatted(Formatting.BLUE));
            case SWEET_BERRY -> tooltip.add(Text.translatable("tooltip.milk_bottle.sweet_berry_milk").formatted(Formatting.BLUE));
            case CHOCOLATE -> tooltip.add(Text.translatable("tooltip.milk_bottle.chocolate_milk").formatted(Formatting.BLUE));
        }
        super.appendTooltip(stack, context, tooltip, type);
    }



    public MilkType getType() {
        return this.type;
    }

    @Override
    public int getMaxUseTime(ItemStack stack, LivingEntity user) {
        return MAX_USE_TIME;
    }

    @Override
    public UseAction getUseAction(ItemStack stack) {
        return UseAction.DRINK;
    }

    @Override
    public SoundEvent getDrinkSound() {
        return SoundEvents.ITEM_HONEY_BOTTLE_DRINK;
    }

    @Override
    public ItemStack getRecipeRemainder(ItemStack stack) {
        return new ItemStack(Items.GLASS_BOTTLE);
    }

    @Override
    public Optional<TooltipData> getTooltipData(ItemStack stack) {
        return super.getTooltipData(stack);
    }

    @Override
    public SoundEvent getEatSound() {
        return SoundEvents.ITEM_HONEY_BOTTLE_DRINK;
    }

    @Override
    public TypedActionResult<ItemStack> use(World world, PlayerEntity user, Hand hand) {
        return ItemUsage.consumeHeldItem(world, user, hand);
    }

    public enum MilkType implements StringIdentifiable {
        PLAIN("neutral"),
        SWEET_BERRY("harmful"),
        CHOCOLATE("beneficial");

        private final String name;

        MilkType (final String name) {
            this.name = name;
        }

        public String getName() {
            return this.name;
        }

        @Override
        public String asString() {
            return this.name;
        }
    }
}
