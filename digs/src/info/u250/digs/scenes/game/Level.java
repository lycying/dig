package info.u250.digs.scenes.game;

import info.u250.c2d.engine.Engine;
import info.u250.digs.Digs;
import info.u250.digs.PixmapHelper;
import info.u250.digs.scenes.GameScene;
import info.u250.digs.scenes.game.entity.EnemyMiya;
import info.u250.digs.scenes.game.entity.GoldTowerEntity;
import info.u250.digs.scenes.game.entity.Ka;
import info.u250.digs.scenes.game.entity.KillCircleEntity;
import info.u250.digs.scenes.game.entity.Npc;
import info.u250.digs.scenes.game.entity.Npc.DigResult;
import info.u250.digs.scenes.game.entity.StepladderEntity;
import info.u250.digs.scenes.game.entity.TeleportEntity;

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

	
	public enum FingerMode{
		Fill,
		Clear,
		Npc,
		Home,
	}

	/*===================Bellow is the entity we must control it =========*/
	private final Array<Npc> npcs = new Array<Npc>();
	private final Array<GoldTowerEntity> docks = new Array<GoldTowerEntity>();
	private final Array<TeleportEntity> inouts = new Array<TeleportEntity>();
	private final Array<StepladderEntity> ladders = new Array<StepladderEntity>();
	private final Array<KillCircleEntity> killrays = new Array<KillCircleEntity>();
	private final Array<Ka> kas = new Array<Ka>();
	private final Array<EnemyMiya> enemyMyiyas = new Array<EnemyMiya>();
	
	/*===================Bellow is the entity we must control it =========*/
	
	/*===================For level draw ==================================*/
	private final static Color FILL_COLOR = new Color(199/255f,140/255f,50f/255,1.0f);
	public  final static float RADIUS = 4;
	/*===================For level draw ==================================*/
	
	private final Vector2 projPos = new Vector2(); // for pixmap to screen coors
	private final Vector2 prePos = new Vector2();  // for touch event position
	
	private GameScene game = null;
	private LevelConfig config = null;
	private PixmapHelper terrain = null;
	private PixmapHelper goldTerrain = null;
	private LevelActor levelActor = null; // Note : this is use to put the terrain to the center of the drawing
	
	/* if it is the fill mode currently */
	private FingerMode fingerMode = FingerMode.Clear;
	
	/* the bellow two attribute is used to load the map async */
	private boolean mapMaking = true;
	private boolean mapTexturing = false;
	
	/* for tick , the duration is {@link Level#ACC} */
	float accum = 0;
	final static float ACC = 1.0f / 60.0f;
	
	/* some mask to cal quickly */
	final static int MASK_CLEAR = 0; //clear color
	final static int MASK_GOLD  = -65281;//yellow
	final static int MASK_BOMB = 16777215;//cyan
	
	private Pixmap[] pip ; // temp attribute to recive the mapmaker results
	
	//fix the multi finger press 
	private InputListener terrainInput = new InputListener(){
		public boolean touchDown(com.badlogic.gdx.scenes.scene2d.InputEvent event, float x, float y, int pointer, int button) {
			if(pointer!=0) return true;
			if(getTouchable() == Touchable.disabled) return true;
			prePos.set(x,y);
			return true;
		}
		public void touchDragged(com.badlogic.gdx.scenes.scene2d.InputEvent event, float x, float y, int pointer) {
			if(pointer!=0)return;
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
					fillTerrain(prePos.x-getX(),prePos.y, RADIUS);
				}
			}
			fillTerrain(position.x-getX(),position.y, RADIUS);

			position.set(x,y);
			prePos.x = position.x;
			prePos.y = position.y;
		};
		public void touchUp(InputEvent event, float x, float y, int pointer, int button) {
			if(pointer!=0)return;
			if(getTouchable() == Touchable.disabled) return;
			prePos.set(0, 0);
		};
	};
	
	
	public Level(GameScene game,LevelConfig config){
		this.config = config;
		this.game = game;
		setSize(config.width,config.height);
		addTerrains();
		
	}
	void assembleToPixmapHelper(){
		clear();
		terrain = new PixmapHelper(pip[0]);
		goldTerrain = new PixmapHelper(pip[1]);
		
		if(null!=config.callback){
			config.callback.before(this);
		}
		
		levelActor = new LevelActor(terrain, goldTerrain);
		this.addActor(levelActor);
		
		addListener(terrainInput);
		if(null!=config.callback){
			config.callback.after(this);
		}
		this.addActor(new FollowLabel(this));
	}
	
	
	void addTerrains(){
		mapMaking = true;
		mapTexturing = false;
		clear();
		Digs.getExecutor().submit(new AsyncTask<Void>() {
			@Override
			public Void call() throws Exception {
				Thread.sleep(200);
				pip = LevelPixmapMaker.gen(config);
				mapMaking = false;
				mapTexturing = true;
				return null;
			}
		});
	}
	// loading.....................................
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
				return;
			}
		}else{
			drawLoading(batch);
			return;
		}
		super.draw(batch, parentAlpha);
	}
	
	/* if we arrived the win status */
	int goldNumber = 0;
	void fingerWinStatus(){
		if(null == game) return;
		goldNumber = 0;
		for(GoldTowerEntity dock:this.docks){
			goldNumber+=dock.getNumber();
		}
		if(goldNumber>=config.aim){
			this.game.win(config, goldNumber, 50, 30, 56);//TODO
		}
	}

	@Override
	public void act(float delta) {
		fingerWinStatus();
		if(!mapMaking && !mapTexturing){
			accum += delta;
			while (accum >= ACC) {
				//OK, tick it
				for(final Npc e : npcs){
					e.tick();
				}
				for(final Ka e : kas){
					e.tick();
				}
				for(final EnemyMiya e:enemyMyiyas){
					e.tick();
				}
				accum -= ACC;
			}
		}
		super.act(delta);
		
		//in order to play the sound more accepted
		Npc.COIN_SOUND_CTL += delta;
		Npc.HURT_SOUND_CTL += delta;
		Npc.TRANS_SOUND_CTL += delta;
		
	}
	/* when the phone switch to another app and resume it , we must reload it */
	public void reload(){
		if(null!=terrain)terrain.reload();
		if(null!=goldTerrain)goldTerrain.reload();
	}
	
	/* fetch gold , 4 pixels */
	void dig(final float radius,float ax,float ay){
		if(terrain == null || goldTerrain == null)return;
		terrain.project(projPos,ax,ay);
		terrain.eraseCircle(projPos.x ,projPos.y ,radius );
		terrain.update();
		
		goldTerrain.project(projPos,ax, ay);
		goldTerrain.eraseCircle(projPos.x ,projPos.y , radius);
		goldTerrain.update();
	}
	/* clear or fill terrain */
	public void fillTerrain(float x,float y,final float radius){
		if(terrain == null )return;
		x += getX();
		terrain.project(projPos, x, y);
		if(fingerMode == FingerMode.Fill){
			terrain.eraseCircle(projPos.x, projPos.y, radius, FILL_COLOR);
		}else if(fingerMode == FingerMode.Clear){
			terrain.eraseCircle(projPos.x, projPos.y, radius ,Color.CLEAR);
		}
		terrain.update();
	}
	/* remvoe a rect of the transport aim in order to store the npcs */
	public void clearTransPort(float x,float y,final float radius){
		if(terrain == null )return;
		x += getX();
		terrain.project(projPos, x, y);
		terrain.eraseRectangle(projPos.x, projPos.y, radius ,Color.CLEAR);
		terrain.update();
	}
	
	//wow,wow, try try try
	int at=0,ag=0;
	float px , py;
	public boolean tryMove(float ax,float ay){
		px = ax;
		py = ay;
		at = 0;
		ag = 0;
		
		terrain.project(projPos, px, py);
		at = ( terrain.getPixel(projPos.x, projPos.y));
		if(at == 0){
			goldTerrain.project(projPos, px, py);
			ag = ( goldTerrain.getPixel(projPos.x, projPos.y));
			return ag == 0;
		}
		return false;
	}
	
	//try dig with a dig result 
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
		return DigResult.None;
	}
	
	
	public FingerMode getFingerMode() {
		return fingerMode;
	}
	public void setFingerMode(FingerMode fingerMode) {
		this.fingerMode = fingerMode;
	}
	
	public Array<EnemyMiya> getEnemyMyiyas() {
		return enemyMyiyas;
	}
	public void addEnemyMiya(EnemyMiya miya){
		this.enemyMyiyas.add(miya);
		this.addActor(miya);
	}
	public void removeEnemyMiya(EnemyMiya miya){
		this.enemyMyiyas.removeValue(miya, true);
		miya.remove();
	}
	
	public Array<GoldTowerEntity> getDocks() {
		return docks;
	}
	public Array<TeleportEntity> getInouts() {
		return inouts;
	}
	public Array<StepladderEntity> getLadders() {
		return ladders;
	}
	public Array<KillCircleEntity> getKillrays() {
		return killrays;
	}
	public Array<Ka> getKas(){
		return kas;
	}
	public void addKa(Ka ka){
		this.kas.add(ka);
		this.addActor(ka);
	}
	public void removeKa(Ka ka){
		this.kas.removeValue(ka, true);
		ka.remove();
	}
	public void addGoldDock(GoldTowerEntity dock){
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
	public void addInOutTrans(TeleportEntity inout){
		this.inouts.add(inout);
		this.addActor(inout);
	}
	public void addStepladder(StepladderEntity ladder){
		this.ladders.add(ladder);
		this.addActor(ladder);
	}
	public void addKillCircle(KillCircleEntity kill){
		this.killrays.add(kill);
		this.addActor(kill);
	}
	public Npc getRandomNpc(){
		if(this.npcs.size<1)return null;
		return this.npcs.get(Digs.RND.nextInt(this.npcs.size));
	}
	/* dispose everything to clear the memory */
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
