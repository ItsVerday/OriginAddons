package com.mikarific.originaddons.ui.components;

import com.mikarific.originaddons.menu.CustomMenus;
import com.mikarific.originaddons.util.MenuUtils;
import com.mojang.blaze3d.systems.RenderSystem;
import net.fabricmc.api.EnvType;
import net.fabricmc.api.Environment;
import net.minecraft.client.MinecraftClient;
import net.minecraft.client.gui.DrawableHelper;
import net.minecraft.client.render.GameRenderer;
import net.minecraft.client.sound.PositionedSoundInstance;
import net.minecraft.client.util.math.MatrixStack;
import net.minecraft.sound.SoundEvents;
import net.minecraft.text.Text;
import net.minecraft.util.Identifier;
import org.jetbrains.annotations.NotNull;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;

import java.util.function.Consumer;

public class UIButton extends UIComponent {
    private final Identifier identifier;
    private int u;
    private int v;
    private int hoveredVOffset;
    private final int textureWidth;
    private final int textureHeight;
    private Runnable action;
    private final TooltipSupplier tooltipSupplier;
    private final boolean playSound;

    public UIButton(Identifier identifier, int x, int y, int width, int height, int u, int v, int hoveredVOffset, int textureWidth, int textureHeight, Runnable action, boolean playSound) {
        super(x, y, width, height);
        this.identifier = identifier;
        this.u = u;
        this.v = v;
        this.hoveredVOffset = hoveredVOffset;
        this.textureWidth = textureWidth;
        this.textureHeight = textureHeight;
        this.action = action;
        this.tooltipSupplier = (button, matrices, mouseX, mouseY) -> {};
        this.playSound = playSound;
    }

    public UIButton(Identifier identifier, int x, int y, int width, int height, int u, int v, int hoveredVOffset, int textureWidth, int textureHeight, Runnable action, TooltipSupplier tooltipSupplier, boolean playSound) {
        super(x, y, width, height);
        this.identifier = identifier;
        this.u = u;
        this.v = v;
        this.hoveredVOffset = hoveredVOffset;
        this.textureWidth = textureWidth;
        this.textureHeight = textureHeight;
        this.action = action;
        this.tooltipSupplier = tooltipSupplier;
        this.playSound = playSound;
    }

    public void draw(@NotNull MatrixStack matrixStack, double mouseX, double mouseY, boolean hideTooltips) {
        if (this.isVisible()) {
            matrixStack.push();
            matrixStack.translate(this.getX(), this.getY(), 1f);
            RenderSystem.setShader(GameRenderer::getPositionTexShader);
            RenderSystem.setShaderColor(1.0f, 1.0f, 1.0f, 1.0f);
            RenderSystem.setShaderTexture(0, this.getIdentifier());
            if (this.isHoveredOrSelected()) {
                DrawableHelper.drawTexture(matrixStack, 0, 0, this.getU(), this.getV() + hoveredVOffset, this.getWidth(), this.getHeight(), this.getTextureWidth(), this.getTextureHeight());
            } else {
                DrawableHelper.drawTexture(matrixStack, 0, 0, this.getU(), this.getV(), this.getWidth(), this.getHeight(), this.getTextureWidth(), this.getTextureHeight());
            }

            matrixStack.pop();
        }

        super.draw(matrixStack, mouseX, mouseY, hideTooltips);
    }

    public void renderTooltip(MatrixStack matrices, double mouseX, double mouseY) {
        tooltipSupplier.onTooltip(this, matrices, mouseX, mouseY + (CustomMenus.getCurrentMenu() != null && CustomMenus.inventoryEnabled() ? 43 : 0));
    }

    public void mouseClicked(int button, CallbackInfoReturnable<Boolean> cir) {
        if ((button == 0 || button == 1)) {
            if (cir.isCancellable()) cir.cancel();
            click(button);
        }
    }

    @Override
    public void click(int button) {
        if (playSound) MinecraftClient.getInstance().getSoundManager().play(PositionedSoundInstance.master(SoundEvents.UI_BUTTON_CLICK, 1.0F));
        MenuUtils.setClickButton(button);
        action.run();
    }

    @Override
    public void renderFixedTooltip(MatrixStack stack) {
        tooltipSupplier.onTooltip(this, stack, getX() + getWidth() - 4, getY() + 8 + (CustomMenus.getCurrentMenu() != null && CustomMenus.inventoryEnabled() ? -43 : 0));
    }

    @Environment(EnvType.CLIENT)
    public interface TooltipSupplier {
        void onTooltip(UIButton button, MatrixStack matrices, double mouseX, double mouseY);

        default void supply(Consumer<Text> consumer) {
        }
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

    public UIButton setU(int u) {
        this.u = u;
        return this;
    }

    public UIButton setV(int v) {
        this.v = v;
        return this;
    }

    public UIButton setHoveredVOffset(int hoveredVOffset) {
        this.hoveredVOffset = hoveredVOffset;
        return this;
    }

    public UIButton setAction(Runnable action) {
        this.action = action;
        return this;
    }
}
