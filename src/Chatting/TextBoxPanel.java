//Michael Ruberto
//Program Description:
//Jun 4, 2018

package Chatting;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.JButton;
import javax.swing.JPanel;
import javax.swing.JTextField;

public class TextBoxPanel extends JPanel{
   private static final long serialVersionUID = 1L;
   private static final int PREF_W = 620;
   private static final int PREF_H = 450;
   private JTextField textbox;
   private JButton sendButton;
   
   public TextBoxPanel(ChatPanel cPanel){
      //Sets up main panel and variables
      this.setBackground(new Color(0, 228, 155));
      this.setFocusable(false);  
      this.setLayout(new BorderLayout());
      textbox = new JTextField();
      sendButton = new JButton("Send");
      sendButton.addActionListener(new ActionListener() {
         @Override
         public void actionPerformed(ActionEvent e) {
            cPanel.sendMessage(textbox.getText());
         }
      });
      this.add(textbox, BorderLayout.WEST);
      this.add(sendButton, BorderLayout.EAST);
   }

   public Dimension getPreferredSize(){
      return new Dimension(PREF_W, PREF_H);
   }

   public JTextField getTextbox(){
      return textbox;
   }

   public void setTextbox(JTextField textbox){
      this.textbox = textbox;
   }

   public JButton getSendButton(){
      return sendButton;
   }

   public void setSendButton(JButton sendButton){
      this.sendButton = sendButton;
   }
}
