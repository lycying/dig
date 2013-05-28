package info.u250.digs.scenes;


import info.u250.c2d.engine.Engine;
import info.u250.c2d.engine.SceneStage;
import info.u250.digs.DigsEngineDrive;
import info.u250.digs.scenes.game.entity.BombMan;
import info.u250.digs.scenes.game.entity.GreenHat;
import info.u250.digs.scenes.game.entity.HealBoy;
import info.u250.digs.scenes.game.entity.Watchmen;
import info.u250.digs.scenes.npclist.NpcDetailTable;
import info.u250.digs.scenes.npclist.NpcItemTable;
import info.u250.digs.scenes.npclist.NpcWrapper;
import info.u250.digs.scenes.npclist.ShopWheel;
import info.u250.digs.scenes.npclist.TopPartTable;
import info.u250.digs.scenes.npclist.TroopItem;
import info.u250.digs.scenes.npclist.TroopSetter;
import info.u250.digs.scenes.npclist.TroopsCost;

import com.badlogic.gdx.Application.ApplicationType;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input.Keys;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.graphics.g2d.TextureAtlas;
import com.badlogic.gdx.scenes.scene2d.Group;
import com.badlogic.gdx.scenes.scene2d.InputEvent;
import com.badlogic.gdx.scenes.scene2d.actions.Actions;
import com.badlogic.gdx.scenes.scene2d.ui.Button;
import com.badlogic.gdx.scenes.scene2d.ui.Image;
import com.badlogic.gdx.scenes.scene2d.ui.Label;
import com.badlogic.gdx.scenes.scene2d.ui.Label.LabelStyle;
import com.badlogic.gdx.scenes.scene2d.ui.ScrollPane;
import com.badlogic.gdx.scenes.scene2d.ui.ScrollPane.ScrollPaneStyle;
import com.badlogic.gdx.scenes.scene2d.ui.Table;
import com.badlogic.gdx.scenes.scene2d.utils.ClickListener;
import com.badlogic.gdx.scenes.scene2d.utils.NinePatchDrawable;
import com.badlogic.gdx.scenes.scene2d.utils.TextureRegionDrawable;

public class NpcListScene extends SceneStage {
	DigsEngineDrive drive;
	final TextureAtlas atlas;
	public final TroopSetter troopSetter;
	public final Group troopSetterContainer;
	public final NpcDetailTable detailTable;
	
	final NpcWrapper npc1;
	final NpcWrapper npc2;
	final NpcWrapper npc3;
	final NpcWrapper npc4;
	
	final TroopItem troop1;
	final TroopItem troop2;
	final TroopItem troop3;
	final TroopItem troop4;
	final TroopItem troop5;//Nil
	final TroopItem troop6;//Nil
	
	final TroopsCost troopsCost ;
	
