import java.net.URL;
import java.net.URI;
import java.net.Socket;
public class HttpClient {

    // Step 4: Attributes
    static String protocol;
    static String host;
    static String path;
    static String filename;
    static int port;

    // Step 3: Main method
   public static void main(String[] args) {
    if (args.length == 0) {
        System.out.println("Please provide a URL");
        return;
    }

    readURL(args[0]);
    getURL();
}

    // Step 3: readURL method
   public static void readURL(String in) {
    try {
        URL url = URI.create(in).toURL();

        protocol = url.getProtocol();
        host = url.getHost();
        port = url.getPort();

        if (port == -1) {
            port = url.getDefaultPort();
        }

        path = url.getPath();

        if (path.isEmpty()) {
            path = "/";
        }

        filename = path.substring(path.lastIndexOf('/') + 1);

        if (filename.isEmpty()) {
            filename = "index.html";
        }

        System.out.println("Protocol: " + protocol);
        System.out.println("Host: " + host);
        System.out.println("Port: " + port);
        System.out.println("Path: " + path);
        System.out.println("Filename: " + filename);

    } catch (Exception e) {
        System.out.println("Error: " + e.getMessage());
    }
}

    // Step 3: getURL method
    public static void getURL() {
    try {
        Socket socket = new Socket(host, port);

        System.out.println("Connected to " + host + ":" + port);

        socket.close();

    } catch (Exception e) {
        System.out.println("Connection error: " + e.getMessage());
    }
}
}