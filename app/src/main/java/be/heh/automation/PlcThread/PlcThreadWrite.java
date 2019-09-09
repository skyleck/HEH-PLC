package be.heh.automation.PlcThread;

import be.heh.automation.Simatic_S7.S7;
import be.heh.automation.Simatic_S7.S7Client;
import be.heh.automation.domain.Plc;

public class PlcThreadWrite  implements Runnable{

    private S7Client s7Client;

    byte[] data;

    public PlcThreadWrite(byte[] data) {
        this.data = data;
    }

    @Override
    public void run() {
        int isConnected = 0;

        Plc plc = new Plc("192.168.1.133",0,2);

        PlcConnection plcConnection = new PlcConnection(plc);
        isConnected = plcConnection.open();

        s7Client = plcConnection.getS7Client();

        while (isConnected == 0){
            s7Client.WriteArea(S7.S7AreaDB,5,0,34,data);
            try {
                Thread.sleep(100);
            }catch (InterruptedException e) {
                e.printStackTrace();
            }
        }
    }

    public void setWrite(int db, byte value){
        data[db] = value;
    }

    public void setWrite(int db, int value){
        S7.SetWordAt(data,db,value);
    }
}
