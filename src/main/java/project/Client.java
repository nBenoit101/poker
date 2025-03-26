/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package project;

/**
 *
 * @author nickbenoit
 */
import java.io.*;
import java.net.Socket;
import java.util.Scanner;

public class Client {
    private Socket socket;
    private BufferedReader in;
    private BufferedWriter out;
    private String username;

    public Client(Socket socket, String username) {
        try {
            this.socket = socket;
            this.out = new BufferedWriter(new OutputStreamWriter(socket.getOutputStream()));
            this.in = new BufferedReader(new InputStreamReader(socket.getInputStream()));
            this.username = username;
            
            // Send username to the server immediately upon connection
            out.write(username);
            out.newLine();
            out.flush();
        } catch (IOException e) {
            closeEverything();
        }
    }

    public void sendMessage() {
        try {
            Scanner scanner = new Scanner(System.in);
            while (socket.isConnected()) {
                String messageToSend = scanner.nextLine();
                if (messageToSend.equalsIgnoreCase("quit")) {
                    System.out.println("Disconnecting...");
                    closeEverything();
                    break;
                }
                out.write(messageToSend);
                out.newLine();
                out.flush();
            }
            scanner.close();
        } catch (IOException e) {
            closeEverything();
        }
    }

    public void listenForMessage() {
        new Thread(() -> {
            try {
                String messageFromServer;
                while (socket.isConnected()) {
                    messageFromServer = in.readLine();
                    if (messageFromServer == null) {
                        break;
                    }
                    System.out.println(messageFromServer);
                }
            } catch (IOException e) {
                System.err.println("Connection lost.");
            } finally {
                closeEverything();
            }
        }).start();
    }

    public void closeEverything() {
        try {
            if (in != null) in.close();
            if (out != null) out.close();
            if (socket != null) socket.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public static void main(String[] args) {
        try {
            Scanner scanner = new Scanner(System.in);
            System.out.print("Please enter your username: ");
            String username = scanner.nextLine();
            Socket socket = new Socket("localhost", 1234);
            Client client = new Client(socket, username);
            client.listenForMessage();
            client.sendMessage();
            scanner.close();
        } catch (IOException e) {
            System.err.println("Could not connect to server.");
        }
    }
}
