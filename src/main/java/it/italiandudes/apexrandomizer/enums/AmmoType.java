package it.italiandudes.apexrandomizer.enums;

import org.jetbrains.annotations.NotNull;

import java.util.Arrays;
import java.util.List;

public enum AmmoType {
    ENERGY("Munizioni Energetiche"),
    HEAVY("Munizioni Pesanti"),
    LIGHT("Munizioni Leggere"),
    SHOTGUN("Cartucce Shotgun"),
    SNIPER("Munizioni per Cecchino"),
    ARROW("Munizioni per Arco"),
    MYTHIC("Munizioni Mitiche");

    // Attributes
    @NotNull private final String name;

    // Static
    @NotNull private static final List<@NotNull AmmoType> AMMO_TYPES_LIST = Arrays.stream(AmmoType.values()).toList();
    public static @NotNull List<@NotNull AmmoType> getAmmoTypesList() {
        return AMMO_TYPES_LIST;
    }

    // Constructor
    AmmoType(@NotNull final String name) {
        this.name = name;
    }

    // Methods
    @Override @NotNull
    public String toString() {
        return name;
    }
}
