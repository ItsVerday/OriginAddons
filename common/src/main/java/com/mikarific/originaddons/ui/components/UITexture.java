package com.mikarific.originaddons.ui.components;

import com.mojang.blaze3d.systems.RenderSystem;
import net.minecraft.client.gui.DrawContext;
import net.minecraft.client.render.GameRenderer;
import net.minecraft.util.Identifier;
import org.jetbrains.annotations.NotNull;

public class UITexture extends UIComponent {
    private final Identifier identifier;
    private int u;
    private int v;
    private final int textureWidth;
    private final int textureHeight;

    public UITexture(Identifier identifier, int x, int y, int width, int height, int u, int v, int textureWidth, int textureHeight) {
        super(x, y, width, height);
        this.identifier = identifier;
        this.u = u;
        this.v = v;
        this.textureWidth = textureWidth;
        this.textureHeight = textureHeight;
    }

    public void draw(@NotNull DrawContext context, double mouseX, double mouseY, boolean hideTooltips) {
        if (this.isVisible()) {
            context.getMatrices().push();
            context.getMatrices().translate(this.getX(), this.getY(), 1f);
            RenderSystem.setShader(GameRenderer::getPositionTexProgram);
            RenderSystem.setShaderColor(1.0f, 1.0f, 1.0f, 1.0f);
            RenderSystem.setShaderTexture(0, this.getIdentifier());
            context.drawTexture(this.getIdentifier(), 0, 0, this.getU(), this.getV(), this.getWidth(), this.getHeight(), this.getTextureWidth(), this.getTextureHeight());
            context.getMatrices().pop();
        }
        super.draw(context, mouseX, mouseY, hideTooltips);
    }

    public Identifier getIdentifier() {
        return identifier;
    }

    public int getU() {
        return u;
    }

    public UITexture setU(int u) {
        this.u = u;
        return this;
    }

    public int getV() {
        return v;
    }

    public UITexture setV(int v) {
        this.v = v;
        return this;
    }

    public int getTextureWidth() {
        return textureWidth;
    }

    public int getTextureHeight() {
        return textureHeight;
    }
}
