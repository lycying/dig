package info.u250.digs.scenes.level;

import info.u250.c2d.engine.Engine;
import info.u250.c2d.graphic.WebColors;
import info.u250.digs.scenes.LevelScene;
import info.u250.digs.scenes.Mv1Scene;
import info.u250.digs.scenes.game.HookLevelConfig;
import info.u250.digs.scenes.game.LevelConfig;
import info.u250.digs.scenes.level.pack.guide.Tour1;
import info.u250.digs.scenes.level.pack.guide.Tour2;
import info.u250.digs.scenes.level.pack.guide.Tour3;
import info.u250.digs.scenes.level.pack.guide.Tour4;
import info.u250.digs.scenes.level.pack.guide.Tour5;
import info.u250.digs.scenes.level.pack.guide.Tour6;
import info.u250.digs.scenes.level.pack.guide.Tour7;
import info.u250.digs.scenes.level.pack.guide.Tour8;
import info.u250.digs.scenes.level.pack.guide.Tour9;
import info.u250.digs.scenes.level.pack.s1.Pack1V__01;
import info.u250.digs.scenes.level.pack.s1.Pack1V__02;
import info.u250.digs.scenes.level.pack.s1.Pack1V__03;
import info.u250.digs.scenes.level.pack.s1.Pack1V__04;
import info.u250.digs.scenes.level.pack.s1.Pack1V__05;
import info.u250.digs.scenes.level.pack.s1.Pack1V__06;
import info.u250.digs.scenes.level.pack.s1.Pack1V__07;
import info.u250.digs.scenes.level.pack.s1.Pack1V__08;
import info.u250.digs.scenes.level.pack.s1.Pack1V__09;
import info.u250.digs.scenes.level.pack.s1.Pack1V__10;
import info.u250.digs.scenes.level.pack.s1.Pack1V__11;
import info.u250.digs.scenes.level.pack.s1.Pack1V__12;
import info.u250.digs.scenes.level.pack.s1.Pack1V__13;
import info.u250.digs.scenes.level.pack.s1.Pack1V__14;
import info.u250.digs.scenes.level.pack.s1.Pack1V__15;
import info.u250.digs.scenes.level.pack.s1.Pack1V__16;
import info.u250.digs.scenes.level.pack.s1.Pack1V__17;
import info.u250.digs.scenes.level.pack.s1.Pack1V__18;
import info.u250.digs.scenes.level.pack.s1.Pack1V__19;
import info.u250.digs.scenes.level.pack.s2.S2Lvl__01;
import info.u250.digs.scenes.level.pack.s2.S2Lvl__02;
import info.u250.digs.scenes.level.pack.s2.S2Lvl__03;
import info.u250.digs.scenes.level.pack.s2.S2Lvl__04;
import info.u250.digs.scenes.level.pack.s2.S2Lvl__05;
import info.u250.digs.scenes.level.pack.s2.S2Lvl__06;
import info.u250.digs.scenes.level.pack.s2.S2Lvl__07;
import info.u250.digs.scenes.level.pack.s2.S2Lvl__08;
import info.u250.digs.scenes.level.pack.s2.S2Lvl__09;

import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.graphics.g2d.TextureAtlas;
import com.badlogic.gdx.scenes.scene2d.Actor;
import com.badlogic.gdx.scenes.scene2d.InputEvent;
import com.badlogic.gdx.scenes.scene2d.ui.Image;
import com.badlogic.gdx.scenes.scene2d.ui.Label;
import com.badlogic.gdx.scenes.scene2d.ui.Label.LabelStyle;
import com.badlogic.gdx.scenes.scene2d.ui.Table;
import com.badlogic.gdx.scenes.scene2d.ui.TextButton;
import com.badlogic.gdx.scenes.scene2d.ui.TextButton.TextButtonStyle;
import com.badlogic.gdx.scenes.scene2d.utils.ClickListener;
import com.badlogic.gdx.scenes.scene2d.utils.NinePatchDrawable;
import com.esotericsoftware.tablelayout.BaseTableLayout;

