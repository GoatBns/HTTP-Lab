# HTTP Client — Java Networking Lab

## 1. Project Description

This project is a simple HTTP client developed in Java as part of our Networking Lab at ENSEA.

The program uses a TCP socket to connect to a web server, send an HTTP GET request, receive the server's response, and save the web page content to a local file.

The URL is provided as a command-line argument.

## 2. Features

The HTTP client can:

- Read and extract information from a URL (host, port, path and filename).
- Establish a TCP connection with a web server.
- Send an HTTP/1.1 GET request.
- Read and check the HTTP status code.
- Display an error if the status code is not `200 OK`.
- Skip HTTP response headers and save only the page content.
- Handle normal and chunked HTTP responses.
- Close the socket and file automatically.
- Handle invalid URLs and connection errors.

## 3. Requirements

- Java JDK (tested with OpenJDK 25)
- A terminal (PowerShell on Windows)
- Internet connection
- Git (optional, for version control)

No external Java libraries are required.

## 4. How to Run

First, clone the GitHub repository:

```bash
git clone https://github.com/GoatBns/HTTP-Lab.git
cd HTTP-Lab
```

### Option 1 — Compile and Run the Java Code

Compile the Java program:

```bash
javac HttpClient.java
```

Run the program with a URL:

```bash
java HttpClient http://example.com/
```

### Option 2 — Run the Executable JAR File

The project also includes an executable JAR file, `HttpClient.jar`.

It allows us to run the HTTP client without manually compiling the Java source code.

To execute it, use:

```bash
java -jar HttpClient.jar http://example.com/
```

We tested the JAR successfully. The program connected to the server, received an HTTP `200 OK` response, and saved 577 bytes of HTML content.

In both cases, the downloaded page is saved as `index.html` in the current directory.

## 5. Example Output

When running the executable JAR with `http://example.com/`, we obtained:

```text
Protocol: http
Host: example.com
Port: 80
Path: /
Filename: index.html
Connected to example.com:80
HTTP GET request sent successfully
Server response: HTTP/1.1 200 OK
HTML saved to: index.html
Total bytes saved: 577
```

The result confirms that the TCP connection was established, the HTTP request succeeded, and the HTML content was saved locally.

The file can be opened using a text editor or web browser.

## 6. Code Organization

The program uses four main methods:

| Method | Description |
|---|---|
| `main()` | Reads the URL argument and starts the client |
| `readURL()` | Extracts the connection information from the URL |
| `readLine()` | Reads one line of the HTTP response |
| `getURL()` | Connects to the server, sends the request and saves the response |

Connection attributes are private, and the code uses `try-with-resources` to close resources automatically.

## 7. Testing

We tested the program with different inputs.

| Test | Expected behavior |
|---|---|
| `http://example.com/` | Downloads the HTML page |
| `https://example.com/` | Displays an unsupported protocol error |
| No URL argument | Displays a usage message |
| Invalid URL | Displays an error message |
| HTTP status other than 200 | Reports the HTTP error |

We also tested the executable JAR file using:

```bash
java -jar HttpClient.jar http://example.com/
```

The JAR executed successfully and downloaded the HTML page.

We used Wireshark to observe the network communication, including DNS resolution, TCP connection establishment, the HTTP GET request, the server response, and connection termination.

## 8. Limitations

This is a basic HTTP client developed for learning purposes.

It supports plain HTTP only, not HTTPS. It does not automatically follow HTTP redirects.

The program handles basic HTTP responses, including chunked transfer encoding, but it does not implement every feature of the HTTP protocol.

## 9. HTTP Servers — Nginx and Tomcat

In the second part of the lab, we installed and tested two HTTP servers: Nginx and Apache Tomcat.

### 9.1 Nginx

We installed Nginx and tested the server by accessing `http://localhost` in a web browser.

We also examined the Nginx configuration files to identify the document root and created a simple HTML5 page to test the server.

This allowed us to understand how Nginx serves static web pages.

### 9.2 Apache Tomcat

We installed Apache Tomcat and accessed the server through a web browser.

We then deployed the sample web application provided by Apache Tomcat and tested it.

We also modified the JSP file `hello.jsp` to display the current date and time, showing how Tomcat generates dynamic web content.

### 9.3 Nginx and Tomcat

Nginx is mainly used as a web server for static content and can also work as a reverse proxy.

Tomcat is a Java application server that runs Java Servlets and JSP pages to generate dynamic content.

They can work together, with Nginx receiving HTTP requests and forwarding dynamic requests to Tomcat.

## 10. Conclusion

This lab helped us understand HTTP communication from both the client and server sides.

We developed a Java HTTP client using TCP sockets, created and tested an executable JAR file, analyzed network traffic with Wireshark, and worked with Nginx and Tomcat to understand how static and dynamic web pages are served.