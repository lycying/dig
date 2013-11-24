package info.u250.digs.scenes.game.callback;

import info.u250.c2d.engine.Engine;
import info.u250.digs.IO;
import info.u250.digs.scenes.game.Level;
import info.u250.digs.scenes.game.LevelCompleteCallback;
import info.u250.digs.scenes.game.dialog.FailDailog;
import info.u250.digs.scenes.game.dialog.InfoDialogForDefaultLevelCompleteCallback;
import info.u250.digs.scenes.game.dialog.WinDialog;
import info.u250.digs.scenes.game.entity.AbstractMoveable;
import info.u250.digs.scenes.game.entity.GoldTowerEntity;

import com.badlogic.gdx.scenes.scene2d.Actor;
import com.badlogic.gdx.scenes.scene2d.actions.Actions;
import com.badlogic.gdx.utils.Timer;

public class DefaultLevelCompleteCallback extends LevelCompleteCallback {
	/* if we arrived the win status */
	int count = 0;
	boolean win = false;
	boolean fail = false;
	@Override
	public boolean tick(Level level) {
		if(win || fail)return true;
		if(level.config.time>0){
			if(level.getGame().leastTime()<=0){
				fail(level);
				Timer.instance().clear();
				Engine.getMusicManager().stopMusic("MusicTimer");
				return true;
			}else if(level.getGame().leastTime()<=10){
				Engine.getMusicManager().playMusic("MusicTimer", true);
			}
		}
		
		count = level.getNpcs().size;
		if(count <= 0){
			fail(level);
			return true;
		}
		
		count = 0;
		for(GoldTowerEntity dock:level.getDocks()){
			count+=dock.getNumber();
		}
		//if we collect all gold and all kas has docked at the tower (only if we has ka on the screen)
		if(count>=level.config.gold){
			if(level.config.ka<=0){
				win(level);
				return true;
			}else{
				count = 0;
				for(GoldTowerEntity dock:level.getDocks()){
					count+=dock.getKaNumber();
				}
				if(level.config.ka == count){
					win(level);
					return true;
				}
			}
		}
		return false;
	}
	void win(Level level){
		WinDialog winDialog = new WinDialog(level.getGame());
		level.getGame().addActor(winDialog);
		winDialog.show(level);
		level.getGame().getStatusPane().pauseCounter();
		Engine.getMusicManager().stopMusic("MusicBattle");
		Engine.getSoundManager().playSound("SoundWin");
		win = true;
		
		int score = level.config.time - level.getGame().leastTime();		
		IO.win(level.config.pack,level.config.idx, score, false);
	}
	void fail(Level level){		
		Engine.getSoundManager().playSound("SoundFail");
		level.getGame().getStatusPane().pauseCounter();
		FailDailog failDialog = new FailDailog(level.getGame());
		failDialog.show();
		level.getGame().addActor(failDialog);
		fail = true;
		
		count = 0;
		count+=level.getNpcs().size;
		count+=level.getKas().size;
		count+=level.getBosses().size;
		count+=level.getEnemyMyiyas().size;
		if(count>0){
			float delay = 0.8f/count;
			count=0;
			for(final Actor actor:level.getChildren()){
				if(actor instanceof AbstractMoveable){
					actor.addAction(Actions.delay(delay*count++,Actions.run(new Runnable() {
						@Override
						public void run() {
							AbstractMoveable.class.cast(actor).die();
						}
					})));
				}
			}
			
		}
	}
	@Override
	public boolean isWin() {
		return win;
	}
	@Override
	public boolean isFail() {
		return fail;
	}
	@Override
	public void failLevel(Level level) {
		if(!fail){
			fail(level);
		}
	}
	InfoDialogForDefaultLevelCompleteCallback info;
	@Override
	public Actor infoBorad(Level level) {
		if(null == info){
			info = new InfoDialogForDefaultLevelCompleteCallback(level);
		} 
		info.show();
		return info;
	}
	

}
