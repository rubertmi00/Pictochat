//Michael Ruberto
//Program Description: The main panel for communication
//May 30, 2018

package Chatting;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.image.BufferedImage;
import java.io.ByteArrayOutputStream;
import java.io.EOFException;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.net.InetAddress;
import java.net.ServerSocket;
import java.net.Socket;

import javax.imageio.ImageIO;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JTextField;

public class ChatPanel extends JPanel{
   private static final long serialVersionUID = 1L;
   private static final int PREF_W = 620;
   private static final int PREF_H = 450;
   private JTextField textBox;
   private MessagePanel chatBox;
   private boolean thisIsAServer;
   
   private ServerSocket server;
   private Socket connection;
   private String serverIP;
   private ObjectOutputStream output;
   private ObjectInputStream input;
   private boolean connected;
   
   public ChatPanel(){
      //Sets up main panel and variables
      this.setBackground(new Color(0, 155, 228));
      this.setFocusable(false);  
      this.setLayout(new BorderLayout());
      textBox = new JTextField();
      chatBox = new MessagePanel();
      this.add(chatBox, BorderLayout.CENTER);
      this.add(textBox, BorderLayout.SOUTH);
      connected = false;
   }
   
   
   public void begin() {
      //Initially, when chatting started, everything would freeze. Stack Overflow recommended using a new thread.
      new Thread(new Runnable()
      {
        @Override
        public void run()
        {
           String[] options = {"Server", "Client"};
           int entry = JOptionPane.showOptionDialog(null, "Server or Client?", "Setup",
                 JOptionPane.YES_NO_OPTION, JOptionPane.NO_OPTION, null, options, options[0]);
           thisIsAServer = !(entry == 1);
           if(thisIsAServer) startRunning();
           else {
              serverIP = (String)JOptionPane.showInputDialog(null, "Enter the IP Address of the Host ", "Connect to Host",
                    JOptionPane.PLAIN_MESSAGE, null, null, "");
              startRunningClient();
           }
        }
      }).start();
      
   }
   
   //Server
   private void startRunning() {
      try {
         server = new ServerSocket(9999, 100);
         while(true) {
            try {
               waitForConnection();
               setUpStreams();
               whileChatting();
            } catch(EOFException eof) {
               showMessage("\nChat Ended");
            }finally {
               close();
            }
         }
      } catch (IOException e) {e.printStackTrace();}
   }
   
   //Server
   private void waitForConnection() throws IOException{
      showMessage("\nWaiting for connection");
      connection = server.accept();
      showMessage("\nConnected to: " + connection.getInetAddress().getHostName());
      connected = true;
   }
   
   //Server + Client
   private void setUpStreams() throws IOException{
      output = new ObjectOutputStream(connection.getOutputStream());
      output.flush();
      input = new ObjectInputStream(connection.getInputStream());
      showMessage("\nStreams successfully setup");
   }

   //Server + Client
   private void whileChatting() throws IOException{
      Object message = "Hello World";
      sendMessage(message);
      do {
         try {
            if(input.readObject() instanceof String) message = input.readObject();
            else if(input.readObject() instanceof byte[]) {
               System.out.println("You have byte array");
               message = input.readObject();
            }
            
            showMessage(message);
         }catch(ClassNotFoundException cnfe) {
            showMessage("\nError");
         }
      }while(!message.equals("CLIENT - END") || !message.equals("SERVER - END"));
   }

   //Server
   private void close() {
      try {
         output.close();
         input.close();
         connection.close();
         connected = false;
      } catch (IOException e) {e.printStackTrace();}
   }

   //Server
   public void sendMessage(Object message) {
      
      String name = "CLIENT";
      if(thisIsAServer) name = "SERVER";
      
      if(message instanceof BufferedImage) {
         try {
            output.writeObject(toByteArray((BufferedImage)message));
            output.flush(); //*
            showMessage("\nImage Sent!");
         }catch(IOException e) {chatBox.getChatBox().append("\nERROR");}
      }
      
      else if(message instanceof String) {
         try {
            output.writeObject("\n" + name + " - " + message);
            output.flush();
            showMessage("\n" + name + " - " + message);
         }catch(IOException e) {chatBox.getChatBox().append("\nERROR");}
      }
      
      try {
         output.flush();
      } catch (IOException e) {e.printStackTrace();}
   }
   
   private byte[] toByteArray(BufferedImage image){
      try (ByteArrayOutputStream out = new ByteArrayOutputStream()){
          ImageIO.write(image, "png", out);
          return out.toByteArray();
      } catch (IOException e) {e.printStackTrace(); return null;}
  }
   

   //Server
   private void showMessage(Object message) {
      if(message instanceof byte[]) {
         chatBox.getChatBox().append("\nImage Received! Check Your Gallery!");
         FileOutputStream stream;
         try {
            //Converts Byte Array back into a file with a picture
            stream = new FileOutputStream(new File("src/SavedPics/NewPic.png"));
            stream.write((byte[])message);
         } catch (IOException e) {e.printStackTrace();}
      }
      else if(message instanceof String) {
         chatBox.getChatBox().append((String)message);
      }
   }
   
   //Client
   private void startRunningClient() {
      try {
         while(true) {
            try {
               connectToServer();
               setUpStreams();
               whileChatting();
            } catch(EOFException eof) {
               showMessage("\nChat Ended");
            }finally {
               close();
            }
         }
      } catch (IOException e) {e.printStackTrace();}
   }
   
   //Client
   private void connectToServer() throws IOException{
      showMessage("\nAttempting connection...");
      connection = new Socket(InetAddress.getByName(serverIP), 9999);
      showMessage("\nConnected to: " + connection.getInetAddress().getHostName());
      connected = true;
   }
   
   public Dimension getPreferredSize(){
      return new Dimension(PREF_W, PREF_H);
   }


   public JTextField getTextBox(){
      return textBox;
   }


   public boolean isConnected(){
      return connected;
   }


   public void setConnected(boolean connected){
      this.connected = connected;
   }
}