public class LevelIdx {
	public static final String[][] Level_String = {
		new String[]{
		"The First Gold",						
		"Walking In The Cloud",					
		"First Blood",							
		"As Time Goes By",						
		"Embrace The Death",					
		"Got No Place To Go",										
		"Meet A Friend",						
		"Enemy Appear",		
		"Deeper And Deeper",
		},
		new String[]{
		"Volcano Foot",
		"Coast of the Raging Sea",
		"Forest Border",
		"Temple Outskirts",
		"Nether Genesis",
		"Giant Valley",
		"Volcanic Ridge",
		"Sea of Rage Mirage",
		"Nature's Forest",
		"Heroic Temple",
		"Dusk City",
		"Door of Darkness",
		"City of Titans",
		"Molten Lands",
		"Abyssal Maze",
		"Nether Jungle",
		"Pantheon of the Gods",
		"Door of Darkness",
		"Ok OK OK"
		},
		new String[]{
		"Dusk City",
		"City of Titans",
		"Molten Lands",
		"Abyssal Maze",
		"Nether Jungle",
		"Pantheon of the Gods",
		"Door of Darkness",
		"Hell Chasm",
		"Nothing Here",
		},
	};
	public static String getLevelName(int pack,int level){
		return Level_String[pack][level];
	}
	public static LevelConfig getLevelConfig(int pack,int level){
		LevelConfig config = null;
		switch(pack){
			case 0:
				config = getGuideLevelConfig(level);
				break;
			case 1:
				config = getS1LevelConfig(level);
				break;
			case 2:
				config = getS2LevelConfig(level);
				break;
		}
		if(null == config){
			HookLevelConfig configx = new HookLevelConfig();
			configx.bottomColor = WebColors.BLACK.get();
			configx.topColor = WebColors.CADET_BLUE.get();
			configx.lineHeight = 380;
			config = configx;
		}
		config.idx = level;
		config.pack = pack;
		config.idxName = Level_String[pack][level];
		
		return config;
	}
	private static LevelConfig getGuideLevelConfig(int level){
		LevelConfig config = null;
		switch(level){
		case 0:
			config = new Tour1();
			break;
		case 1:
			config = new Tour2();
			break;
		case 2:
			config = new Tour3();
			break;
		case 3:
			config = new Tour4();
			break;
		case 4:
			config = new Tour5();
			break;
		case 5:
			config = new Tour6();
			break;
		case 6:
			config = new Tour7();
			break;
		case 7:
			config = new Tour8();
			break;
		case 8:
			config = new Tour9();
			break;
		}
		return config;
	}
	private static LevelConfig getS1LevelConfig(int level){
		LevelConfig config = null;
		switch(level){
		case 0:
			config = new Pack1V__01();
			break;
		case 1:
			config = new Pack1V__02();
			break;
		case 2:
			config = new Pack1V__03();
			break;
		case 3:
			config = new Pack1V__04();
			break;
		case 4:
			config = new Pack1V__05();
			break;
		case 5:
			config = new Pack1V__06();
			break;
		case 6:
			config = new Pack1V__07();
			break;
		case 7:
			config = new Pack1V__08();
			break;
		case 8:
			config = new Pack1V__09();
			break;
		case 9:
			config = new Pack1V__10();
			break;
		case 10:
			config = new Pack1V__11();
			break;
		case 11:
			config = new Pack1V__12();
			break;
		case 12:
			config = new Pack1V__13();
			break;
		case 13:
			config = new Pack1V__14();
			break;
		case 14:
			config = new Pack1V__15();
			break;
		case 15:
			config = new Pack1V__16();
			break;
		case 16:
			config = new Pack1V__17();
			break;
		case 17:
			config = new Pack1V__18();
			break;
		case 18:
			config = new Pack1V__19();
			break;
		}
		return config;
	}
	private static LevelConfig getS2LevelConfig(int level){
		LevelConfig config = null;
		switch(level){
		case 0:
			config = new S2Lvl__01();
			break;
		case 1:
			config = new S2Lvl__02();
			break;
		case 2:
			config = new S2Lvl__03();
			break;
		case 3:
			config = new S2Lvl__04();
			break;
		case 4:
			config = new S2Lvl__05();
			break;
		case 5:
			config = new S2Lvl__06();
			break;
		case 6:
			config = new S2Lvl__07();
			break;
		case 7:
			config = new S2Lvl__08();
			break;
		case 8:
			config = new S2Lvl__09();
			break;
		}
		return config;
	}
	
