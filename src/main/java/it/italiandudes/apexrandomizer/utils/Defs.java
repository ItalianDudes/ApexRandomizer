package it.italiandudes.apexrandomizer.utils;

import it.italiandudes.apexrandomizer.ApexRandomizer;
import org.jetbrains.annotations.NotNull;

import java.io.File;
import java.io.InputStream;
import java.net.URISyntaxException;
import java.net.URL;
import java.util.Objects;

public final class Defs {

    // Logger Context
    public static final String LOGGER_CONTEXT = "Apex Randomizer";

    // Jar App Position
    public static final String JAR_POSITION;
    static {
        try {
            JAR_POSITION = new File(ApexRandomizer.class.getProtectionDomain().getCodeSource().getLocation().toURI()).getAbsolutePath();
        } catch (URISyntaxException e) {
            throw new RuntimeException(e);
        }
    }

    // Resources Location
    public static final class Resources {

        // Project Resources Root
        public static final String PROJECT_RESOURCES_ROOT = "/it/italiandudes/apexrandomizer/resources/";

        //Resource Getters
        public static URL get(@NotNull final String resourceConst) {
            return Objects.requireNonNull(ApexRandomizer.class.getResource(resourceConst));
        }
        public static InputStream getAsStream(@NotNull final String resourceConst) {
            return Objects.requireNonNull(ApexRandomizer.class.getResourceAsStream(resourceConst));
        }

        // JSON
        public static final class JSON {
            public static final String JSON_PLAYER_DATA = "player_data.json";
            public static final String DEFAULT_JSON_PLAYER_DATA = PROJECT_RESOURCES_ROOT + "json/" + JSON_PLAYER_DATA;
        }
    }
}
