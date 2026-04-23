package Project.Network;

import Project.Models.University;
import Project.Repository.UniversityRepository;
import java.io.*;
import java.net.*;
import java.nio.charset.StandardCharsets;

public class Server {
    public static void main(String[] args) {
        System.out.println("Starting DigiUni Remote Server on port 5555...");

        try (ServerSocket server = new ServerSocket(5555);
             Socket socket = server.accept();
             BufferedReader in = new BufferedReader(
                     new InputStreamReader(socket.getInputStream(), StandardCharsets.UTF_8));
             BufferedWriter out = new BufferedWriter(
                     new OutputStreamWriter(socket.getOutputStream(), StandardCharsets.UTF_8))) {

            System.out.println("Client connected!");
            UniversityRepository repo = new UniversityRepository();
            University uni = repo.loadUniversity().orElse(null);

            out.write("Connected to DigiUni Server. Type 'info' to get data, or 'exit'\n");
            out.flush();

            String line;
            while ((line = in.readLine()) != null) {
                if (line.equals("exit")) {
                    out.write("Connection closed.\n");
                    out.flush();
                    break;
                } else if (line.equals("info")) {
                    if(uni != null) {
                        out.write("University: " + uni.universityName() + " | City: " + uni.city() + "\n");
                    } else {
                        out.write("No university data found.\n");
                    }
                    out.flush();
                } else {
                    out.write("Unknown command: " + line + "\n");
                    out.flush();
                }
            }
        } catch (IOException e) {
            System.out.println("Server error: " + e.getMessage());
        }
    }
}
