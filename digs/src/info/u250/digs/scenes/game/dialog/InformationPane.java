package info.u250.digs.scenes.game.dialog;

import info.u250.c2d.engine.Engine;
import info.u250.digs.scenes.GameScene;

import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.graphics.g2d.TextureAtlas;
import com.badlogic.gdx.scenes.scene2d.Group;
import com.badlogic.gdx.scenes.scene2d.InputEvent;
import com.badlogic.gdx.scenes.scene2d.actions.Actions;
import com.badlogic.gdx.scenes.scene2d.ui.Image;
import com.badlogic.gdx.scenes.scene2d.ui.Label;
import com.badlogic.gdx.scenes.scene2d.ui.Label.LabelStyle;
import com.badlogic.gdx.scenes.scene2d.ui.Table;
import com.badlogic.gdx.scenes.scene2d.utils.ClickListener;
import com.badlogic.gdx.scenes.scene2d.utils.NinePatchDrawable;
import com.esotericsoftware.tablelayout.BaseTableLayout;

public class InformationPane extends Group{
	final GameScene game;
	Label lblLevelName;
	Table table = new Table();
	public InformationPane(final GameScene game){
		this.game = game;
		TextureAtlas atlas = Engine.resource("All");
		BitmapFont font = Engine.resource("MenuFont");
		BitmapFont bigFont = Engine.resource("BigFont");
		this.setSize(Engine.getWidth(), Engine.getHeight());
		
		lblLevelName = new Label("Walking In The Cloud",new LabelStyle(bigFont, Color.BLACK));
		
		table.setBackground(new NinePatchDrawable(atlas.createPatch("detail")));
		table.add(lblLevelName).colspan(2).align(BaseTableLayout.CENTER).row();
		table.add(new Label("Victory:",new LabelStyle(font, Color.BLACK))).align(BaseTableLayout.LEFT);
		table.add(new Image(atlas.findRegion("fire-bottom"))).align(BaseTableLayout.RIGHT).row();
		table.add(new Label("   Gold:",new LabelStyle(font, Color.valueOf("102c13")))).align(BaseTableLayout.LEFT);
		table.add(new Label("10002",new LabelStyle(font, Color.valueOf("7fa400")))).align(BaseTableLayout.RIGHT);
		table.row();
		table.add(new Label("   Meet Ka:",new LabelStyle(font, Color.valueOf("082a67")))).align(BaseTableLayout.LEFT);
		table.add(new Label("10",new LabelStyle(font, Color.valueOf("6e9039")))).align(BaseTableLayout.RIGHT);
		table.row();
		table.add(new Label("Defeat:",new LabelStyle(font, Color.BLACK))).align(BaseTableLayout.LEFT);
		table.add(new Image(atlas.findRegion("fire-bottom"))).align(BaseTableLayout.RIGHT).row();
		table.add(new Label("   Ka die or all dig die",new LabelStyle(font, Color.valueOf("650051")))).colspan(2).align(BaseTableLayout.LEFT).row();
		table.add(new Label("   Time up",new LabelStyle(font, Color.valueOf("650051")))).colspan(2).align(BaseTableLayout.LEFT).row();
		
		table.pad(40);
		table.pack();
		this.addActor(table);
		
		this.addListener(new ClickListener(){
			@Override
			public void clicked(InputEvent event, float x, float y) {
				game.setShowAim(false);
				hide();
				super.clicked(event, x, y);
			}
		});
		this.setColor(new Color(1,1,1,0));
	}
	public void show(){
		this.clearActions();
		final InformationPane pane = InformationPane.this;
		lblLevelName.setText(game.level.config.idxName);
		table.pack();
		pane.getColor().a = 1;
		table.setPosition(Engine.getWidth()/2-table.getWidth()/2, Engine.getHeight()/2-table.getHeight()/2);
	}
	public void hide(){
		final InformationPane pane = InformationPane.this;
		pane.addAction(Actions.sequence(Actions.fadeOut(0.5f),Actions.run(new Runnable() {
			@Override
			public void run() {
				Engine.getMusicManager().playMusic("MusicBattle", true);//play the battle music here
				pane.remove();	
			}
		})));
	}
}
