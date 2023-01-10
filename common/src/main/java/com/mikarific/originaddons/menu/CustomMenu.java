package com.mikarific.originaddons.menu;

import com.mikarific.originaddons.OriginAddons;
import com.mikarific.originaddons.ui.Window;
import com.mikarific.originaddons.ui.components.UIComponent;
import net.minecraft.client.gui.screen.Screen;
import net.minecraft.client.util.math.MatrixStack;

import java.util.ArrayList;
import java.util.List;

public abstract class CustomMenu {
    private List<UIComponent> selectableElements = new ArrayList<>();
    private int selectedElement = -1;

    public void doInit(Screen screen, Window window) {
        selectedElement = -1;
        selectableElements.clear();
        init(screen, window);
        update(screen, window);
    }

    public void doDraw(Screen screen, MatrixStack stack) {
        draw(screen);
        if (selectedElement != -1) {
            selectableElements.get(selectedElement).drawTooltip(stack);
        }
    }

    protected void addSelectableElement(UIComponent element) {
        selectableElements.add(element);
    }

    public void selectNextElement(boolean reversed) {
        if (selectableElements.size() == 0) return;

        if (selectedElement != -1) {
            selectableElements.get(selectedElement).setSelected(false);
        }

        if (reversed) {
            selectedElement--;
            if (selectedElement < 0) {
                selectedElement = selectableElements.size() - 1;
            }
        } else {
            selectedElement++;
            if (selectedElement == selectableElements.size()) {
                selectedElement = 0;
            }
        }

        selectableElements.get(selectedElement).setSelected(true);
    }

    public void clickSelectedElement() {
        if (selectedElement == -1) return;
        selectableElements.get(selectedElement).click(0);
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