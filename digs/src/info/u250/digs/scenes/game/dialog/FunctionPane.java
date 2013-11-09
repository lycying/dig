package info.u250.digs.scenes.game.dialog;

import info.u250.c2d.engine.Engine;
import info.u250.digs.scenes.GameScene;
import info.u250.digs.scenes.game.Level.FingerMode;

import com.badlogic.gdx.graphics.g2d.TextureAtlas;
import com.badlogic.gdx.scenes.scene2d.InputEvent;
import com.badlogic.gdx.scenes.scene2d.ui.Button;
import com.badlogic.gdx.scenes.scene2d.ui.ButtonGroup;
import com.badlogic.gdx.scenes.scene2d.ui.Table;
import com.badlogic.gdx.scenes.scene2d.utils.ClickListener;
import com.badlogic.gdx.scenes.scene2d.utils.TextureRegionDrawable;

public class FunctionPane extends Table{
	/* if it is the fill mode currently */
	private FingerMode fingerMode = FingerMode.Clear;

	public FunctionPane(final GameScene gameScene){
		final TextureAtlas atlas = Engine.resource("All");
			
		final Button btn_home = new Button(new TextureRegionDrawable(atlas.findRegion("btn-home-1")),null,new TextureRegionDrawable(atlas.findRegion("btn-home-2")));
		final Button btn_fill = new Button(new TextureRegionDrawable(atlas.findRegion("btn-fill-1")),null,new TextureRegionDrawable(atlas.findRegion("btn-fill-2")));
		final Button btn_dig = new Button(new TextureRegionDrawable(atlas.findRegion("btn-dig-1")),null,new TextureRegionDrawable(atlas.findRegion("btn-dig-2")));
		final Button btn_npc = new Button(new TextureRegionDrawable(atlas.findRegion("btn-npc-1")),null,new TextureRegionDrawable(atlas.findRegion("btn-npc-2")));
		
		
		new ButtonGroup(btn_home,btn_dig,btn_fill,btn_npc);//collection them
		this.add(btn_home).spaceRight(5);
		this.add(btn_npc).spaceRight(5);
		this.add(btn_fill).spaceRight(5);
		this.add(btn_dig).spaceRight(5);
		btn_dig.setChecked(true);
		
		btn_home.addListener(new ClickListener(){
			public void clicked(InputEvent event, float x, float y) {
				fingerMode = FingerMode.Home;
				Engine.getSoundManager().playSound("SoundFunc");
				super.clicked(event, x, y);
			}});
		btn_npc.addListener(new ClickListener(){
			public void clicked(InputEvent event, float x, float y) {
				fingerMode = FingerMode.Npc;
				Engine.getSoundManager().playSound("SoundFunc");
				super.clicked(event, x, y);
			}});
		btn_fill.addListener(new ClickListener(){
			public void clicked(InputEvent event, float x, float y) {
				fingerMode = FingerMode.Fill;
				Engine.getSoundManager().playSound("SoundFunc");
				super.clicked(event, x, y);
			}});
		btn_dig.addListener(new ClickListener(){
			public void clicked(InputEvent event, float x, float y) {
				Engine.getSoundManager().playSound("SoundFunc");
				fingerMode = FingerMode.Clear;
				super.clicked(event, x, y);
			}});

		this.pack();
	}

	public FingerMode getFingerMode() {
		return fingerMode;
	}
	
}
