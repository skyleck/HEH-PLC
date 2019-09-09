package be.heh.automation.PlcThread;

import android.app.Notification;
import android.os.Bundle;
import android.support.v4.app.NotificationCompat;
import android.util.Log;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.Switch;
import android.os.Handler;
import android.os.Message;
import android.widget.TextView;

import be.heh.automation.Simatic_S7.S7;
import be.heh.automation.Simatic_S7.S7Client;
import be.heh.automation.Simatic_S7.S7CpuInfo;
import be.heh.automation.Simatic_S7.S7OrderCode;
import be.heh.automation.domain.Plc;

public class PlcThreadRead implements Runnable {

    private LinearLayout linearLayout;
    private Class enumClass;
    private Handler handler;
    TextView plcInfo;

    private S7Client s7Client;
    byte data[];

    public PlcThreadRead(LinearLayout linearLayout, Class enumClass,
                         Handler handler,TextView plcInfo,byte[] data){
        this.linearLayout = linearLayout;
        this.enumClass = enumClass;
        this.handler = handler;
        this.plcInfo = plcInfo;
        this.data = data;
    }


    @Override
    public void run() {
        int countChild = linearLayout.getChildCount();
        int isConnected = 0;

        Plc plc = new Plc("192.168.1.133",0,2);

        PlcConnection plcConnection = new PlcConnection(plc);
        isConnected = plcConnection.open();

        s7Client = plcConnection.getS7Client();
        if(isConnected == 0) {
            S7OrderCode orderCode = new S7OrderCode();
            s7Client.GetOrderCode(orderCode);
            plcInfo.setText(orderCode.Code().toString().substring(5, 8));
        }

        while (isConnected == 0) {
            isConnected = s7Client.ReadArea(S7.S7AreaDB,5,0,30,data);
            for (int i = 0; i < countChild; i++) {
                LinearLayout ll = (LinearLayout) linearLayout.getChildAt(i);
                View v = ll.getChildAt(1);
                IEnumPlc enumPillsDB = (IEnumPlc) Enum.valueOf(enumClass, ll.getTag().toString());
                int db = enumPillsDB.getDb();
                int dbb = enumPillsDB.getDbb();
                String typeView = v.getClass().getName();
                if(v instanceof android.widget.Switch){
                    Switch s =(Switch) v;
                    boolean state = S7.GetBitAt(data,db,dbb);
                    Log.d("STATE", String.valueOf(state));
                    if(s.isChecked() != state) {
                        Message message = new Message();
                        message.what = PlcHandler.Switch;
                        message.obj = s;
                        handler.sendMessage(message);
                    }
                } else if(v instanceof TextView){
                    TextView t = (TextView) v;
                    int value = S7.GetWordAt(data,db);
                    Message message = new Message();
                    message.what = PlcHandler.TEXTVIEW;
                    message.obj = t;
                    Bundle bundle = new Bundle();
                    bundle.putString("value", String.valueOf(value));
                    message.setData(bundle);
                    handler.sendMessage(message);
                }
            }
            try {
                Thread.sleep(1000);
            }catch (InterruptedException e) {
                e.printStackTrace();
            }
        }
    }

    public S7Client getS7Client() {
        return s7Client;
    }

    public void setS7Client(S7Client s7Client) {
        this.s7Client = s7Client;
    }
}
