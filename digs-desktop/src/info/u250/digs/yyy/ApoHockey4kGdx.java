package info.u250.digs.yyy;

/*
 * Copyright (c) 2005-2012 Dirk Aporius <dirk.aporius@gmail.com>
 * All rights reserved.
 * 
 * Redistribution and use in source and binary forms, with or without
 * modification, are permitted provided that the following conditions
 * are met:
 * 1. Redistributions of source code must retain the above copyright
 *    notice, this list of conditions and the following disclaimer.
 * 2. Redistributions in binary form must reproduce the above copyright
 *    notice, this list of conditions and the following disclaimer in the
 *    documentation and/or other materials provided with the distribution.
 * 3. The name of the author may not be used to endorse or promote products
 *    derived from this software without specific prior written permission.
 * 
 * THIS SOFTWARE IS PROVIDED BY THE AUTHOR ``AS IS'' AND ANY EXPRESS OR
 * IMPLIED WARRANTIES, INCLUDING, BUT NOT LIMITED TO, THE IMPLIED WARRANTIES
 * OF MERCHANTABILITY AND FITNESS FOR A PARTICULAR PURPOSE ARE DISCLAIMED.
 * IN NO EVENT SHALL THE AUTHOR BE LIABLE FOR ANY DIRECT, INDIRECT,
 * INCIDENTAL, SPECIAL, EXEMPLARY, OR CONSEQUENTIAL DAMAGES (INCLUDING, BUT
 * NOT LIMITED TO, PROCUREMENT OF SUBSTITUTE GOODS OR SERVICES; LOSS OF USE,
 * DATA, OR PROFITS; OR BUSINESS INTERRUPTION) HOWEVER CAUSED AND ON ANY
 * THEORY OF LIABILITY, WHETHER IN CONTRACT, STRICT LIABILITY, OR TORT
 * (INCLUDING NEGLIGENCE OR OTHERWISE) ARISING IN ANY WAY OUT OF THE USE OF
 * THIS SOFTWARE, EVEN IF ADVISED OF THE POSSIBILITY OF SUCH DAMAGE.
 */

import info.u250.c2d.engine.Engine;
import info.u250.c2d.engine.EngineDrive;
import info.u250.c2d.engine.Scene;
import info.u250.c2d.engine.resources.AliasResourceManager;


import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.InputAdapter;
import com.badlogic.gdx.InputProcessor;
import com.badlogic.gdx.Input.Keys;
import com.badlogic.gdx.backends.lwjgl.LwjglApplication;
import com.badlogic.gdx.backends.lwjgl.LwjglApplicationConfiguration;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.GL10;
import com.badlogic.gdx.graphics.glutils.ShapeRenderer.ShapeType;
import com.badlogic.gdx.math.Rectangle;


public class ApoHockey4kGdx  implements Scene{

	Rectangle tmpRect1 = new Rectangle();
	Rectangle tmpRect2 = new Rectangle();

	private final static Color[] COLOR_ORDER = new Color[] {
		Color.CYAN,
		Color.BLUE, 
		Color.RED, 
		Color.GREEN,
		Color.YELLOW,
		Color.MAGENTA
	};
	
	private final static int[] WALLS = new int[] {
		10, 10, 6, 286,
		10, 304, 6, 286,
		440 - 16, 10, 6, 286,
		440 - 16, 304, 6, 286,
		17, 7, 103, 6,
		440 - 120, 7, 103, 6,
		17, 587, 103, 6,
		440 - 120, 587, 103, 6,
	};
	
	/**
	 * p[0] == X-Wert Maus
	 * p[1] == Y-Wert Maus
	 * p[2] == winner
	 * p[3] == aktuelle Moveanzahl
	 * p[4] == Maus gedrückt
	 * p[5] == Maus losgelassen
	 * p[6] == Spiel gestartet
	 * p[7] == Level geschafft Klick
	 * p[8] == Punktzahl Spieler Computer
	 * p[9] == Punktzahl Spieler Mensch
	 * p[10] == Punkt erzielt Computer
	 * p[11] == Punkt erzielt Mensch
	 */
	private final int[] p = new int[12];
	float think = 0;
	float[] paddleVec = new float[6];
	int colors[] = new int[16];
	float[] playerspaddle = new float[8];
	
