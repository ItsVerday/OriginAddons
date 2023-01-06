package com.mikarific.originaddons.menu;

import com.mikarific.originaddons.ui.Window;
import com.mikarific.originaddons.ui.components.UIComponent;
import net.minecraft.client.gui.screen.Screen;
import net.minecraft.client.util.math.MatrixStack;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;

import java.util.ArrayList;
import java.util.List;

public abstract class CustomMenu {
    private List<UIComponent> tabSupportedElements = new ArrayList<>();
    int tabbedElement = -1;

    public void doInit(Screen screen, Window window) {
        tabbedElement = -1;
        tabSupportedElements.clear();
        init(screen, window);
    }

    public void doDraw(Screen screen, MatrixStack stack) {
        draw(screen);
        if (tabbedElement != -1) {
            tabSupportedElements.get(tabbedElement).drawTooltip(stack);
        }
    }

    protected void addTabSupportedElement(UIComponent element) {
        tabSupportedElements.add(element);
    }

    public void selectNextElement() {
        if (tabSupportedElements.size() == 0) return;

        if (tabbedElement != -1) {
            tabSupportedElements.get(tabbedElement).setSelected(false);
        }

        tabbedElement++;
        if (tabbedElement == tabSupportedElements.size()) {
            tabbedElement = 0;
        }

        tabSupportedElements.get(tabbedElement).setSelected(true);
    }

    public void clickSelectedElement() {
        if (tabbedElement == -1) return;
        tabSupportedElements.get(tabbedElement).click();
    }

    protected abstract void init(Screen screen, Window window);
    protected abstract void draw(Screen screen);
    public abstract void close(Screen screen);

    public abstract boolean isEnabled();

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

    public void mouseClicked(CallbackInfoReturnable<Boolean> cir) {
        cir.cancel();
    }

    public boolean matchScreen(Screen screen) {
        if (!isEnabled()) return false;
        return matches(screen);
    }
}