package com.mikarific.originaddons.util.emojipicker;

import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import com.google.gson.JsonParser;
import com.mikarific.originaddons.OriginAddons;
import org.apache.commons.io.input.BOMInputStream;

import java.io.InputStream;
import java.io.InputStreamReader;
import java.nio.charset.StandardCharsets;
import java.util.ArrayList;

public class EmojiPicker {
    public static void debugLog(String message) {
        OriginAddons.debugLog(message, OriginAddons.getConfig().debugCategory.emojiPicker);
    }

    public static final String EMOJI_FILE = "assets/originaddons/gui/emojipicker/emoji.json";
    private static final ArrayList<EmojiInstance> emojis = new ArrayList<>();
    public static boolean isEmojiPickerEnabled() {
        return OriginAddons.onOriginRealms() && OriginAddons.getConfig().emojiPicker;
    }

    private static boolean loaded = false;

    public static boolean loadEmojis() {
        InputStream inputStream = Thread.currentThread().getContextClassLoader().getResourceAsStream(EMOJI_FILE);
        try (InputStream in = new BOMInputStream(inputStream); InputStreamReader reader = new InputStreamReader(in, StandardCharsets.UTF_8)) {
            JsonObject emojiJSON = JsonParser.parseReader(reader).getAsJsonObject();
            populateEmojisList(emojiJSON);
            loaded = true;
            return true;
        } catch (Exception e) {
            OriginAddons.LOGGER.error("Emojis failed to load!", e);
            return false;
        }
    }

    public static void populateEmojisList(JsonObject obj) {
        emojis.clear();
        ArrayList<EmojiInstance> unordered = new ArrayList<>();

        for (JsonElement emoji: obj.getAsJsonArray("emojis")) {
            EmojiInfo info = EmojiInfoImpl.fromJSON(emoji.getAsJsonObject());
            EmojiInstance instance = new EmojiInstance(info);
            unordered.add(instance);
        }

        for (JsonElement ordered: obj.getAsJsonArray("order")) {
            String emojiID = ordered.getAsString();
            int emojiIndex = findEmoji(unordered, emojiID);

            if (emojiIndex == -1) {
                OriginAddons.LOGGER.warn("Unknown emoji '" + emojiID + "' in emoji order!");
                continue;
            }

            emojis.add(unordered.get(emojiIndex));
            unordered.remove(emojiIndex);
        }

        for (EmojiInstance unorderedEmoji: unordered) {
            OriginAddons.LOGGER.warn("Emoji '" + unorderedEmoji.getInfo().getID() + "' not in emoji order! Added to end of list by default.");
            emojis.add(unorderedEmoji);
        }
    }

    private static int findEmoji(ArrayList<EmojiInstance> emojis, String id) {
        for (int i = 0; i < emojis.size(); i++) {
            if (emojis.get(i).getInfo().getID().equalsIgnoreCase(id)) return i;
        }
        return -1;
    }

    public static boolean attemptLoad() {
        if (loaded) return true;
        return loadEmojis();
    }

    public static ArrayList<EmojiInstance> getEmojis() {
        if (!attemptLoad()) return new ArrayList<>();
        return emojis;
    }

    public static void clearUnlocked() {
        if (!OriginAddons.getConfig().hideLockedEmojis) return;
        
        debugLog("Clearing unlocked emojis...");
        for (EmojiInstance instance: emojis) instance.setUnlocked(false);
    }
}
