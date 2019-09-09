package be.heh.automation.PlcThread;

import android.os.Handler;
import android.os.Message;
import android.util.Log;
import android.widget.ScrollView;
import android.widget.Switch;
import android.widget.TextView;

public class PlcHandler extends Handler {

    public static final int Switch = 1;
    public static final int TEXTVIEW = 2;

    @Override
    public void handleMessage(Message msg) {
        super.handleMessage(msg);
        int value = msg.what;
        switch (value){
            case Switch:
                android.widget.Switch s = (android.widget.Switch) msg.obj;
                s.setChecked(!s.isChecked());
                break;
            case TEXTVIEW:
                TextView t = (TextView) msg.obj;
                String str = msg.getData().getString("value");
                t.setText(str);
                break;
        }
    }
}
