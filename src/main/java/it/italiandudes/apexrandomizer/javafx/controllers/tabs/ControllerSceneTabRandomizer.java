package it.italiandudes.apexrandomizer.javafx.controllers.tabs;

import it.italiandudes.apexrandomizer.data.PlayerData;
import it.italiandudes.apexrandomizer.data.PlayerDataManager;
import it.italiandudes.apexrandomizer.javafx.JFXDefs;
import it.italiandudes.apexrandomizer.javafx.components.SceneController;
import it.italiandudes.apexrandomizer.javafx.controllers.ControllerSceneMain;
import it.italiandudes.apexrandomizer.javafx.scene.tabs.ScenePlayerPane;
import it.italiandudes.apexrandomizer.utils.Randomizer;
import javafx.application.Platform;
import javafx.fxml.FXML;
import javafx.scene.control.ToggleButton;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.*;
import org.jetbrains.annotations.NotNull;

import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;
import java.util.stream.Collectors;

@SuppressWarnings("unused")
public final class ControllerSceneTabRandomizer {

    // Main Link
    private ControllerSceneMain mainMenuController = null;
    private volatile boolean configurationComplete;
    public void setMainMenuController(@NotNull final ControllerSceneMain controller) {
        mainMenuController = controller;
    }
    public @NotNull ControllerSceneMain getMainMenuController() {
        return mainMenuController;
    }
    public void configurationComplete() {
        configurationComplete = true;
    }

    // Constants
    @NotNull private static final Image PLAY = new Image(JFXDefs.Resources.Image.IMAGE_PLAY);
    @NotNull private static final Image STOP = new Image(JFXDefs.Resources.Image.IMAGE_STOP);

    // Graphic Elements
    @FXML private VBox vBoxPlayersContainer;
    @FXML private ToggleButton toggleButtonRandomizeAll;
    @FXML private ImageView imageViewToggleRandomizeAll;

    // Attributes
    @NotNull private final ArrayList<@NotNull SceneController> playersSceneControllers = new ArrayList<>();
    private List<@NotNull SceneController> sortedPlayersSceneControllers = null;

    // Initialize
    @FXML
    private void initialize() {
        toggleButtonRandomizeAll.selectedProperty().addListener((observable, oldValue, newValue) -> imageViewToggleRandomizeAll.setImage(newValue?STOP:PLAY));
        JFXDefs.startServiceTask(() -> {
            while (!configurationComplete) Thread.onSpinWait();
            Platform.runLater(this::loadPlayerScenes);
        });
    }

    // Methods
    public void loadPlayerScenes() {
        vBoxPlayersContainer.getChildren().removeIf(node -> node instanceof AnchorPane);
        playersSceneControllers.clear();
        for (PlayerData playerData : getPlayersEnabledForRandomizer()) {
            SceneController newPlayer = ScenePlayerPane.getScene(this, playerData);
            playersSceneControllers.add(newPlayer);
            vBoxPlayersContainer.getChildren().add(newPlayer.getParent());
        }
        /*
        sortedPlayersSceneControllers = playersSceneControllers.stream().sorted((o1, o2) -> {
            ControllerScenePlayerPane p1 = (ControllerScenePlayerPane) o1.getController();
            ControllerScenePlayerPane p2 = (ControllerScenePlayerPane) o2.getController();
            return Integer.compare(p2.getPlayerData().getLegends().size(), p1.getPlayerData().getLegends().size()) * -1;
        }).collect(Collectors.toList());*/
        sortedPlayersSceneControllers = playersSceneControllers;
    }
    @NotNull
    public List<@NotNull PlayerData> getPlayersEnabledForRandomizer() {
        return PlayerDataManager.getInstance().getPlayersData().stream()
                .filter(PlayerData::isPlayerEnabled)
                .sorted(Comparator.comparing(PlayerData::getUsername))
                .collect(Collectors.toList());
    }
    private void randomizeAll() {
        for (SceneController sceneHelldiverPane : sortedPlayersSceneControllers) {
            Randomizer.RANDOMIZED_LEGENDS_BUFFER.clear();
            ((ControllerScenePlayerPane) sceneHelldiverPane.getController()).randomizeAll();
        }
    }

    // EDT
    @FXML
    private void toggleRandomizeAll() {
        if (!toggleButtonRandomizeAll.isSelected()) return;
        randomizeAll();
        if (toggleButtonRandomizeAll.isSelected()) Platform.runLater(this::toggleRandomizeAll);
    }
}
