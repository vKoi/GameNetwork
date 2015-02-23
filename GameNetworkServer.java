import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.ServerSocket;
import java.net.Socket;
import java.io.BufferedReader;


public class GameNetworkServer {
	int port_number;
	ServerSocket server_socket;
	Socket client_socket;
	BufferedReader socket_input;
	BufferedReader data;
	PrintWriter socket_output;
	
	public GameNetworkServer(String[] args, int port) throws IOException
	{
		port_number = port;
		data = new BufferedReader(new InputStreamReader(System.in));
		System.out.println( "Connecting to port " + Integer.toString(port_number));
		ServerSocket server_socket = new ServerSocket(port_number);		
	}
	
	public void communicate() throws IOException
	{
		client_socket = server_socket.accept();
		socket_input = new BufferedReader( new InputStreamReader(client_socket.getInputStream()));
		socket_output = new PrintWriter(client_socket.getOutputStream(), true);
		System.out.println("Connected to client socket!");
		sendStdIn();
		socket_input.close();
		socket_output.close();
		
	}
	
	public void sendStdIn() throws IOException
	{
		String message = socket_input.readLine();
		message = data.readLine();
		System.out.println("Transmitting message to client!");
		socket_output.println(message);
		
	}

}
