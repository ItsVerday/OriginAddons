package com.mikarific.originaddons.ui;

import com.mikarific.originaddons.ui.components.UIButton;
import com.mikarific.originaddons.ui.components.UIComponent;
import com.mikarific.originaddons.ui.components.UIEmoji;
import net.minecraft.client.util.math.MatrixStack;
import org.jetbrains.annotations.NotNull;

import java.util.ArrayList;

public class Window {
    private final ArrayList<UIComponent> children = new ArrayList<>();

    public void draw(@NotNull MatrixStack matrixStack, double mouseX, double mouseY) {
        drawChildren(children, matrixStack, mouseX, mouseY);
    }

    public static void drawChildren(ArrayList<UIComponent> children, @NotNull MatrixStack matrixStack, double mouseX, double mouseY) {
        ArrayList<UIButton> buttons = new ArrayList<>();
        children.forEach(child -> {
            child.draw(matrixStack, mouseX, mouseY);
            if (child instanceof UIButton) buttons.add((UIButton) child);
        });
        buttons.forEach(button -> {
            if (button.isVisable() && button.isHovered()) button.renderTooltip(matrixStack, mouseX, mouseY);
        });
    }

    public void mouseClicked(int button) {
        clickChildren(children, button);
    }

    public static void clickChildren(ArrayList<UIComponent> children, int button) {
        children.forEach(child -> {
            if (child.isVisable() && child.isHovered()) child.mouseClicked(button);
        });
    }

    public ArrayList<UIComponent> getChildren() {
        return children;
    }
}