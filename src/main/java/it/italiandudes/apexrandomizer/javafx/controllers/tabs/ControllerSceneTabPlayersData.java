package it.italiandudes.apexrandomizer.javafx.controllers.tabs;

import it.italiandudes.apexrandomizer.data.PlayerData;
import it.italiandudes.apexrandomizer.data.PlayerDataManager;
import it.italiandudes.apexrandomizer.enums.*;
import it.italiandudes.apexrandomizer.javafx.Client;
import it.italiandudes.apexrandomizer.javafx.JFXDefs;
import it.italiandudes.apexrandomizer.javafx.alerts.ErrorAlert;
import it.italiandudes.apexrandomizer.javafx.alerts.InformationAlert;
import it.italiandudes.apexrandomizer.javafx.controllers.ControllerSceneMain;
import it.italiandudes.idl.common.Logger;
import javafx.application.Platform;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.collections.transformation.FilteredList;
import javafx.fxml.FXML;
import javafx.scene.control.*;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.input.ContextMenuEvent;
import javafx.scene.layout.GridPane;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

import java.io.IOException;

public final class ControllerSceneTabPlayersData {

    // Main Link
    private ControllerSceneMain mainMenuController = null;
    private volatile boolean configurationComplete;

    public void setMainMenuController(@NotNull final ControllerSceneMain controller) {
        mainMenuController = controller;
    }

    public void configurationComplete() {
        configurationComplete = true;
    }

    // Constants
    @NotNull private static final Image TICK = JFXDefs.Resources.Image.IMAGE_TICK;
    @NotNull private static final Image CROSS = JFXDefs.Resources.Image.IMAGE_CROSS;

    // Attributes
    private boolean initializationCompleted = false;
    @Nullable private String newPlayer = null;

    @NotNull private final ObservableList<LegendCategory> legendCategories = FXCollections.observableArrayList();
    @NotNull private final FilteredList<LegendCategory> filteredLegendCategories = new FilteredList<>(
            this.legendCategories,
            legendCategory -> true
    );
    @NotNull private final ObservableList<LegendCategory> playerLegendCategories = FXCollections.observableArrayList();
    @NotNull private final FilteredList<LegendCategory> filteredPlayerLegendCategories = new FilteredList<>(
            this.playerLegendCategories,
            legendCategory -> true
    );

    @NotNull private final ObservableList<Legend> legends = FXCollections.observableArrayList();
    @NotNull private final FilteredList<Legend> filteredLegends = new FilteredList<>(
            this.legends,
            legend -> true
    );
    @NotNull private final ObservableList<Legend> playerLegends = FXCollections.observableArrayList();
    @NotNull private final FilteredList<Legend> filteredPlayerLegends = new FilteredList<>(
            this.playerLegends,
            legend -> true
    );

    @NotNull private final ObservableList<WeaponCategory> weaponCategories = FXCollections.observableArrayList();
    @NotNull private final FilteredList<WeaponCategory> filteredWeaponCategories = new FilteredList<>(
            this.weaponCategories,
            weaponCategory -> true
    );
    @NotNull private final ObservableList<WeaponCategory> playerWeaponCategories = FXCollections.observableArrayList();
    @NotNull private final FilteredList<WeaponCategory> filteredPlayerWeaponCategories = new FilteredList<>(
            this.playerWeaponCategories,
            weaponCategory -> true
    );

    @NotNull private final ObservableList<AmmoType> ammoTypes = FXCollections.observableArrayList();
    @NotNull private final FilteredList<AmmoType> filteredAmmoTypes = new FilteredList<>(
            this.ammoTypes,
            ammoType -> true
    );
    @NotNull private final ObservableList<AmmoType> playerAmmoTypes = FXCollections.observableArrayList();
    @NotNull private final FilteredList<AmmoType> filteredPlayerAmmoTypes = new FilteredList<>(
            this.playerAmmoTypes,
            ammoType -> true
    );

