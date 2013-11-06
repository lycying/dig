package info.u250.digs.scenes.level.pack.guide;

import info.u250.c2d.engine.Engine;
import info.u250.c2d.graphic.WebColors;
import info.u250.digs.Digs;
import info.u250.digs.PolygonTable;
import info.u250.digs.scenes.game.Level;
import info.u250.digs.scenes.game.LevelMakeCallBack;
import info.u250.digs.scenes.game.LevelConfig;
import info.u250.digs.scenes.game.entity.GoldTowerEntity;
import info.u250.digs.scenes.game.entity.Npc;
import info.u250.digs.scenes.ui.HintOnScreen;
import info.u250.digs.scenes.ui.ParticleEffectActor;

import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.Pixmap;
import com.badlogic.gdx.graphics.g2d.ParticleEffect;
import com.badlogic.gdx.math.Polygon;

public class Tour5 extends LevelConfig {
	public Tour5(){
		this.surface = "texs/lgrey087.gif";
		this.width = (int)Engine.getWidth() ;
		this.height = (int)Engine.getHeight();
		this.bottomColor = WebColors.BLACK.get();
		this.topColor = WebColors.KHAKI.get();
		this.lineHeight = 300;
		this.segment = 1;
		this.aim = 10;
		
		
		levelMakeCallback = new LevelMakeCallBack() {
			@Override
			public void after(Level level) {
				for(int i=0;i<30;i++){
					Npc e = new Npc();
					e.init(level);
					e.setPosition(500, Engine.getHeight() + Digs.RND.nextFloat()*500);
					level.addNpc(e);
				}
			}
			
			@Override
			public void mapMaker(Pixmap terr, Pixmap gold) {
				{gold.setColor(Color.CYAN);
				Polygon polygon =  PolygonTable.BLACK_CAT;
				polygon.setScale(2f,2f);
				polygon.setPosition(550, 100);
				drawPolygon(polygon, gold);}
				{
				gold.setColor(Color.YELLOW);
				Polygon polygon =  PolygonTable.CAKE_7;
				polygon.setScale(1f,1f);
				polygon.setPosition(700, 100);
				drawPolygon(polygon, gold);
				}
			}

			@Override
			public void before(Level level) {
				ParticleEffect e = Engine.resource("Effect");
				ParticleEffectActor p = new ParticleEffectActor(e,"salut"){
					float accum = 0;
					float delay = 1;
					@Override
					public void act(float delta) {
						accum += delta;
						if(accum>=delay){
							accum = 0;
							setPosition(Digs.RND.nextFloat()*Engine.getWidth(), 300+150*Digs.RND.nextFloat());
							delay = Digs.RND.nextFloat()*2+0.5f;
							Engine.getSoundManager().playSound("SoundLvl5Bang");
							this.getEmitter().start();
						}
						super.act(delta);
					}
				};
				p.setPauseWithEngine(true);//when pause , stop it
				p.setPosition(0, 320);
				level.addActor(p);
				
				GoldTowerEntity dock = new GoldTowerEntity();
				dock.setY(lineHeight);
				level.addGoldDock(dock);
				
				{
					HintOnScreen hint = new HintOnScreen("How to tell if your cat is plotting to kill you? Is it or is it?","hint2",Color.WHITE,250);
					hint.pack();
					hint.setPosition(530, 350);
					level.addActor(hint);
				}
			}
		};
	}
}
