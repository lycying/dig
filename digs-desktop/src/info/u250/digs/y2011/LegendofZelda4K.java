package info.u250.digs.y2011;

/*
 * Legend of Zelda 4K
 * Copyright (C) 2011 meatfighter.com
 *
 * This file is part of Legend of Zelda 4K.
 *
 * Legend of Zelda 4K is free software; you can redistribute it and/or modify
 * it under the terms of the GNU Lesser General Public License as published
 * by the Free Software Foundation; either version 3 of the License, or
 * (at your option) any later version.
 *
 * Legend of Zelda 4K is distributed in the hope that it will be useful,
 * but WITHOUT ANY WARRANTY; without even the implied warranty of
 * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
 * GNU Lesser General Public License for more details.
 *
 * You should have received a copy of the GNU Lesser General Public License
 * along with this program.  If not, see <http://www.gnu.org/licenses/>.
 *
 */

import java.applet.Applet;
import java.awt.Color;
import java.awt.Graphics2D;
import java.awt.event.KeyEvent;
import java.awt.geom.AffineTransform;
import java.awt.image.BufferedImage;
import java.util.ArrayList;
import java.util.Random;

public class LegendofZelda4K extends Applet implements Runnable {

  /**
	 * 
	 */
	private static final long serialVersionUID = 1L;
// keys
  private boolean[] a = new boolean[32768];

  @Override
  public void start() {
    enableEvents(8);
    new Thread(this).start();
  }

