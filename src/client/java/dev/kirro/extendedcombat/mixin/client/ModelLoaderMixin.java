package dev.kirro.extendedcombat.mixin.client;

import dev.kirro.ExtendedCombat;
import dev.kirro.extendedcombat.util.ModItemModels;
import net.minecraft.client.render.model.ModelLoader;
import net.minecraft.client.util.ModelIdentifier;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

@Mixin(ModelLoader.class)
public abstract class ModelLoaderMixin {
    @Shadow
    protected abstract void loadItemModel(ModelIdentifier id);

    @Inject(method = "<init>", at = @At(value = "INVOKE", target = "Lnet/minecraft/client/render/model/ModelLoader;loadItemModel(Lnet/minecraft/client/util/ModelIdentifier;)V", ordinal = 1))
    private void onInit(CallbackInfo ci) {
        ExtendedCombat.LOGGER.info("Loading Baked Item Models:");
        for (ModelIdentifier id : ModItemModels.getModels("_handheld")) {
            this.loadItemModel(id);
        }
        ExtendedCombat.LOGGER.info("Finished Loading Models.");
    }
}
