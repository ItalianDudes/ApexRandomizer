package it.italiandudes.apexrandomizer;

import it.italiandudes.apexrandomizer.enums.Legend;
import it.italiandudes.apexrandomizer.enums.Weapon;

import java.util.Random;

public final class Randomizer {

    // Public
    public static Legend randomizeLegend() {
        return Legend.values()[randomFromZeroTo(Legend.values().length)];
    }
    public static Weapon randomizeWeapon() {
        return Weapon.values()[randomFromZeroTo(Weapon.values().length)];
    }

    // Attributes
    private static final Random RANDOMIZER = new Random();
    private static int randomFromZeroTo(int max) { // 0 Included to Max excluded
        return RANDOMIZER.nextInt(max);
    }
}
