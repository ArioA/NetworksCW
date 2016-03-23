/*
 * Created on 07-Sep-2004
 * @author bandara
 */
package rmi;

import java.net.MalformedURLException;
import java.rmi.Naming;
import java.rmi.registry.LocateRegistry;
import java.rmi.registry.Registry;
import java.rmi.RemoteException;
import java.rmi.server.UnicastRemoteObject;
import java.util.Arrays;

import common.*;
import rmi.RMIServerI;

/**
 * @author bandara
 *
 */
public class RMIServer implements RMIServerI {

	private int totalMessages = -1;
	private int[] receivedMessages;

	public RMIServer() throws RemoteException {
		super();
	}

	public void receiveMessage(MessageInfo msg) throws RemoteException {

		// TO-DO: On receipt of first message, initialise the receive buffer
		if(totalMessages == -1) {

			System.out.println("FIRST IF");

			totalMessages = msg.totalMessages;
			receivedMessages = new int[totalMessages];
			for(int k=0; k < totalMessages; k++) {
				receivedMessages[k] = 0;
			}
		}
	
		// TO-DO: Log receipt of the message

		receivedMessages[msg.messageNum - 1]++;

		System.out.println("NOTED:" + msg.messageNum);		

		// TO-DO: If this is the last expected message, then identify
		//        any missing messages

		if(msg.messageNum == totalMessages) {
			System.out.print("Missing messages are numbers: ");
	
			for(int k = 0; k < totalMessages; k++) {
				if(receivedMessages[k] == 0) {
					System.out.print(k + ", ");
				}
			}

			System.out.print("and that's all. \n");
			totalMessages = -1;
		}
	}


	public static void main(String[] args) {

		// TO-DO: Initialise Security Manager

		if(System.getSecurityManager() == null) {
			System.setSecurityManager(new SecurityManager());
		}

		try {
			String name = "ArioServer";
			RMIServerI server = new RMIServer();
			RMIServerI stub = (RMIServerI) UnicastRemoteObject.exportObject(server, 0);
			Registry registry = LocateRegistry.getRegistry();
			registry.rebind(name, stub);
			System.out.println("Server Bound!");
		} catch (Exception e) {
			System.err.println("Server Error:");
			e.printStackTrace();
		}

		// TO-DO: Instantiate the server class

		// TO-DO: Bind to RMI registry

	}
/*
	protected static void rebindServer(String serverURL, RMIServer server) {

		// TO-DO:
		// Start / find the registry (hint use LocateRegistry.createRegistry(...)
		// If we *know* the registry is running we could skip this (eg run rmiregistry in the start script)

		// TO-DO:
		// Now rebind the server to the registry (rebind replaces any existing servers bound to the serverURL)
		// Note - Registry.rebind (as returned by createRegistry / getRegistry) does something similar but
		// expects different things from the URL field.
	}
*/
}
