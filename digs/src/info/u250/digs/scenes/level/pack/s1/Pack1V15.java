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
import info.u250.digs.scenes.game.entity.GoldTowerEntity;
import info.u250.digs.scenes.game.entity.Ka;
import info.u250.digs.scenes.game.entity.KillCircleEntity;
import info.u250.digs.scenes.game.entity.Npc;
import info.u250.digs.scenes.game.entity.StepladderEntity;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.Pixmap;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.math.Polygon;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.scenes.scene2d.ui.Image;

public class Pack1V15 extends LevelConfig {
	public Pack1V15(){
		this.surface = "qvg/114.png";
		this.width = (int)Engine.getWidth() -SCROLL_WIDTH ;
		this.height = 840;
		this.bottomColor = WebColors.MAROON.get();
		this.topColor = WebColors.PERU.get();
		this.lineHeight = 360+300;
		this.ascent = 25;
		this.segment = 20;
		this.gold = 100;
		this.npc = 20;
		this.ka = 20;
		this.time = 600;
		
		levelMakeCallback = new LevelMakeCallBack() {
			final Texture bgTexture = new Texture(Gdx.files.internal("wb/cloud.png"));
			@Override
			public void dispose() {
				bgTexture.dispose();
			}
			@Override
			public void after(Level level) {
				{
					StepladderEntity e = new StepladderEntity(15, 200, 300);
					level.addStepladder(e);
				}
				{
					StepladderEntity e = new StepladderEntity(13, 700, 300);
					level.addStepladder(e);
				}
				int h = 0;
				while(h < 600){
					KillCircleEntity e = new KillCircleEntity(900, h, 60, WebColors.WHEAT.get());
					level.addKillCircle(e);
					h += 120;
				}
				h = 0;
				while(h < 600){
					KillCircleEntity e = new KillCircleEntity(-40, h, 60, WebColors.WHEAT.get());
					level.addKillCircle(e);
					h += 120;
				}
				for(int i=0;i<npc;i++){
					Npc e = new Npc();
					e.init(level);
					e.setPosition(200+Digs.RND.nextFloat()*200, height + Digs.RND.nextFloat()*100);
					level.addNpc(e);
				}
				for(int i=0;i<ka;i++){
					Ka e = new Ka();
					e.init(level);
					e.setPosition(400+Digs.RND.nextFloat()*50, 420);
					level.addKa(e);
				}
			}
			
			@Override
			public void mapMaker(Pixmap terr, Pixmap gold) {
				{
					gold.setColor(Color.CYAN);
					fillRect(gold, 100, 500, width-200 ,10);
//					fillRect(gold, 200, 300, width-400 ,5);
					Polygon polygon = PolygonTable.COVEROPENEDSCENE9();
					polygon.setScale(1.5f, 0.2f);
					polygon.setPosition(200, 300);
					drawPolygon(polygon, gold);
					gold.setColor(Color.YELLOW);
					int w = 100;
					while(w<width-100){
						fillRect(gold, w, 150, 20,20);
						fillCircle(gold, w, 50, 20);
						w+=120;
					}
				}
				terr.setColor(Color.CLEAR);
				fillCircle(terr, 400, 400, 30);
				fillCircle(terr, 430, 400, 30);
				fillCircle(terr, 460, 400, 30);
				drawPixmapDeco(gold, "stone2", -20, -20);
				drawPixmapDeco(gold, "stone2", 840, -20);
			}
			@Override
			public void before(Level level) {
				{
					final ParallaxGroup pbg = new ParallaxGroup(Engine.getWidth(), Engine.getHeight(), new Vector2(30,0));
					pbg.addActor(new ParallaxLayer(pbg, new Image(bgTexture), new Vector2(1f,1), new Vector2(0,1000), new Vector2(0,700)));
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