package dev.kirro.extendedcombat.sound;

import dev.kirro.ExtendedCombat;
import net.minecraft.registry.Registries;
import net.minecraft.registry.Registry;
import net.minecraft.sound.BlockSoundGroup;
import net.minecraft.sound.SoundEvent;
import net.minecraft.sound.SoundEvents;
import net.minecraft.util.Identifier;

public class ModSounds {
    public static final SoundEvent NETHER_STEEL_BLOCK_BREAK = registerSoundEvent("nether_steel_block_break");
    public static final SoundEvent NETHER_STEEL_BLOCK_STEP = registerSoundEvent("nether_steel_block_step");
    public static final SoundEvent NETHER_STEEL_BLOCK_PLACE = registerSoundEvent("nether_steel_block_place");
    public static final SoundEvent NETHER_STEEL_BLOCK_HIT = registerSoundEvent("nether_steel_block_hit");
    public static final SoundEvent NETHER_STEEL_BLOCK_FALL = registerSoundEvent("nether_steel_block_fall");

    public static final SoundEvent ECHO_STEEL_BLOCK_BREAK = registerSoundEvent("echo_steel_block_break");
    public static final SoundEvent ECHO_STEEL_BLOCK_STEP = registerSoundEvent("echo_steel_block_step");
    public static final SoundEvent ECHO_STEEL_BLOCK_PLACE = registerSoundEvent("echo_steel_block_place");
    public static final SoundEvent ECHO_STEEL_BLOCK_HIT = registerSoundEvent("echo_steel_block_hit");
    public static final SoundEvent ECHO_STEEL_BLOCK_FALL = registerSoundEvent("echo_steel_block_fall");

    public static final BlockSoundGroup NETHER_STEEL_BLOCK = new BlockSoundGroup(1f, 1f,
            NETHER_STEEL_BLOCK_BREAK,
            NETHER_STEEL_BLOCK_STEP,
            NETHER_STEEL_BLOCK_PLACE,
            NETHER_STEEL_BLOCK_HIT,
            NETHER_STEEL_BLOCK_FALL);

    public static final BlockSoundGroup ECHO_STEEL_BLOCK = new BlockSoundGroup(1f, 1f,
            ECHO_STEEL_BLOCK_BREAK,
            ECHO_STEEL_BLOCK_STEP,
            ECHO_STEEL_BLOCK_PLACE,
            ECHO_STEEL_BLOCK_HIT,
            ECHO_STEEL_BLOCK_FALL);


    private static SoundEvent registerSoundEvent(String name) {
        Identifier id = Identifier.of(ExtendedCombat.MOD_ID, name);
        return Registry.register(Registries.SOUND_EVENT, id, SoundEvent.of(id));
    }

    public static void registerSounds() {
        ExtendedCombat.LOGGER.info("Registering sounds for " + ExtendedCombat.MOD_ID);
    }
}
