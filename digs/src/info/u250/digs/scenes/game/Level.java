package info.u250.digs.scenes.game;

import info.u250.c2d.engine.Engine;
import info.u250.digs.Digs;
import info.u250.digs.PixmapHelper;
import info.u250.digs.scenes.game.entity.Npc;
import info.u250.digs.scenes.game.entity.GoldDock;
import info.u250.digs.scenes.game.entity.InOutTrans;
import info.u250.digs.scenes.game.entity.Stepladder;

import java.util.Random;

import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.Pixmap;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.g2d.TextureAtlas;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.scenes.scene2d.Group;
import com.badlogic.gdx.scenes.scene2d.InputEvent;
import com.badlogic.gdx.scenes.scene2d.InputListener;
import com.badlogic.gdx.scenes.scene2d.Touchable;
import com.badlogic.gdx.utils.Array;
import com.badlogic.gdx.utils.async.AsyncTask;

public class Level extends Group{
	public final Array<GoldDock> docks = new Array<GoldDock>();
	public final Array<Npc> npcs = new Array<Npc>();
	public final Array<InOutTrans> inouts = new Array<InOutTrans>();
	public final Array<Stepladder> ladders = new Array<Stepladder>();

	
	private final static Color FILL_COLOR = new Color(199/255f,140/255f,50f/255,1.0f);
	public  final static float RADIUS = 4;
	private final Vector2 projPos = new Vector2();
	private final Vector2 prePos = new Vector2();
	private final Vector2 calPos = new Vector2();
	
	private LevelConfig config = null;
	private PixmapHelper terrain = null;
	private PixmapHelper goldTerrain = null;
	
	private boolean fillMode = false;
	private boolean mapMaking = true;
	private boolean mapTexturing = false;
	
	float accum = 0;
	final static float ACC = 1.0f / 60.0f;
	
