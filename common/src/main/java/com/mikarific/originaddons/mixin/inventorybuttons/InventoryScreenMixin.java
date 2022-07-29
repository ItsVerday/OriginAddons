package com.mikarific.originaddons.mixin.inventorybuttons;

import com.mikarific.originaddons.ui.Window;
import com.mikarific.originaddons.ui.components.UIButton;
import com.mikarific.originaddons.ui.components.UIComponent;
import com.mikarific.originaddons.util.InventoryButtons;
import net.minecraft.client.gui.screen.ingame.AbstractInventoryScreen;
import net.minecraft.client.gui.screen.ingame.InventoryScreen;
import net.minecraft.client.util.math.MatrixStack;
import net.minecraft.entity.player.PlayerInventory;
import net.minecraft.screen.PlayerScreenHandler;
import net.minecraft.text.Text;
import net.minecraft.util.Identifier;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;

@Mixin(InventoryScreen.class)
public abstract class InventoryScreenMixin extends AbstractInventoryScreen<PlayerScreenHandler> {
    @Shadow private float mouseX;
    private final Window window = new Window();
    private UIComponent navigatorButton;

    public InventoryScreenMixin(PlayerScreenHandler screenHandler, PlayerInventory playerInventory, Text text) {
        super(screenHandler, playerInventory, text);
    }

    @Inject(method = "init()V", at = @At(value = "INVOKE", target = "Lnet/minecraft/client/gui/screen/ingame/InventoryScreen;addDrawableChild(Lnet/minecraft/client/gui/Element;)Lnet/minecraft/client/gui/Element;"))
    private void addButtons(CallbackInfo ci) {
        if (InventoryButtons.isEnabled()) {
            Identifier TEXTURE = new Identifier("originaddons", "gui/inventory/inventory_buttons.png");
            int TEXTURE_WIDTH = 20;
            int TEXTURE_HEIGHT = 36;
            this.navigatorButton = new UIButton(TEXTURE, this.x + 127, this.height / 2 - 22, 20, 18, 0, 0, 18, TEXTURE_WIDTH, TEXTURE_HEIGHT, () -> {
                assert client != null;
                assert client.player != null;
                client.player.sendChatMessage("/navigator");
            }, true).setChildOf(window);
        }
    }

    @Inject(method = "method_19891(Lnet/minecraft/client/gui/widget/ButtonWidget;)V", at = @At(value = "TAIL"))
    private void moveButtonsWithRecipeBook(CallbackInfo ci) {
        if (InventoryButtons.isEnabled()) {
            this.navigatorButton.setX(this.x + 127);
        }
    }

    @Inject(method = "render", at = @At(value = "INVOKE", target = "net/minecraft/client/gui/screen/ingame/InventoryScreen.drawMouseoverTooltip(Lnet/minecraft/client/util/math/MatrixStack;II)V"))
    private void renderWindow(MatrixStack matrices, int mouseX, int mouseY, float delta, CallbackInfo ci) {
        window.draw(matrices, mouseX, mouseY);
    }

    @Inject(method = "mouseClicked(DDI)Z", at = @At("HEAD"))
    private void clickWindow(double mouseX, double mouseY, int button, CallbackInfoReturnable<Boolean> cir) {
        window.mouseClicked(button);
    }
}
