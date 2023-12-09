package gemdino.animation;

import gemdino.graphic.Assets;

import java.awt.image.BufferedImage;

public abstract class AnimationMultiple extends Animation
{
    protected BufferedImage[][][] oneStateMultipleImages;
    
    public AnimationMultiple(final BufferedImage[][][] oneStateMultipleImages) {
        if (oneStateMultipleImages == null) {
            this.oneStateMultipleImages = new BufferedImage[][][] { { { Assets.nullImage } } };
        }
        else {
            this.oneStateMultipleImages = oneStateMultipleImages;
            this.maxIndex = oneStateMultipleImages[0].length;
            this.animationKind = oneStateMultipleImages.length;
        }
    }
    
    public AnimationMultiple(final BufferedImage[][][] oneStateMultipleImages, final int updateSpeed) {
        super(updateSpeed);
        if (oneStateMultipleImages == null) {
            this.oneStateMultipleImages = new BufferedImage[][][] { { { Assets.nullImage } } };
            this.maxIndex = 0;
            this.animationKind = 0;
        }
        else {
            this.oneStateMultipleImages = oneStateMultipleImages;
            this.maxIndex = oneStateMultipleImages[0].length;
            this.animationKind = oneStateMultipleImages.length;
        }
    }
    
    public abstract BufferedImage[][] getImages(final int p0);
}
