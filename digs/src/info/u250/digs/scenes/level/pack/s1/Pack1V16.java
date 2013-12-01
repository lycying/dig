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
import info.u250.digs.scenes.game.entity.EnemyMiya;
import info.u250.digs.scenes.game.entity.GoldTowerEntity;
import info.u250.digs.scenes.game.entity.Npc;
import info.u250.digs.scenes.game.entity.TeleportEntity;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.Pixmap;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.math.Polygon;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.scenes.scene2d.ui.Image;

public class Pack1V16 extends LevelConfig {
	public Pack1V16(){
		this.surface = "qvg/115.png";
		this.width = (int)Engine.getWidth() ;
		this.height = 2048;
		this.bottomColor = WebColors.INDIAN_RED.get();
		this.topColor = WebColors.DARK_RED.get();
		this.lineHeight = 360+2048-540;
		this.ascent = 25;
		this.segment = 20;
		this.enemy = 3;
		this.gold = 50;
		this.npc = 20;
		this.time = 60*5;
		
		levelMakeCallback = new LevelMakeCallBack() {
			final Texture bgTexture = new Texture(Gdx.files.internal("wb/bg2.png"));
			@Override
			public void dispose() {
				bgTexture.dispose();
			}
			@Override
			public void after(Level level) {
				for(int i=0;i<npc;i++){
					Npc e = new Npc();
					e.init(level);
					e.setPosition(200+Digs.RND.nextFloat()*200, height + Digs.RND.nextFloat()*100);
					level.addNpc(e);
				}
				{
					TeleportEntity inout = new TeleportEntity(280, 600, 700, 1020);
					level.addInOutTrans(inout);
				}
				{
					EnemyMiya e = new EnemyMiya();
					e.init(level);
					e.setPosition(180, 600);
					level.addEnemyMiya(e);
				}
				{
					EnemyMiya e = new EnemyMiya();
					e.init(level);
					e.setPosition(800, 1020);
					level.addEnemyMiya(e);
				}
				{
					EnemyMiya e = new EnemyMiya();
					e.init(level);
					e.setPosition(380, 1450);
					level.addEnemyMiya(e);
				}
				
			}
			
			@Override
			public void mapMaker(Pixmap terr, Pixmap gold) {
				{
					gold.setColor(Color.YELLOW);
					Polygon polygon =  PolygonTable.COLLECTION_DOODLE_4();
					polygon.setScale(1f, 1f);
					polygon.setPosition(300, 50);
					drawPolygon(polygon, gold);
				}
				{
					gold.setColor(Color.YELLOW);
					Polygon polygon =  PolygonTable.COLLECTION_DOODLE_4();
					polygon.setScale(1f, 1f);
					polygon.setRotation(180);
					polygon.setPosition(800, 50);
					drawPolygon(polygon, gold);
				}
				{
					gold.setColor(Color.CYAN);
					Polygon polygon =  PolygonTable.D_EAST_A();
					polygon.setScale(1f, 1f);
					polygon.setPosition(200, 550);
					drawPolygon(polygon, gold);
				}
				{
					gold.setColor(Color.CYAN);
					Polygon polygon =  PolygonTable.D_EAST_A();
					polygon.setScale(1f, 1f);
					polygon.setRotation(180);
					polygon.setPosition(800, 850);
					drawPolygon(polygon, gold);
				}
				{
					gold.setColor(Color.CYAN);
					Polygon polygon =  PolygonTable.CRISTAL_HD();
					polygon.setScale(1f, 1f);
					polygon.setRotation(180);
					polygon.setPosition(500, 1250);
					drawPolygon(polygon, gold);
				}
				
				{
					int h = 0;
					while(h<1600){
						drawPixmapDeco(gold, "stone2", -20, h);
						drawPixmapDeco(gold, "stone2", 860, h);
						h+=100;
					}
				}
			}
			@Override
			public void before(Level level) {
				{
					final ParallaxGroup pbg = new ParallaxGroup(Engine.getWidth(), Engine.getHeight(), new Vector2(30,0));
					pbg.addActor(new ParallaxLayer(pbg, new Image(bgTexture), new Vector2(1f,1), new Vector2(0,10000), new Vector2(0,0)));
					pbg.setY(2048-540+420);
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