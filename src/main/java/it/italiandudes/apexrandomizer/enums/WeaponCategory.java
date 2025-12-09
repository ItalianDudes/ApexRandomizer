package it.italiandudes.apexrandomizer.enums;

import org.jetbrains.annotations.NotNull;

public enum WeaponCategory {
    ASSAULT_RIFLE("Fucile d'Assalto"),
    SMG("SMG"),
    LMG("LMG"),
    MARKSMAN("Fucile da Tiratore"),
    SNIPER("Fucile da Cecchino"),
    PISTOL("Pistola"),
    SHOTGUN("Fucile a Pompa")
    ;

    // Attributes
    @NotNull private final String name;

    // Constructor
    WeaponCategory(@NotNull final String name) {
        this.name = name;
    }

    // Methods
    @Override @NotNull
    public String toString() {
        return name;
    }
}
