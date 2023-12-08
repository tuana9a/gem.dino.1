package com.tuana9a.gemdino.ui;

import com.tuana9a.gemdino.utils.Utils;

import java.awt.Graphics;
import com.tuana9a.gemdino.screen.Screen;
import java.awt.image.BufferedImage;

public class UiNumber extends UiComponent
{
    BufferedImage[] imagesNumbers;
    private int number;
    
    public UiNumber(final Screen state, final double x, final double y, final int height, final BufferedImage[] imagesNumbers) {
        super(state, x, y, 0, height);
        this.imagesNumbers = imagesNumbers;
    }
    
    @Override
    public void render(final Graphics g) {
        final int[] digitArray = Utils.integerToArray(this.number);
        for (int length = digitArray.length, i = 0; i < length; ++i) {
            g.drawImage(this.imagesNumbers[digitArray[i]], (int)(this.x + i * 64 / 2), (int)this.y, 64, 64, null);
        }
    }
    
    public void setNumber(final int number) {
        this.number = number;
    }
}
