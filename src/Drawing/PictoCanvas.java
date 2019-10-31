package Drawing;

import java.awt.Color;
import java.awt.Dimension;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.RenderingHints;
import java.util.ArrayList;

import javax.swing.JPanel;

//Michael Ruberto
//Program Description: The canvas on which the picture is drawn
//May 12, 2018
public class PictoCanvas  extends JPanel{
   
   private static final long serialVersionUID = 1L;
   private static final int PREF_W = 600;
   private static final int PREF_H = 338;
   private RenderingHints hints =
         new RenderingHints(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);
   private ArrayList<PictoPoint> points;
   
   public PictoCanvas(ArrayList<PictoPoint> points){
      setBackground(Color.WHITE);
      this.setFocusable(true);
      this.setVisible(true);
      this.setSize(new Dimension(PREF_W, PREF_H));
      this.setMaximumSize(new Dimension(PREF_W, PREF_H));
      this.setMinimumSize(new Dimension(PREF_W, PREF_H));
      
      this.points = points;
   }
   
   public void paintComponent(Graphics g){
      super.paintComponent(g);
      Graphics2D g2 = (Graphics2D) g;
      g2.setRenderingHints(hints);
      
      for(int i = 1; i < points.size(); i++) {
         if(points.get(i-1).getLineNum() == points.get(i).getLineNum()) {
            ArrayList<PictoPoint> line = interpolate(points.get(i-1), points.get(i));
            for(PictoPoint p:line)
               p.draw(g2);
         }
         points.get(i).draw(g2);
      }
   }
   
   public ArrayList<PictoPoint> interpolate(PictoPoint p1, PictoPoint p2) {
      ArrayList<PictoPoint> line = new ArrayList<PictoPoint>();
      if(p2.getX() < p1.getX()) {
         PictoPoint temp = p2;
         p2 = p1;
         p1 = temp;
      }
      
      double slope = 0;
      if(p2.getX() - p1.getX() != 0) {
         double deltaY = p2.getY() - p1.getY();
         double deltaX = p2.getX() - p1.getX();
         slope = deltaY/deltaX;
      }
      
      //Bresenham's Line Algorithm
      for(int x = p1.getX() + 1; x < p2.getX(); x += (p1.getR() * 1.5)) {
         int y = (int)(slope * (x - p1.getX()) + p1.getY());
         line.add(new PictoPoint(x, y, p1.getR(), p1.getColor(), p1.getLineNum()));
      }
      
      //Line going vertical, no slope
      if(p1.getX() + 1 >= p2.getX()) {
         if(p1.getY() > p2.getY()) {
            for(int y = p1.getY() - 1; y > p2.getY(); y -= p1.getR())
               line.add(new PictoPoint(p1.getX(), y, p1.getR(), p1.getColor(), p1.getLineNum()));
         }
         else {
            for(int y = p2.getY() - 1; y > p1.getY(); y -= p2.getR())
               line.add(new PictoPoint(p1.getX(), y, p1.getR(), p1.getColor(), p1.getLineNum()));
         }
      }
      return line;
   }

   public ArrayList<PictoPoint> getPoints(){
      return points;
   }

   public void setPoints(ArrayList<PictoPoint> points){
      this.points = points;
   }

   public static int getPrefW(){
      return PREF_W;
   }

   public static int getPrefH(){
      return PREF_H;
   }
}