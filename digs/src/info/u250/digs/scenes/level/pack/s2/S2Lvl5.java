package info.u250.digs.scenes.level.pack.s2;

import info.u250.c2d.engine.Engine;
import info.u250.c2d.graphic.WebColors;
import info.u250.c2d.graphic.parallax.ParallaxGroup;
import info.u250.c2d.graphic.parallax.ParallaxLayer;
import info.u250.digs.Digs;
import info.u250.digs.scenes.game.FaceLevelConfig;
import info.u250.digs.scenes.game.Level;
import info.u250.digs.scenes.game.LevelMakeCallBack;
import info.u250.digs.scenes.game.entity.GoldTowerEntity;
import info.u250.digs.scenes.game.entity.Npc;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Pixmap;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.TextureAtlas;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.scenes.scene2d.actions.Actions;
import com.badlogic.gdx.scenes.scene2d.ui.Image;

//carry as many of gold in center time
public class S2Lvl5 extends FaceLevelConfig {
	public S2Lvl5(){
		this.faces = new Vector2[21];
		for(int i=0;i<faces.length;i++){
			faces[i] = new Vector2();
			faces[i].x = 48*i;
			int dx = i%3;
			if(0==dx){
				faces[i].y = 400;
			}else if(1==dx){
				faces[i].y = 370;
			}else if(2==dx){
				faces[i].y = 320;
			}
		}
		this.lightLine = 360;
		this.surface = "qvg/204.png";
		this.width = (int)Engine.getWidth() ;
		this.height = (int)Engine.getHeight();
		this.bottomColor = WebColors.CORNFLOWER_BLUE.get();
		this.topColor = WebColors.STEEL_BLUE.get();
		
		this.gold = 100;
		this.npc = 20;
		this.time = 60*5;
		
		levelMakeCallback = new LevelMakeCallBack() {
			final Texture ship1 = new Texture(Gdx.files.internal("wb/ship.png"));
			final Texture ship2 = new Texture(Gdx.files.internal("wb/ship2.png"));
			@Override
			public void dispose() {
				ship1.dispose();
				ship2.dispose();
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

			}

			@Override
			public void before(Level level) {
				TextureAtlas atlas = Engine.resource("All");
				final ParallaxGroup pbg = new ParallaxGroup(Engine.getWidth(), Engine.getHeight(), new Vector2(50,0));
				pbg.addActor(new ParallaxLayer(pbg, new Image(atlas.findRegion("cloud")), new Vector2(1,1), new Vector2(500,1000), new Vector2(300,350)));
				pbg.addActor(new ParallaxLayer(pbg, new Image(ship1), new Vector2(2.5f,1), new Vector2(2000,20000), new Vector2(900,450)));
				Image shipImage2 = new Image(ship2);
				shipImage2.addAction(Actions.forever(Actions.sequence(Actions.rotateBy(10,2),Actions.rotateBy(-10,2))));
				pbg.addActor(new ParallaxLayer(pbg,shipImage2 ,new Vector2(0.5f,1), new Vector2(500,2000), new Vector2(0,400)));
				
				level.addActor(pbg);
				
				GoldTowerEntity dock = new GoldTowerEntity();
				dock.setY(lightLine);
				level.addGoldDock(dock);
			}
		};
	}
}