package info.u250.digs.scenes.level.pack.guide;

import info.u250.c2d.engine.Engine;
import info.u250.c2d.graphic.WebColors;
import info.u250.c2d.graphic.parallax.ParallaxGroup;
import info.u250.c2d.graphic.parallax.ParallaxLayer;
import info.u250.digs.PolygonTable;
import info.u250.digs.scenes.game.Level;
import info.u250.digs.scenes.game.Level.FingerMode;
import info.u250.digs.scenes.game.LevelMakeCallBack;
import info.u250.digs.scenes.game.LineLevelConfig;
import info.u250.digs.scenes.game.callback.DefaultLevelCompleteCallback;
import info.u250.digs.scenes.game.entity.GoldTowerEntity;
import info.u250.digs.scenes.game.entity.Npc;
import info.u250.digs.scenes.ui.HintOnScreen;
import info.u250.digs.scenes.ui.InnerMask;
import info.u250.digs.scenes.ui.LineActor;
import info.u250.digs.scenes.ui.ParticleEffectActor;

import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.Pixmap;
import com.badlogic.gdx.graphics.Pixmap.Format;
import com.badlogic.gdx.graphics.g2d.Batch;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.graphics.g2d.ParticleEffect;
import com.badlogic.gdx.graphics.g2d.TextureAtlas;
import com.badlogic.gdx.math.Polygon;
import com.badlogic.gdx.math.Rectangle;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.scenes.scene2d.Actor;
import com.badlogic.gdx.scenes.scene2d.Group;
import com.badlogic.gdx.scenes.scene2d.InputEvent;
import com.badlogic.gdx.scenes.scene2d.actions.Actions;
import com.badlogic.gdx.scenes.scene2d.ui.Image;
import com.badlogic.gdx.scenes.scene2d.ui.Label;
import com.badlogic.gdx.scenes.scene2d.ui.Label.LabelStyle;
import com.badlogic.gdx.scenes.scene2d.utils.ClickListener;
/*
 * this tour show the basic operation that we should 
 * carry one gold to home.
 */
