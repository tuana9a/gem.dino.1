// 
// Decompiled by Procyon v0.5.36
// 

package com.tuana9a.gemdino.configs;

public interface ConfigPlayer {
    int NUMBER = 4;
    int STATE_NUMBER = ConfigAnimal.STATE_NUMBER;
    int[] speeds = {2, 2, 2, 3};
    int[] healths = {100, 10, 10, 25};
    int[] widths = {64, 64, 64, 128};
    int[] heights = {64, 64, 64, 128};
    int[] boundX = {20, 20, 20, 45};
    int[] boundY = {10, 10, 10, 40};
    int[] boundWidth = {ConfigPlayer.widths[0] - 2 * ConfigPlayer.boundX[0], ConfigPlayer.widths[1] - 2 * ConfigPlayer.boundX[1], ConfigPlayer.widths[2] - 2 * ConfigPlayer.boundX[2], ConfigPlayer.widths[3] - 2 * ConfigPlayer.boundX[3]};
    int[] boundHeight = {ConfigPlayer.heights[0] - 2 * ConfigPlayer.boundY[0], ConfigPlayer.heights[1] - 2 * ConfigPlayer.boundY[1], ConfigPlayer.heights[2] - 2 * ConfigPlayer.boundY[2], ConfigPlayer.heights[3] - 2 * ConfigPlayer.boundY[3]};
    int[][][] shoulders = {{{ConfigPlayer.boundX[0] + ConfigPlayer.boundWidth[0], ConfigPlayer.boundY[0]}, {ConfigPlayer.boundX[0], ConfigPlayer.boundY[0]}}, {{ConfigPlayer.boundX[1] + ConfigPlayer.boundWidth[1], ConfigPlayer.boundY[1]}, {ConfigPlayer.boundX[1], ConfigPlayer.boundY[1]}}, {{ConfigPlayer.boundX[2] + ConfigPlayer.boundWidth[2], ConfigPlayer.boundY[2]}, {ConfigPlayer.boundX[2], ConfigPlayer.boundY[2]}}, {{ConfigPlayer.boundX[3] + ConfigPlayer.boundWidth[3], ConfigPlayer.boundY[3]}, {ConfigPlayer.boundX[3], ConfigPlayer.boundY[3]}}};
    int[] spaceHandsX = {30, 30, 30, 10};
    int[] spaceHandsY = {0, 0, 0, -20};
    int[][][] holdingHands = {{{ConfigPlayer.boundX[0] - ConfigPlayer.spaceHandsX[0], ConfigPlayer.boundY[0] + ConfigPlayer.boundHeight[0] / 2 + ConfigPlayer.spaceHandsY[0]}, {ConfigPlayer.boundX[0] + ConfigPlayer.boundWidth[0] + ConfigPlayer.spaceHandsX[0], ConfigPlayer.boundY[0] + ConfigPlayer.boundHeight[0] / 2 + ConfigPlayer.spaceHandsY[0]}}, {{ConfigPlayer.boundX[1] - ConfigPlayer.spaceHandsX[1], ConfigPlayer.boundY[1] + ConfigPlayer.boundHeight[1] / 2 + ConfigPlayer.spaceHandsY[1]}, {ConfigPlayer.boundX[1] + ConfigPlayer.boundWidth[1] + ConfigPlayer.spaceHandsX[1], ConfigPlayer.boundY[1] + ConfigPlayer.boundHeight[1] / 2 + ConfigPlayer.spaceHandsY[1]}}, {{ConfigPlayer.boundX[2] - ConfigPlayer.spaceHandsX[2], ConfigPlayer.boundY[2] + ConfigPlayer.boundHeight[2] / 2 + ConfigPlayer.spaceHandsY[2]}, {ConfigPlayer.boundX[2] + ConfigPlayer.boundWidth[2] + ConfigPlayer.spaceHandsX[2], ConfigPlayer.boundY[2] + ConfigPlayer.boundHeight[2] / 2 + ConfigPlayer.spaceHandsY[2]}}, {{ConfigPlayer.boundX[3] - ConfigPlayer.spaceHandsX[3], ConfigPlayer.boundY[3] + ConfigPlayer.boundHeight[3] / 2 + ConfigPlayer.spaceHandsY[3]}, {ConfigPlayer.boundX[3] + ConfigPlayer.boundWidth[3] + ConfigPlayer.spaceHandsX[3], ConfigPlayer.boundY[3] + ConfigPlayer.boundHeight[3] / 2 + ConfigPlayer.spaceHandsY[3]}}};
}