  public void run() {

    final int VK_LEFT = 0x25;
    final int VK_RIGHT = 0x27;
    final int VK_UP = 0x26;
    final int VK_DOWN = 0x28;
    final int VK_ATTACK = 0x42;

    final int DIRECTION_RIGHT = 0;
    final int DIRECTION_DOWN = 1;
    final int DIRECTION_LEFT = 2;
    final int DIRECTION_UP = 3;

    final int MAP_EMPTY = 0;    
    final int MAP_SWORD = 1;
    final int MAP_CANDLE = 2;
    final int MAP_KEY = 3;
    final int MAP_PRINCESS = 4;
    final int MAP_HEART = 5;
    final int MAP_BRIDGE = 6;
    final int MAP_LADDER = 7;
    final int MAP_WALL = 8;
    final int MAP_LOCK = 9;
    final int MAP_CRACKED_WALL = 10;
    final int MAP_WATER = 11;

    final int SPRITE_COUNT = 22;
    final int MAP_OFFSET = SPRITE_COUNT << 5;
    final int SPRITE_FLIPPED = 32;

    final int SPRITE_LINK_DOWN_1 = 0;
    final int SPRITE_LINK_DOWN_2 = 1;
    final int SPRITE_LINK_DOWN_LUNGE = 2;
    final int SPRITE_LINK_LEFT_1 = 3;
    final int SPRITE_LINK_LEFT_2 = 4;
    final int SPRITE_LINK_LEFT_LUNGE = 5;
    final int SPRITE_LINK_UP_1 = 6;
    final int SPRITE_LINK_UP_2 = SPRITE_LINK_UP_1 + SPRITE_FLIPPED;
    final int SPRITE_LINK_UP_LUNGE = 7;
    final int SPRITE_LINK_RIGHT_1 = SPRITE_LINK_LEFT_1 + SPRITE_FLIPPED;
    final int SPRITE_LINK_RIGHT_2 = SPRITE_LINK_LEFT_2 + SPRITE_FLIPPED;
    final int SPRITE_LINK_RIGHT_LUNGE = SPRITE_LINK_LEFT_LUNGE + SPRITE_FLIPPED;
    final int SPRITE_SWORD = 8;
    final int SPRITE_ROCK = 9;
    final int SPRITE_CRACKED_ROCK = 10;
    final int SPRITE_OCTOROC_1 = 11;
    final int SPRITE_OCTOROC_2 = 12;
    final int SPRITE_CANDLE = 13;
    final int SPRITE_HEART = 14;
    final int SPRITE_KEY = 15;
    final int SPRITE_TRIFORCE = 16;
    final int SPRITE_LOCK = 17;
    final int SPRITE_WATER = 18;
    final int SPRITE_BRIDGE = 19;
    final int SPRITE_PRINCESS = 20;
    final int SPRITE_LADDER = 21;

    final int ENEMY_X = 0;
    final int ENEMY_Y = 1;
    final int ENEMY_SPRITE = 2;
    final int ENEMY_DIRECTION = 3;
    final int ENEMY_COUNTER = 4;
    final int ENEMY_DYING = 5;

    final Color COLOR_FLOOR = new Color(0xF7D8A5);
    final Color COLOR_MAP_1 = new Color(0x666666);
    final Color COLOR_MAP_2 = new Color(0x88D800);
    final Color COLOR_RED = new Color(0xB53120);

    final String S = "u002aua800u00aauaa00u0c95u5630u0c55u5530u0f7buedf0u0f77uddf0u07ffuffc0u06bdu7e80u15afuf554u17aau9575u1fe9u75fdu3f55ub575u0ea9u7575u00aaub555u0054u1ffcu0000u1500u002aua800u00aauaa00u0c95u5630u0c55u5530u0f7buedf0u0f77uddf0u07ffuffc0u05bdu7e00u0eafud550u0eaau55d4u01a5ud7f4u0256ud5d4u02a5ud5d4u006aud554u0054u3ff0u0054u0000u0000u0000u002aua140u00aaua9d0u02a5u5ad4u3295u56f5u3e5buedf5u0e77uddd7u03fdu7e5cu017du7a70u055fueac0u055aua900u0955u6600u0a56u5a00u1a7du6940u56bfua150u553fu0000nua000u00aau9540u0abau5550u2abdu5540u22bfu7ec1u025fu7dfdu0057uffc1u002aubfc1u01aaua95du056fuea5du055fuea51u055fua901u025au5501u0aaauaa00u0015u4000u0015u5000u0000u0000nua000u00aau9540u0abau5550u2abdu5540u22bfu7ec0u025fu7dfcu0057uffc4u002aubfc4u025aufd74u0155ufe74u0955ufa44u0a55ua904u16aau5584u15aauaa50u0540u0540u0000u0000u0002ua800u002aua550u00aeu9554u00afu5550u02afudfb0u0a97udf7fu0815ufff0u002auaffcu00a5u55fcu0095u55f0u0295u5580u0aa5u6a50u16aau9550u15aauaa94u0540u0055u002aua800u00aauaa00u0caauaa30u0eaauaab0u0daauaa70u0f5aua5f0u0356u95c0u0195u5640u016auaa50u0d6auaa50u0d6aua940u0f95u5680u02aauaa80u006aua540u0014u1540u0000u0500u0002uaa40nuaa8cu002auaa9cu032auaabcu03dauaa74uc0d5ua974u70e5u6594u5d59u5694u575auaa90u55dauaa90u157auaa60u05d5u55a4u03aauaaa5u016au8015u0550u0000u0550u0000u0002ua000u0003uf000u0002ua000u0023uf200u002auaa00u0001u5000u0001u5000u0001u5000u0001u5000u0001u5000u0001u5000u0001u5000u0001u5000u0001u5000u0001u5000u0000u4000uffffufdafuffffuf6abuff5au96bbufdaaub6aeuf5aaub6aeuf6aauadaeud6a9uadaaud569uadaaud669uadabud665uadabud665uadabud566ua9aaud96aua9aaufa56uaaaau555au9aabuf555u5555uffffufdafuffffuf6abuff59u96bbufda6ub6a6uf5a9ub69euf6aau6d6eud6a9u9d9aud569u6da6ud669u9d9bud665ua56bud665u9dabud566u69aaud969ua9aaufa56u6aaau555au9aabuf555u5555u0000u0410u0005u1450u0001u5550u0055uf554u405fufe54u401du5d59u5575u7595u557fuf556u557fuf556u5575u7595u401du5d59u405fufe54u0055uf554u0001u5550u0005u1450u0000u0410u0001u0104u0001u4514u0041u5550u0055uf554u041fufe54u051du5d59u0575u7595u057fuf556u057fuf556u0575u7595u051du5d59u041fufe54u0055uf554u0041u5550u0001u4514u0001u0104u0001u0000u0005u5000u0006u5400u0015u9400u0006u9000u0000u0000u000fuf000u000fuf000u000fuf000u008fuf000u008fuf000u008fuf000u008fuf000u008fuf000u00aauaa00u002aua800u0000u0000u0000u0000u0000u0000u0000u0000u0014u5000u0055u5400u0055u5400u0055u5400u0055u5400u0015u5000u0005u4000u0001u0000u0000u0000u0000u0000u0000u0000u0000u0000u0006ua000u001auac00u0068u1b00u0060u0700u0060u0700u006auaa00u0056uab00u0001u8000u0001u8000u0001u8000u0001u8000u0001uac00u0001ua800u0001ua000u0001ua800u0001u8000u0000u0a00u0000u0a00u0000u2a80u0000u2a80u0000uaaa0u0000uaaa0u0002uaaa8u0002uaaa8nuaaaanuaaaau0000u0000u0000u0000u0000u0000u0000u0000u0000u0000u0000u0000u7fffufffeu5fffufffbu57ffuffefu56aauaabfu56aauaabfu56a9u6abfu56a5u5abfu56a5u5abfu56a9u6abfu56a9u6abfu56a9u6abfu56a9u6abfu56aauaabfu5955u557fu6555u555fu9555u5557u5555u5555u5555u5555u5565u5565u5555u5555u5555u5555u5555u5555u5556u5556u5555u5555u5555u5555u5655u5655u5555u5555u5555u5555u5555u5555u5595u5595u5555u5555u5555u5555u6aaau6aaaueeaeueeaeueaaaueaaaueaaaueaaaueaaaueaaaueaaaueaaaueaaaueaaaueaaaueaaaueaaaueaaaueaaaueaaaueaaaueaaaueaaaueaaaueaaaueaaaueeaeueeaeueaaaueaaaufffdufffdu003fufc00u00beube00u00bfufe00u03f2u8fc0u03e2u8bc0u03eauabc0u03fbuefc0u03d6u97c0u0f95u56f0u0fa5u5af0u0f9eub6f0u0a55u55a0u0f55u55f0u0d55u5570u0d55u5570u1555u5554u4fffuffd4u4aaauaaa4u4aaauaaa4u4aaauaaa4u4fffuffd4u4aaauaaa4u4aaauaaa4u4aaauaaa4u4fffuffd4u4aaauaaa4u4aaauaaa4u4aaauaaa4u4fffuffd4u4aaauaaa4u4aaauaaa4u4aaauaaa4uffffu8003u8003u924bu0003u124bu0003u924bu8003u8003ubfffubfffubfffubfffubfffubfffu8003u8003u8003u8003uc003uc003uc003u8003u8003u8103u8403u1153u8403u8103uc003ufff7ufff7ufff7ufff7u0003u0403u1003u0403u1003u0403u0003uffefuffefuffefuffefu0003u1101u0401u1109u0401u1101u0003uffffuffffuffffuffffuffffuf3f3ue1e0u0000ue000ue003ue007uffffuffffuffffuffffu0003ufe01ufe01ufe01ufe01ufe01u0003uffffuffffuffffufe7fu0003ufff1ufff1ufff0ufff1ufff1u0003uc19fuffffuffffuffffuc000u8078u0700u0078u0700u8078uc000ucfffu4fffu4fffu4fffuc000uc100u8444u0100u0444u8100uc000uffffuffffuffffud001u17fduf501u0577ufd10uc1d7u5451ud7fdu1001uf7ffuf7ffuf7ffuf000uffffuffffuc3ffuc3ffuc3ffuc000uffffuffffuffffufe7fuc000u8003u0003u0003u0003u8003uc000uc00fuffffuffffuffffuc603u9029u0000u1028u0000u9029uc603ufe7fufe70ufe77ufe37ufe07ufe03ufe01u0000u0000uc003uc003uc003uffffuffffu8507ub574ua415ubff4u8101ubd7fua540uad5fu2040uffffuffffuffffue7c3ue7c3u67c1u0001u6001ue003ue003uffffuffffuffffuffffuc003uc001ud008uc000ud008uc001uc003uffffuffffuffffu8007u8003ua211u8004uaa50u8004ua211u8003uaa57uaa57uaa57uaa57u0003u2a41u0015u2a40u0014u2a41u0003uffffuffffuffffu87e1u8db1u8991u8991u8e71u83c1u83c1u8001u83c0uffffuffffu8001u8001u8841ua214u8840ua214u8841u8001uff7fuff7fuff7fu8a05uaafduaa01uabf5ub855uaadduaa45uaf75ua021ubdf7ubdf7u8101ubffdu8005ubff5ua411uaddfua101uaffdu8801ufdffufdffufdffuf03eue03cue238ue530ue200ue000uf030uffffuffff";

    int i;
    int j;
    int k;
    int x;
    int y;
    int z;
    int p;
    int q;
    int u;
    int r = 0;
    int s = 0;

    int counter = 0;

    int playerX = 0;
    int playerY = 0;
    int playerSprite = 0;
    int playerDirection = 0;
    int playerLastDirection = 0;
    int playerWalkCount = 0;
    int playerHealth = 0;
    int playerStunned = 0;

    int cameraX = 0;
    int targetCameraX = 0;
    int cameraY = 0;
    int targetCameraY = 0;
    int fadeIntensity = 255;
    int fadeDelta = 1;
 
    int attacking = 0;
    boolean attackReleased = true;
    boolean acquiredSword = false;
    boolean acquiredCandle = false;
    boolean acquiredKey = false;
    boolean scrolling = false;
    boolean flash = false;
    boolean fading = true;
    boolean won = false;

    int[][] map = new int[81][81];

    Random random = new Random();
    AffineTransform affineTransform = new AffineTransform();

    ArrayList<int[]> enemies = new ArrayList<int[]>();

    // create candle light
    BufferedImage candleLight = new BufferedImage(112, 112, 2);
    for(i = 0; i < 112; i++) {
      for(j = 0; j < 112; j++) {
        float X = 55.5f - j;
        float Y = 55.5f - i;
        float R2 = X * X + Y * Y;
        float F = R2 / 1600;        
        candleLight.setRGB(i, j, (R2 < 1600 ? (int)(255 * F * F) : 255) << 24);
      }
    }

    // decompress sprites
    int[] pixels = new int[16];
    int[] pixels2 = new int[16];
    BufferedImage[] sprites = new BufferedImage[64];
    for(i = 0; i < SPRITE_COUNT; i++) {
      sprites[i] = new BufferedImage(16, 16, 2);
      sprites[i + SPRITE_FLIPPED] = new BufferedImage(16, 16, 2);
      for(y = 0; y < 16; y++) {
        for(x = 0; x < 16; x++) {
          j = (S.charAt((x < 8 ? 1 : 0) + (y << 1) + (i << 5))
              >> ((x & 7) << 1)) & 3;
          pixels2[15 - x] = pixels[x] = j == 0 ? 0 : 0xFF000000
              | (i < SPRITE_ROCK ? (j == 1 ? 0x994E00 : j == 2
                      ? 0x88D800 : 0xEA9E22)
                  : i <= SPRITE_CRACKED_ROCK
                      ? (j == 1 ? 0 : j == 2 ? 0x994E00 : 0xF7D8A5)
                  : i < SPRITE_WATER
                      ? (j == 1 ? 0xB53120 : j == 2 ? 0xEA9E22 : 0xFFFFFF)
                  : i < SPRITE_BRIDGE
                      ? (j == 1 ? 0x4240FF : 0x0D9300)
                  : i < SPRITE_PRINCESS
                      ? (j == 1 ? 0x4240FF : j == 2 ? 0x994E00 : 0)
                  : i < SPRITE_LADDER
                      ? (j == 1 ? 0xB53120 : j == 2 ? 0xEA9E22 : 0x994E00)
                      : (j == 1 ? 0 : j == 2 ? 0x4240FF : 0x994E00));
        }
        sprites[i].setRGB(0, y, 16, 1, pixels, 0, 16);
        sprites[i + SPRITE_FLIPPED].setRGB(0, y, 16, 1, pixels2, 0, 16);
      }
    }

    // decompress map
    for(i = 0; i < 5; i++) {
      for(j = 0; j < 55; j++) {
        for(k = 0; k < 16; k++) {
          map[j][(i << 4) + k] = ((S.charAt(MAP_OFFSET + j + 55 * i) >> k) & 1)
              == 1 ? MAP_WALL : MAP_EMPTY;
        }
      }
    }

    // add items to map
    map[45][31] = MAP_SWORD;
    map[51][3] = MAP_CANDLE;
    map[14][51] = MAP_KEY;
    map[27][60] = MAP_KEY;
    map[17][43] = MAP_KEY;
    map[3][53] = MAP_KEY;
    map[34][3] = MAP_LOCK;
    map[5][29] = MAP_LOCK;
    map[4][43] = MAP_LOCK;
    map[8][70] = MAP_LOCK;
    map[3][77] = MAP_PRINCESS;
    map[27][15] = MAP_CRACKED_WALL;

    // add bodies of water and ladder
    for(i = 0; i < 23; i++) {
      if (i != 6) {
        map[i + 32][5] = map[i + 32][6] = MAP_WATER;
      }
    }
    for(i = 0; i < 3; i++) {
      for(j = 0; j < 7; j++) {
        map[i + 32][j + 7] = MAP_WATER;
      }
    }
    for(i = 0; i < 4; i++) {
      for(j = 0; j < 6; j++) {
        map[i + 25][j + 53] = MAP_WATER;
        map[j + 10][14] = MAP_LADDER;
      }
    }

    // add bridge
    map[38][5] = map[38][6] = MAP_BRIDGE;

    BufferedImage image = new BufferedImage(256, 240, 1);
    Graphics2D g = (Graphics2D)image.getGraphics();
    Graphics2D g2 = null;

    long nextFrameStartTime = System.nanoTime();
    while(true) {

      do {
        nextFrameStartTime += 16666667;

        // -- update starts ----------------------------------------------------

        if (fading) {
          fadeIntensity += fadeDelta;
          if (fadeIntensity > 255) {
            
            fadeIntensity = 255;
            fadeDelta = -8;

            // reset game
            playerX = 640;
            playerY = 792;
            playerSprite = SPRITE_LINK_UP_1;
            playerLastDirection = playerDirection = DIRECTION_UP;
            playerWalkCount = 1;
            playerHealth = 6;
            playerStunned = 0;
            attacking = 0;
            cameraX = targetCameraX = 512;
            cameraY = targetCameraY = 704;
            enemies.clear();
          } else if (fadeIntensity < 0) {
            fading = false;
          }
          continue;
        }

        counter++;

        if (!(attackReleased || a[VK_ATTACK])) {
          attackReleased = true;
        }

        // scroll camera when player moves off the screen
        // and push player fully onto new screen
        if (targetCameraX < cameraX) {
          cameraX -= 4;
          if ((playerX & 15) != 0) {
            playerX--;
          }
          continue;
        }
        if (targetCameraX > cameraX) {
          cameraX += 4;
          if ((playerX & 15) != 0) {
            playerX++;
          }
          continue;
        }
        if (targetCameraY < cameraY) {
          cameraY -= 4;
          if ((playerY & 15) != 0) {
            playerY--;
          }
          continue;
        }
        if (targetCameraY > cameraY) {
          cameraY += 4;
          if ((playerY & 15) != 0) {
            playerY++;
          }
          continue;
        }

        if (attacking > 0) {
          float angle = 1.57f * playerDirection;
          r = 8 + playerX + (int)(16 * Math.cos(angle));
          s = 8 + playerY + (int)(16 * Math.sin(angle));
          if (map[s >> 4][r >> 4] == MAP_CRACKED_WALL) {
            map[s >> 4][r >> 4] = MAP_EMPTY;
            int[] enemy = new int[32];
            enemy[ENEMY_X] = (r & ~15) + 8;
            enemy[ENEMY_Y] = (s & ~15) + 8;
            enemy[ENEMY_SPRITE] = SPRITE_OCTOROC_1;
            enemy[ENEMY_DYING] = 1;
            enemies.add(enemy);
          }
        }

        // update enemies
        for(i = enemies.size() - 1; i >= 0; i--) {
          int[] enemy = enemies.get(i);
          if (enemy[ENEMY_DYING] > 0) {
            if (++enemy[ENEMY_DYING] == 16) {
              // remove dead enemy after flashing
              enemies.remove(i);

              if (random.nextInt(7) == 0) {
                x = enemy[ENEMY_X] >> 4;
                y = enemy[ENEMY_Y] >> 4;
                if (map[y][x] == MAP_EMPTY) {
                  map[y][x] = MAP_HEART;
                }
              }
            }
          } else {
            if ((counter & 7) == 0) {
              enemy[ENEMY_SPRITE] = enemy[ENEMY_SPRITE] != SPRITE_OCTOROC_1
                  ? SPRITE_OCTOROC_1 : SPRITE_OCTOROC_2;
            }
            if ((counter & 1) == 0) {
              x = enemy[ENEMY_X];
              y = enemy[ENEMY_Y];
              if (enemy[ENEMY_DIRECTION] == DIRECTION_UP) {
                y--;
              } else if (enemy[ENEMY_DIRECTION] == DIRECTION_DOWN) {
                y++;
              } else if (enemy[ENEMY_DIRECTION] == DIRECTION_LEFT) {
                x--;
              } else {
                x++;
              }
              if (map[(y - 8) >> 4][x >> 4] == MAP_EMPTY
                  && map[(y + 7) >> 4][x >> 4] == MAP_EMPTY
                  && map[y >> 4][(x + 7) >> 4] == MAP_EMPTY
                  && map[(y - 8) >> 4][(x - 8) >> 4] == MAP_EMPTY
                  && x >= cameraX + 8 && x < cameraX + 248
                  && y >= cameraY + 8 && y < cameraY + 168) {
                enemy[ENEMY_X] = x;
                enemy[ENEMY_Y] = y;
              } else {
                enemy[ENEMY_DIRECTION] = random.nextInt(4);
                enemy[ENEMY_COUNTER] = random.nextInt(128);
              }
              if (--enemy[ENEMY_COUNTER] < 0 && (y & 15) == 8 && (x & 15) == 8) {
                enemy[ENEMY_DIRECTION] = random.nextInt(4);
                enemy[ENEMY_COUNTER] = random.nextInt(128);
              }

              if (attacking > 0) {
                x -= r;
                y -= s;
                if (x * x + y * y < 128) {
                  // enemy begins to flash into nonexistence
                  ++enemy[ENEMY_DYING];
                  continue;
                }
              }

              // test for collision between enemy and player
              if (playerStunned == 0 && attacking == 0
                  && playerX <= enemy[ENEMY_X] + 4
                  && playerX >= enemy[ENEMY_X] - 20
                  && playerY <= enemy[ENEMY_Y] + 4
                  && playerY >= enemy[ENEMY_Y] - 20) {
                // player injured and stunned
                playerStunned = 64;
                if (--playerHealth == 0) {
                  // player died
                  fading = true;
                  fadeIntensity = 0;
                  fadeDelta = 2;
                }
              }

              if (enemy[ENEMY_X] < cameraX
                  || enemy[ENEMY_X] >= cameraX + 256
                  || enemy[ENEMY_Y] < cameraY
                  || enemy[ENEMY_Y] >= cameraY + 176) {
                // remove enemies that are out of bounds
                enemies.remove(i);
              }
            }
          }
        }

        if (attacking > 0) {
          attacking--;
          if (playerDirection == DIRECTION_UP) {
            playerSprite = SPRITE_LINK_UP_LUNGE;
          } else if (playerDirection == DIRECTION_DOWN) {
            playerSprite = SPRITE_LINK_DOWN_LUNGE;
          } else if (playerDirection == DIRECTION_LEFT) {
            playerSprite = SPRITE_LINK_LEFT_LUNGE;
          } else {
            playerSprite = SPRITE_LINK_RIGHT_LUNGE;
          }
        } else if (acquiredSword && attackReleased && a[VK_ATTACK]) {
          attackReleased = false;
          attacking = 10;
          playerWalkCount = 0;
        } else {
          // player walks 1.5 pixels on average per step
          i = 1 + (counter & 1);
          x = playerX;
          y = playerY;
          if (playerStunned > 56) {
            if (playerDirection == DIRECTION_UP) {
              y += 4;
            } else if (playerDirection == DIRECTION_DOWN) {
              y -= 4;
            } else if (playerDirection == DIRECTION_LEFT) {
              x += 4;
            } else {
              x -= 4;
            }
          } else if (a[VK_UP]) {
            playerWalkCount++;
            y -= i;
            playerDirection = DIRECTION_UP;
          } else if (a[VK_DOWN]) {
            playerWalkCount++;
            y += i;
            playerDirection = DIRECTION_DOWN;
          } else if (a[VK_LEFT]) {
            playerWalkCount++;
            x -= i;
            playerDirection = DIRECTION_LEFT;
          } else if (a[VK_RIGHT]) {
            playerWalkCount++;
            x += i;
            playerDirection = DIRECTION_RIGHT;
          }
          if (playerStunned > 0) {
            playerStunned--;
          }

          if ((playerWalkCount & 7) == 0
              || playerLastDirection != playerDirection) {
            playerWalkCount++;
            playerLastDirection = playerDirection;

            // toggle walking sprite
            if (playerDirection == DIRECTION_UP) {
              playerSprite = playerSprite != SPRITE_LINK_UP_1
                  ? SPRITE_LINK_UP_1 : SPRITE_LINK_UP_2;
            } else if (playerDirection == DIRECTION_DOWN) {
              playerSprite = playerSprite != SPRITE_LINK_DOWN_1
                  ? SPRITE_LINK_DOWN_1 : SPRITE_LINK_DOWN_2;
            } else if (playerDirection == DIRECTION_LEFT) {
              playerSprite = playerSprite != SPRITE_LINK_LEFT_1
                  ? SPRITE_LINK_LEFT_1 : SPRITE_LINK_LEFT_2;
            } else {
              playerSprite = playerSprite != SPRITE_LINK_RIGHT_1
                  ? SPRITE_LINK_RIGHT_1 : SPRITE_LINK_RIGHT_2;
            }
          }

          // line up player to nearest 8 pixels in perpendular direction
          if (playerDirection == DIRECTION_UP
                || playerDirection == DIRECTION_DOWN) {
            i = x & 7;
            if (i != 0) {
              if (i < 4) {
                x--;
              } else {
                x++;
              }
            }
          } else {
            i = y & 7;
            if (i != 0) {
              if (i < 4) {
                y--;
              } else {
                y++;
              }
            }
          }

          // detect locks
          p = map[(y + 8) >> 4][x >> 4];
          q = map[(y + 15) >> 4][x >> 4];
          r = map[(y + 8) >> 4][(x + 14) >> 4];
          s = map[(y + 15) >> 4][(x + 14) >> 4];
          if (acquiredKey) {
            // lose key when a lock is unlocked
            if (p == MAP_LOCK) {
              map[(y + 8) >> 4][x >> 4] = MAP_EMPTY;
              acquiredKey = false;
            } else if (q == MAP_LOCK) {
              map[(y + 15) >> 4][x >> 4] = MAP_EMPTY;
              acquiredKey = false;
            } else if (r == MAP_LOCK) {
              map[(y + 8) >> 4][(x + 14) >> 4] = MAP_EMPTY;
              acquiredKey = false;
            } else if (s == MAP_LOCK) {
              map[(y + 15) >> 4][(x + 14) >> 4] = MAP_EMPTY;
              acquiredKey = false;
            }
          }

          // walls acts a barriers for the player
          if (p < MAP_WALL && q < MAP_WALL && r < MAP_WALL && s < MAP_WALL) {
            playerX = x;
            playerY = y;
          }

          // acquire items
          i = (y + 12) >> 4;
          j = (x + 8) >> 4;
          k = map[i][j];
          if (k != MAP_EMPTY && k < MAP_BRIDGE) {
            map[i][j] = MAP_EMPTY;
            if (k == MAP_SWORD) {
              acquiredSword = true;
            } else if (k == MAP_CANDLE) {
              acquiredCandle = true;
            } else if (k == MAP_KEY) {
              acquiredKey = true;
            } else if (k == MAP_PRINCESS) {
              won = true;
            } else if (playerHealth < 6) {
              playerHealth++;
            }
          }

          // compute where the camera should be
          targetCameraX = (playerX + 8) & ~0xFF;
          targetCameraY = ((playerY + 12) / 176) * 176;

          if (cameraX != targetCameraX || cameraY != targetCameraY) {
            if (!scrolling) {
              scrolling = true;

              // create new enemies
              for(i = 3; i >= 0; i--) {
                do {
                  x = targetCameraX + ((2 + random.nextInt(12)) << 4);
                  y = targetCameraY + ((2 + random.nextInt(7)) << 4);
                } while(map[(y >> 4)][(x >> 4)] != MAP_EMPTY);
                int[] enemy = new int[32];
                enemy[ENEMY_X] = x + 8;
                enemy[ENEMY_Y] = y + 8;
                enemy[ENEMY_SPRITE] = SPRITE_OCTOROC_1;
                enemy[ENEMY_DIRECTION] = random.nextInt(4);
                enemy[ENEMY_COUNTER] = random.nextInt(128);
                enemies.add(enemy);
              }
            }
          } else {
            scrolling = false;
          }
        }

        // -- update ends ------------------------------------------------------

      } while(nextFrameStartTime < System.nanoTime());

      // -- render starts ------------------------------------------------------

      if (won) {
        // draw ending
        g.setColor(Color.BLACK);
        g.fillRect(0, 0, 256, 240);
        g.drawImage(sprites[SPRITE_TRIFORCE], 123, 84, null);
        g.drawImage(sprites[SPRITE_TRIFORCE], 118, 94, null);
        g.drawImage(sprites[SPRITE_TRIFORCE], 128, 94, null);
        g.drawImage(sprites[SPRITE_PRINCESS], 112, 112, null);
        g.drawImage(sprites[SPRITE_LINK_DOWN_1], 128, 112, null);
      } else {

        // draw ground
        g.setColor(COLOR_FLOOR);
        g.fillRect(0, 64, 256, 176);

        // draw map
        x = cameraX >> 4;
        y = cameraY >> 4;
        r = playerX >> 4;
        s = playerY >> 4;
        z = cameraX & 15;
        k = cameraY & 15;
        for(i = 0; i < 12; i++) {
          for(j = 0; j < 17; j++) {
            p = j + x;
            q = i + y;
            if (((p >= 64 && q >= 22) || (p >= 16 && p < 64 && q < 11))
                  && (!acquiredCandle
                      || (p < r - 2 || p > r + 3 || q < s - 2 || q > s + 3))) {
              g.setColor(Color.BLACK);
              g.fillRect((j << 4) - z, (i << 4) - k + 64, 16, 16);
            } else if ((u = map[q][p]) != MAP_EMPTY) {
              g.drawImage(sprites[
                  u == MAP_WALL ? SPRITE_ROCK
                      : u == MAP_WATER ? SPRITE_WATER
                      : u == MAP_BRIDGE ? SPRITE_BRIDGE
                      : u == MAP_LADDER ? SPRITE_LADDER
                      : u == MAP_HEART ? SPRITE_HEART
                      : u == MAP_LOCK ? SPRITE_LOCK
                      : u == MAP_KEY ? SPRITE_KEY
                      : u == MAP_CRACKED_WALL ? SPRITE_CRACKED_ROCK
                      : u == MAP_SWORD ? SPRITE_SWORD
                      : u == MAP_CANDLE ? SPRITE_CANDLE
                      : SPRITE_PRINCESS],
                  (j << 4) - z, (i << 4) - k + 64, null);
            }
          }
        }

        if (attacking > 0) {
          // draw sword
          i = playerX - cameraX + 8;
          j = playerY - cameraY + 72;
          float angle = 1.57f * (playerDirection - 1);
          if (playerDirection == DIRECTION_UP) {
            i -= 2;
          } else if (playerDirection == DIRECTION_DOWN) {
            i += 2;
          } else if (playerDirection == DIRECTION_LEFT) {
            j += 2;
          } else {
            j++;
          }
          g.translate(i, j);
          g.rotate(angle);
          g.drawImage(sprites[SPRITE_SWORD], -8,
              attacking <= 4 ? (attacking << 1) - 4 : 4, null);
          g.setTransform(affineTransform);
        }

        flash = !flash;
        if (flash || playerStunned == 0) {
          // draw player
          g.drawImage(sprites[playerSprite],
              playerX - cameraX, playerY - cameraY + 64, null);
        }

        if ((x >= 64 && y >= 22) || (x >= 16 && x <= 48 && y == 0)) {
          // draw candle light
          g.drawImage(candleLight, playerX - cameraX - 48,
              playerY - cameraY + 16, null);
        }

        // draw enemies
        for(i = enemies.size() - 1; i >= 0; i--) {
          int[] enemy = enemies.get(i);
          g.translate(enemy[ENEMY_X] - cameraX, enemy[ENEMY_Y] - cameraY + 64);
          if (enemy[ENEMY_DYING] > 0) {
            j = enemy[ENEMY_DYING] < 8 ? enemy[ENEMY_DYING]
                : 15 - enemy[ENEMY_DYING];
            g.setColor(enemy[ENEMY_DYING] < 8 ? Color.WHITE : COLOR_RED);
            g.drawLine(-j, -j, j, j);
            g.drawLine(-j, j, j, -j);
            g.drawLine(0, -j, 0, j);
            g.drawLine(-j, 0, j, 0);
          } else {
            g.rotate(1.57f * enemy[ENEMY_DIRECTION]);
            g.drawImage(sprites[enemy[ENEMY_SPRITE]], -8, -8, null);
          }
          g.setTransform(affineTransform);
        }

        // draw heads-up display (HUD)
        g.setColor(Color.BLACK);
        g.fillRect(0, 0, 256, 64);

        // draw mini-map
        g.setColor(COLOR_MAP_1);
        g.fillRect(16, 24, 64, 32);
        g.setColor(COLOR_MAP_2);
        g.fillRect(15 + ((cameraX + 128) / 20),
            23 + (((cameraY + 88) << 1) / 55), 3, 3);

        if (acquiredSword) {
          // draw sword in HUD
          g.drawImage(sprites[SPRITE_SWORD], 176, 24, null);
        }
        if (acquiredCandle) {
          // draw candle in HUD
          g.drawImage(sprites[SPRITE_CANDLE], 192, 24, null);
        }
        if (acquiredKey) {
          // draw candle in HUD
          g.drawImage(sprites[SPRITE_KEY], 208, 24, null);
        }

        // draw health hearts
        for(i = 0; i < playerHealth; i++) {
          g.drawImage(sprites[SPRITE_HEART], 171 + (i << 3), 44, null);
        }

        if (fading) {
          // draw fade
          g.setColor(new Color(0, 0, 0, fadeIntensity));
          g.fillRect(0, 0, 256, 240);
        }
      }

      // -- render ends --------------------------------------------------------

      // show the hidden buffer
      if (g2 == null) {
        g2 = (Graphics2D)getGraphics();
        requestFocus();
      } else {
        g2.drawImage(image, 0, 0, 512, 480, null);
      }

      // burn off extra cycles
      while(nextFrameStartTime - System.nanoTime() > 0) {
        Thread.yield();
      }
    }
  }

