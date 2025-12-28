package dev.kirro.extendedcombat.item.custom;

import dev.kirro.extendedcombat.item.ModDataComponentTypes;
import net.minecraft.entity.Entity;
import net.minecraft.item.ArmorMaterial;
import net.minecraft.item.ItemStack;
import net.minecraft.item.tooltip.TooltipType;
import net.minecraft.registry.entry.RegistryEntry;
import net.minecraft.text.Text;
import net.minecraft.util.Formatting;
import net.minecraft.world.World;

import java.util.List;

public class HunterMaskItem extends WoolArmorItem {
    public HunterMaskItem(RegistryEntry<ArmorMaterial> material, Type type, Settings settings) {
        super(material, type, settings);
    }

    @Override
    public void inventoryTick(ItemStack stack, World world, Entity entity, int slot, boolean selected) {
    }

    @Override
    public void appendTooltip(ItemStack stack, TooltipContext context, List<Text> tooltip, TooltipType type) {
        if (stack.getItem() instanceof WoolArmorItem item) {
            String id = item.getMaterial().getIdAsString().replace("extendedcombat:", "");
            tooltip.add(Text.translatable("tooltip.extendedcombat." + id + ".mask_tooltip").formatted(Formatting.GRAY));
        }
    }

    public static void cycleData(ItemStack stack, boolean hidden) {
        stack.set(ModDataComponentTypes.HIDDEN, hidden);
    }

    public static boolean isHidden(ItemStack stack) {
        return stack.getOrDefault(ModDataComponentTypes.HIDDEN, false);
    }
}
