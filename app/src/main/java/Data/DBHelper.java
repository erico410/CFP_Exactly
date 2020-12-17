package Data;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

public class DBHelper extends SQLiteOpenHelper {
    private static final int DATABASE_VERSION = 1;
    private static final String DATABASE_NAME = "CFP";
    private static final String DATABASE_TABLE = "CFP_Info";

    private static final String KEY_ID = "_id";
    private static final String KEY_TITLE = "title";
    private static final String KEY_ABBREVIATION = "abbreviation";
    private static final String KEY_URL = "url";
    private static final String KEY_LOCATION = "location";
    private static final String KEY_EVENT_DATE_BEGIN = "event_date_begin";
    private static final String KEY_EVENT_DATE_END = "event_date_end";
    private static final String KEY_SUBMISSION_DEADLINE = "submission_deadline";
    private static final String KEY_OUTLINE = "outline";

    public DBHelper(Context context){
        super(context, DATABASE_NAME, null, DATABASE_VERSION);
    }

    @Override
    public void onCreate(SQLiteDatabase sqLiteDatabase) {
        String table = "CREATE TABLE " + DATABASE_TABLE + "("
                + KEY_ID + " INTEGER PRIMARY KEY AUTOINCREMENT, "
                + KEY_TITLE + " TEXT, "
                + KEY_ABBREVIATION + " TEXT, "
                + KEY_URL + " TEXT, "
                + KEY_LOCATION + " TEXT, "
                + KEY_EVENT_DATE_BEGIN + " DATE, "
                + KEY_EVENT_DATE_END + " DATE, "
                + KEY_SUBMISSION_DEADLINE + " DATE, "
                + KEY_OUTLINE + " TEXT "
                + ")";
        sqLiteDatabase.execSQL(table);
    }

    @Override
    public void onUpgrade(SQLiteDatabase sqLiteDatabase, int i, int i1) {
        sqLiteDatabase.execSQL("DROP TABLE IF EXISTS " + DATABASE_TABLE);
        onCreate(sqLiteDatabase);
    }

    public void insertCFP(CFPInfo data){

    }
}