	public ApoHockey4kGdx(){
		p[7] = 1;
		
		/**
		 * 0 - 1 = Player x y werte
		 * 2 = player time
		 * 3 - 4 = enemy x y werte
		 * 5 = enemy time
		 * 6 = enemy angle
		 * 7 = enemy velocity
		 */
		
		playerspaddle[0] = playerspaddle[3] = 190;
		playerspaddle[1] = 450;
		playerspaddle[4] = 90;
		
		/**
		 * 0 = farbe
		 * 1 = Zeit (wenn größer 0 dann glow
		 */
		
		
		/**
		 * 0 = x - Wert
		 * 1 = y - Wert
		 * 2 = angle
		 * 3 = Geschwindigkeit
		 * 4 = time to glow
		 * 5 = color
		 */
		
		paddleVec[0] = 205;
		paddleVec[1] = 345;
		paddleVec[5] = 3;
	}
	public void render(float delta) {
		Gdx.gl.glEnable(GL10.GL_BLEND);
		Engine.getShapeRenderer().setProjectionMatrix(Engine.getDefaultCamera().combined);
		
			think += delta;
			
			// Update / think
			// Wenn 10 ms vergangen sind, dann denke nach
			if (think >= 0.01) {
				think -= 0.01;
				
				if (p[7] <= 0) {
					float[] oldValues = new float[6];
					for (int i = 0; i < 6; i++) {
						oldValues[i] = playerspaddle[i];
					}
					playerspaddle[0] = p[0] - 30;
					playerspaddle[1] = p[1] - 30;
					
					if (playerspaddle[7] > 0) {
						playerspaddle[3] = playerspaddle[3] + playerspaddle[7] * (float)Math.sin(Math.toRadians(playerspaddle[6]));
						playerspaddle[4] = playerspaddle[4] - playerspaddle[7] * (float)Math.cos(Math.toRadians(playerspaddle[6]));
					}
					for (int i = 0; i < 5; i += 3) {
						if (playerspaddle[i] < 18) {
							playerspaddle[i] = 18;
						}
						if (playerspaddle[i] >= 424 - 60) {
							playerspaddle[i] = 424 - 60;
						}
						
						int add = - 287;
						if (i == 0) {
							add = 0;
						}
						if (playerspaddle[i + 1] <= 300 + add) {
							playerspaddle[i + 1] = 300 + add;
						}
						if (playerspaddle[i + 1] >= 587 - 60 + add) {
							playerspaddle[i + 1] = 587 - 60 + add;
						}
					}
					
					// wall time
					for (int i = 1; i < colors.length; i+=2) {
						if (colors[i] > 0) {
							colors[i] -= 10;
						}
					}
					
					//player time
					for (int i = 2; i < 6; i+=3) {
						playerspaddle[i] -= 10;
						if (playerspaddle[i] < 0) {
							playerspaddle[i] = 0;
						}
					}
					// paddle time
					paddleVec[4] -= 10;
					if (paddleVec[4] < 0) {
						paddleVec[4] = 3000;
					}
					
					// check intersection with paddle
					for (int i = 0; i < 4; i += 3) {
						double angle = 0.0d;
						double angle1 = 0.0d;
						double angle2 = 0.0d;
						float newX = ((paddleVec[0] + 15 - (playerspaddle[i] + 30))*(paddleVec[0] + 15 - (playerspaddle[i] + 30)));
						float newY = ((paddleVec[1] + 15 - (playerspaddle[i + 1] + 30))*(paddleVec[1] + 15 - (playerspaddle[i + 1] + 30)));
						float newRadius = ((45)*(45));

						for (int j = 0; j < 2; j++) {
							double dx = paddleVec[0] + 15 - (playerspaddle[i] + 30);
							double dy = paddleVec[1] + 15 - (playerspaddle[i + 1] + 30);
							
							if ((j == 0) && (i > 0)) {
								dx = paddleVec[0] - 5 - (playerspaddle[i] + 30);
							}
							if (j == 1) {
								dx = 220 - (playerspaddle[i] + 30);
								dy = 50 - (playerspaddle[i + 1] + 30);
							}
					 
							if ( dx == 0.0 ) {
								if ( dy == 0.0 ) {
									angle1 = 0.0;
								} else if ( dy > 0.0 ) {
									angle1 = Math.PI / 2.0;
								} else {
									angle1 = (Math.PI * 3.0) / 2.0;
								}
							} else if( dy == 0.0 ) {
								if( dx > 0.0 ) {
									angle1 = 0.0;
								} else {
									angle1 = Math.PI;
								}
							} else {
								if ( dx < 0.0 ) {
									angle1 = Math.atan( dy/dx ) + Math.PI;
								} else if( dy < 0.0 ) {
									angle1 = Math.atan( dy/dx ) + ( 2*Math.PI );
								} else {
									angle1 = Math.atan( dy/dx );
								}
							}
							angle1 = (float)(( angle1 * 180 ) / Math.PI) + 90;
							if (angle1 >= 360) {
								angle1 -= 360;
							}
							if (j == 0) {
								angle = angle1;
							} else {
								angle2 = angle1;
							}
						}
						
						/** ai */
						if (i == 3) {
							if ((paddleVec[3] <= 0.001f) && (paddleVec[1] < 299 )) {
								playerspaddle[6] = (float)angle;
								playerspaddle[7] = 2.0f;
							} else {
								playerspaddle[6] = (float)angle2;
								playerspaddle[7] = 2.0f;

								if ((playerspaddle[4] > 16) && (playerspaddle[4] < 24) && (paddleVec[1] > 45)) {
									playerspaddle[7] = 2.1f;
									if (playerspaddle[3] < paddleVec[0]) {
										playerspaddle[6] = 90;
									} else if (playerspaddle[3] > paddleVec[0]) {
										playerspaddle[6] = 270;
									}
								}
							}
						}
						
						if (newX + newY <= newRadius) {
							playerspaddle[i + 2] = 3000;
							paddleVec[2] = (float)(angle);
							float speed = (Math.abs(oldValues[i] - playerspaddle[i]) + Math.abs(oldValues[i+1] - playerspaddle[i+1])) * 0.015f;
							if (speed > 0.022) {
								speed = 0.022f;
							}
							if (speed != 0) {
								paddleVec[3] = speed;
							}
							
						}
					}
					
					// move paddle
					float speed = (float)paddleVec[3] * 10 * 30;
					double alpha = paddleVec[2];
					if (alpha > 360) {
						alpha = 360 - alpha;
					} else if (alpha < 0) {
						alpha = 360 + alpha;
					}
					if (speed > 0.001f) {
						float newX = paddleVec[0] + 15 + speed * (float)Math.sin(Math.toRadians(alpha));
						float newY = paddleVec[1] + 15 - speed * (float)Math.cos(Math.toRadians(alpha));
						paddleVec[0] = (newX - 15);
						paddleVec[1] = (newY - 15);
						float next = 0.000002f * 10;
						paddleVec[3] = (paddleVec[3] - next);
						if (paddleVec[3] < 0) {
							paddleVec[3] = 0;
						}
						if (paddleVec[1] < -25) {
							p[11] = p[9] += 1;
							if (p[9] >= 7) {
								p[7] = 1;
								p[11] = 0;
							}
						} else if (paddleVec[1] > 595) {
							p[10] = p[8] += 1;
							if (p[8] >= 7) {
								p[7] = 1;
								p[10] = 0;
							}
						} else if (paddleVec[3] != 0) {
							// check intersects with walls
							float los = paddleVec[2];
							for (int i = 0; i < WALLS.length; i += 4) {
								tmpRect1.x = WALLS[i];
								tmpRect1.y = WALLS[i+1];
								tmpRect1.width = WALLS[i+2];
								tmpRect1.height = WALLS[i+3];
								
								tmpRect2.x = paddleVec[0];
								tmpRect2.y = paddleVec[1];
								tmpRect2.width = 30;
								tmpRect2.height = 30;
								if (tmpRect1.overlaps(tmpRect2)) {
									boolean bPaddle = false;
									if (WALLS[i+2] < WALLS[i+3]) {
										if ((los >= 90) && (los <= 270)) {
											if (los < 180) {
												float dif = los - 90;
												paddleVec[2] = (90 - dif);
											} else {
												float dif = 270 - los;
												paddleVec[2] = (270 + dif);
											}
											bPaddle = true;
										} else {
											if (los < 180) {
												paddleVec[2] = (180 - los);
											} else {
												float dif = 270 - los;
												paddleVec[2] = (270 + dif);
											}
											bPaddle = true;
										}
									} else {
										if (los >= 180) {
											if (los >= 270) {
												paddleVec[2] = (360 - los);
											} else {
												float dif = 180 - los;
												paddleVec[2] = (180 + dif);
											}
											bPaddle = true;
										} else {
											if (los < 90) {
												paddleVec[2] = (360 - los);
											} else {
												float dif = 180 - los;
												paddleVec[2] = (180 + dif);
											}
											bPaddle = true;
										}
									}
									if (bPaddle) {
										// don't touch the wall lovely paddle
										int count = 0;
										
										tmpRect1.x = WALLS[i];
										tmpRect1.y = WALLS[i+1];
										tmpRect1.width = WALLS[i+2];
										tmpRect1.height = WALLS[i+3];
										
										tmpRect2.x = paddleVec[0];
										tmpRect2.y = paddleVec[1];
										tmpRect2.width = 30;
										tmpRect2.height = 30;
										
										while ((count < 30) && tmpRect1.overlaps(tmpRect2)) {
											float radiusOne = 0.5f;
											count++;
											paddleVec[0] = (paddleVec[0] - radiusOne * (float)Math.sin(Math.toRadians(paddleVec[2])) );
											paddleVec[1] = (paddleVec[1] + radiusOne * (float)Math.cos(Math.toRadians(paddleVec[2])) );
										}
										
										paddleVec[2] += 180;
										while (paddleVec[2] > 360) {
											paddleVec[2] -= 360;
										}
										colors[i/2] += 1;
										colors[i/2 + 1] = 3000;
										
										if (WALLS[i+2] > WALLS[i+3]) {
											
											tmpRect1.x = WALLS[i];
											tmpRect1.y = WALLS[i+1];
											tmpRect1.width = WALLS[i+2];
											tmpRect1.height = WALLS[i+3];
											
											tmpRect2.x = paddleVec[0];
											tmpRect2.y = paddleVec[1];
											tmpRect2.width = 30;
											tmpRect2.height = 30;
											
											if (tmpRect1.overlaps(tmpRect2)) {
												if ((paddleVec[2] >= 90) && (paddleVec[2] <= 270)) {
													if (paddleVec[2] < 180) {
														float dif = paddleVec[2] - 90;
														paddleVec[2] = (90 - dif);
													} else {
														float dif = 270 - paddleVec[2];
														paddleVec[2] = (270 + dif);
													}
												} else {
													if (paddleVec[2] < 180) {
														paddleVec[2] = (180 - paddleVec[2]);
													} else {
														float dif = 270 - paddleVec[2];
														paddleVec[2] = (270 + dif);
													}
												}
											}
										}
									}
								}
							}
						}
						if (paddleVec[0] < 16) {
							paddleVec[0] = 16;
						}
						if (paddleVec[0] > 424 - 30) {
							paddleVec[0] = 424 - 30;
						}
					}
				}
				
				if (((p[7] > 0) && (p[5] > 0)) || (p[10] > 0) || (p[11] > 0)) {	
					if (p[7] > 0) {
						p[7] = p[8] = p[9] = 0;
					}
					
					colors = new int[16];
					for (int i = 0; i < WALLS.length; i += 4) {
						colors[i/2] = (int)(Math.random() * COLOR_ORDER.length);
					}
					playerspaddle = new float[8];
					playerspaddle[0] = playerspaddle[3] = 190;
					playerspaddle[1] = 450;
					playerspaddle[4] = 90;
					paddleVec = new float[6];
					paddleVec[0] = 205;
					paddleVec[1] = 345;
					if (p[11] > 0) {
						paddleVec[1] = 225;
					}
					paddleVec[5] = 3;
					
					p[10] = 0;
					p[11] = 0;
				}
				
				p[4] = 0;
				p[5] = 0;
			}

//			lastTime = now;

			// Renderabschnitt
			// Hintergrund malen
			Engine.getShapeRenderer().setColor(new Color(2/255f, 0, 45/255f,1));
			Engine.getShapeRenderer().begin(ShapeType.Filled);
			Engine.getShapeRenderer().rect(0, 0, 440, 600);
			Engine.getShapeRenderer().end();
			
			Engine.getShapeRenderer().setColor(new Color(1,1,1,100f/255f));
			Engine.getShapeRenderer().begin(ShapeType.Line);
			Engine.getShapeRenderer().line(0, 600/2-2, 440, 600/2-2);
			Engine.getShapeRenderer().line(0, 600/2+1, 440, 600/2+1);
			Engine.getShapeRenderer().end();
			
			int width = 100;
			
			Engine.getShapeRenderer().begin(ShapeType.Filled);
			Engine.getShapeRenderer().circle(440/2, 600/2, width/2);
			Engine.getShapeRenderer().end();
			
			width = 94;
			Engine.getShapeRenderer().begin(ShapeType.Filled);
			Engine.getShapeRenderer().circle(440/2, 600/2, width/2);
			Engine.getShapeRenderer().end();

			
			width = 200;
			Engine.getShapeRenderer().begin(ShapeType.Filled);
			Engine.getShapeRenderer().circle(440/2, 0, width/2);
			Engine.getShapeRenderer().circle(440/2, 600, width/2);
			Engine.getShapeRenderer().end();
			

			for (int i = 0; i < WALLS.length; i += 4) {
				if (colors[i/2] >= COLOR_ORDER.length) {
					colors[i/2] = 0;
				}
				Color c = COLOR_ORDER[colors[i/2]];

				width = 10 - Math.abs(1500 - colors[i/2 + 1]) / 150 + 14;
				for (int w = 0; w < width/2 - 2; w++) {
					float add = 200f / (width/2f - 2f);
					int alpha = 255 - ((int)((width/2 - 2 - w) * add));
					Engine.getShapeRenderer().setColor(new Color(c.r, c.g, c.b, alpha/255f));
					if (WALLS[i + 2] < WALLS[i + 3]) {
						Engine.getShapeRenderer().begin(ShapeType.Filled);
						Engine.getShapeRenderer().rect(WALLS[i] + WALLS[i + 2]/2 - width/2 + w, WALLS[i + 1], 1, WALLS[i + 3]);
						Engine.getShapeRenderer().rect(WALLS[i] + WALLS[i + 2]/2 + width/2 - w, WALLS[i + 1], 1, WALLS[i + 3]);
						Engine.getShapeRenderer().end();
					} else {
						Engine.getShapeRenderer().begin(ShapeType.Filled);
						Engine.getShapeRenderer().rect(WALLS[i], WALLS[i + 1] + WALLS[i + 3]/2 - width/2 + w, WALLS[i + 2], 1);
						Engine.getShapeRenderer().rect(WALLS[i], WALLS[i + 1] + WALLS[i + 3]/2 + width/2 - w, WALLS[i + 2], 1);
						Engine.getShapeRenderer().end();
					}
				}
				
				Engine.getShapeRenderer().setColor(Color.WHITE);
				Engine.getShapeRenderer().begin(ShapeType.Filled);
				Engine.getShapeRenderer().rect(WALLS[i], WALLS[i + 1], WALLS[i + 2], WALLS[i + 3]);
				Engine.getShapeRenderer().end();
			}

			Color c = Color.BLUE;
//			int wi = 60;
			for (int i = 0; i < 6; i += 3) {
				if (i  == 3) c = Color.RED;
				
				//g.setStroke(new BasicStroke(1));
				width = (int)(10 - Math.abs(1500 - playerspaddle[i + 2]) / 150 + 14);
				for (int w = 0; w < width/2 - 2; w++) {
					float add = 200f / (width/2f - 2f);
					int alpha = 255 - ((int)((width/2 - 2 - w) * add));
					Engine.getShapeRenderer().setColor(new Color(c.r, c.g, c.b, alpha/255f));
					Engine.getShapeRenderer().begin(ShapeType.Filled);
					Engine.getShapeRenderer().circle((playerspaddle[i] - width/2 + 2 + w) + ( 60 + 2*(width/2 - 2 - w))/2 , (playerspaddle[i + 1] - width/2 + 2 + w) +( 60 + 2*(width/2 - 2 - w))/2,( 60 + 2*(width/2 - 2 - w))/2);
					Engine.getShapeRenderer().circle((playerspaddle[i] + width/2 - 2 - w) + ( 60 - 2*(width/2 - 2 - w))/2 , (playerspaddle[i + 1] + width/2 - 2 - w) +( 60 - 2*(width/2 - 2 - w))/2,( 60 - 2*(width/2 - 2 - w))/2);
					Engine.getShapeRenderer().end();
//					g.drawOval((int)(playerspaddle[i] - width/2 + 2 + w), (int)(playerspaddle[i + 1] - width/2 + 2 + w), (int)(60 + 2*(width/2 - 2 - w)), 60 + 2*(width/2 - 2 - w));	
//					g.drawOval((int)(playerspaddle[i] + width/2 - 2 - w), (int)(playerspaddle[i + 1] + width/2 - 2 - w), (int)(60 - 2*(width/2 - 2 - w)), 60 - 2*(width/2 - 2 - w));	
				}
				
				//g.setStroke(new BasicStroke(3));
				Engine.getShapeRenderer().setColor(Color.WHITE);
				Engine.getShapeRenderer().begin(ShapeType.Filled);
				Engine.getShapeRenderer().circle(playerspaddle[i]+30, playerspaddle[i + 1]+30,30);
				Engine.getShapeRenderer().end();

			}
			
//			g.setStroke(new BasicStroke(1));
			c = COLOR_ORDER[(int)(paddleVec[5])];
//			wi = 30;
			width = (int)(10 - Math.abs(1500 - paddleVec[4]) / 150 + 14);
			for (int w = 0; w < width/2 - 2; w++) {
				float add = 200f / (width/2f - 2f);
				int alpha = 255 - ((int)((width/2 - 2 - w) * add));
				Engine.getShapeRenderer().setColor(new Color(c.r, c.g, c.b, alpha/255f));
				Engine.getShapeRenderer().begin(ShapeType.Filled);
				Engine.getShapeRenderer().circle((paddleVec[0] - width/2 + 2 + w)+(30 + 2*(width/2 - 2 - w))/2, (paddleVec[1] - width/2 + 2 + w)+(30 + 2*(width/2 - 2 - w))/2, (30 + 2*(width/2 - 2 - w))/2);
				Engine.getShapeRenderer().circle((paddleVec[0] + width/2 - 2 - w)+(30 - 2*(width/2 - 2 - w))/2, (paddleVec[1] + width/2 - 2 - w)+(30 - 2*(width/2 - 2 - w))/2,(30 - 2*(width/2 - 2 - w))/2);
				Engine.getShapeRenderer().end();
//				g.drawOval((int)(paddleVec[0] - width/2 + 2 + w), (int)(paddleVec[1] - width/2 + 2 + w), 30 + 2*(width/2 - 2 - w), 30 + 2*(width/2 - 2 - w));	
//				g.drawOval((int)(paddleVec[0] + width/2 - 2 - w), (int)(paddleVec[1] + width/2 - 2 - w), 30 - 2*(width/2 - 2 - w), 30 - 2*(width/2 - 2 - w));	
			}
			
//			g.setStroke(new BasicStroke(3));
			Engine.getShapeRenderer().setColor(Color.WHITE);
			Engine.getShapeRenderer().begin(ShapeType.Filled);
			Engine.getShapeRenderer().circle(paddleVec[0]+15,paddleVec[1]+15,15);
			Engine.getShapeRenderer().end();

//			g.setStroke(new BasicStroke(1));

//			g.setFont(g.getFont().deriveFont(30f).deriveFont(1));
//			for (int i = 8; i < 10; i++) {
//				String s = String.valueOf(p[i]);
//				int w = g.getFontMetrics().stringWidth(s);
//				g.drawString(s, 410 - w/2, 280 + (i-8)*60);
//			}
//			if (p[7] > 0) {
//				String s = "ApoHockey4k";
//				int w = g.getFontMetrics().stringWidth(s);
//				g.drawString(s, 220 - w/2, 60);
//			
//
//				g.setFont(g.getFont().deriveFont(25f).deriveFont(1));
//				s = "Play with the mouse";
//				w = g.getFontMetrics().stringWidth(s);
//				g.drawString(s, 220 - w/2, 510);
//				
//				s = "Click to start";
//				w = g.getFontMetrics().stringWidth(s);
//				g.drawString(s, 220 - w/2, 550);
//				
//				if (p[8] > p[9]) {
//					s = "The computer wins!";	
//				} else if (p[8] < p[9]) {
//					s = "Congratulation, you win!";	
//				}
//				if (p[8] != p[9]) {
//					w = g.getFontMetrics().stringWidth(s);
//					g.drawString(s, 220 - w/2, 310);
//				}
//			}
			
			// Render das Ganze auf den Bildschirm

	}
	
