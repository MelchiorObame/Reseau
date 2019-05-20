package fr.ensisa.hassenforder.transportation.server.network;

import java.io.InputStream;

import fr.ensisa.hassenforder.network.BasicAbstractReader;
import fr.ensisa.hassenforder.transportation.server.model.Route;
import fr.ensisa.hassenforder.transportation.terminal.network.Protocol;

public class TerminalReader extends BasicAbstractReader {
	private long passId;
	private String ticketId;
	private int count;
	public TerminalReader(InputStream inputStream) {
		super (inputStream);
	}

	public void receive() {
		type =readInt ();		
		switch (type) {
		case Protocol.REQUEST_GETINFO_CARD:  // SI IL LIT getInfo, le type de retour sera replyInfo
			passId=this.readLong();			
			this.type=Protocol.REPLY_INFO;  //
			break;
		case Protocol.REQUEST_USE_ROUTE:
			this.ticketId=this.readString();
			//System.out.println("usereceive_:"+ticketId);
			this.count=this.readInt();
			this.type=Protocol.REPLY_OK;
			break;
		case Protocol.REQUEST_USE_URBAN:
			this.ticketId=this.readString();
			this.count=this.readInt();
			this.type=Protocol.REPLY_OK;	
			
			break;
		case Protocol.REQUEST_USE_SUBSCRIPTION:
			this.ticketId=this.readString();
			this.count=this.readInt();
			this.type=Protocol.REPLY_OK;	
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
	
	public int getCount() {
		return count;
	}
}
