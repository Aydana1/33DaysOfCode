/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package clientserverchat;

import java.io.BufferedReader;
import java.io.DataOutputStream;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 *
 * @author ajdana
 */
public class ClientServerChat {

    private static ServerSocket sc;
    private static Socket conSocket;
    private static DataOutputStream outToClient;
    private static BufferedReader inFromClient;
    private static int portNumber;
    
    private static int maxNumOfClients;
    private static final clientsThread[] cThreads = new clientsThread[maxNumOfClients]; 
    /**
     * @param args the command line arguments
     * @throws java.io.IOException
     */
    public static void main(String[] args) throws IOException {
        // TODO code application logic here
        
        int portNumber = 6789;
        
        sc = new ServerSocket(portNumber); 
        conSocket = sc.accept();  
        
    }

    private static class clientsThread extends Thread {

        private final Socket clientSocket;
        private final clientsThread[] threads;
        private String username;
        private String cName;

        private clientsThread(Socket clientSocket, clientsThread[] threads) {
            this.clientSocket = clientSocket;
            this.threads = threads;
            maxNumOfClients = threads.length;
        }
        
        @Override
        public void run() {
            
            try {
                outToClient = new DataOutputStream(conSocket.getOutputStream());
                inFromClient = new BufferedReader(new InputStreamReader(conSocket.getInputStream()));
                while(true) {
                   username = inFromClient.readLine();
                   if(username.indexOf('@') == -1){
                       break;
                   } else {
                       System.out.println("The username cannot have @ symbol.\n");
                   }
                }
                
                
                outToClient.writeBytes("Say hi to " + username + "!\n To exit type 'quit' without quotes.\n");

                synchronized (this) {
                    
                   for(int i=0; i<maxNumOfClients; i++) {  
                       if ( threads[i] == this && threads[i] != null) {
                            cName = "@" + username;
                            break;
                       }       
                   } 
                   
                   for(int i=0; i<maxNumOfClients; i++) {  
                       if ( threads[i] != this && threads[i] != null) {
                            outToClient.writeBytes("A new client " + cName + " has joined the chat room!\n");
                            break;
                       }       
                   }       
                }
                
                /*Conversation started*/
                String readLine;
                while(true) {
                    
                    readLine = inFromClient.readLine();
                    if(readLine.startsWith("quit")) {
                        break;
                    }
                    
                    // If msg is private send it to this client
                    
                    
                }
                
     
            } catch (IOException ex) {
                Logger.getLogger(ClientServerChat.class.getName()).log(Level.SEVERE, null, ex);
            }
          
        
        
            
            
        }
        
    }
    
}
