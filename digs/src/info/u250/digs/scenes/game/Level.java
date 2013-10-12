package info.u250.digs.scenes.game;

import info.u250.c2d.engine.Engine;
import info.u250.digs.Digs;
import info.u250.digs.PixmapHelper;
import info.u250.digs.scenes.game.entity.AttackMan;
import info.u250.digs.scenes.game.entity.GreenHat;
import info.u250.digs.scenes.game.entity.HealMan;

import java.util.Random;

import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.Pixmap;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.g2d.TextureAtlas;
import com.badlogic.gdx.math.Rectangle;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.scenes.scene2d.Group;
import com.badlogic.gdx.scenes.scene2d.InputEvent;
import com.badlogic.gdx.scenes.scene2d.InputListener;
import com.badlogic.gdx.scenes.scene2d.Touchable;
import com.badlogic.gdx.utils.Array;
import com.badlogic.gdx.utils.async.AsyncTask;

public class Level extends Group{
	PixmapHelper terrain = null;
	PixmapHelper goldTerrain = null;
	LevelConfig config;
	
	private  int clrLB = 0;
	private  int clrRB = 0;
	private  int clrLT = 0;
	private  int clrRT = 0;

	private  int clrLB_G = 0;
	private  int clrRB_G = 0;
	private  int clrLT_G = 0;
	private  int clrRT_G = 0;
	
	final private static Color FILL_COLOR = new Color(199/255f,140/255f,50f/255,1.0f);
	final private static float RADIUS = 16;
	private final Vector2 projPos = new Vector2();
	private final Vector2 prePos = new Vector2();
	private final Vector2 calPos = new Vector2();
	
	public  final Array<Dock> docks = new Array<Dock>();
	
	private boolean fillMode = false;
	private boolean mapMaking = true;
	private boolean mapTexturing = false;
	
	Pixmap[] pip ;
	
	InputListener terrainInput = new InputListener(){
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
		addDocks();
		addNpcs();
	}
	public void addNpcs(){
		for(int i=0;i<10;i++){
			GreenHat e = new GreenHat();
			e.init(this);
			e.setPosition(300+i*e.getWidth(), Engine.getHeight() + new Random().nextFloat()*100);
			addActor(e);
		}
		for(int i=0;i<2;i++){
			AttackMan e = new AttackMan();
			e.init(this);
			e.setPosition(10+new Random().nextFloat()*500, Engine.getHeight() + new Random().nextFloat()*100);
			addActor(e);
		}
		for(int i=0;i<3;i++){
			HealMan e = new HealMan();
			e.init(this);
			e.setPosition(400+i*e.getWidth(), Engine.getHeight() + new Random().nextFloat()*100);
			addActor(e);
		}
	}
	
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
		Dock dock = new Dock();
		dock.setPosition(0, 400);
		Dock dock2 = new Dock();
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
		}
		super.act(delta);
	}
	public void reload(){
		if(null!=terrain)terrain.reload();
		if(null!=goldTerrain)goldTerrain.reload();
	}
	public void dig(final float radius,float x,float y){
		if(terrain == null || goldTerrain == null)return;
		x += getX();
		terrain.project(projPos,x,y);
		terrain.eraseCircle(projPos.x ,projPos.y ,radius );
		terrain.update();
		
		goldTerrain.project(projPos,x, y);
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
	public void calculateSpriteRectColor(Rectangle rect){
		rect.x += getX();
		terrain.project(projPos, rect.x, rect.y);
		clrLB = terrain.getPixel(projPos.x, projPos.y) & 0x000000ff;
		terrain.project(projPos, rect.x+rect.width, rect.y);
		clrRB = terrain.getPixel(projPos.x, projPos.y) & 0x000000ff ;
		terrain.project(projPos, rect.x+rect.width, rect.y+rect.height);
		clrRT = terrain.getPixel(projPos.x, projPos.y) & 0x000000ff;
		terrain.project(projPos, rect.x, rect.y+rect.height);
		clrLT = terrain.getPixel(projPos.x, projPos.y) & 0x000000ff;		
		
		goldTerrain.project(projPos, rect.x, rect.y);
		clrLB_G = goldTerrain.getPixel(projPos.x, projPos.y) & 0x000000ff;
		goldTerrain.project(projPos, rect.x+rect.width, rect.y);
		clrRB_G= goldTerrain.getPixel(projPos.x, projPos.y) & 0x000000ff;
		goldTerrain.project(projPos, rect.x+rect.width, rect.y+rect.height);
		clrRT_G = goldTerrain.getPixel(projPos.x, projPos.y) & 0x000000ff;
		goldTerrain.project(projPos, rect.x, rect.y+rect.height);
		clrLT_G = goldTerrain.getPixel(projPos.x, projPos.y) & 0x000000ff;
	}
	public boolean isBlocked(){
		return  (clrLB !=0 || clrLB_G != 0) &&(clrRB != 0 || clrRB_G != 0) && (clrLT != 0 || clrLT_G != 0) && (clrRT != 0 || clrRT_G != 0) ;
	}
	public boolean isSpace(){
		return clrLB==0 && clrLB_G==0 && clrRB==0 && clrRB_G==0;
	}
	public boolean isLBSpace(){
		return clrLB==0  && clrLB_G==0;
	}
	public boolean isLTSpace(){
		return clrLT==0 && clrLT_G == 0;
	}
	public boolean isRBSpace(){
		return clrRB==0 && clrRB_G==0;
	}
	public boolean isRTSpace(){
		return clrRT == 0 &&  clrRT_G == 0;
	}
	public boolean isLB_GSpace(){
		return clrLB_G==0;
	}
	public boolean isLT_GSpace(){
		return clrLT_G == 0;
	}
	public boolean isRB_GSpace(){
		return clrRB_G==0;
	}
	public boolean isRT_GSpace(){
		return clrRT_G == 0;
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
		clear();
	}
	
}
