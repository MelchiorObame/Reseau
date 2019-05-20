package fr.ensisa.hassenforder.transportation.kiosk.network;

import java.io.InputStream;
import fr.ensisa.hassenforder.network.BasicAbstractReader;

import fr.ensisa.hassenforder.transportation.kiosk.network.Protocol;
import fr.ensisa.hassenforder.transportation.kiosk.model.Pass;
import fr.ensisa.hassenforder.transportation.kiosk.model.Ticket;
import fr.ensisa.hassenforder.transportation.kiosk.model.Transaction;

public class KioskReader extends BasicAbstractReader {
	private Pass pass;
	private long passId;
	private long id;
	private int amount;
	private boolean cancel;
    public KioskReader(InputStream inputStream) {
        super(inputStream);
    }

    public void receive() {
        type = readInt();
        switch (type) {
        case Protocol.REPLY_INFO:        	
        	pass=new Pass(this.readLong(),this.readString());
        	int typeT=this.readInt();
			if(typeT==1){
				int size=this.readInt();				
				for(int i=0;i<size;i++){					
					Ticket ticket=new Ticket(this.readString(),this.readString(),this.readString(),this.readInt(),this.readInt());
					pass.addTicket(ticket);
				}
			}
        	break;
        case Protocol.REPLY_CARD:
        	this.passId=this.readLong();        	
        	break;	
        case Protocol.REPLY_PRICE:
			this.amount=this.readInt();
			this.id=this.readLong();
        	break;	
        case Protocol.REPLY_OK: 
			this.id=this.readLong();		
        	break;
        case Protocol.REPLY_KO:
			this.cancel=this.readBoolean();		
        	break;        
        default:break;
        }
    }
    
    public Pass getPass(){
    	return pass;
    }
    public long getId(){
    	return passId;
    }
	
	public long getid() {
		return id;
	}
	
	public int getAmount() {
		return amount;
	}
	
	public boolean getCancel() {
		return cancel;
	}
}
