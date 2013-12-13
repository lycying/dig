package info.u250.digs.scenes.level.pack.s1;

import info.u250.c2d.engine.Engine;
import info.u250.c2d.graphic.WebColors;
import info.u250.digs.Digs;
import info.u250.digs.scenes.game.Level;
import info.u250.digs.scenes.game.LevelMakeCallBack;
import info.u250.digs.scenes.game.LineLevelConfig;
import info.u250.digs.scenes.game.entity.GoldTowerEntity;
import info.u250.digs.scenes.game.entity.Npc;
import info.u250.digs.scenes.ui.ParticleEffectActor;

import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.Pixmap;
import com.badlogic.gdx.graphics.g2d.ParticleEffect;

public class Pack1V__18 extends LineLevelConfig {
	public Pack1V__18(){
		this.surface = "117";
		this.width = (int)Engine.getWidth()-SCROLL_WIDTH ;
		this.height = 840;
		this.bottomColor = WebColors.WHEAT.get();
		this.topColor = WebColors.FOREST_GREEN.get();
		this.lineHeight = 320+300;
		
		this.gold = 100;
		this.npc = 20;
		this.time = 600;
		
		levelMakeCallback = new LevelMakeCallBack() {
			
			@Override
			public void after(Level level) {
				
				for(int i=0;i<npc;i++){
					Npc e = new Npc();
					e.init(level);
					e.setPosition(200+Digs.RND.nextFloat()*200, height + Digs.RND.nextFloat()*100);
					level.addNpc(e);
				}
				
			}
			
			@Override
			public void mapMaker(Pixmap terr, Pixmap gold) {
				
				{
					for(int i=0;i<80;i++){
						gold.setColor(Color.YELLOW);
						fillCircle(gold,(int)(60+Digs.RND.nextFloat()*800), (int)(100+Digs.RND.nextFloat()*500), 3);
					}
					for(int i=0;i<20;i++){
						gold.setColor(Color.CYAN);
						fillCircle(gold,(int)(60+Digs.RND.nextFloat()*800), (int)(100+Digs.RND.nextFloat()*500), 2);
					}
				}
				
				{
					int w = 150;
					while(w<900){
						drawPixmapDeco(terr, "tree7", w, lineHeight-2,0.5f+0.5f*Digs.RND.nextFloat());
						w +=200;
					}
				}
			}
			@Override
			public void before(Level level) {
				ParticleEffect e = Engine.resource("Effect");
				ParticleEffectActor p = new ParticleEffectActor(e,"effect-dot-mu");
				p.setPosition(Engine.getWidth(), height-300);
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