package fr.ensisa.hassenforder.transportation.server.network;

import java.io.IOException;
import java.net.Socket;

import fr.ensisa.hassenforder.transportation.server.NetworkListener;
import fr.ensisa.hassenforder.transportation.server.model.Pass;
import fr.ensisa.hassenforder.transportation.terminal.network.Protocol;


public class TerminalSession extends Thread {
	private Socket connection;
	private NetworkListener listener;
	private long passId;///TEST
	
	public TerminalSession(Socket connection, NetworkListener listener) {
		this.connection = connection;
		this.listener = listener;
		if( listener == null) throw new RuntimeException("listener cannot be null");
	}

	public void close () {
		this.interrupt();
		try {
			if (connection != null)
				connection.close();
		} catch (IOException e) {
		}
		connection = null;
	}

	public boolean operate() {
		try {
			TerminalWriter writer = new TerminalWriter (connection.getOutputStream());
			TerminalReader reader = new TerminalReader (connection.getInputStream());
			reader.receive ();
			switch (reader.getType ()) {				
				case Protocol.REPLY_INFO:
					passId=reader.getPassId();
					writer.writePass(listener.terminalFetchPass(passId));					
					writer.send ();
					break;
				case Protocol.REPLY_OK:
					writer.writeUse(listener.terminalUseTicket(passId,reader.getTicketId(),reader.getCount()));
					writer.send ();
					break;	
				case 0 : return false; // socket closed	
			}		
			return true;
		} catch (IOException e) {
			return false;
		}
	}

	public void run() {
		while (true) {
			if (! operate())
				break;
		}
		try {
			if (connection != null) connection.close();
		} catch (IOException e) {
		}
	}

}
