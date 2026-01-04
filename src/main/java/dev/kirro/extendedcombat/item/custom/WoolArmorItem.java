package dev.kirro.extendedcombat.item.custom;

import dev.kirro.extendedcombat.item.ModDataComponentTypes;
import net.minecraft.item.ArmorMaterial;
import net.minecraft.item.ItemStack;
import net.minecraft.item.tooltip.TooltipType;
import net.minecraft.registry.entry.RegistryEntry;
import net.minecraft.text.Text;
import net.minecraft.util.Formatting;
import net.minecraft.util.Identifier;

import java.util.List;

public class WoolArmorItem extends ModArmorItem {
    public WoolArmorItem(RegistryEntry<ArmorMaterial> material, Type type, Settings settings) {
        super(material, type, settings);
    }

    @Override
    public void appendTooltip(ItemStack stack, TooltipContext context, List<Text> tooltip, TooltipType type) {
        if (stack.getItem() instanceof WoolArmorItem item) {
            Identifier id = Identifier.of(item.getMaterial().getIdAsString());
            tooltip.add(Text.translatable("tooltip.extendedcombat." + id.getPath() + ".cloak_tooltip").formatted(Formatting.GRAY));
        }
        super.appendTooltip(stack, context, tooltip, type);
    }

    public static void cycleData(ItemStack stack, boolean hidden) {
        stack.set(ModDataComponentTypes.HIDDEN, hidden);
    }

    public static boolean isHidden(ItemStack stack) {
        return stack.getOrDefault(ModDataComponentTypes.HIDDEN, false);
    }
}
