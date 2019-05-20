package fr.ensisa.hassenforder.transportation.kiosk.network;

import java.io.IOException;
import java.net.Socket;

import fr.ensisa.hassenforder.transportation.kiosk.model.Pass;
import fr.ensisa.hassenforder.transportation.kiosk.model.Transaction;

public class KioskSession implements ISession {

    private Socket connection;

    public KioskSession() {
    }

    @Override
    synchronized public boolean close() {
        try {
            if (connection != null) {
                connection.close();
            }
            connection = null;
        } catch (IOException e) {
        }
        return true;
    }

    @Override
    synchronized public boolean open() {
        this.close();
        try {
            connection = new Socket("localhost", Protocol.KIOSK_PORT);
            return true;
        } catch (IOException e) {
            return false;
        }
    }

	@Override
	public long createPass() {
        try {
			KioskWriter writer = new KioskWriter (connection.getOutputStream());
			writer.CREATE_CARD();
			writer.send ();
			KioskReader reader = new KioskReader (connection.getInputStream());
			reader.receive();
			return reader.getId();
        } catch (IOException e) {
            return -1L;
        }
	}

	@Override
	public Pass getPassById(long passId) {
        try {
			KioskWriter writer = new KioskWriter (connection.getOutputStream());
			writer.GETINFO_CARD(passId);;
			writer.send ();
			KioskReader reader = new KioskReader (connection.getInputStream());
			reader.receive();
			return reader.getPass();
        } catch (IOException e) {
            return null;
        }
	}

	@Override
	public Transaction buyRoute(long passId, String from, String to, int count) {
        try {
			KioskWriter writer = new KioskWriter (connection.getOutputStream());
			writer.BUY_ROUTE(passId,from,to,count);
			writer.send ();
			KioskReader reader = new KioskReader (connection.getInputStream());
			reader.receive();
			Transaction transaction=new Transaction(reader.getid(),reader.getAmount());
			return transaction;
        } catch (IOException e) {
            return null;
        }
	}

	@Override
	public Transaction buyUrban(long passId, int count) {
        try {
        	if (true != Boolean.TRUE) throw new IOException ();
            return null;
        } catch (IOException e) {
            return null;
        }
	}

	@Override
	public Transaction buySubscription(long passId, int month) {
        try {
        	if (true != Boolean.TRUE) throw new IOException ();
            return null;
        } catch (IOException e) {
            return null;
        }
	}

	@Override
	public boolean cancelTransaction(long id) {
        try {
			KioskWriter writer = new KioskWriter (connection.getOutputStream());
			writer.BUY_CANCEL(id);
			writer.send ();
			KioskReader reader = new KioskReader (connection.getInputStream());
			reader.receive();
			return reader.getCancel();

        } catch (IOException e) {
    		return false;
        }
	}

	@Override
	public long payTransaction(long id, long cardId) {
        try {
			KioskWriter writer = new KioskWriter (connection.getOutputStream());
			writer.BUY_OK(id,cardId);
			writer.send ();
			KioskReader reader = new KioskReader (connection.getInputStream());
			reader.receive();
			return reader.getid();

        } catch (IOException e) {
    		return -1;
        }
	}

 }
