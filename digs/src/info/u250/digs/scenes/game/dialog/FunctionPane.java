package info.u250.digs.scenes.game.dialog;

import info.u250.c2d.engine.Engine;
import info.u250.digs.scenes.GameScene;

import com.badlogic.gdx.graphics.g2d.TextureAtlas;
import com.badlogic.gdx.scenes.scene2d.ui.Button;
import com.badlogic.gdx.scenes.scene2d.ui.ButtonGroup;
import com.badlogic.gdx.scenes.scene2d.ui.Table;
import com.badlogic.gdx.scenes.scene2d.utils.NinePatchDrawable;
import com.badlogic.gdx.scenes.scene2d.utils.TextureRegionDrawable;

public class FunctionPane extends Table{

	public FunctionPane(final GameScene gameScene){
		final TextureAtlas atlas = Engine.resource("All");
		this.setBackground(new NinePatchDrawable(atlas.createPatch("topbar")));
			
		

		final Button btn_home = new Button(new TextureRegionDrawable(atlas.findRegion("btn-home-1")),null,new TextureRegionDrawable(atlas.findRegion("btn-home-2")));
		final Button btn_bomb = new Button(new TextureRegionDrawable(atlas.findRegion("btn-bomb-1")),null,new TextureRegionDrawable(atlas.findRegion("btn-bomb-2")));
		final Button btn_fill = new Button(new TextureRegionDrawable(atlas.findRegion("btn-fill-1")),null,new TextureRegionDrawable(atlas.findRegion("btn-fill-2")));
		final Button btn_dig = new Button(new TextureRegionDrawable(atlas.findRegion("btn-dig-1")),null,new TextureRegionDrawable(atlas.findRegion("btn-dig-2")));
		final Button btn_npc = new Button(new TextureRegionDrawable(atlas.findRegion("btn-npc-1")),null,new TextureRegionDrawable(atlas.findRegion("btn-npc-2")));
		
		final Button btn_help = new Button(new TextureRegionDrawable(atlas.findRegion("btn-help-1")),new TextureRegionDrawable(atlas.findRegion("btn-help-2")));
		
		new ButtonGroup(btn_home,btn_bomb,btn_dig,btn_fill,btn_npc);//collection them
		this.add(btn_help).spaceRight(15);
		this.add(btn_home).spaceRight(15);
		this.add(btn_bomb).spaceRight(15);
		this.add(btn_npc).spaceRight(15);
		this.add(btn_fill).spaceRight(15);
		this.add(btn_dig).spaceRight(15);
		btn_dig.setChecked(true);

		this.pack();
	}
}
