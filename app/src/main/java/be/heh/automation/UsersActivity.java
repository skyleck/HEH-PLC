package be.heh.automation;

import android.app.Activity;
import android.content.Intent;
import android.graphics.Color;
import android.graphics.drawable.Drawable;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.ScrollView;
import android.widget.TextView;

import java.util.ArrayList;

import be.heh.automation.Utils.Connected;
import be.heh.automation.database.User.UserAccessDB;
import be.heh.automation.domain.User;

public class UsersActivity extends Activity {

    LinearLayout li_users_linearLayout;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_users);

        li_users_linearLayout = findViewById(R.id.li_users_linearLayout);

        UserAccessDB userAccessDB = new UserAccessDB(this);
        userAccessDB.openForRead();

        try {
            ArrayList<User> users = userAccessDB.getAllUser();
            for(int i = 0; i < users.size(); i++) {
                LinearLayout linearLayout = new LinearLayout(this);
                linearLayout.setOrientation(LinearLayout.HORIZONTAL);

                LinearLayout info = new LinearLayout(this);
                info.setOrientation(LinearLayout.VERTICAL);

                TextView name = new TextView(this);
                name.setText(users.get(i).getLastname() + " " + users.get(i).getFirstname());
                name.setTextSize(22);

                TextView email = new TextView(this);
                email.setText(users.get(i).getEmail());
                email.setTextSize(22);

                View line = new View(this);
                line.setLayoutParams(new ViewGroup.LayoutParams(li_users_linearLayout.getLayoutParams().width,1));
                line.setBackgroundColor(Color.WHITE);

                info.addView(name);
                info.addView(email);


                LinearLayout.LayoutParams layoutParams = new LinearLayout.LayoutParams(
                        LinearLayout.LayoutParams.MATCH_PARENT, LinearLayout.LayoutParams.WRAP_CONTENT);

                layoutParams.setMargins(0,15,0,15);

                linearLayout.setLayoutParams(layoutParams);

                linearLayout.setTag(users.get(i).getId());

                linearLayout.setOnClickListener(OnClickLinearLayoutManager());

                linearLayout.addView(info);

                if(Connected.connectedUser.getRight() == 2  && users.get(i).getRight() != 2 ) {
                    Button buttonDown = new Button(this);
                    buttonDown.setTag(users.get(i).getId());
                    buttonDown.setText("Downgrade");
                    buttonDown.setOnClickListener(OnClickDowngradeManager());

                    Button buttonUpgrade = new Button(this);
                    buttonUpgrade.setTag(users.get(i).getId());
                    buttonUpgrade.setText("Upgrade");
                    buttonUpgrade.setOnClickListener(OnClickUpgradeManager());

                    Button buttonDelete = new Button(this);
                    buttonDelete.setTag(users.get(i).getId());
                    buttonDelete.setText("Delete");
                    buttonDelete.setOnClickListener(OnClickDeleteManager());

                    if(users.get(i).getRight() == 0){
                        linearLayout.addView(buttonUpgrade);
                    } else{
                        linearLayout.addView(buttonDown);
                    }
                    linearLayout.addView(buttonDelete);
                }
                li_users_linearLayout.addView(linearLayout);
                li_users_linearLayout.addView(line);
            }
        } catch (Exception e) {
            e.printStackTrace();
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
                finish();
                startActivity(getIntent());
                break;
            case R.id.action_pills:
                Intent intent = new Intent(this,PillsActivity.class);
                startActivity(intent);
                break;
            case R.id.action_regulation:
                Intent intent1 = new Intent(this,RegulationActivity.class);
                startActivity(intent1);
                break;
        }
        return true;
    }

    public View.OnClickListener OnClickLinearLayoutManager(){
        return new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(Connected.connectedUser.getRight() == 2) {
                    Intent intent = new Intent(getApplicationContext(), RegisterActivity.class);
                    intent.putExtra("id", Integer.parseInt(v.getTag().toString()));
                    startActivity(intent);
                }
            }
        };
    }

    public View.OnClickListener OnClickDowngradeManager(){
        return new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                try {
                    UserAccessDB userAccessDB = new UserAccessDB(getApplicationContext());
                    userAccessDB.openForRead();
                    User user = userAccessDB.getUserById(Integer.parseInt(v.getTag().toString()));
                    userAccessDB.openForWrite();
                    user.setRight(user.getRight() - 1);
                    userAccessDB.updateUser(user.getId(),user);
                    userAccessDB.Close();
                    finish();
                    startActivity(getIntent());
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
        };
    }

    public View.OnClickListener OnClickUpgradeManager(){
        return new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                try {
                    UserAccessDB userAccessDB = new UserAccessDB(getApplicationContext());
                    userAccessDB.openForRead();
                    User user = userAccessDB.getUserById(Integer.parseInt(v.getTag().toString()));
                    userAccessDB.openForWrite();
                    user.setRight(user.getRight() + 1);
                    userAccessDB.updateUser(user.getId(),user);
                    userAccessDB.Close();
                    finish();
                    startActivity(getIntent());
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
        };
    }

    public View.OnClickListener OnClickDeleteManager(){
        return new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                UserAccessDB userAccessDB = new UserAccessDB(getApplicationContext());
                userAccessDB.openForWrite();
                userAccessDB.removeUser(Integer.parseInt(v.getTag().toString()));
                userAccessDB.Close();
                finish();
                startActivity(getIntent());
            }
        };
    }
}