    @NotNull private final ObservableList<Weapon> weapons = FXCollections.observableArrayList();
    @NotNull private final FilteredList<Weapon> filteredWeapons = new FilteredList<>(
            this.weapons,
            weapon -> true
    );
    @NotNull private final ObservableList<Weapon> playerWeapons = FXCollections.observableArrayList();
    @NotNull private final FilteredList<Weapon> filteredPlayerWeapons = new FilteredList<>(
            this.playerWeapons,
            weapon -> true
    );

    // Graphic Elements
    @FXML private ListView<PlayerData> listViewPlayersData;

    @FXML private GridPane gridPanePlayersDataContainer;
    @FXML private GridPane gridPanePlayerData;

    @FXML private ToggleButton toggleButtonRandomizePlayer;
    @FXML private ImageView imageViewToggleRandomizePlayer;

    @FXML private ComboBox<LegendRandomizationRule> comboBoxLegendRandomizationRule;
    @FXML private ComboBox<WeaponRandomizationRule> comboBoxWeaponsRandomizationRule;

    @FXML private TextField textFieldSearchFromPlayerLegendCategories;
    @FXML private TextField textFieldSearchFromLegendCategories;
    @FXML private ListView<LegendCategory> listViewPlayerLegendCategories;
    @FXML private ListView<LegendCategory> listViewLegendCategories;

    @FXML private TextField textFieldSearchFromPlayerLegends;
    @FXML private TextField textFieldSearchFromLegends;
    @FXML private ListView<Legend> listViewPlayerLegends;
    @FXML private ListView<Legend> listViewLegends;

    @FXML private TextField textFieldSearchFromPlayerWeaponCategories;
    @FXML private TextField textFieldSearchFromWeaponCategories;
    @FXML private ListView<WeaponCategory> listViewPlayerWeaponCategories;
    @FXML private ListView<WeaponCategory> listViewWeaponCategories;

    @FXML private TextField textFieldSearchFromPlayerAmmoTypes;
    @FXML private TextField textFieldSearchFromAmmoTypes;
    @FXML private ListView<AmmoType> listViewPlayerAmmoTypes;
    @FXML private ListView<AmmoType> listViewAmmoTypes;

    @FXML private TextField textFieldSearchFromPlayerWeapons;
    @FXML private TextField textFieldSearchFromWeapons;
    @FXML private ListView<Weapon> listViewPlayerWeapons;
    @FXML private ListView<Weapon> listViewWeapons;

    @FXML private Button buttonRevertChanges;
    @FXML private Button buttonSaveChanges;

