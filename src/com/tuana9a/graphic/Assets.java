// 
// Decompiled by Procyon v0.5.36
// 

package com.tuana9a.graphic;

import com.tuana9a.utils.Utils;
import com.tuana9a.utils.Loading;

import java.awt.image.BufferedImage;

public class Assets {
    public static BufferedImage nullImage;
    public static BufferedImage error;
    public static BufferedImage playerHpIcon;
    public static BufferedImage bossHpBarLeft;
    public static BufferedImage bossHpBarMid;
    public static BufferedImage bossHpBarRight;
    public static BufferedImage gameOverImage;
    public static BufferedImage progressBarLeft;
    public static BufferedImage progressBarMid;
    public static BufferedImage progressBarRight;
    public static BufferedImage nullGround;
    public static BufferedImage groundLand1;
    public static BufferedImage groundBush1;
    public static BufferedImage groundBush2;
    public static BufferedImage groundTree1;
    public static BufferedImage groundTree2;
    public static BufferedImage groundFlower1;
    public static BufferedImage groundFlower2;
    public static BufferedImage groundStone1;
    public static BufferedImage groundStone2;
    public static BufferedImage groundWater1;
    public static BufferedImage groundTele1;
    public static BufferedImage[] imagesNumber;
    public static BufferedImage[][] newGameButton;
    public static BufferedImage[][] exitButton;
    public static BufferedImage[][] returnMenuButton;
    public static BufferedImage[][] resumeButton;
    public static BufferedImage[][] pauseButton;
    public static BufferedImage[][] replayButton;
    public static BufferedImage[][] largerScreenButton;
    public static BufferedImage[][] smallerScreenButton;
    public static BufferedImage[][] musicButton;
    public static BufferedImage[][] soundButton;
    public static BufferedImage[][] loadProgressAnimation;
    public static BufferedImage[][] grass1;
    public static BufferedImage[][] grass2;
    public static BufferedImage[][] tree1;
    public static BufferedImage[][] tree2;
    public static BufferedImage[][] flower1;
    public static BufferedImage[][] flower2;
    public static BufferedImage[][] stone1;
    public static BufferedImage[][] stone2;
    public static BufferedImage[][] tele1;
    public static BufferedImage[][] player0;
    public static BufferedImage[][] player1;
    public static BufferedImage[][] player2;
    public static BufferedImage[][] player3;
    public static BufferedImage[][] enemy0;
    public static BufferedImage[][] enemy1;
    public static BufferedImage[][] enemy2;
    public static BufferedImage[][] shotgun;
    public static BufferedImage[][] bow;
    public static BufferedImage[][] spear;
    public static BufferedImage[][] sword;
    public static BufferedImage[][] gatling;
    public static BufferedImage[][] shotGunOut;
    public static BufferedImage[][] bowOut;
    public static BufferedImage[][] spearOut;
    public static BufferedImage[][] swordOut;
    public static BufferedImage[][] gatlingOut;
    public static BufferedImage[][] hat;
    public static BufferedImage[][] helmet;
    public static BufferedImage[][] modernHelmet;
    public static BufferedImage[][][] weapons;
    public static BufferedImage[][][] weaponOuts;
    public static BufferedImage[][][] players;
    public static BufferedImage[][][] enemies;
    public static BufferedImage[][][] staticObjects;
    public static BufferedImage[][][] emoteFaceSad;
    public static BufferedImage[][][] emoteFaceAngry;
    public static BufferedImage[][][] emoteFaceHappy;
    public static BufferedImage[][][] emoteAnger;
    public static BufferedImage[][][] emoteThreeDots;
    public static BufferedImage[][][] emoteSleep;
    public static BufferedImage[][][] emoteBoss;
    public static BufferedImage[][][] deadState;
    public static BufferedImage[][][] surrenderState;
    public static BufferedImage[][][] bossDeadState;
    public static BufferedImage[][][] bowReload;
    public static BufferedImage[][][] shotGunSplash;
    public static BufferedImage[][][] gatlingSplash;
    public static BufferedImage[][][] shotGunOutEffect;
    public static BufferedImage[][][] gatlingOutEffect;
    public static BufferedImage[][][] bowOutEffect;
    public static final int FULL_PROCESS = 7;
    public static final long TIME_SIMULATOR = 100L;

