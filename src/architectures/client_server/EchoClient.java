package architectures.client_server;

import java.io.*;
import java.net.Socket;

/**
 * To run this example, first, compile both EchoServer.java and EchoClient.java
 * using the following commands:
 * 
 * javac EchoServer.java
 * javac EchoClient.java
 * 
 * Next, start the server by running java EchoServer. In a separate terminal,
 * run the client by executing java EchoClient. The client will connect to the
 * server, send a message, and receive a response (the echoed message). The
 * server will display the received message and the client will display the
 * response.
 * 
 * This simple example demonstrates the client-server architecture, where the
 * server listens for incoming connections and processes requests from clients,
 * while the client sends requests and receives responses from the server.
 * 
 */

public class EchoClient {
    public static void main(String[] args) throws IOException {
        String hostname = "localhost";
        int port = 8080;

        try (Socket socket = new Socket(hostname, port)) {
            System.out.println("Connected to server");

            OutputStream output = socket.getOutputStream();
            PrintWriter writer = new PrintWriter(output, true);

            InputStream input = socket.getInputStream();
            BufferedReader reader = new BufferedReader(new InputStreamReader(input));

            String message = "Hello, Server!";
            System.out.println("Sending message: " + message);
            writer.println(message);

            String response = reader.readLine();
            System.out.println("Received response: " + response);
        }
    }
}

/**
 * During the lecture - split terminal and cd bin then:
 * java architectures.client_server.EchoClient
 */
