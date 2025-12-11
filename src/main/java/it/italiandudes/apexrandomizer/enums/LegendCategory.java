package it.italiandudes.apexrandomizer.enums;

import org.jetbrains.annotations.NotNull;

import java.util.Arrays;
import java.util.List;

public enum LegendCategory {
    ASSAULT("Assalto"),
    SKIRMISHER("Combattente"),
    RECON("Ricognizione"),
    SUPPORT("Supporto"),
    CONTROLLER("Controllo");

    // Attributes
    @NotNull private final String name;

    // Static
    @NotNull private static final List<@NotNull LegendCategory> LEGEND_CATEGORIES_LIST = Arrays.stream(LegendCategory.values()).toList();
    public static @NotNull List<@NotNull LegendCategory> getLegendCategoriesList() {
        return LEGEND_CATEGORIES_LIST;
    }

    // Constructor
    LegendCategory(@NotNull final String name) {
        this.name = name;
    }

    // Methods
    @Override @NotNull
    public String toString() {
        return name;
    }
}
