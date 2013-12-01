package info.u250.digs.scenes.ui;

import info.u250.c2d.engine.Engine;

import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.graphics.g2d.TextureAtlas;
import com.badlogic.gdx.scenes.scene2d.Group;
import com.badlogic.gdx.scenes.scene2d.InputEvent;
import com.badlogic.gdx.scenes.scene2d.ui.Label;
import com.badlogic.gdx.scenes.scene2d.ui.Label.LabelStyle;
import com.badlogic.gdx.scenes.scene2d.ui.Table;
import com.badlogic.gdx.scenes.scene2d.ui.TextButton;
import com.badlogic.gdx.scenes.scene2d.ui.TextButton.TextButtonStyle;
import com.badlogic.gdx.scenes.scene2d.utils.ClickListener;
import com.badlogic.gdx.scenes.scene2d.utils.NinePatchDrawable;
import com.esotericsoftware.tablelayout.BaseTableLayout;

public class CommonDialog extends Group{
	public CommonDialog(String[] strs){
		this.setSize(Engine.getWidth(), Engine.getHeight());
		final Table table = new Table();
		final TextureAtlas atlas = Engine.resource("All");
		final BitmapFont font = Engine.resource("MenuFont");
		table.setBackground(new NinePatchDrawable(atlas.createPatch("level-item-bg")));
		
		table.pad(50);
		for(int i=0;i<strs.length;i++){
			Color c = new Color();
			if(i==0){
				c.set(Color.BLACK);
			}else if(i==1){
				c.set(Color.RED);
			}
			final Label label = new Label(strs[i], new LabelStyle(font,c));
			label.setWrap(true);
			table.add(label).minWidth(400);
			table.row();
		}
		TextButtonStyle style = new TextButtonStyle(
				new NinePatchDrawable(atlas.createPatch("dialog-bg")), 
				new NinePatchDrawable(atlas.createPatch("dialog-bg")), null, font);
		style.fontColor = Color.BLACK;
		style.downFontColor = Color.RED;
		final TextButton view = new TextButton("Ok...",style);
		view.pack();
		view.padTop(20);
		view.padBottom(20);
		table.add(view).align(BaseTableLayout.CENTER);
		table.row();
		table.pack();
		table.setPosition(Engine.getWidth()/2-table.getWidth()/2, Engine.getHeight()/2-table.getHeight()/2);
		
		view.addListener(new ClickListener(){
			@Override
			public void clicked(InputEvent event, float x, float y) {
				CommonDialog.this.remove();
				Engine.doResume();
				super.clicked(event, x, y);
			}
		});
		this.addActor(table);
		
		Engine.doPause();
	}
	public CommonDialog(String str){
		this(new String[]{str});
	}
	public CommonDialog(String[] strs,final Runnable okRunnable){
		this.setSize(Engine.getWidth(), Engine.getHeight());
		final Table table = new Table();
		final TextureAtlas atlas = Engine.resource("All");
		final BitmapFont font = Engine.resource("MenuFont");
		table.setBackground(new NinePatchDrawable(atlas.createPatch("level-item-bg")));
		table.pad(50);
		for(int i=0;i<strs.length;i++){
			Color c = new Color();
			if(i==0){
				c.set(Color.BLACK);
			}else if(i==1){
				c.set(Color.RED);
			}else if(i==2){
				c.set(Color.BLUE);
			}
			final Label label = new Label(strs[i], new LabelStyle(font,c));
			label.setWrap(true);
			table.add(label).colspan(2).minWidth(400);
			table.row();
		}
		
		TextButtonStyle style = new TextButtonStyle(
				new NinePatchDrawable(atlas.createPatch("dialog-bg")), 
				new NinePatchDrawable(atlas.createPatch("dialog-bg")), null, font);
		style.fontColor = Color.BLACK;
		style.downFontColor = Color.RED;
		final TextButton ok = new TextButton("Ok...",style);
		ok.pack();
		ok.padTop(20);
		ok.padBottom(20);
		table.add(ok).align(BaseTableLayout.CENTER);
		final TextButton cancel = new TextButton("Cancel",style);
		cancel.pack();
		cancel.padTop(20);
		cancel.padBottom(20);
		table.add(cancel).align(BaseTableLayout.CENTER);
		table.row();
		table.pack();
		table.setPosition(Engine.getWidth()/2-table.getWidth()/2, Engine.getHeight()/2-table.getHeight()/2);
		
		ok.addListener(new ClickListener(){
			@Override
			public void clicked(InputEvent event, float x, float y) {
				okRunnable.run();
				CommonDialog.this.remove();
				Engine.doResume();
				super.clicked(event, x, y);
			}
		});
		cancel.addListener(new ClickListener(){
			@Override
			public void clicked(InputEvent event, float x, float y) {
				CommonDialog.this.remove();
				Engine.doResume();
				super.clicked(event, x, y);
			}
		});
		this.addActor(table);
		
		Engine.doPause();
	}
}
