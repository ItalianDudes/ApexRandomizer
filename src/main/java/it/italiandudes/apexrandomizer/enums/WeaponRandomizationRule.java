package it.italiandudes.apexrandomizer.enums;

import org.jetbrains.annotations.NotNull;

import java.util.Arrays;
import java.util.List;

public enum WeaponRandomizationRule {
    NO_WEAPON_RANDOMIZATION("Non Randomizzare le Armi"),
    RANDOMIZE_WEAPON("Randomizza le Armi"),
    RANDOMIZE_WEAPON_CATEGORY("Randomizza le Categorie Armi"),
    RANDOMIZE_WEAPON_AMMO("Randomizza i Tipi di Munizioni");

    // Attributes
    @NotNull private final String name;

    // Static
    @NotNull private static final List<@NotNull WeaponRandomizationRule> WEAPON_RANDOMIZATION_RULE_LIST = Arrays.stream(WeaponRandomizationRule.values()).toList();
    public static @NotNull List<@NotNull WeaponRandomizationRule> getWeaponRandomizationRuleList() {
        return WEAPON_RANDOMIZATION_RULE_LIST;
    }

    // Constructor
    WeaponRandomizationRule(@NotNull final String name) {
        this.name = name;
    }

    // Methods
    @Override @NotNull
    public String toString() {
        return name;
    }
}
