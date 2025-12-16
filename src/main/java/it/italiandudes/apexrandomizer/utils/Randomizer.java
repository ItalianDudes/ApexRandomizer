package it.italiandudes.apexrandomizer.utils;

import it.italiandudes.apexrandomizer.data.PlayerData;
import it.italiandudes.apexrandomizer.enums.*;
import org.jetbrains.annotations.NotNull;

import java.util.ArrayList;
import java.util.Random;

public final class Randomizer {

    // Legend Buffer
    public static final @NotNull ArrayList<@NotNull Legend> RANDOMIZED_LEGENDS_BUFFER = new ArrayList<>();

    // Randomization
    public static @NotNull Legend randomizeLegendFromPlayerData(@NotNull final PlayerData playerData) {
        return playerData.getLegends().get(randomFromZeroTo(playerData.getLegends().size()));
    }
    public static @NotNull LegendCategory randomizeLegendCategoryFromPlayerData(@NotNull final PlayerData playerData) {
        return playerData.getLegendCategories().get(randomFromZeroTo(playerData.getLegendCategories().size()));
    }
    public static @NotNull WeaponCategory randomizeWeaponCategoryFromPlayerData(@NotNull final PlayerData playerData) {
        return playerData.getWeaponCategories().get(randomFromZeroTo(playerData.getWeaponCategories().size()));
    }
    public static @NotNull AmmoType randomizeAmmoTypeFromPlayerData(@NotNull final PlayerData playerData) {
        return playerData.getAmmoTypes().get(randomFromZeroTo(playerData.getAmmoTypes().size()));
    }
    public static @NotNull Weapon randomizeWeaponFromPlayerData(@NotNull final PlayerData playerData) {
        return playerData.getWeapons().get(randomFromZeroTo(playerData.getWeapons().size()));
    }

    // Old Methods
    public static @NotNull Legend randomizeLegend() {
        return Legend.values()[randomFromZeroTo(Legend.values().length)];
    }
    public static @NotNull Weapon randomizeWeapon() {
        return Weapon.values()[randomFromZeroTo(Weapon.values().length)];
    }

    // Attributes
    private static final Random RANDOMIZER = new Random();
    private static int randomFromZeroTo(int max) { // 0 Included to Max excluded
        return RANDOMIZER.nextInt(max);
    }
}
