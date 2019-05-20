package fr.ensisa.hassenforder.transportation.server.network;

import java.io.IOException;
import java.net.Socket;

import fr.ensisa.hassenforder.transportation.kiosk.network.Protocol;
import fr.ensisa.hassenforder.transportation.server.NetworkListener;
import fr.ensisa.hassenforder.transportation.server.model.Pass;
import fr.ensisa.hassenforder.transportation.server.model.Ticket;
import fr.ensisa.hassenforder.transportation.server.model.Transaction;

public class KioskSession extends Thread {

	private Socket connection;
	private NetworkListener listener;
	private long passId;
	private Transaction transaction;
	
	public KioskSession(Socket connection, NetworkListener listener) {
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
			KioskWriter writer = new KioskWriter (connection.getOutputStream());
			KioskReader reader = new KioskReader (connection.getInputStream());
			reader.receive ();
			switch (reader.getType ()) {
				case Protocol.REPLY_INFO:
					passId=reader.getPassId();
					writer.writePass(listener.kioskFetchPass(passId));					
					writer.send ();
					break;
				case Protocol.REPLY_CARD:
					passId=listener.kioskCreatePass();		
					writer.createPass(passId);
					writer.send();					
					break;	
				case Protocol.REPLY_PRICE:					
					 transaction=listener.kioskCreateTransaction(reader.getTicket());
					 writer.createTransaction(transaction);
					 writer.send();
					break;
				case Protocol.REPLY_OK:					
					writer.createPayTransaction(listener.kioskPayTransaction(reader.getTransactionId(), reader.getCardId()));
					writer.send();
					break;
				case Protocol.REPLY_KO:					
					writer.createCancelTransaction(listener.kioskCancelTransaction(reader.getTransactionId()));
					writer.send();
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
