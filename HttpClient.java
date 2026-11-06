
import java.net.URI;
import java.net.URL;
import java.net.Socket;
import java.net.InetSocketAddress;
import java.io.*;

/**
 * Simple HTTP client using TCP sockets.
 * Downloads a web page and saves it to a file.
 */
public class HttpClient {

    private String protocol;
    private String host;
    private String path;
    private String filename;
    private int port;

    /** Starts the HTTP client with a URL argument. */
    public static void main(String[] args) {
        if (args.length != 1) {
            System.out.println("Usage: java HttpClient <URL>");
            return;
        }

        HttpClient client = new HttpClient();

        if (client.readURL(args[0])) {
            client.getURL();
        }
    }

    /**
     * Reads the URL and extracts connection information.
     * @param input URL entered by the user
     * @return true if the URL is valid
     */
    public boolean readURL(String input) {
        try {
            URL url = URI.create(input).toURL();

            protocol = url.getProtocol();

            if (!protocol.equalsIgnoreCase("http")) {
                System.out.println("Error: Only HTTP is supported.");
                return false;
            }

            host = url.getHost();

            if (host.isEmpty()) {
                System.out.println("Error: Invalid host.");
                return false;
            }

            port = url.getPort();
            if (port == -1) {
                port = url.getDefaultPort();
            }

            path = url.getFile();
            if (path.isEmpty()) {
                path = "/";
            }

            String filePath = url.getPath();
            filename = filePath.substring(filePath.lastIndexOf('/') + 1);

            if (filename.isEmpty()) {
                filename = "index.html";
            }

            System.out.println("Protocol: " + protocol);
            System.out.println("Host: " + host);
            System.out.println("Port: " + port);
            System.out.println("Path: " + path);
            System.out.println("Filename: " + filename);

            return true;

        } catch (Exception e) {
            System.out.println("Invalid URL: " + e.getMessage());
            return false;
        }
    }

    /**
     * Reads one line from the server response.
     * @param input server input stream
     * @return line without CRLF
     * @throws IOException if reading fails
     */
    private String readLine(InputStream input) throws IOException {
        StringBuilder line = new StringBuilder();
        int character;

        while ((character = input.read()) != -1) {
            if (character == '\n') {
                return line.toString();
            }
            if (character != '\r') {
                line.append((char) character);
            }
        }

        throw new EOFException("Unexpected end of response");
    }

    /**
     * Sends an HTTP GET request and saves the page content.
     */
    public void getURL() {
        try (Socket socket = new Socket()) {

            // Connect with a 5-second timeout
            socket.connect(new InetSocketAddress(host, port), 5000);
            socket.setSoTimeout(10000);

            System.out.println("Connected to " + host + ":" + port);

            InputStream from = socket.getInputStream();
            PrintWriter to = new PrintWriter(socket.getOutputStream());

            // Send HTTP GET request
            to.print("GET " + path + " HTTP/1.1\r\n");
            to.print("Host: " + host + "\r\n");
            to.print("Connection: close\r\n");
            to.print("\r\n");
            to.flush();

            if (to.checkError()) {
                throw new IOException("Failed to send HTTP request");
            }

            System.out.println("HTTP GET request sent successfully");

            // Check HTTP status
            String statusLine = readLine(from);
            System.out.println("Server response: " + statusLine);

            String[] parts = statusLine.split(" ", 3);

            if (parts.length < 2 || !parts[1].equals("200")) {
                System.out.println("HTTP error: " + statusLine);
                return;
            }

            // Skip HTTP headers
            boolean chunked = false;
            String header;

            while (!(header = readLine(from)).isEmpty()) {
                String lowerHeader = header.toLowerCase();

                if (lowerHeader.startsWith("transfer-encoding:")
                        && lowerHeader.contains("chunked")) {
                    chunked = true;
                }
            }

            // Save the page content
            try (OutputStream out = new FileOutputStream(filename)) {

                byte[] buffer = new byte[4096];
                long totalBytes = 0;

                if (chunked) {
                    while (true) {
                        String sizeLine = readLine(from);
                        int size = Integer.parseInt(
                            sizeLine.split(";", 2)[0].trim(), 16
                        );

                        if (size == 0) {
                            break;
                        }

                        int remaining = size;

                        while (remaining > 0) {
                            int bytesRead = from.read(
                                buffer, 0,
                                Math.min(buffer.length, remaining)
                            );

                            if (bytesRead == -1) {
                                throw new EOFException("Incomplete response");
                            }

                            out.write(buffer, 0, bytesRead);
                            totalBytes += bytesRead;
                            remaining -= bytesRead;
                        }

                        readLine(from); // Skip chunk ending
                    }

                } else {
                    int bytesRead;

                    while ((bytesRead = from.read(buffer)) != -1) {
                        out.write(buffer, 0, bytesRead);
                        totalBytes += bytesRead;
                    }
                }

                System.out.println("HTML saved to: " + filename);
                System.out.println("Total bytes saved: " + totalBytes);
            }

        } catch (IOException e) {
            System.out.println("Connection error: " + e.getMessage());
        } catch (IllegalArgumentException e) {
            System.out.println("Invalid HTTP response: " + e.getMessage());
        }
    }
}
