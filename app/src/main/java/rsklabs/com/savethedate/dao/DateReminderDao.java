package rsklabs.com.savethedate.dao;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

/**
 * Created by Kumar on 12/25/2015.
 */
public class DateReminderDao extends SQLiteOpenHelper {

    private static final int DATABASE_VERSION = 1;
    protected static final String DATABASE_NAME = "DateReminder";

    public DateReminderDao(Context context) {
        super(context, DATABASE_NAME, null, DATABASE_VERSION);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {

        String sql = "CREATE TABLE dateReminder ( id INTEGER PRIMARY KEY AUTOINCREMENT, date TEXT, reason TEXT)";

        db.execSQL(sql);

    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {

        String sql = "DROP TABLE IF EXISTS dateReminder";
        db.execSQL(sql);

        onCreate(db);
    }

}

