package info.u250.digs.scenes.game;

import info.u250.c2d.engine.Engine;
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
			Stepladder ladder = new Stepladder(15, 200,100);
			level.addActor(ladder);
			level.ladders.add(ladder);
			
			for(int i=0;i<150;i++){
				Npc e = new Npc();
				e.init(level);
				e.setPosition(200+random.nextFloat()*200, Engine.getHeight() + random.nextFloat()*100);
				level.addActor(e);
				level.npcs.add(e);
			}
			
			InOutTrans inout = new InOutTrans(300,150,500,250);
			level.addActor(inout);
			level.inouts.add(inout);
			
		}
	};
}
