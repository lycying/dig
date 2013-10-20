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

public class Level1_tour1 extends LevelConfig {
	public Level1_tour1(){
		this.surface = "texs/brown091.png";
		this.width = (int)Engine.getWidth();
		this.height = 512;
		this.bottomColor = WebColors.BLACK.get();
		this.topColor = WebColors.DARK_SLATE_GRAY.get();
		this.lineHeight = 300;
		this.segment = 1;
		
		
		callback = new LevelCallBack() {
			@Override
			public void after(Level level) {

				
				for(int i=0;i<5;i++){
					Npc e = new Npc();
					e.init(level);
					e.setPosition(200+Digs.RND.nextFloat()*200, Engine.getHeight() + Digs.RND.nextFloat()*100);
					level.addNpc(e);
				}
				
				ParticleEffect e = Engine.resource("Effect");
				ParticleEffectActor p = new ParticleEffectActor(e,"thousand");
				p.setPosition(50, Engine.getHeight());
				p.setPosition(50, Engine.getHeight());
				level.addActor(p);
			}
			
			@Override
			public void mapMaker(Pixmap terr, Pixmap gold) {
				gold.setColor(Color.YELLOW);
				Polygon polygon =  PolygonTable.FORMPOLYGON_128;
				polygon.setScale(0.2f, 0.2f);
				polygon.setPosition(500, 230);
				drawPolygon(polygon, gold);
			}

			@Override
			public void before(Level level) {
				GoldDock dock = new GoldDock();
				dock.setY(lineHeight);
				level.addGoldDock(dock);
				
				GoldDock dock2 = new GoldDock();
				dock2.setY(lineHeight);
				dock2.setX(Engine.getWidth()-dock2.getWidth());
				level.addGoldDock(dock2);
			}
		};
	}
}
