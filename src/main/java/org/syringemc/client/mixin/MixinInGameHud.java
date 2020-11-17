package org.syringemc.client.mixin;

import net.minecraft.client.gui.hud.InGameHud;
import net.minecraft.client.util.math.MatrixStack;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;
import org.syringemc.client.text.ScreenText;
import org.syringemc.client.text.ScreenTextRenderer;

@Mixin(InGameHud.class)
public class MixinInGameHud {

    @Inject(
            method = "render",
            at = @At(
                    value = "INVOKE",
                    target = "Lnet/minecraft/client/world/ClientWorld;getScoreboard()Lnet/minecraft/scoreboard/Scoreboard;"
            )
    )
    private void render(MatrixStack matrixStack, float tickDelta, CallbackInfo callbackInfo) {
        ScreenTextRenderer.render(matrixStack);
    }

    @Inject(
            method = "tick",
            at = @At("TAIL")
    )
    private void tick(CallbackInfo ci) {
        ScreenTextRenderer.getScreenTexts().stream()
                .map(ScreenTextRenderer.Entry::getScreenText)
                .filter(text -> 0 < text.getFadein())
                .forEach(text -> text.setFadein(text.getFadein() - 1));
        ScreenTextRenderer.getScreenTexts().stream()
                .map(ScreenTextRenderer.Entry::getScreenText)
                .filter(ScreenText::isDiscarded)
                .filter(text -> 0 < text.getFadeout())
                .forEach(text -> text.setFadeout(text.getFadeout() - 1));
    }
}
