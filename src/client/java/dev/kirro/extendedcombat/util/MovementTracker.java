package dev.kirro.extendedcombat.util;

public interface MovementTracker {
    void setImmunityTicks(int ticks);
    int getImmunityTicks();
    void setUsedMidair(boolean usedMidair);
    boolean getUsedMidair();
}