    // Initialize
    @FXML
    private void initialize() {
        initializationCompleted = false;
        JFXDefs.startServiceTask(() -> {
            while (!configurationComplete) Thread.onSpinWait();
            Platform.runLater(() -> {
                listViewPlayersData.getItems().addAll(PlayerDataManager.getInstance().getPlayersData());
                listViewPlayersData.getSelectionModel().selectedItemProperty().addListener((observable, oldValue, newValue) -> {
                    revertChanges();
                    if (newValue != null) {
                        loadPlayerData(newValue);
                        gridPanePlayersDataContainer.setVisible(true);
                    }
                    else gridPanePlayersDataContainer.setVisible(false);
                });

                toggleButtonRandomizePlayer.selectedProperty().addListener((observable, oldValue, newValue) -> imageViewToggleRandomizePlayer.setImage(newValue?TICK:CROSS));
                toggleButtonRandomizePlayer.setSelected(true);

                comboBoxLegendRandomizationRule.setItems(FXCollections.observableList(LegendRandomizationRule.getLegendRandomizationRuleList()));
                comboBoxWeaponsRandomizationRule.setItems(FXCollections.observableList(WeaponRandomizationRule.getWeaponRandomizationRuleList()));

                listViewPlayerLegendCategories.getSelectionModel().setSelectionMode(SelectionMode.MULTIPLE);
                listViewPlayerLegendCategories.setItems(filteredPlayerLegendCategories);
                listViewLegendCategories.getSelectionModel().setSelectionMode(SelectionMode.MULTIPLE);
                listViewLegendCategories.setItems(filteredLegendCategories);

                listViewPlayerLegends.getSelectionModel().setSelectionMode(SelectionMode.MULTIPLE);
                listViewPlayerLegends.setItems(filteredPlayerLegends);
                listViewLegends.getSelectionModel().setSelectionMode(SelectionMode.MULTIPLE);
                listViewLegends.setItems(filteredLegends);

                listViewPlayerWeaponCategories.getSelectionModel().setSelectionMode(SelectionMode.MULTIPLE);
                listViewPlayerWeaponCategories.setItems(filteredPlayerWeaponCategories);
                listViewWeaponCategories.getSelectionModel().setSelectionMode(SelectionMode.MULTIPLE);
                listViewWeaponCategories.setItems(filteredWeaponCategories);

                listViewPlayerAmmoTypes.getSelectionModel().setSelectionMode(SelectionMode.MULTIPLE);
                listViewPlayerAmmoTypes.setItems(filteredPlayerAmmoTypes);
                listViewAmmoTypes.getSelectionModel().setSelectionMode(SelectionMode.MULTIPLE);
                listViewAmmoTypes.setItems(filteredAmmoTypes);

                listViewPlayerWeapons.getSelectionModel().setSelectionMode(SelectionMode.MULTIPLE);
                listViewPlayerWeapons.setItems(filteredPlayerWeapons);
                listViewWeapons.getSelectionModel().setSelectionMode(SelectionMode.MULTIPLE);
                listViewWeapons.setItems(filteredWeapons);

                textFieldSearchFromPlayerLegendCategories.textProperty().addListener((observable, oldValue, newValue) -> filteredPlayerLegendCategories.setPredicate(legendCategory -> legendCategory.toString().toLowerCase().contains(newValue.toLowerCase())));
                textFieldSearchFromPlayerLegends.textProperty().addListener((observable, oldValue, newValue) -> filteredPlayerLegends.setPredicate(legend -> legend.toString().toLowerCase().contains(newValue.toLowerCase())));
                textFieldSearchFromPlayerWeaponCategories.textProperty().addListener((observable, oldValue, newValue) -> filteredPlayerWeaponCategories.setPredicate(weaponCategory -> weaponCategory.toString().toLowerCase().contains(newValue.toLowerCase())));
                textFieldSearchFromPlayerAmmoTypes.textProperty().addListener((observable, oldValue, newValue) -> filteredPlayerAmmoTypes.setPredicate(ammoType -> ammoType.toString().toLowerCase().contains(newValue.toLowerCase())));
                textFieldSearchFromPlayerWeapons.textProperty().addListener((observable, oldValue, newValue) -> filteredPlayerWeapons.setPredicate(weapon -> weapon.toString().toLowerCase().contains(newValue.toLowerCase())));

                textFieldSearchFromLegendCategories.textProperty().addListener((observable, oldValue, newValue) -> filteredLegendCategories.setPredicate(legendCategory -> legendCategory.toString().toLowerCase().contains(newValue.toLowerCase())));
                textFieldSearchFromLegends.textProperty().addListener((observable, oldValue, newValue) -> filteredLegends.setPredicate(legend -> legend.toString().toLowerCase().contains(newValue.toLowerCase())));
                textFieldSearchFromWeaponCategories.textProperty().addListener((observable, oldValue, newValue) -> filteredWeaponCategories.setPredicate(weaponCategory -> weaponCategory.toString().toLowerCase().contains(newValue.toLowerCase())));
                textFieldSearchFromAmmoTypes.textProperty().addListener((observable, oldValue, newValue) -> filteredAmmoTypes.setPredicate(ammoType -> ammoType.toString().toLowerCase().contains(newValue.toLowerCase())));
                textFieldSearchFromWeapons.textProperty().addListener((observable, oldValue, newValue) -> filteredWeapons.setPredicate(weapon -> weapon.toString().toLowerCase().contains(newValue.toLowerCase())));

                gridPanePlayerData.setDisable(false);
                initializationCompleted = true;
            });
        });
    }

