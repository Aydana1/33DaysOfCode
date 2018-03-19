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

/**
 *
 * @author ajdana
 */
public class Client {
    
    public static void main(String argv[]) throws IOException {
        
        Socket clientSocket = new Socket("localhost", 6789);
        
        BufferedReader inFromUser = new BufferedReader(new InputStreamReader(System.in)); //read from keyboard
        DataOutputStream outToServer = new DataOutputStream(clientSocket.getOutputStream());  // send to server
        BufferedReader inFromServer =  new BufferedReader(new InputStreamReader(clientSocket.getInputStream()));
        
    }
    
}
