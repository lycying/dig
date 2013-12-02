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
import info.u250.digs.scenes.game.entity.EnemyMiya;
import info.u250.digs.scenes.game.entity.GoldTowerEntity;
import info.u250.digs.scenes.game.entity.Ka;
import info.u250.digs.scenes.game.entity.KillCircleEntity;
import info.u250.digs.scenes.game.entity.Npc;
import info.u250.digs.scenes.ui.ParticleEffectActor;

import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.Pixmap;
import com.badlogic.gdx.graphics.g2d.ParticleEffect;
import com.badlogic.gdx.graphics.g2d.TextureAtlas;
import com.badlogic.gdx.math.Polygon;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.scenes.scene2d.ui.Image;

public class Pack1V5 extends LevelConfig {
	public Pack1V5(){
		this.surface = "qvg/104.png";
		this.width = (int)Engine.getWidth() ;
		this.height = (int)Engine.getHeight();
		this.bottomColor = WebColors.BISQUE.get();
		this.topColor = WebColors.BURLY_WOOD.get();
		this.lineHeight = 360;
		this.segment = 10;
		this.gold = 0;
		this.npc = 2;
		this.ka = 30;
		this.enemy = 5;
		this.time = 600;
		
		levelMakeCallback = new LevelMakeCallBack() {
			@Override
			public void after(Level level) {
				{
					KillCircleEntity kill = new KillCircleEntity(100, 100, 30, Color.CYAN);
					level.addKillCircle(kill);
				}
				{
					KillCircleEntity kill = new KillCircleEntity(750, 100, 35, Color.MAGENTA);
					level.addKillCircle(kill);
				}
				{
					KillCircleEntity kill = new KillCircleEntity(220, 100, 40, Color.GREEN);
					level.addKillCircle(kill);
				}
				for(int i=0;i<npc;i++){
					Npc e = new Npc();
					e.init(level);
					e.setPosition(200+Digs.RND.nextFloat()*200, Engine.getHeight() + Digs.RND.nextFloat()*100);
					level.addNpc(e);
				}
				for(int i=0;i<ka/2;i++){
					Ka e = new Ka();
					e.init(level);
					e.setPosition(180, 160);
					level.addKa(e);
				}
				for(int i=0;i<ka/2;i++){
					Ka e = new Ka();
					e.init(level);
					e.setPosition(720, 160);
					level.addKa(e);
				}
				for(int i=0;i<enemy;i++){
					EnemyMiya e = new EnemyMiya();
					e.init(level);
					e.setPosition(450, 200);
					level.addEnemyMiya(e);
				}
			}
			
			@Override
			public void mapMaker(Pixmap terr, Pixmap gold) {
				{
					terr.setColor(Color.CLEAR);
					Polygon polygon =  PolygonTable.SPRITZ_128();
					polygon.setScale(0.5f, 0.5f);
					polygon.setPosition(150, 150);
					drawPolygon(polygon, terr);
				}
				{
					terr.setColor(Color.CLEAR);
					Polygon polygon =  PolygonTable.SQUIDGE_128();
					polygon.setScale(0.5f, 0.5f);
					polygon.setPosition(700, 150);
					drawPolygon(polygon, terr);
				}
				{
					terr.setColor(Color.CLEAR);
					Polygon polygon =  PolygonTable.WONDER_PART_9();
					polygon.setScale(1f, 1f);
					polygon.setPosition(400, 180);
					drawPolygon(polygon, terr);
				}
				{
					drawPixmapDeco(gold, "stone6", 470, 50);
					drawPixmapDeco(gold, "stone3", 400, 0);
					drawPixmapDeco(gold, "stone2", 350, -30);
				}
			}
			@Override
			public void before(Level level) {
				TextureAtlas atlas = Engine.resource("All");
				final ParallaxGroup pbg = new ParallaxGroup(Engine.getWidth(), Engine.getHeight(), new Vector2(50,0));
				pbg.addActor(new ParallaxLayer(pbg, new Image(atlas.findRegion("cloud")), new Vector2(1,1), new Vector2(500,1000), new Vector2(300,350)));
				level.addActor(pbg);
				{
					ParticleEffect e = Engine.resource("Effect");
					ParticleEffectActor p = new ParticleEffectActor(e,"cross");
					p.setColor(WebColors.GREEN.get());
					p.setPosition(100, 100);
					level.addActor(p);
				}
				GoldTowerEntity dock = new GoldTowerEntity();
				dock.setY(lineHeight);
				level.addGoldDock(dock);
			}
		};
	}
}