	private Pixmap[] pip ;
	private InputListener terrainInput = new InputListener(){
		public boolean touchDown(com.badlogic.gdx.scenes.scene2d.InputEvent event, float x, float y, int pointer, int button) {
			prePos.set(x,y);
			return true;
		}
		public void touchDragged(com.badlogic.gdx.scenes.scene2d.InputEvent event, float x, float y, int pointer) {
			if(getTouchable() == Touchable.disabled) return;
			
			Vector2 position = new Vector2();
			position.set(x,y);
			
			float distance = position.dst(prePos);
			float step = RADIUS;
			if (distance>step) {
				double count = Math.ceil((double) (distance / (step))) - 1;
				Vector2 vtmp = position.cpy();
				vtmp.sub(prePos);
				vtmp.scl(step/vtmp.len());
				for (int i = 0; i < count; i++) {
					prePos.add(vtmp);
					fillTerrain(prePos.x,prePos.y, RADIUS, fillMode);
				}
			}
			fillTerrain(position.x,position.y, RADIUS, fillMode);

			position.set(x,y);
			prePos.x = position.x;
			prePos.y = position.y;
		};
		public void touchUp(InputEvent event, float x, float y, int pointer, int button) {
			prePos.set(0, 0);
		};
	};
	
	
	public Level(LevelConfig config){
		this.config = config;
		setSize(config.width,512);
		addTerrains();
		
	}
	void assembleToPixmapHelper(){
		clear();
		terrain = new PixmapHelper(pip[0]);
		goldTerrain = new PixmapHelper(pip[1]);
		
		addListener(terrainInput);
		if(null!=config.callback){
			config.callback.call(this);
		}
	}
	Random random = new Random();
	
	
	public void addTerrains(){
		mapMaking = true;
		mapTexturing = false;
		clear();
		Digs.getExecutor().submit(new AsyncTask<Void>() {
			@Override
			public Void call() throws Exception {
				Thread.sleep(200);
				pip = LevelMaker.gen(config);
				mapMaking = false;
				mapTexturing = true;
				return null;
			}
		});
	}
	public void addDocks(){
		docks.clear();
		GoldDock dock = new GoldDock();
		dock.setPosition(0, 400);
		GoldDock dock2 = new GoldDock();
		dock2.setPosition(terrain.pixmap.getWidth()-dock2.getWidth(), 400);
		docks.add(dock);
		docks.add(dock2);
		addActor(dock);
		addActor(dock2);
	}
	void drawLoading(SpriteBatch batch){
		batch.setColor(new Color(1,1,1,.5f));
		batch.draw(Engine.resource("All",TextureAtlas.class).findRegion("color"), 0,0,config.width,Engine.getHeight());
		batch.setColor(Color.WHITE);
		batch.draw(Engine.resource("All",TextureAtlas.class).findRegion("loading"),(Engine.getWidth()-300)/2,240);
	}
	@Override
	public void draw(SpriteBatch batch, float parentAlpha) {
		if(!mapMaking){
			if(mapTexturing){
				assembleToPixmapHelper();
				mapTexturing = false;
			}else{
				terrain.sprite.draw(batch);
				goldTerrain.sprite.draw(batch);
				super.draw(batch, parentAlpha);
			}
		}else{
			drawLoading(batch);
		}
	}
	@Override
	public void act(float delta) {
		if(!mapMaking && !mapTexturing){
			terrain.sprite.setX(getX());
			goldTerrain.sprite.setX(getX());
			
			accum += delta;
			while (accum >= ACC) {
				this.tick();
				accum -= ACC;
			}
		}
		super.act(delta);
	}
	void tick(){
		for(Npc e : npcs){
			e.tick();
		}
	}
	public void reload(){
		if(null!=terrain)terrain.reload();
		if(null!=goldTerrain)goldTerrain.reload();
	}
	void dig(final float radius,float ax,float ay){
		if(terrain == null || goldTerrain == null)return;
		terrain.project(projPos,ax+getX(),ay);
		terrain.eraseCircle(projPos.x ,projPos.y ,radius );
		terrain.update();
		
		goldTerrain.project(projPos,ax+getX(), ay);
		goldTerrain.eraseCircle(projPos.x ,projPos.y , radius);
		goldTerrain.update();
	}
	public void fillTerrain(float x,float y,final float radius,boolean isFillMode){
		if(terrain == null )return;
		x += getX();
		terrain.project(calPos, x, y);
		if(isFillMode){
			terrain.eraseCircle(calPos.x, calPos.y, radius, FILL_COLOR);
		}else{
			terrain.eraseCircle(calPos.x, calPos.y, radius );
		}
		terrain.update();
	}
	public void clearTransPort(float x,float y,final float radius){
		if(terrain == null )return;
		x += getX();
		terrain.project(calPos, x, y);
		terrain.eraseRectangle(calPos.x, calPos.y, radius );
		terrain.update();
	}
	int at=0,ag=0;
	float px , py;
	public boolean tryMove(float ax,float ay){
		px = ax;
		py = ay;
		at = 0;
		ag = 0;
		
		terrain.project(projPos, px+getX(), py);
		at = (0x000000ff & terrain.getPixel(projPos.x, projPos.y));
		if(at == 0){
			goldTerrain.project(projPos, px+getX(), py);
			ag = (0x000000ff & goldTerrain.getPixel(projPos.x, projPos.y));
			return ag == 0;
		}
		return false;
	}
	public boolean tryDig(){
		if(ag != 0){
			dig(2,px,py);
			return true;
		}
		for(int i=-4;i<=4;i+=2){
			for(int j=6;j>=-2;j-=2){
				goldTerrain.project(projPos, px+i, py+j);
				ag = (0x000000ff & goldTerrain.getPixel(projPos.x, projPos.y));
				if(ag != 0){
					dig(2,px+i,py+j);
					return true;
				}
			}
		}
		return false;
	}
	
	public boolean isFillMode() {
		return fillMode;
	}
	public void setFillMode(boolean fillMode) {
		this.fillMode = fillMode;
	}
	
	public void dispose(){
		if(null!=goldTerrain)goldTerrain.dispose();
		if(null!=terrain)terrain.dispose();
		mapMaking = true;
		npcs.clear();
		clear();
	}
	
}
