package info.u250.digs.scenes.level.pack.s2;

import info.u250.c2d.engine.Engine;
import info.u250.c2d.graphic.WebColors;
import info.u250.digs.Digs;
import info.u250.digs.scenes.game.FaceLevelConfig;
import info.u250.digs.scenes.game.Level;
import info.u250.digs.scenes.game.LevelMakeCallBack;
import info.u250.digs.scenes.game.entity.EnemyMiya;
import info.u250.digs.scenes.game.entity.GoldTowerEntity;
import info.u250.digs.scenes.game.entity.Npc;
import info.u250.digs.scenes.ui.ParticleEffectActor;

import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.Pixmap;
import com.badlogic.gdx.graphics.g2d.ParticleEffect;
import com.badlogic.gdx.math.Vector2;

//carry as many of gold in center time
public class S2Lvl__07 extends FaceLevelConfig {
	public S2Lvl__07(){
		this.faces = new Vector2[41];
		for(int i=0;i<faces.length;i++){
			faces[i] = new Vector2();
			faces[i].x = 24*i;
			int dx = i%4;
			if(0==dx){
				faces[i].y = 370;
			}else if(1==dx){
				faces[i].y = 100;
			}else if(2==dx){
				faces[i].y = 375;
			}else if(3==dx){
				faces[i].y = 360;
			}
		}
		this.lightLine = 360;
		this.lineWidth = 5;
		this.lightLine = 360;
		this.surface = "qvg/206.png";
		this.width = (int)Engine.getWidth() ;
		this.height = (int)Engine.getHeight();
		this.bottomColor = WebColors.MIDNIGHT_BLUE.get();
		this.topColor = WebColors.BLACK.get();
		this.gold = 100;
		this.npc = 20;
		this.enemy = 6;
		this.time = 60*5;
		
		levelMakeCallback = new LevelMakeCallBack() {
			
			@Override
			public void after(Level level) {
				
				for(int i=0;i<npc;i++){
					Npc e = new Npc();
					e.init(level);
					e.setPosition(230, Engine.getHeight() + Digs.RND.nextFloat()*1000);
					level.addNpc(e);
				}
				for(int w =400;w<width;w+=96){
					EnemyMiya e = new EnemyMiya();
					e.init(level);
					e.setPosition(w, height);
					level.addEnemyMiya(e);
				}
			}
			
			@Override
			public void mapMaker(Pixmap terr, Pixmap gold) {
				for(int w = 100;w<width;w+=96){
					gold.setColor(Color.YELLOW);
					fillRect(gold, w, 50, 30, 20);
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
				p.setColor(Color.WHITE);
				level.addActor(p);
				
				GoldTowerEntity dock = new GoldTowerEntity();
				dock.setY(lightLine);
				level.addGoldDock(dock);
			}
		};
	}
}