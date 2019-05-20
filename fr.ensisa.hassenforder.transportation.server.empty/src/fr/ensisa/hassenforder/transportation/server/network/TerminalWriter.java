package fr.ensisa.hassenforder.transportation.server.network;

import java.io.OutputStream;

import fr.ensisa.hassenforder.network.BasicAbstractWriter;
import fr.ensisa.hassenforder.transportation.terminal.network.Protocol;
import fr.ensisa.hassenforder.transportation.server.model.Model;
import fr.ensisa.hassenforder.transportation.server.model.Pass;
import fr.ensisa.hassenforder.transportation.server.model.Route;
import fr.ensisa.hassenforder.transportation.server.model.Subscription;
import fr.ensisa.hassenforder.transportation.server.model.Ticket;
import fr.ensisa.hassenforder.transportation.server.model.Urban;

public class TerminalWriter extends BasicAbstractWriter {

	public TerminalWriter(OutputStream outputStream) {
		super (outputStream);
	}

	
    public void writePass(Pass pass ) {
    	this.writeInt(Protocol.REPLY_INFO);    	
    	this.writeLong(pass.getPassId());    	
    	this.writeString(pass.getDescription());
    	this.writeInt(pass.getTickets().size()); 
    	
    	for(int i=0;i<pass.getTickets().size();i++) {
    		this.writeString(((Ticket) pass.getTickets().get(i)).getTicketId());
    		if(pass.getTickets().get(i)instanceof Route) {    		
	    		this.writeInt(1);
	        	this.writeString(((Route) pass.getTickets().get(i)).getFrom());
	        	this.writeString(((Route) pass.getTickets().get(i)).getTo());
	        	this.writeInt(((Route) pass.getTickets().get(i)).getCount());
	        	this.writeInt(((Route) pass.getTickets().get(i)).getUsed());      
    		}
    		if(pass.getTickets().get(i)instanceof Urban) {
    			this.writeInt(2);;
    			this.writeInt(((Urban) pass.getTickets().get(i)).getUsed());
    			this.writeInt(((Urban) pass.getTickets().get(i)).getCount());
    		}
    		if(pass.getTickets().get(i)instanceof Subscription) {
    			this.writeInt(3);
    			this.writeString(((Subscription) pass.getTickets().get(i)).getMonth().toString());
    			this.writeInt(((Subscription) pass.getTickets().get(i)).getUsed());	
    		}
    		
    	}
 	}



	public void writeUse(boolean bool) {   //definit la reponse du use, ok ou ko
    	this.writeInt(Protocol.REPLY_OK);     //pas toujours ok.	
    	this.writeBoolean(bool);    	 
	}
}
