package org.syringemc.client.text;

import net.minecraft.text.Text;
import org.syringemc.client.util.Position;

public class ScreenText {

    private Text text;
    private Position position;
    private float offsetX;
    private float offsetY;
    private float scale;
    private long fadein;
    private boolean shadow;
    private boolean discarded;
    private final long fadeInTotalTicks;
    private long fadeout;
    private long fadeOutTotalTicks;

    public ScreenText(Text text, Position position, float offsetX, float offsetY, float scale, long fadein, boolean shadow) {
        this.text = text;
        this.position = position;
        this.offsetX = offsetX;
        this.offsetY = offsetY;
        this.scale = scale;
        this.fadein = fadein;
        this.shadow = shadow;
        this.fadeInTotalTicks = fadein;
    }

    public Text getText() {
        return text;
    }

    public void setText(Text text) {
        this.text = text;
    }

    public Position getPosition() {
        return position;
    }

    public void setPosition(Position position) {
        this.position = position;
    }

    public float getOffsetX() {
        return offsetX;
    }

    public void setOffsetX(float offsetX) {
        this.offsetX = offsetX;
    }

    public float getOffsetY() {
        return offsetY;
    }

    public void setOffsetY(float offsetY) {
        this.offsetY = offsetY;
    }

    public float getScale() {
        return scale;
    }

    public void setScale(float scale) {
        this.scale = scale;
    }

    public long getFadein() {
        return fadein;
    }

    public void setFadein(long fadein) {
        this.fadein = fadein;
    }

    public boolean isShadow() {
        return shadow;
    }

    public void setShadow(boolean shadow) {
        this.shadow = shadow;
    }

    public boolean isDiscarded() {
        return discarded;
    }

    public void setDiscarded(boolean discarded) {
        this.discarded = discarded;
    }

    public long getFadeout() {
        return fadeout;
    }

    public void setFadeout(long fadeout) {
        this.fadeout = fadeout;
    }

    public long getFadeOutTotalTicks() {
        return fadeOutTotalTicks;
    }

    public void setFadeOutTotalTicks(long fadeOutTotalTicks) {
        this.fadeOutTotalTicks = fadeOutTotalTicks;
    }

    public long getFadeInTotalTicks() {
        return fadeInTotalTicks;
    }
}
