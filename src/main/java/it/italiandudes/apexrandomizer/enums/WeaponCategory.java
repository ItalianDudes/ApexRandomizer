package it.italiandudes.apexrandomizer.enums;

import org.jetbrains.annotations.NotNull;

import java.util.Arrays;
import java.util.List;

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

    // Static
    @NotNull private static final List<@NotNull WeaponCategory> WEAPON_CATEGORIES_LIST = Arrays.stream(WeaponCategory.values()).toList();
    public static @NotNull List<@NotNull WeaponCategory> getWeaponCategoriesList() {
        return WEAPON_CATEGORIES_LIST;
    }

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
