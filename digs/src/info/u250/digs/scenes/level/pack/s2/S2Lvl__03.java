package info.u250.digs.scenes.level.pack.s2;

import info.u250.c2d.engine.Engine;
import info.u250.c2d.graphic.WebColors;
import info.u250.c2d.graphic.parallax.ParallaxGroup;
import info.u250.c2d.graphic.parallax.ParallaxLayer;
import info.u250.digs.Digs;
import info.u250.digs.scenes.game.FaceLevelConfig;
import info.u250.digs.scenes.game.Level;
import info.u250.digs.scenes.game.LevelMakeCallBack;
import info.u250.digs.scenes.game.entity.EnemyMiya;
import info.u250.digs.scenes.game.entity.GoldTowerEntity;
import info.u250.digs.scenes.game.entity.Npc;
import info.u250.digs.scenes.game.entity.StepladderEntity;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.Pixmap;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.TextureAtlas;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.scenes.scene2d.ui.Image;

public class S2Lvl__03 extends FaceLevelConfig {
	public S2Lvl__03(){
		this.faces = new Vector2[]{
				new Vector2(0,100),
				new Vector2(100,120),
				new Vector2(200,140),
				new Vector2(300,160),
				new Vector2(400,200),
				new Vector2(500,300),
				new Vector2(600,200),
				new Vector2(700,160),
				new Vector2(800,140),
				new Vector2(900,120),
				new Vector2(960,120),
		};
		this.lightLine = 360;
		this.surface = "qvg/202.png";
		this.width = (int)Engine.getWidth() ;
		this.height = (int)Engine.getHeight();
		this.bottomColor = WebColors.LIGHT_SALMON.get();
		this.topColor = WebColors.DARK_RED.get();
		
		this.gold = 100;
		this.npc = 20;
		this.enemy = 2;
		this.time = 60*5;
		
		levelMakeCallback = new LevelMakeCallBack() {
			final Texture ship2 = new Texture(Gdx.files.internal("wb/ship9.png"));
			@Override
			public void dispose() {
				ship2.dispose();
			}
			@Override
			public void after(Level level) {
				{
					int w = 200;
					while(w<width){
						StepladderEntity e = new StepladderEntity(25, w, 50);
						level.addStepladder(e);
						w+=180;
					}
					w = 80;
					while(w<width){
						StepladderEntity e = new StepladderEntity(10, w, 100);
						level.addStepladder(e);
						w+=180;
					}
				}
				for(int i=0;i<npc;i++){
					Npc e = new Npc();
					e.init(level);
					e.setPosition(200+Digs.RND.nextFloat()*200, Engine.getHeight() + Digs.RND.nextFloat()*100);
					level.addNpc(e);
				}
				for(int i=0;i<enemy;i++){
					EnemyMiya e = new EnemyMiya();
					e.init(level);
					e.setPosition(700+Digs.RND.nextFloat()*200, Engine.getHeight() + Digs.RND.nextFloat()*100);
					level.addEnemyMiya(e);
				}
				
			}
			
			@Override
			public void mapMaker(Pixmap terr, Pixmap gold) {
				{
					gold.setColor(Color.YELLOW);
					fillCircle(gold, 450, 20, 30);
					fillCircle(gold, 500, 20, 30);
				}
			}

			@Override
			public void before(Level level) {
				TextureAtlas atlas = Engine.resource("All");
				final ParallaxGroup pbg = new ParallaxGroup(Engine.getWidth(), Engine.getHeight(), new Vector2(50,0));
				pbg.addActor(new ParallaxLayer(pbg, new Image(atlas.findRegion("cloud")), new Vector2(1,1), new Vector2(0,1000), new Vector2(300,350)));
				
				Image shipImage2 = new Image(ship2);
				shipImage2.setOrigin(shipImage2.getWidth()/2, 0);
//				shipImage2.addAction(Actions.forever(Actions.sequence(Actions.rotateTo(-10,2),Actions.rotateTo(10,2))));
				pbg.addActor(new ParallaxLayer(pbg,shipImage2 ,new Vector2(0.5f,1), new Vector2(1000,2000), new Vector2(0,250)));
				
				level.addActor(pbg);
				
				GoldTowerEntity dock = new GoldTowerEntity();
				dock.setY(lightLine);
				level.addGoldDock(dock);
			}
		};
	}
}