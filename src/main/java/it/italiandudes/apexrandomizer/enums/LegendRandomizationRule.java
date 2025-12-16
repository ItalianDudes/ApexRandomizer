package it.italiandudes.apexrandomizer.enums;

import org.jetbrains.annotations.NotNull;

import java.util.Arrays;
import java.util.List;

public enum LegendRandomizationRule {
    NO_LEGEND_RANDOMIZATION("Non Randomizzare la Leggenda"),
    RANDOMIZE_LEGEND("Randomizza la Leggenda"),
    RANDOMIZE_LEGEND_CATEGORY("Randomizza la Categoria Leggenda");

    // Attributes
    @NotNull private final String name;

    // Static
    @NotNull private static final List<@NotNull LegendRandomizationRule> LEGEND_RANDOMIZATION_RULE_LIST = Arrays.stream(LegendRandomizationRule.values()).toList();
    public static @NotNull List<@NotNull LegendRandomizationRule> getLegendRandomizationRuleList() {
        return LEGEND_RANDOMIZATION_RULE_LIST;
    }

    // Constructor
    LegendRandomizationRule(@NotNull final String name) {
        this.name = name;
    }

    // Methods
    @Override @NotNull
    public String toString() {
        return name;
    }
}
