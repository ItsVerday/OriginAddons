package com.mikarific.originaddons.settings;

import me.shedaniel.autoconfig.ConfigData;
import me.shedaniel.autoconfig.annotation.Config;
import me.shedaniel.autoconfig.annotation.ConfigEntry;

@Config(name = "originaddons")
public class SettingsConfig implements ConfigData {
    @ConfigEntry.Category("features")
    @ConfigEntry.Gui.Tooltip
    public boolean customMenus = true;

    @ConfigEntry.Category("custommenus")
    @ConfigEntry.Gui.Tooltip
    public boolean customNavigatorMenu = true;
    @ConfigEntry.Category("custommenus")
    @ConfigEntry.Gui.Tooltip
    public boolean customOrbitMenu = true;
    @ConfigEntry.Category("custommenus")
    @ConfigEntry.Gui.Tooltip
    public boolean customProfileMenu = true;

    @ConfigEntry.Category("features")
    @ConfigEntry.Gui.Tooltip
    public boolean emojiPicker = true;
    @ConfigEntry.Category("features")
    @ConfigEntry.Gui.Tooltip
    public boolean hideLockedEmojis = true;
    @ConfigEntry.Category("features")
    @ConfigEntry.Gui.Tooltip
    public boolean blockPicker = true;
    @ConfigEntry.Category("features")
    @ConfigEntry.Gui.Tooltip
    public boolean inventoryButtons = true;
}