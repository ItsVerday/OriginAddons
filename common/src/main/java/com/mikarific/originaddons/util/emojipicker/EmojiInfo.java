package com.mikarific.originaddons.util.emojipicker;

import net.minecraft.text.Style;
import net.minecraft.text.Text;
import net.minecraft.text.TextColor;
import net.minecraft.util.Identifier;

public interface EmojiInfo {
    String getID();
    default String getToken() {
        return ":" + getID() + ":";
    }

    int getWidth();

    String getDisplay();

    Identifier getFont();

    default Text getStyledText() {
        Style style = Style.EMPTY.withFont(getFont()).withColor(getTextColor());
        return Text.translatable(getDisplay()).setStyle(style);
    }

    TextColor getTextColor();
}