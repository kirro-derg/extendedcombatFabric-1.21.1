package dev.kirro.extendedcombat.mixin.client;

import com.llamalad7.mixinextras.sugar.Local;
import dev.kirro.ExtendedCombat;
import dev.kirro.ExtendedCombatClient;
import dev.kirro.extendedcombat.datagen.ModModelProvider;
import dev.kirro.extendedcombat.item.ModItems;
import dev.kirro.extendedcombat.tags.ModItemTags;
import net.minecraft.client.render.VertexConsumerProvider;
import net.minecraft.client.render.item.ItemModels;
import net.minecraft.client.render.item.ItemRenderer;
import net.minecraft.client.render.model.BakedModel;
import net.minecraft.client.render.model.json.ModelTransformationMode;
import net.minecraft.client.util.ModelIdentifier;
import net.minecraft.client.util.math.MatrixStack;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.registry.Registries;
import net.minecraft.registry.entry.RegistryEntry;
import net.minecraft.registry.tag.TagKey;
import net.minecraft.util.Identifier;
import org.spongepowered.asm.mixin.Final;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.Unique;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.ModifyVariable;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

@Mixin(ItemRenderer.class)
public abstract class ItemRendererMixin {

    @Shadow
    @Final
    private ItemModels models;

    @Shadow
    public abstract ItemModels getModels();

    @ModifyVariable(
            method = "renderItem(Lnet/minecraft/item/ItemStack;Lnet/minecraft/client/render/model/json/ModelTransformationMode;ZLnet/minecraft/client/util/math/MatrixStack;Lnet/minecraft/client/render/VertexConsumerProvider;IILnet/minecraft/client/render/model/BakedModel;)V",
            at = @At(value = "HEAD"),
            argsOnly = true
    )
    public BakedModel renderItem(BakedModel bakedModel, @Local(argsOnly = true)ItemStack stack, @Local(argsOnly = true)ModelTransformationMode renderMode) {
        if (stack.isIn(ModItemTags.SCALED_ITEMS) && (renderMode == ModelTransformationMode.GUI)) {
            return this.models.getModelManager().getModel(ModelIdentifier.ofInventoryVariant(ExtendedCombat.id("nether_steel_greatsword")));
        }
        return bakedModel;
    }

    @ModifyVariable(
            method = "getModel",
            at = @At(value = "STORE"),
            ordinal = 1
    )
    public BakedModel getHeldItemModelMixin(BakedModel bakedModel, @Local(argsOnly = true) ItemStack stack) {
        if (stack.isIn(ModItemTags.SCALED_ITEMS)) {
            return this.models.getModelManager().getModel(ModelIdentifier.ofInventoryVariant(ExtendedCombat.id("nether_steel_greatsword_handheld")));
        }
        return bakedModel;
    }
}
