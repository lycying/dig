package info.u250.digs.scenes.game.dialog;

import info.u250.c2d.engine.Engine;
import info.u250.digs.scenes.game.Level;

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

public class InfoDialogForDefaultLevelCompleteCallback extends Group{
	final Level level;
	Label lblLevelName;
	Label lblGold;
	Label lblGoldValue ;
	Label lblVictory;
	Label lblTimeCost;
	Label lblTimeCostValue;
	Label lblMeetKa;
	Label lblMeetKaValue ;
	Label lblDefeat;
	Label lblDefeatKaDie ;
	Label lblDefeatNpcDie ;
	Label lblDefeatTimeUp;
	
	Image deco1,deco2;
	Table table = new Table();
	public InfoDialogForDefaultLevelCompleteCallback(final Level level){
		this.level = level;
		
		TextureAtlas atlas = Engine.resource("All");
		BitmapFont font = Engine.resource("MenuFont");
		BitmapFont bigFont = Engine.resource("BigFont");
		this.setSize(Engine.getWidth(), Engine.getHeight());
		
		table.setBackground(new NinePatchDrawable(atlas.createPatch("detail")));
		
		lblLevelName = new Label("",new LabelStyle(bigFont, Color.BLACK));
		lblGoldValue = new Label("",new LabelStyle(font, Color.valueOf("7fa400")));
		lblVictory = new Label(Engine.getLanguagesManager().getString("java.victory"),new LabelStyle(font, Color.BLACK));
		lblGold = new Label("   "+Engine.getLanguagesManager().getString("java.gold"),new LabelStyle(font, Color.valueOf("102c13")));
		lblMeetKa = new Label("   "+Engine.getLanguagesManager().getString("java.meetka"),new LabelStyle(font, Color.valueOf("082a67")));
		lblMeetKaValue = new Label("10",new LabelStyle(font, Color.valueOf("6e9039")));
		lblDefeat = new Label(Engine.getLanguagesManager().getString("java.defeat"),new LabelStyle(font, Color.BLACK));
		lblDefeatKaDie = new Label("   "+Engine.getLanguagesManager().getString("java.kadie"),new LabelStyle(font, Color.valueOf("650051")));
		lblDefeatNpcDie = new Label("   "+Engine.getLanguagesManager().getString("java.alldie"),new LabelStyle(font, Color.valueOf("650051")));
		lblDefeatTimeUp = new Label("   "+Engine.getLanguagesManager().getString("java.timeup"),new LabelStyle(font, Color.valueOf("650051")));
		lblTimeCost = new Label("   "+Engine.getLanguagesManager().getString("java.intime"),new LabelStyle(font, Color.valueOf("102c13")));
		lblTimeCostValue = new Label("",new LabelStyle(font, Color.valueOf("7fa400")));
		
		deco1 = new Image(atlas.findRegion("fire-bottom"));
		deco2 = new Image(atlas.findRegion("fire-bottom"));
		
		table.pad(40);
		table.pack();
		this.addActor(table);
		
		this.addListener(new ClickListener(){
			@Override
			public void clicked(InputEvent event, float x, float y) {
				level.getGame().reallyStartLevel();
				hide();
				super.clicked(event, x, y);
			}
		});
		this.setColor(new Color(1,1,1,0));
	}
	public void show(){
		this.clearActions();		
		lblLevelName.setText(level.config.idxName);
		lblGoldValue.setText(level.config.gold+"");
		lblMeetKaValue.setText(level.config.ka+"");
		lblTimeCostValue.setText(level.config.time+" s");
		
		table.clear();
		
		table.add(lblLevelName).colspan(2).align(BaseTableLayout.CENTER).row();
		table.add(lblVictory).align(BaseTableLayout.LEFT);
		table.add(deco1).align(BaseTableLayout.RIGHT).row();
		
		if(level.config.gold>0){
			table.add(lblGold).align(BaseTableLayout.LEFT);
			table.add(lblGoldValue).align(BaseTableLayout.RIGHT);
			table.row();
		}
		if(level.config.ka>0){
			table.add(lblMeetKa).align(BaseTableLayout.LEFT);
			table.add(lblMeetKaValue).align(BaseTableLayout.RIGHT);
			table.row();
		}
		if(level.config.time>0){
			table.add(lblTimeCost).align(BaseTableLayout.LEFT);
			table.add(lblTimeCostValue).align(BaseTableLayout.RIGHT);
			table.row();
		}
		
		table.add(lblDefeat).align(BaseTableLayout.LEFT);
		table.add(deco2).align(BaseTableLayout.RIGHT).row();
		table.add(lblDefeatNpcDie).colspan(2).align(BaseTableLayout.LEFT).row();
		
		if(level.config.ka>0){
			table.add(lblDefeatKaDie).colspan(2).align(BaseTableLayout.LEFT).row();
		}
		if(level.config.time>0){
			table.add(lblDefeatTimeUp).colspan(2).align(BaseTableLayout.LEFT).row();
		}
		
		
		table.pack();
		this.getColor().a = 1;
		table.setPosition(Engine.getWidth()/2-table.getWidth()/2, Engine.getHeight()/2-table.getHeight()/2);
		
		Engine.getSoundManager().playSound("SoundNew");
	}
	public void hide(){
		final InfoDialogForDefaultLevelCompleteCallback pane = InfoDialogForDefaultLevelCompleteCallback.this;
		pane.addAction(Actions.sequence(Actions.fadeOut(0.5f),Actions.run(new Runnable() {
			@Override
			public void run() {
				Engine.getMusicManager().playMusic("MusicBattle", true);//play the battle music here
				pane.remove();	
			}
		})));
	}
}
