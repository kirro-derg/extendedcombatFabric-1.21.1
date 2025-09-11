package dev.kirro.extendedcombat.mixin.client;

import dev.kirro.ExtendedCombat;
import dev.kirro.extendedcombat.datagen.ModModelProvider;
import dev.kirro.extendedcombat.util.ModItemModels;
import net.minecraft.client.MinecraftClient;
import net.minecraft.client.render.model.ModelLoader;
import net.minecraft.client.util.ModelIdentifier;
import net.minecraft.entity.EquipmentSlot;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.registry.Registries;
import net.minecraft.util.Identifier;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.Unique;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

@Mixin(ModelLoader.class)
public abstract class ModelLoaderMixin {
    @Shadow
    protected abstract void loadItemModel(ModelIdentifier id);

    @Inject(method = "<init>", at = @At(value = "INVOKE", target = "Lnet/minecraft/client/render/model/ModelLoader;loadItemModel(Lnet/minecraft/client/util/ModelIdentifier;)V", ordinal = 1))
    private void onInit(CallbackInfo ci) {
        System.out.println("Loading Baked Item Models:");
        for (ModelIdentifier id : ModItemModels.getModels("_handheld")) {
            this.loadItemModel(id);
        }
        for (ModelIdentifier id : ModItemModels.getModels("")) {
            this.loadItemModel(id);
        }
        System.out.println("Finished Loading");
    }
}
