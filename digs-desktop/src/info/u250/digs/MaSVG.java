package info.u250.digs;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.utils.XmlReader;
import com.badlogic.gdx.utils.XmlReader.Element;

public abstract class MaSVG
{
   private String file;
   protected float screenheight;
   protected String lastGroupLabel;
   
   public MaSVG(float screenheight)
   {
      this.screenheight = screenheight; // for transforming y
   }
   
   public void load(String name)
   {
      XmlReader xr = new XmlReader();
      Element lev = new Element("a", null);
      file = name;
      try
      {
         lev = xr.parse(Gdx.files.internal(file));
      }
      catch(Exception e)
      {
         Gdx.app.log("test", "Error loading "+file);
      }

      loadElement(lev, 0, 0, 0, 1, 1);      
   }
   
   // the rest
   
   public String getImageName(String trans)
   {
      // /media/amon/praca/android/fluffy/topack-game/tile-grass.png
      String name = "";
      try
      {
         int en = trans.indexOf(".png");
         int st = trans.lastIndexOf('/');
         name = trans.substring(st+1, en);
      }
      catch(Exception e)
      {
         Gdx.app.log("test", "Error getting filename: "+trans);
      }
      return name;
   }
   
   public void loadElement(Element el, float x, float y, float r, float sx, float sy)
   {
      String elname = el.getName();
      float[] fl = new float[1]; // ugly, I know
      int count = el.getChildCount();
      if(elname.equals("g")) // allows getting the name of the group (for example: layer name!)
      {
         String s = el.getAttribute("inkscape:label", "empty");
         if(!s.equals("empty"))
         {
            lastGroupLabel = s;
         }
      }
      
      int sign = 0;
      float r2 = 0;
         
      // magic for transforms and matrixes, don't touch unless you know what you are doing
      String trans = el.getAttribute("transform", "");
      if(!trans.equals(""))
      {
         if(trans.contains("translate("))
         {
            fl = getTwoFloats(trans, "translate(");
            x+=fl[0];
            y+=fl[1];
         }
         else if(trans.contains("matrix("))
         {
            fl = getSixFloats(trans, "matrix(");
            x+=fl[4];
            y+=fl[5];
            
            // new version
            if(fl[1]!=0||fl[0]!=0) 
               r+=(float)(Math.toDegrees(Math.atan2(fl[1], fl[0])));            
            else
               r+=90;
            sign = ((fl[0]*fl[3] - fl[2]*fl[1])<0) ? -1 : 1;
            r2 = (float)(Math.toDegrees(Math.atan2(fl[3], fl[2])));

            float oldsx = sx;
            float oldsy = sy;

            if(sign<0)
            {
               if(r2<0&&r<0)
               {
                  sx*=(fl[0])/Math.cos( Math.toRadians(r) );
                  sy*=(fl[3])/Math.cos( Math.toRadians(r) );
                  sx=1/sx;
                  sy=1/sy;

                                                if(sy<0)
                  {
                     sy=1/sy;
                     sx=1/sx;
                  }
               }
               else
               {
                  sx*=(fl[0])/Math.cos( Math.toRadians(r) );
                  sy*=(fl[3])/Math.cos( Math.toRadians(r) );

                  if(sx==0||sy==0) // necessary for some situation when fl[0] and/or fl[3] are 0
                  {
                     sx=(float) (oldsx*(fl[1])/Math.sin( Math.toRadians(r) ));
                     sy=(float) (oldsy*-(fl[2])/Math.sin( Math.toRadians(r) ));
                  }
               }
            }
            else if(fl[0]!=0||fl[3]!=0)
            {
               sx*=fl[0]/Math.cos( Math.toRadians(r) );
               sy*=fl[3]/Math.cos( Math.toRadians(r) );

               if(sx==0||sy==0) // might not be necessary
               {
                  sx=(float) (oldsx*(fl[1])/Math.sin( Math.toRadians(r) ));
                  sy=(float) (oldsy*-(fl[2])/Math.sin( Math.toRadians(r) ));
               }
            }
            else // might not be necessary
            {
               sx=(float) (oldsx*(fl[1])/Math.sin( Math.toRadians(r) ));
               sy=(float) (oldsy*-(fl[2])/Math.sin( Math.toRadians(r) ));
            }
         }
      }
      
      if(count!=0)
         for(int i=0; i<count; i++)
         {
            loadElement(el.getChild(i), x, y, r, sx, sy);
         }
      
      // important magic for width,height and x,y
      float xx = getFloat(el.getAttribute("x", "0"))*sx;
      float yy = getFloat(el.getAttribute("y", "0"))*sy;
      float width = getFloat(el.getAttribute("width", ""))*sx;
      float height = getFloat(el.getAttribute("height", ""))*sy;
      
      float yyy = screenheight-(y+yy)-height;
      float xxx = x+xx;
      float rr = r;
      float originX = width/2;
      float originY = height/2;
      
      if(fl.length>2) // matrix
      {
         xx+=originX; // it assumes you set originX and originY as width/2 and height/2
         yy+=originY;         
         xxx = (float)(x + xx * Math.cos( Math.toRadians(rr) )
               - yy * Math.sin( Math.toRadians(rr) )) - originX;
         yyy =  screenheight-(float)(y + xx * Math.sin( Math.toRadians(rr) )
               + yy * Math.cos( Math.toRadians(rr) )) - originY;
         
         if(sign<0)
         {
            xx = getFloat(el.getAttribute("x", "0"))*(1/sx);
            yy = getFloat(el.getAttribute("y", "0"))*(1/sy);
            xx+=originX;
            yy+=originY;
            xxx = (float)(x + xx * Math.cos( Math.toRadians(rr) )
                  - yy * Math.sin( Math.toRadians(rr) )) - originX;
            yyy =  screenheight-(float)(y + xx * Math.sin( Math.toRadians(rr) )
                  + yy * Math.cos( Math.toRadians(rr) )) + originY;
         }
         rr = -rr;         
      }
      
      if(elname.equals("tspan")) // tekst
      {
         String text = el.getText();
         newText(text, el, xxx, yyy, width, height, rr);
      }
      else if(elname.equals("image")) // obraz
      {
         String name = getImageName(el.getAttribute("xlink:href", ""));
         
         newImage(name, el, xxx, yyy, width, height, rr);         
      }
      else if(elname.equals("rect")) // obraz
      {
         Element title = el.getChildByName("title");
         if(title!=null)
            newRect(title.getText(), el, xxx, yyy, width, height, rr);
         else
            newRect("", el, xxx, yyy, width, height, rr);
      }
   }
   
