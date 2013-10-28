package info.u250.digs.scenes.level;

import info.u250.c2d.engine.Engine;
import info.u250.digs.scenes.LevelScene;
import info.u250.digs.scenes.level.LevelIdx.RefreshableLevelTable;

import com.badlogic.gdx.graphics.g2d.TextureAtlas;
import com.badlogic.gdx.scenes.scene2d.Group;
import com.badlogic.gdx.scenes.scene2d.InputEvent;
import com.badlogic.gdx.scenes.scene2d.ui.Image;
import com.badlogic.gdx.scenes.scene2d.utils.ClickListener;

public class LevelPack extends Group{
	private int idx = -1;
	public LevelPack(final LevelScene levelScene, int idx){
		TextureAtlas atlas = Engine.resource("All");
		guide_1 = new Image(atlas.findRegion("ls-guide-1"));
		guide_2 = new Image(atlas.findRegion("ls-guide-2"));
		pack1_1 = new Image(atlas.findRegion("ls-pack1-1"));
		pack1_2 = new Image(atlas.findRegion("ls-pack1-2"));
		pack2_1 = new Image(atlas.findRegion("ls-pack2-1"));
		pack2_2 = new Image(atlas.findRegion("ls-pack2-2"));
		this.setSize(122+122+203, 100);
		
		final RefreshableLevelTable guideTabel = LevelIdx.getGuideTable(levelScene); 
		final RefreshableLevelTable pack1Table = LevelIdx.getPack1Table(levelScene); 
		final RefreshableLevelTable pack2Table = LevelIdx.getPack2Table(levelScene); 
		
		if(idx == 0){
			levelScene.getLevelPanel().setWidget(guideTabel);
		}else if(idx == 1){
			levelScene.getLevelPanel().setWidget(pack1Table);
		}else if(idx == 2){
			levelScene.getLevelPanel().setWidget(pack2Table);
		}
		
		final ClickListener guideL = new ClickListener(){
			public void clicked(InputEvent event, float x, float y) {
				if(remake(0)){
					levelScene.getLevelPanel().setWidget(guideTabel);
					levelScene.getLevelPanel().setScrollY(0);
				}
				Engine.getSoundManager().playSound("SoundClick");
				super.clicked(event, x, y);
			}};
		final ClickListener pack1L = new ClickListener(){
			public void clicked(InputEvent event, float x, float y) {
				if(remake(1)){
					levelScene.getLevelPanel().setWidget(pack1Table);
					levelScene.getLevelPanel().setScrollY(0);
				}
				Engine.getSoundManager().playSound("SoundClick");
				super.clicked(event, x, y);
			}};
		final ClickListener pack2L = new ClickListener(){
			public void clicked(InputEvent event, float x, float y) {
				if(remake(2)){
					levelScene.getLevelPanel().setWidget(pack2Table);
					levelScene.getLevelPanel().setScrollY(0);
				}
				Engine.getSoundManager().playSound("SoundClick");
				super.clicked(event, x, y);
			}};
			guide_1.addListener(guideL);guide_2.addListener(guideL);
			pack1_1.addListener(pack1L);pack1_2.addListener(pack1L);
			pack2_1.addListener(pack2L);pack2_2.addListener(pack2L);
		this.remake(0);
	}
	Image guide_1,guide_2,pack1_1,pack1_2,pack2_1,pack2_2;
	int width = 0;
	boolean remake(int idx){
		if(idx == this.idx)return false;
		this.idx = idx;
		width = 0;
		this.clear();
		if(idx==0){
			this.addActor(guide_1);
			width+=guide_1.getWidth();
		}else {
			this.addActor(guide_2);
			width+=guide_2.getWidth();
		}
		if(idx==1){
			this.addActor(pack1_1);
			pack1_1.setX(width);
			width+=pack1_1.getWidth();
		}else{
			this.addActor(pack1_2);
			pack1_2.setX(width);
			width+=pack1_2.getWidth();
		}
		if(idx==2){
			pack2_1.setX(width);
			this.addActor(pack2_1);
		}else {
			pack2_2.setX(width);
			this.addActor(pack2_2);
		}
		return true;
	}
}
