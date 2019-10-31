package Gallery;

import java.awt.Color;
import java.awt.Cursor;
import java.awt.Dimension;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;

import javax.imageio.ImageIO;
import javax.swing.JButton;
import javax.swing.border.LineBorder;

//Michael Ruberto
//Program Description: Gallery buttons which display the image they're linked to
//May 17, 2018
public class Thumbnail extends JButton{
   private static final long serialVersionUID = 1L;
   private BufferedImage image;
   private File file;
   
   public Thumbnail(File file, Color border) {
      this.file = file;
      try {image = ImageIO.read(file);} catch (IOException e) {e.printStackTrace();}
      this.setPreferredSize(new Dimension(50, 100));
      this.setCursor(new Cursor(Cursor.HAND_CURSOR));
      this.setBorder(new LineBorder(border, 3));
   }
   
   public void paintComponent(Graphics g){
      super.paintComponent(g);
      Graphics2D g2 = (Graphics2D) g;
      g2.drawImage(image, 0, 0, this.getWidth(), this.getHeight(), this);
   }

   public BufferedImage getImage(){
      return image;
   }

   public void setImage(BufferedImage image){
      this.image = image;
   }

   public File getFile(){
      return file;
   }

   public void setFile(File file){
      this.file = file;
   }

   @Override
   public String toString(){
      return "Thumbnail [file=" + file.getName() + "Vis? " + this.isVisible() + "]";
   }
}