    public static void loadAll() {
        try {
            Assets.nullImage = Utils.loadImg("resources/general/null1.png");
            Assets.error = Utils.loadImg("resources/general/error.png");
            loadAllUIs();
            loadAllWeapons();
            loadAllStateAnimations();
            loadAllPlayers();
            loadAllEnemies();
            loadAllGrounds();
            loadAllStaticObjects();

        } catch (Exception e) {
            e.printStackTrace();
        }

    }

    public static void loadAllUIs() {
        Assets.imagesNumber = new BufferedImage[]{Utils.loadImg("resources/ui/unit/number/ui_num0.png"), Utils.loadImg("resources/ui/unit/number/ui_num1.png"), Utils.loadImg("resources/ui/unit/number/ui_num2.png"), Utils.loadImg("resources/ui/unit/number/ui_num3.png"), Utils.loadImg("resources/ui/unit/number/ui_num4.png"), Utils.loadImg("resources/ui/unit/number/ui_num5.png"), Utils.loadImg("resources/ui/unit/number/ui_num6.png"), Utils.loadImg("resources/ui/unit/number/ui_num7.png"), Utils.loadImg("resources/ui/unit/number/ui_num8.png"), Utils.loadImg("resources/ui/unit/number/ui_num9.png")};
        Assets.playerHpIcon = Utils.loadImg("resources/ui/unit/health_point/tile_heart.png");
        Assets.gameOverImage = Utils.loadImg("resources/ui/image/game_over/game_over.png");
        Assets.bossHpBarLeft = Utils.loadImg("resources/ui/unit/health_point/hp_boss_left.png");
        Assets.bossHpBarMid = Utils.loadImg("resources/ui/unit/health_point/hp_boss_middle.png");
        Assets.bossHpBarRight = Utils.loadImg("resources/ui/unit/health_point/hp_boss_right.png");
        Assets.progressBarLeft = Utils.loadImg("resources/ui/progress_bar/load_progress_left.png");
        Assets.progressBarMid = Utils.loadImg("resources/ui/progress_bar/load_progress_mid.png");
        Assets.progressBarRight = Utils.loadImg("resources/ui/progress_bar/load_progress_right.png");
        final SpriteSheet tempSprite = new SpriteSheet(Utils.loadImg("resources/ui/progress_bar/load_progress_animation_sprite.png"));
        final int tempWidth = 192;
        final int tempHeight = 96;
        Assets.loadProgressAnimation = new BufferedImage[1][8];
        Assets.loadProgressAnimation[0][0] = tempSprite.crop(0, 0, tempWidth, tempHeight);
        Assets.loadProgressAnimation[0][1] = tempSprite.crop(tempWidth, 0, tempWidth, tempHeight);
        Assets.loadProgressAnimation[0][2] = tempSprite.crop(2 * tempWidth, 0, tempWidth, tempHeight);
        Assets.loadProgressAnimation[0][3] = tempSprite.crop(3 * tempWidth, 0, tempWidth, tempHeight);
        Assets.loadProgressAnimation[0][4] = tempSprite.crop(0, tempHeight, tempWidth, tempHeight);
        Assets.loadProgressAnimation[0][5] = tempSprite.crop(tempWidth, tempHeight, tempWidth, tempHeight);
        Assets.loadProgressAnimation[0][6] = tempSprite.crop(2 * tempWidth, tempHeight, tempWidth, tempHeight);
        Assets.loadProgressAnimation[0][7] = tempSprite.crop(3 * tempWidth, tempHeight, tempWidth, tempHeight);
        Assets.exitButton = new BufferedImage[4][1];
        Assets.soundButton = new BufferedImage[4][1];
        Assets.musicButton = new BufferedImage[4][1];
        Assets.pauseButton = new BufferedImage[4][1];
        Assets.resumeButton = new BufferedImage[4][1];
        Assets.replayButton = new BufferedImage[4][1];
        Assets.newGameButton = new BufferedImage[4][1];
        Assets.returnMenuButton = new BufferedImage[4][1];
        Assets.largerScreenButton = new BufferedImage[1][1];
        Assets.smallerScreenButton = new BufferedImage[1][1];
        Assets.largerScreenButton[0][0] = Utils.loadImg("resources/ui/button/size/larger.png");
        Assets.smallerScreenButton[0][0] = Utils.loadImg("resources/ui/button/size/smaller.png");
        Assets.exitButton[0][0] = Utils.loadImg("resources/ui/button/exit/exit_normal.png");
        Assets.exitButton[1][0] = Utils.loadImg("resources/ui/button/exit/exit_hover.png");
        Assets.exitButton[2][0] = Utils.loadImg("resources/ui/button/exit/exit_press.png");
        Assets.exitButton[3][0] = Assets.exitButton[0][0];
        Assets.pauseButton[0][0] = Utils.loadImg("resources/ui/button/pause/pause_normal.png");
        Assets.pauseButton[1][0] = Utils.loadImg("resources/ui/button/pause/pause_hover.png");
        Assets.pauseButton[2][0] = Utils.loadImg("resources/ui/button/pause/pause_press.png");
        Assets.pauseButton[3][0] = Assets.pauseButton[0][0];
        Assets.musicButton[0][0] = Utils.loadImg("resources/ui/button/sound/music_normal.png");
        Assets.musicButton[1][0] = Utils.loadImg("resources/ui/button/sound/music_hover.png");
        Assets.musicButton[2][0] = Utils.loadImg("resources/ui/button/sound/music_press.png");
        Assets.musicButton[3][0] = Assets.musicButton[0][0];
        Assets.soundButton[0][0] = Utils.loadImg("resources/ui/button/sound/sound_normal.png");
        Assets.soundButton[1][0] = Utils.loadImg("resources/ui/button/sound/sound_hover.png");
        Assets.soundButton[2][0] = Utils.loadImg("resources/ui/button/sound/sound_press.png");
        Assets.soundButton[3][0] = Assets.soundButton[0][0];
        Assets.resumeButton[0][0] = Utils.loadImg("resources/ui/button/resume/resume_normal.png");
        Assets.resumeButton[1][0] = Utils.loadImg("resources/ui/button/resume/resume_hover.png");
        Assets.resumeButton[2][0] = Utils.loadImg("resources/ui/button/resume/resume_press.png");
        Assets.resumeButton[3][0] = Assets.resumeButton[0][0];
        Assets.replayButton[0][0] = Utils.loadImg("resources/ui/button/replay/replay_normal.png");
        Assets.replayButton[1][0] = Utils.loadImg("resources/ui/button/replay/replay_hover.png");
        Assets.replayButton[2][0] = Utils.loadImg("resources/ui/button/replay/replay_press.png");
        Assets.replayButton[3][0] = Assets.replayButton[0][0];
        Assets.newGameButton[0][0] = Utils.loadImg("resources/ui/button/new_game/new_game_normal.png");
        Assets.newGameButton[1][0] = Utils.loadImg("resources/ui/button/new_game/new_game_hover.png");
        Assets.newGameButton[2][0] = Utils.loadImg("resources/ui/button/new_game/new_game_press.png");
        Assets.newGameButton[3][0] = Assets.newGameButton[0][0];
        Assets.returnMenuButton[0][0] = Utils.loadImg("resources/ui/button/return_menu/return_menu_normal.png");
        Assets.returnMenuButton[1][0] = Utils.loadImg("resources/ui/button/return_menu/return_menu_hover.png");
        Assets.returnMenuButton[2][0] = Utils.loadImg("resources/ui/button/return_menu/return_menu_press.png");
        Assets.returnMenuButton[3][0] = Assets.returnMenuButton[0][0];
    }

