package it.italiandudes.apexrandomizer.javafx;

import it.italiandudes.apexrandomizer.javafx.components.SceneController;
import it.italiandudes.apexrandomizer.javafx.scene.SceneMain;
import it.italiandudes.apexrandomizer.javafx.utils.ThemeHandler;
import it.italiandudes.apexrandomizer.utils.Defs;
import it.italiandudes.idl.common.Logger;
import javafx.application.Application;
import javafx.application.Platform;
import javafx.scene.Scene;
import javafx.scene.input.Clipboard;
import javafx.scene.input.KeyCode;
import javafx.scene.input.KeyEvent;
import javafx.stage.Modality;
import javafx.stage.Stage;
import javafx.stage.WindowEvent;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

import java.util.Objects;

@SuppressWarnings("unused")
public final class Client extends Application {

    // Attributes
    private static Clipboard SYSTEM_CLIPBOARD = null;
    private static Stage STAGE = null;
    private static SceneController SCENE = null;

    // Initial Stage Configuration
    @Override
    public void start(@NotNull final Stage stage) {
        Logger.log("Initializing JavaFX Stage...", Defs.LOGGER_CONTEXT);
        SYSTEM_CLIPBOARD = Clipboard.getSystemClipboard();
        Client.STAGE = stage;
        stage.setResizable(true);
        stage.setTitle(JFXDefs.AppInfo.NAME);
        stage.getIcons().add(JFXDefs.AppInfo.LOGO);
        SCENE = SceneMain.getScene();
        stage.setScene(new Scene(SCENE.getParent()));
        Logger.log("Loading Theme...", Defs.LOGGER_CONTEXT);
        ThemeHandler.loadTheme(stage.getScene());
        stage.show();
        Logger.log("JavaFX Stage Initialized! Post initialization...", Defs.LOGGER_CONTEXT);
        stage.setX((JFXDefs.SystemGraphicInfo.SCREEN_WIDTH - stage.getWidth()) / 2);
        stage.setY((JFXDefs.SystemGraphicInfo.SCREEN_HEIGHT - stage.getHeight()) / 2);
        stage.getScene().getWindow().addEventHandler(WindowEvent.WINDOW_CLOSE_REQUEST, event -> exit());

        // Fullscreen Event Listener
        stage.addEventFilter(KeyEvent.KEY_PRESSED, (KeyEvent event) -> {
            if (event.getCode() == KeyCode.F11) {
                if (!stage.isFullScreen()) {
                    if (!stage.isMaximized()) {
                        stage.setMaximized(true);
                    }
                    stage.setFullScreen(true);
                }
            } else if (event.getCode() == KeyCode.ESCAPE) {
                if (stage.isFullScreen()) stage.setFullScreen(false);
            }
        });

        // Notice into the logs that the application started Successfully
        Logger.log("Post completed, D&D Visualizer started successfully!", Defs.LOGGER_CONTEXT);
    }

    // Start Method
    public static void start(@NotNull final String[] args) {
        launch(args);
    }

    // Methods
    @NotNull
    public static Clipboard getSystemClipboard() {
        return SYSTEM_CLIPBOARD;
    }
    @NotNull
    public static Stage getStage(){
        return STAGE;
    }
    @NotNull
    public static SceneController getScene() {
        return SCENE;
    }
    public static void setScene(@Nullable final SceneController newScene) {
        if (newScene == null) {
            Client.exit(-1);
            return;
        }
        SCENE = newScene;
        STAGE.getScene().setRoot(newScene.getParent());
    }
    @NotNull
    public static Stage initPopupStage(@Nullable final SceneController sceneController) {
        return genPopupStage(Objects.requireNonNull(sceneController));
    }
    @NotNull
    private static Stage genPopupStage(@NotNull final SceneController sceneController) {
        Stage popupStage = new Stage();
        popupStage.setResizable(true);
        popupStage.getIcons().add(JFXDefs.AppInfo.LOGO);
        popupStage.setTitle(JFXDefs.AppInfo.NAME);
        popupStage.initOwner(STAGE);
        popupStage.initModality(Modality.WINDOW_MODAL);
        popupStage.setScene(new Scene(sceneController.getParent()));
        ThemeHandler.loadTheme(popupStage.getScene());
        return popupStage;
    }
    public static void exit() {
        exit(0);
    }
    public static void exit(final int code) {
        if (code != 0) {
            Logger.log("Exit Method Called with non-zero code " + code + ", this probably means an error has occurred.", Defs.LOGGER_CONTEXT);
        } else {
            Logger.log("Exit Method Called, exiting HellRandomizer...", Defs.LOGGER_CONTEXT);
        }
        Platform.runLater(() -> STAGE.hide());
        Logger.close();
        System.exit(0);
    }
}
