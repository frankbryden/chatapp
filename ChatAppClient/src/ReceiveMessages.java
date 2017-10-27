import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;

public class ReceiveMessages implements Runnable{
	BufferedReader bf;
	Boolean running;
	public ReceiveMessages(InputStream is){
		this.bf = new BufferedReader(new InputStreamReader(is));
		this.running = true;
	}
	@Override
	public void run() {
		while (this.running){
			try {
				System.out.println(this.bf.readLine());
			} catch (IOException e) {
				System.err.println("[RecMessages] Failed to read input stream.");
			}
		}
	}
	
	public void finish(){
		this.running = false;
	}
}
