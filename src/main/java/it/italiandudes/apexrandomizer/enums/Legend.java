package it.italiandudes.apexrandomizer.enums;

import org.jetbrains.annotations.NotNull;

public enum Legend {
    BANGALORE("Bangalore", LegendType.ASSAULT),
    FUSE("Fuse", LegendType.ASSAULT),
    ASH("Ash", LegendType.ASSAULT),
    MAD_MAGGIE("Mad Maggie", LegendType.ASSAULT),
    BALLISTIC("Ballistic", LegendType.ASSAULT, true),
    PATHFINDER("Pathfinder", LegendType.SKIRMISHER),
    WRAITH("Wraith", LegendType.SKIRMISHER),
    OCTANE("Octane", LegendType.SKIRMISHER),
    REVENANT("Revenant", LegendType.SKIRMISHER),
    HORIZON("Horizon", LegendType.SKIRMISHER),
    ALTER("Alter", LegendType.SKIRMISHER),
    BLOODHOUND("Bloodhound", LegendType.RECON),
    CRYPTO("Crypto", LegendType.RECON),
    VALKYRIE("Valkyrie", LegendType.RECON),
    SEER("Seer", LegendType.RECON),
    VANTAGE("Vantage", LegendType.RECON),
    GIBRALTAR("Gibraltar", LegendType.SUPPORT),
    LIFELINE("Lifeline", LegendType.SUPPORT),
    MIRAGE("Mirage", LegendType.SUPPORT),
    LOBA("Loba", LegendType.SUPPORT),
    NEWCASTLE("Newcastle", LegendType.SUPPORT),
    CONDUIT("Conduit", LegendType.SUPPORT),
    CAUSTIC("Caustic", LegendType.CONTROLLER),
    WATTSON("Wattson", LegendType.CONTROLLER),
    RAMPART("Rampart", LegendType.CONTROLLER),
    CATALYST("Catalyst", LegendType.CONTROLLER),
    SPARROW("Sparrow", LegendType.RECON);

    // Attributes
    @NotNull private final String name;
    @NotNull private final LegendType legendType;
    private final boolean hasSling;

    // Constructors
    Legend(@NotNull final String name, @NotNull final LegendType legendType, final boolean hasSling) {
        this.name = name;
        this.legendType = legendType;
        this.hasSling = hasSling;
    }
    Legend(@NotNull final String name, @NotNull final LegendType legendType) {
        this(name, legendType, false);
    }

    // Methods
    public @NotNull LegendType getLegendType() {
        return legendType;
    }
    public boolean hasSling() {
        return hasSling;
    }
    @Override @NotNull
    public String toString() {
        return name;
    }
}