    // Methods
    private void enableChangesButton() {
        buttonRevertChanges.setDisable(false);
        buttonSaveChanges.setDisable(false);
        mainMenuController.getTabPlayersData().setText("Dati Giocatori *");
    }
    private void disableChangesButton() {
        buttonRevertChanges.setDisable(true);
        buttonSaveChanges.setDisable(true);
        mainMenuController.getTabPlayersData().setText("Dati Giocatori");
    }
    private void createNewPlayer(@NotNull final String name) {
        newPlayer = name;
        gridPanePlayersDataContainer.setVisible(true);
        gridPanePlayerData.setDisable(true);
        toggleButtonRandomizePlayer.setSelected(false);

        this.comboBoxLegendRandomizationRule.getSelectionModel().clearSelection();
        this.comboBoxWeaponsRandomizationRule.getSelectionModel().clearSelection();

        this.playerLegendCategories.clear();
        this.legendCategories.setAll(LegendCategory.getLegendCategoriesList());

        this.playerLegends.clear();
        this.legends.setAll(Legend.getLegendsList());

        this.playerWeaponCategories.clear();
        this.weaponCategories.setAll(WeaponCategory.getWeaponCategoriesList());

        this.playerAmmoTypes.clear();
        this.ammoTypes.setAll(AmmoType.getAmmoTypesList());

        this.playerWeapons.clear();
        this.weapons.setAll(Weapon.getWeaponsList());

        gridPanePlayerData.setDisable(false);
        stageChanges();
    }
    private void loadPlayerData(@NotNull final PlayerData playerData) {
        gridPanePlayerData.setDisable(true);
        toggleButtonRandomizePlayer.setSelected(playerData.isPlayerEnabled());

        this.comboBoxLegendRandomizationRule.getSelectionModel().select(playerData.getLegendRandomizationRule());
        this.comboBoxWeaponsRandomizationRule.getSelectionModel().select(playerData.getWeaponRandomizationRule());

        this.playerLegendCategories.setAll(playerData.getLegendCategories());
        this.legendCategories.setAll(LegendCategory.getLegendCategoriesList());
        this.legendCategories.removeAll(this.playerLegendCategories);

        this.playerLegends.setAll(playerData.getLegends());
        this.legends.setAll(Legend.getLegendsList());
        this.legends.removeAll(this.playerLegends);

        this.playerWeaponCategories.setAll(playerData.getWeaponCategories());
        this.weaponCategories.setAll(WeaponCategory.getWeaponCategoriesList());
        this.weaponCategories.removeAll(this.playerWeaponCategories);

        this.playerAmmoTypes.setAll(playerData.getAmmoTypes());
        this.ammoTypes.setAll(AmmoType.getAmmoTypesList());
        this.ammoTypes.removeAll(this.playerAmmoTypes);

        this.playerWeapons.setAll(playerData.getWeapons());
        this.weapons.setAll(Weapon.getWeaponsList());
        this.weapons.removeAll(this.playerWeapons);

        gridPanePlayerData.setDisable(false);
    }