    public static void loadAllPlayers() {
        Assets.player0 = new BufferedImage[2][1];
        Assets.player1 = new BufferedImage[2][1];
        Assets.player2 = new BufferedImage[2][1];
        Assets.player3 = new BufferedImage[2][4];
        Assets.player0[1][0] = Utils.loadImg("resources/entity/moving/player/simple/character_roundRed.png");
        Assets.player0[0][0] = Utils.reverseImg(Assets.player0[1][0]);
        Assets.player1[1][0] = Utils.loadImg("resources/entity/moving/player/simple/character_roundGreen.png");
        Assets.player1[0][0] = Utils.reverseImg(Assets.player1[1][0]);
        Assets.player2[1][0] = Utils.loadImg("resources/entity/moving/player/simple/character_roundYellow.png");
        Assets.player2[0][0] = Utils.reverseImg(Assets.player2[1][0]);
        final SpriteSheet temp0 = new SpriteSheet(Utils.loadImg("resources/entity/moving/player/odin/odin.png"));
        Assets.player3[0][0] = temp0.crop(0, 80, 80, 80);
        Assets.player3[0][1] = temp0.crop(80, 80, 80, 80);
        Assets.player3[0][2] = temp0.crop(160, 80, 80, 80);
        Assets.player3[0][3] = temp0.crop(240, 80, 80, 80);
        Assets.player3[1][0] = temp0.crop(0, 160, 80, 80);
        Assets.player3[1][1] = temp0.crop(80, 160, 80, 80);
        Assets.player3[1][2] = temp0.crop(160, 160, 80, 80);
        Assets.player3[1][3] = temp0.crop(240, 160, 80, 80);
        Assets.players = new BufferedImage[][][]{Assets.player0, Assets.player1, Assets.player2, Assets.player3};
    }

