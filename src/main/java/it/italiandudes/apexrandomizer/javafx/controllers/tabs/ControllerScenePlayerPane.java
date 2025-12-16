package it.italiandudes.apexrandomizer.javafx.controllers.tabs;

import it.italiandudes.apexrandomizer.data.PlayerData;
import it.italiandudes.apexrandomizer.enums.*;
import it.italiandudes.apexrandomizer.javafx.JFXDefs;
import it.italiandudes.apexrandomizer.utils.Randomizer;
import javafx.application.Platform;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Label;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

@SuppressWarnings("unused")
public final class ControllerScenePlayerPane {

    // Main Link
    private ControllerSceneTabRandomizer randomizerTabController = null;
    private PlayerData playerData;
    private volatile boolean configurationComplete;
    public void setRandomizerTabController(@NotNull final ControllerSceneTabRandomizer controller) {
        randomizerTabController = controller;
    }
    public @NotNull ControllerSceneTabRandomizer getRandomizerTabController() {
        return randomizerTabController;
    }
    public void setPlayerData(@NotNull final PlayerData playerData) {
        this.playerData = playerData;
    }
    public @NotNull PlayerData getPlayerData() {
        return playerData;
    }
    public void configurationComplete() {
        configurationComplete = true;
    }

    // Attributes
    private Legend randomizedLegend = null;

    // Graphic Elements
    @FXML private Label labelPlayerName;
    @FXML private Label labelLegend;
    @FXML private Label labelPlayerLegend;
    @FXML private Label labelFirst;
    @FXML private Label labelPlayerFirst;
    @FXML private Label labelSecond;
    @FXML private Label labelPlayerSecond;
    @FXML private Label labelSling;
    @FXML private Label labelPlayerSling;

    // Initialize
    @FXML
    private void initialize() {
        // resetFields();
        JFXDefs.startServiceTask(() -> {
            while (!configurationComplete) Thread.onSpinWait();
            Platform.runLater(() -> setPlayerName(playerData.getUsername()));
        });
    }

    // Methods
    public void setPlayerName(@NotNull final String name) {
        labelPlayerName.setText(name);
    }
    public void setLegend(@Nullable final Legend legend) {
        Randomizer.RANDOMIZED_LEGENDS_BUFFER.remove(randomizedLegend);
        randomizedLegend = legend;
        if (randomizedLegend != null) Randomizer.RANDOMIZED_LEGENDS_BUFFER.add(randomizedLegend);
        labelLegend.setText("Leggenda:");
        labelPlayerLegend.setText(legend!=null?legend.toString():"NA");
    }
    public void setLegendCategory(@NotNull final LegendCategory legendCategory) {
        Randomizer.RANDOMIZED_LEGENDS_BUFFER.remove(randomizedLegend);
        randomizedLegend = null;
        labelLegend.setText("Categoria Leggenda:");
        labelPlayerLegend.setText(legendCategory.toString());
    }
    public void setFirstWeapon(@Nullable final Weapon weapon) {
        labelFirst.setText("Arma 1:");
        labelPlayerFirst.setText(weapon!=null?weapon.toString():"NA");
    }
    public void setFirstWeaponCategory(@NotNull final WeaponCategory category) {
        labelFirst.setText("Categoria Arma 1:");
        labelPlayerFirst.setText(category.toString());
    }
    public void setFirstWeaponAmmoType(@NotNull final AmmoType ammoType) {
        labelFirst.setText("Munizioni Arma 1:");
        labelPlayerFirst.setText(ammoType.toString());
    }
    public void setSecondWeapon(@Nullable final Weapon weapon) {
        labelSecond.setText("Arma 2:");
        labelPlayerSecond.setText(weapon!=null?weapon.toString():"NA");
    }
    public void setSecondWeaponCategory(@NotNull final WeaponCategory category) {
        labelSecond.setText("Categoria Arma 2:");
        labelPlayerSecond.setText(category.toString());
    }
    public void setSecondWeaponAmmoType(@NotNull final AmmoType ammoType) {
        labelSecond.setText("Munizioni Arma 2:");
        labelPlayerSecond.setText(ammoType.toString());
    }
    public void setSlingWeapon(@Nullable final Weapon weapon) {
        labelSling.setText("Arma Tracolla:");
        labelPlayerSling.setText(weapon!=null?weapon.toString():"NA");
    }
    public void setSlingWeaponCategory(@NotNull final WeaponCategory category) {
        labelSling.setText("Categoria Arma Tracolla:");
        labelPlayerSling.setText(category.toString());
    }
    public void setSlingWeaponAmmoType(@NotNull final AmmoType ammoType) {
        labelSling.setText("Munizioni Arma Tracolla:");
        labelPlayerSling.setText(ammoType.toString());
    }

