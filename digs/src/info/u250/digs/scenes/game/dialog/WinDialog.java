package info.u250.digs.scenes.game.dialog;

import info.u250.c2d.engine.Engine;
import info.u250.c2d.graphic.WebColors;
import info.u250.digs.Digs;
import info.u250.digs.scenes.GameScene;
import info.u250.digs.scenes.game.LevelConfig;
import info.u250.digs.scenes.level.LevelIdx;
import info.u250.digs.scenes.ui.ParticleEffectActor;
import info.u250.digs.scenes.ui.Water;

import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.graphics.g2d.NinePatch;
import com.badlogic.gdx.graphics.g2d.ParticleEffect;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.g2d.TextureAtlas;
import com.badlogic.gdx.scenes.scene2d.Group;
import com.badlogic.gdx.scenes.scene2d.InputEvent;
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
	Button btnExit ;
	Button btnRestart;
	
	public Water water = new Water( 201, 260, 
			new Color(151f/255f,196f/255f,188f/255f,0.2f),
			new Color(151f/255f,196f/255f,188f/255f,0.2f));
	public WinDialog(final GameScene game){		
		TextureAtlas atlas = Engine.resource("All");
		
		this.setSize(Engine.getWidth(), Engine.getHeight());
		

		btnNextLevel = new Button(new TextureRegionDrawable(atlas.findRegion("d-nextlevel")), new TextureRegionDrawable(atlas.findRegion("d-nextlevel")));
		btnExit = new Button(new TextureRegionDrawable(atlas.findRegion("d-exit")), new TextureRegionDrawable(atlas.findRegion("d-exit")));
		
		btnNextLevel.setSize(btnNextLevel.getPrefWidth(), btnNextLevel.getPrefHeight());
		btnExit.setSize(btnExit.getPrefWidth(), btnExit.getPrefHeight());
		btnRestart = new Button(new TextureRegionDrawable(atlas.findRegion("restart")), new TextureRegionDrawable(atlas.findRegion("restart")));
		
		Table functionTable = new Table();
		functionTable.setBackground(new NinePatchDrawable(atlas.createPatch("detail")));
//		btnExit.setPosition(70, 380);
//		btnRestart.setPosition(btnExit.getX()+btnExit.getWidth()+30, btnExit.getY());
//		btnNextLevel.setPosition(btnRestart.getX()+btnRestart.getWidth()+30, btnRestart.getY());
		functionTable.pad(30);
		functionTable.add(btnExit).row().space(20);
		functionTable.add(btnRestart).row().spaceBottom(20);
		functionTable.add(btnNextLevel).row().spaceBottom(20);
		functionTable.pack();

	
//		Image bg = new Image(atlas.findRegion("win-bg"));
//		bg.setPosition(Engine.getWidth()/2-bg.getWidth()/2,0);
		functionTable.setPosition(Engine.getWidth()-functionTable.getWidth()+20, 40);

		Image mask = new Image(atlas.findRegion("color"));
		mask.setSize(this.getWidth(), this.getHeight());
		mask.setColor(new Color(0,0,0,0.7f));
		this.addActor(mask);
//		this.addActor(bg);
		this.addActor(functionTable);
		
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
		resultTable.pad(40);
		resultTable.add(levelTableInfo).colspan(2).align(BaseTableLayout.CENTER);
		resultTable.row();
		resultTable.add(new Label("Gold:", new LabelStyle(font,Color.WHITE))).align(BaseTableLayout.LEFT).minWidth(250);
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
		resultTable.setPosition(Engine.getWidth()/2-resultTable.getWidth()/2, Engine.getHeight()/2-resultTable.getHeight()/2);
		
		
		ParticleEffect e = Engine.resource("Effect");
		ParticleEffectActor p = new ParticleEffectActor(e,"win");
		p.setPosition(resultTable.getX()+30, resultTable.getY()+resultTable.getHeight()-30);
		this.addActor(p);
		
		this.addActor(resultTable);
		
		btnNextLevel.addListener(new ClickListener(){
			@Override
			public void clicked(InputEvent event, float x, float y) {
				Engine.doResume();
				game.nextLevel();
				super.clicked(event, x, y);
			}
		});
		btnExit.addListener(new ClickListener(){
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
	public void show(LevelConfig config,int gold,int npc,int npcDead,int time){
		lblLvlIdx.setText("Level "+(config.idx+1));
		lblLvlDesc.setText(LevelIdx.getLevelName(config.pack, config.idx));
		lblCoin.setText(gold+"");
		lblBBMan.setText(npc+"");
		lblBBManDead.setText(npcDead+"");
		lblTime.setText(time+"");
		resultTable.pack();
		resultTable.setPosition(Engine.getWidth()/2-resultTable.getWidth()/2, Engine.getHeight()/2-resultTable.getHeight()/2);
	}
	public void close(){
		this.remove();
	}
	float accum = 0;
	@Override
	public void draw(SpriteBatch batch, float parentAlpha) {
		super.draw(batch, parentAlpha);
		accum += Engine.getDeltaTime();
		if(accum>0.7f){
			accum -=0.7f;
			water.splash(Digs.RND.nextInt((int)Engine.getWidth()), 200*Digs.RND.nextFloat());
		}
		water.update(Engine.getDeltaTime());
		water.draw();
//		batch.end();
//		batch.begin();
//		table.draw(batch, parentAlpha);
	}
}
