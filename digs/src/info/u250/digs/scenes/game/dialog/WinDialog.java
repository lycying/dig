package info.u250.digs.scenes.game.dialog;

import info.u250.c2d.engine.Engine;
import info.u250.c2d.graphic.WebColors;
import info.u250.digs.scenes.GameScene;
import info.u250.digs.scenes.game.LevelConfig;
import info.u250.digs.scenes.level.LevelIdx;

import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.graphics.g2d.NinePatch;
import com.badlogic.gdx.graphics.g2d.TextureAtlas;
import com.badlogic.gdx.scenes.scene2d.Group;
import com.badlogic.gdx.scenes.scene2d.InputEvent;
import com.badlogic.gdx.scenes.scene2d.actions.Actions;
import com.badlogic.gdx.scenes.scene2d.ui.Button;
import com.badlogic.gdx.scenes.scene2d.ui.Image;
import com.badlogic.gdx.scenes.scene2d.ui.Label;
import com.badlogic.gdx.scenes.scene2d.ui.Label.LabelStyle;
import com.badlogic.gdx.scenes.scene2d.ui.Table;
import com.badlogic.gdx.scenes.scene2d.utils.ClickListener;
import com.badlogic.gdx.scenes.scene2d.utils.NinePatchDrawable;
import com.badlogic.gdx.scenes.scene2d.utils.TextureRegionDrawable;
import com.esotericsoftware.tablelayout.BaseTableLayout;

public class WinDialog extends Group {
	
	Button btnNextLevel ;
	Button btnMenu ;
	Button btnRestart;
	
