package com.mikarific.originaddons.mixin.custommenus;

import com.mikarific.originaddons.util.custommenus.CustomMenus;
import com.mikarific.originaddons.util.custommenus.screens.*;
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
        if (CustomMenus.isEnabled(currentScreen)) {
            assert currentScreen != null;
            boolean slotEnabled = false;
            boolean inventoryEnabled = false;
            if (CustomMenus.isBadges(currentScreen)) {
                slotEnabled = Badges.getAllowedSlots().contains(slot.getIndex());
                inventoryEnabled = Badges.inventoryEnabled();
            }
            if (CustomMenus.isGesturesFavorites(currentScreen)) {
                slotEnabled = GesturesFavorites.getAllowedSlots().contains(slot.getIndex());
                inventoryEnabled = GesturesFavorites.inventoryEnabled();
            }
            if (CustomMenus.isGesturesAll(currentScreen)) {
                slotEnabled = GesturesAll.getAllowedSlots().contains(slot.getIndex());
                inventoryEnabled = GesturesAll.inventoryEnabled();
            }
            if (CustomMenus.isNavigator(currentScreen)) {
                slotEnabled = Navigator.getAllowedSlots().contains(slot.getIndex());
                inventoryEnabled = Navigator.inventoryEnabled();
            }
            if (CustomMenus.isOrbit(currentScreen)) {
                slotEnabled = Orbit.getAllowedSlots().contains(slot.getIndex());
                inventoryEnabled = Orbit.inventoryEnabled();
            }
            if (CustomMenus.isPainting(currentScreen)) {
                slotEnabled = Painting.getAllowedSlots().contains(slot.getIndex());
                inventoryEnabled = Painting.inventoryEnabled();
            }
            if (CustomMenus.isProfile(currentScreen)) {
                slotEnabled = Profile.getAllowedSlots().contains(slot.getIndex());
                inventoryEnabled = Profile.inventoryEnabled();
            }
            if (CustomMenus.isProfileStaff(currentScreen)) {
                slotEnabled = ProfileStaff.getAllowedSlots().contains(slot.getIndex());
                inventoryEnabled = ProfileStaff.inventoryEnabled();
            }
            if (CustomMenus.isProfilePunish(currentScreen)) {
                slotEnabled = ProfilePunish.getAllowedSlots().contains(slot.getIndex());
                inventoryEnabled = ProfilePunish.inventoryEnabled();
            }
            if (CustomMenus.isRealms(currentScreen)) {
                slotEnabled = Realms.getAllowedSlots().contains(slot.getIndex());
                inventoryEnabled = Realms.inventoryEnabled();
            }
            if (CustomMenus.isRealmsRoleSelect(currentScreen)) {
                slotEnabled = RealmsRoleSelect.getAllowedSlots().contains(slot.getIndex());
                inventoryEnabled = RealmsRoleSelect.inventoryEnabled();
            }
            if (CustomMenus.isRealmsSettings(currentScreen)) {
                slotEnabled = RealmsSettings.getAllowedSlots().contains(slot.getIndex());
                inventoryEnabled = RealmsSettings.inventoryEnabled();
            }

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
        Screen currentScreen = MinecraftClient.getInstance().currentScreen;
        if (CustomMenus.isEnabled(currentScreen) && !isEnabled()) {
            cir.cancel();
            cir.setReturnValue(false);
            return;
        }
    }
}
