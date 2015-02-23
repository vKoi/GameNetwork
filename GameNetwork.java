import java.io.IOException;

public class GameNetwork {
	
	static boolean launch_client;
	int port_number;
	int hostname_arg;
	
	public GameNetwork() {
		launch_client = true;
		port_number = -1;
		hostname_arg = -1;
	}
	
	protected int findHostnameArg(String[] args) 
	{
		for(int i = 0; i < args.length; i++)
		{
			if(args[i].charAt(0) != '-')
				return i;
			else
				i++;
		}
		
		return -1;
	}
	
	public boolean parseArgs(String[] args)
	{
		try
		{
			for( int i= 0; i < args.length; i++)
			{
				if( args[i].charAt(0) != '-')
					return true;
				switch( args[i].charAt(1))
				{
				case 'l':
					if ( (i + 1) < args.length)
					{
						launch_client = false;
						i++;
						port_number = Integer.valueOf(args[i]);
					}
					else
						return false;
					break;
				default:
					return false;
				}
			}
		} catch (NumberFormatException e)
		{
			return false;
		}
	return true;
}

	public void startClient (String[] args) throws IOException
	{
		hostname_arg = findHostnameArg(args);
		if (hostname_arg == -1)
		{
			System.out.println("Host address missing");
			return;
		}
		if (hostname_arg + 1 < args.length)
		{
			try
			{
				port_number = Integer.valueOf(args[hostname_arg+1]);
			}
			catch(NumberFormatException e)
			{
				System.out.println( "Invalid port number: " + e.getMessage());
				port_number = -1;
				return;
			}
		}
		GameNetworkClient client = new GameNetworkClient(hostname_arg, port_number);
		client.communicate();
	}
	
	public void startServer(String[] args) throws IOException
	{
		GameNetworkServer server = new GameNetworkServer(args, port_number );
		server.communicate();
	}
	

	public static void main(String[] args){
		GameNetwork gamenetwork = new GameNetwork();
		
		if (args.length < 1) {
			System.out.println( "To Start a Lobby Enter the Following in the Command Line: -l [port number]");
			System.out.println( "To Connect to the Lobby Enter the Following in the Command Line: [hostname] [port number]");
			return;
		}
		
		if ( !gamenetwork.parseArgs(args))
		{
			System.out.println( "Invalid argument(s)");
			return;
		}
		
		try
		{
			if (GameNetwork.launch_client)
				gamenetwork.startClient(args);
			else
				gamenetwork.startServer(args);
		} catch (IOException e){
			System.out.println(e.getMessage());
		}
	}	
}
