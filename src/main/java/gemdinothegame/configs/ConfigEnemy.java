//
// Decompiled by Procyon v0.5.36
//

package gemdinothegame.configs;

public interface ConfigEnemy {
    int NUMBER = 3;
    int STATE_NUMBER = ConfigAnimal.STATE_NUMBER;
    int[] bossEnemies = {2};
    int[] aggressiveEnemies = {1};
    long[] deadTimeOuts = {3000L, 5000L, -1L};
    int[] speeds = {2, 2, 2};
    int[] healths = {10, 20, 50};
    long[] typicalTimers = {3000L, 1000L, 1000L};
    long[] reactionTimers = {2000L, 1500L, 700L};
    int REACT_TIME = -500;
    int[] workDistances = {600, 800, 1000};
    int[] eyeDistances = {400, 800, 1000};
    int[] widths = {64, 128, 200};
    int[] heights = {64, 128, 200};
    int[] boundX = {20, 40, 50};
    int[] boundY = {10, 20, 50};
    int[] boundWidth = {ConfigEnemy.widths[0] - 2 * ConfigEnemy.boundX[0], ConfigEnemy.widths[1] - 2 * ConfigEnemy.boundX[1], ConfigEnemy.widths[2] - 2 * ConfigEnemy.boundX[2]};
    int[][][] shoulders = {{{ConfigEnemy.boundX[0] + ConfigEnemy.boundWidth[0], ConfigEnemy.boundY[0]}, {ConfigEnemy.boundX[0], ConfigEnemy.boundY[0]}}, {{ConfigEnemy.boundX[1] + ConfigEnemy.boundWidth[1], ConfigEnemy.boundY[1]}, {ConfigEnemy.boundX[1], ConfigEnemy.boundY[1]}}, {{ConfigEnemy.boundX[2] + ConfigEnemy.boundWidth[2], ConfigEnemy.boundY[2]}, {ConfigEnemy.boundX[2], ConfigEnemy.boundY[2]}}};
    int[] boundHeight = {ConfigEnemy.heights[0] - 2 * ConfigEnemy.boundY[0], ConfigEnemy.heights[1] - 2 * ConfigEnemy.boundY[1], ConfigEnemy.heights[2] - 2 * ConfigEnemy.boundY[2]};
    int[] spaceHandsX = {30, 35, 35};
    int[] spaceHandsY = {0, 0, 0};
    int[][][] holdingHands = {{{ConfigEnemy.boundX[0] - ConfigEnemy.spaceHandsX[0], ConfigEnemy.boundY[0] + ConfigEnemy.boundHeight[0] / 2 + ConfigEnemy.spaceHandsY[0]}, {ConfigEnemy.boundX[0] + ConfigEnemy.boundWidth[0] + ConfigEnemy.spaceHandsX[0], ConfigEnemy.boundY[0] + ConfigEnemy.boundHeight[0] / 2 + ConfigEnemy.spaceHandsY[0]}}, {{ConfigEnemy.boundX[1] - ConfigEnemy.spaceHandsX[1], ConfigEnemy.boundY[1] + ConfigEnemy.boundHeight[1] / 2 + ConfigEnemy.spaceHandsY[1]}, {ConfigEnemy.boundX[1] + ConfigEnemy.boundWidth[1] + ConfigEnemy.spaceHandsX[1], ConfigEnemy.boundY[1] + ConfigEnemy.boundHeight[1] / 2 + ConfigEnemy.spaceHandsY[1]}}, {{ConfigEnemy.boundX[2] - ConfigEnemy.spaceHandsX[2], ConfigEnemy.boundY[2] + ConfigEnemy.boundHeight[2] / 2 + ConfigEnemy.spaceHandsY[2]}, {ConfigEnemy.boundX[2] + ConfigEnemy.boundWidth[2] + ConfigEnemy.spaceHandsX[2], ConfigEnemy.boundY[2] + ConfigEnemy.boundHeight[2] / 2 + ConfigEnemy.spaceHandsY[2]}}};
}
