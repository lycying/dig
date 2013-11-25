
package info.u250.digs.scenes.level.pack.s1;

import info.u250.c2d.engine.Engine;
import info.u250.c2d.graphic.WebColors;
import info.u250.c2d.graphic.parallax.ParallaxGroup;
import info.u250.c2d.graphic.parallax.ParallaxLayer;
import info.u250.digs.Digs;
import info.u250.digs.PolygonTable;
import info.u250.digs.scenes.game.Level;
import info.u250.digs.scenes.game.LevelConfig;
import info.u250.digs.scenes.game.LevelMakeCallBack;
import info.u250.digs.scenes.game.entity.Boss;
import info.u250.digs.scenes.game.entity.GoldTowerEntity;
import info.u250.digs.scenes.game.entity.KillCircleEntity;
import info.u250.digs.scenes.game.entity.Npc;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.Pixmap;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.math.Polygon;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.scenes.scene2d.ui.Image;

public class S1Lvl11 extends LevelConfig {
	public S1Lvl11(){
		this.surface = "qvg/110.jpg";
		this.width = (int)Engine.getWidth() ;
		this.height = (int)Engine.getHeight();
		this.bottomColor = WebColors.DARK_BLUE.get();
		this.topColor = WebColors.DODGER_BLUE.get();
		this.lineHeight = 360;
		this.segment = 40;
		this.ascent = 20;
		this.gold = 500;
		this.npc = 30;
		this.time = 60*5;
		
		levelMakeCallback = new LevelMakeCallBack() {
			final Texture bgTexture = new Texture(Gdx.files.internal("wb/round.png"));
			@Override
			public void dispose() {
				bgTexture.dispose();
			}
			@Override
			public void after(Level level) {
				for(int i=0;i<npc;i++){
					Npc e = new Npc();
					e.init(level);
					e.setPosition(200, height + Digs.RND.nextFloat()*height);
					level.addNpc(e);
				}
				Boss boss = new Boss();
				boss.setBossLandHeight(200);
				boss.init(level);
				boss.setPosition(800, 240);
				level.addBoss(boss);
				{
					KillCircleEntity e = new KillCircleEntity(300,-100 ,100, Color.BLACK);
					level.addKillCircle(e);
				}
			}
			
			@Override
			public void mapMaker(Pixmap terr, Pixmap gold) {
				{
					gold.setColor(Color.YELLOW);
					Polygon polygon =  PolygonTable.IMG_ISLAND6();
					polygon.setScale(0.2f, 0.2f);
					polygon.setPosition(700, 200);
					drawPolygon(polygon, gold);
				}
				{
					gold.setColor(Color.YELLOW);
					Polygon polygon =  PolygonTable.IMG_ISLAND6();
					polygon.setScale(0.2f, 0.2f);
					polygon.setPosition(100, 200);
					drawPolygon(polygon, gold);
				}
				{
					gold.setColor(Color.YELLOW);
					Polygon polygon =  PolygonTable.LL_GIRL_IN_A_BOX();
//					polygon.setScale(0.2f, 0.2f);
					polygon.setPosition(600, 20);
					drawPolygon(polygon, gold);
				}
				{
					gold.setColor(Color.YELLOW);
					Polygon polygon =  PolygonTable.LL_GIRL_IN_A_BOX();
					polygon.setScale(0.5f, 0.5f);
					polygon.setPosition(200, 20);
					drawPolygon(polygon, gold);
				}
				{
					gold.setColor(Color.YELLOW);
					Polygon polygon =  PolygonTable.LL_GIRL_IN_A_BOX();
					polygon.setScale(0.5f, 0.5f);
					polygon.setPosition(900, 20);
					drawPolygon(polygon, gold);
				}
			}
			@Override
			public void before(Level level) {
				{
					final Image img1 = new Image(bgTexture);
					final Image img2 = new Image(bgTexture);
					final Image img3 = new Image(bgTexture);
					final Image img4 = new Image(bgTexture);
					img1.setColor(Color.YELLOW);
					img2.setColor(Color.GREEN);
					img3.setColor(Color.PINK);
					img4.setColor(Color.RED);
					
					
					final ParallaxGroup pbg = new ParallaxGroup(Engine.getWidth(), Engine.getHeight(), new Vector2(25,50));
					pbg.addActor(new ParallaxLayer(pbg,img1, new Vector2(1f,1), new Vector2(940,600), new Vector2(100,0)));
					pbg.addActor(new ParallaxLayer(pbg,img2 , new Vector2(1f,2.2f), new Vector2(940,270), new Vector2(150,0)));
					pbg.addActor(new ParallaxLayer(pbg,img3 , new Vector2(1f,1.5f), new Vector2(700,560), new Vector2(200,0)));
					pbg.addActor(new ParallaxLayer(pbg,img4 , new Vector2(1f,2f), new Vector2(600,560), new Vector2(100,0)));
					level.addActor(pbg);
				}
				{
					GoldTowerEntity dock = new GoldTowerEntity();
					dock.setY(lineHeight);
					level.addGoldDock(dock);
				}
				{
					GoldTowerEntity dock = new GoldTowerEntity();
					dock.setPosition(width-dock.getWidth(), lineHeight);
					level.addGoldDock(dock);
				}
				
			}
		};
	}
}