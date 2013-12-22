package info.u250.digs.scenes.level.pack.s2;

import info.u250.c2d.engine.Engine;
import info.u250.c2d.graphic.WebColors;
import info.u250.digs.Digs;
import info.u250.digs.scenes.game.FaceLevelConfig;
import info.u250.digs.scenes.game.Level;
import info.u250.digs.scenes.game.LevelMakeCallBack;
import info.u250.digs.scenes.game.entity.Boss;
import info.u250.digs.scenes.game.entity.GoldTowerEntity;
import info.u250.digs.scenes.game.entity.Npc;

import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.Pixmap;
import com.badlogic.gdx.math.Vector2;

public class S2Lvl__08 extends FaceLevelConfig {
	public S2Lvl__08(){
		this.faces = new Vector2[]{
				new Vector2(0,100),
				new Vector2(100,460),
				new Vector2(200,360),
				new Vector2(400,540),
				new Vector2(900,100),
				new Vector2(960,100)
		};
		this.surface = "207";
		this.width = (int)Engine.getWidth() ;
		this.height = (int)Engine.getHeight();
		this.bottomColor = WebColors.NAVY.get();
		this.topColor = WebColors.BLACK.get();
		this.gold = 100;
		this.npc = 20;
		this.time = 600;
		
		levelMakeCallback = new LevelMakeCallBack() {
			@Override
			public void after(Level level) {
				
				for(int i=0;i<npc;i++){
					Npc e = new Npc();
					e.init(level);
					e.setPosition(200+Digs.RND.nextFloat()*200, Engine.getHeight() + Digs.RND.nextFloat()*100);
					level.addNpc(e);
				}
				{
					Boss e = new Boss();
					e.init(level);
					e.setBossLandHeight(100);
					e.setPosition(10, 300);
					level.addBoss(e);
				}
				{
					Boss e = new Boss();
					e.init(level);
					e.setBossLandHeight(100);
					e.setPosition(20, 300);
					level.addBoss(e);
				}
				{
					Boss e = new Boss();
					e.init(level);
					e.setBossLandHeight(100);
					e.setPosition(30, 300);
					level.addBoss(e);
				}
				{
					Boss e = new Boss();
					e.init(level);
					e.setBossLandHeight(100);
					e.setPosition(930, 300);
					level.addBoss(e);
				}
				{
					Boss e = new Boss();
					e.init(level);
					e.setBossLandHeight(100);
					e.setPosition(910, 300);
					level.addBoss(e);
				}
				{
					Boss e = new Boss();
					e.init(level);
					e.setBossLandHeight(100);
					e.setPosition(940, 300);
					level.addBoss(e);
				}
			}
			
			@Override
			public void mapMaker(Pixmap terr, Pixmap gold) {
				gold.setColor(Color.YELLOW);
				fillRect(gold, 100, 50, 800, 50);
			}

			@Override
			public void before(Level level) {
				
				GoldTowerEntity dock = new GoldTowerEntity();
				dock.setY(360);
				level.addGoldDock(dock);
			}
		};
	}
}