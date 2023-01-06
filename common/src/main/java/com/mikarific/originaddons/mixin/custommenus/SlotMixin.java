package com.mikarific.originaddons.mixin.custommenus;

import com.mikarific.originaddons.menu.CustomMenu;
import com.mikarific.originaddons.menu.CustomMenus;
import net.minecraft.client.MinecraftClient;
import net.minecraft.client.gui.screen.Screen;
import net.minecraft.client.network.ClientPlayerEntity;
import net.minecraft.entity.player.PlayerInventory;
import net.minecraft.item.ItemStack;
import net.minecraft.screen.slot.Slot;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;

@Mixin(Slot.class)
public abstract class SlotMixin {
    @Shadow public abstract boolean isEnabled();

    @Inject(method = "isEnabled", at = @At("HEAD"), cancellable = true)
    private void slotEnabled(CallbackInfoReturnable<Boolean> cir) {
        assert MinecraftClient.getInstance().player != null;
        ClientPlayerEntity player = MinecraftClient.getInstance().player;
        Screen currentScreen = MinecraftClient.getInstance().currentScreen;
        Slot slot = ((Slot)(Object)this);
        if (CustomMenus.getCurrentMenu() != null) {
            assert currentScreen != null;
            boolean slotEnabled = CustomMenus.getCurrentMenu().getAllowedSlots().contains(slot.getIndex());
            boolean inventoryEnabled = CustomMenus.inventoryEnabled();
            if (inventoryEnabled && slot.inventory instanceof PlayerInventory) {
                cir.setReturnValue(true);
            } else {
                cir.setReturnValue(slotEnabled);
            }
        } else {
            cir.setReturnValue(true);
        }
    }

    @Inject(method = "canInsert", at = @At("HEAD"), cancellable = true)
    private void canInsert(ItemStack stack, CallbackInfoReturnable<Boolean> cir) {
        assert MinecraftClient.getInstance().player != null;
        if (CustomMenus.getCurrentMenu() != null && !isEnabled()) {
            cir.cancel();
            cir.setReturnValue(false);
            return;
        }
    }
}