    // EDT
    @FXML
    private void showPlayersContextMenu(@NotNull final ContextMenuEvent event) {
        ContextMenu contextMenu = new ContextMenu();
        Menu addPlayer = new Menu("Aggiungi Giocatore");
        MenuItem add = new MenuItem();
        TextField name = new TextField();
        name.setPromptText("Nome Giocatore");
        add.setGraphic(name);
        addPlayer.getItems().add(add);
        contextMenu.getItems().add(addPlayer);
        add.setOnAction(event1 -> {
            if (name.getText() == null || name.getText().replace("\t", "").replace(" ", "").isEmpty()) return;
            createNewPlayer(name.getText());
        });

        PlayerData data = listViewPlayersData.getSelectionModel().getSelectedItem();
        if (data != null) {
            Menu renamePlayer = new Menu("Rinomina Giocatore");
            MenuItem rename = new MenuItem();
            renamePlayer.getItems().add(rename);
            TextField field = new TextField();
            field.setPromptText("Nuovo Nome");
            rename.setGraphic(field);
            rename.setOnAction(event1 -> {
                if (field.getText() == null || field.getText().replace("\t", "").replace(" ", "").isEmpty()) return;
                data.setUsername(field.getText());
                listViewPlayersData.refresh();
                JFXDefs.startServiceTask(() -> {
                    try {
                        PlayerDataManager.getInstance().savePlayerData();
                        Platform.runLater(() -> new InformationAlert("SUCCESSO", "Rinominazione Giocatore", "Rinominazione avvenuta con successo!"));
                    } catch (IOException e) {
                        Logger.log(e);
                        Platform.runLater(() -> new ErrorAlert("ERRORE", "Errore di I/O", "Si e' verificato un errore durante la scrittura dei dati."));
                    }
                });
            });
            contextMenu.getItems().add(renamePlayer);

            MenuItem delete = new MenuItem("Elimina Giocatore");
            delete.setOnAction(event1 -> {
                JFXDefs.startServiceTask(() -> {
                    try {
                        PlayerDataManager.getInstance().getPlayersData().remove(data);
                        PlayerDataManager.getInstance().savePlayerData();
                        Platform.runLater(() -> mainMenuController.getTabRandomizerController().loadPlayerScenes());
                    } catch (IOException e) {
                        Logger.log(e);
                    }
                });
                listViewPlayersData.getItems().remove(data);
            });
            contextMenu.getItems().add(delete);
        }

        contextMenu.setAutoHide(true);
        contextMenu.show(Client.getStage(), event.getScreenX(), event.getScreenY());
    }

    // EDT: Player Lists
    @FXML
    private void addToPlayerLegendCategories() {
        ObservableList<LegendCategory> selection = listViewLegendCategories.getSelectionModel().getSelectedItems();
        if (selection == null) return;
        playerLegendCategories.addAll(selection);
        legendCategories.removeAll(selection);
        stageChanges();
    }
    @FXML
    private void removeFromPlayerLegendCategories() {
        ObservableList<LegendCategory> selection = listViewPlayerLegendCategories.getSelectionModel().getSelectedItems();
        if (selection == null) return;
        legendCategories.addAll(selection);
        playerLegendCategories.removeAll(selection);
        stageChanges();
    }
    @FXML
    private void addToPlayerLegends() {
        ObservableList<Legend> selection = listViewLegends.getSelectionModel().getSelectedItems();
        if (selection == null) return;
        playerLegends.addAll(selection);
        legends.removeAll(selection);
        stageChanges();
    }
    @FXML
    private void removeFromPlayerLegends() {
        ObservableList<Legend> selection = listViewPlayerLegends.getSelectionModel().getSelectedItems();
        if (selection == null) return;
        legends.addAll(selection);
        playerLegends.removeAll(selection);
        stageChanges();
    }

    @FXML
    private void addToPlayerWeaponCategories() {
        ObservableList<WeaponCategory> selection = listViewWeaponCategories.getSelectionModel().getSelectedItems();
        if (selection == null) return;
        playerWeaponCategories.addAll(selection);
        weaponCategories.removeAll(selection);
        stageChanges();
    }
    @FXML
    private void removeFromPlayerWeaponCategories() {
        ObservableList<WeaponCategory> selection = listViewPlayerWeaponCategories.getSelectionModel().getSelectedItems();
        if (selection == null) return;
        weaponCategories.addAll(selection);
        playerWeaponCategories.removeAll(selection);
        stageChanges();
    }

    @FXML
    private void addToPlayerAmmoTypes() {
        ObservableList<AmmoType> selection = listViewAmmoTypes.getSelectionModel().getSelectedItems();
        if (selection == null) return;
        playerAmmoTypes.addAll(selection);
        ammoTypes.removeAll(selection);
        stageChanges();
    }
    @FXML
    private void removeFromPlayerAmmoTypes() {
        ObservableList<AmmoType> selection = listViewPlayerAmmoTypes.getSelectionModel().getSelectedItems();
        if (selection == null) return;
        ammoTypes.addAll(selection);
        playerAmmoTypes.removeAll(selection);
        stageChanges();
    }

