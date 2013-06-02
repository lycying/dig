package info.u250.digs.scenes.game;

import info.u250.c2d.engine.Engine;
import info.u250.c2d.graphic.pixmap.PixmapHelper;
import info.u250.digs.scenes.game.entity.GreenHat;
import info.u250.digs.scenes.game.entity.HealMan;
import info.u250.digs.scenes.game.entity.AttackMan;

import java.util.Random;

import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.math.Rectangle;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.scenes.scene2d.Group;
import com.badlogic.gdx.scenes.scene2d.InputListener;
import com.badlogic.gdx.scenes.scene2d.Touchable;
import com.badlogic.gdx.utils.Array;

public class Terrain extends Group{
	PixmapHelper terrain = null;
	PixmapHelper goldTerrain = null;
	TerrainConfig config;
	
	private final Color colorLeftBottom = new Color();
	private final Color colorRightBottom = new Color();
	private final Color colorLeftTop = new Color();
	private final Color colorRightTop = new Color();
	
	private final Color colorLeftBottom_gold = new Color();
	private final Color colorRightBottom_gold = new Color();
	private final Color colorLeftTop_gold = new Color();
	private final Color colorRightTop_gold = new Color();
	
	
	private final Vector2 tmpProjectedPosition = new Vector2();
	private Vector2 prePosition = new Vector2();
	final private Color fillColor = new Color(199/255f,140/255f,50f/255,1.0f);
	private float radius = 16;
	private boolean fillMode = false;
	
	
	public Array<Dock> docks = new Array<Dock>();
	
	
	
	InputListener terrainInput = new InputListener(){
		public boolean touchDown(com.badlogic.gdx.scenes.scene2d.InputEvent event, float x, float y, int pointer, int button) {
			prePosition.set(x,y);
			return true;
		}
		public void touchDragged(com.badlogic.gdx.scenes.scene2d.InputEvent event, float x, float y, int pointer) {
			if(getTouchable() == Touchable.disabled) return;
			
			Vector2 position = new Vector2();
			position.set(x,y);
			
			float distance = position.dst(prePosition);
			float step = radius;
			if (distance >step) {
				double count = Math.ceil((double) (distance / (step))) - 1;
				Vector2 vtmp = position.cpy();
				vtmp.sub(prePosition);
				vtmp.scl(step/vtmp.len());
				for (int i = 0; i < count; i++) {
					prePosition.add(vtmp);
					fillTerrain(prePosition.cpy(), radius, fillMode);
				}
			}

			fillTerrain(position, radius, fillMode);

			position.set(x,y);
			prePosition.x = position.x;
			prePosition.y = position.y;
		};
		public void touchUp(com.badlogic.gdx.scenes.scene2d.InputEvent event, float x, float y, int pointer, int button) {
			prePosition.set(0, 0);
		};
	};
	
	
	public Terrain(TerrainConfig config){
		this.config = config;
		this.addTerrains();
		this.addNpcs();
	}
	
	public void addTerrains(){
		terrain = new PixmapHelper(MapMaker.genMap(config));
		goldTerrain = new PixmapHelper(new GoldPixmap(config.width, 480, 20));
		this.addListener(terrainInput);
		this.setSize(terrain.pixmap.getWidth(), terrain.pixmap.getHeight());
	}
	public void addDocks(){
		docks.clear();
		Dock dock = new Dock();
		dock.setPosition(0, 400);
		Dock dock2 = new Dock();
		dock2.setPosition(terrain.pixmap.getWidth()-dock2.getWidth(), 400);
		docks.add(dock);
		docks.add(dock2);
		this.addActor(dock);
		this.addActor(dock2);
	}
	
	public void addNpcs(){
		for(int i=0;i<10;i++){
			GreenHat e = new GreenHat();
			e.init(this);
			e.setPosition(300+i*e.getWidth(), Engine.getHeight() + new Random().nextFloat()*100);
			this.addActor(e);
		}
		for(int i=0;i<2;i++){
			AttackMan e = new AttackMan();
			e.init(this);
			e.setPosition(10+new Random().nextFloat()*500, Engine.getHeight() + new Random().nextFloat()*100);
			this.addActor(e);
		}
		for(int i=0;i<3;i++){
			HealMan e = new HealMan();
			e.init(this);
			e.setPosition(400+i*e.getWidth(), Engine.getHeight() + new Random().nextFloat()*100);
			this.addActor(e);
		}
	}
	
