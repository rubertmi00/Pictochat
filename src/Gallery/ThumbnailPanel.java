package Gallery;

import java.awt.Dimension;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.RenderingHints;
import java.awt.image.BufferedImage;

import javax.swing.JPanel;

//Michael Ruberto
//Program Description: The panel that holds the Thumbnails and displays the images when they are selected
//May 17, 2018
public class ThumbnailPanel extends JPanel{
   private static final long serialVersionUID = 1L;
   private static final int PREF_W = 600;
   private static final int PREF_H = 338;
   private RenderingHints hints =
         new RenderingHints(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);
   private BufferedImage img;
   private boolean showImg;
   
   public ThumbnailPanel(){
      super();
      this.setSize(new Dimension(PREF_W, PREF_H));
      img = new BufferedImage(1, 1, BufferedImage.TYPE_INT_RGB);
      showImg = false;
   }
   
   public void paintComponent(Graphics g){
      super.paintComponent(g);
      Graphics2D g2 = (Graphics2D) g;
      g2.setRenderingHints(hints);
      if(showImg)
         g2.drawImage(img, 0, 0, this.getWidth(), this.getHeight(), this);
   }
   
   public void displayImage(BufferedImage img) {
      this.img = img;
      showImg = true;
   }

   public void setShowImg(boolean showImg){
      this.showImg = showImg;
   }
}
