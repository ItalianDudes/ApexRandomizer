package it.italiandudes.apexrandomizer.enums;

import org.jetbrains.annotations.NotNull;

import java.util.Arrays;
import java.util.List;

public enum Weapon {
    HAVOC("Havoc", WeaponCategory.ASSAULT_RIFLE, AmmoType.MYTHIC),
    FLATLINE("Flatline", WeaponCategory.ASSAULT_RIFLE, AmmoType.HEAVY),
    HEMLOK("Hemlok", WeaponCategory.ASSAULT_RIFLE, AmmoType.HEAVY),
    R301("R-301", WeaponCategory.ASSAULT_RIFLE, AmmoType.LIGHT),
    NEMESIS("Nemesis", WeaponCategory.ASSAULT_RIFLE, AmmoType.ENERGY),
    ALTERNATOR("Alternator", WeaponCategory.SMG, AmmoType.LIGHT),
    PROWLER("Prowler", WeaponCategory.SMG, AmmoType.HEAVY),
    R99("R-99", WeaponCategory.SMG, AmmoType.LIGHT),
    VOLT("Volt", WeaponCategory.SMG, AmmoType.ENERGY),
    CAR("C.A.R.", WeaponCategory.SMG, AmmoType.MYTHIC),
    DEVOTION("Devotion", WeaponCategory.LMG, AmmoType.ENERGY),
    LSTAR("L-STAR", WeaponCategory.LMG, AmmoType.ENERGY),
    SPITFIRE("Spitfire", WeaponCategory.LMG, AmmoType.LIGHT),
    RAMPAGE("Rampage", WeaponCategory.LMG, AmmoType.HEAVY),
    G7_SCOUT("G7 Scout", WeaponCategory.MARKSMAN, AmmoType.LIGHT),
    TRIPLE_TAKE("Triple Take", WeaponCategory.MARKSMAN, AmmoType.ENERGY),
    REPEATER_3030("30-30 Repeater", WeaponCategory.MARKSMAN, AmmoType.HEAVY),
    BOCEK("Bocek", WeaponCategory.MARKSMAN, AmmoType.ARROW),
    RADIUS("Radius", WeaponCategory.SNIPER, AmmoType.SNIPER),
    LONGBOW("Longbow", WeaponCategory.SNIPER, AmmoType.SNIPER),
    KRABER("Kraber", WeaponCategory.SNIPER, AmmoType.MYTHIC),
    SENTINEL("Sentinel", WeaponCategory.SNIPER, AmmoType.SNIPER),
    RE45("RE-45", WeaponCategory.PISTOL, AmmoType.ENERGY),
    P2020("P2020", WeaponCategory.PISTOL, AmmoType.MYTHIC),
    WINGMAN("Wingman", WeaponCategory.PISTOL, AmmoType.SNIPER),
    EVA8("EVA-8", WeaponCategory.SHOTGUN, AmmoType.SHOTGUN),
    MASTIFF("Mastiff", WeaponCategory.SHOTGUN, AmmoType.SHOTGUN),
    MOZAMBIQUE("Mozambique", WeaponCategory.SHOTGUN, AmmoType.SHOTGUN),
    PEACEKEEPER("Peacekeeper", WeaponCategory.SHOTGUN, AmmoType.SHOTGUN);

    // Attributes
    @NotNull private final String name;
    @NotNull private final WeaponCategory weaponCategory;
    @NotNull private final AmmoType ammoType;

    // Static
    @NotNull private static final List<@NotNull Weapon> WEAPONS_LIST = Arrays.stream(Weapon.values()).toList();
    public static @NotNull List<@NotNull Weapon> getWeaponsList() {
        return WEAPONS_LIST;
    }

    // Constructor
    Weapon(@NotNull final String name, @NotNull final WeaponCategory weaponCategory, @NotNull final AmmoType ammoType) {
        this.name = name;
        this.weaponCategory = weaponCategory;
        this.ammoType = ammoType;
    }

    // Methods
    public @NotNull WeaponCategory getWeaponCategory() {
        return weaponCategory;
    }
    public @NotNull AmmoType getAmmoType() {
        return ammoType;
    }
    @Override @NotNull
    public String toString() {
        return name;
    }
}
