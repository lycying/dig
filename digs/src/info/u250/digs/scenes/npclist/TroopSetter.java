package info.u250.digs.scenes.npclist;

import info.u250.c2d.engine.Engine;
import info.u250.digs.scenes.NpcListScene;

import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.g2d.TextureAtlas;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.scenes.scene2d.Group;
import com.badlogic.gdx.scenes.scene2d.InputEvent;
import com.badlogic.gdx.scenes.scene2d.ui.Button;
import com.badlogic.gdx.scenes.scene2d.ui.Image;
import com.badlogic.gdx.scenes.scene2d.ui.Table;
import com.badlogic.gdx.scenes.scene2d.utils.ClickListener;
import com.badlogic.gdx.scenes.scene2d.utils.NinePatchDrawable;
import com.badlogic.gdx.scenes.scene2d.utils.TextureRegionDrawable;

public class TroopSetter extends Group {
	TroopItem target;
	NpcListScene container;
	public TroopSetter(NpcListScene container){
		this.container = container;
		//BitmapFont font = Engine.resource("Font");
		TextureAtlas atlas = Engine.resource("All");
		Image bg = new Image(atlas.findRegion("color"));
		bg.setColor(Color.BLACK);
		bg.getColor().a = 0.6f;
		bg.setSize(458, 450);
		
		
		Table background = new Table();
		background.setBackground(new NinePatchDrawable(atlas.createPatch("topbar")));
		background.pack();
		background.setSize(380, 380);
		background.getColor().a = 0.8f;
		background.setPosition(bg.getWidth()/2-background.getWidth()/2, 50);
		
		Button btn_add_10 = new Button(new TextureRegionDrawable(atlas.findRegion("btn-t-add-10-1")), new TextureRegionDrawable(atlas.findRegion("btn-t-add-10-2")));
		Button btn_sub_10 = new Button(new TextureRegionDrawable(atlas.findRegion("btn-t-sub-10-1")), new TextureRegionDrawable(atlas.findRegion("btn-t-sub-10-2")));
		Button btn_add_5 = new Button(new TextureRegionDrawable(atlas.findRegion("btn-t-add-5-1")), new TextureRegionDrawable(atlas.findRegion("btn-t-add-5-2")));
		Button btn_sub_5 = new Button(new TextureRegionDrawable(atlas.findRegion("btn-t-sub-5-1")), new TextureRegionDrawable(atlas.findRegion("btn-t-sub-5-2")));
		Button btn_add_2 = new Button(new TextureRegionDrawable(atlas.findRegion("btn-t-add-2-1")), new TextureRegionDrawable(atlas.findRegion("btn-t-add-2-2")));
		Button btn_sub_2 = new Button(new TextureRegionDrawable(atlas.findRegion("btn-t-sub-2-1")), new TextureRegionDrawable(atlas.findRegion("btn-t-sub-2-2")));
		Button btn_add_1 = new Button(new TextureRegionDrawable(atlas.findRegion("btn-t-add-1-1")), new TextureRegionDrawable(atlas.findRegion("btn-t-add-1-2")));
		Button btn_sub_1 = new Button(new TextureRegionDrawable(atlas.findRegion("btn-t-sub-1-1")), new TextureRegionDrawable(atlas.findRegion("btn-t-sub-1-2")));
		Button btn_close = new Button(new TextureRegionDrawable(atlas.findRegion("btn-t-close-1")), new TextureRegionDrawable(atlas.findRegion("btn-t-close-2")));
		btn_close.setPosition(bg.getWidth()/2-btn_close.getWidth()/2, 5);
		
		
		Table btnTable = new Table();
		btnTable.add(btn_sub_10).space(10);
		btnTable.add(btn_add_10).space(10);
		btnTable.row();
		btnTable.add(btn_sub_5).space(10);
		btnTable.add(btn_add_5).space(10);
		btnTable.row();
		btnTable.add(btn_sub_2).space(10);
		btnTable.add(btn_add_2).space(10);
		btnTable.row();
		btnTable.add(btn_sub_1).space(10);
		btnTable.add(btn_add_1).space(10);
		btnTable.row();
		btnTable.pack();
		btnTable.setPosition(bg.getWidth()/2-btnTable.getWidth()/2, 110);
		
		this.addActor(bg);
		this.addActor(background);
		this.addActor(btnTable);
		this.addActor(btn_close);
		this.setPosition(445,25);
		
		btn_sub_1.addListener(new ClickListener(){
			@Override
			public void clicked(InputEvent event, float x, float y) {
				Engine.getSoundManager().playSound("SoundClick");
				addTroops(-1);
				super.clicked(event, x, y);
			}
		});
		btn_add_1.addListener(new ClickListener(){
			@Override
			public void clicked(InputEvent event, float x, float y) {
				Engine.getSoundManager().playSound("SoundClick");
				addTroops(1);
				super.clicked(event, x, y);
			}
		});
		btn_sub_2.addListener(new ClickListener(){
			@Override
			public void clicked(InputEvent event, float x, float y) {
				Engine.getSoundManager().playSound("SoundClick");
				addTroops(-2);
				super.clicked(event, x, y);
			}
		});
		btn_add_2.addListener(new ClickListener(){
			@Override
			public void clicked(InputEvent event, float x, float y) {
				Engine.getSoundManager().playSound("SoundClick");
				addTroops(2);
				super.clicked(event, x, y);
			}
		});
		btn_sub_5.addListener(new ClickListener(){
			@Override
			public void clicked(InputEvent event, float x, float y) {
				Engine.getSoundManager().playSound("SoundClick");
				addTroops(-5);
				super.clicked(event, x, y);
			}
		});
		btn_add_5.addListener(new ClickListener(){
			@Override
			public void clicked(InputEvent event, float x, float y) {
				Engine.getSoundManager().playSound("SoundClick");
				addTroops(5);
				super.clicked(event, x, y);
			}
		});
		btn_sub_10.addListener(new ClickListener(){
			@Override
			public void clicked(InputEvent event, float x, float y) {
				Engine.getSoundManager().playSound("SoundClick");
				addTroops(-10);
				super.clicked(event, x, y);
			}
		});
		btn_add_10.addListener(new ClickListener(){
			@Override
			public void clicked(InputEvent event, float x, float y) {
				Engine.getSoundManager().playSound("SoundClick");
				addTroops(10);
				super.clicked(event, x, y);
			}
		});
		
		btn_close.addListener(new ClickListener(){
			@Override
			public void clicked(InputEvent event, float x, float y) {
				Engine.getSoundManager().playSound("SoundClick");
				TroopSetter.this.remove();
				super.clicked(event, x, y);
			}
		});
	}
	public void show(TroopItem target){
		this.target = target;
	}
	void addTroops(int number){
		this.target.setTroopNumber(this.target.getTroopNumber()+number);
	}
	Vector2 tmp = new Vector2();
	@Override
	public void act(float delta) {
		container.followImage.setOrigin(0, 2);
		container.followImage.setPosition(500, 250);
		tmp.set(target.localToAscendantCoordinates(this,tmp.set(85f/2, 85f/2)));
		
		float dst = tmp.dst(500, 250);
		float angle = tmp.sub(500, 250).angle();
		
		container.followImage.setScaleX(dst/4.0f);
		container.followImage.setRotation(angle);
		super.act(delta);
	}
}
