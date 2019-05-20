package fr.ensisa.hassenforder.transportation.server.network;

import java.io.InputStream;

import fr.ensisa.hassenforder.network.BasicAbstractReader;
import fr.ensisa.hassenforder.transportation.kiosk.network.Protocol;
import fr.ensisa.hassenforder.transportation.server.model.Route;
import fr.ensisa.hassenforder.transportation.server.model.Subscription;
import fr.ensisa.hassenforder.transportation.server.model.Ticket;
import fr.ensisa.hassenforder.transportation.server.model.Urban;

public class KioskReader extends BasicAbstractReader {
	private long passId;
	private String ticketId;
	private Ticket ticket;
	private long cardId;
	private long id;
	
	public KioskReader(InputStream inputStream) {
		super (inputStream);
	}

	public void receive() {
		type = readInt ();
		switch (type) {
		case Protocol.REQUEST_GETINFO_CARD:
			passId=this.readLong();			
			this.type=Protocol.REPLY_INFO;
			break;
		case Protocol.REQUEST_CREATE:						
			this.type=Protocol.REPLY_CARD;			
			break;
		case Protocol.REQUEST_BUY_ROUTE:			
			passId=this.readLong();
			ticket=new Route(passId,this.readString(),this.readString(),this.readInt());
			this.type=Protocol.REPLY_PRICE;
			break;
		case Protocol.REQUEST_BUY_URBAN:
			passId=this.readLong();
			ticket=new Urban(passId,this.readInt());
			break;
		case Protocol.REQUEST_BUY_SUBSCRIPTION:
			passId=this.readLong();
			//ticket=new Subscription(passId,this.readInt());
			break;
		case Protocol.REQUEST_BUY_OK:
			id=this.readLong();
			cardId=this.readLong();
			this.type=Protocol.REPLY_OK;
			break;
		case Protocol.REQUEST_BUY_CANCEL:
			id=this.readLong();
			this.type=Protocol.REPLY_KO;
			break;
		default:
			break;
		}
	}
	public long getPassId() {
		return passId;
	}
	
	public String getTicketId() {
		return ticketId;
	}
	
	public Ticket getTicket() {
		return ticket;
	}
	public long getCardId() {
		return cardId;
	}
	public long getTransactionId() {		
		return id;
	}
	
}
