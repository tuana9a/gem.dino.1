// 
// Decompiled by Procyon v0.5.36
// 

package com.tuana9a.config;

public interface ConfigWeapon
{
    public static final int NUMBER = 5;
    public static final int STATE_NUMBER = 5;
    public static final int[] shootWeapons = { 0, 2, 4 };
    public static final int[] shotGunWeapons = { 0 };
    public static final int[] bowWeapons = { 2 };
    public static final int[] swordWeapons = { 3 };
    public static final int[] spearWeapons = { 1 };
    public static final int[] gatlingWeapons = { 4 };
    public static final int[] damages = { 2, 3, 2, 2, 1 };
    public static final double[] attackSpeeds = { 2.0, 2.0, 1.0, 3.0, 8.0 };
    public static final double[] accuracies = { 0.3141592653589793, 0.0, 0.15707963267948966, 0.0, 0.15707963267948966 };
    public static final double[] rotateSpeeds = { 0.04487989505128276, 0.05235987755982988, 0.04487989505128276, 0.05235987755982988, 0.04487989505128276 };
    public static final double[] reloadRatios = { 0.8, 0.8, 0.8, 0.8, 0.8 };
    public static final double[] recoilRadianRegens = { 0.0, 0.0, 0.0, 0.0, 0.0 };
    public static final double[] recoilAmounts = { 2.0, -6.0, 2.0, 0.0, 2.0 };
    public static final double[] recoilRegens = { 1.0, 2.0, 2.0, 0.0, 1.0 };
    public static final double[] recoilRadianAmounts = { 0.0, 0.0, 0.0, 0.0, 0.0 };
    public static final int[] widths = { 100, 150, 128, 128, 128 };
    public static final int[] heights = { 100, 75, 128, 128, 128 };
    public static final int[] boundX = { 20, 30, 49, 20, 30 };
    public static final int[] boundY = { 35, 30, 19, 50, 50 };
    public static final int[] boundWidth = { ConfigWeapon.widths[0] - 2 * ConfigWeapon.boundX[0], ConfigWeapon.widths[1] - 2 * ConfigWeapon.boundX[1], ConfigWeapon.widths[2] - 2 * ConfigWeapon.boundX[2], ConfigWeapon.widths[3] - 2 * ConfigWeapon.boundX[3], ConfigWeapon.widths[4] - 2 * ConfigWeapon.boundX[4] };
    public static final int[] boundHeight = { ConfigWeapon.heights[0] - 2 * ConfigWeapon.boundY[0], ConfigWeapon.heights[1] - 2 * ConfigWeapon.boundY[1], ConfigWeapon.heights[2] - 2 * ConfigWeapon.boundY[2], ConfigWeapon.heights[3] - 2 * ConfigWeapon.boundY[3], ConfigWeapon.heights[4] - 2 * ConfigWeapon.boundY[4] };
    public static final double[][][] rotateRels = { { { ConfigWeapon.boundX[0] + ConfigWeapon.boundWidth[0], ConfigWeapon.boundY[0] + ConfigWeapon.boundHeight[0] / 2.0f }, { ConfigWeapon.boundX[0], ConfigWeapon.boundY[0] + ConfigWeapon.boundHeight[0] / 2.0f } }, { { ConfigWeapon.boundX[1] + ConfigWeapon.boundWidth[1], ConfigWeapon.boundY[1] + ConfigWeapon.boundHeight[1] / 2.0f }, { ConfigWeapon.boundX[1], ConfigWeapon.boundY[1] + ConfigWeapon.boundHeight[1] / 2.0f } }, { { ConfigWeapon.boundX[2] + ConfigWeapon.boundWidth[2], ConfigWeapon.boundY[2] + ConfigWeapon.boundHeight[2] / 2.0f }, { ConfigWeapon.boundX[2], ConfigWeapon.boundY[2] + ConfigWeapon.boundHeight[2] / 2.0f } }, { { ConfigWeapon.boundX[3] + ConfigWeapon.boundWidth[3], ConfigWeapon.boundY[3] + ConfigWeapon.boundHeight[3] / 2.0f }, { ConfigWeapon.boundX[3], ConfigWeapon.boundY[3] + ConfigWeapon.boundHeight[3] / 2.0f } }, { { ConfigWeapon.boundX[4] + ConfigWeapon.boundWidth[4], ConfigWeapon.boundY[4] + ConfigWeapon.boundHeight[4] / 2.0f }, { ConfigWeapon.boundX[4], ConfigWeapon.boundY[4] + ConfigWeapon.boundHeight[4] / 2.0f } } };
}
