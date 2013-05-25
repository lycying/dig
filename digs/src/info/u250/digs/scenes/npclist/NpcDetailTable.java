package info.u250.digs.scenes.npclist;

import info.u250.c2d.engine.Engine;

import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.graphics.g2d.TextureAtlas;
import com.badlogic.gdx.scenes.scene2d.Group;
import com.badlogic.gdx.scenes.scene2d.ui.Label;
import com.badlogic.gdx.scenes.scene2d.ui.Label.LabelStyle;
import com.badlogic.gdx.scenes.scene2d.ui.Table;
import com.badlogic.gdx.scenes.scene2d.utils.NinePatchDrawable;

public class NpcDetailTable extends Table{
	Label speed ;
	Label hp ;
	Label gold;
	Label attack;
	Label attackRange ;
	Label heal;
	Label healRange;
	Label defense;
	
	NpcWrapper npc;
	
	Group npcSolt;
	public NpcDetailTable(){
		BitmapFont font = Engine.resource("Font");
		TextureAtlas atlas = Engine.resource("All");
		
		LabelStyle blackStyle =  new LabelStyle(font, Color.BLACK);
		LabelStyle blackStyle2 =  new LabelStyle(font, Color.BLACK);
		LabelStyle redStyle =  new LabelStyle(font, Color.BLUE);
		redStyle.background = new NinePatchDrawable(atlas.createPatch("label2"));
		blackStyle.background = new NinePatchDrawable(atlas.createPatch("label"));
		
		Label lblSpeed = new Label("Speed:",blackStyle);
		speed = new  Label("50",redStyle);
		Label lblHp = new Label("Hp:",blackStyle);
		hp = new  Label("5",redStyle);
		Label lblGold = new Label("Gold Hold:",blackStyle);
		gold = new  Label("2",redStyle);
		
		Label lblAttack = new Label("Attack:",blackStyle);
		attack = new  Label("5",redStyle);
		//Label lblAttackRange = new Label("Range:",blackStyle);
		attackRange = new  Label("100",redStyle);
		Label lblHeal = new Label("Heal:",blackStyle);
		heal = new  Label("5",redStyle);
		//Label lblHealRange = new Label("Range:",blackStyle);
		healRange = new  Label("100",redStyle);
		Label lblDefense = new Label("Defense:",blackStyle2);
		defense = new  Label("No",redStyle);
		
		Table topRightTable = new Table();
		topRightTable.padLeft(10);
		topRightTable.add(lblSpeed).width(110);
		topRightTable.add(speed).width(110);
		topRightTable.row();
		topRightTable.add(lblHp).width(110);
		topRightTable.add(hp).width(110);
		topRightTable.row();
		topRightTable.add(lblGold).width(110);
		topRightTable.add(gold).width(110);
		topRightTable.row();
		topRightTable.add(lblAttack).width(110);
		topRightTable.add(attack).width(110);
//		topRightTable.add(lblAttackRange).width(110);
//		topRightTable.add(attackRange).width(110);
		topRightTable.row();
		topRightTable.add(lblHeal).width(110);
		topRightTable.add(heal).width(110);
		topRightTable.row();
		topRightTable.add(lblDefense).width(110);
		topRightTable.add(defense).width(110);
		
		
		npcSolt = new Group();
		npcSolt.setSize(130, 0);
		topRightTable.add(npcSolt).width(130);
		
		 
//		topRightTable.add(lblHealRange).width(110);
//		topRightTable.add(healRange).width(110);
		topRightTable.pack();
		
		
		this.add(topRightTable);
		this.row();
		
		this.pack();
		this.setPosition(80, 300);
		this.setBackground(new NinePatchDrawable(atlas.createPatch("detail")));
		
	}
	
	public void fill(NpcWrapper eWrapper){
		this.npc = eWrapper;
		eWrapper.e.setScale(8);
		npcSolt.clear();
		npcSolt.addActor(eWrapper.e);
		speed.setText((int)eWrapper.e.speed+"");
		hp.setText(eWrapper.e.hp+"");
		hp.setColor(eWrapper.e.hp>0?Color.WHITE:Color.YELLOW);
		gold.setText(eWrapper.e.goldHold+"");
		gold.setColor(eWrapper.e.goldHold>0?Color.WHITE:Color.YELLOW);
		attack.setText(eWrapper.e.attack+"");
		attack.setColor(eWrapper.e.attack>0?Color.WHITE:Color.YELLOW);
		attackRange.setText(eWrapper.e.attackRange+"");
		heal.setText(eWrapper.e.heal+"");
		heal.setColor(eWrapper.e.heal>0?Color.WHITE:Color.YELLOW);
		healRange.setText(eWrapper.e.healRange+"");
		defense.setText(eWrapper.e.defense?"Yes":"No");
		defense.setColor(eWrapper.e.defense?Color.GREEN:Color.RED);
	}

	public NpcWrapper getNpc() {
		return npc;
	}
	

}
