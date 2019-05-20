package fr.ensisa.hassenforder.transportation.terminal.network;

public class Protocol {

    public static final int TERMINAL_PORT = 8888;
    
    public static final int REQUEST_GETINFO_CARD =       0x0101;
    public static final int REQUEST_USE_ROUTE =       0x0102;
    public static final int REQUEST_USE_URBAN  =       0x0103;
    public static final int REQUEST_USE_SUBSCRIPTION =       0x0104;

    public static final int REPLY_OK =              0x0201;
    public static final int REPLY_KO =              0x0202;
    public static final int REPLY_INFO =            0x0203;

}
