package it.italiandudes.apexrandomizer.enums;

import org.jetbrains.annotations.NotNull;

import java.util.Arrays;
import java.util.List;

public enum Legend {
    BANGALORE("Bangalore", LegendCategory.ASSAULT),
    FUSE("Fuse", LegendCategory.ASSAULT),
    ASH("Ash", LegendCategory.ASSAULT),
    MAD_MAGGIE("Mad Maggie", LegendCategory.ASSAULT),
    BALLISTIC("Ballistic", LegendCategory.ASSAULT, true),
    PATHFINDER("Pathfinder", LegendCategory.SKIRMISHER),
    WRAITH("Wraith", LegendCategory.SKIRMISHER),
    OCTANE("Octane", LegendCategory.SKIRMISHER),
    REVENANT("Revenant", LegendCategory.SKIRMISHER),
    HORIZON("Horizon", LegendCategory.SKIRMISHER),
    ALTER("Alter", LegendCategory.SKIRMISHER),
    BLOODHOUND("Bloodhound", LegendCategory.RECON),
    CRYPTO("Crypto", LegendCategory.RECON),
    VALKYRIE("Valkyrie", LegendCategory.RECON),
    SEER("Seer", LegendCategory.RECON),
    VANTAGE("Vantage", LegendCategory.RECON),
    GIBRALTAR("Gibraltar", LegendCategory.SUPPORT),
    LIFELINE("Lifeline", LegendCategory.SUPPORT),
    MIRAGE("Mirage", LegendCategory.SUPPORT),
    LOBA("Loba", LegendCategory.SUPPORT),
    NEWCASTLE("Newcastle", LegendCategory.SUPPORT),
    CONDUIT("Conduit", LegendCategory.SUPPORT),
    CAUSTIC("Caustic", LegendCategory.CONTROLLER),
    WATTSON("Wattson", LegendCategory.CONTROLLER),
    RAMPART("Rampart", LegendCategory.CONTROLLER),
    CATALYST("Catalyst", LegendCategory.CONTROLLER),
    SPARROW("Sparrow", LegendCategory.RECON);

    // Attributes
    @NotNull private final String name;
    @NotNull private final LegendCategory legendCategory;
    private final boolean hasSling;

    // Static
    @NotNull private static final List<@NotNull Legend> LEGENDS_LIST = Arrays.stream(Legend.values()).toList();
    public static @NotNull List<@NotNull Legend> getLegendsList() {
        return LEGENDS_LIST;
    }

    // Constructors
    Legend(@NotNull final String name, @NotNull final LegendCategory legendCategory, final boolean hasSling) {
        this.name = name;
        this.legendCategory = legendCategory;
        this.hasSling = hasSling;
    }
    Legend(@NotNull final String name, @NotNull final LegendCategory legendCategory) {
        this(name, legendCategory, false);
    }

    // Methods
    public @NotNull LegendCategory getLegendCategory() {
        return legendCategory;
    }
    public boolean hasSling() {
        return hasSling;
    }
    @Override @NotNull
    public String toString() {
        return name;
    }
}
