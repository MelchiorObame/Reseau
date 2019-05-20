package fr.ensisa.hassenforder.transportation.terminal.network;

import java.io.OutputStream;

import fr.ensisa.hassenforder.network.BasicAbstractWriter;
import fr.ensisa.hassenforder.transportation.terminal.network.Protocol;

public class CommandWriter extends BasicAbstractWriter {

    public CommandWriter(OutputStream outputStream) {
        super(outputStream);
    }
    
    public void GETINFO_CARD(long cardId) {
    	this.writeInt(Protocol.REQUEST_GETINFO_CARD);
    	this.writeLong(cardId);    	
    }
    
    public void USE_ROUTE(String ticketId, int count) {
    	this.writeInt(Protocol.REQUEST_USE_ROUTE);
    	this.writeString(ticketId);
    	this.writeInt(count);
    }
    
    public void USE_URBAN(String ticketId, int count) {
    	this.writeInt(Protocol.REQUEST_USE_URBAN);
    	this.writeString(ticketId);
    	this.writeInt(count);
    }
    public void USE_SUBSCRIPTION(String ticketId, int count){
    	this.writeInt(Protocol.REQUEST_USE_SUBSCRIPTION);
    	this.writeString(ticketId);
    	this.writeInt(count);
    }
}

