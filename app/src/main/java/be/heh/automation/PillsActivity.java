package be.heh.automation;

import android.app.Activity;
import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import be.heh.automation.PlcThread.EnumPillsDB;
import be.heh.automation.PlcThread.EnumRegulationDB;
import be.heh.automation.PlcThread.PlcConnection;
import be.heh.automation.PlcThread.PlcHandler;
import be.heh.automation.PlcThread.PlcThreadRead;
import be.heh.automation.PlcThread.PlcThreadWrite;
import be.heh.automation.Utils.Connected;
import be.heh.automation.domain.Plc;

public class PillsActivity extends Activity {

    LinearLayout li_pills_readLinearLayout;
    LinearLayout li_pills_writeLinearLayout;

    PlcThreadWrite plcThreadWrite;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_pills);

        initComponent();

        byte[] data = new byte[512];

        PlcThreadRead plcThreadRead = new PlcThreadRead(
                (LinearLayout)findViewById(R.id.li_pills_readLinearLayout),
                EnumPillsDB.class,
                new PlcHandler(),
                (TextView)findViewById(R.id.t_pills_plc),data);

        Thread threadRead = new Thread(plcThreadRead);
        threadRead.start();

        try {
            Thread.sleep(100);
        }catch (InterruptedException e) {
            e.printStackTrace();
        }

        plcThreadWrite = new PlcThreadWrite(data);

        Thread threadWrite = new Thread(plcThreadWrite);
        threadWrite.start();
    }

    public void initComponent(){
        li_pills_readLinearLayout = findViewById(R.id.li_pills_readLinearLayout);
        li_pills_writeLinearLayout = findViewById(R.id.li_pills_writeLinearLayout);

        if(Connected.connectedUser.getRight() == 0){
            li_pills_writeLinearLayout.setVisibility(View.GONE);
        }
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu){
        getMenuInflater().inflate(R.menu.menu,menu);
        return true;
    }

    public boolean onOptionsItemSelected(MenuItem item){
        switch (item.getItemId()){
            case R.id.action_user:
                Intent intent = new Intent(this,UsersActivity.class);
                startActivity(intent);
                break;
            case R.id.action_pills:
                finish();
                startActivity(getIntent());
                break;
            case R.id.action_regulation:
                Intent intent1 = new Intent(this,RegulationActivity.class);
                startActivity(intent1);
                break;
        }
        return true;
    }

    public void OnClickUpdateByteManager(View view) {
        String tag = view.getTag().toString();
        EditText editText = new EditText(this);
        switch (tag) {
            case "DBB5":
                editText = (EditText) findViewById(R.id.et_pills_dbb5);
                break;
            case "DBB6":
                editText = (EditText) findViewById(R.id.et_pills_dbb6);
                break;
            case "DBB7":
                editText = (EditText) findViewById(R.id.et_pills_dbb7);
                break;
            case "DBB8":
                editText = (EditText) findViewById(R.id.et_pills_dbb8);
                break;
        }

        try {
            EnumPillsDB enumPillsDB = Enum.valueOf(EnumPillsDB.class, tag);
            int db = enumPillsDB.getDb();
            plcThreadWrite.setWrite(db,Byte.parseByte(editText.getText().toString(),2));
            Toast.makeText(this,"Modification Sucessfull",Toast.LENGTH_LONG).show();
        }catch (NumberFormatException e){
            e.printStackTrace();
            Toast.makeText(this," Error: The value must be a byte",Toast.LENGTH_LONG).show();
        }
    }

    public void OnClickUpdateWordManager(View view) {
        String tag = view.getTag().toString();
        EditText editText = new EditText(this);
        switch (tag) {
            case "DBW18":
                editText = (EditText)findViewById(R.id.et_pills_dbw18);
                break;

        }
        EnumPillsDB enumPillsDB = Enum.valueOf(EnumPillsDB.class, tag);
        int db = enumPillsDB.getDb();
        try {
            int value = Integer.parseInt(editText.getText().toString());
            if(value >= -32768 && value <= 32767) {
                plcThreadWrite.setWrite(db, Integer.parseInt(editText.getText().toString()));
                Toast.makeText(this,"Modification Sucessfull",Toast.LENGTH_LONG).show();
            }else {
                Toast.makeText(this,
                        "Error: The value must be a interger between -32768 and 32767",
                        Toast.LENGTH_LONG).show();
            }
        }catch (NumberFormatException e){
            e.printStackTrace();
            Toast.makeText(this,
                    "Error: The value must be a interger between -32768 and 32767",
                    Toast.LENGTH_LONG).show();
        }
    }
}
