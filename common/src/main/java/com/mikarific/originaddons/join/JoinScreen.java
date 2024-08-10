package com.mikarific.originaddons.join;

import com.google.gson.JsonObject;
import com.mikarific.originaddons.OriginAddons;
import com.mikarific.originaddons.util.blockpicker.BlockPicker;
import com.mikarific.originaddons.util.emojipicker.EmojiPicker;
import net.minecraft.client.MinecraftClient;
import net.minecraft.client.gui.DrawContext;
import net.minecraft.client.gui.screen.Screen;
import net.minecraft.client.gui.screen.multiplayer.ConnectScreen;
import net.minecraft.client.gui.screen.multiplayer.MultiplayerScreen;
import net.minecraft.client.network.ServerAddress;
import net.minecraft.client.network.ServerInfo;
import net.minecraft.text.Text;
import net.minecraft.util.JsonHelper;

import java.io.*;
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
        super(Text.translatable("originaddons.join.title"));
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

        this.status = Text.translatable("originaddons.join.emoji");
        //update(new File(originAddonsDirectory, "emoji.json"), "https://api.originaddons.com/emoji.json?v=" + Instant.now().toEpochMilli(), info.get("emojiVersion").getAsInt(), EmojiPicker::unload);
        this.status = Text.translatable("originaddons.join.blockpick");
        //update(new File(originAddonsDirectory, "blockpicker.json"), "https://api.originaddons.com/blockpicker.json?v=" + Instant.now().toEpochMilli(), info.get("blockpickVersion").getAsInt(), BlockPicker::clear);
        assert this.client != null;
        ConnectScreen.connect(this.parent, this.client, ServerAddress.parse(this.entry.address), this.entry, false, null);
    }

    private void update(File file, String url, int latestVersion, Runnable action) {
        OriginAddons.LOGGER.info("Attempting to update " + file.getName() + " to version " + latestVersion + "...");
        try {
            InputStream inputStream = new URL(url).openStream();
            if (!file.exists()) {
                Files.copy(inputStream, file.toPath(), StandardCopyOption.REPLACE_EXISTING);
                OriginAddons.LOGGER.info("Created file " + file.getName() + "!");
                action.run();
            } else {
                Scanner scanner = new Scanner(file, StandardCharsets.UTF_8);
                StringBuilder string = new StringBuilder();
                while (scanner.hasNextLine()) {
                    string.append(scanner.nextLine());
                }

                JsonObject json = JsonHelper.deserialize(string.toString()).getAsJsonObject();
                scanner.close();

                OriginAddons.LOGGER.info("Current version for file " + file.getName() + " is " + json.get("version").getAsInt() + ".");
                if (latestVersion > json.get("version").getAsInt()) {
                    Files.copy(inputStream, file.toPath(), StandardCopyOption.REPLACE_EXISTING);
                    OriginAddons.LOGGER.info("Updated file " + file.getName() + "!");
                    action.run();
                } else {
                    OriginAddons.LOGGER.info("File " + file.getName() + " is up to date.");
                }
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public void render(DrawContext context, int mouseX, int mouseY, float delta) {
        this.renderBackground(context, mouseX, mouseY, delta);
        context.drawCenteredTextWithShadow(this.textRenderer, this.status, this.width / 2, this.height / 2 - 50, 16777215);
        super.render(context, mouseX, mouseY, delta);
    }

    public boolean shouldCloseOnEsc() {
        return false;
    }
}
