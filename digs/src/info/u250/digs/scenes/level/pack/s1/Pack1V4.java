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
import info.u250.digs.scenes.game.entity.KillCircleEntity;
import info.u250.digs.scenes.game.entity.Npc;
import info.u250.digs.scenes.ui.ParticleEffectActor;

import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.Pixmap;
import com.badlogic.gdx.graphics.g2d.ParticleEffect;
import com.badlogic.gdx.graphics.g2d.TextureAtlas;
import com.badlogic.gdx.math.Polygon;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.scenes.scene2d.ui.Image;

public class Pack1V4 extends LevelConfig {
	public Pack1V4(){
		this.surface = "qvg/103.jpg";
		this.width = (int)Engine.getWidth() ;
		this.height = (int)Engine.getHeight();
		this.bottomColor = WebColors.LIGHT_PINK.get();
		this.topColor = WebColors.DEEP_PINK.get();
		this.lineHeight = 380;
		this.ascent = 25;
		this.segment = 30;
		this.gold = 200;
		this.npc = 30;
		this.time = 60*5;
		
		levelMakeCallback = new LevelMakeCallBack() {
			@Override
			public void after(Level level) {
				{
					KillCircleEntity kill = new KillCircleEntity(100, 100, 30, Color.CYAN);
					level.addKillCircle(kill);
				}
				{
					KillCircleEntity kill = new KillCircleEntity(320, 100, 30, Color.GREEN);
					level.addKillCircle(kill);
				}
				{
					KillCircleEntity kill = new KillCircleEntity(750, 100, 200, Color.BLUE);
					level.addKillCircle(kill);
				}
				for(int i=0;i<npc;i++){
					Npc e = new Npc();
					e.init(level);
					e.setPosition(200+Digs.RND.nextFloat()*200, Engine.getHeight() + Digs.RND.nextFloat()*100);
					level.addNpc(e);
				}
//				{
//					TeleportEntity inout = new TeleportEntity(220,60,600,500,new Color(1,1,1,0.5f),new Color(1,0,0,0.5f));
//					level.addInOutTrans(inout);
//				}
//				{
//					TeleportEntity inout = new TeleportEntity(220,20,720,160);
//					level.addInOutTrans(inout);
//				}
			}
			
			@Override
			public void mapMaker(Pixmap terr, Pixmap gold) {
				{
					gold.setColor(Color.YELLOW);
					Polygon polygon =  PolygonTable.WARRIOR_BY_ANIMATEDARCTICSTUDIO_D69O6JL();
					polygon.setScale(0.4f, 0.4f);
					polygon.setPosition(200, 100);
					drawPolygon(polygon, gold);
				}
				{
					gold.setColor(Color.YELLOW);
					Polygon polygon =  PolygonTable.PEOPLEXXXXX();
					polygon.setScale(1f, 1f);
					polygon.setPosition(700, 50);
					drawPolygon(polygon, gold);
				}
				{
					gold.setColor(Color.WHITE);
					Polygon polygon =  PolygonTable.WONDER_PART_6();
					polygon.setScale(2f, 1f);
					polygon.setPosition(500, 450);
					drawPolygon(polygon, gold);
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
					p.setColor(WebColors.TOMATO.get());
					p.setPosition(100, 100);
					level.addActor(p);
				}
				
				GoldTowerEntity dock = new GoldTowerEntity();
				dock.setY(lineHeight);
				level.addGoldDock(dock);
			}
		};
	}
}