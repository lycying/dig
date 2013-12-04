package info.u250.digs.scenes.game;

import info.u250.c2d.engine.Engine;
import info.u250.digs.Digs;
import info.u250.digs.scenes.game.callback.DefaultLevelCompleteCallback;
import info.u250.digs.scenes.game.entity.Boss;
import info.u250.digs.scenes.game.entity.GoldTowerEntity;
import info.u250.digs.scenes.game.entity.Npc;

import java.util.Random;

import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.Pixmap;

public class LevelConfig {
	public static final int SCROLL_WIDTH = 30;
	//for level information auto make
	public int pack = 0;
	public int idx  = 0;
	public String idxName  = "";
	//auto information auto make end
	
	public int gold = -1;
	public int ka = -1;
	public int npc = -1;
	public int enemy = -1;
	//the second : time up !!!
	public int time = -1; 
	public String surface = "qvg/000.png";
	public Color bottomColor = null;
	public Color topColor = null;
	
	public int width = (int)Engine.getWidth();
	public int height = (int)Engine.getHeight();
	
	public int lineHeight = 450;
	public int segment = 8;
	public int ascent = 50;
	
	public LevelCompleteCallback levelCompleteCallback = new DefaultLevelCompleteCallback();
	public LevelMakeCallBack levelMakeCallback = new LevelMakeCallBack() {
		Random random = new Random();
		@Override
		public void after(Level level) {
//			KillCircleEntity ray = new KillCircleEntity(550, 50, 100,Color.WHITE);
//			level.addKillCircle(ray);
//			KillCircleEntity ray2 = new KillCircleEntity(300, 0,50,Color.BLUE);
//			level.addKillCircle(ray2);
//			KillCircleEntity ray3 = new KillCircleEntity(100, 0,76,Color.GREEN);
//			level.addKillCircle(ray3);
//			KillCircleEntity ray4 = new KillCircleEntity(800, -20,88,Color.CYAN);
//			level.addKillCircle(ray4);
//			
//			StepladderEntity ladder = new StepladderEntity(15, 200,100);
//			level.addStepladder(ladder);
			for(int i=0;i<1;i++){
				Boss e = new Boss();
				e.setBossLandHeight(50);
				e.init(level);
				e.setPosition(500+Digs.RND.nextFloat()*200, Engine.getHeight() + Digs.RND.nextFloat()*100);
				level.addBoss(e);
			}
			for(int i=0;i<20;i++){
				Npc e = new Npc();
				e.init(level);
				e.setPosition(400+random.nextFloat()*50, Engine.getHeight() + random.nextFloat()*300);
				level.addNpc(e);
			}
			
//			TeleportEntity inout = new TeleportEntity(300,150,500,250);
//			level.addInOutTrans(inout);
			
		}
		
		@Override
		public void mapMaker(Pixmap terr, Pixmap gold) {
//			gold.setColor(Color.YELLOW);
//			Polygon polygon =  PolygonTable.WALLACE_128;
//			polygon.setPosition(200, 50);
//			drawPolygon(polygon, gold);
//			
//			gold.setColor(Color.CYAN);
//			Polygon bomb =  PolygonTable.ADIUM;
//			bomb.setPosition(400, 50);
//			bomb.setRotation(50);
//			drawPolygon(bomb, gold);
		}

		@Override
		public void before(Level level) {
			GoldTowerEntity dock = new GoldTowerEntity();
			dock.setY(LevelConfig.this.lineHeight);
			level.addGoldDock(dock);
		}
	};
}
