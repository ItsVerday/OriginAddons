package com.mikarific.originaddons.ui.components;

import com.mikarific.originaddons.menu.CustomMenus;
import com.mikarific.originaddons.ui.Window;
import net.minecraft.client.util.math.MatrixStack;
import org.jetbrains.annotations.NotNull;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;

import java.util.ArrayList;

public class UIComponent {
    private double x;
    private double y;
    private int width;
    private int height;
    private double originalY;
    private double yOffset = 0.0;
    private boolean visible = true;
    private boolean hovered = false;
    private boolean selected = false;
    private final ArrayList<UIComponent> children = new ArrayList<>();

    public UIComponent(int x, int y, int width, int height) {
        this.x = x;
        this.y = y;
        this.width = width;
        this.height = height;
        this.originalY = y;
    }

    public void draw(@NotNull MatrixStack matrixStack, double mouseX, double mouseY) {
        if (this.isVisible()) setHovered(mouseX, mouseY);
        Window.drawChildren(children, matrixStack, mouseX, mouseY);
    }

    public void mouseClicked(int button, CallbackInfoReturnable<Boolean> cir) {
        Window.clickChildren(children, button, cir);
    }

    public double getX() {
        return this.x;
    }

    public double getY() {
        return this.y;
    }

    public int getWidth() {
        return this.width;
    }

    public int getHeight() {
        return this.height;
    }

    public double getOriginalY() {
        return originalY;
    }

    public double getYOffset() {
        return yOffset;
    }

    public UIComponent setX(double x) {
        this.x = x;
        return this;
    }

    public UIComponent setY(double y) {
        this.y = y;
        return this;
    }

    public UIComponent setWidth(int width) {
        this.width = width;
        return this;
    }

    public UIComponent setHeight(int height) {
        this.height = height;
        return this;
    }

    public UIComponent setYOffset(double yOffset) {
        this.yOffset = yOffset;
        return this;
    }

    public ArrayList<UIComponent> getChildren() {
        return children;
    }

    public boolean isHoveredOrSelected() {
        return this.hovered || this.selected;
    }

    public boolean isHovered() {
        return this.hovered;
    }

    public UIComponent setSelected(boolean selected) {
        this.selected = selected;
        return this;
    }

    public UIComponent setHovered(double mouseX, double mouseY) {
        return setHovered(this.x, this.y, this.width, this.height, mouseX, mouseY);
    }

    public UIComponent setHovered(double x, double y, int width, int height, double mouseX, double mouseY) {
        if (CustomMenus.getCurrentMenu() != null && CustomMenus.inventoryEnabled()) {
            y -= 44;
        }

        this.hovered = mouseX >= x && mouseY >= y && mouseX < x + width && mouseY < y + height;
        return this;
    }

    public boolean isVisible() {
        return this.visible;
    }

    public UIComponent setVisible(boolean visible) {
        this.visible = visible;
        children.forEach(child -> child.setVisible(visible));
        return this;
    }

    public UIComponent setChildOf(UIComponent parent) {
        parent.getChildren().add(this);
        UIComponent child = parent.getChildren().get(parent.getChildren().size() - 1);
        child.setX(child.getX() + parent.getX());
        child.setY(child.getY() + parent.getY());
        child.setVisible(parent.isVisible());
        return this;
    }
    public UIComponent setChildOf(Window parent) {
        parent.getChildren().add(this);
        return this;
    }

    public void click() {}

    public void drawTooltip(MatrixStack stack) {}
}