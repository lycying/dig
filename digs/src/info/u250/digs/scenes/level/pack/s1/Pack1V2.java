package info.u250.digs.scenes.level.pack.s1;

import info.u250.c2d.engine.Engine;
import info.u250.c2d.graphic.WebColors;
import info.u250.c2d.graphic.parallax.ParallaxGroup;
import info.u250.c2d.graphic.parallax.ParallaxLayer;
import info.u250.digs.Digs;
import info.u250.digs.PolygonTable;
import info.u250.digs.scenes.game.Level;
import info.u250.digs.scenes.game.LevelConfig;
import info.u250.digs.scenes.game.LevelMakeCallBack;
import info.u250.digs.scenes.game.entity.GoldTowerEntity;
import info.u250.digs.scenes.game.entity.Npc;
import info.u250.digs.scenes.ui.ParticleEffectActor;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.Pixmap;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.ParticleEffect;
import com.badlogic.gdx.math.Polygon;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.scenes.scene2d.ui.Image;

public class Pack1V2 extends LevelConfig {
	public Pack1V2(){
		this.surface = "qvg/101.jpg";
		this.width = (int)Engine.getWidth() ;
		this.height = (int)Engine.getHeight();
		this.bottomColor = WebColors.MEDIUM_ORCHID.get();
		this.topColor = WebColors.BLACK.get();
		this.lineHeight = 360;
		this.ascent = 15;
		this.segment = 50;
		this.gold = 200;
		this.npc = 20;
		this.time = 600;
		
		levelMakeCallback = new LevelMakeCallBack() {
			final Texture ship1 = new Texture(Gdx.files.internal("wb/ship4.png"));
			@Override
			public void dispose() {
				ship1.dispose();
			}
			@Override
			public void after(Level level) {
				
				for(int i=0;i<npc;i++){
					Npc e = new Npc();
					e.init(level);
					e.setPosition(200+Digs.RND.nextFloat()*200, Engine.getHeight() + Digs.RND.nextFloat()*100);
					level.addNpc(e);
				}
				
			}
			
			@Override
			public void mapMaker(Pixmap terr, Pixmap gold) {
				{
				gold.setColor(Color.YELLOW);
				Polygon polygon =  PolygonTable.LL_GIRL_IN_A_BOX();
				polygon.setScale(1f, 1f);
				polygon.setPosition(700, 100);
				drawPolygon(polygon, gold);
				}
				{
					drawPixmapDeco(gold, "stone1", -20, -50);
					drawPixmapDeco(terr, "deco3", 900, lineHeight-ascent*2);
				}
			}

			@Override
			public void before(Level level) {
				final ParallaxGroup pbg = new ParallaxGroup(Engine.getWidth(), Engine.getHeight(), new Vector2(100,0));
				pbg.addActor(new ParallaxLayer(pbg, new Image(ship1), new Vector2(2.5f,1), new Vector2(2000,20000), new Vector2(900,430)));
				
				level.addActor(pbg);
				
				{
					ParticleEffect e = Engine.resource("Effect");
					ParticleEffectActor p = new ParticleEffectActor(e,"balloon");
					p.setColor(WebColors.YELLOW.get());
					p.setPosition(100, 0);
					level.addActor(p);
				}
				
				GoldTowerEntity dock = new GoldTowerEntity();
				dock.setY(lineHeight);
				level.addGoldDock(dock);
			}
		};
	}
}