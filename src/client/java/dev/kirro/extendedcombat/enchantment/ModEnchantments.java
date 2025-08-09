package dev.kirro.extendedcombat.enchantment;

import dev.kirro.ExtendedCombat;
import dev.kirro.extendedcombat.enchantment.custom.*;
import net.fabricmc.loader.api.FabricLoader;
import net.minecraft.component.EnchantmentEffectComponentTypes;
import net.minecraft.component.type.AttributeModifierSlot;
import net.minecraft.enchantment.Enchantment;
import net.minecraft.enchantment.EnchantmentLevelBasedValue;
import net.minecraft.enchantment.effect.EnchantmentValueEffect;
import net.minecraft.enchantment.effect.value.AddEnchantmentEffect;
import net.minecraft.item.Item;
import net.minecraft.registry.Registerable;
import net.minecraft.registry.RegistryKey;
import net.minecraft.registry.RegistryKeys;
import net.minecraft.registry.entry.RegistryEntryList;
import net.minecraft.registry.tag.ItemTags;
import net.minecraft.util.Identifier;

public class ModEnchantments {
    public static final RegistryKey<Enchantment> DASH =
            RegistryKey.of(RegistryKeys.ENCHANTMENT, Identifier.of(ExtendedCombat.MOD_ID, "dash"));

    public static final RegistryKey<Enchantment> AIR_JUMP =
            RegistryKey.of(RegistryKeys.ENCHANTMENT, Identifier.of(ExtendedCombat.MOD_ID, "air_jump"));

    public static final RegistryKey<Enchantment> BLINK =
            RegistryKey.of(RegistryKeys.ENCHANTMENT, Identifier.of(ExtendedCombat.MOD_ID, "blink"));

    public static final RegistryKey<Enchantment> OBSCURITY =
            RegistryKey.of(RegistryKeys.ENCHANTMENT, Identifier.of(ExtendedCombat.MOD_ID, "obscurity"));

    public static final RegistryKey<Enchantment> VANITY =
            RegistryKey.of(RegistryKeys.ENCHANTMENT, Identifier.of(ExtendedCombat.MOD_ID, "vanity"));

    public static Enchantment create(Identifier id, RegistryEntryList<Item> supportedItems, int maxLevel, AttributeModifierSlot slot, EffectsAdder effectsAdder) {
        Enchantment.Builder builder = Enchantment.builder(Enchantment.definition(supportedItems, 5, maxLevel, Enchantment.leveledCost(5, 6), Enchantment.leveledCost(20, 6), 2, slot));
        effectsAdder.addEffects(builder);
        return builder.build(id);
    }

    public static void bootStrap(Registerable<Enchantment> registerable) {
        var items = registerable.getRegistryLookup(RegistryKeys.ITEM);

        registerable.register(DASH, create(DASH.getValue(),
                items.getOrThrow(ItemTags.LEG_ARMOR_ENCHANTABLE),
                3,
                AttributeModifierSlot.LEGS, builder -> builder.addNonListEffect(
                        ModEnchantmentEffectComponentTypes.DASH,
                        new DashEnchantmentEffect(
                                new AddEnchantmentEffect(EnchantmentLevelBasedValue.linear(1, -0.25f)),
                                new AddEnchantmentEffect(EnchantmentLevelBasedValue.linear(0.85f, 0.3f))
                        ))));

        registerable.register(AIR_JUMP, create(AIR_JUMP.getValue(),
                items.getOrThrow(ItemTags.FOOT_ARMOR_ENCHANTABLE),
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
                items.getOrThrow(ItemTags.CHEST_ARMOR_ENCHANTABLE),
                1,
                AttributeModifierSlot.CHEST, builder -> builder.addNonListEffect(
                        ModEnchantmentEffectComponentTypes.BLINK,
                        new BlinkEnchantmentEffect(
                                new AddEnchantmentEffect(EnchantmentLevelBasedValue.constant(10)),
                                new AddEnchantmentEffect(EnchantmentLevelBasedValue.constant(5))
                        ))));

        registerable.register(OBSCURITY, create(OBSCURITY.getValue(),
                items.getOrThrow(ItemTags.HEAD_ARMOR_ENCHANTABLE),
                1,
                AttributeModifierSlot.HEAD, builder -> builder.addNonListEffect(
                        ModEnchantmentEffectComponentTypes.OBSCURITY,
                        new ObscurityEnchantmentEffect(
                                new AddEnchantmentEffect(EnchantmentLevelBasedValue.constant(1))
                        ))));

        registerable.register(VANITY, create(VANITY.getValue(),
                items.getOrThrow(ItemTags.ARMOR_ENCHANTABLE),
                1,
                AttributeModifierSlot.ARMOR, builder -> builder.addNonListEffect(
                        ModEnchantmentEffectComponentTypes.VANITY,
                        new VanityEnchantmentEffect(
                                new AddEnchantmentEffect(EnchantmentLevelBasedValue.constant(1))
                        ))));
    }

    public interface EffectsAdder {
        void addEffects(Enchantment.Builder builder);
    }
}
