package info.u250.digs.scenes.game;

import info.u250.c2d.engine.Engine;
import info.u250.digs.scenes.game.entity.KillCircle;
import info.u250.digs.scenes.game.entity.Npc;
import info.u250.digs.scenes.game.entity.InOutTrans;
import info.u250.digs.scenes.game.entity.Stepladder;

import java.util.Random;

import com.badlogic.gdx.graphics.Color;

public class LevelConfig {
	public interface LevelCallBack{
		void call(Level level);
	}
	public String surface = "texs/brown096.jpg";
	public Color bottomColor = null;
	public Color topColor = null;
	
	public int width = 2048;
	public int height = 512;
	
	public int lineHeight = 450;
	public int segment = 8;
	
	public LevelCallBack callback = new LevelCallBack() {
		Random random = new Random();
		@Override
		public void call(Level level) {

			KillCircle ray = new KillCircle(550, 50, 100,Color.WHITE);
			level.addKillCircle(ray);
			KillCircle ray2 = new KillCircle(300, 0,50,Color.BLUE);
			level.addKillCircle(ray2);
			KillCircle ray3 = new KillCircle(100, 0,76,Color.GREEN);
			level.addKillCircle(ray3);
			KillCircle ray4 = new KillCircle(800, -20,88,Color.CYAN);
			level.addKillCircle(ray4);
			
			Stepladder ladder = new Stepladder(15, 200,100);
			level.addStepladder(ladder);
			
			for(int i=0;i<200;i++){
				Npc e = new Npc();
				e.init(level);
				e.setPosition(200+random.nextFloat()*200, Engine.getHeight() + random.nextFloat()*100);
				level.addNpc(e);
			}
			
			InOutTrans inout = new InOutTrans(300,150,500,250);
			level.addInOutTrans(inout);
			
		}
	};
}
