package it.italiandudes.apexrandomizer.data;

import it.italiandudes.apexrandomizer.utils.Defs;
import it.italiandudes.idl.common.JSONManager;
import it.italiandudes.idl.common.JarHandler;
import it.italiandudes.idl.common.Logger;
import org.jetbrains.annotations.NotNull;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

public final class PlayerDataProvider {

    // Attributes
    public final List<PlayerData> playerDataList = new ArrayList<>();

    // Constructors
    private PlayerDataProvider() {
        File playerDataFile = new File(new File(Defs.JAR_POSITION).getParent() + File.separator + Defs.Resources.JSON.JSON_PLAYER_DATA);
        if (!playerDataFile.exists() || !playerDataFile.isFile()) {
            try {
                JarHandler.copyFileFromJar(new File(Defs.JAR_POSITION), Defs.Resources.JSON.DEFAULT_JSON_PLAYER_DATA, playerDataFile, true);
            } catch (IOException e) {
                Logger.log(e, Defs.LOGGER_CONTEXT);
                System.exit(1);
            }
        }

        JSONArray jsonPlayerDataArray;
        try {
            JSONObject jsonPlayerDataObject = JSONManager.readJSON(playerDataFile);
            jsonPlayerDataArray = jsonPlayerDataObject.getJSONArray("player_data");
        } catch (Exception e) {
            Logger.log(e, Defs.LOGGER_CONTEXT);
            System.exit(1);
            return;
        }

        for (int i=0; i<jsonPlayerDataArray.length(); i++) {
            try {
                JSONObject jsonPlayerData = jsonPlayerDataArray.getJSONObject(i);
                playerDataList.add(new PlayerData(jsonPlayerData));
            } catch (JSONException e) {
                Logger.log("Trovata voce dati giocatore invalida all'indice [" + i + "], passando al prossimo...", Defs.LOGGER_CONTEXT);
            }
        }
        Logger.log("Caricati correttamente i dati di " + playerDataList.size() + " giocatori!", Defs.LOGGER_CONTEXT);
    }

    // Methods
    public @NotNull List<@NotNull PlayerData> getPlayerDataList() {
        return playerDataList;
    }

    // Instance
    private static PlayerDataProvider instance = null;
    public static PlayerDataProvider getInstance() {
        if (instance == null) {
            instance = new PlayerDataProvider();
        }
        return instance;
    }
}
