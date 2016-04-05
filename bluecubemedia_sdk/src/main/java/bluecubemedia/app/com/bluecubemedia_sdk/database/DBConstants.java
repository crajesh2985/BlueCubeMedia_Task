package bluecubemedia.app.com.bluecubemedia_sdk.database;

public class DBConstants {

    public static final String TAG = "DBConstants";
    public static final String DATABASE_NAME = "CurrencyCalc";
    public static final int DATABASE_VERSION = 1;
    public static final String HISTORY_TABLE = "history";
    public static final String COL_ID = "_id";
    public static final String COL_FROM = "from_value";
    public static final String COL_TO = "to_value";
    public static final String COL_AMOUNT = "amount";
    public static final String COL_FINIAL_RATE = "finial_rate";
    /**
     * Query for creating table user detail
     */
    public static final String CREATE_HISTORY_TABLE = "CREATE TABLE "
            + HISTORY_TABLE + "("
            + COL_ID + " INTEGER PRIMARY KEY AUTOINCREMENT, "
            + COL_FROM + " TEXT NOT NULL,"
            + COL_TO + " TEXT NOT NULL,"
            + COL_AMOUNT + " TEXT NOT NULL,"
            + COL_FINIAL_RATE + " TEXT NOT NULL" + ");";

    /**
     * Query for removing the existing table
     */
    public static final String DROP_HISTORY_DETAIL_TABLE = "DROP TABLE IF EXISTS "
            + HISTORY_TABLE;
}