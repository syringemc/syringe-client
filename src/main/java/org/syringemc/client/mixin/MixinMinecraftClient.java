package org.syringemc.client.mixin;

import net.minecraft.client.MinecraftClient;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;
import org.syringemc.client.text.ScreenTextRenderer;

@Mixin(MinecraftClient.class)
public class MixinMinecraftClient {

    @Inject(
            method = {"disconnect()V", "disconnect(Lnet/minecraft/client/gui/screen/Screen;)V"},
            at = @At("HEAD")
    )
    private void onDisconnect(CallbackInfo ci) {
        ScreenTextRenderer.discardAll();
    }
}
