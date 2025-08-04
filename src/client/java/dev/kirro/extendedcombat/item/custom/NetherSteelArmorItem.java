package dev.kirro.extendedcombat.item.custom;

import com.google.common.collect.ImmutableMap;
import dev.kirro.extendedcombat.entity.custom.StatueEntity;
import net.minecraft.component.type.AttributeModifierSlot;
import net.minecraft.component.type.AttributeModifiersComponent;
import net.minecraft.entity.Entity;
import net.minecraft.entity.LivingEntity;
import net.minecraft.entity.attribute.EntityAttributeModifier;
import net.minecraft.entity.attribute.EntityAttributes;
import net.minecraft.entity.effect.StatusEffectInstance;
import net.minecraft.entity.effect.StatusEffects;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.item.ArmorItem;
import net.minecraft.item.ArmorMaterial;
import net.minecraft.item.ItemStack;
import net.minecraft.registry.entry.RegistryEntry;
import net.minecraft.world.World;
import virtuoel.pehkui.api.ScaleTypes;

import java.util.List;
import java.util.Map;

public class NetherSteelArmorItem extends ArmorItem {

    private static final Map<RegistryEntry<ArmorMaterial>, List<StatusEffectInstance>> MATERIAL_TO_EFFECT_MAP =
            (new ImmutableMap.Builder<RegistryEntry<ArmorMaterial>, List<StatusEffectInstance>>())
                    .put(ModArmorMaterials.NETHER_STEEL,
                            List.of(new StatusEffectInstance(StatusEffects.FIRE_RESISTANCE, 20, 1, false, false, false)))
                    .build();

    public NetherSteelArmorItem(RegistryEntry<ArmorMaterial> material, Type type, Settings settings) {
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
            if (entity instanceof PlayerEntity player && hasFullSuitOfArmorOn(player)) {
                evaluateArmorEffects(player);
                evaluatePlayerScale(player);
            } else if (entity instanceof PlayerEntity player && !hasFullSuitOfArmorOn(player)){
                evaluatePlayerScale(player);
            } else if (entity instanceof LivingEntity player && hasFullSuitOfArmorOn((PlayerEntity) player)) {
                evaluatePlayerScale((PlayerEntity) player);
            } else if (entity instanceof LivingEntity player && !hasFullSuitOfArmorOn((PlayerEntity) player)) {
                evaluatePlayerScale((PlayerEntity) player);
            }
            super.inventoryTick(stack, world, entity, slot, selected);
        }
    }

    private void evaluateArmorEffects(PlayerEntity player) {
        for (Map.Entry<RegistryEntry<ArmorMaterial>, List<StatusEffectInstance>> entry : MATERIAL_TO_EFFECT_MAP.entrySet()) {
            RegistryEntry<ArmorMaterial> mapArmorMaterial = entry.getKey();
            List<StatusEffectInstance> mapStatusEffect = entry.getValue();

            if (hasCorrectArmorOn(mapArmorMaterial, player)) {
                addStatusEffectForMaterial(player, mapArmorMaterial, mapStatusEffect);
            }
        }
    }

    private void evaluatePlayerScale(PlayerEntity player) {
        for (Map.Entry<RegistryEntry<ArmorMaterial>, List<StatusEffectInstance>> entry : MATERIAL_TO_EFFECT_MAP.entrySet()) {
            RegistryEntry<ArmorMaterial> mapArmorMaterial = entry.getKey();

            if (hasCorrectArmorOn(mapArmorMaterial, player)) {
                ScaleTypes.BASE.getScaleData(player).setTargetScale(1.25f);
            } else if (!hasCorrectArmorOn(mapArmorMaterial, player)) {
                ScaleTypes.BASE.getScaleData(player).setTargetScale(1.0f);
            }
        }
    }

    private void addStatusEffectForMaterial(PlayerEntity player, RegistryEntry<ArmorMaterial> mapArmorMaterial, List<StatusEffectInstance> mapStatusEffect) {
        boolean hasPlayerEffect = mapStatusEffect.stream().allMatch(StatusEffectInstance -> player.hasStatusEffect(StatusEffectInstance.getEffectType()));

        if (hasCorrectArmorOn(mapArmorMaterial, player) && !hasPlayerEffect) {
            for (StatusEffectInstance instance : mapStatusEffect) {
                player.addStatusEffect(new StatusEffectInstance(instance.getEffectType(),
                        instance.getDuration(), instance.getAmplifier(), instance.isAmbient(), instance.shouldShowParticles()));
            }
        }
    }

    private boolean hasFullSuitOfArmorOn(PlayerEntity player) {
        ItemStack boots = player.getInventory().getArmorStack(0);
        ItemStack leggings = player.getInventory().getArmorStack(1);
        ItemStack chestplate = player.getInventory().getArmorStack(2);
        ItemStack helmet = player.getInventory().getArmorStack(3);

        return !helmet.isEmpty() && !chestplate.isEmpty()
                && !leggings.isEmpty() && !boots.isEmpty();
    }

    private boolean hasCorrectArmorOn(RegistryEntry<ArmorMaterial> material, PlayerEntity player) {
        for (ItemStack armorStack: player.getInventory().armor) {
            if(!(armorStack.getItem() instanceof ArmorItem)) {
                return false;
            }
        }

        ArmorItem boots = ((ArmorItem)player.getInventory().getArmorStack(0).getItem());
        ArmorItem leggings = ((ArmorItem)player.getInventory().getArmorStack(1).getItem());
        ArmorItem chestplate = ((ArmorItem)player.getInventory().getArmorStack(2).getItem());
        ArmorItem helmet = ((ArmorItem)player.getInventory().getArmorStack(3).getItem());

        return helmet.getMaterial() == material && chestplate.getMaterial() == material
                && leggings.getMaterial() == material && boots.getMaterial() == material;
    }
}
