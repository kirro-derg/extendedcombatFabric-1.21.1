package dev.kirro.extendedcombat.item.custom;

import dev.kirro.extendedcombat.effects.ModStatusEffects;
import dev.kirro.extendedcombat.tags.ModItemTags;
import net.minecraft.component.type.AttributeModifierSlot;
import net.minecraft.component.type.AttributeModifiersComponent;
import net.minecraft.entity.Entity;
import net.minecraft.entity.EquipmentSlot;
import net.minecraft.entity.LivingEntity;
import net.minecraft.entity.attribute.EntityAttributeModifier;
import net.minecraft.entity.attribute.EntityAttributes;
import net.minecraft.entity.effect.StatusEffectInstance;
import net.minecraft.entity.effect.StatusEffects;
import net.minecraft.item.ArmorItem;
import net.minecraft.item.ArmorMaterial;
import net.minecraft.item.ItemStack;
import net.minecraft.registry.entry.RegistryEntry;
import net.minecraft.world.World;
import virtuoel.pehkui.api.ScaleTypes;

public class EchoSteelArmorItem extends ArmorItem {

    public EchoSteelArmorItem(RegistryEntry<ArmorMaterial> material, Type type, Settings settings) {
        super(material, type, settings);
    }

    public static AttributeModifiersComponent createAttributeModifiers(ArmorMaterial material, float fallDamageMultiplier) {
        return AttributeModifiersComponent.builder()
                .add(
                        EntityAttributes.GENERIC_FALL_DAMAGE_MULTIPLIER,
                        new EntityAttributeModifier(BASE_ATTACK_DAMAGE_MODIFIER_ID, fallDamageMultiplier, EntityAttributeModifier.Operation.ADD_MULTIPLIED_BASE),
                        AttributeModifierSlot.FEET
                )
                .build();
    }

    @Override
    public void inventoryTick(ItemStack stack, World world, Entity entity, int slot, boolean selected) {
        if(!world.isClient()) {
            if (entity instanceof LivingEntity living && hasFullSuitOfArmorOn(living)) {
                evaluateArmorEffects(living);
            }
            super.inventoryTick(stack, world, entity, slot, selected);
        }
    }

    private void evaluateArmorEffects(LivingEntity entity) {
            if (hasCorrectArmorOn(entity)) {
                entity.addStatusEffect(new StatusEffectInstance(StatusEffects.FIRE_RESISTANCE, 20, 1, false, false, false));
                entity.addStatusEffect(new StatusEffectInstance(StatusEffects.DARKNESS, 100, 0, false, false, false));
            }
    }

    private boolean hasFullSuitOfArmorOn(LivingEntity entity) {
        ItemStack boots = entity.getEquippedStack(EquipmentSlot.FEET);
        ItemStack leggings = entity.getEquippedStack(EquipmentSlot.LEGS);
        ItemStack chestplate = entity.getEquippedStack(EquipmentSlot.CHEST);
        ItemStack helmet = entity.getEquippedStack(EquipmentSlot.HEAD);

        return !helmet.isEmpty() && !chestplate.isEmpty()
                && !leggings.isEmpty() && !boots.isEmpty();
    }

    public boolean hasCorrectArmorOn(LivingEntity entity) {
        for (ItemStack armorStack: entity.getArmorItems()) {
            if(!(armorStack.getItem() instanceof ArmorItem)) {
                return false;
            }
        }

        ItemStack boots = (entity.getEquippedStack(EquipmentSlot.FEET));
        ItemStack leggings = (entity.getEquippedStack(EquipmentSlot.LEGS));
        ItemStack chestplate = (entity.getEquippedStack(EquipmentSlot.CHEST));
        ItemStack helmet = (entity.getEquippedStack(EquipmentSlot.HEAD));

        return helmet.isIn(ModItemTags.ECHO_ARMOR)
                && chestplate.isIn(ModItemTags.ECHO_ARMOR)
                && leggings.isIn(ModItemTags.ECHO_ARMOR)
                && boots.isIn(ModItemTags.ECHO_ARMOR);
    }
}
