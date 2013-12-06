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
import info.u250.digs.scenes.game.entity.GoldTowerEntity;
import info.u250.digs.scenes.game.entity.Ka;
import info.u250.digs.scenes.game.entity.Npc;
import info.u250.digs.scenes.ui.FireDeco;
import info.u250.digs.scenes.ui.ParticleEffectActor;

import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.Pixmap;
import com.badlogic.gdx.graphics.g2d.ParticleEffect;
import com.badlogic.gdx.graphics.g2d.TextureAtlas;
import com.badlogic.gdx.math.Polygon;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.scenes.scene2d.ui.Image;

public class Pack1V__06 extends HookLevelConfig {
	public Pack1V__06(){
		this.surface = "qvg/105.jpg";
		this.width = (int)Engine.getWidth() ;
		this.height = (int)Engine.getHeight();
		this.bottomColor = WebColors.SPRING_GREEN.get();
		this.topColor = WebColors.DARK_GREEN.get();
		this.lineHeight = 400;
		this.ascent = 20;
		this.segment = 80;
		this.gold = 50;
		this.npc = 30;
		this.ka = 10;
		this.time = 600;
		
		levelMakeCallback = new LevelMakeCallBack() {
			@Override
			public void after(Level level) {
				for(int i=0;i<npc;i++){
					Npc e = new Npc();
					e.init(level);
					e.setPosition(140,100);
					level.addNpc(e);
				}
				for(int i=0;i<ka;i++){
					Ka e = new Ka();
					e.init(level);
					e.setPosition(200+Digs.RND.nextFloat()*200, Engine.getHeight() + Digs.RND.nextFloat()*100);
					level.addKa(e);
				}
			}
			
			@Override
			public void mapMaker(Pixmap terr, Pixmap gold) {
				{
					gold.setColor(Color.YELLOW);
					Polygon polygon =  PolygonTable.XUANYUNBUFF();
					polygon.setScale(0.6f, 0.6f);
					polygon.setPosition(600, 200);
					drawPolygon(polygon, gold);
				}
				{
					gold.setColor(Color.CLEAR);
					fillCircle(terr, 140, 100, 25);
					fillRect(terr, 140, 80, 400, 10);
				}
			}
			@Override
			public void before(Level level) {
				TextureAtlas atlas = Engine.resource("All");
				final ParallaxGroup pbg = new ParallaxGroup(Engine.getWidth(), Engine.getHeight(), new Vector2(50,0));
				pbg.addActor(new ParallaxLayer(pbg, new Image(atlas.findRegion("cloud")), new Vector2(1,1), new Vector2(500,1000), new Vector2(300,350)));
				level.addActor(pbg);
				
				
				{
					ParticleEffect e = Engine.resource("Effect");
					ParticleEffectActor p = new ParticleEffectActor(e,"balloon");
					p.setColor(WebColors.GREEN_YELLOW.get());
					p.setPosition(100, 0);
					level.addActor(p);
				}
				FireDeco deco = new FireDeco();
				deco.setPosition(100, 50);
				level.addActor(deco);
				
				GoldTowerEntity dock = new GoldTowerEntity();
				dock.setY(lineHeight);
				level.addGoldDock(dock);
			}
		};
	}
}