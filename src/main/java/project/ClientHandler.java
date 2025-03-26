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
import java.util.concurrent.CopyOnWriteArrayList;

public class ClientHandler implements Runnable {
    public static CopyOnWriteArrayList<ClientHandler> clients = new CopyOnWriteArrayList<>();
    private Socket socket;
    private BufferedReader in;
    private BufferedWriter out;
    private String username;

    public ClientHandler(Socket socket) {
        try {
            this.socket = socket;
            this.out = new BufferedWriter(new OutputStreamWriter(socket.getOutputStream()));
            this.in = new BufferedReader(new InputStreamReader(socket.getInputStream()));

            this.username = this.in.readLine(); // First message is the username
            clients.add(this);

            System.out.println(username + " has joined.");
            broadcastMessage(username + " has joined!");

        } catch (IOException e) {
            closeEverything();
        }
    }

    @Override
    public void run() {
        String messageFromUser;

        while (socket.isConnected()) {
            try {
                messageFromUser = in.readLine();
                if (messageFromUser == null) {
                    break; // Handle client disconnection
                }
                broadcastMessage(username + ": " + messageFromUser);
            } catch (IOException e) {
                break;
            }
        }
        closeEverything();
    }

    public void broadcastMessage(String message) {
        for (ClientHandler client : clients) {
            try {
                if (!client.username.equals(this.username)) {
                    client.out.write(message);
                    client.out.newLine();
                    client.out.flush();
                }
            } catch (IOException e) {
                client.closeEverything();
            }
        }
    }

    public void removeClient() {
        clients.remove(this);
        System.out.println(username + " has left.");
        broadcastMessage(username + " left the server.");
    }

    public void closeEverything() {
        removeClient();
        try {
            if (in != null) in.close();
            if (out != null) out.close();
            if (socket != null) socket.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