	InputProcessor input = new InputAdapter(){
		public boolean keyDown(int keycode) {
			if(keycode==Keys.DEL){
				p[7] = 1;
			}
			return true;
		}
		public boolean touchDown(int screenX, int screenY, int pointer, int button) {
			p[4] = 1;
			p[5] = 0;
			return true;
		}
		public boolean touchUp(int screenX, int screenY, int pointer, int button) {
			p[4] = 0;
			p[5] = 1;
			return true;
		};
		public boolean touchDragged(int screenX, int screenY, int pointer) {
			p[0] = (int)Engine.screenToWorld(screenX, screenY).x;
			p[1] = (int)Engine.screenToWorld(screenX, screenY).y;
			return true;
		};
	};

	
	public static void main(String args[]){
		final float w = 450f;
		final float h = 600f;
		final float scale = 1f;
		LwjglApplicationConfiguration config = new LwjglApplicationConfiguration();
		config.width = (int)(w/scale);
		config.height =(int)(h/scale);
		new LwjglApplication(new Engine() {
			
			@Override
			protected EngineDrive onSetupEngineDrive() {
				return new EngineDrive() {
					
					@Override
					public EngineOptions onSetupEngine() {
						EngineOptions opt = new EngineOptions(new String[]{},w,h);
						return opt;
					}
					
					@Override
					public void onResourcesRegister(AliasResourceManager<String> reg) {
						
					}
					
					@Override
					public void onLoadedResourcesCompleted() {
						Engine.setMainScene(new ApoHockey4kGdx());
					}
					
					@Override
					public void dispose() {
						
					}
				};
			}
		},config);
	}

	@Override
	public void update(float delta) {
		// TODO Auto-generated method stub
		
	}


	@Override
	public InputProcessor getInputProcessor() {
		return input;
	}

	@Override
	public void show() {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void hide() {
		// TODO Auto-generated method stub
		
	}
}

