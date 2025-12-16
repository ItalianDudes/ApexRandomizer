package it.italiandudes.apexrandomizer.javafx;

import it.italiandudes.apexrandomizer.utils.Defs;
import javafx.concurrent.Service;
import javafx.concurrent.Task;
import javafx.geometry.Rectangle2D;
import javafx.scene.image.Image;
import javafx.stage.Screen;
import org.jetbrains.annotations.NotNull;

@SuppressWarnings("unused")
public final class JFXDefs {

    // Service Starter
    public static void startServiceTask(@NotNull final Runnable runnable) {
        new Service<Void>() {
            @Override
            protected Task<Void> createTask() {
                return new Task<>() {
                    @Override
                    protected Void call() {
                        runnable.run();
                        return null;
                    }
                };
            }
        }.start();
    }

    // App Info
    public static final class AppInfo {
        public static final String NAME = "ApexRandomizer";
        public static final Image LOGO = new Image(Defs.Resources.get(Resources.Image.APP_LOGO).toString());
    }

    // System Info
    public static final class SystemGraphicInfo {
        public static final Rectangle2D SCREEN_RESOLUTION = Screen.getPrimary().getBounds();
        public static final double SCREEN_WIDTH = SCREEN_RESOLUTION.getWidth();
        public static final double SCREEN_HEIGHT = SCREEN_RESOLUTION.getHeight();
    }

    // Resource Locations
    public static final class Resources {

        // FXML Location
        public static final class FXML {
            private static final String FXML_DIR = Defs.Resources.PROJECT_RESOURCES_ROOT + "fxml/";
            public static final String FXML_MAIN = FXML_DIR + "SceneMain.fxml";
            public static final class Tabs {
                private static final String TABS_DIR = FXML_DIR + "tabs/";
                public static final String FXML_TAB_RANDOMIZER = TABS_DIR + "SceneTabRandomizer.fxml";
                public static final String FXML_TAB_PLAYER_PANE = TABS_DIR + "ScenePlayerPane.fxml";
                public static final String FXML_TAB_PLAYERS_DATA = TABS_DIR + "SceneTabPlayersData.fxml";
                public static final String FXML_TAB_SETTINGS = TABS_DIR + "SceneTabSettings.fxml";
            }
        }

        // GIFs
        public static final class GIF {
            private static final String GIF_DIR = Defs.Resources.PROJECT_RESOURCES_ROOT + "gif/";
            public static final javafx.scene.image.Image GIF_LOADING = new javafx.scene.image.Image(GIF_DIR + "loading.gif");
        }

        // CSS Location
        public static final class CSS {
            private static final String CSS_DIR = Defs.Resources.PROJECT_RESOURCES_ROOT + "css/";
            public static final String THEME = CSS_DIR + "theme.css";
        }

        // Image Location
        public static final class Image {
            private static final String IMAGE_DIR = Defs.Resources.PROJECT_RESOURCES_ROOT + "image/";
            public static final String APP_LOGO = IMAGE_DIR + "app-logo.png";
            public static final String IMAGE_FILE_EXPLORER = IMAGE_DIR + "file-explorer.png";
            public static final String IMAGE_PLAY = IMAGE_DIR + "play.png";
            public static final String IMAGE_STOP = IMAGE_DIR + "stop.png";
            public static final javafx.scene.image.Image IMAGE_TICK = new javafx.scene.image.Image(IMAGE_DIR + "tick.png");
            public static final javafx.scene.image.Image IMAGE_CROSS = new javafx.scene.image.Image(IMAGE_DIR + "cross.png");
            public static final javafx.scene.image.Image IMAGE_WHITELIST = new javafx.scene.image.Image(IMAGE_DIR + "whitelist.png");
            public static final javafx.scene.image.Image IMAGE_BLACKLIST = new javafx.scene.image.Image(IMAGE_DIR + "blacklist.png");
        }
    }
}
