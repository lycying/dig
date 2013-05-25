package info.u250.digs.scenes.npclist;

import info.u250.c2d.engine.Engine;
import info.u250.digs.scenes.NpcListScene;
import info.u250.digs.scenes.game.entity.BaseEntity;

import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.graphics.g2d.TextureAtlas;
import com.badlogic.gdx.math.MathUtils;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.scenes.scene2d.InputEvent;
import com.badlogic.gdx.scenes.scene2d.ui.Image;
import com.badlogic.gdx.scenes.scene2d.ui.Label;
import com.badlogic.gdx.scenes.scene2d.ui.Label.LabelStyle;
import com.badlogic.gdx.scenes.scene2d.ui.Table;
import com.badlogic.gdx.scenes.scene2d.utils.ClickListener;
import com.badlogic.gdx.scenes.scene2d.utils.NinePatchDrawable;
import com.esotericsoftware.tablelayout.BaseTableLayout;

public class NpcItemTable extends  Table{
	
	final NpcListScene container;
	@SuppressWarnings("rawtypes")
	final Class flag;
	public NpcItemTable(final NpcWrapper eWrapper,final NpcListScene npcListScene){
		container = npcListScene;
		flag = eWrapper.e.getClass();
		TextureAtlas atlas = Engine.resource("All");
		
		this.setBackground(new NinePatchDrawable(atlas.createPatch("ui-npc-item")));
		try {
			//TODO
			this.add(new Image(((BaseEntity)Class.forName(eWrapper.e.getClass().getName()).newInstance()).getWalkAnimationLeft().getKeyFrame(0)));
		} catch (Exception e1) {
			this.add();
		}
		
		Label title = new Label(eWrapper.title,new LabelStyle(Engine.resource("Font",BitmapFont.class),Color.YELLOW));
		Label desc = new Label(eWrapper.desc,new LabelStyle(Engine.resource("Font",BitmapFont.class),Color.BLACK));
		Label clickTo = new Label("click to see it's attributes ",new LabelStyle(Engine.resource("Font",BitmapFont.class),Color.GRAY));
		desc.setWrap(true);
		this.add(title).align(BaseTableLayout.LEFT);
		this.row();
		this.add(desc).colspan(2).minWidth(400);
		this.row();
		this.add(clickTo).colspan(2).minWidth(400);
		this.pack();
		this.setWidth(450);
		
		this.addListener(new ClickListener(){
			@Override
			public void clicked(InputEvent event, float x, float y) {
				npcListScene.detailTable.fill(eWrapper);
				super.clicked(event, x, y);
			}
		});
	} 
	Vector2 tmp = new Vector2();
	@Override
	public void act(float delta) {
		if(container.detailTable.getNpc().e.getClass() == flag){
			this.setColor(Color.YELLOW);
			
			container.followImage.setOrigin(0, 2);
			container.followImage.setPosition(380, 350);
			
			tmp.set(this.localToAscendantCoordinates(container.detailTable,tmp.set(0, this.getHeight()/2)));
			float dst = tmp.dst(380, 350);
			float angle = tmp.sub(380, 350).angle();
			float y = dst*MathUtils.sinDeg(angle);
			if(y>115f){
				dst = 115f/MathUtils.sinDeg(angle);
			}else if(y<-320f){
				dst = -320f/MathUtils.sinDeg(angle);
			}
			container.followImage.setScaleX(dst/4.0f);
			container.followImage.setRotation(angle);
		}else{
			this.setColor(Color.WHITE);
		}
		super.act(delta);
	}
}
