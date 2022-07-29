package com.mikarific.originaddons.settings;

import me.shedaniel.autoconfig.ConfigData;
import me.shedaniel.autoconfig.annotation.Config;
import me.shedaniel.autoconfig.annotation.ConfigEntry;

@Config(name = "originaddons")
public class SettingsConfig implements ConfigData {
    @ConfigEntry.Gui.Tooltip
    public boolean customMenus = true;

    @ConfigEntry.Gui.Tooltip
    @ConfigEntry.Gui.CollapsibleObject
    public CustomMenus customMenusCategory = new CustomMenus();
    public static class CustomMenus {
        @ConfigEntry.Gui.Tooltip
        public boolean navigator = true;
    }

    public boolean emojiPicker = true;
    public boolean blockPicker = true;
    public boolean inventoryButtons = true;
}