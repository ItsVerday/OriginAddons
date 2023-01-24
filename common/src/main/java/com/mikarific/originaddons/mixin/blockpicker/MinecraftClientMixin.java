package com.mikarific.originaddons.mixin.blockpicker;

import com.mikarific.originaddons.OriginAddons;
import com.mikarific.originaddons.util.ItemStackUtils;
import com.mikarific.originaddons.util.blockpicker.BlockPicker;
import net.minecraft.block.BlockState;
import net.minecraft.client.MinecraftClient;
import net.minecraft.client.network.ClientPlayerEntity;
import net.minecraft.entity.player.PlayerInventory;
import net.minecraft.item.ItemStack;
import net.minecraft.util.hit.BlockHitResult;
import net.minecraft.util.hit.HitResult;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.registry.Registry;
import net.minecraft.world.BlockView;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

import java.util.Arrays;
import java.util.HashMap;
import java.util.Map;
import java.util.stream.Collectors;

@Mixin(MinecraftClient.class)
public class MinecraftClientMixin {
    private static final boolean DEBUG_BLOCKPICKER = false;

    private static void debugLog(String msg) {
        OriginAddons.debugLog(msg, DEBUG_BLOCKPICKER);
    }

    @Inject(at = @At("HEAD"), method = "doItemPick", cancellable = true)
    private void doItemPickWrapper(CallbackInfo ci) {
        if (BlockPicker.isEnabled()) {
            MinecraftClient client = MinecraftClient.getInstance();
            ClientPlayerEntity player = client.player;
            HitResult result = client.crosshairTarget;

            if (result instanceof BlockHitResult blockHitResult) {
                assert player != null;
                BlockView view = player.getEntityWorld();
                BlockPos pos = blockHitResult.getBlockPos();
                String customBlockName = getCustomBlockName(view, pos);

                if (customBlockName.length() == 0) return;

                PlayerInventory inventory = player.getInventory();
                int pickSlot = -1;

                debugLog("Attempting to match '" + customBlockName + "' for blockpicker...");

                for (int slot = 0; slot < inventory.size(); slot++) {
                    ItemStack itemStack = inventory.getStack(slot);

                    String customBlockItemName = getCustomBlockItemName(itemStack);
                    if (customBlockItemName.contains(":")) {
                        String[] parts = customBlockItemName.split(":");
                        customBlockItemName = parts[parts.length - 1];
                    }

                    customBlockItemName = BlockPicker.cleanModelName(customBlockItemName);

                    if (customBlockItemName.length() == 0) continue;
                    if (compareCustomBlockNames(customBlockName, customBlockItemName)) {
                        debugLog("MATCH! Slot " + slot + ": " + customBlockItemName + " <- (" + customBlockName + ")");
                        pickSlot = slot;
                        break;
                    }

                    debugLog("- Slot " + slot + ": " + customBlockItemName);
                }

                if (pickSlot > -1) {
                    if (PlayerInventory.isValidHotbarIndex(pickSlot)) {
                        inventory.selectedSlot = pickSlot;
                        // Slot 40 = offhand, which means we are already holding the block
                    } else if (pickSlot != 40) {
                        assert client.interactionManager != null;
                        client.interactionManager.pickFromInventory(pickSlot);
                    }
                } else {
                    debugLog("Failed to match '" + customBlockName + "' to any ItemStacks in inventory!");
                }

                ci.cancel();
            }
        }
    }

    private boolean compareCustomBlockNames(String a, String b) {
        return cleanCustomBlockName(a).equalsIgnoreCase(cleanCustomBlockName(b));
    }

    private static final Map<String, String> cleanCustomBlockNameCache = new HashMap<>();

    private String cleanCustomBlockName(String name) {
        if (cleanCustomBlockNameCache.containsKey(name)) return cleanCustomBlockNameCache.get(name);

        String cleaned = Arrays.stream(name.toLowerCase().split("_")).map(word -> word.endsWith("s") ? word.substring(0, word.length() - 1) : word).sorted().collect(Collectors.joining("_"));
        cleanCustomBlockNameCache.put(name, cleaned);
        return cleaned;
    }

    private String getCustomBlockItemName(ItemStack itemStack) {
        String customID = ItemStackUtils.getItemStackCustomID(itemStack);
        if (customID.length() > 0) return customID;

        return Registry.ITEM.getId(itemStack.getItem()).getPath();
    }

    private String getCustomBlockName(BlockView view, BlockPos pos) {
        BlockState blockState = view.getBlockState(pos);
        String customBlockName = BlockPicker.getCustomBlockName(blockState);

        if (customBlockName.equalsIgnoreCase("empty_button")) {
            BlockState above = view.getBlockState(pos.add(0, 1, 0));
            customBlockName = BlockPicker.getCustomBlockName(above);
        }

        return customBlockName;
    }
}
