package info.u250.digs.scenes.level.pack.s1;

import info.u250.c2d.engine.Engine;
import info.u250.c2d.graphic.WebColors;
import info.u250.c2d.graphic.parallax.ParallaxGroup;
import info.u250.c2d.graphic.parallax.ParallaxLayer;
import info.u250.digs.Digs;
import info.u250.digs.PolygonTable;
import info.u250.digs.scenes.game.HookLevelConfig;
import info.u250.digs.scenes.game.Level;
import info.u250.digs.scenes.game.LevelMakeCallBack;
import info.u250.digs.scenes.game.entity.Boss;
import info.u250.digs.scenes.game.entity.EnemyMiya;
import info.u250.digs.scenes.game.entity.GoldTowerEntity;
import info.u250.digs.scenes.game.entity.Npc;
import info.u250.digs.scenes.game.entity.StepladderEntity;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.Pixmap;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.math.Polygon;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.scenes.scene2d.ui.Image;

public class Pack1V__14 extends HookLevelConfig {
	public Pack1V__14(){
		this.surface = "113";
		this.width = (int)Engine.getWidth()-SCROLL_WIDTH ;
		this.height = 1024;
		this.bottomColor = WebColors.DARK_GRAY.get();
		this.topColor = WebColors.DARK_SLATE_GRAY.get();
		this.lineHeight = 360+1024-540;
		this.ascent = 25;
		this.segment = 20;
		this.gold = 150;
		this.npc = 15;
		this.enemy = 2;
		this.time = 600;
		
		levelMakeCallback = new LevelMakeCallBack() {
			final Texture bgTexture = new Texture(Gdx.files.internal("wb/island.png"));
			@Override
			public void dispose() {
				bgTexture.dispose();
			}
			@Override
			public void after(Level level) {
				{
					int w = 100;
					while(w<width){
						StepladderEntity ladder = new StepladderEntity(8, w,350);
						level.addStepladder(ladder);
						w+=100;
					}
					w = 50;
					while(w<width){
						StepladderEntity ladder = new StepladderEntity(8, w,500);
						level.addStepladder(ladder);
						w+=100;
					}
					
				}
				for(int i=0;i<npc;i++){
					Npc e = new Npc();
					e.init(level);
					e.setPosition(200+Digs.RND.nextFloat()*200, height + Digs.RND.nextFloat()*100);
					level.addNpc(e);
				}
				{
					Boss boss = new Boss();
					boss.setBossLandHeight(300);
					boss.init(level);
					boss.setPosition(100, 350);
					level.addBoss(boss);
					EnemyMiya e = new EnemyMiya();
					e.init(level);
					e.setPosition(100, 350);
					level.addEnemyMiya(e);
				}
				{
					Boss boss = new Boss();
					boss.setBossLandHeight(300);
					boss.init(level);
					boss.setPosition(860, 350);
					level.addBoss(boss);
					EnemyMiya e = new EnemyMiya();
					e.init(level);
					e.setPosition(860, 350);
					level.addEnemyMiya(e);
				}
			}
			
			@Override
			public void mapMaker(Pixmap terr, Pixmap gold) {
				{
					int w = 100;
					while(w<width){
						gold.setColor(Color.YELLOW);
						Polygon polygon =  PolygonTable.CRISTAL();
						polygon.setScale(0.5f, 0.5f);
						polygon.setPosition(w, 100);
						drawPolygon(polygon, gold);
						w+=150;
					}
					w = 100;
					while(w<width){
						gold.setColor(Color.YELLOW);
						Polygon polygon =  PolygonTable.CRISTAL();
						polygon.setScale(0.5f, 0.5f);
						polygon.setPosition(w, 200);
						drawPolygon(polygon, gold);
						w+=150;
					}
					w = 100;
					while(w<width){
						gold.setColor(Color.YELLOW);
						Polygon polygon =  PolygonTable.CRISTAL();
						polygon.setScale(0.8f, 0.8f);
						polygon.setPosition(w, 0);
						drawPolygon(polygon, gold);
						w+=150;
					}
				}
				{
					gold.setColor(Color.CYAN);
					Polygon polygon =  PolygonTable.TOAST();
					polygon.setScale(4f, 0.4f);
					polygon.setPosition(10, 30);
					drawPolygon(polygon, gold);
				}
				{
					gold.setColor(Color.YELLOW);
					Polygon polygon =  PolygonTable.COMBO_8();
					polygon.setScale(0.2f, 0.2f);
					polygon.setPosition(180, 520);
					drawPolygon(polygon, gold);
				}
				{
					gold.setColor(Color.YELLOW);
					Polygon polygon =  PolygonTable.COVERSCENE8();
					polygon.setScale(0.4f, 0.4f);
					polygon.setPosition(50, 330);
					drawPolygon(polygon, gold);
				}
				{
					drawPixmapDeco(gold, "stone5", 720, 520);
					drawPixmapDeco(gold, "stone5", 120, 520,0.5f);
					drawPixmapDeco(gold, "stone5", 420, 520,0.3f);
					drawPixmapDeco(gold, "stone2", 600, 650,0.5f,0.2f);
					drawPixmapDeco(gold, "stone2", 700, 450,0.2f,0.5f);
				}
				
			}
			@Override
			public void before(Level level) {
				{
					Image bgImage1 = new Image(bgTexture);
					Image bgImage2 = new Image(bgTexture);
					Image bgImage3 = new Image(bgTexture);
					Image bgImage4 = new Image(bgTexture);
					bgImage2.setColor(Color.GREEN);
					bgImage2.setScale(0.5f);
					bgImage2.setX(600);
					bgImage3.setX(250);
					bgImage3.setY(30);
					bgImage3.setScale(0.4f);
					bgImage3.setColor(Color.RED);
					bgImage4.setScale(0.9f);
					final ParallaxGroup pbg = new ParallaxGroup(Engine.getWidth(), Engine.getHeight(), new Vector2(30,0));
					ParallaxLayer layer = new ParallaxLayer(pbg, bgImage1, new Vector2(1,1), new Vector2(100,10000), new Vector2(300,834));
					layer.setWidth(800);
					layer.addActor(bgImage2);
					layer.addActor(bgImage3);
					
					bgImage4.setColor(Color.YELLOW);
					pbg.addActor(new ParallaxLayer(pbg, bgImage4, new Vector2(2f,1), new Vector2(1000,1000), new Vector2(300,834)));
					pbg.addActor(layer);
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