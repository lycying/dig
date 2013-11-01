package info.u250.digs.scenes.ui;


import com.badlogic.gdx.math.Interpolation;
import com.badlogic.gdx.scenes.scene2d.Actor;
import com.badlogic.gdx.scenes.scene2d.actions.TemporalAction;
import com.badlogic.gdx.scenes.scene2d.ui.Label;
import com.badlogic.gdx.utils.Pool;
import com.badlogic.gdx.utils.Pools;

/**
 * 
 * Ticker for {@link Label}.
 * 
 * @author Aleksandar Markovic
 */
public class Ticker extends TemporalAction {
   
   private CharSequence completeText;
   private StringBuilder currentDisplay = new StringBuilder();
   private Label label;
   private int currentPos;
   
   public Ticker(){}
   
   public Ticker(float duration) { super(duration);}
   
   public Ticker(float duration, CharSequence text){
      this(duration, text, null);
   }
   
   public Ticker(float duration, CharSequence text, Interpolation interpolation) {
      super(duration, interpolation);
      completeText = text;
      currentDisplay.setLength(text.length());
   }
   
   /**
    * Resets Ticker.
    */
   @Override
   public void restart() {
      super.restart();
      completeText = null;
      currentDisplay.delete(0, currentDisplay.length());
      label = null;
      currentPos = -1;
   }
   
   /**
    * Sets Label to be target of Ticker. Performs check if actor is instance of Label by instanceof operator.
    * @see #setActor(Actor);
    */
   @Override
   public void setActor(Actor actor) {
      if(!(actor instanceof Label))
         if(actor == null) super.setActor(actor);
         else throw new IllegalArgumentException("Ticker uses only Labels");
      
      super.setActor(actor);
      label = (Label)super.actor;
   }
   
   /**
    * Sets text to be ticked.
    * @param text
    */
   public void setText(CharSequence text) {
      completeText = text;
      currentDisplay.setLength(text.length());
   }
   
   @Override
   protected void begin() {
      currentPos = 0;
      currentDisplay.append(completeText.charAt(0));
   }
   
   
   @Override
   protected void update(float percent) {
      currentPos = (int) (completeText.length() * percent);
      currentDisplay.delete(0, currentDisplay.length());
      for(int i = 0; i < currentPos; i++) {
         currentDisplay.append(completeText.charAt(i));
      }
      
      label.setText(currentDisplay);
   }
   
   /**
    * Static factory method for Ticker with default duration of 2f. Pools instance.
    * @param text
    * @return
    */
   public static Ticker obtain(CharSequence text) {
      return Ticker.obtain(text, 2f);
   }
   
   /**
    * Static factory method for Ticker. Pools instance.
    * @param text
    * @param duration
    * @return
    */
   public static Ticker obtain(CharSequence text, float duration) {
      Pool<Ticker> pool = Pools.get(Ticker.class);
      Ticker t = pool.obtain();
      t.setPool(pool);
      t.setDuration(duration);
      t.completeText = text;
      t.currentDisplay.setLength(text.length());
      return t;
      
   }
   
}
