package be.heh.automation;

import android.app.Activity;
import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import java.util.ArrayList;

import be.heh.automation.Utils.Connected;
import be.heh.automation.database.User.UserAccessDB;
import be.heh.automation.domain.User;

public class RegisterActivity extends Activity {

    Button btn_register_register;
    Button btn_register_update;

    EditText et_register_lastname;
    EditText et_register_firstname;
    EditText et_register_email;
    EditText et_register_password;
    EditText et_register_confirmPassword;

    User user = null;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register);

        try {
            initComponent();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public void initComponent() throws Exception {
        btn_register_register = findViewById(R.id.btn_register_register);
        btn_register_update = findViewById(R.id.btn_register_update);

        et_register_lastname = findViewById(R.id.et_register_lastname);
        et_register_firstname = findViewById(R.id.et_register_firstname); ;
        et_register_email = findViewById(R.id.et_register_email);
        et_register_password = findViewById(R.id.et_register_password);;
        et_register_confirmPassword = findViewById(R.id.et_register_confirmPassword);

        if(getIntent().getIntExtra("id",-1) != -1){
            UserAccessDB userAccessDB = new UserAccessDB(this);
            userAccessDB.openForRead();
            user = userAccessDB.getUserById((Integer) getIntent().getExtras().get("id"));
            et_register_lastname.setText(user.getLastname());
            et_register_firstname.setText(user.getFirstname());
            et_register_password.setText(user.getPassword());
            et_register_confirmPassword.setText(user.getPassword());
            et_register_email.setText(user.getEmail());
            userAccessDB.Close();
            btn_register_register.setVisibility(View.GONE);
            btn_register_update.setVisibility(View.VISIBLE);
        }
    }

    public void OnClickRegisterManager(View view) {
        try {
            User userInsert = new User(0,et_register_lastname.getText().toString(),
                                 et_register_firstname.getText().toString(),
                                 et_register_password.getText().toString(),
                                 et_register_email.getText().toString(),
                                 0);

            if(et_register_password.getText().toString().equals(et_register_confirmPassword.getText().toString())) {
                UserAccessDB userAccessDB = new UserAccessDB(this);
                userAccessDB.openForWrite();
                if(userAccessDB.getAllUser().size() == 0){
                    userInsert.setRight(2);
                }
                userAccessDB.insertUser(userInsert);
                userAccessDB.Close();
                Intent intent = new Intent(this,LoginActivity.class);
                startActivity(intent);
            }else{
                Toast.makeText(this,"Password not matches",Toast.LENGTH_LONG).show();
            }
        } catch (Exception e) {
            Toast.makeText(this,e.getMessage(),Toast.LENGTH_LONG).show();
        }
    }

    public void OnClickUpdateManager(View view) throws Exception {
        try {
            User userUpdate = new User(user.getId(),et_register_lastname.getText().toString(),
                    et_register_firstname.getText().toString(),
                    et_register_password.getText().toString(),
                    et_register_email.getText().toString(),
                    user.getRight());
            if(et_register_password.getText().toString().equals(et_register_confirmPassword.getText().toString())) {
                UserAccessDB userAccessDB = new UserAccessDB(this);
                userAccessDB.openForWrite();
                userAccessDB.updateUser(user.getId(),userUpdate);
                userAccessDB.Close();
                Intent intent = new Intent(this,UsersActivity.class);
                startActivity(intent);
            }else{
                Toast.makeText(this,"Password not matches",Toast.LENGTH_LONG).show();
            }
        }catch (Exception e) {
            Toast.makeText(this,e.getMessage(),Toast.LENGTH_LONG).show();
        }
    }
}
