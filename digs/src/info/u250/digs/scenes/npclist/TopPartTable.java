package info.u250.digs.scenes.npclist;

import info.u250.c2d.engine.Engine;

import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.graphics.g2d.TextureAtlas;
import com.badlogic.gdx.scenes.scene2d.ui.Image;
import com.badlogic.gdx.scenes.scene2d.ui.Label;
import com.badlogic.gdx.scenes.scene2d.ui.Label.LabelStyle;
import com.badlogic.gdx.scenes.scene2d.ui.Table;
import com.badlogic.gdx.scenes.scene2d.utils.NinePatchDrawable;

public class TopPartTable extends Table {

	Label lblGold ;
	Label lblWhiteGold;
	Label lblJewel;
	public TopPartTable(){
		BitmapFont font = Engine.resource("Font");
		TextureAtlas atlas = Engine.resource("All");
		
		this.setBackground(new NinePatchDrawable(atlas.createPatch("ui-topinfo")));
		
		Image flagGold = new Image(atlas.findRegion("flag-gold"));
		Image flagWhiteGold = new Image(atlas.findRegion("flag-white-gold"));
		Image flagJewel = new Image(atlas.findRegion("flag-jewel"));
		
		lblGold = new Label("123456789",new LabelStyle(font,new Color(115f/255f,89f/255f,123f/255f,1)));
		lblWhiteGold = new Label("1234567890",new LabelStyle(font,new Color(115f/255f,89f/255f,123f/255f,1)));
		lblJewel = new Label("123456",new LabelStyle(font,new Color(24f/255f,113f/255f,0,1)));
		
		this.add(flagGold).padLeft(50);
		this.add(lblGold).width(150).spaceLeft(10);
		this.add(flagWhiteGold);
		this.add(lblWhiteGold).width(150).spaceLeft(10);
		this.add(flagJewel);
		this.add(lblJewel).width(150).spaceLeft(10);
		
//		topInfo.setColor(new Color(0,125f/225f,110f/255f,1));
		this.pack();
		this.setSize(Engine.getWidth(), 40);
		this.setPosition(0, Engine.getHeight()-this.getHeight());
	}
}
