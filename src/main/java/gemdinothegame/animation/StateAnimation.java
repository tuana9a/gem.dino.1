package gemdinothegame.animation;

import gemdinothegame.graphic.Assets;

import java.awt.Graphics2D;
import java.awt.image.BufferedImage;

public class StateAnimation extends AnimationMultiple
{
    public static final int ONE = 1;
    public static final int THREE_STATE_ANIMAL = 3;
    public static final int STATIC_CONST = 0;
    public double[][] rel;
    public double[][] rotateRel;
    public double width;
    public double height;
    
    public StateAnimation(final BufferedImage[][][] images, final double[][] rel, final double width, final double height) {
        super(images);
        this.rel = rel;
        this.width = width;
        this.height = height;
        this.animationKind = images[0].length;
        this.maxIndex = images[0][0].length;
    }
    
    public StateAnimation(final BufferedImage[][][] images, final double[][] rel, final double width, final double height, final int updateSpeed) {
        super(images, updateSpeed);
        this.rel = rel;
        this.width = width;
        this.height = height;
        this.animationKind = images[0].length;
        this.maxIndex = images[0][0].length;
    }
    
    public StateAnimation(final BufferedImage[][][] images, final double[][] rel, final double[][] rotateRel, final double width, final double height) {
        super(images);
        this.rel = rel;
        this.rotateRel = rotateRel;
        this.width = width;
        this.height = height;
        this.animationKind = images[0].length;
        this.maxIndex = images[0][0].length;
    }
    
    public StateAnimation(final BufferedImage[][][] images, final double[][] rel, final double[][] rotateRel, final double width, final double height, final int updateSpeed) {
        super(images, updateSpeed);
        this.rel = rel;
        this.rotateRel = rotateRel;
        this.width = width;
        this.height = height;
        this.animationKind = images[0].length;
        this.maxIndex = images[0][0].length;
    }
    
    public void render(final Graphics2D g2d, final int moveDirect, final double xCam, final double yCam, final double xRotateCam, final double yRotateCam) {
        final BufferedImage[][] images = this.getImages(moveDirect);
        final int length = images.length;
        if (this.animationKind == 1) {
            for (final BufferedImage[] image : images) {
                g2d.drawImage(image[this.currentIndex], (int)(xCam + this.rel[moveDirect][0]), (int)(yCam + this.rel[moveDirect][1]), (int)this.width, (int)this.height, null);
            }
        }
        else {
            for (int i = 0; i < length; ++i) {
                final Graphics2D g2d2 = (Graphics2D)g2d.create();
                g2d2.rotate(this.rotateRel[moveDirect][i], xRotateCam + this.rel[moveDirect][0], yRotateCam + this.rel[moveDirect][1]);
                g2d2.drawImage(images[i][this.currentIndex], (int)xCam, (int)yCam, (int)this.width, (int)this.height, null);
            }
        }
    }
    
    @Override
    public BufferedImage[][] getImages(final int state) {
        final BufferedImage[][] returnImage = this.oneStateMultipleImages[state];
        if (returnImage == null) {
            return new BufferedImage[][] { { Assets.nullImage } };
        }
        return returnImage;
    }
}
