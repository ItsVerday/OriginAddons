package com.mikarific.originaddons.ui;

import com.mojang.blaze3d.systems.RenderSystem;
import net.minecraft.client.gui.Drawable;
import net.minecraft.client.gui.DrawableHelper;
import net.minecraft.client.gui.widget.ButtonWidget;
import net.minecraft.client.render.GameRenderer;
import net.minecraft.client.util.math.MatrixStack;
import net.minecraft.text.Text;
import net.minecraft.util.Identifier;

public class TextureWidget extends DrawableHelper implements Drawable {
    private final int x;
    private final int y;
    private final int width;
    private final int height;
    private final int u;
    private final int v;
    private final int textureWidth;
    private final int textureHeight;
    private final Identifier texture;

    public TextureWidget(int x, int y, int width, int height, int u, int v, Identifier texture, int textureWidth, int textureHeight) {
        this.x = x;
        this.y = y;
        this.width = width;
        this.height = height;
        this.u = u;
        this.v = v;
        this.textureWidth = textureWidth;
        this.textureHeight = textureHeight;
        this.texture = texture;
    }

    @Override
    public void render(MatrixStack matrices, int mouseX, int mouseY, float delta) {
        RenderSystem.setShader(GameRenderer::getPositionTexShader);
        RenderSystem.setShaderTexture(0, this.texture);
        RenderSystem.enableDepthTest();
        drawTexture(matrices, this.x, this.y, (float) this.u, (float) this.v, this.width, this.height, this.textureWidth, this.textureHeight);
    }
}
