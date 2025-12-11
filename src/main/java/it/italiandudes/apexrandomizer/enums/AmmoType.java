package it.italiandudes.apexrandomizer.enums;

import org.jetbrains.annotations.NotNull;

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
