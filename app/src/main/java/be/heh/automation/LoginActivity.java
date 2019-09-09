package be.heh.automation;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.View;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.EditText;
import android.widget.Toast;

import java.util.ArrayList;

import be.heh.automation.Utils.Connected;
import be.heh.automation.database.User.UserAccessDB;
import be.heh.automation.database.User.UserBddSqlite;
import be.heh.automation.domain.User;

public class LoginActivity extends Activity {

    EditText et_login_email;
    EditText et_login_password;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        UserAccessDB userAccessDB = new UserAccessDB(this);
        userAccessDB.openForRead();
        try {
            if(userAccessDB.getAllUser().size() == 0){
                Intent intent = new Intent(this,RegisterActivity.class);
                startActivity(intent);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        userAccessDB.Close();

        initComponent();
    }

    public void initComponent(){
        et_login_email = findViewById(R.id.et_login_email);
        et_login_password = findViewById(R.id.et_login_password);
    }

    public void OnClickLoginManager(View view) {
        UserAccessDB userAccessDB = new UserAccessDB(this);
        userAccessDB.openForRead();
        User user = null;
        try {
            user = userAccessDB.getUserByMail(et_login_email.getText().toString());
            if(user == null || !user.getPassword().equals(et_login_password.getText().toString())){
                Toast.makeText(this,"Login incorrect", Toast.LENGTH_LONG).show();
            }else {
                Connected.connectedUser = user;
                Intent intent = new Intent(this,UsersActivity.class);
                startActivity(intent);
            }
        } catch (Exception e) {
            e.printStackTrace();
            Toast.makeText(this,"Login incorrect", Toast.LENGTH_LONG).show();
        } finally {
            userAccessDB.Close();
        }
    }

    public void OnClickRegisterManager(View view) {
        Intent intent = new Intent(this,RegisterActivity.class);
        startActivity(intent);
    }
}
