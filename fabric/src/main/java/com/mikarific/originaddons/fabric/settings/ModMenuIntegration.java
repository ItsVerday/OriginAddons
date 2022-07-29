package com.mikarific.originaddons.fabric.settings;

import com.mikarific.originaddons.OriginAddons;
import com.terraformersmc.modmenu.api.ConfigScreenFactory;
import com.terraformersmc.modmenu.api.ModMenuApi;

public class ModMenuIntegration implements ModMenuApi {
    @Override
    public ConfigScreenFactory<?> getModConfigScreenFactory() {
        return OriginAddons::getConfigScreen;
    }
}
