package com.modforge.writethecodeforaminecraftmodthat;

import net.fabricmc.api.ClientModInitializer;
import net.fabricmc.fabric.api.client.event.lifecycle.v1.ClientTickEvents;
import net.fabricmc.fabric.api.client.keybinding.v1.KeyBindingHelper;
import net.minecraft.client.MinecraftClient;
import net.minecraft.client.gui.DrawContext;
import net.minecraft.client.gui.screen.Screen;
import net.minecraft.client.gui.widget.ButtonWidget;
import net.minecraft.client.option.KeyBinding;
import net.minecraft.client.util.InputUtil;
import net.minecraft.text.Text;
import org.lwjgl.glfw.GLFW;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class WriteTheCodeForAMinecraftModThatMod implements ClientModInitializer {
    private static final Logger LOGGER = LoggerFactory.getLogger("write-the-code-for-a-minecraft-mod-that--mp4yr3u0");

    public static volatile boolean XRAY_ENABLED = false;
    public static KeyBinding OPEN_MENU_KEY;

    @Override
    public void onInitializeClient() {
        try {
            LOGGER.info("Secret Menu XRAY client mod loaded.");

            OPEN_MENU_KEY = KeyBindingHelper.registerKeyBinding(new KeyBinding(
                "key.write-the-code-for-a-minecraft-mod-that--mp4yr3u0.open_menu",
                InputUtil.Type.KEYSYM,
                GLFW.GLFW_KEY_KP_MULTIPLY,
                "category.write-the-code-for-a-minecraft-mod-that--mp4yr3u0.general"
            ));

            ClientTickEvents.END_CLIENT_TICK.register(client -> {
                try {
                    if (client == null || client.player == null) return;
                    while (OPEN_MENU_KEY.wasPressed()) {
                        client.setScreen(new WriteTheCodeForAMinecraftModThatModScreen());
                    }
                } catch (Throwable t) {
                    LOGGER.error("Client tick handling failed", t);
                }
            });
        } catch (Throwable t) {
            LOGGER.error("Failed to initialize client mod", t);
        }
    }
}

class WriteTheCodeForAMinecraftModThatModScreen extends Screen {
    protected WriteTheCodeForAMinecraftModThatModScreen() {
        super(Text.literal("Secret Menu"));
    }

    @Override
    protected void init() {
        try {
            this.addDrawableChild(ButtonWidget.builder(Text.literal("XRAY"), btn -> {
                try {
                    WriteTheCodeForAMinecraftModThatMod.XRAY_ENABLED = !WriteTheCodeForAMinecraftModThatMod.XRAY_ENABLED;
                    btn.setMessage(Text.literal(WriteTheCodeForAMinecraftModThatMod.XRAY_ENABLED ? "XRAY: ON" : "XRAY"));
                    MinecraftClient mc = MinecraftClient.getInstance();
                    if (mc.worldRenderer != null) mc.worldRenderer.reload();
                } catch (Throwable t) {
                    LoggerFactory.getLogger("write-the-code-for-a-minecraft-mod-that--mp4yr3u0").error("Failed to toggle XRAY", t);
                }
            }).dimensions(8, 8, 60, 20).build());
        } catch (Throwable t) {
            LoggerFactory.getLogger("write-the-code-for-a-minecraft-mod-that--mp4yr3u0").error("Failed to init Secret Menu screen", t);
        }
    }

    @Override
    public void render(DrawContext ctx, int mouseX, int mouseY, float delta) {
        this.renderBackground(ctx, mouseX, mouseY, delta);
        super.render(ctx, mouseX, mouseY, delta);
        ctx.drawTextWithShadow(this.textRenderer, this.title, this.width / 2 - this.textRenderer.getWidth(this.title) / 2, 30, 0xFFFFFF);
    }

    @Override
    public boolean shouldPause() {
        return false;
    }
}
