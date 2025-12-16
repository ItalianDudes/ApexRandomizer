package it.italiandudes.apexrandomizer.data;

import it.italiandudes.apexrandomizer.enums.*;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.Setter;
import org.jetbrains.annotations.NotNull;
import org.json.JSONArray;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;

@Getter
@Setter
@EqualsAndHashCode
public final class PlayerData {

    // Attributes
    @NotNull private String username;
    private boolean isPlayerEnabled;
    private LegendRandomizationRule legendRandomizationRule;
    private WeaponRandomizationRule weaponRandomizationRule;
    @NotNull private List<@NotNull LegendCategory> legendCategories;
    @NotNull private List<@NotNull Legend> legends;
    @NotNull private List<@NotNull WeaponCategory> weaponCategories;
    @NotNull private List<@NotNull AmmoType> ammoTypes;
    @NotNull private List<@NotNull Weapon> weapons;

    // Randomization Flags
    private boolean preventSameWeaponRandomization = false;
    private boolean preventSameWeaponCategoryRandomization = false;
    private boolean preventMultipleSniper = false;
    private boolean preventMultipleMarksman = false;
    private boolean preventComboSniperMarksman = false;
    private boolean ignoreRandomizationRulesForSling = false;

    // Constructors

    public PlayerData(
            @NotNull String username, boolean isPlayerEnabled, LegendRandomizationRule legendRandomizationRule,
            WeaponRandomizationRule weaponRandomizationRule, @NotNull List<@NotNull LegendCategory> legendCategories,
            @NotNull List<@NotNull Legend> legends, @NotNull List<@NotNull WeaponCategory> weaponCategories,
            @NotNull List<@NotNull AmmoType> ammoTypes, @NotNull List<@NotNull Weapon> weapons,
            boolean preventSameWeaponRandomization, boolean preventSameWeaponCategoryRandomization,
            boolean preventMultipleSniper, boolean preventMultipleMarksman, boolean preventComboSniperMarksman,
            boolean ignoreRandomizationRulesForSling) {
        this.username = username;
        this.isPlayerEnabled = isPlayerEnabled;
        this.legendRandomizationRule = legendRandomizationRule;
        this.weaponRandomizationRule = weaponRandomizationRule;
        this.legendCategories = new ArrayList<>(legendCategories);
        this.legends = new ArrayList<>(legends);
        this.weaponCategories = new ArrayList<>(weaponCategories);
        this.ammoTypes = new ArrayList<>(ammoTypes);
        this.weapons = new ArrayList<>(weapons);
        this.preventSameWeaponRandomization = preventSameWeaponRandomization;
        this.preventSameWeaponCategoryRandomization = preventSameWeaponCategoryRandomization;
        this.preventMultipleSniper = preventMultipleSniper;
        this.preventMultipleMarksman = preventMultipleMarksman;
        this.preventComboSniperMarksman = preventComboSniperMarksman;
        this.ignoreRandomizationRulesForSling = ignoreRandomizationRulesForSling;
    }
    public PlayerData(
            @NotNull String username, boolean isPlayerEnabled, LegendRandomizationRule legendRandomizationRule,
            WeaponRandomizationRule weaponRandomizationRule, @NotNull List<@NotNull LegendCategory> legendCategories,
            @NotNull List<@NotNull Legend> legends, @NotNull List<@NotNull WeaponCategory> weaponCategories,
            @NotNull List<@NotNull AmmoType> ammoTypes, @NotNull List<@NotNull Weapon> weapons) {
        this.username = username;
        this.isPlayerEnabled = isPlayerEnabled;
        this.legendRandomizationRule = legendRandomizationRule;
        this.weaponRandomizationRule = weaponRandomizationRule;
        this.legendCategories = new ArrayList<>(legendCategories);
        this.legends = new ArrayList<>(legends);
        this.weaponCategories = new ArrayList<>(weaponCategories);
        this.ammoTypes = new ArrayList<>(ammoTypes);
        this.weapons = new ArrayList<>(weapons);
    }
    public PlayerData(@NotNull final JSONObject playerData) {

        this.username = playerData.getString("username");
        this.isPlayerEnabled = playerData.getBoolean("is_player_enabled");

        // Legends
        this.legendRandomizationRule = LegendRandomizationRule.values()[playerData.getInt("legend_randomization_rule")];
        this.legendCategories = new ArrayList<>();
        JSONArray legendCategoriesArray = playerData.getJSONArray("legend_categories");
        for (int i=0; i<legendCategoriesArray.length(); i++) {
            this.legendCategories.add(LegendCategory.valueOf(legendCategoriesArray.getString(i)));
        }
        this.legends = new ArrayList<>();
        JSONArray legendsArray = playerData.getJSONArray("legends");
        for (int i=0; i<legendsArray.length(); i++) {
            this.legends.add(Legend.valueOf(legendsArray.getString(i)));
        }

        // Weapons
        this.weaponRandomizationRule = WeaponRandomizationRule.values()[playerData.getInt("weapon_randomization_rule")];
        this.weaponCategories = new ArrayList<>();
        JSONArray weaponCategoriesArray = playerData.getJSONArray("weapon_categories");
        for (int i=0; i<weaponCategoriesArray.length(); i++) {
            this.weaponCategories.add(WeaponCategory.valueOf(weaponCategoriesArray.getString(i)));
        }
        this.ammoTypes = new ArrayList<>();
        JSONArray ammoTypesArray = playerData.getJSONArray("ammo_types");
        for (int i=0; i<ammoTypesArray.length(); i++) {
            this.ammoTypes.add(AmmoType.valueOf(ammoTypesArray.getString(i)));
        }
        this.weapons = new ArrayList<>();
        JSONArray weaponsArray = playerData.getJSONArray("weapons");
        for (int i=0; i<weaponsArray.length(); i++) {
            this.weapons.add(Weapon.valueOf(weaponsArray.getString(i)));
        }

        // Randomization Flags
        this.preventSameWeaponRandomization = playerData.getBoolean("prevent_same_weapon_randomization");
        this.preventSameWeaponCategoryRandomization = playerData.getBoolean("prevent_same_weapon_category_randomization");
        this.preventMultipleSniper = playerData.getBoolean("prevent_multiple_sniper");
        this.preventMultipleMarksman = playerData.getBoolean("prevent_multiple_marksman");
        this.preventComboSniperMarksman = playerData.getBoolean("prevent_combo_sniper_marksman");
        this.ignoreRandomizationRulesForSling = playerData.getBoolean("ignore_randomization_rules_for_sling");
    }

