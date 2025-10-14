package dev.kirro.mixin.client;

import com.llamalad7.mixinextras.sugar.Local;
import dev.kirro.ModConfig;
import dev.kirro.extendedcombat.util.ItemEntryIterator;
import net.minecraft.client.render.item.ItemModels;
import net.minecraft.client.render.item.ItemRenderer;
import net.minecraft.client.render.model.BakedModel;
import net.minecraft.client.render.model.json.ModelTransformationMode;
import net.minecraft.client.util.ModelIdentifier;
import net.minecraft.item.ItemStack;
import org.spongepowered.asm.mixin.Final;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.ModifyVariable;

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
        if (ModConfig.acceptableItems.contains(ItemEntryIterator.getItemId(stack)) && renderMode == ModelTransformationMode.GUI) {
            bakedModel = this.models.getModelManager().getModel(ModelIdentifier.ofInventoryVariant(ItemEntryIterator.getModelPath(stack, "")));
        }

        return bakedModel;
    }

    @ModifyVariable(
            method = "getModel",
            at = @At(value = "STORE"),
            ordinal = 1
    )
    public BakedModel getHeldItemModelMixin(BakedModel bakedModel, @Local(argsOnly = true) ItemStack stack) {
        if (ModConfig.acceptableItems.contains(ItemEntryIterator.getItemId(stack))) {
            bakedModel = this.models.getModelManager().getModel(ModelIdentifier.ofInventoryVariant(ItemEntryIterator.getModelPath(stack, "_handheld")));
        }

        return bakedModel;
    }
}
