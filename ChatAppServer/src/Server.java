import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.ArrayList;

public class Server {
	
	ServerSocket server_socket;
	Socket client_socket;
	ArrayList<RunClient> clients;
	
	public Server(int port){
		this.clients = new ArrayList<RunClient>();
		this.server_socket = null;
		try {
			info("Listening on port " + String.valueOf(port));
			this.server_socket = new ServerSocket(port);
			while (true){
				this.clients.add(new RunClient(this.server_socket.accept(), this));
				new Thread(this.clients.get(this.clients.size() - 1)).start();
			}
		} catch (IOException e) {
			//e.printStackTrace();
			fatal_error("Socket could not be created");
		}
		
	}
	
	public void dispatch_message(String username, String message) {
		for (RunClient client: clients) {
			if (client.getUserName().compareTo(username) != 0) { // Dispatch message to everyone except user who sent it
				client.sendMessage(message);
			}
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

