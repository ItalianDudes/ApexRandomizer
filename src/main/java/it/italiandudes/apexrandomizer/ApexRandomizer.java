package it.italiandudes.apexrandomizer;

import it.italiandudes.apexrandomizer.data.PlayerDataManager;
import it.italiandudes.apexrandomizer.javafx.Client;
import it.italiandudes.apexrandomizer.utils.Defs;
import it.italiandudes.idl.common.InfoFlags;
import it.italiandudes.idl.common.Logger;

import java.io.IOException;
import java.io.PrintStream;
import java.nio.charset.StandardCharsets;
import java.util.Arrays;
import java.util.function.Predicate;

public final class ApexRandomizer {

    // Main Method
    public static void main(String[] args) {

        if (Arrays.stream(args).anyMatch(Predicate.isEqual("-nogui"))) {
            ApexRandomizer_OLD.main(args);
            return;
        }

        // Setting Charset to UTF-8
        System.setOut(new PrintStream(System.out, true, StandardCharsets.UTF_8));
        System.setErr(new PrintStream(System.err, true, StandardCharsets.UTF_8));

        // Initializing the logger
        try {
            Logger.init();
            Logger.log("Logger initialized!", Defs.LOGGER_CONTEXT);
        } catch (IOException e) {
            Logger.log("An error has occurred during Logger initialization, exit...", Defs.LOGGER_CONTEXT);
            return;
        }

        // Configure the shutdown hooks
        Logger.log("Configuring Shutdown Hooks...", Defs.LOGGER_CONTEXT);
        Runtime.getRuntime().addShutdownHook(new Thread(Logger::close));
        Logger.log("Shutdown Hooks configured!", Defs.LOGGER_CONTEXT);

        // Pre-Load Players Data
        if (PlayerDataManager.getInstance() == null) {
            Logger.log("An error has occurred while loading player data!");
            exit(1);
        }

        // Starting UI
        try {
            Logger.log("Starting UI...", Defs.LOGGER_CONTEXT);
            Client.start(args);
        } catch (NoClassDefFoundError e) {
            Logger.log("ERROR: TO RUN THIS JAR YOU NEED JAVA 21!", new InfoFlags(true, true, true, true), Defs.LOGGER_CONTEXT);
            Logger.log(e, Defs.LOGGER_CONTEXT);
            exit(1);
        }
    }

    // Exit
    public static void exit(int code) {
        Logger.close();
        System.exit(code);
    }
}
