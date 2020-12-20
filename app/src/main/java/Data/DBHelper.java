package Data;

import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteException;
import android.database.sqlite.SQLiteOpenHelper;
import android.util.Log;

import java.io.FileOutputStream;
import java.io.InputStream;
import java.io.OutputStream;
import java.util.ArrayList;

public class DBHelper extends SQLiteOpenHelper {
    private static final int DATABASE_VERSION = 1;
    private static final String DATABASE_NAME = "CFP_Exactly.db";

    private static final String INFO_TABLE = "CFPInfo";

    private static String DB_PATH = "/data/data/" +
            "com.cfp_exactly" + "/databases/";

    private static final String KEY_EVENT_ID = "_id";
    private static final String KEY_TITLE = "title";
    private static final String KEY_ABBREVIATION = "abbreviation";
    private static final String KEY_URL = "url";
    private static final String KEY_LOCATION = "location";
    private static final String KEY_EVENT_DATE_BEGIN = "event_date_begin";
    private static final String KEY_EVENT_DATE_END = "event_date_end";
    private static final String KEY_SUBMISSION_DEADLINE = "submission_deadline";
    private static final String KEY_OUTLINE = "outline";

    private static Context ctx;

    public DBHelper(Context context){
        super(context, DATABASE_NAME, null, DATABASE_VERSION);
        ctx = context;
    }

    public CFPInfo getInfoByEventId(Integer eventId){
        String selectQuery = "SELECT * FROM " + INFO_TABLE + " WHERE " + KEY_EVENT_ID + "=" + Integer.toString(eventId);

        SQLiteDatabase db = this.getReadableDatabase();
        Cursor cursor = db.rawQuery(selectQuery, null);

        CFPInfo item = new CFPInfo();

        if(cursor.moveToFirst()) {
            item.setEventId(cursor.getInt(0));
            item.setTitle(cursor.getString(1));
            item.setAbbreviation(cursor.getString(2));
            item.setUrl(cursor.getString(3));
            item.setLocation(cursor.getString(4));
            item.setEventDateBegin(cursor.getString(5));
            item.setEventDateEnd(cursor.getString(6));
            item.setSubmissionDeadline(cursor.getString(7));
            item.setOutline(cursor.getString(8));
        }
        db.close();

        return item;
    }

    public ArrayList<CFPInfo> getInfoByQuery(String query){
        String selectQuery = "SELECT * FROM " + INFO_TABLE + query + " LIMIT 20";

        Log.v("getInfoByQuery", selectQuery);
        SQLiteDatabase db = this.getReadableDatabase();
        Cursor cursor = db.rawQuery(selectQuery, null);

        ArrayList<CFPInfo> list = new ArrayList<CFPInfo>();

        if(cursor.moveToFirst()) {
            do {
                CFPInfo item = new CFPInfo();
                item.setEventId(cursor.getInt(0));
                item.setTitle(cursor.getString(1));
                item.setAbbreviation(cursor.getString(2));
                item.setUrl(cursor.getString(3));
                item.setLocation(cursor.getString(4));
                item.setEventDateBegin(cursor.getString(5));
                item.setEventDateEnd(cursor.getString(6));
                item.setSubmissionDeadline(cursor.getString(7));
                item.setOutline(cursor.getString(8));
                list.add(item);
            }while(cursor.moveToNext());
        }
        db.close();

        return list;
    }

    @Override
    public void onCreate(SQLiteDatabase sqLiteDatabase) {

    }

    @Override
    public void onUpgrade(SQLiteDatabase sqLiteDatabase, int i, int i1) {
        if(!createDatabase()){
            Log.e("database", "create error");
        }
    }

    public boolean createDatabase() {
        boolean dbExist = checkDatabase();
        this.getReadableDatabase();
        if (dbExist == false) {
            if (copyDatabase() == false) {
                return false;
            }
        }
        return true;
    }

    private boolean checkDatabase() {
        SQLiteDatabase checkDB = null;
        String dbpath = DB_PATH + DATABASE_NAME;
        try {
            checkDB = SQLiteDatabase.openDatabase(dbpath,
                    null, SQLiteDatabase.OPEN_READONLY);
        } catch (SQLiteException e) {
            return false;
        }
        if (checkDB != null) {
            checkDB.close();
            return true;
        }
        return false;
    }

    private boolean copyDatabase() {
        try {
            InputStream input = ctx.getAssets().open(DATABASE_NAME);
            this.getReadableDatabase();
            String outFileName = DB_PATH + DATABASE_NAME;
            OutputStream output =
                    new FileOutputStream(outFileName);
            byte [] buffer = new byte[1024];
            int length;
            while ((length = input.read(buffer)) > 0) {
                output.write(buffer, 0, length);
            }
            output.flush();
            output.close();
            input.close();
        } catch (Exception e) {
            return false;
        }
        return true;
    }
}
