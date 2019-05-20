package fr.ensisa.hassenforder.transportation.terminal.network;

import java.io.InputStream;
import fr.ensisa.hassenforder.network.BasicAbstractReader;

import fr.ensisa.hassenforder.transportation.terminal.network.Protocol;
import fr.ensisa.hassenforder.transportation.terminal.model.Pass;
import fr.ensisa.hassenforder.transportation.terminal.model.Ticket;

public class CommandReader extends BasicAbstractReader {
	private Pass pass;
	private boolean use;
	
    public CommandReader(InputStream inputStream) {
        super(inputStream);
    }

    public void receive() {
        type =readInt();       
        switch (type) {
        case Protocol.REPLY_INFO:        	
        	pass=new Pass(this.readLong(),this.readString());
        	int n=this.readInt();
        	for(int i=0;i<n;i++){
        		String ticketId=this.readString();
        		int b=this.readInt();
        		if(b==1) {     
	        		Ticket ticket=new Ticket(ticketId,this.readString(),this.readString(),this.readInt(),this.readInt());
	        		pass.addTicket(ticket);
        		}
	        		
        		if(b==2) {
        			Ticket ticket=new Ticket(ticketId,this.readInt(),this.readInt());
	        		pass.addTicket(ticket);
        		}
        		if(b==3) {
        			Ticket ticket=new Ticket(ticketId,Ticket.Month.valueOf(this.readString()),this.readInt());
	        		pass.addTicket(ticket);
        		}       		
        	}
        	break;
        case Protocol.REPLY_OK:        	
        	this.use=this.readBoolean();        	
        	break;
        case Protocol.REPLY_KO:
        	this.use=false;
        	break;        
        default:break;
        }
    }
    
 
    public Pass getPass(){
    	return pass;
    }
    
    public boolean getUse(){
    	return use;
    }
}
