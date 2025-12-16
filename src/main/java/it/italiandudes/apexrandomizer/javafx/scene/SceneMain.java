package it.italiandudes.apexrandomizer.javafx.scene;

import it.italiandudes.apexrandomizer.javafx.JFXDefs;
import it.italiandudes.apexrandomizer.javafx.components.SceneController;
import it.italiandudes.apexrandomizer.javafx.controllers.ControllerSceneMain;
import it.italiandudes.apexrandomizer.utils.Defs;
import it.italiandudes.idl.common.Logger;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import org.jetbrains.annotations.NotNull;

import java.io.IOException;

public final class SceneMain {

    // Scene Generator
    @NotNull
    public static SceneController getScene() {
        try {
            FXMLLoader loader = new FXMLLoader(Defs.Resources.get(JFXDefs.Resources.FXML.FXML_MAIN));
            Parent root = loader.load();
            ControllerSceneMain controller = loader.getController();
            return new SceneController(root, controller);
        } catch (IOException e) {
            Logger.log(e);
            Logger.close();
            System.exit(-1);
            return null;
        }
    }
}
