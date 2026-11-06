\# HTTP Client — Java



\## Project Description



This project is part of our HTTP networking lab at ENSEA. The goal is to create a simple Java HTTP client using TCP sockets.



The program takes a URL as a command-line argument, connects to a web server, sends an HTTP GET request, and saves the web page in a local file.



\## Technologies



Java, TCP sockets, HTTP/1.1, Git.



\## How to Run



Compile the Java program:



```bash

javac HttpClient.java

```



Run the program:



```bash

java HttpClient http://neverssl.com/

```



\## Current Progress



The program can read a URL and extract the protocol, host, port, path, and filename.



The TCP connection has also been implemented, but the connection test returned a timeout error. The HTTP request and file download are not yet completed.

