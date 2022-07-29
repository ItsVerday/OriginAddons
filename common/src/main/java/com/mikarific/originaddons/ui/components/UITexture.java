package com.mikarific.originaddons.ui.components;

import com.mojang.blaze3d.systems.RenderSystem;
import net.minecraft.client.gui.DrawableHelper;
import net.minecraft.client.render.GameRenderer;
import net.minecraft.client.util.math.MatrixStack;
import net.minecraft.util.Identifier;
import org.jetbrains.annotations.NotNull;

public class UITexture extends UIComponent {
    private final Identifier identifier;
    private final int u;
    private final int v;
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

    public void draw(@NotNull MatrixStack matrixStack, double mouseX, double mouseY) {
        if (this.isVisable()) {
            matrixStack.push();
            matrixStack.translate(this.getX(), this.getY(), 1f);
            RenderSystem.setShader(GameRenderer::getPositionTexShader);
            RenderSystem.setShaderColor(1.0f, 1.0f, 1.0f, 1.0f);
            RenderSystem.setShaderTexture(0, this.getIdentifier());
            DrawableHelper.drawTexture(matrixStack, 0, 0, this.getU(), this.getV(), this.getWidth(), this.getHeight(), this.getTextureWidth(), this.getTextureHeight());
            matrixStack.pop();
        }
        super.draw(matrixStack, mouseX, mouseY);
    }

    public Identifier getIdentifier() {
        return identifier;
    }

    public int getU() {
        return u;
    }

    public int getV() {
        return v;
    }

    public int getTextureWidth() {
        return textureWidth;
    }

    public int getTextureHeight() {
        return textureHeight;
    }
}
