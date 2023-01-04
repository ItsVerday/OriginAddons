package com.mikarific.originaddons.util.custommenus;

import com.google.common.collect.Lists;
import com.mikarific.originaddons.OriginAddons;
import it.unimi.dsi.fastutil.ints.Int2ObjectArrayMap;
import net.minecraft.client.MinecraftClient;
import net.minecraft.client.gui.screen.Screen;
import net.minecraft.client.util.InputUtil;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NbtCompound;
import net.minecraft.nbt.NbtList;
import net.minecraft.network.packet.c2s.play.ClickSlotC2SPacket;
import net.minecraft.screen.GenericContainerScreenHandler;
import net.minecraft.screen.slot.SlotActionType;
import net.minecraft.text.*;
import net.minecraft.util.Formatting;

import java.util.*;

public class CustomMenus {

    private static boolean teleportingHome = false;
    private static final String BADGES = "솷";
    private static final String GESTURES_FAVORITES = "쉂";
    private static final String GESTURES_ALL = "쉃";
    private static final String NAVIGATOR = "섥";
    private static final String ORBIT = "숰";
    private static final String PAINTING = "섈";
    private static final String PROFILE = "쇉";
    private static final String PROFILE_STAFF = "쉋";
    private static final String PROFILE_CHAT = "쉎";
    private static final String PROFILE_BEHAVIOR = "쉏";
    private static final String PROFILE_MODS = "쉐";
    private static final String REALMS = "섩";
    private static final String REALMS_ROLE_SELECT = "솮";
    private static final String REALMS_SETTINGS = "솭";

    private static final List<String> customMenus = List.of(
            BADGES,
            GESTURES_FAVORITES,
            GESTURES_ALL,
            NAVIGATOR,
            ORBIT,
            PAINTING,
            PROFILE,
            PROFILE_STAFF,
            PROFILE_CHAT,
            PROFILE_BEHAVIOR,
            PROFILE_MODS,
            REALMS,
            REALMS_ROLE_SELECT,
            REALMS_SETTINGS
    );

    public static boolean isCustomScreen(Screen screen) {
        if (screen == null) return false;
        return customMenus.stream().anyMatch(screen.getTitle().getString()::contains);
    }

    public static boolean isEnabled(Screen screen) {
        if (!isCustomScreen(screen)) return false;
        Optional<String> title = customMenus.stream().filter(screen.getTitle().getString()::contains).findAny();
        if (title.isEmpty()) return false;

        Map<String, Boolean> enabledMenus = new HashMap<>() {{
            put(BADGES, OriginAddons.getConfig().customBadgeMenu);
            put(GESTURES_FAVORITES, OriginAddons.getConfig().customGesturesMenu);
            put(GESTURES_ALL, OriginAddons.getConfig().customGesturesMenu);
            put(NAVIGATOR, OriginAddons.getConfig().customNavigatorMenu);
            put(ORBIT, OriginAddons.getConfig().customOrbitMenu);
            put(PAINTING, OriginAddons.getConfig().customPaintingMenu);
            put(PROFILE, OriginAddons.getConfig().customProfileMenu);
            put(PROFILE_STAFF, OriginAddons.getConfig().customProfileMenu);
            put(PROFILE_CHAT, OriginAddons.getConfig().customProfileMenu);
            put(PROFILE_BEHAVIOR, OriginAddons.getConfig().customProfileMenu);
            put(PROFILE_MODS, OriginAddons.getConfig().customProfileMenu);
            put(REALMS, OriginAddons.getConfig().customRealmsMenu);
            put(REALMS_ROLE_SELECT, OriginAddons.getConfig().customRealmsMenu);
            put(REALMS_SETTINGS, OriginAddons.getConfig().customRealmsMenu);
        }};

        return OriginAddons.onOriginRealms() && OriginAddons.getConfig().customMenus && enabledMenus.get(title.get());
    }

    public static boolean isBadges(Screen screen) {
        return screen.getTitle().getString().contains(BADGES);
    }

    public static boolean isGesturesFavorites(Screen screen) {
        return screen.getTitle().getString().contains(GESTURES_FAVORITES);
    }

    public static boolean isGesturesAll(Screen screen) {
        return screen.getTitle().getString().contains(GESTURES_ALL);
    }

    public static boolean isNavigator(Screen screen) {
        return screen.getTitle().getString().contains(NAVIGATOR);
    }

    public static boolean isOrbit(Screen screen) {
        return screen.getTitle().getString().contains(ORBIT);
    }

    public static boolean isPainting(Screen screen) {
        return screen.getTitle().getString().contains(PAINTING);
    }

    public static boolean isProfile(Screen screen) {
        return screen.getTitle().getString().contains(PROFILE);
    }

    public static boolean isProfileStaff(Screen screen) {
        return screen.getTitle().getString().contains(PROFILE_STAFF);
    }

    public static boolean isProfilePunish(Screen screen) {
        return screen.getTitle().getString().contains(PROFILE_CHAT) || screen.getTitle().getString().contains(PROFILE_BEHAVIOR) || screen.getTitle().getString().contains(PROFILE_MODS);
    }

    public static boolean isProfilePunishChat(Screen screen) {
        return screen.getTitle().getString().contains(PROFILE_CHAT);
    }

    public static boolean isProfilePunishBehavior(Screen screen) {
        return screen.getTitle().getString().contains(PROFILE_BEHAVIOR);
    }

    public static boolean isProfilePunishMods(Screen screen) {
        return screen.getTitle().getString().contains(PROFILE_MODS);
    }

    public static boolean isRealms(Screen screen) {
        return screen.getTitle().getString().contains(REALMS);
    }

    public static boolean isRealmsRoleSelect(Screen screen) {
        return screen.getTitle().getString().contains(REALMS_ROLE_SELECT);
    }

    public static boolean isRealmsSettings(Screen screen) {
        return screen.getTitle().getString().contains(REALMS_SETTINGS);
    }

    public static void setTeleportingHome(boolean teleportingHome) {
        CustomMenus.teleportingHome = teleportingHome;
    }

    public static boolean getTeleportingHome() {
        return teleportingHome;
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
}
