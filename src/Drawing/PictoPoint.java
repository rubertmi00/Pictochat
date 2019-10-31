//Michael Ruberto
//Program Description: Points which will be drawn on the canvas
//May 15, 2018

package Drawing;

import java.awt.Color;
import java.awt.Graphics2D;
import java.awt.geom.Ellipse2D;

public class PictoPoint{
   private int x, y, r, lineNum;
   private Color color;
   
   public PictoPoint(int x, int y, int r, Color color, int lineNum){
      this.x = x;
      this.y = y;
      this.r = r;
      this.color = color;
      this.lineNum = lineNum;
   }
   
   public void draw(Graphics2D g2) {
      g2.setColor(color);
      g2.fill(new Ellipse2D.Double(x - r, y - r, 2*r, 2*r));
   }

   public int getX(){
      return x;
   }

   public void setX(int x){
      this.x = x;
   }

   public int getY(){
      return y;
   }

   public void setY(int y){
      this.y = y;
   }

   public int getR(){
      return r;
   }

   public void setR(int r){
      this.r = r;
   }

   public Color getColor(){
      return color;
   }

   public void setColor(Color color){
      this.color = color;
   }

   public int getLineNum(){
      return lineNum;
   }

   public void setLineNum(int lineNum){
      this.lineNum = lineNum;
   }
   
   public Ellipse2D.Double getBounds() {
      return new Ellipse2D.Double(x - r, y - r, 2*r, 2*r);
   }
}
