package info.u250.digs.scenes.level;

import info.u250.c2d.engine.Engine;
import info.u250.c2d.graphic.WebColors;
import info.u250.digs.scenes.LevelScene;
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
import info.u250.digs.scenes.level.pack.s1.S1Lvl1;
import info.u250.digs.scenes.level.pack.s1.S1Lvl2;
import info.u250.digs.scenes.level.pack.s1.S1Lvl3;

import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.graphics.g2d.TextureAtlas;
import com.badlogic.gdx.scenes.scene2d.Actor;
import com.badlogic.gdx.scenes.scene2d.ui.Image;
import com.badlogic.gdx.scenes.scene2d.ui.Label;
import com.badlogic.gdx.scenes.scene2d.ui.Label.LabelStyle;
import com.badlogic.gdx.scenes.scene2d.ui.Table;

public class LevelIdx {
	public static final String[][] Level_String = {
		new String[]{
		"The First Gold",						//第一块金子
		"Walking In The Cloud",					//云端漫步
		"First Blood",							//第一滴血
		"As Time Goes By",						//时光流逝
		"Embrace The Death",					//与死相拥
		"Got No Place To Go",					//何去何从
		"Deeper And Deeper",					//一直向下
		"Meet A Friend",						//与朋友同行
		"Enemy Appear",							//敌人出现
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
		"Gonryun Shrine",
		"Ruins of Grandeur",
		"Wild Sacred Peak",
		"Divine Tomb",
		"Scorched Lands",
		"Aquatic Domain",
		"Verdant Lush",
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
		}
		if(null == config){
			config = new LevelConfig();
			config.bottomColor = WebColors.BLACK.get();
			config.topColor = WebColors.CADET_BLUE.get();
			config.lineHeight = 380;
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
			config = new S1Lvl1();
			break;
		case 1:
			config = new S1Lvl2();
			break;
		case 2:
			config = new S1Lvl3();
			break;
		}
		return config;
	}
	
	public static RefreshableLevelTable getGuideTable(final LevelScene lvlSce){
		RefreshableLevelTable table = new RefreshableLevelTable(0);
		table.add(new LevelItemTextTable("Choose Level")).row();
		TextureAtlas atlas = Engine.resource("All");
		{
			for(int i=0;i<Level_String[0].length;i++){
				LevelItem item = new LevelItem(lvlSce,0,i,Level_String[0][i]);
				table.add(item).spaceBottom(10);
				table.row();
			}
		}
		
//		table.add();
//		table.add();
//		table.add();
//		table.row().spaceBottom(15);
//		table.add(new GuideItem(lvlSce,0,0,Level_String[0][0])).spaceRight(10);
//		table.add(new GuideItem(lvlSce,0,1,Level_String[0][1])).spaceRight(10);
//		table.add(new GuideItem(lvlSce,0,2,Level_String[0][2]));
//		table.row().spaceBottom(15);
//		table.add(new GuideItem(lvlSce,0,3,Level_String[0][3])).spaceRight(10);
//		table.add(new GuideItem(lvlSce,0,4,Level_String[0][4])).spaceRight(10);
//		table.add(new GuideItem(lvlSce,0,5,Level_String[0][5])).spaceRight(10);
//		table.row().spaceBottom(15);
//		table.add(new GuideItem(lvlSce,0,6,Level_String[0][6])).spaceRight(10);
//		table.add(new GuideItem(lvlSce,0,7,Level_String[0][7])).spaceRight(10);
//		table.add(new GuideItem(lvlSce,0,8,Level_String[0][8]));
//		table.row().spaceBottom(100);
		
		BitmapFont font = Engine.resource("BigFont");
		table.add(new Label("Now come to the new world",new LabelStyle(font,Color.WHITE))).row();
		table.add(new Image(atlas.findRegion("finger")));

		return table;
	}
	public static RefreshableLevelTable getPack1Table(final LevelScene lvlSce){
		RefreshableLevelTable table = new RefreshableLevelTable(1);
		table.add(new LevelItemTextTable("Choose Level")).row();
		{
			for(int i=0;i<Level_String[1].length;i++){
				LevelItem item = new LevelItem(lvlSce,1,i,Level_String[1][i]);
				table.add(item).spaceBottom(10);
				table.row();
			}
		}
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
		return table;
	}
	public final static class RefreshableLevelTable extends Table{
		int pack = 0;
		public RefreshableLevelTable(int pack){
			this.pack = pack;
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
