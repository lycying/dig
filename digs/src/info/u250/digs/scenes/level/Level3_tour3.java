package info.u250.digs.scenes.level;

import info.u250.c2d.engine.Engine;
import info.u250.c2d.graphic.WebColors;
import info.u250.c2d.graphic.parallax.ParallaxGroup;
import info.u250.c2d.graphic.parallax.ParallaxLayer;
import info.u250.digs.Digs;
import info.u250.digs.PolygonTable;
import info.u250.digs.scenes.game.Level;
import info.u250.digs.scenes.game.LevelConfig;
import info.u250.digs.scenes.game.entity.GoldTowerEntity;
import info.u250.digs.scenes.game.entity.KillCircleEntity;
import info.u250.digs.scenes.game.entity.Npc;

import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.Pixmap;
import com.badlogic.gdx.graphics.g2d.TextureAtlas;
import com.badlogic.gdx.math.Polygon;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.scenes.scene2d.ui.Image;

class Level3_tour3 extends LevelConfig {
	public Level3_tour3(){
		this.surface = "texs/Leather.jpg";
		this.width = (int)Engine.getWidth();
		this.height = (int)Engine.getHeight();
		this.bottomColor = WebColors.BLACK.get();
		this.topColor = WebColors.DODGER_BLUE.get();
		this.lineHeight = 300;
		this.segment = 10;
		
		
		callback = new LevelCallBack() {
			@Override
			public void before(Level level) {
				TextureAtlas atlas = Engine.resource("All");
				final ParallaxGroup pbg = new ParallaxGroup(Engine.getWidth(), Engine.getHeight(), new Vector2(50,0));
				pbg.addActor(new ParallaxLayer(pbg, new Image(atlas.findRegion("cloud")), new Vector2(1,1), new Vector2(500,1000), new Vector2(300,350)));
				level.addActor(pbg);
				
				GoldTowerEntity dock = new GoldTowerEntity();
				dock.setY(lineHeight);
				level.addGoldDock(dock);
			}
			
			@Override
			public void mapMaker(Pixmap terr, Pixmap gold) {
				gold.setColor(Color.YELLOW);
				Polygon polygon =  PolygonTable.IMG_ISLAND6;
				polygon.setScale(0.5f, 0.5f);
				polygon.setPosition(650, 400);
				drawPolygon(polygon, gold);
			}

			@Override
			public void after(Level level) {
				
				KillCircleEntity ray = new KillCircleEntity(400, 150, 100,Color.WHITE);
				level.addKillCircle(ray);
				
				for(int i=0;i<5;i++){
					Npc e = new Npc();
					e.init(level);
					e.setPosition(100+Digs.RND.nextFloat()*200, Engine.getHeight() + Digs.RND.nextFloat()*100);
					level.addNpc(e);
				}
			}
		};
	}
}
