package info.u250.digs.scenes.level.pack.s2;

import info.u250.c2d.engine.Engine;
import info.u250.c2d.graphic.WebColors;
import info.u250.c2d.graphic.parallax.ParallaxGroup;
import info.u250.c2d.graphic.parallax.ParallaxLayer;
import info.u250.digs.Digs;
import info.u250.digs.PolygonTable;
import info.u250.digs.scenes.game.HookLevelConfig;
import info.u250.digs.scenes.game.Level;
import info.u250.digs.scenes.game.LevelMakeCallBack;
import info.u250.digs.scenes.game.entity.GoldTowerEntity;
import info.u250.digs.scenes.game.entity.Npc;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.Pixmap;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.TextureAtlas;
import com.badlogic.gdx.math.Polygon;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.scenes.scene2d.actions.Actions;
import com.badlogic.gdx.scenes.scene2d.ui.Image;

//carry as many of gold in center time
//全部是金子
public class S2Lvl5 extends HookLevelConfig {
	public S2Lvl5(){
		this.surface = "qvg/204.png";
		this.width = (int)Engine.getWidth() ;
		this.height = (int)Engine.getHeight();
		this.bottomColor = WebColors.CORNFLOWER_BLUE.get();
		this.topColor = WebColors.STEEL_BLUE.get();
		this.lineHeight = 360;
		this.ascent = 25;
		this.segment = 20;
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
				{
				gold.setColor(Color.YELLOW);
				Polygon polygon =  PolygonTable.FROG();
				polygon.setScale(0.4f, 0.4f);
				polygon.setPosition(400, 100);
				drawPolygon(polygon, gold);
				}
				{
					gold.setColor(Color.YELLOW);
					Polygon polygon =  PolygonTable.BABELFISH();
					polygon.setScale(0.4f, 0.4f);
					polygon.setPosition(800, 200);
					drawPolygon(polygon, gold);
				}
				{
					gold.setColor(Color.CYAN);
					Polygon polygon =  PolygonTable.IMG_ISLAND8();
					polygon.setScale(0.8f, 0.3f);
					polygon.setPosition(440, 170);
					drawPolygon(polygon, gold);
				}
				{
					drawPixmapDeco(gold, "stone2", 0, -50);
					drawPixmapDeco(gold, "stone5", 300, 110);
				}
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
				dock.setY(lineHeight);
				level.addGoldDock(dock);
			}
		};
	}
}