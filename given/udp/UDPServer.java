/*
 * Created on 07-Sep-2004
 * @author bandara
 */
package udp;

import java.io.IOException;
import java.net.DatagramPacket;
import java.net.DatagramSocket;
import java.net.SocketException;
import java.net.SocketTimeoutException;
import java.util.Arrays;

import common.MessageInfo;

/**
 * @author bandara
 *
 */
public class UDPServer {

	private DatagramSocket recvSoc;
	private int totalMessages = -1;
	private int[] receivedMessages;
	private boolean close;

	private void run() {
		// int				pacSize;
		byte[]			pacData;
		DatagramPacket 	pac;
		close = false;

		// TO-DO: Receive the messages and process them by calling processMessage(...).
		//        Use a timeout (e.g. 30 secs) to ensure the program doesn't block forever

		while(!close) {
			try {
				pacData = new byte[256];
				pac = new DatagramPacket(pacData, pacData.length);
				recvSoc.receive(pac);
				processMessage(new String(pac.getData(), 0, pac.getLength()-1));
			} catch(Exception e) {
				System.err.println("Waited on receive too long.");
				System.err.print("Unreceived messages are: ");
				for(int k = 0; k < totalMessages; k++) {
					if(receivedMessages[k] == 0) {
						System.err.print(k + ", ");
					}
				}
				System.err.println("and that is all.");
				close = true;
			}
		}
		recvSoc.close();
	}

	public void processMessage(String data) {

		MessageInfo msg = null;

		try {
			// TO-DO: Use the data to construct a new MessageInfo object
			msg = new MessageInfo(data);
	
			// TO-DO: On receipt of first message, initialise the receive buffer
			if(totalMessages == -1) {
				totalMessages = msg.totalMessages;
				receivedMessages = new int[totalMessages];
				Arrays.fill(receivedMessages, 0);
			}
	
			// TO-DO: Log receipt of the message
			receivedMessages[msg.messageNum-1]++;
			System.out.println("MESSAGE RECEIVED: " + msg.messageNum);
	
			// TO-DO: If this is the last expected message, then identify
			//        any missing messages
			if(msg.messageNum == totalMessages) {
				System.out.print("Unreceived messages are: ");
				for(int k = 0; k < totalMessages; k++) {
					if(receivedMessages[k] == 0) {
						System.out.print(k + ", ");
					}
				}
				System.out.println("and that is all.");
			
			// Restore to uninitiated value.
				totalMessages = -1;
				this.close = true;
			}
		}	catch(Exception e) {
			System.err.println("ProcessMessage Error:");
			e.printStackTrace();
		}
	}


	public UDPServer(int rp) {
		// TO-DO: Initialise UDP socket for receiving data
		try {
			recvSoc = new DatagramSocket(rp);
			recvSoc.setSoTimeout(30000); // Set the timeout period to 10 seconds.

			// Done Initialisation
			System.out.println("UDPServer ready");
		} catch (Exception e) {
			System.err.println("Datagram Socket creation error:");
			e.printStackTrace();
		}
	}

	public static void main(String args[]) {
		int	recvPort;

		// Get the parameters from command line
		if (args.length < 1) {
			System.err.println("Arguments required: recv port");
			System.exit(-1);
		}
		recvPort = Integer.parseInt(args[0]);

		// TO-DO: Construct Server object and start it by calling run().
		UDPServer udpServer = new UDPServer(recvPort);
		udpServer.run();
	}

}
