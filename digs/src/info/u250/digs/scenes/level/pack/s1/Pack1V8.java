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
import info.u250.digs.scenes.game.entity.KillCircleEntity;
import info.u250.digs.scenes.game.entity.Npc;
import info.u250.digs.scenes.game.entity.StepladderEntity;
import info.u250.digs.scenes.game.entity.TeleportEntity;
import info.u250.digs.scenes.ui.ParticleEffectActor;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.Pixmap;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.ParticleEffect;
import com.badlogic.gdx.graphics.g2d.TextureAtlas;
import com.badlogic.gdx.math.Polygon;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.scenes.scene2d.actions.Actions;
import com.badlogic.gdx.scenes.scene2d.ui.Image;

public class Pack1V8 extends LevelConfig {
	public Pack1V8(){
		this.surface = "qvg/107.png";
		this.width = (int)Engine.getWidth() ;
		this.height = (int)Engine.getHeight();
		this.bottomColor = WebColors.CORNFLOWER_BLUE.get();
		this.topColor = WebColors.STEEL_BLUE.get();
		this.lineHeight = 360;
		this.ascent = 25;
		this.segment = 20;
		this.gold = 100;
		this.npc = 20;
		this.time = 600;
		
		levelMakeCallback = new LevelMakeCallBack() {
			final Texture ship1 = new Texture(Gdx.files.internal("wb/ship6.png"));
			
			@Override
			public void dispose() {
				ship1.dispose();
				
			}
			@Override
			public void after(Level level) {
				{
					StepladderEntity e = new StepladderEntity(10, 900, 60);
					level.addStepladder(e);
				}
				{
					StepladderEntity e = new StepladderEntity(10, 80, 60);
					level.addStepladder(e);
				}
				for(int i=0;i<npc;i++){
					Npc e = new Npc();
					e.init(level);
					e.setPosition(200+Digs.RND.nextFloat()*200, Engine.getHeight() + Digs.RND.nextFloat()*100);
					level.addNpc(e);
				}
				
				{
					KillCircleEntity e = new KillCircleEntity(-120,-100, 120, Color.WHITE);
					level.addKillCircle(e);
				}
				{
					KillCircleEntity e = new KillCircleEntity(960-120,-100, 120, Color.WHITE);
					level.addKillCircle(e);
				}
				{
					KillCircleEntity e = new KillCircleEntity(430,300, 100, Color.WHITE);
					level.addKillCircle(e);
				}
				{
					KillCircleEntity e = new KillCircleEntity(630,200, 50, Color.WHITE);
					level.addKillCircle(e);
				}
				{
					TeleportEntity e = new TeleportEntity(400, 350, 900, 420);
					level.addInOutTrans(e);
				}
			}
			
			@Override
			public void mapMaker(Pixmap terr, Pixmap gold) {
				{
					
					drawPixmapDeco(gold, "stone3", 850, 260);
					drawPixmapDeco(gold, "stone2", 400, 300);
				}
				{
					gold.setColor(Color.YELLOW);
					Polygon polygon =  PolygonTable.NOLA_128();
					polygon.setScale(0.8f, 0.8f);
					polygon.setPosition(800, 250);
					drawPolygon(polygon, gold);
					}
				{
					gold.setColor(Color.CYAN);
					Polygon polygon =  PolygonTable.IMG_ISLAND5();
					polygon.setScale(3f, 3f);
					polygon.setPosition(800, -100);
					drawPolygon(polygon, gold);
					}
				
				{
					gold.setColor(Color.CYAN);
					fillRect(gold, 100, 50, 2, 100);
					fillRect(gold, 400, 50, 2, 100);
					fillRect(gold, 700, 50, 2, 100);
				}
				
			}

			@Override
			public void before(Level level) {
				TextureAtlas atlas = Engine.resource("All");
				final ParallaxGroup pbg = new ParallaxGroup(Engine.getWidth(), Engine.getHeight(), new Vector2(50,0));
				pbg.addActor(new ParallaxLayer(pbg, new Image(atlas.findRegion("cloud")), new Vector2(1,1), new Vector2(500,1000), new Vector2(300,350)));
				Image shipImage2 = new Image(ship1);
				shipImage2.addAction(Actions.forever(Actions.sequence(Actions.rotateTo(-10,2),Actions.rotateTo(10,2))));
				pbg.addActor(new ParallaxLayer(pbg, shipImage2, new Vector2(2.5f,1), new Vector2(1000,20000), new Vector2(900,350)));
		
				level.addActor(pbg);
				
				GoldTowerEntity dock = new GoldTowerEntity();
				dock.setY(lineHeight);
				level.addGoldDock(dock);
				
				ParticleEffect e = Engine.resource("Effect");
				ParticleEffectActor p = new ParticleEffectActor(e,"smoke");
				p.setPosition(470, 410);
				level.addActor(p);
			}
		};
	}
}