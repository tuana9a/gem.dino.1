package gemdino.animation;

import java.awt.Graphics2D;

import gemdino.graphic.Assets;

import java.awt.image.BufferedImage;

public abstract class AnimationSingle extends Animation
{
    protected BufferedImage[][] oneStateOneImages;
    
    public AnimationSingle(final BufferedImage[][] oneStateOneImages) {
        if (oneStateOneImages == null) {
            this.oneStateOneImages = new BufferedImage[][] { { Assets.nullImage } };
        }
        else {
            this.oneStateOneImages = oneStateOneImages;
            this.maxIndex = oneStateOneImages[0].length;
            this.animationKind = oneStateOneImages.length;
        }
    }
    
    public AnimationSingle(final BufferedImage[][] oneStateOneImages, final int updateSpeed) {
        super(updateSpeed);
        if (oneStateOneImages == null) {
            this.oneStateOneImages = new BufferedImage[][] { { Assets.nullImage } };
            this.maxIndex = 0;
            this.animationKind = 0;
        }
        else {
            this.oneStateOneImages = oneStateOneImages;
            this.maxIndex = oneStateOneImages[0].length;
            this.animationKind = oneStateOneImages.length;
        }
    }
    
    public abstract BufferedImage getImage(final int p0);
    
    public void render(final Graphics2D g2d, final int which, final double xCam, final double yCam, final int width, final int height) {
        g2d.drawImage(this.getImage(which), (int)xCam, (int)yCam, width, height, null);
    }
}
