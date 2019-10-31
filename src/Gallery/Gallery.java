package Gallery;
import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.GridLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.util.ArrayList;

import javax.swing.JPanel;

import Chatting.ChatPanel;

//Michael Ruberto
//Program Description: The main panel for viewing previous drawings
//June 5, 2018
public class Gallery extends JPanel {
   private static final long serialVersionUID = 1L;
   private static final int PREF_W = 620;
   private static final int PREF_H = 450;
   private File[] files;
   private File activeFile;
   private ArrayList<Thumbnail> thumbnails;
   private BufferedImage selection;
   private GalleryHeader header;
   private ThumbnailPanel thumbnailP;
   private ChatPanel cPanel;
   
   public Gallery(ChatPanel cPanel){
      //Sets up main panel and variables
      this.setBackground(new Color(0, 155, 228));
      this.setFocusable(true);
      this.setLayout(new BorderLayout());
      this.cPanel = cPanel;
      files = new File("src/SavedPics").listFiles();
      activeFile = null;
      thumbnails = new ArrayList<Thumbnail>();
      
      header = new GalleryHeader(this);
      header.setBackground(new Color(0, 155, 228));
      this.add(header, BorderLayout.NORTH);
      
      thumbnailP = new ThumbnailPanel();
      thumbnailP.setBackground(new Color(0, 155, 228));
      thumbnailP.setLayout(new GridLayout(3, 3));  
      this.add(thumbnailP, BorderLayout.CENTER);
      
      resetThumbnails();
   }
   
   public void toggleButtonVisibility(){
      for(int i = 0; i < thumbnails.size(); i++)
         thumbnails.get(i).setVisible(!thumbnails.get(i).isVisible());
   }
   
   public void resetThumbnails() {
      //Remove all old buttons
      for(int i = 0; i < thumbnails.size(); i++)
         thumbnailP.remove(thumbnails.get(i));
      
      //Delete old buttons, re-check saved files
      thumbnails.clear();
      files = new File("src/SavedPics").listFiles();
      
      //Create and add new buttons
      for(int i = 1; i < files.length; i++) {
         Thumbnail tempButton = new Thumbnail(files[i], new Color(0, 155, 228));
         tempButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
               Thumbnail pressed = (Thumbnail) e.getSource();
               selection = pressed.getImage();
               activeFile = pressed.getFile();
               thumbnailP.displayImage(selection);
               header.showButtons(true);
               toggleButtonVisibility();
               repaint();
            }
         });
         thumbnails.add(tempButton);
      }
      
      //Add every new button to the panel
      for(int i = 0; i < thumbnails.size(); i++)
         thumbnailP.add(thumbnails.get(i));
      
      revalidate();
   }
   
   public void deleteImage() {
      for(int i = 0; i < thumbnails.size(); i++)
         if(selection.equals(thumbnails.get(i).getImage()))
            try {Files.delete(thumbnails.get(i).getFile().toPath());} catch (IOException e) {e.printStackTrace();}
      
      resetThumbnails();
      
      thumbnailP.setShowImg(false);
      header.showButtons(false);
      repaint();
   }
   
   public void sendImage() {
      cPanel.sendMessage(selection);
   }
   
   public void returnToMenu() {
      toggleButtonVisibility();
      thumbnailP.setShowImg(false);
      repaint();
   }
   
   public Dimension getPreferredSize(){
      return new Dimension(PREF_W, PREF_H);
   }

   public ArrayList<Thumbnail> getThumbnails(){
      return thumbnails;
   }

   public void setThumbnails(ArrayList<Thumbnail> thumbnails){
      this.thumbnails = thumbnails;
   }

   public GalleryHeader getHeader(){
      return header;
   }
   
   public File getActiveFile() {
      return activeFile;
   }
}