package com.mikarific.originaddons.util;

import com.mikarific.originaddons.OriginAddons;

public class InventoryButtons {
    public static boolean isEnabled() {
        return OriginAddons.onOriginRealms() && OriginAddons.getConfig().inventoryButtons;
    }
}
