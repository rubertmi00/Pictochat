//Michael Ruberto
//Program Description: Main panel that controls everything else in the app
//May 17, 2018

package UI;

import java.awt.CardLayout;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.RenderingHints;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.awt.event.MouseMotionListener;

import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.SwingUtilities;

import Chatting.ChatPanel;
import Drawing.ArtPanel;
import Gallery.Gallery;

public class PictochatMain  extends JPanel implements KeyListener, MouseListener, MouseMotionListener{
   
   private static final long serialVersionUID = 1L;
   private static final int PREF_W = 620;
   private static final int PREF_H = 450;
   private RenderingHints hints =
         new RenderingHints(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);
   private boolean commandPressed, shiftPressed;
   private CardLayout cards;
   private ArtPanel aPanel;
   private Gallery gPanel;
   private ChatPanel cPanel;
   private boolean galleryOn, artOn, chatOn;
   
   public PictochatMain(){
      setBackground(Color.BLACK);
      this.setLayout(null);
      this.addKeyListener(this);
      this.addMouseListener(this);
      this.addMouseMotionListener(this);
      this.setFocusable(true);
      this.requestFocus();
      cards = new CardLayout();
      aPanel = new ArtPanel();
      cPanel = new ChatPanel();
      gPanel = new Gallery(cPanel);
      this.setLayout(cards);
      this.add(aPanel, "Art Panel");
      this.add(gPanel, "Gallery Panel");
      this.add(cPanel, "Chat Panel");
      cards.show(this, "Chat Panel");
      chatOn = true;
      galleryOn = false;
      artOn = false;
      commandPressed = shiftPressed = false;
      cPanel.begin();
   }

   public Dimension getPreferredSize(){
      return new Dimension(PREF_W, PREF_H);
   }
   
   public void paintComponent(Graphics g){
      super.paintComponent(g);
      Graphics2D g2 = (Graphics2D) g;
      g2.setRenderingHints(hints);
   }
   
   private static void createAndShowGUI(){
      JFrame frame = new JFrame("Pictochat");
      PictochatMain panelManager = new PictochatMain();
      frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
      frame.getContentPane().add(panelManager);
      frame.pack();
      frame.setLocationRelativeTo(null);
      frame.setVisible(true);
   }
   
   public static void main(String[] args){
      SwingUtilities.invokeLater(new Runnable(){
         public void run(){
            createAndShowGUI();
         }
      });
   }

   @Override
   public void keyPressed(KeyEvent e){
      int key = e.getKeyCode();
      
      //Booleans for command and shift
      if(key == KeyEvent.VK_META) commandPressed=true;
      else if(key == KeyEvent.VK_SHIFT) shiftPressed=true;
      
      //Changing Panels
      else if(key == KeyEvent.VK_C && commandPressed) {
         cards.show(this, "Chat Panel"); artOn = false; galleryOn = false; chatOn = true;}
      else if(key == KeyEvent.VK_A && commandPressed) {
         cards.show(this, "Art Panel"); artOn = true; galleryOn = false; chatOn = false;}
      else if(key == KeyEvent.VK_G && commandPressed) {
         cards.show(this, "Gallery Panel"); artOn = false; galleryOn = true; chatOn = false; aPanel.clearCanvas();
         gPanel.returnToMenu();
         gPanel.resetThumbnails();
      }
      
      //Chat Commands
      else if(key == KeyEvent.VK_ENTER && chatOn && cPanel.isConnected()) {
         cPanel.sendMessage(cPanel.getTextBox().getText());
         cPanel.getTextBox().setText("");
      }
      
      
      //Gallery Commands
      else if(key == KeyEvent.VK_BACK_SPACE && !gPanel.getThumbnails().get(0).isVisible() && galleryOn) {
         gPanel.returnToMenu();
         gPanel.getHeader().showButtons(false);
      }
      else if(key == KeyEvent.VK_D && commandPressed && galleryOn) gPanel.deleteImage();
      
      //Drawing Commands
      else if(key == KeyEvent.VK_R && commandPressed && artOn) aPanel.clearCanvas();
      else if(key == KeyEvent.VK_Z && commandPressed && shiftPressed && artOn) aPanel.redo();
      else if(key == KeyEvent.VK_Z && commandPressed && artOn) aPanel.undo();
      else if(key == KeyEvent.VK_S && commandPressed && artOn) aPanel.saveImage();
   }
   
   @Override
   public void keyReleased(KeyEvent e){
      int key = e.getKeyCode();
      if(key == KeyEvent.VK_META) commandPressed=false;
      else if(key == KeyEvent.VK_SHIFT) shiftPressed=false;
   }

   @Override
   public void mouseDragged(MouseEvent e){
      if(artOn)
         aPanel.drawPoint(e.getX(), e.getY());
   }
   
   @Override
   public void mousePressed(MouseEvent e){
      if(artOn)
         aPanel.drawPoint(e.getX(), e.getY());      
   }

   @Override
   public void mouseReleased(MouseEvent e){
      if(artOn)
         aPanel.nextLine();
      this.requestFocus();
   }
   
   @Override
   public void mouseMoved(MouseEvent e){}
   @Override
   public void mouseClicked(MouseEvent e){}
   @Override
   public void mouseEntered(MouseEvent e){}
   @Override
   public void mouseExited(MouseEvent e){}
   @Override
   public void keyTyped(KeyEvent e){}
}