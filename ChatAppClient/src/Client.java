import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.Socket;
import java.util.Scanner;

public class Client {
	BufferedReader is;
	PrintWriter os;
	Socket client_socket;
	Scanner s;
	String username;
	
	public Client(String host, int port){
		info("Attempting connection to " + host + " on port "  + String.valueOf(port) + "...");
		this.s = new Scanner(System.in);
		try {
			this.client_socket = new Socket(host, port);
			this.is = new BufferedReader(new InputStreamReader(this.client_socket.getInputStream()));
			this.os = new PrintWriter(this.client_socket.getOutputStream());
		} catch (IOException e) {
			// TODO Auto-generated catch block
			//e.printStackTrace();
			fatal_error("Failed to open connection.");
		}
		info("Connected with server " + this.client_socket.getInetAddress());
		
		System.out.println("output your username : ");
		username = this.s.nextLine();
		
		this.os.write(username + "\n");
		this.os.flush();
		
		String message = "";
		while(message.trim().compareToIgnoreCase("quit") != 0){
			message = this.s.nextLine();
			this.os.write(username + message + "\n");
			this.os.flush();
			info("Sent message " + message + " to server.");
		}
	}
	
	public void fatal_error(String message){
		System.err.println(message);
		System.exit(0);
	}
	
	public void info(String message){
		System.out.println("[+] " + message);
	}
}
