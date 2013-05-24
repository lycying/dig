package info.u250.digs.scenes;


import info.u250.c2d.engine.Engine;
import info.u250.c2d.engine.SceneStage;
import info.u250.digs.scenes.game.entity.BaseEntity;
import info.u250.digs.scenes.game.entity.BombMan;
import info.u250.digs.scenes.game.entity.HealBoy;
import info.u250.digs.scenes.game.entity.NPC;
import info.u250.digs.scenes.game.entity.Watchmen;
import info.u250.digs.scenes.npclist.NpcDetailTable;
import info.u250.digs.scenes.npclist.NpcItemTable;
import info.u250.digs.scenes.npclist.TroopItem;
import info.u250.digs.scenes.npclist.TroopNumberSetter;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.g2d.TextureAtlas;
import com.badlogic.gdx.scenes.scene2d.InputEvent;
import com.badlogic.gdx.scenes.scene2d.ui.Button;
import com.badlogic.gdx.scenes.scene2d.ui.Image;
import com.badlogic.gdx.scenes.scene2d.ui.ScrollPane;
import com.badlogic.gdx.scenes.scene2d.ui.Table;
import com.badlogic.gdx.scenes.scene2d.ui.ScrollPane.ScrollPaneStyle;
import com.badlogic.gdx.scenes.scene2d.utils.ClickListener;
import com.badlogic.gdx.scenes.scene2d.utils.NinePatchDrawable;
import com.badlogic.gdx.scenes.scene2d.utils.TextureRegionDrawable;

public class NpcListScene extends SceneStage {
	final TextureAtlas atlas;
	public final NpcDetailTable detailTable;
	
	final BaseEntity npc1;
	final BaseEntity npc2;
	final BaseEntity npc3;
	final BaseEntity npc4;
	
	public final Image followImage ;
	public final TroopNumberSetter troopNumberSetter;
	public NpcListScene(){
		atlas = Engine.resource("All");
		
		
		Table backgroundFromNinePatch = new Table();
		backgroundFromNinePatch.setBackground(new NinePatchDrawable(atlas.createPatch("ui-board")));
		backgroundFromNinePatch.pack();
		backgroundFromNinePatch.setSize(Engine.getWidth()-50, 500);
		backgroundFromNinePatch.setX(25);
		this.addActor(backgroundFromNinePatch);
		
		followImage = new Image(atlas.findRegion("color"));
		followImage.setColor(new Color(116f/255f, 88f/255f, 133f/255f, 0.5f));
		
		troopNumberSetter = new TroopNumberSetter();
		
		npc1 = new NPC();
		npc2 = new Watchmen();
		npc3 = new HealBoy();
		npc4 = new BombMan();
		npc1.title = "The most common npc in the world";
		npc1.desc = "Right now, take part in a battle events and aim to get special rare prizes! And the game will feature more and more events as time goes on, including Raid Battles against unique, limited-time-only characters!Steve Jobs: The Man Who Thought Different,Thank you to all ZOOKEEPER BATTLE fans for 4.5 million total Downloads!";
		npc2.title = "The most common npc in the world";
		npc2.desc = "Invite friends to the game and get one Power Bottle per friend! (Max. 10 invites)And players who invite 10 friends will get the rare  deco-item!";
		npc3.title = "The most common npc in the world";
		npc3.desc = "This npc has very fast speed , with thire heal skill . Most people choose this to heal thire troops . Do you want have some?";
		npc4.title = "The most common npc in the world";
		npc4.desc = "This npc has very fast speed , with thire heal skill . Most people choose this to heal thire troops . Do you want have some?";
		
		detailTable = new NpcDetailTable();
		detailTable.fill(npc1);
		
		//right scroll pane
		final Table npcListTable = new Table();
		npcListTable.add(new NpcItemTable(npc1,this));
		npcListTable.row().spaceTop(20);
		npcListTable.add(new NpcItemTable(npc2,this));
		npcListTable.row().spaceTop(20);
		npcListTable.add(new NpcItemTable(npc3,this));
		npcListTable.row().spaceTop(20);
		npcListTable.add(new NpcItemTable(npc4,this));
		npcListTable.row().spaceTop(20);
		
		npcListTable.pack();
		ScrollPane npcsScrollPane = new ScrollPane(npcListTable);
		npcsScrollPane.setSize(450, 430);
		npcsScrollPane.setPosition(440, 35);
		npcsScrollPane.setStyle(new ScrollPaneStyle(null,null,null,new NinePatchDrawable(atlas.createPatch("default-rect-pad")), new NinePatchDrawable(atlas.createPatch("default-slider"))));
		npcsScrollPane.setFillParent(false);
		npcsScrollPane.setScrollingDisabled(true, false);
		npcsScrollPane.setFlickScroll(true);
		npcsScrollPane.setFadeScrollBars(true);
		npcsScrollPane.setOverscroll(true, true);
		npcsScrollPane.setScrollbarsOnTop(false);
		this.addActor(npcsScrollPane);
		
		
		this.addActor(detailTable);
		
		
		Table troopsTable = new Table();
		troopsTable.padTop(5).padBottom(5);
		TroopItem troop1 = new TroopItem(this);
		TroopItem troop2 = new TroopItem(this);
		TroopItem troop3 = new TroopItem(this);
		TroopItem troop4 = new TroopItem(this);
		TroopItem troop5 = new TroopItem(this);
		TroopItem troop6 = new TroopItem(this);
		troopsTable.add(troop1).spaceRight(25).spaceBottom(10);
		troopsTable.add(troop2).spaceRight(25).spaceBottom(10);
		troopsTable.add(troop3).spaceRight(25).spaceBottom(10);
		troopsTable.row();
		troopsTable.add(troop4).spaceRight(25).spaceBottom(10);
		troopsTable.add(troop5).spaceRight(25).spaceBottom(10);
		troopsTable.add(troop6).spaceRight(25).spaceBottom(10);
		troopsTable.pack();
		troopsTable.setPosition(80, 90);
		troopsTable.setBackground(new NinePatchDrawable(atlas.createPatch("detail")));
		troopsTable.setWidth(360);
		
		Button button = new Button(new TextureRegionDrawable(atlas.findRegion("btn-start-1")), new TextureRegionDrawable(atlas.findRegion("btn-start-2")));
		button.addListener(new ClickListener(){
			@Override
			public void clicked(InputEvent event, float x, float y) {
				Engine.setMainScene(new GameScene());
				super.clicked(event, x, y);
			}
		});
		button.setPosition(150, 40);
		this.addActor(troopsTable);
		this.addActor(button);
		this.addActor(followImage);
		
		Table topInfo  = new Table();
		topInfo.setBackground(new NinePatchDrawable(atlas.createPatch("ui-topinfo")));
//		topInfo.setColor(new Color(0,125f/225f,110f/255f,1));
		topInfo.pack();
		topInfo.setSize(Engine.getWidth(), 35);
		topInfo.setPosition(0, Engine.getHeight()-topInfo.getHeight());
		this.addActor(topInfo);
		
		//this.addActor(troopNumberSetter);
	}
	@Override
	public void draw() {
		Gdx.gl.glClearColor(116f/255f, 88f/255f, 123f/255f, 1);
		super.draw();
	}
}
