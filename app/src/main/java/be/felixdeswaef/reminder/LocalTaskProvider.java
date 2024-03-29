package be.felixdeswaef.reminder;

import android.content.ContentProvider;
import android.content.ContentUris;
import android.content.ContentValues;
import android.content.Context;
import android.content.UriMatcher;
import android.database.Cursor;
import android.database.SQLException;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.database.sqlite.SQLiteQueryBuilder;
import android.net.Uri;
import android.text.TextUtils;

import java.util.HashMap;

public class LocalTaskProvider extends ContentProvider {
    static final String PROVIDER_NAME = "be.felixdeswaef.reminder.LocalTaskProvider";
    static final String URL = "content://" + PROVIDER_NAME + "/tasks";
    static final Uri CONTENT_URI = Uri.parse(URL);

    static final String _ID = "_id";
    static final String NAME = "name";


    private static HashMap<String, String> TASK_PROJECTION_MAP;

    static final int TASK = 1;
    static final int TASK_ID = 2;

    static final UriMatcher uriMatcher;

static{
        uriMatcher = new UriMatcher(UriMatcher.NO_MATCH);
        uriMatcher.addURI(PROVIDER_NAME, "tasks", TASK);
        uriMatcher.addURI(PROVIDER_NAME, "tasks/#", TASK_ID);
        }

/**
 * Database specific constant declarations
 */

private SQLiteDatabase db;
static final String DATABASE_NAME = "tasker";
static final String TASK_TABLE_NAME = "task";
static final int DATABASE_VERSION = 1;
static final String CREATE_DB_TABLE =
        " CREATE TABLE " + TASK_TABLE_NAME +
        " (_id INTEGER PRIMARY KEY AUTOINCREMENT, " +
        " name TEXT NOT NULL, " +
        " value TEXT NOT NULL);";

/**
 * Helper class that actually creates and manages
 * the provider's underlying data repository.
 */

private static class DatabaseHelper extends SQLiteOpenHelper {
    DatabaseHelper(Context context){
        super(context, DATABASE_NAME, null, DATABASE_VERSION);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        db.execSQL(CREATE_DB_TABLE);
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        db.execSQL("DROP TABLE IF EXISTS " + TASK_TABLE_NAME);
        onCreate(db);
    }
}

    @Override
    public boolean onCreate() {
        Context context = getContext();
        DatabaseHelper dbHelper = new DatabaseHelper(context);

        /**
         * Create a write able database which will trigger its
         * creation if it doesn't already exist.
         */

        db = dbHelper.getWritableDatabase();
        return (db == null)? false:true;
    }

    @Override
    public Uri insert(Uri uri, ContentValues values) {
        //add new task
        long rowID = db.insert(TASK_TABLE_NAME, "", values);


        if (rowID > 0) {   //succes ?
            Uri _uri = ContentUris.withAppendedId(CONTENT_URI, rowID);
            getContext().getContentResolver().notifyChange(_uri, null);
            return _uri;
        }

        throw new SQLException("Failed to add a record into " + uri);
    }

    @Override
    public Cursor query(Uri uri, String[] projection,
                        String selection, String[] selectionArgs, String sortOrder) {
        SQLiteQueryBuilder qb = new SQLiteQueryBuilder();
        qb.setTables(TASK_TABLE_NAME);

        switch (uriMatcher.match(uri)) {
            case TASK:
                qb.setProjectionMap(TASK_PROJECTION_MAP);
                break;

            case TASK_ID: //if id was specified
                qb.appendWhere( _ID + "=" + uri.getPathSegments().get(1));
                break;

            default:
        }

        if (sortOrder == null || sortOrder == ""){
            //sortorder not set
            sortOrder = NAME;
        }

        Cursor c = qb.query(db,	projection,	selection,
                selectionArgs,null, null, sortOrder);

        /**
         * register to watch a content URI for changes
         */
        c.setNotificationUri(getContext().getContentResolver(), uri);
        return c;
    }

    @Override
    public int delete(Uri uri, String selection, String[] selectionArgs) {
        int count = 0;
        switch (uriMatcher.match(uri)){
            case TASK:  //delete all
                count = db.delete(TASK_TABLE_NAME, selection, selectionArgs);
                break;

            case TASK_ID: //delete on id
                String id = uri.getPathSegments().get(1);
                count = db.delete(TASK_TABLE_NAME, _ID +  " = " + id +
                                (!TextUtils.isEmpty(selection) ? " AND (" + selection + ')' : ""), selectionArgs);
                break;
            default:
                throw new IllegalArgumentException("Unknown URI " + uri);
        }

        getContext().getContentResolver().notifyChange(uri, null);
        return count;
    }

    @Override
    public int update(Uri uri, ContentValues values,
                      String selection, String[] selectionArgs) {
        int count = 0;
        switch (uriMatcher.match(uri)) {
            case TASK:
                count = db.update(TASK_TABLE_NAME, values, selection, selectionArgs);
                break;

            case TASK_ID:
                count = db.update(TASK_TABLE_NAME, values,
                        _ID + " = " + uri.getPathSegments().get(1) +(!TextUtils.isEmpty(selection) ? " AND (" +selection + ')' : ""), selectionArgs);
                break;
            default:
                throw new IllegalArgumentException("Unknown URI " + uri );
        }

        getContext().getContentResolver().notifyChange(uri, null);
        return count;
    }

    @Override
    public String getType(Uri uri) {
        switch (uriMatcher.match(uri)){

            case TASK:
                return "felixdeswaef.android.cursor.dir/felixdeswaef.reminder.task";

            case TASK_ID:
                return "felixdeswaef.android.cursor.item/felixdeswaef.reminder.task";
            default:
                throw new IllegalArgumentException("Unsupported URI: " + uri);
        }
    }
}