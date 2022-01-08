// 
// Decompiled by Procyon v0.5.36
// 

package com.tuana9a.config;

public interface ConfigWeaponOut
{
    public static final int NUMBER = 5;
    public static final int STATE_NUMBER = 3;
    public static final int _4EVER = -1;
    public static final int[] lifeTimeLimit = { 200, 200, 5000, 1000, 5000 };
    public static final double[] speeds = { 10.0, 0.0, 10.0, 0.0, 8.0, 25.0 };
    public static final int[] damages = { ConfigWeapon.damages[0], ConfigWeapon.damages[1], ConfigWeapon.damages[2], ConfigWeapon.damages[3], ConfigWeapon.damages[4] };
    public static final int[] widths = { 64, 40, 128, 128, 64 };
    public static final int[] heights = { 64, 40, 128, 128, 64 };
    public static final int[] boundX = { 25, 0, 35, 15, 15 };
    public static final int[] boundY = { 25, 0, 55, 30, 25 };
    public static final int[] boundWidth = { ConfigWeaponOut.widths[0] - 2 * ConfigWeaponOut.boundX[0], ConfigWeaponOut.widths[1] - 2 * ConfigWeaponOut.boundX[1], ConfigWeaponOut.widths[2] - 2 * ConfigWeaponOut.boundX[2], ConfigWeaponOut.widths[3] - 2 * ConfigWeaponOut.boundX[3], ConfigWeaponOut.widths[4] - 2 * ConfigWeaponOut.boundX[4] };
    public static final int[] boundHeight = { ConfigWeaponOut.heights[0] - 2 * ConfigWeaponOut.boundY[0], ConfigWeaponOut.heights[1] - 2 * ConfigWeaponOut.boundY[1], ConfigWeaponOut.heights[2] - 2 * ConfigWeaponOut.boundY[2], ConfigWeaponOut.heights[3] - 2 * ConfigWeaponOut.boundY[3], ConfigWeaponOut.heights[4] - 2 * ConfigWeaponOut.boundY[4] };
    public static final double[][][] rotateRels = { { { ConfigWeaponOut.widths[0] / 2.0, ConfigWeaponOut.heights[0] / 2.0 }, { ConfigWeaponOut.widths[0] / 2.0, ConfigWeaponOut.heights[0] / 2.0 } }, { { ConfigWeaponOut.widths[1] / 2.0, ConfigWeaponOut.heights[1] / 2.0 }, { ConfigWeaponOut.widths[1] / 2.0, ConfigWeaponOut.heights[1] / 2.0 } }, { { ConfigWeaponOut.widths[2], ConfigWeaponOut.heights[2] / 2.0 }, { 0.0, ConfigWeaponOut.heights[2] / 2.0 } }, { { ConfigWeaponOut.widths[3] / 2.0, ConfigWeaponOut.heights[3] / 2.0 }, { ConfigWeaponOut.widths[3] / 2.0, ConfigWeaponOut.heights[3] / 2.0 } }, { { ConfigWeaponOut.widths[4] / 2.0, ConfigWeaponOut.heights[4] / 2.0 }, { ConfigWeaponOut.widths[4] / 2.0, ConfigWeaponOut.heights[4] / 2.0 } } };
}
