package com.mikarific.originaddons.menu;

import com.mikarific.originaddons.OriginAddons;
import com.mikarific.originaddons.ui.Window;
import com.mikarific.originaddons.ui.components.UIComponent;
import net.minecraft.client.gui.screen.Screen;
import net.minecraft.client.util.math.MatrixStack;

import java.util.ArrayList;
import java.util.List;
import java.util.function.Supplier;

public abstract class CustomMenu {
    private List<UIComponent> selectableElements = new ArrayList<>();
    private List<Supplier<Boolean>> enabledElements = new ArrayList<>();
    private int selectedElement = -1;
    private double oldMouseX = -1;
    private double oldMouseY = -1;

    private boolean renderSelectedTooltip = false;

    public void doInit(Screen screen, Window window) {
        selectedElement = -1;
        selectableElements.clear();
        init(screen, window);
        update(screen, window);
    }

    public boolean isRenderSelectedTooltip() {
        return renderSelectedTooltip;
    }

    public void doDraw(Screen screen, double mouseX, double mouseY) {
        updateMouseHover(mouseX, mouseY);
        draw(screen);
    }

    public void drawSelectedElementTooltip(MatrixStack stack) {
        if (selectedElement != -1 && renderSelectedTooltip) {
            selectableElements.get(selectedElement).renderFixedTooltip(stack);
        }
    }

    private void updateMouseHover(double mouseX, double mouseY) {
        if (oldMouseX == -1) oldMouseX = mouseX;
        if (oldMouseY == -1) oldMouseY = mouseY;

        if (Math.abs(mouseX - oldMouseX) < 1.0 && Math.abs(mouseY - oldMouseY) < 1.0) return;

        for (UIComponent elt: selectableElements) {
            if (elt.isHovered()) {
                oldMouseX = mouseX;
                oldMouseY = mouseY;
                renderSelectedTooltip = false;
                return;
            }
        }
    }

    protected void addSelectableElement(UIComponent element) {
        addSelectableElement(element, () -> true);
    }

    protected void addSelectableElement(UIComponent element, Supplier<Boolean> enabled) {
        selectableElements.add(element);
        enabledElements.add(enabled);
    }

    public void selectNextElement(boolean reversed) {
        if (selectableElements.size() == 0) return;

        renderSelectedTooltip = true;
        if (selectedElement != -1) {
            selectableElements.get(selectedElement).setSelected(false);
        }

        int originalSelectedElement = selectedElement;

        do {
            if (reversed) {
                selectedElement--;
                if (selectedElement < 0) selectedElement = selectableElements.size() - 1;
            } else {
                selectedElement++;
                if (selectedElement == selectableElements.size()) selectedElement = 0;
            }
        } while (!enabledElements.get(selectedElement).get() && selectedElement != originalSelectedElement);

        if (selectedElement == originalSelectedElement && !enabledElements.get(selectedElement).get()) {
            selectedElement = -1;
            return;
        }

        selectableElements.get(selectedElement).setSelected(true);
    }

    public void clickSelectedElement(int button) {
        if (selectedElement == -1) return;
        selectableElements.get(selectedElement).click(button);
    }

    protected abstract void init(Screen screen, Window window);
    protected abstract void draw(Screen screen);
    public abstract void close(Screen screen);

    public void update(Screen screen, Window window) {}

    public abstract boolean isEnabled();

    public boolean enabled() {
        return OriginAddons.onOriginRealms() && OriginAddons.getConfig().customMenus && isEnabled();
    }

    public abstract String getTitle();
    protected boolean matches(Screen screen) {
        return screen.getTitle().getString().contains(getTitle());
    }

    public boolean inventoryEnabled() {
        return false;
    }

    public List<Integer> getAllowedSlots() {
        return new ArrayList<>();
    }

    public void mouseClicked(Screen screen, Window window) {
        if (selectedElement > -1) {
            selectableElements.get(selectedElement).setSelected(false);
        }

        selectedElement = -1;
        update(screen, window);
    }

    public boolean matchScreen(Screen screen) {
        if (!enabled()) return false;
        return matches(screen);
    }
}