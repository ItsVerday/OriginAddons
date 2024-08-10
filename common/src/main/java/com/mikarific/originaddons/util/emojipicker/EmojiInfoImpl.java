package com.mikarific.originaddons.util.emojipicker;

import com.google.gson.JsonObject;
import net.minecraft.text.Style;
import net.minecraft.text.Text;
import net.minecraft.text.TextColor;
import net.minecraft.util.Identifier;

public class EmojiInfoImpl implements EmojiInfo {
    private final String id;
    private final int width;
    private final String display;
    private final Identifier font;
    private final TextColor textColor;

    private Text styledText = null;

    public EmojiInfoImpl(String id, int width, String display, Identifier font, TextColor textColor) {
        this.id = id;
        this.width = width;
        this.display = display;
        this.font = font;
        this.textColor = textColor;
    }

    @Override
    public String getID() {
        return id;
    }

    @Override
    public int getWidth() {
        return width;
    }

    @Override
    public String getDisplay() {
        if (display == null) return getID();
        return display;
    }

    @Override
    public Identifier getFont() {
        return font;
    }

    @Override
    public TextColor getTextColor() {
        return textColor;
    }

    @Override
    public Text getStyledText() {
        if (styledText == null) styledText = EmojiInfo.super.getStyledText();
        return styledText;
    }

    public static EmojiInfo fromJSON(JsonObject obj) {
        String id = obj.get("id").getAsString();
        int width = obj.has("width") ? obj.get("width").getAsInt() : 1;
        String display = obj.has("display") ? obj.get("display").getAsString() : null;
        Identifier font = obj.has("font") ? Identifier.tryParse(obj.get("font").getAsString()) : Style.DEFAULT_FONT_ID;
        TextColor textColor = TextColor.parse(obj.has("color") ? obj.get("color").getAsString() : "white").getOrThrow();

        return new EmojiInfoImpl(id, width, display, font, textColor);
    }
}