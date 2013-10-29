package info.u250.digs.scenes.level;

import info.u250.c2d.engine.Engine;
import info.u250.c2d.graphic.WebColors;
import info.u250.digs.scenes.LevelScene;
import info.u250.digs.scenes.game.LevelConfig;
import info.u250.digs.scenes.level.pack.guide.Level1_tour1;
import info.u250.digs.scenes.level.pack.guide.Level2_tour2;
import info.u250.digs.scenes.level.pack.guide.Level3_tour3;
import info.u250.digs.scenes.level.pack.guide.Level4_tour4;
import info.u250.digs.scenes.level.pack.guide.Level5_tour5;
import info.u250.digs.scenes.level.pack.guide.Level6_tour6;

import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.scenes.scene2d.Actor;
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
			config = new Level1_tour1();
			break;
		case 1:
			config = new Level2_tour2();
			break;
		case 2:
			config = new Level3_tour3();
			break;
		case 3:
			config = new Level4_tour4();
			break;
		case 4:
			config = new Level5_tour5();
			break;
		case 5:
			config = new Level6_tour6();
			break;
		}
		return config;
	}
	
	public static RefreshableLevelTable getGuideTable(final LevelScene lvlSce){
		final BitmapFont font = Engine.resource("MenuFont",BitmapFont.class);
		RefreshableLevelTable table = new RefreshableLevelTable(0);
		table.add(new LevelItemTextTable("Choose Level")).row();
		{
			for(int i=0;i<Level_String[0].length;i++){
				LevelItem item = new LevelItem(lvlSce,0,i,Level_String[0][i]);
				table.add(item).spaceBottom(10);
				table.row();
			}
		}
		table.add(new Label("I feel very sorrow ,\n but i do not why ..",new LabelStyle(font,Color.BLUE))).row();
		table.add(new Label("I do nothing but dig , \n i feel it must be sth worth to do ..",new LabelStyle(font,Color.YELLOW))).row();
		table.add(new Label("I love crying in the rain. \n because when i do, \n no one can hear the pain.",new LabelStyle(font,Color.WHITE))).row();
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
