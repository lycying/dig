package info.u250.digs.scenes.ui;

import info.u250.c2d.engine.Engine;

import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.graphics.g2d.TextureAtlas;
import com.badlogic.gdx.scenes.scene2d.ui.Label;
import com.badlogic.gdx.scenes.scene2d.ui.Label.LabelStyle;
import com.badlogic.gdx.scenes.scene2d.ui.Table;
import com.badlogic.gdx.scenes.scene2d.utils.NinePatchDrawable;

public class HintOnScreen extends Table{
	public HintOnScreen(String text,Color color){
		this(text,"hint1",color,200);
	}
	private Label label ;
	public HintOnScreen(String text,String background,Color color,int minWidth){
		label = new Label(text, new LabelStyle(Engine.resource("Font",BitmapFont.class), color));
		label.setWrap(true);
		this.add(label).minWidth(minWidth).row();
		this.setBackground(new NinePatchDrawable(Engine.resource("All",TextureAtlas.class).createPatch(background)));
		this.pack();
	}
}
