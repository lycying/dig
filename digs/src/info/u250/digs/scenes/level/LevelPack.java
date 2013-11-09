package info.u250.digs.scenes.level;

import info.u250.c2d.engine.Engine;
import info.u250.digs.IO;
import info.u250.digs.scenes.LevelScene;
import info.u250.digs.scenes.level.LevelIdx.RefreshableLevelTable;

import com.badlogic.gdx.graphics.g2d.TextureAtlas;
import com.badlogic.gdx.scenes.scene2d.InputEvent;
import com.badlogic.gdx.scenes.scene2d.ui.Image;
import com.badlogic.gdx.scenes.scene2d.ui.Table;
import com.badlogic.gdx.scenes.scene2d.utils.ClickListener;

public class LevelPack extends Table{
	private int idx = -1;
	public LevelPack(final LevelScene levelScene, int idx){
		TextureAtlas atlas = Engine.resource("All");
//		BitmapFont font = Engine.resource("BigFont");
//		TextButtonStyle style = new TextButtonStyle(new NinePatchDrawable(atlas.createPatch("btn")), null, null, font);
//		style.fontColor = Color.BLACK;
//		TextButtonStyle style2 = new TextButtonStyle(new NinePatchDrawable(atlas.createPatch("btn-click")), null, null, font);
//		style2.fontColor = Color.RED;
//		guide_1 = new TextButton("Guide",style2);
//		guide_2 = new TextButton("Guide",style);
//		pack1_1 = new TextButton("Pack1",style2);
//		pack1_2 = new TextButton("Pack1",style);
//		pack2_1 = new TextButton("Pack2",style2);
//		pack2_2 = new TextButton("Pack2",style);
//		guide_1.padRight(60);
//		pack1_1.padRight(60);
//		pack2_1.padRight(60);
		guide_1 = new Image(atlas.findRegion("btn-guide-2"));
		guide_2 = new Image(atlas.findRegion("btn-guide-1"));
		pack1_1 = new Image(atlas.findRegion("btn-pack1-2"));
		pack1_2 = new Image(atlas.findRegion("btn-pack1-1"));
		pack2_1 = new Image(atlas.findRegion("btn-pack2-2"));
		pack2_2 = new Image(atlas.findRegion("btn-pack2-1"));
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
		this.remake(idx);
		final ClickListener guideL = new ClickListener(){
			public void clicked(InputEvent event, float x, float y) {
				if(remake(0)){
					levelScene.getLevelPanel().setWidget(guideTabel);
					levelScene.getLevelPanel().setScrollY(0);
				}
				Engine.getSoundManager().playSound("SoundChooseGuide");
				super.clicked(event, x, y);
			}};
		final ClickListener pack1L = new ClickListener(){
			public void clicked(InputEvent event, float x, float y) {
				if(remake(1)){
					levelScene.getLevelPanel().setWidget(pack1Table);
					levelScene.getLevelPanel().setScrollY(0);
				}
				Engine.getSoundManager().playSound("SoundChoosePack1");
				super.clicked(event, x, y);
			}};
		final ClickListener pack2L = new ClickListener(){
			public void clicked(InputEvent event, float x, float y) {
				if(remake(2)){
					levelScene.getLevelPanel().setWidget(pack2Table);
					levelScene.getLevelPanel().setScrollY(0);
				}
				Engine.getSoundManager().playSound("SoundChoosePack2");
				super.clicked(event, x, y);
			}};
			guide_1.addListener(guideL);guide_2.addListener(guideL);
			pack1_1.addListener(pack1L);pack1_2.addListener(pack1L);
			pack2_1.addListener(pack2L);pack2_2.addListener(pack2L);
	}
	Image guide_1,guide_2,pack1_1,pack1_2,pack2_1,pack2_2;
	boolean remake(int idx){
		if(idx == this.idx)return false;
		this.idx = idx;
		this.clear();
		this.add();
		this.row().spaceBottom(20);;
		if(idx==0){
			this.add(guide_1);
		}else {
			this.add(guide_2);
		}
		this.row().spaceBottom(20);
		if(idx==1){
			this.add(pack1_1);
		}else{
			this.add(pack1_2);
		}
		this.row().spaceBottom(20);
		if(idx==2){
			this.add(pack2_1);
		}else {
			this.add(pack2_2);
		}
		this.pack();
		IO.savePackScroll(idx);//save the pack status
		return true;
	}
}
