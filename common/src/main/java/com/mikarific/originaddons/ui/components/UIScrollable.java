package com.mikarific.originaddons.ui.components;

import com.mikarific.originaddons.ui.Window;
import com.mikarific.originaddons.util.Other;
import com.mojang.blaze3d.systems.RenderSystem;
import net.minecraft.client.MinecraftClient;
import net.minecraft.client.util.math.MatrixStack;
import org.jetbrains.annotations.NotNull;

import java.util.ArrayList;

public class UIScrollable extends UIComponent {
    private double maxYOffset;
    private int childrenLength = 0;

    public UIScrollable(int x, int y, int width, int height) {
        super(x, y, width, height);
    }

    public void draw(@NotNull MatrixStack matrixStack, double mouseX, double mouseY) {
        ArrayList<UIButton> buttons = new ArrayList<>();
        if (this.getChildren().size() != childrenLength) {
            double lowestY = MinecraftClient.getInstance().getWindow().getHeight();
            double highestY = 0;
            for (UIComponent child : this.getChildren()) {
                if (child.getY() < lowestY) lowestY = this.getY() + child.getOriginalY();
                if (child.getY() + child.getHeight() > highestY) highestY = this.getY() + child.getOriginalY() + child.getHeight();
            }

            maxYOffset = -(highestY - lowestY - this.getHeight());
            childrenLength = this.getChildren().size();
        }
        if (this.isVisable()) {
            this.setHovered(mouseX, mouseY);
            double scaleFactor = MinecraftClient.getInstance().getWindow().getScaleFactor();
            RenderSystem.enableScissor((int) (this.getX() * scaleFactor), (int) ((MinecraftClient.getInstance().getWindow().getScaledHeight() - (this.getY() + this.getHeight())) * scaleFactor), (int) (this.getWidth() * scaleFactor), (int) (this.getHeight() * scaleFactor));
            this.getChildren().forEach(child -> {
                if (this.isHovered()) {
                    double y = child.getY() + Other.scrollOffset * 5;
                    double yOffset = child.getYOffset() + Other.scrollOffset * 5;
                    if (yOffset > 0.0) {
                        y = this.getY() + child.getOriginalY();
                        yOffset = 0.0;
                    }
                    if (yOffset < maxYOffset) {
                        y = this.getY() + child.getOriginalY() + maxYOffset;
                        yOffset = maxYOffset;
                    }
                    child.setY(y);
                    child.setYOffset(yOffset);
                }
                child.draw(matrixStack, mouseX, mouseY);
                if (child instanceof UIButton) buttons.add((UIButton) child);
            });
            Other.resetScrollOffset();
            RenderSystem.disableScissor();
        }
        buttons.forEach(button -> {
            if (button.isVisable() && button.isHovered() && this.isHovered()) button.renderTooltip(matrixStack, mouseX, mouseY);
        });
    }
}