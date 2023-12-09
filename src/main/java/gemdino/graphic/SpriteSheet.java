package gemdino.graphic;

import java.awt.image.BufferedImage;

public class SpriteSheet {
    private final BufferedImage bufferedImage;

    public SpriteSheet(final BufferedImage bufferedImage) {
        this.bufferedImage = bufferedImage;
    }

    public BufferedImage crop(final int x, final int y, final int width, final int height) {
        return this.bufferedImage.getSubimage(x, y, width, height);
    }
}
