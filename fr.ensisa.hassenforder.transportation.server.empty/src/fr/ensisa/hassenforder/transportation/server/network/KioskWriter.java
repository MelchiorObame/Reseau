package fr.ensisa.hassenforder.transportation.server.network;

import java.io.OutputStream;

import fr.ensisa.hassenforder.network.BasicAbstractWriter;
import fr.ensisa.hassenforder.transportation.kiosk.network.Protocol;
import fr.ensisa.hassenforder.transportation.server.model.Pass;
import fr.ensisa.hassenforder.transportation.server.model.Route;
import fr.ensisa.hassenforder.transportation.server.model.Subscription;
import fr.ensisa.hassenforder.transportation.server.model.Ticket;
import fr.ensisa.hassenforder.transportation.server.model.Transaction;
import fr.ensisa.hassenforder.transportation.server.model.Urban;

public class KioskWriter extends BasicAbstractWriter {

	public KioskWriter(OutputStream outputStream) {
		super (outputStream);
	}

    public void writePass(Pass pass ) {
    	this.writeInt(Protocol.REPLY_INFO);    	
    	this.writeLong(pass.getPassId());    	
    	this.writeString(pass.getDescription());
    	if(((Route) pass.getTickets().get(0)).getFrom()!=null) {
    		this.writeInt(1);
    		this.writeInt(pass.getTickets().size());
	    	for(int i=0;i<pass.getTickets().size();i++) {
	    		this.writeString(((Ticket) pass.getTickets().get(i)).getTicketId());
	        	this.writeString(((Route) pass.getTickets().get(i)).getFrom());
	        	this.writeString(((Route) pass.getTickets().get(i)).getTo());
	        	this.writeInt(((Route) pass.getTickets().get(i)).getCount());
	        	this.writeInt(((Route) pass.getTickets().get(i)).getUsed());  
	        }      	
    	}
    }	
		
	 public void createPass(long passId){
		this.writeInt(Protocol.REPLY_CARD);
		this.writeLong(passId);				
	 }	
	 public void createTransaction(Transaction transaction) {
		 this.writeInt(Protocol.REPLY_PRICE);
		 this.writeInt(transaction.getAmount());
		 this.writeLong(transaction.getId());
	 }
	 
	 public void createPayTransaction(long id) {
		 this.writeInt(Protocol.REPLY_OK);
		 this.writeLong(id);
	 }
	 
	 public void createCancelTransaction(boolean b) {
		 this.writeInt(Protocol.REPLY_KO);
		 this.writeBoolean(b);
	 }
}
