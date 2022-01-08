// 
// Decompiled by Procyon v0.5.36
// 

package com.tuana9a.config;

public interface ConfigEnemy
{
    public static final int NUMBER = 3;
    public static final int STATE_NUMBER = ConfigAnimal.STATE_NUMBER;
    public static final int[] bossEnemies = { 2 };
    public static final int[] aggressiveEnemies = { 1 };
    public static final long[] deadTimeOuts = { 3000L, 5000L, -1L };
    public static final int[] speeds = { 2, 2, 2 };
    public static final int[] healths = { 10, 20, 50 };
    public static final long[] typicalTimers = { 3000L, 1000L, 1000L };
    public static final long[] reactionTimers = { 2000L, 1500L, 700L };
    public static final int REACT_TIME = -500;
    public static final int[] workDistances = { 600, 800, 1000 };
    public static final int[] eyeDistances = { 400, 800, 1000 };
    public static final int[] widths = { 64, 128, 200 };
    public static final int[] heights = { 64, 128, 200 };
    public static final int[] boundX = { 20, 40, 50 };
    public static final int[] boundY = { 10, 20, 50 };
    public static final int[] boundWidth = { ConfigEnemy.widths[0] - 2 * ConfigEnemy.boundX[0], ConfigEnemy.widths[1] - 2 * ConfigEnemy.boundX[1], ConfigEnemy.widths[2] - 2 * ConfigEnemy.boundX[2] };
    public static final int[][][] shoulders = { { { ConfigEnemy.boundX[0] + ConfigEnemy.boundWidth[0], ConfigEnemy.boundY[0] }, { ConfigEnemy.boundX[0], ConfigEnemy.boundY[0] } }, { { ConfigEnemy.boundX[1] + ConfigEnemy.boundWidth[1], ConfigEnemy.boundY[1] }, { ConfigEnemy.boundX[1], ConfigEnemy.boundY[1] } }, { { ConfigEnemy.boundX[2] + ConfigEnemy.boundWidth[2], ConfigEnemy.boundY[2] }, { ConfigEnemy.boundX[2], ConfigEnemy.boundY[2] } } };
    public static final int[] boundHeight = { ConfigEnemy.heights[0] - 2 * ConfigEnemy.boundY[0], ConfigEnemy.heights[1] - 2 * ConfigEnemy.boundY[1], ConfigEnemy.heights[2] - 2 * ConfigEnemy.boundY[2] };
    public static final int[] spaceHandsX = { 30, 35, 35 };
    public static final int[] spaceHandsY = { 0, 0, 0 };
    public static final int[][][] holdingHands = { { { ConfigEnemy.boundX[0] - ConfigEnemy.spaceHandsX[0], ConfigEnemy.boundY[0] + ConfigEnemy.boundHeight[0] / 2 + ConfigEnemy.spaceHandsY[0] }, { ConfigEnemy.boundX[0] + ConfigEnemy.boundWidth[0] + ConfigEnemy.spaceHandsX[0], ConfigEnemy.boundY[0] + ConfigEnemy.boundHeight[0] / 2 + ConfigEnemy.spaceHandsY[0] } }, { { ConfigEnemy.boundX[1] - ConfigEnemy.spaceHandsX[1], ConfigEnemy.boundY[1] + ConfigEnemy.boundHeight[1] / 2 + ConfigEnemy.spaceHandsY[1] }, { ConfigEnemy.boundX[1] + ConfigEnemy.boundWidth[1] + ConfigEnemy.spaceHandsX[1], ConfigEnemy.boundY[1] + ConfigEnemy.boundHeight[1] / 2 + ConfigEnemy.spaceHandsY[1] } }, { { ConfigEnemy.boundX[2] - ConfigEnemy.spaceHandsX[2], ConfigEnemy.boundY[2] + ConfigEnemy.boundHeight[2] / 2 + ConfigEnemy.spaceHandsY[2] }, { ConfigEnemy.boundX[2] + ConfigEnemy.boundWidth[2] + ConfigEnemy.spaceHandsX[2], ConfigEnemy.boundY[2] + ConfigEnemy.boundHeight[2] / 2 + ConfigEnemy.spaceHandsY[2] } } };
}
