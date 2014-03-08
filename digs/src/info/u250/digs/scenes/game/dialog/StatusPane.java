package info.u250.digs.scenes.game.dialog;

import info.u250.c2d.engine.Engine;
import info.u250.c2d.graphic.WebColors;
import info.u250.digs.scenes.game.Level;
import info.u250.digs.scenes.game.entity.GoldTowerEntity;
import info.u250.digs.scenes.ui.CountDownTimer;

import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.graphics.g2d.TextureAtlas;
import com.badlogic.gdx.scenes.scene2d.Touchable;
import com.badlogic.gdx.scenes.scene2d.ui.Image;
import com.badlogic.gdx.scenes.scene2d.ui.Label;
import com.badlogic.gdx.scenes.scene2d.ui.Label.LabelStyle;
import com.badlogic.gdx.scenes.scene2d.ui.Table;
import com.badlogic.gdx.utils.Timer;

public class StatusPane extends Table{
	Label lblNpc ;
	Label lblGold;
	Label lblTime;
	Label lblKa;
	Label lblTitle;
	
	Image npc,ka,time,gold,title;
	
	int count = 0;
	CountDownTimer timer = null;
	
	public CountDownTimer getTimer() {
		return timer;
	}

	public StatusPane(){
		final TextureAtlas atlas = Engine.resource("All");
		final BitmapFont font = Engine.resource("Font");
//		this.setBackground(new NinePatchDrawable(atlas.createPatch("label2")));
		npc = new Image(atlas.findRegion("v-npc"));
		ka = new Image(atlas.findRegion("v-ka"));
		time = new Image(atlas.findRegion("v-time"));
		gold = new Image(atlas.findRegion("v-gold"));
		title = new Image(atlas.findRegion("v-title"));
		lblNpc = new Label("000",new LabelStyle(font, WebColors.DARK_GREEN.get()));
		lblKa = new Label("000",new LabelStyle(font, WebColors.DARK_BLUE.get()));
		lblGold = new Label("000",new LabelStyle(font, WebColors.WHITE.get()));
		lblTitle = new Label("Level 1:Walking In The Cloud",new LabelStyle(font, WebColors.WHEAT.get()));
		lblTime = new Label("00:00",new LabelStyle(font, WebColors.BLACK.get()));
		timer = new CountDownTimer(lblTime);
		
		this.clearListeners();
		this.setTouchable(Touchable.disabled);
	}
	
	public void startCounter(Level level){
		Timer.instance().clear();
		if(level.config.time<=0)return;
		timer.setSceonds(level.config.time);
		timer.setPause(false);
		timer.start();
	}
	public void pauseCounter(){
		timer.setPause(true);
	}
	public void resumeCounter(){
		timer.setPause(false);
	}
	public void update(Level level){
		if(level.config.ka>0){
			count = 0;
			for(GoldTowerEntity dock:level.getDocks()){
				count+=dock.getKaNumber();
			}
			this.lblKa.setText(count+"/"+level.config.ka);
		}
		this.lblNpc.setText(level.getNpcs().size+"");
		count = 0;
		for(GoldTowerEntity dock:level.getDocks()){
			count+=dock.getNumber();
		}
		if(level.config.gold>0){
			this.lblGold.setText(level.config.gold+"("+count+")");
		}
	}
	public void show(Level level){
		this.clear();
		
		this.lblTitle.setText(Engine.getLanguagesManager().getString("java.level_prefix")+(level.config.idx+1)+": "+level.config.idxName);
		this.lblKa.setText(level.getKas().size+"");
		this.lblNpc.setText(level.getNpcs().size+"");
		this.lblTime.setText("00:00");
		this.lblGold.setText("0");
		
		
		this.add(title).spaceRight(20);
		this.add(lblTitle).spaceRight(50);
		this.add(npc).spaceRight(10);
		this.add(lblNpc).minWidth(50);
		
		if(level.config.gold>0){
			this.add(gold).spaceRight(10);
			this.add(lblGold).minWidth(60);
		}
		if(level.config.ka>0){
			this.add(ka).spaceRight(10);
			this.add(lblKa).minWidth(60);
		}
		if(level.config.time>0){
			this.add(time).spaceRight(10);
			this.add(lblTime).minWidth(100);
		}
		if(level.config.time<=0){
			this.padRight(50);
		}
		this.pack();
	}
}
