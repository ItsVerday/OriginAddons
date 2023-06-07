package com.mikarific.originaddons.settings;

import com.mikarific.originaddons.util.RocketBootsItemBarType;
import me.shedaniel.autoconfig.ConfigData;
import me.shedaniel.autoconfig.annotation.Config;
import me.shedaniel.autoconfig.annotation.ConfigEntry;

@Config(name = "originaddons")
public class SettingsConfig implements ConfigData {
    @ConfigEntry.Category("features")
    @ConfigEntry.Gui.Tooltip
    public boolean customMenus = true;
    @ConfigEntry.Category("features")
    @ConfigEntry.Gui.Tooltip
    public boolean customTooltips = true;

    @ConfigEntry.Category("customtooltips")
    @ConfigEntry.Gui.Tooltip
    public boolean customBottledExperienceLevelsFrom0Tooltip = true;
    @ConfigEntry.Category("customtooltips")
    @ConfigEntry.Gui.Tooltip
    public boolean customBottledExperienceLevelsFromCurrentTooltip = true;

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

    @ConfigEntry.Category("features")
    @ConfigEntry.Gui.Tooltip
    public boolean rocketBootsFuelBar = true;
    @ConfigEntry.Category("features")
    @ConfigEntry.Gui.Tooltip
    public boolean neverHideRocketBootsFuelBar = false;
    @ConfigEntry.Category("features")
    @ConfigEntry.Gui.Tooltip
    public boolean noRocketBootsFuelBarShaking = false;
    @ConfigEntry.Category("features")
    @ConfigEntry.Gui.Tooltip
    public RocketBootsItemBarType rocketBootsItemBarType = RocketBootsItemBarType.FUEL;

    @ConfigEntry.Category("features")
    @ConfigEntry.Gui.Tooltip
    public boolean cropStarsIcon = true;


    @ConfigEntry.Category("features")
    @ConfigEntry.Gui.Tooltip
    public boolean optimizeItemFrameRendering = true;

    @ConfigEntry.Category("features")
    @ConfigEntry.Gui.Tooltip
    public boolean updateNotifications = true;
}