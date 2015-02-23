import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.InetAddress;
import java.net.Socket;
import java.io.BufferedReader;


public class GameNetworkClient {
	int port_number;
	Socket client_socket;
	InetAddress host;
	BufferedReader data_input;
	BufferedReader sock_input;
	PrintWriter sock_output;
	
	public GameNetworkClient( int hostname_arg, int port) throws IOException
	{
		port_number = port;
		host = InetAddress.getLoopbackAddress();
		data_input = new BufferedReader( new InputStreamReader(System.in));
		System.out.println("Connecting to port " + Integer.toString(port_number));
		client_socket = new Socket(host, port);
		sock_input = new BufferedReader(new InputStreamReader(client_socket.getInputStream()));
		sock_output = new PrintWriter(client_socket.getOutputStream(), true);
	}
	
	public void communicate() throws IOException
	{
		sendStdIn();
		sock_input.close();
		sock_output.close();
	}
	
	public void sendStdIn() throws IOException
	{
		String message= data_input.readLine();
		sock_output.println(message);
		message = sock_input.readLine();
		System.out.println(message);
	}

}
