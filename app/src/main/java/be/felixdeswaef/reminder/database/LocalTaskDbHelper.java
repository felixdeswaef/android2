package be.felixdeswaef.reminder.database;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

public class LocalTaskDbHelper extends SQLiteOpenHelper {

    private static final int DATABASE_VERSION = 1;
    private static final String DATABASE_NAME = "LOCALTASKDB";
    public LocalTaskDbHelper(Context context)
    {
        super(context,DATABASE_NAME,null,DATABASE_VERSION);

    }
    @Override
    public void onCreate(SQLiteDatabase db)
    {
        //create from models
        //db.execSQL(...); //TODO
    }
    public void onUpgrade(SQLiteDatabase db, int oldVersion,int newVersion)
    {
        //drop old
        //db.execSQL("DROP TABLE IF EXISTS "+....TABLE_NAME); //TODO
        onCreate(db);

    }

}
