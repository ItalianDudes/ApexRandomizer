package it.italiandudes.apexrandomizer.data;

import it.italiandudes.apexrandomizer.enums.*;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import org.jetbrains.annotations.NotNull;
import org.json.JSONArray;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;

@Getter
@EqualsAndHashCode
public final class PlayerData {

    // Attributes
    @NotNull private final String username;
    private final boolean isPlayerEnabled;
    private final LegendRandomizationRule legendRandomizationRule;
    private final WeaponRandomizationRule weaponRandomizationRule;
    @NotNull private final List<@NotNull Legend> randomizableLegends;
    @NotNull private final List<@NotNull LegendCategory> randomizableLegendCategories;
    @NotNull private final List<@NotNull Weapon> randomizableWeapons;
    @NotNull private final List<@NotNull WeaponCategory> randomizableWeaponCategories;
    @NotNull private final List<@NotNull AmmoType> randomizableAmmoTypes;

    // Randomization Flags
    private boolean preventSameWeaponRandomization = false;
    private boolean preventSameWeaponCategoryRandomization = false;
    private boolean preventMultipleSniper = false;
    private boolean preventMultipleMarksman = false;
    private boolean preventComboSniperMarksman = false;
    private boolean ignoreRandomizationRulesForSling = false;

    // Constructors
    public PlayerData(@NotNull final JSONObject playerData) {

        // Username
        this.username = playerData.getString("username");

        // Is Player Enabled for Randomization
        this.isPlayerEnabled = playerData.getBoolean("is_player_enabled");

        if (!isPlayerEnabled) {
            legendRandomizationRule = LegendRandomizationRule.NO_LEGEND_RANDOMIZATION;
            weaponRandomizationRule = WeaponRandomizationRule.NO_WEAPON_RANDOMIZATION;
            randomizableLegends = new ArrayList<>();
            randomizableLegendCategories = new ArrayList<>();
            randomizableWeapons = new ArrayList<>();
            randomizableWeaponCategories = new ArrayList<>();
            randomizableAmmoTypes = new ArrayList<>();
            return;
        }

        // Randomize Legends?
        this.legendRandomizationRule = LegendRandomizationRule.values()[playerData.getInt("legend_randomization_rule")];
        if (legendRandomizationRule != LegendRandomizationRule.NO_LEGEND_RANDOMIZATION) {

            // Legend Category List
            List<LegendCategory> playerLegendCategoriesBlacklist = new ArrayList<>();
            JSONArray legendCategoriesBlacklistArray = playerData.getJSONArray("legend_categories_blacklist_array");
            for (int i = 0; i < legendCategoriesBlacklistArray.length(); i++) {
                playerLegendCategoriesBlacklist.add(LegendCategory.valueOf(legendCategoriesBlacklistArray.getString(i)));
            }
            this.randomizableLegendCategories = LegendCategory.getLegendCategoriesList().stream().filter(category -> !playerLegendCategoriesBlacklist.contains(category)).toList();

            // Legend List
            List<Legend> playerLegendList;
            boolean isLegendListWhitelist = playerData.getBoolean("is_legend_list_whitelist");
            JSONArray legendArray = playerData.getJSONArray("legend_array");
            List<Legend> configLegendList = new ArrayList<>();
            for (int i = 0; i < legendArray.length(); i++) {
                configLegendList.add(Legend.valueOf(legendArray.getString(i)));
            }
            if (isLegendListWhitelist) playerLegendList = configLegendList;
            else if (configLegendList.isEmpty()) playerLegendList = Legend.getLegendsList();
            else playerLegendList = Legend.getLegendsList().stream().filter(legend -> !configLegendList.contains(legend)).toList();

            // Final Legend List
            this.randomizableLegends = playerLegendList.stream().filter(legend -> !playerLegendCategoriesBlacklist.contains(legend.getLegendCategory())).toList();
        } else {
            this.randomizableLegends = new ArrayList<>();
            this.randomizableLegendCategories = new ArrayList<>();
        }

        // Randomize Weapons?
        this.weaponRandomizationRule = WeaponRandomizationRule.values()[playerData.getInt("weapon_randomization_rule")];
        if (weaponRandomizationRule != WeaponRandomizationRule.NO_WEAPON_RANDOMIZATION) {

            // Weapon Category List
            List<WeaponCategory> playerWeaponCategoriesBlacklist = new ArrayList<>();
            JSONArray weaponCategoryArray = playerData.getJSONArray("weapon_categories_blacklist_array");
            for (int i = 0; i < weaponCategoryArray.length(); i++) {
                playerWeaponCategoriesBlacklist.add(WeaponCategory.valueOf(weaponCategoryArray.getString(i)));
            }
            this.randomizableWeaponCategories = WeaponCategory.getWeaponCategoriesList().stream().filter(category -> !playerWeaponCategoriesBlacklist.contains(category)).toList();

            // Weapon Ammo Type List
            this.randomizableAmmoTypes = new ArrayList<>();
            JSONArray ammoTypeArray = playerData.getJSONArray("ammo_type_whitelist_array");
            for (int i=0; i<ammoTypeArray.length(); i++) {
                this.randomizableAmmoTypes.add(AmmoType.valueOf(ammoTypeArray.getString(i)));
            }

            // Weapon List
            List<Weapon> playerWeaponList;
            boolean isWeaponListWhitelist = playerData.getBoolean("is_weapons_list_whitelist");
            JSONArray weaponArray = playerData.getJSONArray("weapon_array");
            List<Weapon> configWeaponList = new ArrayList<>();
            for (int i = 0; i < weaponArray.length(); i++) {
                configWeaponList.add(Weapon.valueOf(weaponArray.getString(i)));
            }
            if (isWeaponListWhitelist) playerWeaponList = configWeaponList;
            else if (configWeaponList.isEmpty()) playerWeaponList = Weapon.getWeaponsList();
            else playerWeaponList = Weapon.getWeaponsList().stream().filter(weapon -> !configWeaponList.contains(weapon)).toList();

            // Final Weapon List
            this.randomizableWeapons = playerWeaponList.stream().filter(weapon -> !playerWeaponCategoriesBlacklist.contains(weapon.getWeaponCategory())).toList();
        } else {
            this.randomizableWeapons = new ArrayList<>();
            this.randomizableWeaponCategories = new ArrayList<>();
            this.randomizableAmmoTypes = new ArrayList<>();
        }

        // Randomization Flags
        this.preventSameWeaponRandomization = playerData.getBoolean("prevent_same_weapon_randomization");
        this.preventSameWeaponCategoryRandomization = playerData.getBoolean("prevent_same_weapon_category_randomization");
        this.preventMultipleSniper = playerData.getBoolean("prevent_multiple_sniper");
        this.preventMultipleMarksman = playerData.getBoolean("prevent_multiple_marksman");
        this.preventComboSniperMarksman = playerData.getBoolean("prevent_combo_sniper_marksman");
        this.ignoreRandomizationRulesForSling = playerData.getBoolean("ignore_randomization_rules_for_sling");
    }
}
