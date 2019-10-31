package Gallery;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Cursor;
import java.awt.Font;
import java.awt.FontMetrics;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.RenderingHints;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.JButton;
import javax.swing.JPanel;

//Michael Ruberto
//Program Description: Header for the gallery panel which will hold buttons
//May 17, 2018
public class GalleryHeader extends JPanel{
   private static final long serialVersionUID = 1L;
   private RenderingHints hints =
         new RenderingHints(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);
   private Font font;
   private JButton delete, send;
   private Gallery gPanel;
   
   public GalleryHeader(Gallery gallery){
      super();
      this.font = new Font("Arial Rounded MT Bold", Font.PLAIN, 32);
      this.setLayout(new BorderLayout());
      this.gPanel = gallery;
      
      delete = new JButton("Delete");
      delete.setCursor(new Cursor(Cursor.HAND_CURSOR));
      delete.addActionListener(new ActionListener() {
         @Override
         public void actionPerformed(ActionEvent e) {
            gPanel.deleteImage();
         }
      });
      delete.setSize(200, 50);
      delete.setVisible(false);
      this.add(delete, BorderLayout.WEST);
      
      send = new JButton("Send");
      send.setCursor(new Cursor(Cursor.HAND_CURSOR));
      send.addActionListener(new ActionListener() {
         @Override
         public void actionPerformed(ActionEvent e) {
            gPanel.sendImage();
         }
      });
      send.setSize(200, 50);
      send.setVisible(false);
      this.add(send, BorderLayout.EAST);
   }
   
   public void showButtons(boolean show) {
      send.setVisible(show);
      delete.setVisible(show);
   }
   
   public void paintComponent(Graphics g){
      super.paintComponent(g);
      Graphics2D g2 = (Graphics2D) g;
      g2.setRenderingHints(hints);
      g2.setFont(font);
      FontMetrics fontM = g2.getFontMetrics(font);
      g2.setColor(Color.WHITE);
      String fileName = gPanel.getActiveFile().getName().substring(0, gPanel.getActiveFile().getName().indexOf('.'));
      g2.drawString(fileName, this.getWidth()/2 - fontM.stringWidth(fileName)/2, fontM.getAscent() - 5);
   }
}
