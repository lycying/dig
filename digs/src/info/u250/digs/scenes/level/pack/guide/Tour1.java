package info.u250.digs.scenes.level.pack.guide;

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
import info.u250.digs.scenes.ui.HintOnScreen;
import info.u250.digs.scenes.ui.ParticleEffectActor;

import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.Pixmap;
import com.badlogic.gdx.graphics.g2d.ParticleEffect;
import com.badlogic.gdx.graphics.g2d.TextureAtlas;
import com.badlogic.gdx.math.Polygon;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.scenes.scene2d.actions.Actions;
import com.badlogic.gdx.scenes.scene2d.ui.Image;
/*
 * this tour show the basic operation that we should 
 * carry one gold to home.
 */
public class Tour1 extends LevelConfig {
	public Tour1(){
		this.surface = "texs/brown091.png";
		this.width = (int)Engine.getWidth() ;
		this.height = (int)Engine.getHeight();
		this.bottomColor = WebColors.BLACK.get();
		this.topColor = WebColors.DARK_SLATE_GRAY.get();
		this.lineHeight = 300;
		this.segment = 1;
		this.gold = 1;// once you got a gold , you win!
		this.time = 3*60;
		this.npc = 5;
		
		levelMakeCallback = new LevelMakeCallBack() {
			@Override
			public void after(Level level) {

				
				for(int i=0;i<npc;i++){
					Npc e = new Npc();
					e.init(level);
					e.setPosition(200+Digs.RND.nextFloat()*200, Engine.getHeight() + Digs.RND.nextFloat()*100);
					e.setDirection(1);
					level.addNpc(e);
				}
				
				{
					HintOnScreen hint = new HintOnScreen("Bring the gold home!","hint4",Color.BLACK,100);
					hint.pack();
					hint.setPosition(520, 200);
					hint.setColor(new Color(1,1,1,0.8f));
					level.addActor(hint);
					}
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

				TextureAtlas atlas = Engine.resource("All");
				final ParallaxGroup pbg = new ParallaxGroup(Engine.getWidth(), Engine.getHeight(), new Vector2(50,0));
				pbg.addActor(new ParallaxLayer(pbg, new Image(atlas.findRegion("cloud")), new Vector2(1,1), new Vector2(500,1000), new Vector2(300,350)));
				level.addActor(pbg);
				
				ParticleEffect e = Engine.resource("Effect");
				ParticleEffectActor p = new ParticleEffectActor(e,"effect-dot-mu");
				p.setPosition(Engine.getWidth(), 200);
				level.addActor(p);
				
				{
					HintOnScreen hint = new HintOnScreen("They will walk straight forward until they hit a wall or get bored.","hint1",Color.WHITE,300);
					hint.pack();
					hint.setPosition(450, 450);
					level.addActor(hint);
					}
				{
					HintOnScreen hint = new HintOnScreen("Home!","hint4",Color.BLACK,60);
					hint.pack();
					hint.addAction(Actions.forever(Actions.sequence(Actions.moveBy(10, 0,0.2f),Actions.moveBy(-10, 0,0.2f))));
					hint.setPosition(90, 320);
					level.addActor(hint);
					}
				
				GoldTowerEntity dock = new GoldTowerEntity();
				dock.setY(lineHeight);
				level.addGoldDock(dock);
				
				GoldTowerEntity dock2 = new GoldTowerEntity();
				dock2.setY(lineHeight);
				dock2.setX(Engine.getWidth()-dock2.getWidth());
				level.addGoldDock(dock2);
				
			}
		};
	}
}
