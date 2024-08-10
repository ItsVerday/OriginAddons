package com.mikarific.originaddons.ui;

import com.mikarific.originaddons.ui.components.UIButton;
import com.mikarific.originaddons.ui.components.UIComponent;
import com.mikarific.originaddons.ui.components.UIItem;
import com.mikarific.originaddons.ui.components.UITexture;
import net.minecraft.client.MinecraftClient;
import net.minecraft.client.gui.DrawContext;
import net.minecraft.client.util.math.MatrixStack;
import net.minecraft.util.Identifier;
import org.jetbrains.annotations.NotNull;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;

import java.util.ArrayList;

public class Window {
    private boolean includeInventory = false;
    private ArrayList<UIComponent> children = new ArrayList<>();

    public void draw(@NotNull DrawContext context, double mouseX, double mouseY) {
        draw(context, mouseX, mouseY, false);
    }

    public void draw(@NotNull DrawContext context, double mouseX, double mouseY, boolean hideTooltips) {
        if (includeInventory) {
            context.getMatrices().push();
            context.getMatrices().translate(0, -43, 0);
            int maxY = 0;
            for (UIComponent child : children) {
                int bottom = (int) (child.getY() + child.getHeight());
                if (bottom > maxY) {
                    maxY = bottom;
                }
            }

            UIComponent inventory = new UITexture(new Identifier("textures/gui/container/inventory.png"), (MinecraftClient.getInstance().getWindow().getScaledWidth() - 176) / 2, maxY, 176, 87, 0, 79, 256, 256);
            inventory.draw(context, mouseX, mouseY, hideTooltips);
        }

        int halfWidth = MinecraftClient.getInstance().getWindow().getScaledWidth() / 2;
        int halfHeight = MinecraftClient.getInstance().getWindow().getScaledHeight() / 2;
        mouseX -= halfWidth;
        mouseY -= halfHeight;

        context.getMatrices().translate(halfWidth, halfHeight, 0.0);
        drawChildren(children, context, mouseX, mouseY, hideTooltips);
        if (includeInventory) context.getMatrices().pop();
    }

    public static void drawChildren(ArrayList<UIComponent> children, @NotNull DrawContext context, double mouseX, double mouseY, boolean hideTooltips) {
        ArrayList<UIButton> buttons = new ArrayList<>();
        ArrayList<UIItem> items = new ArrayList<>();
        children.forEach(child -> {
            child.draw(context, mouseX, mouseY, hideTooltips);
            if (child instanceof UIButton) buttons.add((UIButton) child);
            if (child instanceof UIItem) items.add((UIItem) child);
        });
        buttons.forEach(button -> {
            if (button.isVisible() && button.isHovered() && !hideTooltips) button.renderTooltip(context, mouseX, mouseY);
        });
        items.forEach(item -> {
            if (item.isVisible() && item.isHovered() && !hideTooltips) item.renderTooltip(context, mouseX, mouseY);
        });
    }

    public UIComponent mouseClicked(int button, CallbackInfoReturnable<Boolean> cir) {
        return clickChildren(children, button, cir);
    }

    public static UIComponent clickChildren(ArrayList<UIComponent> children, int button, CallbackInfoReturnable<Boolean> cir) {
        UIComponent clickedElement = null;
        for (UIComponent child: children) {
            if (child.isVisible() && child.isHovered()) {
                UIComponent clickedChild = child.mouseClicked(button, cir);
                if (clickedElement == null && clickedChild != null) clickedElement = clickedChild;
            }
        }

        return clickedElement;
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