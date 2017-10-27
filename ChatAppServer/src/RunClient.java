import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.io.PrintWriter;
import java.net.Socket;

public class RunClient implements Runnable{
	BufferedReader is;
	PrintWriter os;
	Socket client_socket;
	Server server; //Handle back to the server
	private String username;
	
	public RunClient(Socket client_socket, Server server){
		this.client_socket = client_socket;
		
		try{
			this.os = new PrintWriter(this.client_socket.getOutputStream());
			this.is = new BufferedReader(new InputStreamReader(this.client_socket.getInputStream()));
		} catch (IOException e) {
			fatal_error("Could not open connection with client.");
		}
		
		this.server = server;
		
		info("Connected with client " + String.valueOf(this.client_socket.getInetAddress()));
		
	}
	
	public void fatal_error(String message){
		System.err.println(message);
		System.exit(0);
	}
	
	public void info(String message){
		System.out.println("[+] " + message);
	}
	
	public void sendMessage(String message) {
		this.os.write(message);
		this.os.flush();
	}
	
	public String getUserName() {
		return this.username;
	}

	@Override
	public void run() {
		String message = "";
		try {
			
			while ((message = is.readLine()) != null){
				
				if (message.startsWith("username:")) {
					
					this.username = message.split(":")[1];
					info(this.username + " connected.");
				
				}
				
				info("Message from client : " + message);
				this.server.dispatch_message(this.username, message);
			}
			
		} catch (IOException e) {
			//e.printStackTrace();
		}
	}

}