    public static void loadAllEnemies() {
        Assets.enemy0 = new BufferedImage[2][1];
        Assets.enemy0[1][0] = Utils.loadImg("resources/entity/moving/player/simple/character_squarePurple.png");
        Assets.enemy0[0][0] = Utils.reverseImg(Assets.enemy0[1][0]);
        Assets.enemies = new BufferedImage[][][]{Assets.enemy0, Assets.player1, Assets.player2};
    }

    public static void loadAllWeapons() {
        Assets.shotgun = new BufferedImage[2][1];
        Assets.shotGunOut = new BufferedImage[2][1];
        Assets.spear = new BufferedImage[2][1];
        Assets.spearOut = new BufferedImage[2][1];
        Assets.bow = new BufferedImage[2][1];
        Assets.bowOut = new BufferedImage[2][1];
        Assets.sword = new BufferedImage[2][1];
        Assets.swordOut = new BufferedImage[2][1];
        Assets.gatling = new BufferedImage[2][4];
        Assets.gatlingOut = new BufferedImage[2][1];
        Assets.shotgun[1][0] = Utils.loadImg("resources/entity/weapon/shotgun/item_gun.png");
        Assets.shotgun[0][0] = Utils.reverseImg(Assets.shotgun[1][0]);
        Assets.shotGunOut[1][0] = Utils.loadImg("resources/entity/weapon/shotgun/effect_blast.png");
        Assets.shotGunOut[0][0] = Utils.reverseImg(Assets.shotGunOut[1][0]);
        Assets.spear[1][0] = Utils.rotateImg(Utils.loadImg("resources/entity/weapon/spear/item_spear.png"), 1.5707963267948966);
        Assets.spear[0][0] = Utils.reverseImg(Assets.spear[1][0]);
        Assets.spearOut[1][0] = Utils.loadImg("resources/entity/weapon/spear/spear_out.png");
        Assets.spearOut[0][0] = Utils.reverseImg(Assets.spearOut[1][0]);
        Assets.bow[1][0] = Utils.loadImg("resources/entity/weapon/bow/item_bow.png");
        Assets.bow[0][0] = Utils.reverseImg(Assets.bow[1][0]);
        Assets.bowOut[1][0] = Utils.loadImg("resources/entity/weapon/bow/item_arrow.png");
        Assets.bowOut[0][0] = Utils.reverseImg(Assets.bowOut[1][0]);
        Assets.sword[1][0] = Utils.rotateImg(Utils.loadImg("resources/entity/weapon/sword/item_sword.png"), 1.5707963267948966);
        Assets.sword[0][0] = Utils.reverseImg(Assets.sword[1][0]);
        Assets.swordOut[1][0] = Utils.loadImg("resources/entity/weapon/sword/sword_out.png");
        Assets.swordOut[0][0] = Utils.reverseImg(Assets.swordOut[1][0]);
        Assets.gatling[1][0] = Utils.loadImg("resources/entity/weapon/gatling/gatling0.png");
        Assets.gatling[1][1] = Utils.loadImg("resources/entity/weapon/gatling/gatling1.png");
        Assets.gatling[1][2] = Utils.loadImg("resources/entity/weapon/gatling/gatling2.png");
        Assets.gatling[1][3] = Utils.loadImg("resources/entity/weapon/gatling/gatling3.png");
        Assets.gatling[0][0] = Utils.reverseImg(Assets.gatling[1][0]);
        Assets.gatling[0][1] = Utils.reverseImg(Assets.gatling[1][1]);
        Assets.gatling[0][2] = Utils.reverseImg(Assets.gatling[1][2]);
        Assets.gatling[0][3] = Utils.reverseImg(Assets.gatling[1][3]);
        Assets.gatlingOut[1][0] = Utils.loadImg("resources/entity/weapon/gatling/gatling_out0.png");
        Assets.gatlingOut[0][0] = Utils.reverseImg(Assets.gatlingOut[1][0]);
        Assets.weapons = new BufferedImage[][][]{Assets.shotgun, Assets.spear, Assets.bow, Assets.sword, Assets.gatling};
        Assets.weaponOuts = new BufferedImage[][][]{Assets.shotGunOut, Assets.spearOut, Assets.bowOut, Assets.swordOut, Assets.gatlingOut};
    }

