package com.mikarific.originaddons.join;

import com.google.gson.JsonObject;
import com.mikarific.originaddons.util.emojipicker.EmojiPicker;
import net.minecraft.client.MinecraftClient;
import net.minecraft.client.gui.screen.ConnectScreen;
import net.minecraft.client.gui.screen.Screen;
import net.minecraft.client.gui.screen.multiplayer.MultiplayerScreen;
import net.minecraft.client.network.ServerAddress;
import net.minecraft.client.network.ServerInfo;
import net.minecraft.client.util.NarratorManager;
import net.minecraft.client.util.math.MatrixStack;
import net.minecraft.text.Text;
import net.minecraft.text.TranslatableText;
import net.minecraft.util.JsonHelper;
import net.minecraft.util.Util;
import org.apache.commons.io.FileUtils;
import org.apache.commons.io.input.BOMInputStream;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

import java.io.*;
import java.net.MalformedURLException;
import java.net.URL;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.StandardCopyOption;
import java.time.Instant;
import java.util.Scanner;

public class JoinScreen extends Screen {
    private final MultiplayerScreen parent;
    private final ServerInfo entry;
    private final JsonObject info;
    private Text status;

    public JoinScreen(MultiplayerScreen parent, ServerInfo entry, JsonObject info) {
        super(new TranslatableText("originaddons.join.title"));
        this.parent = parent;
        this.entry = entry;
        this.info = info;
    }

    protected void init() {
        super.init();
        update();
    }

    public void update() {
        File runDirectory = MinecraftClient.getInstance().runDirectory;
        File originAddonsDirectory = new File(runDirectory, "originaddons");
        if (!originAddonsDirectory.exists()) {
            //noinspection ResultOfMethodCallIgnored
            originAddonsDirectory.mkdirs();
        }
        this.status = new TranslatableText("originaddons.join.emoji");
        update(new File(originAddonsDirectory, "emoji.json"), "https://api.originaddons.com/emoji.json?v=" + Instant.now().toEpochMilli(), info.get("emojiVersion").getAsInt(), EmojiPicker::unload);
        this.status = new TranslatableText("originaddons.join.blockpick");
        update(new File(originAddonsDirectory, "blockpicker.json"), "https://api.originaddons.com/blockpicker.json?v=" + Instant.now().toEpochMilli(), info.get("blockpickVersion").getAsInt(), EmojiPicker::unload);
        assert this.client != null;
        ConnectScreen.connect(this.parent, this.client, ServerAddress.parse(this.entry.address), this.entry);
    }

    private void update(File file, String url, int latestVersion, Runnable action) {
        try {
            InputStream inputStream = new URL(url).openStream();
            if (!file.exists()) {
                Files.copy(inputStream, file.toPath(), StandardCopyOption.REPLACE_EXISTING);
                action.run();
            } else {
                Scanner scanner = new Scanner(file, StandardCharsets.UTF_8);
                StringBuilder string = new StringBuilder();
                while (scanner.hasNextLine()) {
                    string.append(scanner.nextLine());
                }
                System.out.println(string);
                JsonObject json = JsonHelper.deserialize(string.toString()).getAsJsonObject();
                if (latestVersion > json.get("version").getAsInt()) {
                    Files.copy(inputStream, file.toPath(), StandardCopyOption.REPLACE_EXISTING);
                    action.run();
                }
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public void render(MatrixStack matrices, int mouseX, int mouseY, float delta) {
        this.renderBackground(matrices);
        drawCenteredText(matrices, this.textRenderer, this.status, this.width / 2, this.height / 2 - 50, 16777215);
        super.render(matrices, mouseX, mouseY, delta);
    }

    public boolean shouldCloseOnEsc() {
        return false;
    }
}
