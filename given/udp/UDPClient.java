/*
 * Created on 07-Sep-2004
 * @author bandara
 */

package udp;

import java.io.IOException;
import java.net.DatagramPacket;
import java.net.DatagramSocket;
import java.net.InetAddress;
import java.net.SocketException;
import java.net.UnknownHostException;

import common.MessageInfo;

/**
 * @author bandara
 *
 */
public class UDPClient {

	private DatagramSocket sendSoc;

	public static void main(String[] args) {
		InetAddress	serverAddr = null;
		int			recvPort;
		int 		countTo;

		// Get the parameters
		if (args.length < 3) {
			System.err.println("Arguments required: server name/IP, recv port, message count");
			System.exit(-1);
		}

		try {
			serverAddr = InetAddress.getByName(args[0]);
		} catch (UnknownHostException e) {
			System.out.println("Bad server address in UDPClient, " + args[0] + " caused an unknown host exception " + e);
			System.exit(-1);
		}
		recvPort = Integer.parseInt(args[1]);
		countTo = Integer.parseInt(args[2]);


		// TO-DO: Construct UDP client class and try to send messages
		UDPClient client = new UDPClient();
		client.testLoop(serverAddr, recvPort, countTo);
		client.sendSoc.close();
	}

	public UDPClient() {
		// TO-DO: Initialise the UDP socket for sending data
		try {
			sendSoc = new DatagramSocket();
		} catch(Exception e) {
			System.err.println("UDPClient constructor exception:");
			e.printStackTrace();
		}
	}

	private void testLoop(InetAddress serverAddr, int recvPort, int countTo) {
		int				tries = 1;

		// TO-DO: Send the messages to the server
		try {
			String message;
			MessageInfo msgInfo;
			for(int k = 1; k <= countTo; k++) {
				msgInfo = new MessageInfo(countTo, k);
				message = new String(msgInfo.toString());
				for(int j = 0; j < tries; j++) {
					this.send(message, serverAddr, recvPort);
				}
			}
		} catch(Exception e) {
			System.err.println("Error in testLoop():");
			e.printStackTrace();
		}
	}
	

	private void send(String payload, InetAddress destAddr, int destPort) {
		int				payloadSize;
		byte[]				pktData;
		DatagramPacket		pkt;
		
		try {
			// TO-DO: build the datagram packet and send it to the server
			payloadSize = payload.length();
			pktData = new byte[payloadSize];
			pktData = payload.getBytes();
			pkt = new DatagramPacket(pktData, payloadSize, destAddr, destPort);
			sendSoc.send(pkt);
		} catch (Exception e) {
			System.err.println("send() Error:");
			e.printStackTrace();
		}
	}
}
