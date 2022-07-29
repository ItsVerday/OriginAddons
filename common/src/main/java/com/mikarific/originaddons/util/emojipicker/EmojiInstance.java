package com.mikarific.originaddons.util.emojipicker;

public class EmojiInstance {
    private EmojiInfo info;
    private boolean unlocked;

    public EmojiInstance(EmojiInfo info) {
        this.info = info;
        this.unlocked = false;
    }

    public EmojiInfo getInfo() {
        return info;
    }

    public boolean isUnlocked() {
        return unlocked;
    }

    public void setUnlocked(boolean unlocked) {
        this.unlocked = unlocked;
    }
}