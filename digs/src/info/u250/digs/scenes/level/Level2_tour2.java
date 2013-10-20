package info.u250.digs.scenes.level;

import info.u250.c2d.engine.Engine;
import info.u250.c2d.graphic.WebColors;
import info.u250.digs.Digs;
import info.u250.digs.PolygonTable;
import info.u250.digs.scenes.game.Level;
import info.u250.digs.scenes.game.LevelConfig;
import info.u250.digs.scenes.game.entity.GoldDock;
import info.u250.digs.scenes.game.entity.Npc;
import info.u250.digs.scenes.ui.ParticleEffectActor;

import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.Pixmap;
import com.badlogic.gdx.graphics.g2d.ParticleEffect;
import com.badlogic.gdx.math.Polygon;

public class Level2_tour2 extends LevelConfig {
	public Level2_tour2(){
		this.surface = "texs/green013.jpg";
		this.width = (int)Engine.getWidth();
		this.height = 512;
		this.bottomColor = WebColors.BLACK.get();
		this.topColor = WebColors.KHAKI.get();
		this.lineHeight = 300;
		this.segment = 2;
		
		
		callback = new LevelCallBack() {
			@Override
			public void after(Level level) {
				
				for(int i=0;i<5;i++){
					Npc e = new Npc();
					e.init(level);
					e.setPosition(200+Digs.RND.nextFloat()*200, Engine.getHeight() + Digs.RND.nextFloat()*100);
					level.addNpc(e);
				}
				
			}
			
			@Override
			public void mapMaker(Pixmap terr, Pixmap gold) {
				gold.setColor(Color.YELLOW);
				Polygon polygon =  PolygonTable.WONDER_PART_8;
				polygon.setScale(1f, 1f);
				polygon.setPosition(650, 400);
				drawPolygon(polygon, gold);
			}

			@Override
			public void before(Level level) {
				ParticleEffect e = Engine.resource("Effect");
				ParticleEffectActor p = new ParticleEffectActor(e,"snow");
				p.setPosition(0, Engine.getHeight());
				level.addActor(p);
				
				GoldDock dock = new GoldDock();
				dock.setY(lineHeight);
				level.addGoldDock(dock);
			}
		};
	}
}
