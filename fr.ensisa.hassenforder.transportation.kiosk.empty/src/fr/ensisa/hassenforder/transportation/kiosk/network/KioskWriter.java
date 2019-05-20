package fr.ensisa.hassenforder.transportation.kiosk.network;

import java.io.OutputStream;
import fr.ensisa.hassenforder.network.BasicAbstractWriter;
import fr.ensisa.hassenforder.transportation.kiosk.network.Protocol;

public class KioskWriter extends BasicAbstractWriter {

    public KioskWriter(OutputStream outputStream) {
        super(outputStream);
    }
    
    public void GETINFO_CARD(long passId) {
    	this.writeInt(Protocol.REQUEST_GETINFO_CARD);
    	this.writeLong(passId);    	
    }
    
    public void CREATE_CARD() {
    	this.writeInt(Protocol.REQUEST_CREATE);    	
    }    

    public void BUY_ROUTE(long passId, String from, String to, int count) {
		this.writeInt(Protocol.REQUEST_BUY_ROUTE);
		this.writeLong(passId);
		this.writeString(from);
		this.writeString(to);
		this.writeInt(count);
	}
    public void BUY_URBAN(long passId, int count) {
		this.writeInt(Protocol.REQUEST_BUY_URBAN);
		this.writeLong(passId);
		this.writeInt(count);
	}
    public void BUY_SUBSCRIPTION(long passId, int month) {
		this.writeInt(Protocol.REQUEST_BUY_SUBSCRIPTION);
		this.writeLong(passId);
		this.writeInt(month);
	}
    public void	BUY_OK(long id, long cardId) {
		this.writeInt(Protocol.REQUEST_BUY_OK);
		this.writeLong(id);
		this.writeLong(cardId);
	}	
    public void	BUY_CANCEL(long id) {
		this.writeInt(Protocol.REQUEST_BUY_CANCEL);
		this.writeLong(id);
	}
    
}