	Table functionTable;
	public WinDialog(final GameScene game){		
		TextureAtlas atlas = Engine.resource("All");
		
		this.setSize(Engine.getWidth(), Engine.getHeight());
		

		btnNextLevel = new Button(new TextureRegionDrawable(atlas.findRegion("w-next-level")), new TextureRegionDrawable(atlas.findRegion("w-next-level")));
		btnMenu = new Button(new TextureRegionDrawable(atlas.findRegion("w-menu")), new TextureRegionDrawable(atlas.findRegion("w-menu")));
		
		btnNextLevel.setSize(btnNextLevel.getPrefWidth(), btnNextLevel.getPrefHeight());
		btnMenu.setSize(btnMenu.getPrefWidth(), btnMenu.getPrefHeight());
		btnRestart = new Button(new TextureRegionDrawable(atlas.findRegion("w-restart")), new TextureRegionDrawable(atlas.findRegion("w-restart")));
		
		functionTable = new Table();
		functionTable.add(btnMenu).spaceRight(10);
		functionTable.add(btnRestart).spaceRight(10);
		functionTable.add(btnNextLevel).spaceRight(10);
		functionTable.pack();
		functionTable.setPosition(Engine.getWidth()-functionTable.getWidth()+20, 40);

		Image mask = new Image(atlas.findRegion("color"));
		mask.setSize(this.getWidth(), this.getHeight());
		mask.setColor(new Color(0,0,0,0.7f));
		this.addActor(mask);
		
		
		BitmapFont font = Engine.resource("BigFont");
		
		lblCoin = new Label("243", new LabelStyle(font,Color.YELLOW));
		lblBBMan = new Label("50", new LabelStyle(font,Color.YELLOW));
		lblBBManDead = new Label("25", new LabelStyle(font,Color.YELLOW));
		lblTime = new Label("23:34", new LabelStyle(font,Color.YELLOW));
		lblLvlIdx = new Label("Level 1:", new LabelStyle(font,WebColors.TEAL.get()));
		lblLvlDesc = new Label("Rookie Training Ground", new LabelStyle(font,WebColors.DARK_SLATE_BLUE.get()));
		
		Table levelTableInfo = new Table();
		levelTableInfo.pad(20);
		levelTableInfo.add(lblLvlIdx);
		levelTableInfo.add(lblLvlDesc);
		NinePatch line = atlas.createPatch("label");
		line.setColor(new Color(0,0,0,0.3f));
		levelTableInfo.setBackground(new NinePatchDrawable(line));
		levelTableInfo.pack();
		
		
		resultTable = new Table();
//		NinePatch nine = new NinePatch(atlas.findRegion("color"));
		NinePatch nine = atlas.createPatch("win-bg");
//		nine.setColor(new Color(1,1,1,0.5f));
		resultTable.setBackground(new NinePatchDrawable(nine));
		resultTable.add(levelTableInfo).colspan(2).align(BaseTableLayout.CENTER);
		resultTable.row();
		resultTable.add(new Label("Gold:", new LabelStyle(font,Color.WHITE))).align(BaseTableLayout.LEFT).minWidth(450);
		resultTable.add(lblCoin);
		resultTable.row();
		resultTable.add(new Label("BBMan Born:", new LabelStyle(font,Color.WHITE))).align(BaseTableLayout.LEFT);
		resultTable.add(lblBBMan);
		resultTable.row();
		resultTable.add(new Label("BBMan Dead:", new LabelStyle(font,Color.WHITE))).align(BaseTableLayout.LEFT);
		resultTable.add(lblBBManDead);
		resultTable.row();
		resultTable.add(new Label("Time:", new LabelStyle(font,Color.WHITE))).align(BaseTableLayout.LEFT);
		resultTable.add(lblTime);
		resultTable.pack();
		resultTable.setPosition(Engine.getWidth()/2-resultTable.getWidth()/2, Engine.getHeight()-resultTable.getHeight());
		
		this.addActor(resultTable);
		this.addActor(functionTable);
		
		eye1 = new Image(atlas.findRegion("dog-eye"));
		eye2 = new Image(atlas.findRegion("dog-eye"));
		eye1.setOrigin(eye1.getWidth()/2,eye1.getHeight()/2);
		eye2.setOrigin(eye1.getWidth()/2,eye1.getHeight()/2);
		eye1.addAction(Actions.forever(Actions.sequence(
				Actions.scaleTo(0.9f, 0, 0.1f),
				Actions.scaleTo(1, 1, 0.1f),
				Actions.scaleTo(0.9f, 0, 0.1f),
				Actions.scaleTo(1, 1, 0.1f),
				Actions.delay(2)
		)));
		eye2.addAction(Actions.forever(Actions.sequence(
				Actions.scaleTo(0.9f, 0, 0.1f),
				Actions.scaleTo(1, 1, 0.1f),
				Actions.scaleTo(0.9f, 0, 0.1f),
				Actions.scaleTo(1, 1, 0.1f),
				Actions.delay(2)
		)));
		this.addActor(eye1);
		this.addActor(eye2);
		
		btnNextLevel.addListener(new ClickListener(){
			@Override
			public void clicked(InputEvent event, float x, float y) {
				Engine.doResume();
				game.nextLevel();
				super.clicked(event, x, y);
			}
		});
		btnMenu.addListener(new ClickListener(){
			@Override
			public void clicked(InputEvent event, float x, float y) {
				Engine.doResume();
				game.drive.setToLevelScene();
				super.clicked(event, x, y);
			}
		});
		btnRestart.addListener(new ClickListener(){
			@Override
			public void clicked(InputEvent event, float x, float y) {
				Engine.doResume();
				game.restart();
				super.clicked(event, x, y);
			}
		});
	}
	Table resultTable = null;
	Label lblCoin = null;
	Label lblBBMan = null;
	Label lblBBManDead = null;
	Label lblTime = null;
	Label lblLvlIdx = null;
	Label lblLvlDesc = null;
	final Image eye1,eye2;
	public void show(LevelConfig config,int gold,int npc,int npcDead,int time){
		lblLvlIdx.setText("Level "+(config.idx+1)+": ");
		lblLvlDesc.setText(LevelIdx.getLevelName(config.pack, config.idx));
		lblCoin.setText(gold+"");
		lblBBMan.setText(npc+"");
		lblBBManDead.setText(npcDead+"");
		lblTime.setText(time+"");
		resultTable.pack();
		resultTable.setPosition(200, Engine.getHeight()-resultTable.getHeight()-60);
		eye1.setPosition(resultTable.getX()+140, Engine.getHeight()-125);
		eye2.setPosition(resultTable.getX()+180, Engine.getHeight()-123);
		functionTable.setPosition(200+170, Engine.getHeight()-functionTable.getHeight()-174);
	}
	public void close(){
		this.remove();
	}
}
