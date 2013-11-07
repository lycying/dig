package info.u250.digs.scenes.level;

import info.u250.c2d.engine.Engine;
import info.u250.c2d.graphic.WebColors;
import info.u250.digs.Digs;
import info.u250.digs.IO;
import info.u250.digs.scenes.LevelScene;

import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.graphics.g2d.NinePatch;
import com.badlogic.gdx.graphics.g2d.TextureAtlas;
import com.badlogic.gdx.scenes.scene2d.Group;
import com.badlogic.gdx.scenes.scene2d.InputEvent;
import com.badlogic.gdx.scenes.scene2d.ui.Image;
import com.badlogic.gdx.scenes.scene2d.ui.Label;
import com.badlogic.gdx.scenes.scene2d.ui.Label.LabelStyle;
import com.badlogic.gdx.scenes.scene2d.ui.Table;
import com.badlogic.gdx.scenes.scene2d.utils.ClickListener;
import com.badlogic.gdx.scenes.scene2d.utils.NinePatchDrawable;

public class LevelItem extends  Group{
	private int pack;
	private int level;
	Image menu_play,lock,pass;
	public LevelItem(final LevelScene levelScene,final int pack,final int level ,String levelName){
		this.pack = pack;
		this.level = level;
		
		this.setSize(680, 80);
		TextureAtlas atlas = Engine.resource("All");
		//background
		final Image bg = new Image( atlas.createPatch("level-item-bg-5"));
		switch(pack){
		case 0:
			bg.setColor(WebColors.MEDIUM_SEA_GREEN.get());
			break;
		case 1:
			bg.setColor(WebColors.ORANGE.get());
			break;
		case 2:
			bg.setColor(WebColors.LIGHT_CORAL.get());
			break;
		}
		bg.setSize(this.getWidth(), this.getHeight());
		
		//title
		Label title = new Label(levelName,new LabelStyle(Engine.resource("MenuFont",BitmapFont.class),Color.YELLOW));
		title.setPosition(90, 25);
		
		BitmapFont font = Engine.resource("Font");
		Table t = new Table();
		t.setBackground(new NinePatchDrawable(atlas.createPatch("ui-label-bg")));
		t.add(new Image(atlas.findRegion("award")));
		t.add(new Image(atlas.findRegion("npc"))).spaceRight(5);
		t.add(new Label(Digs.RND.nextInt(2000)+"",new LabelStyle(font, Color.WHITE))).spaceRight(5);
		t.add(new Image(atlas.findRegion("dead"))).spaceRight(5);
		t.add(new Label(Digs.RND.nextInt(2000)+"",new LabelStyle(font, Color.WHITE))).spaceRight(5);
		t.add(new Image(atlas.findRegion("time"))).spaceRight(5);
		t.add(new Label(Digs.RND.nextInt(2000)+"",new LabelStyle(font, Color.WHITE))).spaceRight(5);
		t.add(new Image(atlas.findRegion("flag-gold-many"))).spaceRight(5);
		t.add(new Label(Digs.RND.nextInt(2000)+"",new LabelStyle(font, Color.WHITE))).spaceRight(5);
		t.pack();
		t.setPosition(180, 10);
		
		menu_play = new Image(atlas.findRegion("menu_play"));
		menu_play.setPosition(this.getWidth()-menu_play.getWidth()-20,(this.getHeight()-menu_play.getHeight())/2);
		menu_play.setColor(new Color(0.8f,0.8f,0.8f,0.8f));
		
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
		lock.setPosition(45, 15);
		pass.setPosition(45, 15);
		this.addActor(bg);
		this.addActor(title);
		
		Label levelNumber = new Label(""+(level+1),new LabelStyle(Engine.resource("BigFont",BitmapFont.class),Color.WHITE));
		NinePatch  patch = atlas.createPatch("level-item-bg-4");
		levelNumber.getStyle().background = new NinePatchDrawable(patch);
		levelNumber.pack();
		switch(pack){
		case 0:
			patch.setColor(WebColors.LIME.get());
			break;
		case 1:
			patch.setColor(WebColors.GOLD.get());
			break;
		case 2:
			patch.setColor(WebColors.RED.get());
			break;
		}
		this.addActor(levelNumber);
	} 
	public void refresh(){
		int currentPack = IO.getPack();
		int currentLevel = IO.getLevel();
		pass.remove();
		lock.remove();
		menu_play.remove();
		
		if(pack == 0 || currentPack>pack){//i have complete the level pack, the guide pack is public to everyone
			this.addActor(pass);
			this.addActor(menu_play);
			this.setColor(Color.WHITE);
		}else if(currentPack==pack){
			if(currentLevel>level){//i have complete the level
				this.addActor(pass);
				this.addActor(menu_play);
				this.setColor(Color.WHITE);
			}else if( (0==level&&currentLevel==0) || (currentLevel!=0&&currentLevel==level-1)){// i will play this level
				//TODO this should use a current flag
				this.addActor(menu_play);
				this.setColor(Color.YELLOW);
			}else{
				this.addActor(lock);
				this.setColor(Color.WHITE);
			}
		}else{
			this.addActor(lock);
		}
	}
}
