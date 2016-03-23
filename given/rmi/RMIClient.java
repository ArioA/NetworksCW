/*
 * Created on 07-Sep-2004
 * @author bandara
 */
package rmi;

import java.rmi.Naming;
import java.rmi.NotBoundException;
import java.rmi.RemoteException;
import java.rmi.registry.LocateRegistry;
import java.rmi.registry.Registry;

import common.MessageInfo;

/**
 * @author bandara
 *
 */
public class RMIClient {

	public static void main(String[] args) {

		RMIServerI iRMIServer = null;

		// Check arguments for Server host and number of messages
		if (args.length < 2){
			System.out.println("Needs 2 arguments: ServerHostName/IPAddress, TotalMessageCount");
			System.exit(-1);
		}

		String urlServer = new String("rmi://" + args[0] + "/RMIServer");
		int numMessages = Integer.parseInt(args[1]);

		// TO-DO: Initialise Security Manager

		if(System.getSecurityManager() == null) {
			System.setSecurityManager(new SecurityManager());
		}

		// TO-DO: Bind to RMIServer

		try {
			String name = "ArioServer";
			Registry registry = LocateRegistry.getRegistry(urlServer);
			RMIServerI server = (RMIServerI) registry.lookup(name);
			

		// TO-DO: Attempt to send messages the specified number of times

			for(int k = 0; k < numMessages; k++) {
				MessageInfo msg = new MessageInfo(k + 1, numMessages);
				server.receiveMessage(msg);
			}

		} catch (Exception e) {
			System.err.println("Client Error:");
			e.printStackTrace();
		}

	}
}
