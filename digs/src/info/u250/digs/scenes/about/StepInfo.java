package info.u250.digs.scenes.about;

import static com.badlogic.gdx.scenes.scene2d.actions.Actions.moveBy;
import info.u250.c2d.engine.Engine;

import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.math.Interpolation;
import com.badlogic.gdx.scenes.scene2d.Group;
import com.badlogic.gdx.scenes.scene2d.actions.Actions;
import com.badlogic.gdx.scenes.scene2d.ui.Label;
import com.badlogic.gdx.scenes.scene2d.ui.Label.LabelStyle;

public class StepInfo extends Group {
	public StepInfo() {
		
	}
	public void play(int  type,String text) {
		this.setPosition(100,400);
		this.setScale(1);
		this.clear();
		switch(type){
		case 0:{
			String[] strs = text.split("");
			float width = 0;
			for(int i=0;i<strs.length;i++){
				String s = strs[i];
				Label temp = new Label(s, new LabelStyle(Engine.resource("BigFont", BitmapFont.class), Color.WHITE));
				temp.setPosition(width, 300);
				temp.addAction(Actions.delay(0.1f*i,Actions.sequence(Actions.moveBy(0, -300,0.5f,Interpolation.swingOut),Actions.delay(3f,Actions.moveBy(0, 300,0.3f)))));
				this.addActor(temp);
				width+=temp.getPrefWidth();
			}
			this.setPosition(100, 420);
			this.addAction(Actions.sequence(Actions.delay(10f,Actions.run(new Runnable() {
				@Override
				public void run() {
					StepInfo.this.remove();
				}
			}))));
			}break;
		case 1:{
			Label battleLabel = new Label(text, new LabelStyle(Engine.resource("BigFont", BitmapFont.class), Color.WHITE));
			this.setSize(battleLabel.getPrefWidth(), battleLabel.getPrefHeight());
			this.setPosition(100,420);
			this.setOrigin(this.getWidth() / 2, this.getHeight()/2);
			this.setScale(0);
			this.addAction(Actions.sequence(Actions.scaleTo(1, 1,1f,Interpolation.swingOut),Actions.forever(
					Actions.sequence(moveBy(0, 60, 0.5f, Interpolation.pow2Out),moveBy(0, -60, 0.5f, Interpolation.pow2In))
			)));
			this.addActor(battleLabel);
			}break;
		}
	}
}
