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
import info.u250.digs.scenes.game.entity.Ka;
import info.u250.digs.scenes.game.entity.Npc;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.Pixmap;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.math.Polygon;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.scenes.scene2d.ui.Image;

public class Pack1V12 extends LevelConfig {
	public Pack1V12(){
		this.surface = "qvg/111.png";
		this.width = (int)Engine.getWidth() ;
		this.height = 840;
		this.bottomColor = WebColors.STEEL_BLUE.get();
		this.topColor = WebColors.ROYAL_BLUE.get();
		this.lineHeight = 360+300;
		this.ascent = 25;
		this.segment = 20;
		this.gold = 150;
		this.npc = 20;
		this.ka = 5;
		this.time = 600;
		
		levelMakeCallback = new LevelMakeCallBack() {
			final Texture bgTexture = new Texture(Gdx.files.internal("wb/rocket.png"));
			@Override
			public void dispose() {
				bgTexture.dispose();
			}
			@Override
			public void after(Level level) {
				for(int i=0;i<npc;i++){
					Npc e = new Npc();
					e.init(level);
					e.setPosition(200+Digs.RND.nextFloat()*200, Engine.getHeight() + Digs.RND.nextFloat()*100+300);
					level.addNpc(e);
				}
				for(int i=0;i<ka;i++){
					Ka e = new Ka();
					e.init(level);
					e.setPosition(400+Digs.RND.nextFloat()*100, 330);
					level.addKa(e);
				}
				Boss boss = new Boss();
				boss.setBossLandHeight(300);
				boss.init(level);
				boss.setPosition(800, 400);
				level.addBoss(boss);
				
			}
			
			@Override
			public void mapMaker(Pixmap terr, Pixmap gold) {
				{
					gold.setColor(Color.YELLOW);
					Polygon polygon =  PolygonTable.LIZARD0010();
					polygon.setScale(1f, 1f);
					polygon.setPosition(200, 150);
					drawPolygon(polygon, gold);
				}
				{
					gold.setColor(Color.YELLOW);
					Polygon polygon =  PolygonTable.IMG_ISLAND1();
					polygon.setScale(1f, 1.3f);
					polygon.setPosition(700, 80);
					drawPolygon(polygon, gold);
				}
				
				{
					gold.setColor(Color.CYAN);
					Polygon polygon =  PolygonTable.TOAST();
					polygon.setScale(4f, 0.4f);
					polygon.setPosition(10, 30);
					drawPolygon(polygon, gold);
				}
				{
					
					drawPixmapDeco(gold, "stone2", 780, -30,2);
					drawPixmapDeco(gold, "stone2", 680, -30);
					drawPixmapDeco(gold, "stone5", 300, 300,0.5f);
					drawPixmapDeco(gold, "stone2", 600, 350,0.5f,0.2f);
					drawPixmapDeco(gold, "stone6", -10, 20,0.5f);
					drawPixmapDeco(gold, "stone6", 100, 20);
					drawPixmapDeco(gold, "stone4", 660, 130);
					drawPixmapDeco(gold, "stone4", 750, 150,0.8f);
					drawPixmapDeco(gold, "deco2", 850, 0,1f);
					
					terr.setColor(WebColors.KHAKI.get());
					fillRect(terr, 380, 300, 140, 80);
					terr.setColor(Color.CLEAR);
					fillRect(terr, 400, 320, 100, 40);
				}
			}
			@Override
			public void before(Level level) {
				{
					final ParallaxGroup pbg = new ParallaxGroup(Engine.getWidth(), Engine.getHeight(), new Vector2(1000,0));
					pbg.addActor(new ParallaxLayer(pbg, new Image(bgTexture), new Vector2(1f,1), new Vector2(1200,1000), new Vector2(0,700)));
					pbg.addActor(new ParallaxLayer(pbg, new Image(bgTexture), new Vector2(1.4f,1), new Vector2(2000,1000), new Vector2(0,740)));
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