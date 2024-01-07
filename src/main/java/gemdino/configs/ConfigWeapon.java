package gemdino.configs;

public interface ConfigWeapon {
    int NUMBER = 5;
    int STATE_NUMBER = 5;
    int[] shootWeapons = {0, 2, 4};
    int[] shotGunWeapons = {0};
    int[] bowWeapons = {2};
    int[] swordWeapons = {3};
    int[] spearWeapons = {1};
    int[] gatlingWeapons = {4};
    int[] damages = {2, 3, 2, 2, 1};
    double[] attackSpeeds = {2.0, 2.0, 1.0, 3.0, 8.0};
    double[] accuracies = {0.3141592653589793, 0.0, 0.15707963267948966, 0.0, 0.15707963267948966};
    double[] rotateSpeeds = {0.04487989505128276, 0.05235987755982988, 0.04487989505128276, 0.05235987755982988, 0.04487989505128276};
    double[] reloadRatios = {0.8, 0.8, 0.8, 0.8, 0.8};
    double[] recoilRadianRegens = {0.0, 0.0, 0.0, 0.0, 0.0};
    double[] recoilAmounts = {2.0, -6.0, 2.0, 0.0, 2.0};
    double[] recoilRegens = {1.0, 2.0, 2.0, 0.0, 1.0};
    double[] recoilRadianAmounts = {0.0, 0.0, 0.0, 0.0, 0.0};
    int[] widths = {100, 150, 128, 128, 128};
    int[] heights = {100, 75, 128, 128, 128};
    int[] boundX = {20, 30, 49, 20, 30};
    int[] boundY = {35, 30, 19, 50, 50};
    int[] boundWidth = {ConfigWeapon.widths[0] - 2 * ConfigWeapon.boundX[0], ConfigWeapon.widths[1] - 2 * ConfigWeapon.boundX[1], ConfigWeapon.widths[2] - 2 * ConfigWeapon.boundX[2], ConfigWeapon.widths[3] - 2 * ConfigWeapon.boundX[3], ConfigWeapon.widths[4] - 2 * ConfigWeapon.boundX[4]};
    int[] boundHeight = {ConfigWeapon.heights[0] - 2 * ConfigWeapon.boundY[0], ConfigWeapon.heights[1] - 2 * ConfigWeapon.boundY[1], ConfigWeapon.heights[2] - 2 * ConfigWeapon.boundY[2], ConfigWeapon.heights[3] - 2 * ConfigWeapon.boundY[3], ConfigWeapon.heights[4] - 2 * ConfigWeapon.boundY[4]};
    double[][][] rotateRels = {{{ConfigWeapon.boundX[0] + ConfigWeapon.boundWidth[0], ConfigWeapon.boundY[0] + ConfigWeapon.boundHeight[0] / 2.0f}, {ConfigWeapon.boundX[0], ConfigWeapon.boundY[0] + ConfigWeapon.boundHeight[0] / 2.0f}}, {{ConfigWeapon.boundX[1] + ConfigWeapon.boundWidth[1], ConfigWeapon.boundY[1] + ConfigWeapon.boundHeight[1] / 2.0f}, {ConfigWeapon.boundX[1], ConfigWeapon.boundY[1] + ConfigWeapon.boundHeight[1] / 2.0f}}, {{ConfigWeapon.boundX[2] + ConfigWeapon.boundWidth[2], ConfigWeapon.boundY[2] + ConfigWeapon.boundHeight[2] / 2.0f}, {ConfigWeapon.boundX[2], ConfigWeapon.boundY[2] + ConfigWeapon.boundHeight[2] / 2.0f}}, {{ConfigWeapon.boundX[3] + ConfigWeapon.boundWidth[3], ConfigWeapon.boundY[3] + ConfigWeapon.boundHeight[3] / 2.0f}, {ConfigWeapon.boundX[3], ConfigWeapon.boundY[3] + ConfigWeapon.boundHeight[3] / 2.0f}}, {{ConfigWeapon.boundX[4] + ConfigWeapon.boundWidth[4], ConfigWeapon.boundY[4] + ConfigWeapon.boundHeight[4] / 2.0f}, {ConfigWeapon.boundX[4], ConfigWeapon.boundY[4] + ConfigWeapon.boundHeight[4] / 2.0f}}};
}
