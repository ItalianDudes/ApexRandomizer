package it.italiandudes.apexrandomizer.javafx.controllers;

import it.italiandudes.apexrandomizer.javafx.components.SceneController;
import it.italiandudes.apexrandomizer.javafx.controllers.tabs.ControllerSceneTabPlayersData;
import it.italiandudes.apexrandomizer.javafx.controllers.tabs.ControllerSceneTabRandomizer;
import it.italiandudes.apexrandomizer.javafx.scene.tabs.SceneTabPlayersData;
import it.italiandudes.apexrandomizer.javafx.scene.tabs.SceneTabRandomizer;
import javafx.fxml.FXML;
import javafx.scene.control.Tab;
import org.jetbrains.annotations.NotNull;

@SuppressWarnings("unused")
public final class ControllerSceneMain {

    // Graphic Elements
    @FXML private Tab tabRandomizer;
    @FXML private Tab tabPlayersData;

    // Scene Controllers
    private SceneController sceneControllerTabRandomizer;
    private SceneController sceneControllerTabPlayersData;

    // Initialize
    @FXML
    private void initialize() {
        sceneControllerTabRandomizer = SceneTabRandomizer.getScene(this);
        tabRandomizer.setContent(sceneControllerTabRandomizer.getParent());
        sceneControllerTabPlayersData = SceneTabPlayersData.getScene(this);
        tabPlayersData.setContent(sceneControllerTabPlayersData.getParent());
    }

    // Methods
    @NotNull
    public Tab getTabRandomizer() {
        return tabRandomizer;
    }
    @NotNull
    public Tab getTabPlayersData() {
        return tabPlayersData;
    }
    @NotNull
    public ControllerSceneTabRandomizer getTabRandomizerController() {
        return (ControllerSceneTabRandomizer) sceneControllerTabRandomizer.getController();
    }
    @NotNull
    public ControllerSceneTabPlayersData getTabPlayersDataController() {
        return (ControllerSceneTabPlayersData) sceneControllerTabPlayersData.getController();
    }
}
