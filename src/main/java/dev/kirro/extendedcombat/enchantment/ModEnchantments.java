package dev.kirro.extendedcombat.enchantment;

import dev.kirro.ExtendedCombat;
import dev.kirro.extendedcombat.enchantment.custom.*;
import dev.kirro.extendedcombat.tags.ModItemTags;
import net.minecraft.component.type.AttributeModifierSlot;
import net.minecraft.enchantment.Enchantment;
import net.minecraft.enchantment.EnchantmentLevelBasedValue;
import net.minecraft.enchantment.effect.value.AddEnchantmentEffect;
import net.minecraft.item.Item;
import net.minecraft.registry.Registerable;
import net.minecraft.registry.RegistryKey;
import net.minecraft.registry.RegistryKeys;
import net.minecraft.registry.entry.RegistryEntryList;
import net.minecraft.util.Identifier;

public class ModEnchantments {
    public static final RegistryKey<Enchantment> DASH = create("dash");

    public static final RegistryKey<Enchantment> AIR_JUMP = create("air_jump");

    public static final RegistryKey<Enchantment> BLINK = create("blink");

    public static final RegistryKey<Enchantment> OBSCURITY = create("obscurity");

    public static final RegistryKey<Enchantment> VANITY = create("vanity");

    public static final RegistryKey<Enchantment> STEALTH = create("stealth");

    public static final RegistryKey<Enchantment> KEEPSAKE = create("keepsake");

    public static final RegistryKey<Enchantment> BURST = create("burst");

    public static final RegistryKey<Enchantment> CONCUSSION = create("concussion");

    public static final RegistryKey<Enchantment> FLUID_WALKER = create("fluid_walker");

    public static final RegistryKey<Enchantment> SWIFTNESS = create("swiftness");

    public static final RegistryKey<Enchantment> WATERGEL = create("watergel");

    public static Enchantment create(Identifier id, RegistryEntryList<Item> supportedItems, int maxLevel, AttributeModifierSlot slot, EffectsAdder effectsAdder) {
        Enchantment.Builder builder = Enchantment.builder(Enchantment.definition(supportedItems, 5, maxLevel, Enchantment.leveledCost(5, 6), Enchantment.leveledCost(20, 6), 2, slot));
        effectsAdder.addEffects(builder);
        return builder.build(id);
    }

    public static Enchantment createCustom(Identifier id, RegistryEntryList<Item> supportedItems, int weight, int maxLevel, Enchantment.Cost minCost, Enchantment.Cost maxCost, AttributeModifierSlot slot, EffectsAdder effectsAdder) {
        Enchantment.Builder builder = Enchantment.builder(Enchantment.definition(supportedItems, weight, maxLevel, minCost, maxCost, 2, slot));
        effectsAdder.addEffects(builder);
        return builder.build(id);
    }

