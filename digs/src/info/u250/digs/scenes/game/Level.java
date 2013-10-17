package info.u250.digs.scenes.game;

import info.u250.c2d.engine.Engine;
import info.u250.digs.Digs;
import info.u250.digs.PixmapHelper;
import info.u250.digs.scenes.game.entity.GoldDock;
import info.u250.digs.scenes.game.entity.InOutTrans;
import info.u250.digs.scenes.game.entity.KillCircle;
import info.u250.digs.scenes.game.entity.Npc;
import info.u250.digs.scenes.game.entity.Stepladder;

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
	public enum DigResult{
		None,
		Gold,
		Bomb,
	}
	private final Array<Npc> npcs = new Array<Npc>();
	private final Array<GoldDock> docks = new Array<GoldDock>();
	private final Array<InOutTrans> inouts = new Array<InOutTrans>();
	private final Array<Stepladder> ladders = new Array<Stepladder>();
	private final Array<KillCircle> killrays = new Array<KillCircle>();

	
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
	
	final static int MASK_CLEAR = 0;
	final static int MASK_GOLD  = -65281;
	final static int MASK_BOMB = 16777215;
	
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
			terrain.eraseCircle(calPos.x, calPos.y, radius ,Color.CLEAR);
		}
		terrain.update();
	}
	public void clearTransPort(float x,float y,final float radius){
		if(terrain == null )return;
		x += getX();
		terrain.project(calPos, x, y);
		terrain.eraseRectangle(calPos.x, calPos.y, radius ,Color.CLEAR);
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
		at = ( terrain.getPixel(projPos.x, projPos.y));
		if(at == 0){
			goldTerrain.project(projPos, px+getX(), py);
			ag = ( goldTerrain.getPixel(projPos.x, projPos.y));
			return ag == 0;
		}
		return false;
	}
	public DigResult tryDig(boolean holdGold){
		int key = Digs.RND.nextInt(4*11);
		int ax = key%4*(Digs.RND.nextBoolean()?1:-1);
		int ay = key%11-2;
		
		goldTerrain.project(projPos, px+ax, py+ay);
		ag = ( goldTerrain.getPixel(projPos.x, projPos.y));
		if(ag != 0){
			if(ag == MASK_GOLD){
				if(!holdGold){
					dig(2,px+ax,py+ay);
					return DigResult.Gold;
				}
			}else if(ag == MASK_BOMB){
				dig(20,px+ax,py+ay);
				return DigResult.Bomb;
			}
		}
//		int lor = Digs.RND.nextBoolean()?1:-1;
//		for(int i=5;i>=1;i-=3){
//			for(int j=10;j>=-2;j-=3){
//				//left or right
//				goldTerrain.project(projPos, px+lor*i, py+j);
//				ag = ( goldTerrain.getPixel(projPos.x, projPos.y));
//				if(ag != 0){
//					if(ag == MASK_GOLD){
//						if(!holdGold){
//							dig(2,px+lor*i,py+j);
//							return DigResult.Gold;
//						}
//					}else if(ag == MASK_BOMB){
//						dig(20,px+lor*i,py+j);
//						return DigResult.Bomb;
//					}
//				}
//				//again
//				goldTerrain.project(projPos, px-lor*i, py+j);
//				ag = ( goldTerrain.getPixel(projPos.x, projPos.y));
//				if(ag != 0){
//					if(ag == MASK_GOLD){
//						if(!holdGold){
//							dig(2,px-lor*i,py+j);
//							return DigResult.Gold;
//						}
//					}else if(ag == MASK_BOMB){
//						dig(20,px-lor*i,py+j);
//						return DigResult.Bomb;
//					}
//				}
//			}
//		}
		return DigResult.None;
	}
	
	public boolean isFillMode() {
		return fillMode;
	}
	public void setFillMode(boolean fillMode) {
		this.fillMode = fillMode;
	}
	public Array<GoldDock> getDocks() {
		return docks;
	}
	public Array<InOutTrans> getInouts() {
		return inouts;
	}
	public Array<Stepladder> getLadders() {
		return ladders;
	}
	public Array<KillCircle> getKillrays() {
		return killrays;
	}
	public void addGoldDock(GoldDock dock){
		this.docks.add(dock);
		this.addActor(dock);
	}
	public void addNpc(Npc npc){
		this.npcs.add(npc);
		this.addActor(npc);
	}
	public void removeNpc(Npc npc){
		this.npcs.removeValue(npc, true);
		npc.remove();
	}
	public void addInOutTrans(InOutTrans inout){
		this.inouts.add(inout);
		this.addActor(inout);
	}
	public void addStepladder(Stepladder ladder){
		this.ladders.add(ladder);
		this.addActor(ladder);
	}
	public void addKillCircle(KillCircle kill){
		this.killrays.add(kill);
		this.addActor(kill);
	}
	public void dispose(){
		if(null!=goldTerrain)goldTerrain.dispose();
		if(null!=terrain)terrain.dispose();
		mapMaking = true;
		npcs.clear();
		docks.clear();
		inouts.clear();
		ladders.clear();
		killrays.clear();
		clear();
	}
	
}
