
package info.u250.digs.scenes.level.pack.s1;

import info.u250.c2d.engine.Engine;
import info.u250.c2d.graphic.WebColors;
import info.u250.c2d.graphic.parallax.ParallaxGroup;
import info.u250.c2d.graphic.parallax.ParallaxLayer;
import info.u250.digs.Digs;
import info.u250.digs.PolygonTable;
import info.u250.digs.scenes.game.HookLevelConfig;
import info.u250.digs.scenes.game.Level;
import info.u250.digs.scenes.game.LevelMakeCallBack;
import info.u250.digs.scenes.game.entity.Boss;
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

public class Pack1V__11 extends HookLevelConfig {
	public Pack1V__11(){
		this.surface = "qvg/110.jpg";
		this.width = (int)Engine.getWidth() -SCROLL_WIDTH;
		this.height = 840;
		this.bottomColor = WebColors.DARK_BLUE.get();
		this.topColor = WebColors.DODGER_BLUE.get();
		this.lineHeight = 660;
		this.segment = 40;
		this.ascent = 20;
		this.gold = 100;
		this.npc = 30;
		this.time = 600;
		
		levelMakeCallback = new LevelMakeCallBack() {
			final Texture bgTexture = new Texture(Gdx.files.internal("wb/ship7.png"));
			@Override
			public void dispose() {
				bgTexture.dispose();
			}
			@Override
			public void after(Level level) {
				for(int i=0;i<npc;i++){
					Npc e = new Npc();
					e.init(level);
					e.setPosition(200, height + Digs.RND.nextFloat()*height);
					level.addNpc(e);
				}
				Boss boss = new Boss();
				boss.setBossLandHeight(200);
				boss.init(level);
				boss.setPosition(800, 240);
				level.addBoss(boss);
				
			}
			
			@Override
			public void mapMaker(Pixmap terr, Pixmap gold) {
				{
					gold.setColor(Color.YELLOW);
					fillRect(gold, 100, 300, 200, 2);
					fillRect(gold, 600, 300, 200, 2);
					fillRect(gold, 300, 460, 200, 2);
				}
				{
					int w = 100;
					while(w<width){
						gold.setColor(Color.YELLOW);
						Polygon polygon =  PolygonTable.FORMPOLYGON_128();
						polygon.setScale(0.5f, 0.5f);
						polygon.setPosition(w, 80);
						drawPolygon(polygon, gold);
						w+=150;
					}
				}
				{
					int w = 100;
					while(w<width){
						drawPixmapDeco(gold, "stone5", w, 100,0.3f);
						w+=150;
					}
					
				}
			}
			@Override
			public void before(Level level) {
				{
					final Image img1 = new Image(bgTexture);
					final Image img2 = new Image(bgTexture);
					final Image img3 = new Image(bgTexture);
					final Image img4 = new Image(bgTexture);
					img1.setColor(Color.YELLOW);
					img2.setColor(Color.GREEN);
					img3.setColor(Color.PINK);
					img4.setColor(Color.RED);
					
					
					TextureAtlas atlas = Engine.resource("All");
					final ParallaxGroup pbg = new ParallaxGroup(Engine.getWidth(), Engine.getHeight(), new Vector2(50,0));
					pbg.addActor(new ParallaxLayer(pbg, new Image(atlas.findRegion("cloud")), new Vector2(1,1), new Vector2(500,1000), new Vector2(300,350)));
					Image shipImage2 = new Image(bgTexture);
					shipImage2.addAction(Actions.forever(Actions.sequence(Actions.rotateTo(-10,2),Actions.rotateTo(10,2))));
					pbg.addActor(new ParallaxLayer(pbg, shipImage2, new Vector2(2.5f,1), new Vector2(1000,20000), new Vector2(900,320)));
			
					pbg.setY(300);
					level.addActor(pbg);
				}
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