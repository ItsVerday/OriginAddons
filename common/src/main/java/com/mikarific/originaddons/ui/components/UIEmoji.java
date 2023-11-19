package com.mikarific.originaddons.ui.components;

import com.mikarific.originaddons.util.emojipicker.EmojiInstance;
import net.minecraft.client.MinecraftClient;
import net.minecraft.client.gui.DrawContext;
import net.minecraft.util.Identifier;
import org.jetbrains.annotations.NotNull;

public class UIEmoji extends UIButton {
    private final EmojiInstance emoji;

    public UIEmoji(EmojiInstance emoji, Identifier identifier, int x, int y, int u, int v, int hoveredVOffset, int textureWidth, int textureHeight, Runnable action, boolean playSound) {
        super(identifier, x, y, emoji.getInfo().getWidth() * 12, 12, u, v, hoveredVOffset, textureWidth, textureHeight, action, playSound);
        this.emoji = emoji;
    }

    public UIEmoji(EmojiInstance emoji, Identifier identifier, int x, int y, int u, int v, int hoveredVOffset, int textureWidth, int textureHeight, Runnable action, TooltipSupplier tooltipSupplier, boolean playSound) {
        super(identifier, x, y, emoji.getInfo().getWidth() * 12, 12, u, v, hoveredVOffset, textureWidth, textureHeight, action, tooltipSupplier, playSound);
        this.emoji = emoji;
    }

    public void draw(@NotNull DrawContext context, double mouseX, double mouseY, boolean hideTooltips) {
        super.draw(context, mouseX, mouseY, hideTooltips);
        if (this.isVisible()) {
            context.getMatrices().push();
            context.getMatrices().translate(this.getX(), this.getY(), 1f);
            context.drawText(MinecraftClient.getInstance().textRenderer, emoji.getInfo().getStyledText(), 1, 2, emoji.getInfo().getTextColor().getRgb(), false);
            context.getMatrices().pop();
        }
    }
}