    public static void loadAllGrounds() {
        Assets.groundLand1 = Utils.loadImg("resources/map/all_grounds/land1.png");
        Assets.groundBush1 = Utils.loadImg("resources/map/all_grounds/grass1.png");
        Assets.groundBush2 = Utils.loadImg("resources/map/all_grounds/grass2.png");
        Assets.groundTree1 = Utils.loadImg("resources/map/all_grounds/tree1.png");
        Assets.groundTree2 = Utils.loadImg("resources/map/all_grounds/tree2.png");
        Assets.groundFlower1 = Utils.loadImg("resources/map/all_grounds/flower1.png");
        Assets.groundFlower2 = Utils.loadImg("resources/map/all_grounds/flower2.png");
        Assets.groundStone1 = Utils.loadImg("resources/map/all_grounds/stone1.png");
        Assets.groundStone2 = Utils.loadImg("resources/map/all_grounds/stone2.png");
        Assets.groundWater1 = Utils.loadImg("resources/map/all_grounds/water1.png");
        Assets.groundTele1 = Utils.loadImg("resources/map/all_grounds/tele1.png");
        Assets.nullGround = Utils.loadImg("resources/general/null1.png");
    }

    public static void loadAllStaticObjects() {
        Assets.tree1 = new BufferedImage[1][1];
        Assets.tree2 = new BufferedImage[1][1];
        Assets.grass1 = new BufferedImage[1][1];
        Assets.grass2 = new BufferedImage[1][1];
        Assets.stone1 = new BufferedImage[1][1];
        Assets.stone2 = new BufferedImage[1][1];
        Assets.flower1 = new BufferedImage[1][1];
        Assets.flower2 = new BufferedImage[1][1];
        Assets.tele1 = new BufferedImage[1][10];
        Assets.tree1[0][0] = Utils.loadImg("resources/entity/static_object/tree1.png");
        Assets.tree2[0][0] = Utils.loadImg("resources/entity/static_object/tree2.png");
        Assets.grass1[0][0] = Utils.loadImg("resources/entity/static_object/grass1.png");
        final SpriteSheet teleGateSprite = new SpriteSheet(Utils.loadImg("resources/entity/static_object/tele_gate_sprite.png"));
        Assets.tele1[0][0] = teleGateSprite.crop(0, 0, 100, 100);
        Assets.tele1[0][1] = teleGateSprite.crop(100, 0, 100, 100);
        Assets.tele1[0][2] = teleGateSprite.crop(200, 0, 100, 100);
        Assets.tele1[0][3] = teleGateSprite.crop(300, 0, 100, 100);
        Assets.tele1[0][4] = teleGateSprite.crop(400, 0, 100, 100);
        Assets.tele1[0][5] = teleGateSprite.crop(500, 0, 100, 100);
        Assets.tele1[0][6] = teleGateSprite.crop(0, 100, 100, 100);
        Assets.tele1[0][7] = teleGateSprite.crop(100, 100, 100, 100);
        Assets.tele1[0][8] = teleGateSprite.crop(300, 100, 100, 100);
        Assets.tele1[0][9] = teleGateSprite.crop(400, 100, 100, 100);
        Assets.staticObjects = new BufferedImage[][][]{null, Assets.tree1, Assets.tree2, Assets.grass1, Assets.grass1, Assets.flower1, Assets.flower2, Assets.stone1, Assets.stone2, null, Assets.tele1};
    }