	public static RefreshableLevelTable getGuideTable(final LevelScene lvlSce){
		RefreshableLevelTable table = new RefreshableLevelTable(0);
		BitmapFont font = Engine.resource("BigFont");
		TextureAtlas atlas = Engine.resource("All");
		{
			Table tb = new Table();
			tb.setBackground(new NinePatchDrawable( atlas.createPatch("level-item-bg-3")));
			tb.add(new Image(atlas.findRegion("lvl-mv-1")));
			tb.add(new Label("I wakeup at somewhere...",new LabelStyle(font,Color.WHITE))).minWidth(500);
			TextButtonStyle style = new TextButtonStyle(
					new NinePatchDrawable(atlas.createPatch("level-item-bg-2")), 
					new NinePatchDrawable(atlas.createPatch("level-item-bg-2")), null, font);
			style.fontColor = Color.BLACK;
			style.downFontColor = Color.RED;
			final TextButton view = new TextButton("View",style);
			view.pack();
			view.padTop(20);
			view.padBottom(20);
			tb.add(view);
			tb.pack();
			table.add(tb).align(BaseTableLayout.LEFT).padTop(40).row();
			view.addListener(new ClickListener(){
				@Override
				public void clicked(InputEvent event, float x, float y) {
					Engine.setMainScene(new Mv1Scene(lvlSce));
					super.clicked(event, x, y);
				}
			});
		}
		{
			for(int i=0;i<Level_String[0].length;i++){
				LevelItem item = new LevelItem(lvlSce,0,i,Level_String[0][i]);
				table.add(item).spaceBottom(10);
				table.row();
			}
		}
		{
			Table tb = new Table();
			tb.setBackground(new NinePatchDrawable( atlas.createPatch("level-item-bg-3")));
			tb.add(new Image(atlas.findRegion("guide-icon")));
			tb.add(new Label("The basic guide",new LabelStyle(font,Color.WHITE))).minWidth(580);
			tb.pack();
			table.add(tb).align(BaseTableLayout.LEFT).row();
		}
		table.add(new Image(atlas.findRegion("finger")));
		table.pack();
		return table;
	}
	public static RefreshableLevelTable getPack1Table(final LevelScene lvlSce){
		RefreshableLevelTable table = new RefreshableLevelTable(1);
		
		{
			table.add().align(BaseTableLayout.LEFT).padTop(60).row();
		}
		{
			for(int i=0;i<Level_String[1].length;i++){
				LevelItem item = new LevelItem(lvlSce,1,i,Level_String[1][i]);
				table.add(item).spaceBottom(10);
				table.row();
			}
		}
		table.pack();
		return table;
	}
	public static RefreshableLevelTable getPack2Table(final LevelScene lvlSce){
		RefreshableLevelTable table = new RefreshableLevelTable(2);
		table.add(new LevelItemTextTable("Choose Level")).row();
		{
			for(int i=0;i<Level_String[2].length;i++){
				LevelItem item = new LevelItem(lvlSce,2,i,Level_String[2][i]);
				table.add(item).spaceBottom(10);
				table.row();
			}
		}
		table.pack();
		return table;
	}
	public final static class RefreshableLevelTable extends Table{
		int pack = 0;
		public RefreshableLevelTable(int pack){
			this.pack = pack;
		}
		@Override
		public void pack() {
			super.pack();
			this.refresh();
		}
		public void refresh(){
			for(Actor actor:this.getChildren()){
				if(actor instanceof LevelItem){
					LevelItem.class.cast(actor).refresh();
				}
			}
		}
	}
}
