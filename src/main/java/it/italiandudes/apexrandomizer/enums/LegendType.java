package it.italiandudes.apexrandomizer.enums;

import org.jetbrains.annotations.NotNull;

public enum LegendType {
    ASSAULT("Assalto"),
    SKIRMISHER("Combattente"),
    RECON("Ricognizione"),
    SUPPORT("Supporto"),
    CONTROLLER("Controllo");

    // Attributes
    @NotNull private final String name;

    // Constructor
    LegendType(@NotNull final String name) {
        this.name = name;
    }

    // Methods
    @Override @NotNull
    public String toString() {
        return name;
    }
}
