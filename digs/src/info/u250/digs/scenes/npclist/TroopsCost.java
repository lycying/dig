package info.u250.digs.scenes.npclist;

import info.u250.c2d.engine.Engine;

import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.graphics.g2d.TextureAtlas;
import com.badlogic.gdx.scenes.scene2d.actions.Actions;
import com.badlogic.gdx.scenes.scene2d.ui.Image;
import com.badlogic.gdx.scenes.scene2d.ui.Label;
import com.badlogic.gdx.scenes.scene2d.ui.Label.LabelStyle;
import com.badlogic.gdx.scenes.scene2d.ui.Table;
import com.badlogic.gdx.scenes.scene2d.utils.NinePatchDrawable;

public class TroopsCost extends Table{
	final Label lblNumber;
	int number = 0;
	
	public TroopsCost(){
		TextureAtlas atlas = Engine.resource("All");
		BitmapFont font = Engine.resource("Font");
//		this.setBackground(new NinePatchDrawable(atlas.createPatch("label")));
		
		final Image flagGoldMany = new Image(atlas.findRegion("flag-gold-many"));
		final Label lblDesc = new Label("Cost:   ",new LabelStyle(font,Color.DARK_GRAY));
		lblNumber = new Label("0",new LabelStyle(font,Color.BLACK));
		lblNumber.getStyle().background = new NinePatchDrawable(atlas.createPatch("label"));
		lblDesc.getStyle().background = lblNumber.getStyle().background;
		this.add(flagGoldMany).spaceRight(5);
		this.add(lblDesc);
		this.add(lblNumber).width(90);
		
		this.pack();
		flagGoldMany.setOrigin(flagGoldMany.getWidth()/2, flagGoldMany.getHeight()/3);
		flagGoldMany.addAction(Actions.forever(Actions.sequence(
				Actions.delay(1),
//				Actions.parallel(
//						Actions.moveBy(0, 100, 0.5f,Interpolation.pow2Out),
//						Actions.rotateTo(380, 0.5f,Interpolation.pow2Out),
//						Actions.scaleTo(0.8f,0.8f, 0.5f,Interpolation.pow2Out)
//						),
//				Actions.parallel(
//						Actions.moveBy(0, -100, 0.5f,Interpolation.pow2In),
//						Actions.rotateTo(720, 0.5f,Interpolation.pow2In),
//						Actions.scaleTo(1f,1f, 0.5f,Interpolation.pow2In)
//						),
				Actions.scaleTo(1.2f, 1.2f,0.1f),
				Actions.scaleTo(1f, 1f,0.1f)
//				Actions.run(new Runnable() {
//					@Override
//					public void run() {
//						flagGoldMany.setRotation(0);
//					}
//				})
				)));
	}
	
	public void setTroopCost(int cost){
		this.number = cost;
		this.lblNumber.setText( cost+"" );
	}
	public int getTroopCost(){
		return this.number;
	}
}