   // actions to override
   
   abstract public void newImage(String name, Element el, float xxx, float yyy,
         float width, float height, float rr);
   
   abstract public void newRect(String name, Element el, float xxx, float yyy,
         float width, float height, float rr);

   abstract public void newText(String text, Element el, float xxx, float yyy,
         float width, float height, float rr);

   // statics
   
   public static float[] getSixFloats(String trans, String search)
   {
      int st = trans.indexOf(search);
      float[] fl = new float[6];
      float xx = 0;
      float yy = 0;
      
      float zz = 0;
      float x2 = 0;
      float y2 = 0;
      float z2 = 0;
      
      if(st!=-1) // jest translate
      {
         int comma = trans.indexOf(",", st);
         int bracket = trans.indexOf(")", st);
         int comma2 = trans.indexOf(",", comma+1);
         try
         {
            
            xx = new Float(trans.substring(st+search.length(), comma));
            yy = new Float(trans.substring(comma+1, comma2));
            comma = comma2;
            comma2 = trans.indexOf(",", comma+1);
            zz = new Float(trans.substring(comma+1, comma2));
            
            comma = comma2;
            comma2 = trans.indexOf(",", comma+1);
            x2 = new Float(trans.substring(comma+1, comma2));
            
            comma = comma2;
            comma2 = trans.indexOf(",", comma+1);
            y2 = new Float(trans.substring(comma+1, comma2));
            
            comma = comma2;
            z2 = new Float(trans.substring(comma+1, bracket));
            
         }
         catch(Exception e)
         {
            
         }
      }
      fl[0] = xx;
      fl[1] = yy;
      fl[2] = zz;
      fl[3] = x2;
      fl[4] = y2;
      fl[5] = z2;
      return fl;
   }
   
   public static float[] getTwoFloats(String trans, String search)
   {
      int st = trans.indexOf(search);
      float[] fl = new float[2];
      float xx = 0;
      float yy = 0;
      if(st!=-1) // jest translate
      {
         int comma = trans.indexOf(",", st);
         int bracket = trans.indexOf(")", st);
         try
         {
            
            xx = new Float(trans.substring(st+search.length(), comma));
            yy = new Float(trans.substring(comma+1, bracket));
         }
         catch(Exception e)
         {
            
         }
      }
      fl[0] = xx;
      fl[1] = yy;
      return fl;
   }
   
   public int getInt(String t, String beg)
   {
      return getInt(t.substring(beg.length()));
   }
   
   public int getInt(String trans)
   {
      try
      {
         return new Integer(trans);
      }
      catch(Exception e)
      {
      }
      return 0;
   }
   
   public float getFloat(String t, String beg)
   {
      return getFloat(t.substring(beg.length()));
   }
   
   public float getFloat(String trans)
   {
      try
      {
         return new Float(trans);
      }
      catch(Exception e)
      {
      }
      return 0;
   }
}
