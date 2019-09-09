package be.heh.automation.database.User;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.util.Log;

public class UserBddSqlite extends SQLiteOpenHelper {

    private static final String TABLE_USER = "TABLE_USER";

    private static final String COL_ID = "ID";
    private static final String COL_LASTNAME = "LASTNAME";
    private static final String COL_FIRSTNAME = "FIRSTNAME";
    private static final String COL_PASSWORD = "PASSWORD";
    private static final String COL_EMAIL = "EMAIL";
    private static final String COL_RIGHT = "RIGHTS";

    private static final String CREATE_BDD = "CREATE TABLE " + TABLE_USER + "("
                                                + COL_ID + " INTEGER PRIMARY KEY AUTOINCREMENT, "
                                                + COL_LASTNAME + " TEXT NOT NULL, "
                                                + COL_FIRSTNAME + " TEXT NOT NULL, "
                                                + COL_PASSWORD  + " TEXT NOT NULL, "
                                                + COL_EMAIL + " TEXT NOT NULL, "
                                                + COL_RIGHT + " INTEGER NOT NULL);";

    public UserBddSqlite(Context context, String name, SQLiteDatabase.CursorFactory factory, int version) {
        super (context, name, factory, version);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        Log.d("CREATE", CREATE_BDD);
        db.execSQL(CREATE_BDD);
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        db.execSQL("DROP TABLE " + TABLE_USER);
        onCreate(db);
    }
}