    public static void bootStrap(Registerable<Enchantment> registerable) {
        var items = registerable.getRegistryLookup(RegistryKeys.ITEM);
        var enchantments = registerable.getRegistryLookup(RegistryKeys.ENCHANTMENT);

        registerable.register(DASH, create(DASH.getValue(),
                items.getOrThrow(ModItemTags.DASH_ENCHANTABLE),
                3,
                AttributeModifierSlot.LEGS, builder -> builder.addNonListEffect(
                        ModEnchantmentEffectComponentTypes.DASH,
                        new DashEnchantmentEffect(
                                new AddEnchantmentEffect(EnchantmentLevelBasedValue.linear(1, -0.25f)),
                                new AddEnchantmentEffect(EnchantmentLevelBasedValue.linear(0.85f, 0.3f))
                        ))));

        registerable.register(AIR_JUMP, create(AIR_JUMP.getValue(),
                items.getOrThrow(ModItemTags.AIR_JUMP_ENCHANTABLE),
                3,
                AttributeModifierSlot.FEET, builder -> builder.addNonListEffect(
                        ModEnchantmentEffectComponentTypes.AIR_JUMP,
                        new AirJumpEnchantmentEffect(
                                new AddEnchantmentEffect(EnchantmentLevelBasedValue.constant(1.45f)),
                                new AddEnchantmentEffect(EnchantmentLevelBasedValue.constant(0.5f)),
                                new AddEnchantmentEffect(EnchantmentLevelBasedValue.constant(0.5f)),
                                new AddEnchantmentEffect(EnchantmentLevelBasedValue.linear(1))
                        ))));

        registerable.register(BLINK, create(BLINK.getValue(),
                items.getOrThrow(ModItemTags.BLINK_ENCHANTABLE),
                1,
                AttributeModifierSlot.CHEST, builder -> builder.addNonListEffect(
                        ModEnchantmentEffectComponentTypes.BLINK,
                        new BlinkEnchantmentEffect(
                                new AddEnchantmentEffect(EnchantmentLevelBasedValue.constant(10)),
                                new AddEnchantmentEffect(EnchantmentLevelBasedValue.constant(5))
                        ))));

        registerable.register(OBSCURITY, create(OBSCURITY.getValue(),
                items.getOrThrow(ModItemTags.OBSCURITY_ENCHANTABLE),
                1,
                AttributeModifierSlot.HEAD, builder -> builder.addNonListEffect(
                        ModEnchantmentEffectComponentTypes.OBSCURITY,
                        new ObscurityEnchantmentEffect(
                                new AddEnchantmentEffect(EnchantmentLevelBasedValue.constant(1))
                        ))));

        registerable.register(VANITY, create(VANITY.getValue(),
                items.getOrThrow(ModItemTags.VANITY_ENCHANTABLE),
                1,
                AttributeModifierSlot.ARMOR, builder -> builder.addNonListEffect(
                        ModEnchantmentEffectComponentTypes.VANITY,
                        new VanityEnchantmentEffect(
                                new AddEnchantmentEffect(EnchantmentLevelBasedValue.constant(1))
                        ))));

        registerable.register(STEALTH, create(STEALTH.getValue(),
                items.getOrThrow(ModItemTags.STEALTH_ENCHANTABLE),
                1,
                AttributeModifierSlot.CHEST, builder -> builder.addNonListEffect(
                        ModEnchantmentEffectComponentTypes.STEALTH,
                        new StealthEnchantmentEffect(
                                new AddEnchantmentEffect(EnchantmentLevelBasedValue.constant(1))
                        ))));

        registerable.register(KEEPSAKE, createCustom(KEEPSAKE.getValue(),
                items.getOrThrow(ModItemTags.KEEPSAKE_ENCHANTABLE),
                2,
                1,
                Enchantment.leveledCost(25, 10),
                Enchantment.leveledCost(75, 10),
                AttributeModifierSlot.ANY, builder -> builder.addNonListEffect(
                        ModEnchantmentEffectComponentTypes.KEEPSAKE,
                        new KeepsakeEnchantmentEffect(
                                new AddEnchantmentEffect(EnchantmentLevelBasedValue.constant(1))
                        )).exclusiveSet(enchantments.getOrThrow(ModEnchantmentTags.DURABILITY_EXCLUSIVE_SET))));

        registerable.register(BURST, createCustom(BURST.getValue(),
                items.getOrThrow(ModItemTags.BURST_ENCHANTABLE),
                2,
                3,
                Enchantment.leveledCost(25, 10),
                Enchantment.leveledCost(75, 10),
                AttributeModifierSlot.CHEST, builder -> builder.addNonListEffect(
                        ModEnchantmentEffectComponentTypes.BURST,
                        new BurstEnchantmentEffect(
                                new AddEnchantmentEffect(EnchantmentLevelBasedValue.linear(1.05f, 0.5f))
                        ))));

        registerable.register(CONCUSSION, createCustom(CONCUSSION.getValue(),
                items.getOrThrow(ModItemTags.CONCUSSION_ENCHANTABLE),
                5,
                3,
                Enchantment.leveledCost(5, 1),
                Enchantment.leveledCost(10, 1),
                AttributeModifierSlot.MAINHAND, builder -> builder.addNonListEffect(
                        ModEnchantmentEffectComponentTypes.CONCUSSION,
                        new ConcussionEnchantmentEffect(
                                new AddEnchantmentEffect(EnchantmentLevelBasedValue.constant(1))
                        ))));

        registerable.register(FLUID_WALKER, createCustom(FLUID_WALKER.getValue(),
                items.getOrThrow(ModItemTags.FLUID_WALKER_ENCHANTABLE),
                5,
                3,
                Enchantment.leveledCost(5, 5),
                Enchantment.leveledCost(10, 5),
                AttributeModifierSlot.FEET, builder -> builder.addNonListEffect(
                        ModEnchantmentEffectComponentTypes.FLUID_WALKER,
                        new FluidWalkerEnchantmentEffect(
                                new AddEnchantmentEffect(EnchantmentLevelBasedValue.linear(1.3f, 0.4f))
                        ))));

        registerable.register(SWIFTNESS, createCustom(SWIFTNESS.getValue(),
                items.getOrThrow(ModItemTags.SWIFTNESS_ENCHANTABLE),
                5,
                1,
                Enchantment.leveledCost(5, 5),
                Enchantment.leveledCost(10, 5),
                AttributeModifierSlot.LEGS, builder -> builder.addNonListEffect(
                        ModEnchantmentEffectComponentTypes.SWIFTNESS,
                        new SwiftnessEnchantmentEffect(
                                new AddEnchantmentEffect(EnchantmentLevelBasedValue.constant(0.5f))
                        ))));

        registerable.register(WATERGEL, createCustom(WATERGEL.getValue(),
                items.getOrThrow(ModItemTags.WATERGEL_ENCHANTABLE),
                5,
                1,
                Enchantment.leveledCost(65, 10),
                Enchantment.leveledCost(75, 10),
                AttributeModifierSlot.LEGS, builder -> builder.addNonListEffect(
                        ModEnchantmentEffectComponentTypes.WATERGEL,
                        new WaterGelEnchantmentEffect(
                                new AddEnchantmentEffect(EnchantmentLevelBasedValue.constant(1.0f))
                        ))));
    }

    public interface EffectsAdder {
        void addEffects(Enchantment.Builder builder);
    }

    private static RegistryKey<Enchantment> create(String name) {
        return RegistryKey.of(RegistryKeys.ENCHANTMENT, ExtendedCombat.id(name));
    }
}
