package com.mikarific.originaddons.ui.components;

import net.minecraft.client.MinecraftClient;
import net.minecraft.client.gui.DrawContext;
import net.minecraft.text.Text;
import org.jetbrains.annotations.NotNull;

public class UIText extends UIComponent {
    private Text text;
    private int color;

    public UIText(Text text, int color, int x, int y) {
        super(x, y, 0, 0);
        this.text = text;
        this.color = color;
        this.setX(x);
        this.setY(y);
    }

    public void draw(@NotNull DrawContext context, double mouseX, double mouseY, boolean hideTooltips) {
        if (this.isVisible()) {
            context.getMatrices().push();
            context.getMatrices().translate(this.getX(), this.getY(), 1f);
            context.drawText(MinecraftClient.getInstance().textRenderer, this.text, 0, 0, this.color, false);
            context.getMatrices().pop();
        }
        super.draw(context, mouseX, mouseY, hideTooltips);
    }

    public UIText setText(Text text) {
        this.text = text;
        return this;
    }
}