    public static void loadAllStateAnimations() {
        Assets.deadState = new BufferedImage[2][1][1];
        Assets.deadState[1][0][0] = Utils.loadImg("resources/entity/moving/state/state_dead.png");
        Assets.deadState[0][0][0] = Utils.reverseImg(Assets.deadState[1][0][0]);
        Assets.surrenderState = new BufferedImage[2][1][1];
        Assets.surrenderState[1][0][0] = Utils.loadImg("resources/entity/moving/state/state_surrender.png");
        Assets.surrenderState[0][0][0] = Utils.reverseImg(Assets.surrenderState[1][0][0]);
        Assets.emoteThreeDots = new BufferedImage[2][1][1];
        Assets.emoteThreeDots[1][0][0] = Utils.loadImg("resources/entity/moving/emote/emote_dots3.png");
        Assets.emoteThreeDots[0][0][0] = Utils.reverseImg(Assets.emoteThreeDots[1][0][0]);
        Assets.emoteSleep = new BufferedImage[2][1][1];
        Assets.emoteSleep[1][0][0] = Utils.loadImg("resources/entity/moving/emote/emote_sleeps.png");
        Assets.emoteSleep[0][0][0] = Utils.reverseImg(Assets.emoteSleep[1][0][0]);
        Assets.emoteFaceSad = new BufferedImage[2][1][1];
        Assets.emoteFaceSad[1][0][0] = Utils.loadImg("resources/entity/moving/emote/emote_faceSad.png");
        Assets.emoteFaceSad[0][0][0] = Utils.reverseImg(Assets.emoteFaceSad[1][0][0]);
        Assets.emoteFaceAngry = new BufferedImage[2][1][1];
        Assets.emoteFaceAngry[1][0][0] = Utils.loadImg("resources/entity/moving/emote/emote_faceAngry.png");
        Assets.emoteFaceAngry[0][0][0] = Utils.reverseImg(Assets.emoteFaceAngry[1][0][0]);
        Assets.emoteFaceHappy = new BufferedImage[2][1][1];
        Assets.emoteFaceHappy[1][0][0] = Utils.loadImg("resources/entity/moving/emote/emote_faceHappy.png");
        Assets.emoteFaceHappy[0][0][0] = Utils.reverseImg(Assets.emoteFaceHappy[1][0][0]);
        Assets.emoteAnger = new BufferedImage[2][1][1];
        Assets.emoteAnger[1][0][0] = Utils.loadImg("resources/entity/moving/emote/emote_anger.png");
        Assets.emoteAnger[0][0][0] = Utils.reverseImg(Assets.emoteAnger[1][0][0]);
        Assets.emoteBoss = new BufferedImage[2][1][1];
        Assets.emoteBoss[1][0][0] = Utils.loadImg("resources/entity/moving/emote/emote_exclamation.png");
        Assets.emoteBoss[0][0][0] = Utils.reverseImg(Assets.emoteBoss[1][0][0]);
        Assets.bowReload = new BufferedImage[2][5][1];
        Assets.bowReload[1][0][0] = Utils.loadImg("resources/entity/weapon/bow/item_arrow.png");
        Assets.bowReload[1][1][0] = Utils.loadImg("resources/entity/weapon/bow/item_arrow.png");
        Assets.bowReload[1][2][0] = Utils.loadImg("resources/entity/weapon/bow/item_arrow.png");
        Assets.bowReload[1][3][0] = Utils.loadImg("resources/entity/weapon/bow/item_arrow.png");
        Assets.bowReload[1][4][0] = Utils.loadImg("resources/entity/weapon/bow/item_arrow.png");
        Assets.bowReload[0][0][0] = Utils.reverseImg(Assets.bowReload[1][0][0]);
        Assets.bowReload[0][1][0] = Utils.reverseImg(Assets.bowReload[1][1][0]);
        Assets.bowReload[0][2][0] = Utils.reverseImg(Assets.bowReload[1][2][0]);
        Assets.bowReload[0][3][0] = Utils.reverseImg(Assets.bowReload[1][3][0]);
        Assets.bowReload[0][4][0] = Utils.reverseImg(Assets.bowReload[1][4][0]);
        final SpriteSheet bossDeadSprite = new SpriteSheet(Utils.loadImg("resources/entity/moving/state/state_boss_dead.png"));
        Assets.bossDeadState = new BufferedImage[2][1][10];
        Assets.bossDeadState[1][0][0] = bossDeadSprite.crop(0, 0, 100, 100);
        Assets.bossDeadState[1][0][1] = bossDeadSprite.crop(200, 0, 100, 100);
        Assets.bossDeadState[1][0][2] = bossDeadSprite.crop(300, 0, 100, 100);
        Assets.bossDeadState[1][0][3] = bossDeadSprite.crop(400, 0, 100, 100);
        Assets.bossDeadState[1][0][4] = bossDeadSprite.crop(0, 100, 100, 100);
        Assets.bossDeadState[1][0][5] = bossDeadSprite.crop(100, 100, 100, 100);
        Assets.bossDeadState[1][0][6] = bossDeadSprite.crop(200, 100, 100, 100);
        Assets.bossDeadState[1][0][7] = bossDeadSprite.crop(300, 100, 100, 100);
        Assets.bossDeadState[1][0][8] = bossDeadSprite.crop(400, 100, 100, 100);
        Assets.bossDeadState[1][0][9] = bossDeadSprite.crop(0, 200, 100, 100);
        Assets.bossDeadState[0][0][0] = Utils.reverseImg(Assets.bossDeadState[1][0][0]);
        Assets.bossDeadState[0][0][1] = Utils.reverseImg(Assets.bossDeadState[1][0][1]);
        Assets.bossDeadState[0][0][2] = Utils.reverseImg(Assets.bossDeadState[1][0][2]);
        Assets.bossDeadState[0][0][3] = Utils.reverseImg(Assets.bossDeadState[1][0][3]);
        Assets.bossDeadState[0][0][4] = Utils.reverseImg(Assets.bossDeadState[1][0][4]);
        Assets.bossDeadState[0][0][5] = Utils.reverseImg(Assets.bossDeadState[1][0][5]);
        Assets.bossDeadState[0][0][6] = Utils.reverseImg(Assets.bossDeadState[1][0][6]);
        Assets.bossDeadState[0][0][7] = Utils.reverseImg(Assets.bossDeadState[1][0][7]);
        Assets.bossDeadState[0][0][8] = Utils.reverseImg(Assets.bossDeadState[1][0][8]);
        Assets.bossDeadState[0][0][9] = Utils.reverseImg(Assets.bossDeadState[1][0][9]);
        Assets.gatlingSplash = new BufferedImage[2][1][4];
        Assets.gatlingSplash[1][0][0] = Utils.loadImg("resources/entity/weapon/gatling/gatling_splash0.png");
        Assets.gatlingSplash[1][0][1] = Utils.loadImg("resources/entity/weapon/gatling/gatling_splash1.png");
        Assets.gatlingSplash[1][0][2] = Utils.loadImg("resources/entity/weapon/gatling/gatling_splash2.png");
        Assets.gatlingSplash[1][0][3] = Utils.loadImg("resources/entity/weapon/gatling/gatling_splash3.png");
        Assets.gatlingSplash[0][0][0] = Utils.reverseImg(Assets.gatlingSplash[1][0][0]);
        Assets.gatlingSplash[0][0][1] = Utils.reverseImg(Assets.gatlingSplash[1][0][1]);
        Assets.gatlingSplash[0][0][2] = Utils.reverseImg(Assets.gatlingSplash[1][0][2]);
        Assets.gatlingSplash[0][0][3] = Utils.reverseImg(Assets.gatlingSplash[1][0][3]);
        Assets.shotGunSplash = new BufferedImage[2][1][1];
        Assets.shotGunSplash[1][0][0] = Utils.loadImg("resources/entity/weapon/shotgun/shotgun_splash0.png");
        Assets.shotGunSplash[0][0][0] = Utils.reverseImg(Assets.shotGunSplash[1][0][0]);
        Assets.gatlingOutEffect = new BufferedImage[2][1][1];
        Assets.gatlingOutEffect[1][0][0] = Utils.loadImg("resources/entity/weapon/gatling/gatling_out_effect0.png");
        Assets.gatlingOutEffect[0][0][0] = Utils.reverseImg(Assets.gatlingOutEffect[1][0][0]);
        Assets.bowOutEffect = new BufferedImage[2][1][1];
        Assets.bowOutEffect[1][0][0] = Utils.loadImg("resources/entity/weapon/bow/item_arrow_effect.png");
        Assets.bowOutEffect[0][0][0] = Utils.reverseImg(Assets.bowOutEffect[1][0][0]);
        Assets.shotGunOutEffect = new BufferedImage[2][1][1];
        Assets.shotGunOutEffect[1][0][0] = Utils.loadImg("resources/entity/weapon/shotgun/effect_blast_effect.png");
        Assets.shotGunOutEffect[0][0][0] = Utils.reverseImg(Assets.shotGunOutEffect[1][0][0]);
    }
}