  @Override
  public void processKeyEvent(KeyEvent keyEvent) {
    final int VK_LEFT = 0x25;
    final int VK_RIGHT = 0x27;
    final int VK_UP = 0x26;
    final int VK_DOWN = 0x28;
    final int VK_ATTACK = 0x42;
    final int VK_W = 0x57;
    final int VK_S = 0x53;
    final int VK_A = 0x41;
    final int VK_D = 0x44;

    int k = keyEvent.getKeyCode();
    if (k > 0) {
      k = k == VK_W ? VK_UP : k == VK_D ? VK_RIGHT : k == VK_A ? VK_LEFT
          : k == VK_S ? VK_DOWN : k;
      a[(k >= VK_LEFT && k <= VK_DOWN) ? k : VK_ATTACK]
          = keyEvent.getID() != 402;
    }
  }

  // to run in window, uncomment below
  /*public static void main(String[] args) throws Throwable {
    javax.swing.JFrame frame = new javax.swing.JFrame("Legend of Zelda 4K");
    frame.setDefaultCloseOperation(javax.swing.JFrame.EXIT_ON_CLOSE);
    a applet = new a();
    applet.setPreferredSize(new java.awt.Dimension(512, 480));
    frame.add(applet, java.awt.BorderLayout.CENTER);
    frame.setResizable(false);
    frame.pack();
    frame.setLocationRelativeTo(null);
    frame.setVisible(true);
    Thread.sleep(250);
    applet.start();
  }*/
}

