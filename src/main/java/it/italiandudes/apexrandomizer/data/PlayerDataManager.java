package it.italiandudes.apexrandomizer.data;

import it.italiandudes.apexrandomizer.ApexRandomizer;
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

public final class PlayerDataManager {

    // Attributes
    public final List<PlayerData> playerDataList;

    // Constructors
    private PlayerDataManager() {
        File playerDataFile = new File(new File(Defs.JAR_POSITION).getParent() + File.separator + Defs.Resources.JSON.JSON_PLAYER_DATA);
        if (!playerDataFile.exists() || !playerDataFile.isFile()) {
            try {
                JarHandler.copyFileFromJar(new File(Defs.JAR_POSITION), Defs.Resources.JSON.DEFAULT_JSON_PLAYER_DATA, playerDataFile, true);
            } catch (IOException e) {
                Logger.log(e, Defs.LOGGER_CONTEXT);
                playerDataList = null;
                ApexRandomizer.exit(1);
                return;
            }
        }

        JSONArray jsonPlayerDataArray;
        try {
            JSONObject jsonPlayerDataObject = JSONManager.readJSON(playerDataFile);
            jsonPlayerDataArray = jsonPlayerDataObject.getJSONArray("player_data");
        } catch (Exception e) {
            Logger.log(e, Defs.LOGGER_CONTEXT);
            playerDataList = null;
            return;
        }

        List<PlayerData> playerDataList = new ArrayList<>();
        for (int i=0; i<jsonPlayerDataArray.length(); i++) {
            try {
                JSONObject jsonPlayerData = jsonPlayerDataArray.getJSONObject(i);
                playerDataList.add(new PlayerData(jsonPlayerData));
            } catch (JSONException e) {
                Logger.log("Found invalid player data at index [" + i + "], skipping player data...", Defs.LOGGER_CONTEXT);
            }
        }
        this.playerDataList = playerDataList;
        Logger.log("Correctly loaded data for " + playerDataList.size() + " players!", Defs.LOGGER_CONTEXT);
    }

    // Methods
    public @NotNull List<@NotNull PlayerData> getPlayersData() {
        return playerDataList;
    }
    public void savePlayerData() throws IOException {
        JSONObject playerDataJSON = new JSONObject();
        JSONArray playerDataArray = new JSONArray();
        for (PlayerData playerData : playerDataList) {
            playerDataArray.put(playerData.toJSON());
        }
        playerDataJSON.put("player_data", playerDataArray);
        JSONManager.writeJSON(playerDataJSON, new File(new File(Defs.JAR_POSITION).getParent() + File.separator + Defs.Resources.JSON.JSON_PLAYER_DATA));
    }

    // Instance
    private static PlayerDataManager instance = null;
    public static PlayerDataManager getInstance() {
        if (instance == null) {
            instance = new PlayerDataManager();
            if (instance.playerDataList == null) instance = null;
        }
        return instance;
    }
}
