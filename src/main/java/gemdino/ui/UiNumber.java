package gemdino.ui;

import gemdino.app.App;
import gemdino.utils.Utils;

import java.awt.Graphics;

import gemdino.screen.Screen;

import java.awt.image.BufferedImage;

public class UiNumber extends UiComponent {
    BufferedImage[] imagesNumbers;
    private int number;

    public UiNumber(App app, final Screen screen, final double x, final double y, final int height, final BufferedImage[] imagesNumbers) {
        super(app, screen, x, y, 0, height);
        this.imagesNumbers = imagesNumbers;
    }

    @Override
    public void render(final Graphics g) {
        final int[] digitArray = Utils.integerToArray(this.number);
        for (int length = digitArray.length, i = 0; i < length; ++i) {
            g.drawImage(this.imagesNumbers[digitArray[i]], (int) (this.x + i * 64 / 2), (int) this.y, 64, 64, null);
        }
    }

    public void setNumber(final int number) {
        this.number = number;
    }
}
