package Project.Network;

import java.io.*;
import java.net.Socket;
import java.nio.charset.StandardCharsets;
import java.util.Scanner;

public class Client {
    public static void main(String[] args) {
        try (Socket socket = new Socket("localhost", 5555);
             BufferedWriter out = new BufferedWriter(
                     new OutputStreamWriter(socket.getOutputStream(), StandardCharsets.UTF_8));
             BufferedReader in = new BufferedReader(
                     new InputStreamReader(socket.getInputStream(), StandardCharsets.UTF_8));
             Scanner scanner = new Scanner(System.in)) {

            System.out.println(in.readLine());

            while (true) {
                System.out.print("Remote>> ");
                String command = scanner.nextLine();
                out.write(command + "\n");
                out.flush();
                String response = in.readLine();
                System.out.println("Server: " + response);

                if (command.equals("exit")) {
                    break;
                }
            }
        } catch (IOException e) {
            System.out.println("Connection error. Is the server running? " + e.getMessage());
        }
    }
}