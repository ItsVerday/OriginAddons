package com.mikarific.originaddons.util.emojipicker;

public class EmojiInstance {
    private EmojiInfo info;
    private boolean unlocked;
    private boolean hidden;

    public EmojiInstance(EmojiInfo info) {
        this.info = info;
        this.unlocked = false;
        this.hidden = false;
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

    public boolean isHidden() {
        return hidden;
    }

    public void setHidden(boolean hidden) {
        this.hidden = hidden;
    }
}