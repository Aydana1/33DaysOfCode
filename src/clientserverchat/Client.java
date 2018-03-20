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
import java.net.Socket;
import java.util.Scanner;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 *
 * @author ajdana
 */
public class Client implements Runnable {
    
    private static Socket clientSocket = null;
    private static BufferedReader inFromUser = null;
    private static DataOutputStream outToServer = null;
    private static BufferedReader inFromServer = null;
    private static String send, rec;
    
    private final int portNumber;
    private final String hostname;
    private String username;
    
    public String getUsername() {
        return username;
    }    
    
    public void setUsername(String username) {
        this.username = username;
    }
    
    public Client(String hostname, int portNumber, String username) {
        this.hostname = hostname;
        this.portNumber = portNumber;
        this.username = username;
    }
    

    public static void main(String argv[]) throws IOException {
        
        int portNumber = 6789;
        String hostname = "localhost";
        String username = "Anonymous";
	Scanner scan = new Scanner(System.in);
		
	System.out.println("Enter the username: ");
	username = scan.nextLine();
  
        Client client = new Client(hostname, portNumber, username);
        
        clientSocket = new Socket("localhost", 6789); 
        
        inFromUser = new BufferedReader(new InputStreamReader(System.in)); //read from keyboard
        outToServer = new DataOutputStream(clientSocket.getOutputStream());  // send to server
        inFromServer =  new BufferedReader(new InputStreamReader(clientSocket.getInputStream()));
        
        if(clientSocket != null && inFromServer != null && outToServer != null) {
            
            new Thread(client).start();
              
            while(true){
              send = inFromUser.readLine();  //from user
              outToServer.writeBytes(send + '\n');  //to server
              break;
            }
            
            clientSocket.close();
            inFromUser.close();
            outToServer.close();
        }
        
        scan.close();
         
    }

    @Override
    public void run() {
             
        while(true) {
            try {
                rec = inFromServer.readLine();  //from server
            } catch (IOException ex) {
                Logger.getLogger(Client.class.getName()).log(Level.SEVERE, null, ex);
            }
             System.out.println("FROM SERVER: " + rec);
              
              if(rec.equals("END")){
                try {
                    clientSocket.close();
                } catch (IOException ex) {
                    Logger.getLogger(Client.class.getName()).log(Level.SEVERE, null, ex);
                }
                  break;
               } 
        }
    
    }
    
}
