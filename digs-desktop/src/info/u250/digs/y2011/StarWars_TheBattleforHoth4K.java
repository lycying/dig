package info.u250.digs.y2011;

/*
 * Star Wars: The Battle for Hoth 4K
 * Copyright (C) 2011 meatfighter.com
 *
 * This file is part of Star Wars: The Battle for Hoth 4K.
 *
 * Star Wars: The Battle for Hoth 4K is free software; you can redistribute it and/or modify
 * it under the terms of the GNU Lesser General Public License as published
 * by the Free Software Foundation; either version 3 of the License, or
 * (at your option) any later version.
 *
 * Star Wars: The Battle for Hoth 4K is distributed in the hope that it will be useful,
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

public class StarWars_TheBattleforHoth4K extends Applet implements Runnable {

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

    final int LASER_X = 0;
    final int LASER_Y = 1;
    final int LASER_Z = 2;
    final int LASER_RX = 3;
    final int LASER_RY = 4;
    final int LASER_RZ = 5;
    final int LASER_COUNT = 6;
    final int LASER_TYPE = 7;

    final int LASER_TYPE_PLAYER = 0;
    final int LASER_TYPE_ENEMY = 1;

    final int SPRITE_X = 0;
    final int SPRITE_Y = 1;
    final int SPRITE_Z = 2;
    final int SPRITE_WIDTH = 3;
    final int SPRITE_HEIGHT = 4;
    final int SPRITE_INDEX = 5;
    final int SPRITE_DRAWN = 6;
    final int SPRITE_ENEMY_INDEX = 7;
    final int SPRITE_PALETTE = 8;

    final int SPRITE_ATAT = 0;
    final int SPRITE_ATST = 1;

    final int ENEMY_TYPE_ATAT = 0;
    final int ENEMY_TYPE_ATST = 1;
    final int ENEMY_TYPE_EXPLOSION = 2;

    final int ENEMY_X = 0;
    final int ENEMY_Z = 1;
    final int ENEMY_TYPE = 2;
    final int ENEMY_POWER = 3;
    final int ENEMY_Y = 4;
    final int ENEMY_RX = 5;
    final int ENEMY_RY = 6;
    final int ENEMY_RZ = 7;

    final int SCREEN_SIZE = 362;
    final int HALF_SCREEN_SIZE = SCREEN_SIZE / 2;
    final int QUARTER_SCREEN_SIZE = SCREEN_SIZE / 4;
    final int SCREEN_DISTANCE = SCREEN_SIZE;
    final int SKY_Y = 8192;
    final int MIN_Y = HALF_SCREEN_SIZE;
    final int MAX_Y = SKY_Y / 2;
    final int LASER_SPEED = 512;
    final int LASER_LENGTH = 1024;
    final int LASER_WIDTH = 32;
    final int LASER_HALF_WIDTH = LASER_WIDTH / 2;
    final int LASER_DELAY = 8;
    final int LASER_MAX_COUNT = 64;
    final int HIGH_SPEED = 32;
    final int LOW_SPEED = 16;
    final float MAX_YAW_VELOCITY = 0.02f;
    final float YAW_ACCELERATION = 0.0005f;
    final int MAX_HEIGHT_VELOCITY = 32;
    final int HEIGHT_ACCELERATION = 1;
    final float ROLL_SCALE = -1f / MAX_YAW_VELOCITY;
    final int MAX_BATTLE_DISTANCE_2 = 268435456;
    final int MAX_LEVEL = 6;

    final String S = "u0000u0000u0000u0000u0000u0000u0000u0000u0000u0000u0000u4000u4000ud000ud000ud000u5000u5400u4000u0000u0000u4000u0000u0000u0000u0000u0000u0000u0000u0000u0000u0000u0000u0000u0000u0000u0000u0000u0000u0000u0000u0000u0000u0000u0000u0000u0000u0000u0000u0000u0000u0000u0000u0000u0000u0000u0000u0000u0000u0000u0000u0000u0000u0000u555cub7a9ua9aeu69a5uda65uf95bub5bbu6a6du5eedu5599u5555u5454u0005u0000u0000u0000u0000u0000u0000u0000u0000u0000u0000u0000u0000u0000u0000u0000u0000u0000u0000u0000u0000u0000u0000u0000u0000u0000u0000u0000u0000u0000u0000u0000u0000u0000u0000u0000u0000uc000u4000u4000u7000u6000ud010ud436ud91aud7d5ub5bfub767ub679ub559ub6d5u5559u0d55u4b6au4015u4000u0000u0000u0000u0000u0000u0000u0000u0000u0000u0000u0000u0000u0000u0000u0000u0000u0000u0000u0000u4000u4000u4000u4000u4000ud000ua000ud800u7800u9400ufc00ub400ua500u0100u0000u6c00ue568ubfe5uaabfuaaabuaffbu9abbu9abau9abau9abau956auaaaaubabbuaaaau9a99uaaaau5555u5555u5665u5655u5ed5u6a55u5a56u7559u1557u1955u1990u1590u1994u5994u7654u75f5u57a9u57a9u55a5u5555u5559u5599u5599u5565u5766u5766u976au5955u597du55a9u5e9au565fu5675u555au55afu56aau1565u0005u555cufff5ubbb7u99b6uaab6u5ab6ufeb6u5ab6uaab6u5ab6ufeb6u5adauaadauaadauaadauaadauffdauaad5u6f55u6a55u6f55ubd5au5555u155au1a95u0540u0000u0000u0000u0000u0000u0000u0000u0000u0000u0000u0000u0000u0000u0000u0001u0001u0001u0001u0001u0005u0015u001du0015u0015u0029u0055u0055u0000u0000u5555uffffueebeu6696uaaaauaa55uabffuaa55uaaaauaa55ubbffua955ubaeaua9aaubaaauaaaauafffuaaaau7e59u6a59u7e59u5eaau5555u9560u5a90u0100u0000u0000u0000u0000u0000u0000u0000u0000u0000u0000u0000u0000u0000u0000u0000u0000u0000u0000u0000u0000u0000u0000u0000u0000u0000u6000u5000u0000u0000u700dud397u5556uaa96uaa56uaa96u5a56u9a96u9a56u9a96uea96uaa95uaaa5ufa95ubaa5u6a95uaaa5u5555u5555u6555u6555ued69uaa55u5a56u7556u5a64u6690u6690u6650u6550u66e4u6678u5964uf554uad50uad50ua550u5550u5550u6550u6550u9550u9550u9650u9564u5564ud555ud55du6555u6555u6555u5555ud595u9000u5400u0035u015bu0015u00d5u00aau006au036au026bu01abu01abu0dabu1feau1aaau1aafu1a9au1695u06aau0555u0015u0056u0055u0055u0017u0000u0000u0000u0000u0000u0000u0000u0001u0001u0001u0005u0006u0006u0005u0005u0006u0006u0006u0005u0019u0019u0019u0015u006du015au0675u19dfu15afu1ff5u5bafu555au0155u0000u0000u0000u0000u0000u0000u0000u0000u0000u0000u0000u0000u002auaa7fu556au0015u0000u0000u0000u0000u0000u0000u0000u0000u0000u0000u0000u0000u0000u0000u0000u0000u0000u0000u0000u0000u0000u0000u0000u0000u0000u0000u0000u0000u0000u0000u0000u0000u0000u0000u0000u0000u0000u0000u0000u0000u0000u0000u0000u0000u0000u0000u0000u0000u0000u0000u0000ua000ua800uee00uf780ubbe0ubae0ue7f8ub6f8udbf8u6efeubbfdueffdubf5du7dadu1eadu1fadu07fdu07d4u0140u0000u0000u0000u0000u0000u0000u0000u0000u0000u0000u0000u0000u0000u0000u0000u0000u0000u0000u0000u0000u0000u0000u0000u0000u0000u0000u0000u0000u0000u0000u0000u0000u0000u0000u0000u0000u0000u9000u5400u0000u0000u0000ua800ufea0ueafau76aaudbafu6fafu6effu5bbau6efau5aaeu66abu999aua666ua955ueaaau5555u1554u1554u5554u5550u5540u9900u5500ue900ube40uef90uef90udf90u9f50u7d00u9400u4000u0000u0000u0000u0000u0000u0000u0000u0000u0000u0000u0000u0000u0000u0000u0000u0000u0000u0000u0000u0000u0000u0000u4000u5400ua900u9b90uaab9u6aebu5555u0000u0000u0000u02aau0bffu2fffu2abfubfa9u7ff6ubff6u7ff6ubff6u7ea6uaa56u55aeu6abbu6eefu17bbu0155u0000u0000u0000u0000u0000u0001uafa9ud69eua7f7udffdudf7dudeedue7f7uf95auaaa5u5555u9954u6550u9500u5000u4000u0000u0000u0000u0000u4000u4000u4000u4000u5000u9000u5000u9000u6400u9400u6540u97d0u6ab5u156bu0016u0001u0001u0001u0001u0001u0000u0000u0000u0000u0000u0000u0000u0000u0000u0000u0000u0000u0000u0000u0000u0000u0000u0000u0000u0000u0000u0000u0000u0000u0000u0002u002fu001du005bu01bfu17ffu6fffuffffubffeu7fa5u5905u001au0156u01a9u0199u05a9u0565u05a9u05a9u016au006au006au001au0016u0005u0006u0005u0001u0015u006au0016u0000u0000u0000u0000u0000u0000u0000u0000u0000u0000u0000u0000u0000u0000u0000u0000u0000u0000u0000u0000u0000u0000u0000u0000u0000u0000u0000u0000u0000u0000u0000u0000u0000u0000u0000u0000u0000u0000u0000u0000u0016u007fu017bu067fu0bd9u1ff4ubff8ubfe0uff40ufd00uf400ud000u8000u0000u0000u0000u0000u0000u0000u0000u0000u0000u0000u0000u0000u0000u0000u0000u0000u0000u0000u0000ufa00uff80u9940u0000u0000u0000u0000u0000u0000u0000u0000u0000u0000u0000u0000u0000u0000u0000u0000u0000u0000u0000u0000u0000u0000u0000u0000u0000u0000u0000u0000u0000u0000u0000u0000u0000u0000u0000u0000u0000u0002u008bu02cbu02efu02efu02eeu03edu03b8u03b8u0ba8u0ba0u0be0u07e0u07e0u0b80u0b80u0f80u2e80u0680u0580u0740u1fd0u2edau1fffu16efu6bbbu1666";

    int i;
    int j;
    int k;
    int x;
    int y;
    int z;
    int u;
    int v;
    int w;
    int p;
    int q;

    int laserDelay = 0;
    int spriteCount = 0;

    int enemyHitSpriteIndex = 0;
    int enemyHitPower = 0;
    int enemyHitCountdown = 0;
    int enemyLaserDelay = 0;

    int lastEnemyDrawn = 0;

    int fadeIntensity = 255;
    int level = 1;

    int playerPower = 0;
    boolean dying = false;
    boolean fading = true;
    boolean fadingIn = false;
    boolean paused = true;
    float xo = 0;
    float yo = 0;
    float zo = 0;
    float yaw = 0;
    float roll = 0;

    float yawVelocity = 0;
    float heightVelocity = 0;

    float yawCos = 0;
    float yawSin = 0;

    float X;
    float Y;
    float Z;

    ArrayList<float[]> lasers = new ArrayList<float[]>();
    ArrayList<int[]> enemies = new ArrayList<int[]>();

    AffineTransform transform = new AffineTransform();
    BufferedImage image = new BufferedImage(362, 362, 1);
    BufferedImage image2 = new BufferedImage(512, 512, 1);
    Graphics2D g = (Graphics2D)image.getGraphics();
    Graphics2D g2 = (Graphics2D)image2.getGraphics();
    Graphics2D g3 = null;

    int[] pixels = new int[362];
    int[] xPoints = new int[4];
    int[] yPoints = new int[4];
    
    Random random = new Random();

    int[][] floorTexture = new int[256][256];
    int[][] skyTexture = new int[256][256];
    int[][] scaledSprites = new int[4096][9];

    BufferedImage[] sprites = new BufferedImage[2];

    Color[] firePalette = new Color[32];

    // generate fire palette
    for(i = 0; i < 32; i++) {
      firePalette[i] = new Color(
          255 - ((255 * i) >> 5),
          80 - ((80 * i) >> 5),
          0,
          255 - (i << 3));
    }    

    // decompress sprites
    for(k = 0; k < 2; k++) {
      int width = 8;
      int height = 55;
      if (k == 1) {
        width = 7;
        height = 64;
      }
      sprites[k] = new BufferedImage(width << 3, height, 2);
      for(x = 0; x < width; x++) {
        for(y = 0; y < height; y++) {
          for(i = 0; i < 8; i++) {
            j = (S.charAt((k == 0 ? 0 : 440) + height * x + y) >> (i << 1)) & 3;
            pixels[i] = j == 0 ? 0 : 0xFF000000
                | (j == 1 ? 0 : j == 2 ? 0x7F7F7F : 0xBFBFBF);
          }
          sprites[k].setRGB(x << 3, y, 8, 1, pixels, 0, 8);
        }
      }
    }

    // generate floor texture    
    for(y = 0; y < 256; y++) {
      for(x = 0; x < 256; x++) {
        floorTexture[y][x] = 128;
      }
    }
    for(z = 0; z < 8; z++) {
      for(y = 0; y < 256; y++) {
        for(x = 0; x < 256; x++) {
          floorTexture[y][x] += random.nextInt(1 << (8 - (z >> 2)))
              - (1 << (7 - (z >> 2)));
          if (floorTexture[y][x] < 0) {
            floorTexture[y][x] = 0;
          } else if (floorTexture[y][x] > 255) {
            floorTexture[y][x] = 255;
          }
        }
      }
      for(y = 0; y < 256; y++) {
        for(x = 0; x < 256; x++) {
          k = 0;
          for(i = -1; i <= 1; i++) {
            for(j = -1; j <= 1; j++) {
              k += floorTexture[(y + i) & 0xFF][(x + j) & 0xFF];
            }
          }
          floorTexture[y][x] = k / 9;
        }
      }
    }
    for(y = 0; y < 256; y++) {
      for(x = 0; x < 256; x++) {
        i = floorTexture[y][x];
        floorTexture[y][x] = ((0xF7 + (0x9D - 0xF7) * i / 255) << 16)
            | ((0xF8 + (0xA4 - 0xF8) * i / 255) << 8)
            | (0xFD + (0xD8 - 0xFD) * i / 255);
        skyTexture[y][x] = ((0x84 + (0xBE - 0x84) * i / 255) << 16)
            | ((0xB5 + (0xDE - 0xB5) * i / 255) << 8)
            | (0xF7 + (0xF3 - 0xF7) * i / 255);
      }
    }

    long nextFrameStartTime = System.nanoTime();
    while(true) {

      do {
        nextFrameStartTime += 16666667;

        // -- update starts ----------------------------------------------------

        if (fading) {
          // update fade

          if (fadingIn) {
            fadeIntensity -= 4;
            if (fadeIntensity <= 0) {
              fadeIntensity = 0;
              fading = false;
            }
          } else if (dying) {
            fadeIntensity += 2;
          } else {
            fadeIntensity += 4;
          }
          if (fadeIntensity >= 255) {
            fadeIntensity = 255;

            if (level == MAX_LEVEL) {
              // kill screen
              continue;
            }

            fadingIn = true;
            dying = false;

            playerPower = 64;
            lastEnemyDrawn = 0;
            xo = 0;
            yo = MIN_Y;
            zo = 16384;
            yaw = 0;
            roll = 0;
            yawCos = 1;
            yawSin = 0;
            yawVelocity = 0;
            heightVelocity = 0;
            
            lasers.clear();
            enemies.clear();

            // reset game
            for(i = 0; i < (level << 2) + 3; i++) {
              X = random.nextFloat() * 6.28f;
              Y = random.nextFloat() * 16384;
              int[] atat = new int[8];
              atat[ENEMY_X] = (int)(Y * Math.cos(X));
              atat[ENEMY_Z] = (int)(Y * Math.sin(X));
              atat[ENEMY_TYPE] = random.nextInt(2);
              atat[ENEMY_POWER] = 64;
              enemies.add(atat);
            }

            nextFrameStartTime = System.nanoTime();
          }
          continue;
        }

        if (enemies.size() == 0) {
          // advance the level when there are no more enemies to kill
          level++;
          fading = true;
          fadingIn = false;
          continue;
        }

        // the game starts out paused
        // hit fire to begin
        if (paused) {
          if (a[VK_ATTACK]) {
            paused = false;
          } else {
            continue;
          }
        }

        if (enemyHitCountdown > 0) {
          enemyHitCountdown--;
        }

        yawCos = (float)Math.cos(yaw);
        yawSin = (float)Math.sin(yaw);

        if (enemyLaserDelay > 0) {
          enemyLaserDelay--;
        } else {
          enemyLaserDelay = LASER_DELAY;
          if (lastEnemyDrawn >= 0 && enemies.size() > 0) {
            // enemy shoots at player
            int[] enemy = enemies.get(lastEnemyDrawn);
            float[] laser = new float[8];
            laser[LASER_X] = enemy[ENEMY_X];
            laser[LASER_Y] = enemy[ENEMY_TYPE] == ENEMY_TYPE_ATAT ? 640 : 128;
            laser[LASER_Z] = enemy[ENEMY_Z];
            laser[LASER_RX] = random.nextInt(768) - 384 + xo - enemy[ENEMY_X];
            laser[LASER_RY] = random.nextInt(768) - 384 + yo - laser[LASER_Y];
            laser[LASER_RZ] = random.nextInt(768) - 384 + zo - enemy[ENEMY_Z];
            float mag = (float)Math.sqrt(laser[LASER_RX] * laser[LASER_RX]
                + laser[LASER_RY] * laser[LASER_RY]
                + laser[LASER_RZ] * laser[LASER_RZ]);
            laser[LASER_RX] /= mag;
            laser[LASER_RY] /= mag;
            laser[LASER_RZ] /= mag;
            laser[LASER_TYPE] = LASER_TYPE_ENEMY;
            lasers.add(laser);
          }
        }

        if (laserDelay > 0) {
          k = LOW_SPEED;
          laserDelay--;
        } else {
          k = HIGH_SPEED;
          if (a[VK_ATTACK]) {
            // player shoots dual lasers
            laserDelay = LASER_DELAY;
            float[] laser = new float[8];
            laser[LASER_RX] = yawSin;
            laser[LASER_RZ] = -yawCos;
            laser[LASER_X] = xo - QUARTER_SCREEN_SIZE * yawCos;
            laser[LASER_Y] = yo - HALF_SCREEN_SIZE;
            laser[LASER_Z] = zo - QUARTER_SCREEN_SIZE * yawSin;
            lasers.add(laser);
            laser = new float[8];
            laser[LASER_RX] = yawSin;
            laser[LASER_RZ] = -yawCos;
            laser[LASER_X] = xo + QUARTER_SCREEN_SIZE * yawCos;
            laser[LASER_Y] = yo - HALF_SCREEN_SIZE;
            laser[LASER_Z] = zo + QUARTER_SCREEN_SIZE * yawSin;
            lasers.add(laser);
          }
        }

        // player moves forward
        xo += k * yawSin;
        zo -= k * yawCos;

        yaw += yawVelocity;
        roll = ROLL_SCALE * yawVelocity;

        if (xo * xo + zo * zo > MAX_BATTLE_DISTANCE_2) {
          // autopilot takes control and turns the player back into battle
          //    if the player leaves the battle area

          X = yaw + 0.01f;
          Y = yaw - 0.01f;

          if (zo * Math.cos(X) - xo * Math.sin(X)
                > zo * Math.cos(Y) - xo * Math.sin(Y))  {
            if (yawVelocity < MAX_YAW_VELOCITY) {
              yawVelocity += YAW_ACCELERATION;
            }
          } else {
            if (yawVelocity > -MAX_YAW_VELOCITY) {
              yawVelocity -= YAW_ACCELERATION;
            }
          }
        } else {

          // autopilot off

          // player controls yaw
          if (a[VK_LEFT]) {
            if (yawVelocity > -MAX_YAW_VELOCITY) {
              yawVelocity -= YAW_ACCELERATION;
            }
          } else if (a[VK_RIGHT]) {
            if (yawVelocity < MAX_YAW_VELOCITY) {
              yawVelocity += YAW_ACCELERATION;
            }
          } else if (yawVelocity > YAW_ACCELERATION) {
            yawVelocity -= YAW_ACCELERATION;
          } else if (yawVelocity < -YAW_ACCELERATION) {
            yawVelocity += YAW_ACCELERATION;
          } else {
            yawVelocity = 0;
          }

          // player controls altitude
          if (a[VK_DOWN]) {
            if (heightVelocity > -MAX_HEIGHT_VELOCITY) {
              heightVelocity -= HEIGHT_ACCELERATION;
            }
          } else if (a[VK_UP] && yo < MAX_Y) {
            if (heightVelocity < MAX_HEIGHT_VELOCITY) {
              heightVelocity += HEIGHT_ACCELERATION;
            }
          } else if (heightVelocity > HEIGHT_ACCELERATION) {
            heightVelocity -= HEIGHT_ACCELERATION;
          } else if (heightVelocity < -HEIGHT_ACCELERATION) {
            heightVelocity += HEIGHT_ACCELERATION;
          } else {
            heightVelocity = 0;
          }

          yo += heightVelocity;
          if (yo > MAX_Y) {
            yo = MAX_Y;
            heightVelocity = 0;
          } else if (yo < MIN_Y) {
            yo = MIN_Y;
            heightVelocity = 0;
          }
        }

        // update lasers
        for(i = lasers.size() - 1; i >= 0; i--) {
          float[] laser = lasers.get(i);
          laser[LASER_X] += LASER_SPEED * laser[LASER_RX];
          laser[LASER_Y] += LASER_SPEED * laser[LASER_RY];
          laser[LASER_Z] += LASER_SPEED * laser[LASER_RZ];

          if (laser[LASER_TYPE] == LASER_TYPE_ENEMY) {
            X = xo - laser[LASER_X];
            Y = yo - laser[LASER_Y];
            Z = zo - laser[LASER_Z];

            if (X * X + Y * Y + Z * Z < 32768 && --playerPower <= 0) {
              dying = true;
              fading = true;
              fadingIn = false;
            }
          }

          if (++laser[LASER_COUNT] > LASER_MAX_COUNT
              || laser[LASER_Y] < 0 || laser[LASER_Y] > SKY_Y) {
            lasers.remove(i);
          }
        }

        // update enemies
        outter2: for(j = enemies.size() - 1; j >= 0; j--) {
          int[] enemy = enemies.get(j);
          if (enemy[ENEMY_TYPE] == ENEMY_TYPE_EXPLOSION) {
            // animate explosion
            if (++enemy[ENEMY_POWER] >= 32) {
              enemies.remove(j);
            } else {
              enemy[ENEMY_X] += enemy[ENEMY_RX];
              enemy[ENEMY_Y] += enemy[ENEMY_RY];
              enemy[ENEMY_Z] += enemy[ENEMY_RZ];
            }
          } else {
            // test for collision between player and enemy
            X = enemy[ENEMY_X] - xo;
            Z = enemy[ENEMY_Z] - zo;

            float xe = yawCos;
            float ze = yawSin;
            float dot = X * xe + Z * ze; // x-coordinate
            xe *= dot;
            ze *= dot;
            float x2 = X - xe;
            float z2 = Z - ze;
            
            if (x2 * x2 + z2 * z2 < 1024) {
              // player is very close to enemy surface; execute collision test
              if (enemy[ENEMY_TYPE] == ENEMY_TYPE_ATAT) {
                u = 16;
                v = 40;
                w = 53;
                p = 64;
                q = 55;
              } else {
                u = 4;
                v = 36;
                w = 59;
                p = 56;
                q = 64;
              }
              x = (int)(v - dot / u);
              y = (int)(w - yo / u);

              // pixel test on sprite
              if (x >= 0 && y >= 0 && x < p && y < q
                  && (enemy[ENEMY_TYPE] == ENEMY_TYPE_ATST
                      || sprites[SPRITE_ATAT].getRGB(x, y) != 0)) {
                dying = true;
                fading = true;
                fadingIn = false;
              }
            }

            // test for collisions between laser and enemy
            outter: for(i = lasers.size() - 1; i >= 0; i--) {
              float[] laser = lasers.get(i);
              if (laser[LASER_TYPE] == LASER_TYPE_PLAYER) {
                for(k = 0; k < 512; k += 32) {
                  X = enemy[ENEMY_X] - (laser[LASER_X] + k * laser[LASER_RX]);
                  Z = enemy[ENEMY_Z] - (laser[LASER_Z] + k * laser[LASER_RZ]);

                  xe = yawCos;
                  ze = yawSin;
                  dot = X * xe + Z * ze; // x-coordinate
                  xe *= dot;
                  ze *= dot;
                  x2 = X - xe;
                  z2 = Z - ze;

                  if (x2 * x2 + z2 * z2 < 1024) {
                    // laser is very close to enemy surface
                    // execute collision test
                    if (enemy[ENEMY_TYPE] == ENEMY_TYPE_ATAT) {
                      u = 16;
                      v = 40;
                      w = 53;
                      p = 64;
                      q = 55;
                    } else {
                      u = 4;
                      v = 36;
                      w = 59;
                      p = 56;
                      q = 64;
                    }
                    x = (int)(v - dot / u);
                    y = (int)(w - laser[LASER_Y] / u);

                    // pixel test on sprite
                    if (x >= 0 && y >= 0 && x < p && y < q
                        && (enemy[ENEMY_TYPE] == ENEMY_TYPE_ATST
                            || sprites[SPRITE_ATAT].getRGB(x, y) != 0)) {

                      lasers.remove(i);

                      enemy[ENEMY_POWER] -= 2;
                      if (enemy[ENEMY_POWER] <= 0) {
                        // big explosion
                        v = 128;
                        w = 45;
                        p = 22;
                      } else {
                        // tiny explosion
                        v = 8;
                        w = 9;
                        p = 5;
                      }

                      // generate explosions
                      for(u = 0; u < v; u++) {
                        int[] explosion = new int[8];
                        explosion[ENEMY_TYPE] = ENEMY_TYPE_EXPLOSION;
                        explosion[ENEMY_X]
                            = (int)(laser[LASER_X] + k * laser[LASER_RX]);
                        explosion[ENEMY_Y]
                            = (int)(laser[LASER_Y] + k * laser[LASER_RY]);
                        explosion[ENEMY_Z]
                            = (int)(laser[LASER_Z] + k * laser[LASER_RZ]);
                        explosion[ENEMY_RX] = random.nextInt(w) - p;
                        explosion[ENEMY_RY] = random.nextInt(w) - p;
                        explosion[ENEMY_RZ] = random.nextInt(w) - p;
                        enemies.add(explosion);
                      }

                      if (enemy[ENEMY_POWER] <= 0) {
                        enemies.remove(j);
                        enemyHitCountdown = 0;
                        continue outter2;
                      } else {
                        enemyHitSpriteIndex = enemy[ENEMY_TYPE];
                        enemyHitPower = enemy[ENEMY_POWER];
                        enemyHitCountdown = 128;
                      }

                      continue outter;
                    }
                  }
                }
              }
            }
          }
        }

        // -- update ends ------------------------------------------------------

      } while(nextFrameStartTime < System.nanoTime());

      // -- render starts ------------------------------------------------------

      // draw ground and sky
      for(i = 0; i < 362; i++) { // rows

        // compute normalized ray in direction of scanline
        float ry = HALF_SCREEN_SIZE - i;
        float rz = -SCREEN_DISTANCE;
        float mag = (float)Math.sqrt(ry * ry + rz * rz);
        ry /= mag;
        rz /= mag;

        // compute hit time
        for(k = 0; k < 2; k++) {
          float t = (SKY_Y * k - yo) / ry;
          int[][] texture = k == 0 ? floorTexture : skyTexture;
          int textureScale = k == 0 ? 4 : 8;
          if (t > 0) {
            // compute scale and left of scanline on floor
            float scale = t / SCREEN_DISTANCE;
            float floorX = -scale * HALF_SCREEN_SIZE;
            float floorZ = rz * t;

            // rotate the scanline based on yaw angle
            float fx = floorX * yawCos - floorZ * yawSin;
            float fz = floorX * yawSin + floorZ * yawCos;
            float dfx = scale * yawCos;
            float dfz = scale * yawSin;

            // scanning across row
            for(j = 0; j < 362; j++) {
              pixels[j] = texture[((int)(zo + fz) >> textureScale) & 0xFF]
                  [((int)(xo + fx) >> textureScale) & 0xFF];

              fx += dfx;
              fz += dfz;
            }

            image.setRGB(0, i, SCREEN_SIZE, 1, pixels, 0, SCREEN_SIZE);
          }
        }
      }

      // find visible sprites and perform 3D projection onto 2D plane
      spriteCount = 0;
      for(i = enemies.size() - 1; i >= 0; i--) {
        int[] enemy = enemies.get(i);

        float x0 = enemy[ENEMY_X] - xo;
        float z0 = enemy[ENEMY_Z] - zo;

        // apply inverse transform to rotate into view
        float x1 = yawCos * x0 + yawSin * z0;
        float z1 = yawCos * z0 - yawSin * x0;

        if (z1 < 0) {
          float K = SCREEN_DISTANCE / -z1;

          int width = 1024;
          int height = 864;
          int offsetX = 640;
          int offsetY = 832;

          if (enemy[ENEMY_TYPE] == ENEMY_TYPE_ATST) {
            width = 224;
            height = 256;
            offsetX = 144;
            offsetY = 236;
          } else if (enemy[ENEMY_TYPE] == ENEMY_TYPE_EXPLOSION) {
            width = 64 - enemy[ENEMY_POWER];
            height = width;
            offsetX = width >> 1;
            offsetY = enemy[ENEMY_Y] + width;
          }

          if (spriteCount < 4096) {
            int[] scaledSprite = scaledSprites[spriteCount++];
            scaledSprite[SPRITE_X] = 181 + (int)((x1 - offsetX) * K);
            scaledSprite[SPRITE_Y] = 181 - (int)((offsetY - yo) * K);
            scaledSprite[SPRITE_Z] = (int)z1;
            scaledSprite[SPRITE_WIDTH] = (int)(K * width);
            scaledSprite[SPRITE_HEIGHT] = (int)(K * height);
            scaledSprite[SPRITE_INDEX] = enemy[ENEMY_TYPE];
            scaledSprite[SPRITE_DRAWN] = 0;
            scaledSprite[SPRITE_ENEMY_INDEX] = i;
            scaledSprite[SPRITE_PALETTE] = enemy[ENEMY_POWER];
          }
        }
      }

      // draw sprites in sorted order
      lastEnemyDrawn = -1;
      for(i = 0; i < spriteCount; i++) {
        int[] scaledSprite = null;
        for(j = 0; j < spriteCount; j++) {
          if (scaledSprites[j][SPRITE_DRAWN] == 0
              && (scaledSprite == null
                  || scaledSprites[j][SPRITE_Z] < scaledSprite[SPRITE_Z])) {
            scaledSprite = scaledSprites[j];
          }
        }
        scaledSprite[SPRITE_DRAWN] = 1;
        if (scaledSprite[SPRITE_INDEX] == ENEMY_TYPE_EXPLOSION) {
          // draw explosion
          g.setColor(firePalette[scaledSprite[SPRITE_PALETTE]]);
          g.fillOval(
              scaledSprite[SPRITE_X],
              scaledSprite[SPRITE_Y],
              scaledSprite[SPRITE_WIDTH],
              scaledSprite[SPRITE_HEIGHT]);
        } else {
          g.drawImage(sprites[scaledSprite[SPRITE_INDEX]],
              scaledSprite[SPRITE_X],
              scaledSprite[SPRITE_Y],
              scaledSprite[SPRITE_WIDTH],
              scaledSprite[SPRITE_HEIGHT],
              null);
        }
        if (enemies.get(scaledSprite[SPRITE_ENEMY_INDEX])[ENEMY_TYPE]
              != ENEMY_TYPE_EXPLOSION) {
          lastEnemyDrawn = scaledSprite[SPRITE_ENEMY_INDEX];
        }
      }

      // draw lasers
      g.setColor(Color.RED);
      for(i = lasers.size() - 1; i >= 0; i--) {
        float[] laser = lasers.get(i);

        // find end points relative to player ship position
        float x1 = laser[LASER_X] - xo;
        float y1 = laser[LASER_Y] - yo;
        float z1 = laser[LASER_Z] - zo;

        float x2 = x1 + LASER_LENGTH * laser[LASER_RX];
        float y2 = y1 + LASER_LENGTH * laser[LASER_RY];
        float z2 = z1 + LASER_LENGTH * laser[LASER_RZ];

        // apply inverse transform to rotate into view
        float _x1 = yawCos * x1 + yawSin * z1;
        float _x2 = yawCos * x2 + yawSin * z2;

        float _z1 = yawCos * z1 - yawSin * x1;
        float _z2 = yawCos * z2 - yawSin * x2;

        // if in front of player, draw laser
        if (_z1 < 0 && _z2 < 0) {
          float k1 = SCREEN_DISTANCE / -_z1;
          float k2 = SCREEN_DISTANCE / -_z2;

          xPoints[0] = 181 + (int)((_x1 - LASER_HALF_WIDTH) * k1);
          xPoints[1] = 181 + (int)((_x1 + LASER_HALF_WIDTH) * k1);
          xPoints[2] = 181 + (int)((_x2 + LASER_HALF_WIDTH) * k2);
          xPoints[3] = 181 + (int)((_x2 - LASER_HALF_WIDTH) * k2);
          yPoints[0] = yPoints[1] = 181 - (int)(y1 * k1);
          yPoints[2] = yPoints[3] = 181 - (int)(y2 * k2);

          yPoints[1] += 2;
          yPoints[3] += 2;

          g.fillPolygon(xPoints, yPoints, 4);
        }
      }

      // rotate the hidden buffer by the player angle
      g2.translate(-107, -107);
      g2.rotate(roll, 362, 362);
      g2.drawImage(image, 0, 0, 724, 724, null);
      g2.setTransform(transform);

      // draw target
      g2.setColor(Color.GREEN);
      g2.drawRect(240, 250, 32, 32);

      if (enemyHitCountdown > 0) {
        // draw enemy hit power bar and sprite (HUD)
        g2.drawImage(sprites[enemyHitSpriteIndex], 432, 16, null);
        g2.drawRect(428, 96, 64, 8);
        g2.fillRect(428, 96, enemyHitPower, 8);
      } else {
        // draw radar (HUD)        
        g2.translate(464, 48);
        g2.drawOval(-32, -32, 64, 64);
        g2.fillOval(-2, -2, 4, 4);
        g2.drawLine(0, 0, 14, -29);
        g2.drawLine(0, 0, -14, -29);
        g2.rotate(-yaw);
        g2.drawLine(0, 0, ((int)(-xo)) >> 9, ((int)(-zo)) >> 9);
        g2.drawOval((((int)(-xo)) >> 9) - 4, (((int)(-zo)) >> 9) - 4, 6, 6);
        for(i = 0; i < enemies.size(); i++) {
          int[] enemy = enemies.get(i);
          j = ((int)(enemy[ENEMY_X] - xo)) >> 9;
          k = ((int)(enemy[ENEMY_Z] - zo)) >> 9;
          if (j * j + k * k <= 1024) {
            g2.fillOval(j - 2, k - 2, 4, 4);
          }
        }
        g2.setTransform(transform);
      }

      // draw player power bar (HUD)
      g2.drawRect(20, 20, 64, 8);
      g2.fillRect(20, 20, playerPower, 8);

      if (fading) {
        // draw fade
        g2.setColor(new Color(dying ? 255 : 0, 0, 0, fadeIntensity));
        g2.fillRect(0, 0, 512, 512);
      }

      // -- render ends --------------------------------------------------------

      // show the hidden buffer
      if (g3 == null) {
        g3 = (Graphics2D)getGraphics();
        requestFocus();
      } else {
        g3.drawImage(image2, 0, 0, null);
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
    javax.swing.JFrame frame = new javax.swing.JFrame(
        "Star Wars: The Battle for Hoth 4K");
    frame.setDefaultCloseOperation(javax.swing.JFrame.EXIT_ON_CLOSE);
    a applet = new a();
    applet.setPreferredSize(new java.awt.Dimension(512, 512));
    frame.add(applet, java.awt.BorderLayout.CENTER);
    frame.setResizable(false);
    frame.pack();
    frame.setLocationRelativeTo(null);
    frame.setVisible(true);
    Thread.sleep(250);
    applet.start();
  }*/
}
