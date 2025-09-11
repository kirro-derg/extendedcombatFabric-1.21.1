package dev.kirro.extendedcombat.mixin.client;

import com.llamalad7.mixinextras.sugar.Local;
import dev.kirro.extendedcombat.tags.ModItemTags;
import net.minecraft.client.render.item.ItemModels;
import net.minecraft.client.render.item.ItemRenderer;
import net.minecraft.client.render.model.BakedModel;
import net.minecraft.client.render.model.json.ModelTransformationMode;
import net.minecraft.client.util.ModelIdentifier;
import net.minecraft.item.ItemStack;
import net.minecraft.registry.Registries;
import net.minecraft.util.Identifier;
import org.spongepowered.asm.mixin.Final;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.Unique;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.ModifyVariable;

import java.util.ArrayList;
import java.util.List;

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
            bakedModel = this.models.getModelManager().getModel(ModelIdentifier.ofInventoryVariant(getModelPath(stack, false)));

            //for (ModelIdentifier id : ModItemModels.getModels("")) {
            //    bakedModel = this.models.getModelManager().getModel(id);
            //}
            // bakedModel = this.models.getModelManager().getModel(ModItemModels.getModelsForStack("", stack));


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
            //this.models.getModelManager().getModel(ModelIdentifier.ofInventoryVariant(ExtendedCombat.id("nether_steel_greatsword_handheld")));

            bakedModel = this.models.getModelManager().getModel(ModelIdentifier.ofInventoryVariant(getModelPath(stack, true)));

            //for (ModelIdentifier id : ModItemModels.getModels("_handheld")) {
            //    model.add(this.models.getModelManager().getModel(id));
            //    for (BakedModel item : model) {
            //        return item;
            //    }
            //}
        }

        return bakedModel;
    }

    @Unique
    private Identifier getModelPath(ItemStack stack, boolean correctMode) {
        Identifier textureId = Registries.ITEM.getId(stack.getItem());
        Identifier truncatedPath = Identifier.of(textureId.getNamespace(), textureId.getPath());

        if (correctMode) {
            return Identifier.of(textureId.getNamespace(), textureId.getPath() + "_handheld");
        } else {
            return Identifier.of(textureId.getNamespace(), textureId.getPath());
        }

    }
}
