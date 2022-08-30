package com.mikarific.originaddons.util;

import com.google.gson.JsonObject;
import com.mikarific.originaddons.OriginAddons;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class Other {
    public static double scrollOffset = 0.0;

    public static void changeScrollOffset(double amount) {
        scrollOffset += amount;
    }

    public static void resetScrollOffset() {
        scrollOffset = 0.0;
    }

    public static boolean newerVersionExists(JsonObject json) {
        boolean isNewer = false;
        if (json.get("latestVersion") != null) {
            List<String> currentVersionList = parseVersion(OriginAddons.VERSION);
            List<String> newerVersionList = parseVersion(json.get("latestVersion").getAsString());
            if (currentVersionList.size() >= 3 && newerVersionList.size() >= 3) {
                for (int i = 0; i < 3; i++) {
                    if (Integer.parseInt(newerVersionList.get(i)) > Integer.parseInt(currentVersionList.get(i))) isNewer = true;
                }
            }
        }
        return isNewer;
    }

    private static List<String> parseVersion(String version) {
         if (version.matches("\\d+\\.\\d+\\.\\d+(-[A-Z]+)?")) {
             List<String> versionList = new ArrayList<>(Arrays.stream(version.split("\\.")).toList());
             if (!version.contains("-")) {
                 return versionList;
             } else {
                 String[] incrementAndQualifier = versionList.get(2).split("-");
                 versionList.set(2, incrementAndQualifier[0]);
                 versionList.add(incrementAndQualifier[1]);
                 return versionList;
             }
        } else {
            return new ArrayList<>();
        }
    }
}
