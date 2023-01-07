package com.mikarific.originaddons.util;

import com.google.common.collect.Lists;
import it.unimi.dsi.fastutil.ints.Int2ObjectArrayMap;
import net.minecraft.client.MinecraftClient;
import net.minecraft.client.util.InputUtil;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NbtCompound;
import net.minecraft.nbt.NbtList;
import net.minecraft.network.packet.c2s.play.ClickSlotC2SPacket;
import net.minecraft.screen.GenericContainerScreenHandler;
import net.minecraft.screen.slot.SlotActionType;
import net.minecraft.text.*;
import net.minecraft.util.Formatting;

import java.util.List;
import java.util.Objects;

public class MenuUtils {
    public static List<Text> getDisplayTooltip(ItemStack item) {
        List<Text> list = Lists.newArrayList();
        MutableText mutableText = (new LiteralText("")).append(item.getName()).formatted(item.getRarity().formatting);
        if (item.hasCustomName()) {
            mutableText.formatted(Formatting.ITALIC);
        }
        list.add(mutableText);
        int j;
        if (item.hasNbt()) {
            assert item.getNbt() != null;
            if (item.getNbt().contains("display", 10)) {
                NbtCompound nbtCompound = item.getNbt().getCompound("display");
                if (nbtCompound.getType("Lore") == 9) {
                    NbtList nbtList = nbtCompound.getList("Lore", 8);
                    for(j = 0; j < nbtList.size(); ++j) {
                        String string = nbtList.getString(j);
                        try {
                            MutableText mutableText2 = Text.Serializer.fromJson(string);
                            if (mutableText2 != null) {
                                list.add(Texts.setStyleIfAbsent(mutableText2, Style.EMPTY.withColor(Formatting.DARK_PURPLE).withItalic(true)));
                            }
                        } catch (Exception var19) {
                            nbtCompound.remove("Lore");
                        }
                    }
                }
            }
        }
        return list;
    }

    public static void pickupItemAtSlot(int slot) {
        if (MinecraftClient.getInstance().player.currentScreenHandler instanceof GenericContainerScreenHandler screenHandler) {
            Int2ObjectArrayMap<ItemStack> stack = new Int2ObjectArrayMap<>();
            stack.put(slot, screenHandler.getSlot(slot).getStack());
            boolean hasShiftDown = InputUtil.isKeyPressed(MinecraftClient.getInstance().getWindow().getHandle(), 340) || InputUtil.isKeyPressed(MinecraftClient.getInstance().getWindow().getHandle(), 344);
            if (hasShiftDown) {
                Objects.requireNonNull(MinecraftClient.getInstance().getNetworkHandler()).sendPacket(new ClickSlotC2SPacket(screenHandler.syncId, 0, slot, 0, SlotActionType.QUICK_MOVE, screenHandler.getSlot(0).getStack(), stack));
            } else {
                Objects.requireNonNull(MinecraftClient.getInstance().getNetworkHandler()).sendPacket(new ClickSlotC2SPacket(screenHandler.syncId, 0, slot, 0, SlotActionType.PICKUP, screenHandler.getSlot(0).getStack(), stack));
            }
        }
    }
}