    @FXML
    private void addToPlayerWeapons() {
        ObservableList<Weapon> selection = listViewWeapons.getSelectionModel().getSelectedItems();
        if (selection == null) return;
        playerWeapons.addAll(selection);
        weapons.removeAll(selection);
        stageChanges();
    }
    @FXML
    private void removeFromPlayerWeapons() {
        ObservableList<Weapon> selection = listViewPlayerWeapons.getSelectionModel().getSelectedItems();
        if (selection == null) return;
        weapons.addAll(selection);
        playerWeapons.removeAll(selection);
        stageChanges();
    }

    // EDT: Change
    @FXML
    private void stageChanges() {
        if (initializationCompleted) enableChangesButton();
    }
    @FXML
    private void revertChanges() {
        if (newPlayer != null) createNewPlayer(newPlayer);
        else {
            PlayerData data = listViewPlayersData.getSelectionModel().getSelectedItem();
            if (data != null) loadPlayerData(listViewPlayersData.getSelectionModel().getSelectedItem());
            else gridPanePlayersDataContainer.setVisible(false);
            disableChangesButton();
        }
    }
    @FXML
    private void saveChanges() {
        disableChangesButton();
        LegendRandomizationRule legendRandomizationRule = comboBoxLegendRandomizationRule.getSelectionModel().getSelectedItem();
        WeaponRandomizationRule weaponRandomizationRule = comboBoxWeaponsRandomizationRule.getSelectionModel().getSelectedItem();

        if (legendRandomizationRule == null) {
            new ErrorAlert("ERRORE", "Errore di Inserimento", "La regola di randomizzazione della leggenda e' obbligatoria.");
            enableChangesButton();
            return;
        }
        if (weaponRandomizationRule == null) {
            new ErrorAlert("ERRORE", "Errore di Inserimento", "La regola di randomizzazione delle armi e' obbligatoria.");
            enableChangesButton();
            return;
        }
        if (newPlayer != null) {
            PlayerData newData = new PlayerData(
                    newPlayer, toggleButtonRandomizePlayer.isSelected(),
                    legendRandomizationRule, weaponRandomizationRule,
                    legendCategories,
                    legends,
                    weaponCategories,
                    ammoTypes,
                    weapons
            );
            PlayerDataManager.getInstance().getPlayersData().add(newData);
            listViewPlayersData.getItems().add(newData);
            listViewPlayersData.getSelectionModel().select(newData);
            disableChangesButton();
        } else {
            PlayerData playerData = listViewPlayersData.getSelectionModel().getSelectedItem();
            if (playerData == null) {
                new ErrorAlert("ERRORE", "Errore di Salvataggio", "E' stato premuto il tasto di salvataggio senza però avere un giocatore selezionato.");
                newPlayer = null;
                return;
            }
            playerData.setPlayerEnabled(toggleButtonRandomizePlayer.isSelected());

            playerData.setLegendRandomizationRule(legendRandomizationRule);
            playerData.setWeaponRandomizationRule(weaponRandomizationRule);

            playerData.getLegendCategories().clear();
            playerData.getLegendCategories().addAll(playerLegendCategories);

            playerData.getLegends().clear();
            playerData.getLegends().addAll(playerLegends);

            playerData.getWeaponCategories().clear();
            playerData.getWeaponCategories().addAll(playerWeaponCategories);

            playerData.getAmmoTypes().clear();
            playerData.getAmmoTypes().addAll(playerAmmoTypes);

            playerData.getWeapons().clear();
            playerData.getWeapons().addAll(playerWeapons);
        }
        newPlayer = null;
        JFXDefs.startServiceTask(() -> {
            try {
                PlayerDataManager.getInstance().savePlayerData();
                Platform.runLater(() -> {
                    mainMenuController.getTabRandomizerController().loadPlayerScenes();
                    new InformationAlert("SUCCESSO", "Salvataggio Modifiche", "Modifiche salvate con successo!");
                });
            } catch (IOException e) {
                Platform.runLater(() -> new ErrorAlert("ERRORE", "Errore di I/O", "Si e' verificato un errore durante il salvataggio dei dati, chiusura applicazione..."));
                Logger.log(e);
                Client.exit(-1);
            }
        });
    }
}