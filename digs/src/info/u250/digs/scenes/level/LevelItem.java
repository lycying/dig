package info.u250.digs.scenes.level;

import info.u250.c2d.engine.Engine;
import info.u250.c2d.graphic.WebColors;
import info.u250.digs.Digs;
import info.u250.digs.IO;
import info.u250.digs.scenes.LevelScene;
import info.u250.digs.scenes.ui.ParticleEffectActor;

import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.graphics.g2d.NinePatch;
import com.badlogic.gdx.graphics.g2d.ParticleEffect;
import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.graphics.g2d.TextureAtlas;
import com.badlogic.gdx.scenes.scene2d.Group;
import com.badlogic.gdx.scenes.scene2d.InputEvent;
import com.badlogic.gdx.scenes.scene2d.ui.Button;
import com.badlogic.gdx.scenes.scene2d.ui.Image;
import com.badlogic.gdx.scenes.scene2d.ui.Label;
import com.badlogic.gdx.scenes.scene2d.ui.Label.LabelStyle;
import com.badlogic.gdx.scenes.scene2d.ui.TextButton;
import com.badlogic.gdx.scenes.scene2d.ui.TextButton.TextButtonStyle;
import com.badlogic.gdx.scenes.scene2d.utils.ClickListener;
import com.badlogic.gdx.scenes.scene2d.utils.NinePatchDrawable;
import com.badlogic.gdx.scenes.scene2d.utils.SpriteDrawable;

public class LevelItem extends  Group{
	private int pack;
	private int level;
	final Button menu_play,leaderboard;
	final Image lock,pass,bg;
	final Label title,levelNumber;
	static ParticleEffectActor current = null;
	public LevelItem(final LevelScene levelScene,final int pack,final int level ,String levelName){
		this.pack = pack;
		this.level = level;
		
		this.setSize(680, 80);
		TextureAtlas atlas = Engine.resource("All");
		//background
		bg = new Image( atlas.createPatch("level-item-bg-3"));
		switch(pack){
		case 0:
			bg.setColor(WebColors.AQUA.get());
			break;
		case 1:
			bg.setColor(WebColors.LIGHT_CORAL.get());
			break;
		case 2:
			bg.setColor(WebColors.ORANGE.get());
			break;
		}
		bg.setSize(this.getWidth(), this.getHeight());
		
		//title
		title = new Label(levelName,new LabelStyle(Engine.resource("MenuFont",BitmapFont.class),Color.YELLOW));
		
		
		
		BitmapFont bigFont = Engine.resource("BigFont");
		NinePatch patchBg = atlas.createPatch("level-item-bg-4");
		switch(pack){
		case 0:
			patchBg.setColor(WebColors.LIGHT_BLUE.get());
			break;
		case 1:
			patchBg.setColor(WebColors.RED.get());
			break;
		case 2:
			patchBg.setColor(WebColors.GOLD.get());
			break;
		}
		TextButtonStyle style = new TextButtonStyle(
				new NinePatchDrawable(patchBg), null, null, bigFont);
		style.fontColor = Color.WHITE;
		style.downFontColor = Color.RED;
		menu_play = new TextButton(Engine.getLanguagesManager().getString("java.play"),style);
		menu_play.pack();
		menu_play.setHeight(80);
		
		menu_play.setPosition(this.getWidth()-menu_play.getWidth(),(this.getHeight()-menu_play.getHeight())/2);
		
		menu_play.addListener(new ClickListener(){
			@Override
			public void clicked(InputEvent event, float x, float y) {
				Engine.getSoundManager().playSound("SoundClick");
				levelScene.startLevel(pack,level);
				super.clicked(event, x, y);
			}
		});

		
		lock = new Image(atlas.findRegion("lock"));
		pass = new Image(atlas.findRegion("pass"));
		
		Sprite leaderboardDisabled = atlas.createSprite("leaderboards");
		leaderboardDisabled.setColor(Color.YELLOW);
		leaderboard = new Button(
				new SpriteDrawable(atlas.createSprite("leaderboards")),
				new SpriteDrawable(leaderboardDisabled));
		
		leaderboard.addListener(new ClickListener(){
			@Override
			public void clicked(InputEvent event, float x, float y) {
				Engine.getSoundManager().playSound("SoundClick");
				Digs.getGPSR().gpsShowLeaderboard(IO.getLeaderboardHandel(pack, level));
				super.clicked(event, x, y);
			}
		});
		
		if(null == current){
			ParticleEffect e = Engine.resource("Effect");
			current = new ParticleEffectActor(e, "fire");
		}
		
		this.addActor(bg);
		this.addActor(title);
		
		levelNumber = new Label(""+(level+1),new LabelStyle(bigFont,Color.WHITE));
		NinePatch  patch = atlas.createPatch("level-item-bg-4");
		levelNumber.getStyle().background = new NinePatchDrawable(patch);
		levelNumber.pack();
		levelNumber.setHeight(80);
		switch(pack){
		case 0:
			patch.setColor(WebColors.LIGHT_BLUE.get());
			break;
		case 1:
			patch.setColor(WebColors.RED.get());
			break;
		case 2:
			patch.setColor(WebColors.GOLD.get());
			break;
		}
		this.addActor(levelNumber);
	} 
	public void refresh(){
		int currentPack = IO.getPack();
		int currentLevel = IO.getLevel();
//		int currentPack = 3;
//		int currentLevel = 99;
		pass.remove();
		lock.remove();
		menu_play.remove();
		leaderboard.remove();
		title.getStyle().fontColor = Color.YELLOW;
		if(currentPack>pack){//i have complete the level pack, the guide pack is public to everyone
			this.addActor(pass);
			this.addActor(menu_play);
			this.setColor(Color.WHITE);
		}else if(currentPack==pack){
			if(currentLevel>=level){//i have complete the level
				this.addActor(pass);
				this.addActor(menu_play);
				this.setColor(Color.WHITE);
			}else if( 
					(0==level&&currentLevel==0) || //first play the game
					(currentLevel==level-1)){ // i will play this level
				current.setPosition(60, 30);
				this.addActor(current);
				this.addActor(menu_play);
				title.getStyle().fontColor = Color.MAGENTA;
			}else{
				this.addActor(lock);
				this.setColor(Color.WHITE);
			}
		}else{
			if(pack-currentPack==1){
				if(level==0 && currentLevel==LevelIdx.Level_String[currentPack].length-1 ){
					current.setPosition(130, 30);
					this.addActor(current);
					this.addActor(menu_play);
					title.getStyle().fontColor = Color.MAGENTA;
				}else{
					this.addActor(lock);
				}
			}else{
				this.addActor(lock);
			}
			
		}
		
		title.setPosition(90, 15);
		lock.setPosition(45, 15);
		pass.setPosition(45, 15);
		levelNumber.setPosition(0, 0);
		bg.setPosition(0, 0);
		bg.setSize(this.getWidth(), this.getHeight());
		
		
		if(pack>0){
			title.setPosition(160, 15);
			lock.setPosition(115, 15);
			pass.setPosition(115, 15);
			levelNumber.setPosition(70, 0);
			leaderboard.setPosition(10, 10);
			this.addActor(leaderboard);
		}
		
	}
}
