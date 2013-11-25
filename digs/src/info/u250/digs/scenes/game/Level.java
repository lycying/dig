package info.u250.digs.scenes.game;

import info.u250.c2d.engine.Engine;
import info.u250.digs.Digs;
import info.u250.digs.PixmapHelper;
import info.u250.digs.scenes.GameScene;
import info.u250.digs.scenes.game.entity.Boss;
import info.u250.digs.scenes.game.entity.EnemyMiya;
import info.u250.digs.scenes.game.entity.GoldTowerEntity;
import info.u250.digs.scenes.game.entity.Ka;
import info.u250.digs.scenes.game.entity.KillCircleEntity;
import info.u250.digs.scenes.game.entity.Npc;
import info.u250.digs.scenes.game.entity.Npc.DigResult;
import info.u250.digs.scenes.game.entity.StepladderEntity;
import info.u250.digs.scenes.game.entity.TeleportEntity;
import info.u250.digs.scenes.ui.WaterActor;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.Pixmap;
import com.badlogic.gdx.graphics.g2d.Batch;
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
	private final Array<EnemyMiya> enemyMiyas = new Array<EnemyMiya>();
	private final Array<Boss> bosses = new Array<Boss>();
	
	/*===================Bellow is the entity we must control it =========*/
	
	/*===================For level draw ==================================*/
	private final static Color FILL_COLOR = new Color(199/255f,140/255f,50f/255,1.0f);
	public  final static float RADIUS = 4;
	/*===================For level draw ==================================*/
	
	private final Vector2 projPos = new Vector2(); // for pixmap to screen coors
	
	public LevelConfig config = null;
	private GameScene game = null;
	private PixmapHelper terrain = null;
	private PixmapHelper goldTerrain = null;
	private LevelActor levelActor = null; // Note : this is use to put the terrain to the center of the drawing
	
	
	/* the bellow two attribute is used to load the map async */
	private boolean mapMaking = true;
	private boolean mapTexturing = false;
	
	/* for tick , the duration is {@link Level#ACC} */
	float accum = 0;
	static float ACC = 1.0f / 90.0f;
	
	/* some mask to cal quickly */
	final static int MASK_CLEAR = 0; //clear color
	final static int MASK_GOLD  = -65281;//yellow
	final static int MASK_BOMB = 16777215;//cyan
		
	private Pixmap[] pip ; // temp attribute to recive the mapmaker results
	private FingerMode getFingerMode(){
		if(null == game)return FingerMode.Fill;
		return game.getFingerMode();
	}
	//fix the multi finger press 
	private InputListener terrainInput = new InputListener(){
		Vector2 position = new Vector2();
		Vector2 posPre = new Vector2();
		Vector2 vtmp = new Vector2();
		public void followParentScroll(float yTouchPos,float yAmount){
			if(null == game) return;
			if(getHeight()<=Engine.getHeight())return;
			if(yTouchPos<100 || yTouchPos>Engine.getHeight()-100){
				game.getScroll().scrollTo(
						0, 
						game.getScroll().getScrollY()+yAmount, 
						game.getScroll().getWidth(),
						game.getScroll().getHeight());
			}
			//OK , scroll it to center
		}
		public boolean touchDown(com.badlogic.gdx.scenes.scene2d.InputEvent event, float x, float y, int pointer, int button) {
			if(pointer!=0) return true;
			if(getTouchable() == Touchable.disabled) return true;
			posPre.set(x,y);
			followParentScroll(y,0);
			return true;
		}
		public void touchDragged(com.badlogic.gdx.scenes.scene2d.InputEvent event, float x, float y, int pointer) {
			if(pointer!=0)return;
			if(getTouchable() == Touchable.disabled) return;
			
			position.set(x,y);
			
			float distance = position.dst(posPre);
			float step = RADIUS;
			if (distance>step) {
				double count = Math.ceil((double) (distance / (step))) - 1;
				vtmp.set(position);
				vtmp.sub(posPre);
				vtmp.scl(step/vtmp.len());
				for (int i = 0; i < count; i++) {
					posPre.add(vtmp);
					fillTerrain(posPre.x-getX(),posPre.y, RADIUS,getFingerMode());
					followParentScroll(y,position.y-posPre.y);
				}
			}
			fillTerrain(position.x-getX(),position.y, RADIUS,getFingerMode());
			followParentScroll(y,position.y-posPre.y);

			position.set(x,y);
			posPre.x = position.x;
			posPre.y = position.y;
		};
		public void touchUp(InputEvent event, float x, float y, int pointer, int button) {
			if(pointer!=0)return;
			if(getTouchable() == Touchable.disabled) return;
			posPre.set(0, 0);
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
		
		if(null!=config.levelMakeCallback){
			config.levelMakeCallback.before(this);
		}
		
		levelActor = new LevelActor(terrain, goldTerrain);
		this.addActor(levelActor);
		
		addListener(terrainInput);
		if(null!=config.levelMakeCallback){
			config.levelMakeCallback.after(this);
		}
		this.addActor(new FollowLabel(this));
		
		if(null!=game){
			this.addActor(new WaterActor(24, new Color(1,0,0,0.4f), new Color(1,0,0,0.4f)));
			game.levelCallback();
		}
	}
	
	
	void addTerrains(){
		mapMaking = true;
		mapTexturing = false;
		clear();
		Digs.getExecutor().submit(new AsyncTask<Void>() {
			@Override
			public Void call() throws Exception {
				try{
					Thread.sleep(200);
					pip = LevelPixmapMaker.gen(config);
					mapMaking = false;
					mapTexturing = true;
					return null;
				}catch(Exception ex){
					ex.printStackTrace();
					throw ex;
				}
			}
		});
	}
	// loading.....................................
	void drawLoading(Batch batch){
		batch.setColor(new Color(1,1,1,.5f));
		batch.draw(Engine.resource("All",TextureAtlas.class).findRegion("color"), 0,0,config.width,Engine.getHeight());
		batch.setColor(Color.WHITE);
		batch.draw(Engine.resource("All",TextureAtlas.class).findRegion("loading"),(Engine.getWidth()-300)/2,240);
	}
	@Override
	public void draw(Batch batch, float parentAlpha) {
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

	@Override
	public void act(float delta) {
		if(!mapMaking && !mapTexturing){
			super.act(delta);
			
			if(null!=game){
				if(game.isShowAim())return;
				if(config.levelCompleteCallback.tick(this)) return ;//win callback
			}
			
			accum += delta;
			while (accum >= ACC) {
				//OK, tick it
				for(final Npc e : npcs){
					e.tick();
				}
				for(final Ka e : kas){
					e.tick();
				}
				for(final EnemyMiya e:enemyMiyas){
					e.tick();
				}
				for(final Boss e:bosses){
					e.tick();
				}
				accum -= ACC;
				ACC = Gdx.graphics.getFramesPerSecond()/5400f;//this method keep the npcs move at the same speed what ever the device is 
			}
		}
		
		//in order to play the sound more accepted
		Npc.COIN_SOUND_CTL += delta;
		Npc.HURT_SOUND_CTL += delta;
		Npc.TRANS_SOUND_CTL += delta;
		Npc.DIE_SOUND_CTL += delta;
	}
	/* when the phone switch to another app and resume it , we must reload it */
	public void reload(){
		if(null!=terrain)terrain.reload();
		if(null!=goldTerrain)goldTerrain.reload();
	}
	int digCount = 0;
	
	/* fetch gold , 4 pixels */
	public void dig(final float radius,float ax,float ay){
		if(terrain == null || goldTerrain == null)return;
		Engine.getSoundManager().playSound("SoundDig");
		
		terrain.project(projPos,ax,ay);
		terrain.eraseCircle(projPos.x ,projPos.y ,radius );
		terrain.update();
		
		goldTerrain.project(projPos,ax, ay);
		goldTerrain.eraseCircle(projPos.x ,projPos.y , radius);
		goldTerrain.update();
	}
	/* clear or fill terrain */
	public void fillTerrain(float x,float y,final float radius,FingerMode mode){
		if(terrain == null )return;
		x += getX();
		terrain.project(projPos, x, y);
		if(mode == FingerMode.Fill){
			terrain.eraseCircle(projPos.x, projPos.y, radius, FILL_COLOR);
		}else if(mode == FingerMode.Clear){
			terrain.eraseCircle(projPos.x, projPos.y, radius ,Color.CLEAR);
			//draw the dirt effect.
			//dirtEffect(x-getX(), y);//too big the effect for the FPS
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
	public DigResult tryDig(boolean isFree){
		int key = Digs.RND.nextInt(4*11);
		int ax = key%4*(Digs.RND.nextBoolean()?1:-1);
		int ay = key%11-2;
		
		goldTerrain.project(projPos, px+ax, py+ay);
		ag = ( goldTerrain.getPixel(projPos.x, projPos.y));
		if(ag != 0){
			if(ag == MASK_GOLD){
				if(isFree){
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
	public Array<Boss> getBosses(){
		return bosses;
	}
	public void addBoss(Boss boss){
		this.bosses.add(boss);
		this.addActor(boss);
	}
	public Array<EnemyMiya> getEnemyMyiyas() {
		return enemyMiyas;
	}
	public void addEnemyMiya(EnemyMiya miya){
		this.enemyMiyas.add(miya);
		this.addActor(miya);
	}
	public void removeBoss(Boss boss){
		this.bosses.removeValue(boss, true);
		boss.remove();
	}
	public void removeEnemyMiya(EnemyMiya miya){
		this.enemyMiyas.removeValue(miya, true);
		miya.remove();
	}
	public Array<Npc> getNpcs(){
		return npcs;
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
	public boolean isMapMaking() {
		return mapMaking;
	}
	public GameScene getGame() {
		return game;
	}
	/* dispose everything to clear the memory */
	public void dispose(){
		if(null!=config)config.levelMakeCallback.dispose();
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
