//Michael Ruberto
//Program Description: Displays sent and received messages
//Jun 1, 2018

package Chatting;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Dimension;

import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTextArea;

public class MessagePanel extends JPanel{
   private static final long serialVersionUID = 1L;
   private static final int PREF_W = 620;
   private static final int PREF_H = 450;
   private JTextArea chatBox;
   private JScrollPane scroller;
   
   public MessagePanel(){
      //Sets up main panel and variables
      this.setBackground(new Color(0, 228, 155));
      this.setFocusable(false);  
//      this.setLayout(new GridLayout(3,3));
      this.setLayout(new BorderLayout());
      chatBox = new JTextArea();
      chatBox.setFocusable(false);
      chatBox.setEditable(false);
      scroller = new JScrollPane(chatBox);
      this.add(scroller, BorderLayout.CENTER);
      
      JPanel[] formatting = new JPanel[4];
      for(int i = 0; i < formatting.length; i++) {
         formatting[i] = new JPanel();
         formatting[i].setBackground(new Color(0, 155, 228));
      }
      this.add(formatting[0], BorderLayout.NORTH);
      this.add(formatting[1], BorderLayout.SOUTH);
      this.add(formatting[2], BorderLayout.EAST);
      this.add(formatting[3], BorderLayout.WEST);
   }

   public Dimension getPreferredSize(){
      return new Dimension(PREF_W, PREF_H);
   }

   public JTextArea getChatBox(){
      return chatBox;
   }

   public void setChatBox(JTextArea chatBox){
      this.chatBox = chatBox;
   }
}