	public final Image followImage ;
	public NpcListScene(DigsEngineDrive drive){
		this.drive = drive;
		
		atlas = Engine.resource("All");
		BitmapFont font = Engine.resource("Font");
		
		Table backgroundFromNinePatch = new Table();
		backgroundFromNinePatch.setBackground(new NinePatchDrawable(atlas.createPatch("ui-board")));
		backgroundFromNinePatch.pack();
		backgroundFromNinePatch.setSize(Engine.getWidth()-50, 500);
		backgroundFromNinePatch.setX(25);
		this.addActor(backgroundFromNinePatch);
		
		followImage = new Image(atlas.findRegion("color"));
		followImage.setColor(new Color(116f/255f, 88f/255f, 133f/255f, 0.5f));
				
		npc1 = new NpcWrapper(new GreenHat());
		npc2 = new NpcWrapper(new Watchmen());
		npc3 = new NpcWrapper(new HealBoy());
		npc4 = new NpcWrapper(new BombMan());
		npc1.title = "The most common npc in the world";
		npc1.desc = "Right now, take part in a battle events and aim to get special rare prizes! And the game will feature more and more events as time goes on, including Raid Battles against unique, limited-time-only characters!Steve Jobs: The Man Who Thought Different,Thank you to all ZOOKEEPER BATTLE fans for 4.5 million total Downloads!";
		npc1.troopIcon = "npc1-walk-right-1";
		npc1.lock = false;
		
		npc2.title = "The most common npc in the world";
		npc2.desc = "Invite friends to the game and get one Power Bottle per friend! (Max. 10 invites)And players who invite 10 friends will get the rare  deco-item!";
		npc2.troopIcon = "npc2-walk-right-1";
		npc2.lock = false;
		
		npc3.title = "The most common npc in the world";
		npc3.desc = "This npc has very fast speed , with thire heal skill . Most people choose this to heal thire troops . Do you want have some?";
		npc3.troopIcon = "npc3-walk-right-1";
		npc3.lock = false;
		
		npc4.title = "The most common npc in the world";
		npc4.desc = "This npc has very fast speed , with thire heal skill . Most people choose this to heal thire troops . Do you want have some?";
		npc4.troopIcon = "bombman";
		npc4.lock = false;
		
		troop1 = new TroopItem(npc1,this);
		troop2 = new TroopItem(npc2,this);
		troop3 = new TroopItem(npc3,this);
		troop4 = new TroopItem(npc4,this);
		troop5 = new TroopItem(null,this);
		troop6 = new TroopItem(null,this);
		
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
		final ScrollPane npcsScrollPane = new ScrollPane(npcListTable);
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
		troopsTable.add(troop1).spaceRight(25).spaceBottom(10);
		troopsTable.add(troop2).spaceRight(25).spaceBottom(10);
		troopsTable.add(troop3).spaceRight(25).spaceBottom(10);
		troopsTable.row();
		troopsTable.add(troop4).spaceRight(25).spaceBottom(10);
		troopsTable.add(troop5).spaceRight(25).spaceBottom(10);
		troopsTable.add(troop6).spaceRight(25).spaceBottom(10);
		troopsTable.pack();
		troopsTable.setPosition(80, 80);
		troopsTable.setBackground(new NinePatchDrawable(atlas.createPatch("detail")));
		troopsTable.setWidth(360);
		
		Button btnStart = new Button(new TextureRegionDrawable(atlas.findRegion("btn-start-1")), new TextureRegionDrawable(atlas.findRegion("btn-start-2")));
		btnStart.addListener(new ClickListener(){
			@Override
			public void clicked(InputEvent event, float x, float y) {
				NpcListScene.this.drive.getGameScene().configGame(null);
				Engine.setMainScene(NpcListScene.this.drive.getGameScene());
				super.clicked(event, x, y);
			}
		});
		btnStart.setPosition(295, 30);
		
		
		
		troopsCost = new TroopsCost();
		troopsCost.setPosition(85, 34);
		Label lblTroopsTableInformation = new Label("Select your troops for dig:",new LabelStyle(font,new Color(81f/255f,5f/255f,17f/255f,1)));
		lblTroopsTableInformation.setPosition(90, 272);
		this.addActor(lblTroopsTableInformation);
		this.addActor(troopsTable);
		this.addActor(troopsCost);
		this.addActor(btnStart);
		
		
		TopPartTable topInfo  = new TopPartTable();
		
		this.addActor(topInfo);
		troopSetterContainer = new Group();
		troopSetter = new TroopSetter();
		
		this.addActor(troopSetterContainer);
		
		ShopWheel shopWheel = new ShopWheel(){
			@Override
			public void act(float delta) {
				this.wheel.setRotation(npcsScrollPane.getScrollPercentY()*360);
				super.act(delta);
			}
		};
		ShopWheel shopWheel2 = new ShopWheel(){
			@Override
			public void act(float delta) {
				this.wheel.setRotation(npcsScrollPane.getScrollPercentY()*-720);
				super.act(delta);
			}
		};
		shopWheel2.setPosition(860, -10);
		shopWheel2.setScale(0.7f);
		this.addActor(shopWheel);
		this.addActor(shopWheel2);
		
		Image shopImage = new Image(atlas.findRegion("shop"));
		shopImage.setOrigin(shopImage.getWidth()/2, shopImage.getHeight()/2);
		shopImage.setPosition(860,500);
		shopImage.addAction(Actions.forever(Actions.sequence(Actions.scaleTo(0.8f,0.8f,0.1f),Actions.scaleTo(1f,1f,0.1f))));
		this.addActor(shopImage);
		this.addActor(followImage);
	}
	public void countTroopsCost(){
		int cost = 0;
		cost += troop1.getTroopCost();
		cost += troop2.getTroopCost();
		cost += troop3.getTroopCost();
		cost += troop4.getTroopCost();
		cost += troop5.getTroopCost();
		cost += troop6.getTroopCost();
		troopsCost.setTroopCost(cost);
	}
	@Override
	public void draw() {
		Gdx.gl.glClearColor(116f/255f, 88f/255f, 123f/255f, 1);
		super.draw();
	}
	@Override
	public void show() {
		super.show();
		Engine.getMusicManager().playMusic("MusicBackground", true);
	}
	@Override
	public void hide() {
		Engine.getMusicManager().stopMusic("MusicBackground");
		super.hide();
	}
	
	@Override
	public boolean keyDown(int keycode) {
		if (Gdx.app.getType() == ApplicationType.Android) {
			if (keycode == Keys.BACK) {
				Engine.setMainScene(drive.getStartUpScene());
			}
		} else {
			if (keycode == Keys.DEL) {
				Engine.setMainScene(drive.getStartUpScene());
			}
		}
		return super.keyDown(keycode);
	}
}
