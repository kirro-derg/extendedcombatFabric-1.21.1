package dev.kirro;

import dev.kirro.extendedcombat.block.ModBlocks;
import dev.kirro.extendedcombat.enchantment.payload.*;
import dev.kirro.extendedcombat.entity.ModEntities;
import dev.kirro.extendedcombat.entity.custom.ModEntityModelLayers;
import dev.kirro.extendedcombat.entity.render.ChairRenderer;
import dev.kirro.extendedcombat.event.AirJumpRenderEvent;
import dev.kirro.extendedcombat.event.BlinkRenderEvent;
import dev.kirro.extendedcombat.event.DashRenderEvent;
import dev.kirro.extendedcombat.util.ExtendedCombatClientUtil;
import net.fabricmc.api.ClientModInitializer;
import net.fabricmc.fabric.api.blockrenderlayer.v1.BlockRenderLayerMap;
import net.fabricmc.fabric.api.client.keybinding.v1.KeyBindingHelper;
import net.fabricmc.fabric.api.client.networking.v1.ClientPlayNetworking;
import net.fabricmc.fabric.api.client.rendering.v1.EntityRendererRegistry;
import net.fabricmc.fabric.api.client.rendering.v1.HudRenderCallback;
import net.fabricmc.fabric.api.resource.ResourceManagerHelper;
import net.fabricmc.fabric.api.resource.ResourcePackActivationType;
import net.fabricmc.loader.api.FabricLoader;
import net.minecraft.client.option.KeyBinding;
import net.minecraft.client.render.RenderLayer;
import net.minecraft.client.util.InputUtil;
import org.lwjgl.glfw.GLFW;

import java.util.function.Supplier;

public class ExtendedCombatClient implements ClientModInitializer {

    @Override
	public void onInitializeClient() {
		BlockRenderLayerMap.INSTANCE.putBlock(ModBlocks.FRAMED_GLASS_PANEL, RenderLayer.getCutout());
        BlockRenderLayerMap.INSTANCE.putBlock(ModBlocks.BLACK_APPLE_BUSH, RenderLayer.getCutout());

        FabricLoader.getInstance().getModContainer(ExtendedCombat.MOD_ID).ifPresent(modContainer -> {
            ResourceManagerHelper.registerBuiltinResourcePack(ExtendedCombat.id("extendedcombat_programmer_art"), modContainer, ResourcePackActivationType.NORMAL);
            ResourceManagerHelper.registerBuiltinResourcePack(ExtendedCombat.id("template_handheld_pack"), modContainer, ResourcePackActivationType.NORMAL);
        });

		ModEntityModelLayers.registerLayerDefinitions();
		EntityRendererRegistry.register(ModEntities.CHAIR, ChairRenderer::new);

		registerEvents();
		registerPayloads();
    }

	public static final KeyBinding DASH = registerKeyBinding(() -> KeyBindingHelper.registerKeyBinding(new KeyBinding(
			"key.extendedcombat.dash",
			InputUtil.Type.KEYSYM,
			GLFW.GLFW_KEY_LEFT_SHIFT,
			"key.categories.enchantments")));
	public static final KeyBinding BLINK = registerKeyBinding(() -> KeyBindingHelper.registerKeyBinding(new KeyBinding(
			"key.extendedcombat.blink",
			InputUtil.Type.KEYSYM,
			GLFW.GLFW_KEY_B,
			"key.categories.enchantments")));

	private static KeyBinding registerKeyBinding(Supplier<KeyBinding> supplier) {
		KeyBinding keyBinding = supplier.get();
		ExtendedCombatClientUtil.MOVEMENT.add(keyBinding);
		return keyBinding;
	}

	private void registerEvents() {
        // enchantment hud renderers
		HudRenderCallback.EVENT.register(new DashRenderEvent());
		HudRenderCallback.EVENT.register(new AirJumpRenderEvent());
		HudRenderCallback.EVENT.register(new BlinkRenderEvent());
	}

	private void registerPayloads() {
        // client receivers
		ClientPlayNetworking.registerGlobalReceiver(AirJumpParticlePayload.ID, new AirJumpParticlePayload.Receiver());
		ClientPlayNetworking.registerGlobalReceiver(DashParticlePayload.ID, new DashParticlePayload.Reciever());
		ClientPlayNetworking.registerGlobalReceiver(BlinkParticlePayload.ID, new BlinkParticlePayload.Reciever());
		ClientPlayNetworking.registerGlobalReceiver(BlinkSyncPayload.ID, BlinkSyncPayload::handle);
	}
}