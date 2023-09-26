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
        if (json.get("latestVersion") != null) {
            String current = OriginAddons.VERSION;
            String latest = json.get("latestVersion").getAsString();

            return compareVersions(current, latest) == -1;
        }

        return false;
    }

    public static int compareVersions(String a, String b) {
        List<Integer> versionA = parseVersion(a).stream().map(Integer::parseInt).toList();
        List<Integer> versionB = parseVersion(b).stream().map(Integer::parseInt).toList();

        for (int i = 0; i < 3; i++) {
            int ia = versionA.get(i);
            int ib = versionB.get(i);

            if (ia > ib) return 1;
            if (ia < ib) return -1;
        }

        return 0;
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
