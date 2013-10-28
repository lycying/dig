package info.u250.digs.scenes.level;

import info.u250.c2d.engine.Engine;
import info.u250.digs.Digs;
import info.u250.digs.scenes.LevelScene;

import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.graphics.g2d.TextureAtlas;
import com.badlogic.gdx.scenes.scene2d.Group;
import com.badlogic.gdx.scenes.scene2d.InputEvent;
import com.badlogic.gdx.scenes.scene2d.ui.Image;
import com.badlogic.gdx.scenes.scene2d.ui.Label;
import com.badlogic.gdx.scenes.scene2d.ui.Label.LabelStyle;
import com.badlogic.gdx.scenes.scene2d.ui.Table;
import com.badlogic.gdx.scenes.scene2d.utils.ClickListener;
import com.badlogic.gdx.scenes.scene2d.utils.NinePatchDrawable;

public class LevelItemTable extends  Group{
	private int pack;
	private int level;
	public LevelItemTable(final LevelScene levelScene,final int pack,final int level ,String levelName){
		this.pack = pack;
		this.level = level;
		
		this.setSize(680, 80);
		TextureAtlas texs = Engine.resource("Texs");
		TextureAtlas atlas = Engine.resource("All");
		Image bg = new Image( atlas.createPatch("level-item-bg"));
		switch(pack){
		case 0:
			bg.setColor(new Color(210f/255f,254f/255f,212f/255f,0.4f));
			break;
		case 1:
			bg.setColor(new Color(210f/255f,242f/255f,254f/255f,0.4f));
			break;
		case 2:
			bg.setColor(new Color(254f/255f,238f/255f,210f/255f,0.4f));
			break;
		}
		bg.setSize(this.getWidth(), this.getHeight());
		Label title = new Label(level+":"+levelName,new LabelStyle(
				Engine.resource("MenuFont",BitmapFont.class),Color.WHITE));
		title.setPosition(100, 25);
		Image icon = new Image(texs.getRegions().get(level));
		icon.setSize(60, 60);
		icon.setPosition(10f, 10f);
		
		
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
		
		
		
		Image menu_play = new Image(atlas.findRegion("menu_play"));
		menu_play.setPosition(this.getWidth()-menu_play.getWidth()-20,(this.getHeight()-menu_play.getHeight())/2);
	
		
		menu_play.addListener(new ClickListener(){
			@Override
			public void clicked(InputEvent event, float x, float y) {
				levelScene.startLevel(pack,level);
				super.clicked(event, x, y);
			}
		});
		
		Image lock = new Image(atlas.findRegion("lock"));
		Image pass = new Image(atlas.findRegion("pass"));
		Table t2 = new Table();
		t2.setBackground(new NinePatchDrawable(atlas.createPatch("label2")));
		t2.add(new Image(atlas.findRegion("char")));
		t2.add(new Label(Digs.RND.nextInt(2000)+"",new LabelStyle(font, Color.BLACK)));
		t2.pack();
		t2.setPosition(100, 10);
		
		lock.setPosition(80, 50);
		pass.setPosition(80, 50);
		this.addActor(bg);
		this.addActor(icon);
		this.addActor(title);
		
		this.addActor(t2);
		
		if(level<6){
			this.addActor(pass);
			this.addActor(menu_play);
			this.addActor(t);
		}else{
			this.addActor(lock);
			this.setColor(Color.DARK_GRAY);
		}
		
	} 
	public void refresh(){
		String key = "au"+pack+level;
		Engine.getPreferences().getString(key);
	}
}
