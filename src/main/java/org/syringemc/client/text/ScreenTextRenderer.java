package org.syringemc.client.text;

import com.mojang.blaze3d.systems.RenderSystem;
import net.minecraft.client.MinecraftClient;
import net.minecraft.client.font.TextRenderer;
import net.minecraft.client.util.math.MatrixStack;
import net.minecraft.text.Text;
import net.minecraft.util.math.MathHelper;

import java.util.ArrayList;
import java.util.List;

public class ScreenTextRenderer {

    private static final List<Entry> texts = new ArrayList<>();
    private static final int MIN_ALPHA = 4;

    private ScreenTextRenderer() {
    }

    public synchronized static void displayText(String key, ScreenText text) {
        texts.add(new Entry(key, text));
    }

    public static void discardText(String key, long fadeOut) {
        texts.stream()
                .filter(entry -> entry.key.equals(key))
                .findFirst()
                .ifPresent(entry -> {
                    ScreenText screenText = entry.getScreenText();
                    screenText.setDiscarded(true);
                    screenText.setFadeout(fadeOut);
                    screenText.setFadeOutTotalTicks(fadeOut);
                });
    }

    public static void discardAll() {
        texts.clear();
    }

    public static List<Entry> getScreenTexts() {
        return texts;
    }

    @SuppressWarnings("deprecation")
    public static void render(MatrixStack matrixStack) {
        MinecraftClient client = MinecraftClient.getInstance();
        TextRenderer textRenderer = client.inGameHud.getFontRenderer();

        for (int i = 0; i < texts.size(); i++) {
            Entry entry = texts.get(i);
            ScreenText screenText = entry.screenText;
            Text text = screenText.getText();
            float scale = screenText.getScale();
            int scaledHeight = client.getWindow().getScaledHeight();
            int scaledWidth = client.getWindow().getScaledWidth();
            float baseOffsetX = 0;
            float baseOffsetY = 0;
            float textWidth = textRenderer.getWidth(text) * scale;
            float fontHeight = textRenderer.fontHeight * scale;
            switch (screenText.getPosition()) {
                case TOP:
                    baseOffsetX = (scaledWidth - textWidth) / 2f;
                    break;
                case BOTTOM:
                    baseOffsetX = (scaledWidth - textWidth) / 2f;
                    baseOffsetY = scaledHeight - fontHeight;
                    break;
                case LEFT:
                    baseOffsetY = (scaledHeight - fontHeight) / 2f;
                    break;
                case RIGHT:
                    baseOffsetX = scaledWidth - textWidth;
                    baseOffsetY = (scaledHeight - fontHeight) / 2f;
                    break;
                case TOP_LEFT:
                    break;
                case TOP_RIGHT:
                    baseOffsetX = scaledWidth - textWidth;
                    break;
                case BOTTOM_LEFT:
                    baseOffsetY = scaledHeight - fontHeight;
                    break;
                case BOTTOM_RIGHT:
                    baseOffsetX = scaledWidth - textWidth;
                    baseOffsetY = scaledHeight - fontHeight;
                    break;
                case CENTER:
                    baseOffsetX = (scaledWidth - textWidth) / 2f;
                    baseOffsetY = (scaledHeight - fontHeight) / 2f;
                    break;
            }

            long alpha;
            if (screenText.isDiscarded()) {
                double a = 255.0 / screenText.getFadeOutTotalTicks();
                long fadeout = screenText.getFadeout();
                alpha = (long) (a * fadeout);
                if (alpha <= 0) {
                    texts.removeIf(e -> e.key.equals(entry.key));
                }
            } else {
                double a = 255.0 / screenText.getFadeInTotalTicks();
                long fadein = screenText.getFadein();
                alpha = (long) (255 - a * fadein);
            }
            alpha = MathHelper.clamp(alpha, MIN_ALPHA, 255) << 24;

            RenderSystem.pushMatrix();
            RenderSystem.scalef(scale, scale, scale);
            if (screenText.isShadow()) {
                textRenderer.drawWithShadow(matrixStack, text, (baseOffsetX + screenText.getOffsetX()) / scale, (baseOffsetY + screenText.getOffsetY()) / scale, (int) (0xffffff | alpha));
            } else {
                textRenderer.draw(matrixStack, text, (baseOffsetX + screenText.getOffsetX()) / scale, (baseOffsetY + screenText.getOffsetY()) / scale, (int) (0xffffff | alpha));
            }
            RenderSystem.popMatrix();
        }
    }

    public static class Entry {
        private final String key;
        private final ScreenText screenText;

        public Entry(String key, ScreenText screenText) {
            this.key = key;
            this.screenText = screenText;
        }

        public String getKey() {
            return key;
        }

        public ScreenText getScreenText() {
            return screenText;
        }
    }
}
