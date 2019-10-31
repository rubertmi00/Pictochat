package Drawing;
import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Cursor;
import java.awt.Dimension;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.image.BufferedImage;
import java.io.File;
import java.util.ArrayList;

import javax.imageio.ImageIO;
import javax.swing.JButton;
import javax.swing.JColorChooser;
import javax.swing.JComponent;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JSlider;
import javax.swing.colorchooser.AbstractColorChooserPanel;

//Michael Ruberto
//Program Description: The main panel for all things drawing
//May 7, 2018
public class ArtPanel extends JPanel {
   private static final long serialVersionUID = 1L;
   private static final int PREF_W = 620;
   private static final int PREF_H = 450;
   private ArrayList<PictoPoint> points, removedPoints;
   private int lineNum;
   private Color currentColor;
   private JColorChooser chooser;
   private JSlider sizeSlide;
   private JButton saveButton;
   private JPanel sizeSaveP, optionP;
   private PictoCanvas canvas;
   
   public ArtPanel(){
      //Sets up main panel and variables
      this.setBackground(new Color(0, 155, 228));
      this.setFocusable(false);
      this.setLayout(new BorderLayout());
      
      
      points = new ArrayList<PictoPoint>();
      removedPoints = new ArrayList<PictoPoint>();
      currentColor = Color.BLACK;
      lineNum = 0;
      
      //Canvas
      canvas = new PictoCanvas(points);
      this.add(canvas, BorderLayout.CENTER);
      
      //Sets up color chooser
      chooser = new JColorChooser(Color.BLACK);
      AbstractColorChooserPanel[] panels = chooser.getChooserPanels(); //Gets all the sub panels as an array
      for (int i = 0; i < panels.length; i++) {
         if (!panels[i].getDisplayName().equalsIgnoreCase("Swatches"))
             chooser.removeChooserPanel(panels[i]); //Removes all the panels I don't want
         else {
            panels[i].setBackground(new Color(0, 155, 228)); //Changes the background color of the component I do want
         }
      }
      
      /*There's a small area on the color chooser panel that stays gray even when the background is changed.
       * This small segment of code targets that component individually and changes that color. Thanks Stack Overflow!*/
      AbstractColorChooserPanel panel = chooser.getChooserPanels()[0];
      JComponent component = (JComponent) panel.getComponent(0);
      component.setBackground(new Color(0, 155, 228)); 
      
      chooser.setCursor(new Cursor(Cursor.HAND_CURSOR));
      chooser.setPreviewPanel(new JPanel());
      
      //Creates panel for size slider and save button
      sizeSaveP = new JPanel();
      sizeSaveP.setBackground(new Color(0, 155, 228));
      sizeSaveP.setLayout(new BorderLayout());
      
      //Sets up size slider
      sizeSlide = new JSlider(1, 45);
      sizeSlide.setCursor(new Cursor(Cursor.HAND_CURSOR));
      sizeSaveP.add(sizeSlide, BorderLayout.CENTER);
      
      //Sets up save button
      saveButton = new JButton("Save");
      saveButton.setPreferredSize(new Dimension(sizeSaveP.getWidth(), 40));
      saveButton.setCursor(new Cursor(Cursor.HAND_CURSOR));
      saveButton.addActionListener(new ActionListener() {
         @Override
         public void actionPerformed(ActionEvent e) {
            saveImage();
         }
      });
      sizeSaveP.add(saveButton, BorderLayout.SOUTH);
      
      //Adds controls to one panel, adds that panel to bottom of main
      optionP = new JPanel();
      optionP.setBackground(new Color(0, 155, 228));
      optionP.setLayout(new BorderLayout());
      optionP.add(sizeSaveP, BorderLayout.EAST);
      optionP.add(chooser, BorderLayout.WEST);
      this.add(optionP, BorderLayout.SOUTH);      
   }
   
   public void saveImage() {
      ///Creates a Buffered Image with the default dimensions of the canvas
      BufferedImage image = new BufferedImage(PictoCanvas.getPrefW(), PictoCanvas.getPrefH(), BufferedImage.TYPE_INT_RGB);
      canvas.paint(image.getGraphics()); //Draws the panel contents on graphics object of BufferedImage
      
      String filename = "src/SavedPics/" + JOptionPane.showInputDialog("Enter the filename to save as:");
      filename += ".png";
      
      if(!filename.equals("src/SavedPics/null.png")) {
         try{
            File f = new File(filename);
            ImageIO.write(image, "png", f);
         } catch(Exception e){e.printStackTrace();}
      }
   }
   
   public void undo() {
      lineNum--;
      
      //Overwrite backed up points from same line
      for(int i = removedPoints.size() - 1; i >= 0; i--) 
         if(removedPoints.get(i).getLineNum() == lineNum)
            removedPoints.remove(i);
      
      //Remove points from highest line
      for(int i = points.size() - 1; i >= 0; i--)
         if(points.get(i).getLineNum() == lineNum)
            removedPoints.add(points.remove(i));
      
      canvas.setPoints(points);
      canvas.repaint();
   }
   
   public void redo() {
      //Add back the points from the removed line
      boolean noneRemoved = true;
      for(int i = removedPoints.size() - 1; i >= 0; i--)
         if(removedPoints.get(i).getLineNum() == lineNum) {
            points.add(removedPoints.remove(i));
            noneRemoved = false;
         }
      if(!noneRemoved) lineNum++;
      
      canvas.setPoints(points);
      canvas.repaint();
   }
   
   public void drawPoint(int x, int y) {
      this.requestFocus();
      currentColor = chooser.getColor();
      points.add(new PictoPoint(x, y, sizeSlide.getValue(), currentColor, lineNum));
      canvas.setPoints(points);
      canvas.repaint();
   }
   
   public void clearCanvas() {
      points.clear();
      removedPoints.clear();
      canvas.setPoints(points);
      canvas.repaint();
   }
   
   public void nextLine() {
      removedPoints.clear();
      lineNum++;
   }

   public Dimension getPreferredSize(){
      return new Dimension(PREF_W, PREF_H);
   }
}