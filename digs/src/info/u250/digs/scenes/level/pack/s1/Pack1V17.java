package info.u250.digs.scenes.level.pack.s1;

import info.u250.c2d.engine.Engine;
import info.u250.c2d.graphic.WebColors;
import info.u250.digs.Digs;
import info.u250.digs.scenes.game.Level;
import info.u250.digs.scenes.game.LevelMakeCallBack;
import info.u250.digs.scenes.game.LineLevelConfig;
import info.u250.digs.scenes.game.entity.GoldTowerEntity;
import info.u250.digs.scenes.game.entity.Ka;
import info.u250.digs.scenes.game.entity.Npc;
import info.u250.digs.scenes.game.entity.StepladderEntity;
import info.u250.digs.scenes.ui.ParticleEffectActor;

import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.Pixmap;
import com.badlogic.gdx.graphics.g2d.ParticleEffect;

public class Pack1V17 extends LineLevelConfig {
	public Pack1V17(){
		this.surface = "qvg/116.jpg";
		this.width = (int)Engine.getWidth() -SCROLL_WIDTH;
		this.height = 840;
		this.bottomColor = WebColors.DARK_SLATE_BLUE.get();
		this.topColor = WebColors.BLACK.get();
		this.lineHeight = 360+300;
		
		this.gold = 100;
		this.npc = 20;
		this.ka = 10;
		this.time = 600;
		
		levelMakeCallback = new LevelMakeCallBack() {
			
			@Override
			public void after(Level level) {
				{
					StepladderEntity e = new StepladderEntity(35, 100, 0);
					level.addStepladder(e);
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
					e.setPosition(400+Digs.RND.nextFloat()*50, 120);
					level.addKa(e);
				}
			}
			
			@Override
			public void mapMaker(Pixmap terr, Pixmap gold) {
				{
					terr.setColor(Color.CLEAR);
					fillCircle(terr, 400, 100, 30);
					fillCircle(terr, 430, 100, 30);
					fillCircle(terr, 460, 100, 30);
				}
				{
					for(int i=0;i<60;i++){
						gold.setColor(Color.YELLOW);
						fillCircle(gold,(int)(200+Digs.RND.nextFloat()*700), (int)(100+Digs.RND.nextFloat()*500), 3);
					}
				}
				drawPixmapDeco(gold, "stone2", -20, -20);
				drawPixmapDeco(gold, "stone2", 840, -20);
				
				{
					int w = 100;
					while(w<900){
						drawPixmapDeco(terr, "tree", w, lineHeight-2,0.5f+0.5f*Digs.RND.nextFloat());
						w +=150;
					}
				}
			}
			@Override
			public void before(Level level) {
				ParticleEffect e = Engine.resource("Effect");
				ParticleEffectActor p = new ParticleEffectActor(e,"snow");
				p.setPosition(0, height);
				level.addActor(p);
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