	@Override
	public void draw(SpriteBatch batch, float parentAlpha) {
		this.terrain.sprite.draw(batch);
		this.goldTerrain.sprite.draw(batch);
		
		super.draw(batch, parentAlpha);
	}
	@Override
	public void act(float delta) {
		this.terrain.sprite.setX(getX());
		this.goldTerrain.sprite.setX(getX());
		super.act(delta);
		
	}
	public void reload(){
		this.terrain.reload();
		this.goldTerrain.reload();
	}
	public void dig(final float radius,float x,float y){
		//Change the offset==========================
		x += this.getX();
		this.terrain.project(tmpProjectedPosition,x,y);
		this.terrain.eraseCircle(tmpProjectedPosition.x ,tmpProjectedPosition.y ,radius );
		this.terrain.update();
		
		this.goldTerrain.project(tmpProjectedPosition,x, y);
		this.goldTerrain.eraseCircle(tmpProjectedPosition.x ,tmpProjectedPosition.y , radius);
		this.goldTerrain.update();
	}
	public void fillTerrain(final Vector2 position,final float radius,boolean isFillMode){
		//Change the offset==========================
		position.x += this.getX();
		this.terrain.project(position, position.x, position.y);
		if(isFillMode){
			this.terrain.eraseCircle(position.x, position.y, radius, fillColor);
		}else{
			this.terrain.eraseCircle(position.x, position.y, radius );
		}
		this.terrain.update();
	}
	public void calculateSpriteRectColor(Rectangle rect){
		//Change the offset==========================
		rect.x += this.getX();
		terrain.project(tmpProjectedPosition, rect.x, rect.y);
		Color.rgba8888ToColor(colorLeftBottom, terrain.getPixel(tmpProjectedPosition.x, tmpProjectedPosition.y));
		terrain.project(tmpProjectedPosition, rect.x+rect.width, rect.y);
		Color.rgba8888ToColor(colorRightBottom, terrain.getPixel(tmpProjectedPosition.x, tmpProjectedPosition.y));
		terrain.project(tmpProjectedPosition, rect.x+rect.width, rect.y+rect.height);
		Color.rgba8888ToColor(colorRightTop, terrain.getPixel(tmpProjectedPosition.x, tmpProjectedPosition.y));
		terrain.project(tmpProjectedPosition, rect.x, rect.y+rect.height);
		Color.rgba8888ToColor(colorLeftTop, terrain.getPixel(tmpProjectedPosition.x, tmpProjectedPosition.y));
		
		
		goldTerrain.project(tmpProjectedPosition, rect.x, rect.y);
		Color.rgba8888ToColor(colorLeftBottom_gold, goldTerrain.getPixel(tmpProjectedPosition.x, tmpProjectedPosition.y));
		goldTerrain.project(tmpProjectedPosition, rect.x+rect.width, rect.y);
		Color.rgba8888ToColor(colorRightBottom_gold, goldTerrain.getPixel(tmpProjectedPosition.x, tmpProjectedPosition.y));
		goldTerrain.project(tmpProjectedPosition, rect.x+rect.width, rect.y+rect.height);
		Color.rgba8888ToColor(colorRightTop_gold, goldTerrain.getPixel(tmpProjectedPosition.x, tmpProjectedPosition.y));
		goldTerrain.project(tmpProjectedPosition, rect.x, rect.y+rect.height);
		Color.rgba8888ToColor(colorLeftTop_gold, goldTerrain.getPixel(tmpProjectedPosition.x, tmpProjectedPosition.y));
	}
	public boolean isBlocked(){
		return  (colorLeftBottom.a !=0 || colorLeftBottom_gold.a != 0) &&
				(colorRightBottom.a != 0 || colorRightBottom_gold.a != 0) &&
				(colorLeftTop.a != 0 || colorLeftTop_gold.a != 0) &&
				(colorRightTop.a != 0 || colorRightTop_gold.a != 0) ;
	}
	public boolean isSpace(){
		return (colorLeftBottom.a+colorLeftBottom_gold.a+colorRightBottom.a+colorRightBottom_gold.a) == 0;
	}
	public boolean isLeftBottomSpace(){
		return colorLeftBottom.a+colorLeftBottom_gold.a==0;
	}
	public boolean isLeftTopSpace(){
		return colorLeftTop.a + colorLeftTop_gold.a == 0;
	}
	public boolean isRightBottomSpace(){
		return colorRightBottom.a+colorRightBottom_gold.a==0;
	}
	public boolean isRightTopSpace(){
		return colorRightTop.a + colorRightTop_gold.a == 0;
	}
	
	public boolean isLeftBottomSpace_gold(){
		return colorLeftBottom_gold.a==0;
	}
	public boolean isLeftTopSpace_gold(){
		return colorLeftTop_gold.a == 0;
	}
	public boolean isRightBottomSpace_gold(){
		return colorRightBottom_gold.a==0;
	}
	public boolean isRightTopSpace_gold(){
		return colorRightTop_gold.a == 0;
	}
	
	
	public boolean isFillMode() {
		return fillMode;
	}
	public void setFillMode(boolean fillMode) {
		this.fillMode = fillMode;
	}
	
	public void dispose(){
		this.goldTerrain.dispose();
		this.terrain.dispose();
		this.clear();
	}
	
}