    // EDT
    @FXML
    private void randomizeLegend() {
        switch (playerData.getLegendRandomizationRule()) {
            case NO_LEGEND_RANDOMIZATION -> setLegend(null);
            case RANDOMIZE_LEGEND -> {
                Legend legend;
                do {
                    legend = Randomizer.randomizeLegendFromPlayerData(playerData);
                } while (Randomizer.RANDOMIZED_LEGENDS_BUFFER.contains(legend));
                setLegend(legend);
                randomizeSling(null);
            }
            case RANDOMIZE_LEGEND_CATEGORY -> setLegendCategory(Randomizer.randomizeLegendCategoryFromPlayerData(playerData));
        }
    }
    @FXML
    private void randomizeFirst() {
        switch (playerData.getWeaponRandomizationRule()) {
            case NO_WEAPON_RANDOMIZATION -> setFirstWeapon(null);
            case RANDOMIZE_WEAPON -> setFirstWeapon(Randomizer.randomizeWeaponFromPlayerData(playerData));
            case RANDOMIZE_WEAPON_CATEGORY -> setFirstWeaponCategory(Randomizer.randomizeWeaponCategoryFromPlayerData(playerData));
            case RANDOMIZE_WEAPON_AMMO -> setFirstWeaponAmmoType(Randomizer.randomizeAmmoTypeFromPlayerData(playerData));
        }
    }
    @FXML
    private void randomizeSecond() {
        switch (playerData.getWeaponRandomizationRule()) {
            case NO_WEAPON_RANDOMIZATION -> setSecondWeapon(null);
            case RANDOMIZE_WEAPON -> setSecondWeapon(Randomizer.randomizeWeaponFromPlayerData(playerData));
            case RANDOMIZE_WEAPON_CATEGORY -> setSecondWeaponCategory(Randomizer.randomizeWeaponCategoryFromPlayerData(playerData));
            case RANDOMIZE_WEAPON_AMMO -> setSecondWeaponAmmoType(Randomizer.randomizeAmmoTypeFromPlayerData(playerData));
        }
    }
    @FXML
    private void randomizeSling(@Nullable ActionEvent event) {
        if (event != null || randomizedLegend == Legend.BALLISTIC)switch (playerData.getWeaponRandomizationRule()) {
            case NO_WEAPON_RANDOMIZATION -> setSlingWeapon(null);
            case RANDOMIZE_WEAPON -> setSlingWeapon(Randomizer.randomizeWeaponFromPlayerData(playerData));
            case RANDOMIZE_WEAPON_CATEGORY -> setSlingWeaponCategory(Randomizer.randomizeWeaponCategoryFromPlayerData(playerData));
            case RANDOMIZE_WEAPON_AMMO -> setSlingWeaponAmmoType(Randomizer.randomizeAmmoTypeFromPlayerData(playerData));
        } else setSlingWeapon(null);
    }
    @FXML
    public void randomizeAll() {
        randomizeLegend();
        randomizeFirst();
        randomizeSecond();
        randomizeSling(null);
    }
}