    // Methods
    public JSONObject toJSON() {
        JSONObject playerData = new JSONObject();

        playerData.put("username", username);
        playerData.put("is_player_enabled", isPlayerEnabled);

        playerData.put("legend_randomization_rule", legendRandomizationRule.ordinal());
        playerData.put("weapon_randomization_rule", weaponRandomizationRule.ordinal());

        JSONArray legendCategoriesArray = new JSONArray();
        for (LegendCategory category : legendCategories) {
            legendCategoriesArray.put(category.name());
        }
        playerData.put("legend_categories", legendCategoriesArray);

        JSONArray legendsArray = new JSONArray();
        for (Legend legend : legends) {
            legendsArray.put(legend.name());
        }
        playerData.put("legends", legendsArray);

        JSONArray weaponCategoriesArray = new JSONArray();
        for (WeaponCategory category : weaponCategories) {
            weaponCategoriesArray.put(category.name());
        }
        playerData.put("weapon_categories", weaponCategoriesArray);

        JSONArray ammoTypesArray = new JSONArray();
        for (AmmoType ammoType : ammoTypes) {
            ammoTypesArray.put(ammoType.name());
        }
        playerData.put("ammo_types", ammoTypesArray);

        JSONArray weaponsArray = new JSONArray();
        for (Weapon weapon : weapons) {
            weaponsArray.put(weapon.name());
        }
        playerData.put("weapons", weaponsArray);

        playerData.put("prevent_same_weapon_randomization", preventSameWeaponRandomization);
        playerData.put("prevent_same_weapon_category_randomization", preventSameWeaponCategoryRandomization);
        playerData.put("prevent_multiple_sniper", preventMultipleSniper);
        playerData.put("prevent_multiple_marksman", preventMultipleMarksman);
        playerData.put("prevent_combo_sniper_marksman", preventComboSniperMarksman);
        playerData.put("ignore_randomization_rules_for_sling", ignoreRandomizationRulesForSling);

        return playerData;
    }

    // ToString
    @Override @NotNull
    public String toString() {
        return username;
    }
}