public class Tour1 extends LineLevelConfig {
	final int TYPE_INF_DOCK = 0;
	final int TYPE_INF_GOLD = 2;
	final int TYPE_INF_PATH = 3;
	final int TYPE_INF_BUTTON = 4;
	final int TYPE_INF_NPC = 5;
	final int TYPE_INF_DONE = 6;
	final int TYPE_INF_YOURTURN = 7;
	int type = TYPE_INF_GOLD;
	public Tour1(){
		this.surface = "qvg/000.png";
		this.width = (int)Engine.getWidth() ;
		this.height = (int)Engine.getHeight();
		this.bottomColor = WebColors.BLACK.get();
		this.topColor = WebColors.DARK_SLATE_GRAY.get();
		this.lineHeight = 300;
		this.gold = 1;// once you got a gold , you win!
		this.time = 3*60;
		this.npc = 1;
		levelCompleteCallback = new DefaultLevelCompleteCallback(){
			@Override
			public Actor infoBorad(Level level) {
				return new Group();
			}
		};
		levelMakeCallback = new LevelMakeCallBack() {
			final Pixmap pixmap = new Pixmap(width, height, Format.RGBA8888);
			@Override
			public void dispose() {
				pixmap.dispose();
			}
			
			final TextureAtlas atlas = Engine.resource("All");
			final Image finger = new Image(atlas.findRegion("finger"));
			final LineActor line = new LineActor(200, 298, 504, 234);
			final BitmapFont font = Engine.resource("MenuFont");
			final Label lblText = new Label("", new LabelStyle(font, Color.YELLOW));
			public void upAndDown(){
				finger.clearActions();
				finger.addAction(Actions.forever(Actions.sequence(Actions.moveBy(0, -20,0.2f),Actions.moveBy(0, 20,0.2f))));
			}
			public void drawLine(){
				finger.clearActions();
				finger.addAction(Actions.forever(Actions.sequence(Actions.moveTo(200, lineHeight-50),Actions.moveTo(500, 180,2f))));
			}
			@Override
			public void after(final Level level) {
				{
					HintOnScreen hint = new HintOnScreen("Bring the gold home!","hint3",Color.BLACK,100);
					hint.pack();
					hint.setPosition(475, 135);
					hint.setColor(new Color(1,1,1,0.8f));
					level.addActor(hint);
					}
				
				lblText.setText("Welcome to \"Fool Dig\", \nI will guide you through some basic operation.");
				lblText.setPosition(200, 330);
				level.getGame().addActor(lblText);
				final InnerMask mask = new InnerMask(width, height);
				mask.addListener(new ClickListener(){
					@Override
					public void clicked(InputEvent event, float x, float y) {
						if(type == TYPE_INF_GOLD){
							mask.setRect(new Rectangle(496,225,32,32));
							type = TYPE_INF_DOCK;
							level.getGame().addActor(finger);
							finger.setPosition(500, 180);
							upAndDown();
							lblText.setPosition(450, 120);
							lblText.setText("This is gold");
							level.getGame().addActor(lblText);
						}else if(type == TYPE_INF_DOCK){
							mask.setRect(new Rectangle(0,300,100,20));
							type = TYPE_INF_NPC;
							level.getGame().addActor(finger);
							finger.setPosition(40, 250);
							upAndDown();
							lblText.setPosition(160, 280);
							lblText.setText("You need bring the gold  home");
							level.getGame().addActor(lblText);
						}else if(type==TYPE_INF_NPC){
							level.getGame().reallyStartLevel();
							type = TYPE_INF_BUTTON;
							finger.remove();
							lblText.setPosition(100, 150);
							lblText.setText("I'll do the work");
							level.getGame().addActor(lblText);
						}else if(type==TYPE_INF_BUTTON){
							mask.setRect(new Rectangle(80,460,80,80));
							type = TYPE_INF_PATH;
							level.getGame().addActor(finger);
							finger.setPosition(120, 400);
							upAndDown();
							lblText.setPosition(200, 460);
							lblText.setText("This tool used to dig");
							level.getGame().addActor(lblText);
						}else if(type==TYPE_INF_PATH){
							mask.setRect(new Rectangle(80,230,500,150));
							type=TYPE_INF_DONE;
							level.getGame().addActor(line);
							line.setColor(Color.BLACK);
							level.getGame().addActor(finger);
							drawLine();
							lblText.setPosition(150, 180);
							lblText.setText("Dig using with your finger");
							level.getGame().addActor(lblText);
						}else if(type==TYPE_INF_DONE){
							mask.remove();
							finger.remove();
							line.remove();
							type = TYPE_INF_YOURTURN;
							lblText.setPosition(100, 120);
							lblText.setText("Alright, let's try it out.");
							level.getGame().addActor(lblText);
							lblText.getStyle().fontColor = Color.BLUE;
						}
						super.clicked(event, x, y);
					}
				});
				
				level.getGame().addActor(mask);
				
				final Npc e = new Npc(){
					@Override
					public void draw(Batch batch, float parentAlpha) {
						if(type==TYPE_INF_BUTTON){
							mask.setRect(new Rectangle(this.getX()-this.getWidth()/2-10,this.getY()-10,this.getWidth()+20,this.getHeight()+20));
						}else if(type==TYPE_INF_DONE){
							this.direction = 1;
							this.setPosition(100, 300);
						}
						super.draw(batch, parentAlpha);
					}
				};
				e.init(level);
				e.setPosition(200, 450);
				e.setDirection(1);
				level.addNpc(e);
			}
			
			@Override
			public void mapMaker(Pixmap terr, Pixmap gold) {
				gold.setColor(Color.YELLOW);
				Polygon polygon =  PolygonTable.FORMPOLYGON_128();
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
				{
					HintOnScreen hint = new HintOnScreen("Home!","hint5",Color.BLACK,60);
					hint.pack();
					hint.addAction(Actions.forever(Actions.sequence(Actions.moveBy(-10, 0,0.2f),Actions.moveBy(10, 0,0.2f))));
					hint.setPosition(800, 320);
					level.addActor(hint);
					}
				
				GoldTowerEntity dock = new GoldTowerEntity();
				dock.setY(lineHeight);
				level.addGoldDock(dock);
				
				GoldTowerEntity dock2 = new GoldTowerEntity();
				dock2.setY(lineHeight);
				dock2.setX(Engine.getWidth()-dock2.getWidth());
				level.addGoldDock(dock2);
				
				level.getGame().setFingerMode(FingerMode.Clear);
				
			}
		};
	}
}
