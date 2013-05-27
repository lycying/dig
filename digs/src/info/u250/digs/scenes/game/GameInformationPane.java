package info.u250.digs.scenes.game;

import info.u250.c2d.engine.Engine;
import info.u250.digs.scenes.ui.CountDownTimer;

import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.graphics.g2d.TextureAtlas;
import com.badlogic.gdx.scenes.scene2d.actions.Actions;
import com.badlogic.gdx.scenes.scene2d.ui.Image;
import com.badlogic.gdx.scenes.scene2d.ui.Label;
import com.badlogic.gdx.scenes.scene2d.ui.Table;
import com.badlogic.gdx.scenes.scene2d.ui.Label.LabelStyle;
import com.badlogic.gdx.scenes.scene2d.utils.NinePatchDrawable;

public class GameInformationPane extends Table{
	final CountDownTimer  countDownTimer;
	final Label lblGold;
	public GameInformationPane(){
		TextureAtlas atlas = Engine.resource("All");
		BitmapFont font = Engine.resource("Font");
		lblGold = new Label("0", new LabelStyle(font, Color.RED));
		
		this.setBackground(new NinePatchDrawable(atlas.createPatch("topbar")));
		this.add(new Image(atlas.findRegion("flag-gold-many")));
		this.add(new Label("Gold:", new LabelStyle(font, Color.BLACK)));
		this.add(lblGold).width(100).spaceRight(5);
		final Image clock = new Image(atlas.findRegion("flag-clock"));
		clock.setOrigin(clock.getWidth()/2, clock.getHeight()/2);
		clock.addAction(Actions.forever(
				Actions.sequence(
						Actions.delay(2),
						Actions.rotateBy(15,0.2f),
						Actions.rotateBy(-30,0.2f),
						Actions.rotateBy(15,0.1f)
						)
				));
		this.add(clock);
		Label countDownLabel = new Label("00:00", new LabelStyle(font, Color.BLACK));
		countDownTimer = new CountDownTimer(countDownLabel);
		countDownTimer.start();
		this.add(countDownLabel).width(100);
		this.padLeft(10).padRight(20).padTop(0).padBottom(0);
		this.pack();
//		this.setWidth(Engine.getWidth());
		this.setY(Engine.getHeight()-this.getHeight() - 5);
	}
}
