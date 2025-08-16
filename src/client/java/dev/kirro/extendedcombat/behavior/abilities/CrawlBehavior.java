package dev.kirro.extendedcombat.behavior.abilities;

import dev.kirro.ExtendedcombatClient;
import dev.kirro.extendedcombat.behavior.abilities.payload.CrawlPayload;
import dev.kirro.extendedcombat.behavior.abilities.payload.CrawlSyncPayload;
import dev.kirro.extendedcombat.entity.components.ModEntityComponents;
import dev.kirro.extendedcombat.util.ExtendedCombatClientUtil;
import net.minecraft.entity.EntityPose;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.nbt.NbtCompound;
import net.minecraft.registry.RegistryWrapper;
import org.ladysnake.cca.api.v3.component.sync.AutoSyncedComponent;
import org.ladysnake.cca.api.v3.component.tick.CommonTickingComponent;

public class CrawlBehavior implements AutoSyncedComponent, CommonTickingComponent {
    private final PlayerEntity player;
    private static boolean crawling;

    public CrawlBehavior(PlayerEntity player) {
        this.player = player;
    }

    @Override
    public void readFromNbt(NbtCompound nbtCompound, RegistryWrapper.WrapperLookup wrapperLookup) {
        nbtCompound.getBoolean("Crawling");
    }

    @Override
    public void writeToNbt(NbtCompound nbtCompound, RegistryWrapper.WrapperLookup wrapperLookup) {
        nbtCompound.putBoolean("Crawling", crawling);
    }

    @Override
    public void tick() {
        if (ExtendedcombatClient.CRAWL.isPressed()) {
            crawling = true;
            sync();
        } else {
            reset();
        }
    }

    @Override
    public void clientTick() {
        tick();
        use();
        CrawlPayload.send(isCrawling());
        CrawlSyncPayload.setPlayerPose(player);
    }

    public void use() {
        player.setSwimming(crawling);
        ExtendedCombatClientUtil.setCrawling(player.getUuid(), crawling);
    }

    public void sync() {
        ModEntityComponents.CRAWL.sync(player);
    }

    public boolean isCrawling() {
        return crawling;

    }

    public void setCrawling(boolean isCrawling) {
        crawling = isCrawling;
    }

    public void reset() {
        crawling = false;
        sync();
    }
}
