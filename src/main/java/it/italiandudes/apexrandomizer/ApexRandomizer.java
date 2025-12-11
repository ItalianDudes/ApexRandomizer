package it.italiandudes.apexrandomizer;

import it.italiandudes.apexrandomizer.enums.Legend;
import it.italiandudes.apexrandomizer.utils.Randomizer;
import org.jetbrains.annotations.NotNull;

import java.io.PrintStream;
import java.nio.charset.StandardCharsets;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.function.Predicate;

public final class ApexRandomizer {

    // Constants
    public static final HashMap<@NotNull String,@NotNull Legend[]> PLAYER_EXCLUDED_LEGENDS = new HashMap<>();
    static {
        PLAYER_EXCLUDED_LEGENDS.put("Alessio", new Legend[] {});
        PLAYER_EXCLUDED_LEGENDS.put("Jennifer", new Legend[] {});
    }

    // Main Method
    public static void main(String[] args) {

        // TODO: args --add-player | --delete-player | --edit-player

        // Setting Charset to UTF-8
        System.setOut(new PrintStream(System.out, true, StandardCharsets.UTF_8));
        System.setErr(new PrintStream(System.err, true, StandardCharsets.UTF_8));

        final ArrayList<@NotNull Legend> PICKED_LEGENDS = new ArrayList<>();
        for (String player : PLAYER_EXCLUDED_LEGENDS.keySet()) {
            Legend legend = null;
            while (legend == null || PICKED_LEGENDS.contains(legend) || Arrays.stream(PLAYER_EXCLUDED_LEGENDS.get(player)).anyMatch(Predicate.isEqual(legend))) {
                legend = Randomizer.randomizeLegend();
            }
            PICKED_LEGENDS.add(legend);
            System.out.println("Giocatore: " + player);
            System.out.println("\t-Leggenda: " + legend);
            System.out.println("\t-Armi:");
            System.out.println("\t\t- " + Randomizer.randomizeWeapon());
            System.out.println("\t\t- " + Randomizer.randomizeWeapon());
            if (legend.hasSling()) System.out.println("\t\t- " + Randomizer.randomizeWeapon() + " [TRACOLLA]");
            System.out.println("\n");
        }
    }
}
