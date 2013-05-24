package info.u250.digs.scenes.npclist;

import info.u250.c2d.engine.Engine;

import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.graphics.g2d.TextureAtlas;
import com.badlogic.gdx.math.Interpolation;
import com.badlogic.gdx.scenes.scene2d.Group;
import com.badlogic.gdx.scenes.scene2d.InputEvent;
import com.badlogic.gdx.scenes.scene2d.Touchable;
import com.badlogic.gdx.scenes.scene2d.actions.Actions;
import com.badlogic.gdx.scenes.scene2d.ui.Button;
import com.badlogic.gdx.scenes.scene2d.ui.Image;
import com.badlogic.gdx.scenes.scene2d.ui.Label;
import com.badlogic.gdx.scenes.scene2d.ui.Label.LabelStyle;
import com.badlogic.gdx.scenes.scene2d.ui.Table;
import com.badlogic.gdx.scenes.scene2d.utils.ClickListener;
import com.badlogic.gdx.scenes.scene2d.utils.NinePatchDrawable;
import com.badlogic.gdx.scenes.scene2d.utils.TextureRegionDrawable;

public class TroopNumberSetter extends Group {
	
	Label lblNumber ;
	Label lblGoldNumber ;
	Button btnAdd ;
	Button btnSub ;
	Button btnClose ;
	Button btnOk ;
	Group mainGroup ;
	TroopItem aim;
	
	int number;
	public TroopNumberSetter(){
		TextureAtlas atlas = Engine.resource("All");
		BitmapFont font = Engine.resource("Font");
		this.setSize(Engine.getWidth(), Engine.getHeight());
		
		mainGroup = new Group();
		lblNumber = new Label("00", new LabelStyle(font,Color.WHITE));
		btnOk = new Button(new TextureRegionDrawable(atlas.findRegion("btn-ok-1")), new TextureRegionDrawable(atlas.findRegion("btn-ok-2")));
		
		btnAdd = new Button(new TextureRegionDrawable(atlas.findRegion("btn-add-1")), new TextureRegionDrawable(atlas.findRegion("btn-add-2")));
		btnSub = new Button(new TextureRegionDrawable(atlas.findRegion("btn-sub-1")), new TextureRegionDrawable(atlas.findRegion("btn-sub-2")));
		
		
		Table table = new Table();
		table.setBackground(new NinePatchDrawable(atlas.createPatch("ui-board")));
		table.pack();
		table.setSize(500, 350);
		mainGroup.setSize(500, 350);
		mainGroup.setPosition(Engine.getWidth()/2-mainGroup.getWidth()/2, Engine.getHeight()/2-mainGroup.getHeight()/2);
		
		
		btnOk.setPosition(mainGroup.getWidth()/2-btnOk.getWidth()/2, 35);
		btnSub.setPosition(50, 100);
		btnAdd.setPosition(mainGroup.getWidth()-50-btnAdd.getWidth(), 100);
		lblNumber.setPosition(mainGroup.getWidth()/2-lblNumber.getWidth()/2, 150);
		
		
		mainGroup.addActor(table);
		mainGroup.addActor(btnOk);
		mainGroup.addActor(btnAdd);
		mainGroup.addActor(btnSub);
		mainGroup.addActor(lblNumber);
		
		Image mask = new Image(atlas.findRegion("color"));
		mask.setSize(this.getWidth(), this.getHeight());
		mask.setColor(new Color(0,0,0,0.7f));
		this.addActor(mask);
		this.addActor(mainGroup);
		
		//this.getColor().a = 0;
		
		
		btnOk.addListener(new ClickListener(){
			@Override
			public void clicked(InputEvent event, float x, float y) {
				aim.setTroopNumber(number);
				close();
				super.clicked(event, x, y);
			}
		});
		btnSub.addListener(new ClickListener(){
			@Override
			public void clicked(InputEvent event, float x, float y) {
				number--;
				if(number<=0){
					btnSub.setTouchable(Touchable.disabled);
				}
				lblNumber.setText(number+"");
				aim.setTroopNumber(number);
				super.clicked(event, x, y);
			}
		});
		btnAdd.addListener(new ClickListener(){
			@Override
			public void clicked(InputEvent event, float x, float y) {
				number++;
				if(number>0){
					btnSub.setTouchable(Touchable.enabled);
				}
				lblNumber.setText(number+"");
				aim.setTroopNumber(number);
				super.clicked(event, x, y);
			}
		});
		
		mainGroup.setY(mainGroup.getY()+800);
	}
	public void close(){
		mainGroup.addAction(Actions.sequence(Actions.moveBy(0,800,0.2f,Interpolation.swingIn),Actions.run(new Runnable() {	
			@Override
			public void run() {
				TroopNumberSetter.this.remove();
			}
		})));
	}
	public void show(TroopItem troopItem){
		this.number = troopItem.number;
		this.lblNumber.setText(""+this.number);
		this.aim = troopItem;
		mainGroup.addAction(Actions.moveBy(0,-800,0.2f,Interpolation.swingOut));
	}
	
	
}
