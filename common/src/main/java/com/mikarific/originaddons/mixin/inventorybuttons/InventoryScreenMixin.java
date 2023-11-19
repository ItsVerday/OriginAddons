package com.mikarific.originaddons.mixin.inventorybuttons;

import com.mikarific.originaddons.util.InventoryButtons;
import com.mikarific.originaddons.util.MenuUtils;
import net.minecraft.client.gui.screen.ingame.AbstractInventoryScreen;
import net.minecraft.client.gui.screen.ingame.InventoryScreen;
import net.minecraft.client.gui.widget.TexturedButtonWidget;
import net.minecraft.client.util.math.MatrixStack;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.entity.player.PlayerInventory;
import net.minecraft.item.ItemStack;
import net.minecraft.network.packet.c2s.play.CloseHandledScreenC2SPacket;
import net.minecraft.network.packet.s2c.play.CloseScreenS2CPacket;
import net.minecraft.screen.PlayerScreenHandler;
import net.minecraft.server.network.ServerPlayerEntity;
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
    private static TexturedButtonWidget navigatorMenuButton;
    public InventoryScreenMixin(PlayerScreenHandler screenHandler, PlayerInventory playerInventory, Text text) {
        super(screenHandler, playerInventory, text);
    }

    private void placeCursorItem(PlayerEntity player) {
        ItemStack itemStack = this.getScreenHandler().getCursorStack();
        if (!itemStack.isEmpty()) {
            if (player.isAlive()) {
                player.getInventory().offerOrDrop(itemStack);
            } else {
                player.dropItem(itemStack, false);
            }

            this.getScreenHandler().setCursorStack(ItemStack.EMPTY);
        }
    }

    @Inject(method = "init()V", at = @At(value = "INVOKE", target = "Lnet/minecraft/client/gui/screen/ingame/InventoryScreen;addDrawableChild(Lnet/minecraft/client/gui/Element;)Lnet/minecraft/client/gui/Element;"))
    private void addButtons(CallbackInfo ci) {
        if (InventoryButtons.isEnabled()) {
            Identifier TEXTURE = new Identifier("originaddons", "textures/gui/inventory/inventory_buttons.png");
            navigatorMenuButton = new TexturedButtonWidget(this.x + 127, this.height / 2 - 22, 20, 18, 0, 0, 18, TEXTURE, 20, 36, (button) -> {
                assert client != null;
                assert client.player != null;
                MenuUtils.sendCommand(client.player, "/navigator");
                placeCursorItem(client.player);
            });

            addDrawableChild(navigatorMenuButton);

            /*
            window.resizeWindow();
            int TEXTURE_WIDTH = 20;
            int TEXTURE_HEIGHT = 36;
            this.navigatorButton = new UIButton(TEXTURE, this.x + 127, this.height / 2 - 22, 20, 18, 0, 0, 18, TEXTURE_WIDTH, TEXTURE_HEIGHT, () -> {
                assert client != null;
                assert client.player != null;
                MenuUtils.sendCommand(client.player, "/navigator");
                close();
            }, true).setChildOf(window);
            */
        }
    }

    @Inject(method = "method_19891(Lnet/minecraft/client/gui/widget/ButtonWidget;)V", at = @At(value = "TAIL"))
    private void moveButtonsWithRecipeBook(CallbackInfo ci) {
        if (InventoryButtons.isEnabled()) {
            navigatorMenuButton.setX(this.x + 127);
        }
    }
}
