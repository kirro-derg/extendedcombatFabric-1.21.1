package dev.kirro;

import dev.kirro.extendedcombat.block.ModBlocks;
import dev.kirro.extendedcombat.block.entity.ModBlockEntityTypes;
import dev.kirro.extendedcombat.datagen.ModRecipeProvider;
import dev.kirro.extendedcombat.effects.ModStatusEffects;
import dev.kirro.extendedcombat.enchantment.ModEnchantmentEffectComponentTypes;
import dev.kirro.extendedcombat.enchantment.payload.*;
import dev.kirro.extendedcombat.entity.ModEntities;
import dev.kirro.extendedcombat.entity.custom.StatueEntity;
import dev.kirro.extendedcombat.event.AirMobilityEvent;
import dev.kirro.extendedcombat.event.EquipmentResetEvent;
import dev.kirro.extendedcombat.event.HammerUsageEvent;
import dev.kirro.extendedcombat.event.MultiplyMovementSpeedEvent;
import dev.kirro.extendedcombat.item.ModItemGroups;
import dev.kirro.extendedcombat.item.ModItems;
import dev.kirro.extendedcombat.behavior.item.XPRepairTracker;
import dev.kirro.extendedcombat.potion.ModPotions;
import dev.kirro.extendedcombat.sound.ModSounds;
import dev.kirro.extendedcombat.villager.ModPOI;
import net.fabricmc.api.ModInitializer;

import net.fabricmc.fabric.api.client.event.lifecycle.v1.ClientTickEvents;
import net.fabricmc.fabric.api.event.lifecycle.v1.ServerEntityEvents;
import net.fabricmc.fabric.api.event.lifecycle.v1.ServerTickEvents;
import net.fabricmc.fabric.api.event.player.PlayerBlockBreakEvents;
import net.fabricmc.fabric.api.networking.v1.PayloadTypeRegistry;
import net.fabricmc.fabric.api.networking.v1.PlayerLookup;
import net.fabricmc.fabric.api.networking.v1.ServerPlayNetworking;
import net.fabricmc.fabric.api.object.builder.v1.entity.FabricDefaultAttributeRegistry;
import net.minecraft.entity.EquipmentSlot;
import net.minecraft.entity.LivingEntity;
import net.minecraft.server.network.ServerPlayerEntity;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import virtuoel.pehkui.api.ScaleTypes;

import java.util.Objects;

public class ExtendedCombat implements ModInitializer {
	public static final String MOD_ID = "extendedcombat";

	// This logger is used to write text to the console and the log file.
	// It is considered best practice to use your mod id as the logger's name.
	// That way, it's clear which mod wrote info, warnings, and errors.
	public static final Logger LOGGER = LoggerFactory.getLogger(MOD_ID);

	@Override
	public void onInitialize() {
		ModItemGroups.registerItemGroups();

		ModSounds.registerSounds();

		ModItems.registerModItems();
		ModBlocks.registerModBlocks();
		ModBlockEntityTypes.registerBlockEntityTypes();

		ModPOI.registerPOIs();

		ModEntities.registerModEntities();

        ModStatusEffects.registerStatusEffects();
        ModPotions.registerPotions();
        ModRecipeProvider.registerPotionRecipes();

		ModEnchantmentEffectComponentTypes.register();

		registerEvents();
		registerPayloads();


		ServerTickEvents.END_SERVER_TICK.register(server -> {
			for (ServerPlayerEntity player : server.getPlayerManager().getPlayerList()) {
				XPRepairTracker.tick(player);
			}
		});

		ClientTickEvents.END_CLIENT_TICK.register(client -> {
		});

		FabricDefaultAttributeRegistry.register(ModEntities.STATUE, StatueEntity.createAttributes());


	}

	private void registerEvents() {
		// config
		MultiplyMovementSpeedEvent.EVENT.register(new AirMobilityEvent());
        // item
        PlayerBlockBreakEvents.BEFORE.register(new HammerUsageEvent());
		// enchantment
		ServerEntityEvents.EQUIPMENT_CHANGE.register(new EquipmentResetEvent());
	}

	private void registerPayloads() {
		//server
		PayloadTypeRegistry.playS2C().register(AirJumpParticlePayload.ID, AirJumpParticlePayload.CODEC);
		PayloadTypeRegistry.playS2C().register(DashParticlePayload.ID, DashParticlePayload.CODEC);
		PayloadTypeRegistry.playS2C().register(BlinkParticlePayload.ID, BlinkParticlePayload.CODEC);
		PayloadTypeRegistry.playS2C().register(BlinkSyncPayload.ID, BlinkSyncPayload.CODEC);
		// client
		PayloadTypeRegistry.playC2S().register(AirJumpPayload.ID, AirJumpPayload.CODEC);
		PayloadTypeRegistry.playC2S().register(DashPayload.ID, DashPayload.CODEC);
		PayloadTypeRegistry.playC2S().register(BlinkPayload.ID, BlinkPayload.CODEC);
		// server receivers
		ServerPlayNetworking.registerGlobalReceiver(AirJumpPayload.ID, new AirJumpPayload.Reciever());
		ServerPlayNetworking.registerGlobalReceiver(DashPayload.ID, new DashPayload.Reciever());
		ServerPlayNetworking.registerGlobalReceiver(BlinkPayload.ID, new BlinkPayload.Reciever());
	}
}