package com.mikarific.originaddons.join;

import com.google.gson.JsonArray;
import com.google.gson.JsonObject;
import com.mikarific.originaddons.OriginAddons;
import net.minecraft.client.MinecraftClient;
import net.minecraft.client.font.MultilineText;
import net.minecraft.client.gui.screen.*;
import net.minecraft.client.gui.screen.multiplayer.MultiplayerScreen;
import net.minecraft.client.gui.widget.ButtonWidget;
import net.minecraft.client.network.ServerAddress;
import net.minecraft.client.network.ServerInfo;
import net.minecraft.client.util.math.MatrixStack;
import net.minecraft.text.LiteralText;
import net.minecraft.text.Style;
import net.minecraft.text.TranslatableText;
import net.minecraft.util.JsonHelper;
import net.minecraft.util.Util;

import java.io.IOException;
import java.io.InputStreamReader;
import java.net.MalformedURLException;
import java.net.URL;
import java.time.Instant;
import java.util.ArrayList;
import java.util.List;

public class UpdateScreen extends Screen {
    private final Screen parent;
    private final ServerInfo entry;
    private final JsonObject info;
    private final String latestVersion;
    private final List<MultilineText> changelog = new ArrayList<>();
    private int changelogLineCount = 0;
    private final String download;

    public UpdateScreen(Screen parent, ServerInfo entry, JsonObject info, String latestVersion) throws IOException {
        super(new TranslatableText("originaddons.update.title"));
        this.parent = parent;
        this.entry = entry;
        this.info = info;
        this.latestVersion = latestVersion;
        URL versionsUrl = new URL("https://api.originaddons.com/versions.json?v=" + Instant.now().toEpochMilli());
        InputStreamReader versionsReader = new InputStreamReader(versionsUrl.openStream());
        JsonObject versions = JsonHelper.deserialize(versionsReader).getAsJsonObject();
        if (versions.get(latestVersion) != null && versions.get(latestVersion).getAsJsonObject().get("changelog") != null) {
            versions.get(latestVersion).getAsJsonObject().get("changelog").getAsJsonArray().forEach(line -> {
                MultilineText multilineText = MultilineText.create(MinecraftClient.getInstance().textRenderer, new LiteralText(" - " + line), 300);
                this.changelog.add(multilineText);
                changelogLineCount += multilineText.count();
            });
        }
        this.download = versions.get(latestVersion) != null && versions.get(latestVersion).getAsJsonObject().get("download") != null ? versions.get(latestVersion).getAsJsonObject().get("download").getAsString() : "https://originaddons.com";
    }

    protected void init() {
        super.init();
        int height = 110 + changelogLineCount * 10;
        if (changelog.size() > 0) height += 20;
        this.addDrawableChild(new ButtonWidget(this.width / 2 - 150, height, 148, 20, new TranslatableText("originaddons.update.update"), (button) -> {
            Util.getOperatingSystem().open(download);
        }));
        this.addDrawableChild(new ButtonWidget(this.width / 2 + 2, height, 148, 20, new TranslatableText("originaddons.update.joinanyways"), (button) -> {
            assert this.client != null;
            this.client.setScreen(new JoinScreen((MultiplayerScreen) this.parent, entry, info));
        }));
        this.addDrawableChild(new ButtonWidget(this.width / 2 - 100, height + 24, 200, 20, new TranslatableText("originaddons.update.cancel"), (button) -> {
            assert this.client != null;
            this.client.setScreen(this.parent);
        }));
    }

    public void render(MatrixStack matrices, int mouseX, int mouseY, float delta) {
        this.renderBackground(matrices);
        drawCenteredText(matrices, this.textRenderer, this.title.copy().setStyle(Style.EMPTY.withBold(true)), this.width / 2, 70, 16777215);
        drawCenteredText(matrices, this.textRenderer, new TranslatableText("originaddons.update.message").getString().replaceAll("%currentVersion%", OriginAddons.VERSION).replaceAll("%latestVersion%", latestVersion), this.width / 2, 90, 16777215);
        if (changelog.size() > 0) drawCenteredText(matrices, this.textRenderer, new TranslatableText("originaddons.update.changelog").setStyle(Style.EMPTY.withBold(true)), this.width / 2, 110, 16777215);
        int y = 120;
        for(MultilineText line : changelog) {
            line.drawWithShadow(matrices, this.width / 2 - 150, y, 10, 16777215);
            y += line.count() * 10;
        }
        super.render(matrices, mouseX, mouseY, delta);
    }

    public boolean shouldCloseOnEsc() {
        return false;
    }
}
