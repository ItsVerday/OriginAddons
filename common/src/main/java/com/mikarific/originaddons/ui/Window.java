package com.mikarific.originaddons.ui;

import com.mikarific.originaddons.ui.components.UIButton;
import com.mikarific.originaddons.ui.components.UIComponent;
import com.mikarific.originaddons.ui.components.UIItem;
import com.mikarific.originaddons.ui.components.UITexture;
import net.minecraft.client.MinecraftClient;
import net.minecraft.client.util.math.MatrixStack;
import net.minecraft.util.Identifier;
import org.jetbrains.annotations.NotNull;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;

import java.util.ArrayList;

public class Window {
    private boolean includeInventory = false;
    private ArrayList<UIComponent> children = new ArrayList<>();

    public void draw(@NotNull MatrixStack matrixStack, double mouseX, double mouseY) {
        draw(matrixStack, mouseX, mouseY, false);
    }

    public void draw(@NotNull MatrixStack matrixStack, double mouseX, double mouseY, boolean hideTooltips) {
        if (includeInventory) {
            matrixStack.push();
            matrixStack.translate(0, -43, 0);
            int maxY = 0;
            for (UIComponent child : children) {
                int bottom = (int) (child.getY() + child.getHeight());
                if (bottom > maxY) {
                    maxY = bottom;
                }
            }

            UIComponent inventory = new UITexture(new Identifier("textures/gui/container/inventory.png"), (MinecraftClient.getInstance().getWindow().getScaledWidth() - 176) / 2, maxY, 176, 87, 0, 79, 256, 256);
            inventory.draw(matrixStack, mouseX, mouseY, hideTooltips);
        }

        drawChildren(children, matrixStack, mouseX, mouseY, hideTooltips);
        if (includeInventory) matrixStack.pop();
    }

    public static void drawChildren(ArrayList<UIComponent> children, @NotNull MatrixStack matrixStack, double mouseX, double mouseY, boolean hideTooltips) {
        ArrayList<UIButton> buttons = new ArrayList<>();
        ArrayList<UIItem> items = new ArrayList<>();
        children.forEach(child -> {
            child.draw(matrixStack, mouseX, mouseY, hideTooltips);
            if (child instanceof UIButton) buttons.add((UIButton) child);
            if (child instanceof UIItem) items.add((UIItem) child);
        });
        buttons.forEach(button -> {
            if (button.isVisible() && button.isHovered() && !hideTooltips) button.renderTooltip(matrixStack, mouseX, mouseY);
        });
        items.forEach(item -> {
            if (item.isVisible() && item.isHovered() && !hideTooltips) item.renderTooltip(matrixStack, mouseX, mouseY);
        });
    }

    public void mouseClicked(int button, CallbackInfoReturnable<Boolean> cir) {
        clickChildren(children, button, cir);
    }

    public static void clickChildren(ArrayList<UIComponent> children, int button, CallbackInfoReturnable<Boolean> cir) {
        children.forEach(child -> {
            if (child.isVisible() && child.isHovered()) child.mouseClicked(button, cir);
        });

    }

    public ArrayList<UIComponent> getChildren() {
        return children;
    }

    public void resizeWindow() {
        this.children = new ArrayList<>();
    }

    public void includeInventory() {
        includeInventory = !includeInventory;
    }
}