package be.heh.automation.PlcThread;

import be.heh.automation.Simatic_S7.S7;
import be.heh.automation.Simatic_S7.S7Client;
import be.heh.automation.domain.Plc;

public class PlcConnection {

    private Plc plc;
    private S7Client s7Client;

    public PlcConnection(Plc plc) {
        this.plc = plc;
        s7Client = new S7Client();
        s7Client.SetConnectionType(S7.S7_BASIC);
    }

    public int open(){
        int isOpen = s7Client.ConnectTo(plc.getIp(),plc.getRack(),plc.getSlot());
        return isOpen;
    }

    public void close(){
        s7Client.Disconnect();
    }

    public Plc getPlc() {
        return plc;
    }

    public void setPlc(Plc plc) {
        this.plc = plc;
    }

    public S7Client getS7Client() {
        return s7Client;
    }

    public void setS7Client(S7Client s7Client) {
        this.s7Client = s7Client;
    }
}
