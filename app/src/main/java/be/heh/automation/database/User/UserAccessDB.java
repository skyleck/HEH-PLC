package be.heh.automation.database.User;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;

import java.util.ArrayList;

import be.heh.automation.domain.User;

public class UserAccessDB {

    private static final int VERSION = 1;

    private static final String NAME_DB = "Automation";
    private static final String TABLE_USER = "table_user";

    private static final String COL_ID = "ID";
    private static final int NUM_COL_ID = 0;
    private static final String COL_LASTNAME = "LASTNAME";
    private static final int NUM_COL_LASTNAME = 1;
    private static final String COL_FIRSTNAME = "FIRSTNAME";
    private static final int NUM_COL_FIRSTNAME = 2;
    private static final String COL_PASSWORD = "PASSWORD";
    private static final int NUM_COL_PASSWORD = 3;
    private static final String COL_EMAIL = "EMAIL";
    private static final int NUM_COL_EMAIL = 4;
    private static final String COL_RIGHT = "RIGHTS";
    private static final int NUM_COL_RIGHT = 5;

    private SQLiteDatabase db;
    private UserBddSqlite userdb;

    public UserAccessDB(Context c) {
        userdb = new UserBddSqlite(c, NAME_DB, null, VERSION);
    }

    public void openForWrite() {
        db = userdb.getWritableDatabase();
    }

    public void openForRead() {
        db = userdb.getReadableDatabase();
    }

    public void Close() {
        db.close();
    }

    public ContentValues constructConvalues(User u) {
        ContentValues content = new ContentValues();
        content.put(COL_LASTNAME, u.getLastname());
        content.put(COL_FIRSTNAME, u.getFirstname());
        content.put(COL_PASSWORD, u.getPassword());
        content.put(COL_EMAIL, u.getEmail());
        content.put(COL_RIGHT, u.getRight());

        return content;
    }

    public long insertUser(User u) {
        return db.insert(TABLE_USER, null, constructConvalues(u));
    }

    public int updateUser(int i, User u) {
        return db.update(TABLE_USER, constructConvalues(u), COL_ID + " = " + i, null);
    }

    public int removeUser(int id) {
        return db.delete(TABLE_USER, COL_ID + " = " + id, null);
    }

    public int truncateDB() {
        return db.delete(TABLE_USER, null, null);
    }

    public User getUserById(int id) throws Exception {
        Cursor c = db.query(TABLE_USER, new String[]{
                        COL_ID, COL_LASTNAME,COL_FIRSTNAME, COL_PASSWORD, COL_EMAIL, COL_RIGHT},
                COL_ID + " LIKE \"" + id + "\"", null, null, null,
                COL_ID);
        return cursorToUser(c);
    }

    public User getUserByMail(String email) throws Exception {
        Cursor c = db.query(TABLE_USER, new String[]{
                        COL_ID, COL_LASTNAME,COL_FIRSTNAME, COL_PASSWORD, COL_EMAIL, COL_RIGHT},
                COL_EMAIL + " LIKE \"" + email + "\"", null, null, null,
                COL_ID);
        return cursorToUser(c);
    }

    public User cursorToUser(Cursor c) throws Exception {
        if (c.getCount() == 0) {
            c.close();
            return null;
        }
        c.moveToFirst();

        User user = new User(c.getInt(NUM_COL_ID),
                c.getString(NUM_COL_LASTNAME),
                c.getString(NUM_COL_FIRSTNAME),
                c.getString(NUM_COL_PASSWORD),
                c.getString(NUM_COL_EMAIL),
                c.getInt(NUM_COL_RIGHT));
        c.close();
        return user;
    }

    public ArrayList<User> getAllUser() throws Exception {
        Cursor c = db.query(TABLE_USER,  new String[]{
                COL_ID, COL_LASTNAME,COL_FIRSTNAME, COL_PASSWORD, COL_EMAIL, COL_RIGHT},
                null, null, null, null, COL_ID);

        ArrayList<User> tabUser = new ArrayList<User>();

        if(c.getCount() == 0) {
            c.close();
            return tabUser;
        }

        while (c.moveToNext()){
            User user = new User(c.getInt(NUM_COL_ID),
                    c.getString(NUM_COL_LASTNAME),
                    c.getString(NUM_COL_FIRSTNAME),
                    c.getString(NUM_COL_PASSWORD),
                    c.getString(NUM_COL_EMAIL),
                    c.getInt(NUM_COL_RIGHT));
            tabUser.add(user);
        }
        c.close();
        return tabUser;
    }
}
