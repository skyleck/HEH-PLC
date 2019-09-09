package be.heh.automation;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.Switch;
import android.widget.TextView;
import android.widget.Toast;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

import be.heh.automation.PlcThread.EnumPillsDB;
import be.heh.automation.PlcThread.EnumRegulationDB;
import be.heh.automation.PlcThread.PlcHandler;
import be.heh.automation.PlcThread.PlcThreadRead;
import be.heh.automation.PlcThread.PlcThreadWrite;
import be.heh.automation.Utils.Connected;

public class RegulationActivity extends Activity{

    LinearLayout li_regulation_readLinearLayout;
    LinearLayout li_regulation_writeLinearLayout;

    PlcThreadWrite plcThreadWrite;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_regulation);

        initComponent();

        byte[] data = new byte[512];

        PlcThreadRead plcThreadRead = new PlcThreadRead(
                (LinearLayout)findViewById(R.id.li_regulation_readLinearLayout),
                EnumRegulationDB.class,
                new PlcHandler(),
                (TextView)findViewById(R.id.t_regulation_plc),data);

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
        li_regulation_readLinearLayout = findViewById(R.id.li_regulation_readLinearLayout);
        li_regulation_writeLinearLayout = findViewById(R.id.li_regulation_writeLinearLayout);

        if(Connected.connectedUser.getRight() == 0){
            li_regulation_writeLinearLayout.setVisibility(View.GONE);
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
                Intent intent1 = new Intent(this,PillsActivity.class);
                startActivity(intent1);
                break;
            case R.id.action_regulation:
                finish();
                startActivity(getIntent());
                break;
        }
        return true;
    }

    public void OnClickUpdateByteManager(View view) {
        String tag = view.getTag().toString();
        EditText editText = new EditText(this);
        switch (tag) {
            case "DB2":
                editText = (EditText) findViewById(R.id.et_regulation_db2);
                break;
            case "DB3":
                editText = (EditText) findViewById(R.id.et_regulation_db3);
                break;
        }

        try {
            EnumRegulationDB enumRegulationDB = Enum.valueOf(EnumRegulationDB.class, tag);
            int db = enumRegulationDB.getDb();
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
            case "DB24":
                editText = (EditText)findViewById(R.id.et_regulation_db24);
                break;
            case "DB26":
                editText = (EditText) findViewById(R.id.et_regulation_db26);
                break;
            case "DB28":
                editText = (EditText)findViewById(R.id.et_regulation_db28);
                break;
            case "DB30":
                editText = (EditText)findViewById(R.id.et_regulation_db30);
                break;
        }
        EnumRegulationDB enumRegulationDB = Enum.valueOf(EnumRegulationDB.class, tag);
        int db = enumRegulationDB.getDb();
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
