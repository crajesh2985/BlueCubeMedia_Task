package bluecubemedia.app.com.bluecubemedia_sdk.database;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.SQLException;
import android.database.sqlite.SQLiteDatabase;
import android.util.Log;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;


public class DBAdapter {

    private Context context;
    public DatabaseHelper DBHelper;
    public SQLiteDatabase db;

    /**
     * @param ctx
     */

    public DBAdapter(Context ctx) {
        this.context = ctx;
        DBHelper = new DatabaseHelper(context);
    }

    // ---opens the database---

    /**
     * @return
     * @throws SQLException
     */
    public DBAdapter open() throws SQLException {
        try {
            db = DBHelper.getWritableDatabase();
            return this;

        } catch (SQLException ex) {
            db = DBHelper.getReadableDatabase();
        }
        return this;
    }

    // ---closes the database---

    /**
     * --Close database
     */
    public void close() {
        DBHelper.close();
    }


    public long addInHistoryRequest(String from, String to, String amount, String finalrate){
        ContentValues values = new ContentValues();
        values.put(DBConstants.COL_FROM, from);
        values.put(DBConstants.COL_TO, to);
        values.put(DBConstants.COL_AMOUNT, amount);
        values.put(DBConstants.COL_FINIAL_RATE, finalrate);
        return db.insert(DBConstants.HISTORY_TABLE, null,
                values);
    }


    public Cursor getHistoryDetails() {
        Cursor c = db.query(DBConstants.HISTORY_TABLE, null, null, null, null,
                null, null);
        return c;
    }

}
