package rsklabs.com.savethedate.helper;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;

import java.util.ArrayList;
import java.util.List;

import rsklabs.com.savethedate.bean.DateReminderBean;
import rsklabs.com.savethedate.dao.DateReminderDao;


/**
 * Created by Kumar on 12/25/2015.
 */
public class DateReminderHelper extends DateReminderDao {

    public DateReminderHelper(Context context) {
        super(context);
    }

    public boolean create(DateReminderBean dateReminderBean) {

        ContentValues values = new ContentValues();

        values.put("date", dateReminderBean.date);
        values.put("reason", dateReminderBean.reason);

        SQLiteDatabase db = this.getWritableDatabase();

        boolean createSuccessful = db.insert("dateReminder", null, values) > 0;
        db.close();

        return createSuccessful;
    }

    public List<DateReminderBean> read() {

        List<DateReminderBean> recordsList = new ArrayList<DateReminderBean>();

        String sql = "SELECT * FROM dateReminder ORDER BY id DESC";

        SQLiteDatabase db = this.getWritableDatabase();
        Cursor cursor = db.rawQuery(sql, null);

        if (cursor.moveToFirst()) {
            do {
                int id = Integer.parseInt(cursor.getString(cursor.getColumnIndex("id")));
                String date = cursor.getString(cursor.getColumnIndex("date"));
                String reason = cursor.getString(cursor.getColumnIndex("reason"));

                DateReminderBean dateReminderBean = new DateReminderBean();
                dateReminderBean.id= id;
                dateReminderBean.date = date;
                dateReminderBean.reason = reason;
                recordsList.add(dateReminderBean);

            } while (cursor.moveToNext());
        }

        cursor.close();
        db.close();

        return recordsList;
    }

    public boolean delete(int id) {
        boolean deleteSuccessful = false;

        SQLiteDatabase db = this.getWritableDatabase();
        deleteSuccessful = db.delete("dateReminder", "id ='" + id + "'", null) > 0;
        db.close();

        return deleteSuccessful;

    }
}
