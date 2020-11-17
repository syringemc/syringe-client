package org.syringemc.client;

import net.fabricmc.fabric.api.network.ClientSidePacketRegistry;
import net.minecraft.text.Text;
import net.minecraft.util.Identifier;
import org.syringemc.client.text.ScreenText;
import org.syringemc.client.text.ScreenTextRenderer;
import org.syringemc.client.util.Position;

public class SyringePacketListener {

    private static final String NAMESPACE = "syringe";
    private SyringePacketListener() {
    }

    public static void register() {
        ClientSidePacketRegistry.INSTANCE.register(id("display_text"), (packetContext, packetByteBuf) -> {
            String key = packetByteBuf.readString();
            Text text = packetByteBuf.readText();
            Position position = Position.valueOf(packetByteBuf.readString());
            float offsetX = packetByteBuf.readFloat();
            float offsetY = packetByteBuf.readFloat();
            float scale = packetByteBuf.readFloat();
            long fadeIn = packetByteBuf.readLong();
            boolean shadow = packetByteBuf.readBoolean();
            ScreenText screenText = new ScreenText(text, position, offsetX, offsetY, scale, fadeIn, shadow);
            ScreenTextRenderer.displayText(key, screenText);
        });

        ClientSidePacketRegistry.INSTANCE.register(id("discard_text"), (packetContext, packetByteBuf) -> {
            String key = packetByteBuf.readString();
            long fadeOut = packetByteBuf.readLong();
            ScreenTextRenderer.discardText(key, fadeOut);
        });
    }

    private static Identifier id(String path) {
        return new Identifier(NAMESPACE, path);
    }
}
