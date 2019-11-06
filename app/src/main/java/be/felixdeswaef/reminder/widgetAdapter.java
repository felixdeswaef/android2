package be.felixdeswaef.reminder;

import android.content.ContentResolver;
import android.content.Context;
import android.content.Context;
import android.content.Intent;
import android.database.Cursor;
import android.database.DatabaseUtils;
import android.net.Uri;
import android.os.Binder;
import android.util.Log;
import android.widget.RemoteViews;
import android.widget.RemoteViewsService;

import com.google.gson.Gson;

public class widgetAdapter implements RemoteViewsService.RemoteViewsFactory {
    private Context context;
    task[] tasklist;
    private Intent intent;

    //For obtaining the activity's context and intent
    public widgetAdapter(Context context, Intent intent) {
        this.context = context;
        this.intent = intent;
    }

    private void initCursor(){ //TODO get/update data
        task a = new task("a task");
        task b = new task("btask");
        task c = new task("c task");
        tasklist = new task[] {a,b,c};


        /*
        //tasklist = MainActivity.handler.getData();
        ContentResolver cr = context.getContentResolver();
        Cursor cur = null;
        Uri uri = Uri.parse(LocalTaskProvider.CONTENT_URI + "/");
        String[] projection = {"value", "name", "_id"};
        String selection = "";

        String[] values = {};

        cur = cr.query(uri, projection, "_id = ?", values, null);
        Log.d("DBDB", DatabaseUtils.dumpCursorToString(cur));
        Log.d("DBDB", values[0]);
        cur.moveToLast();
        String data = cur.getString(0);
        cur.getColumnNames();
        try {
            task t = new Gson().fromJson(data, task.class);
            t.id = cur.getInt(2);
            tasklist = new task[] {t};
        } catch (Exception e) {
            Log.d("DBDB", "exepted ::" + data + values[0]);
            Log.e("DBDBEE", e.toString());

        }
        /*
        if (cursor != null) {
            cursor.close();
        }
        final long identityToken = Binder.clearCallingIdentity();
        /**This is done because the widget runs as a separate thread
         when compared to the current app and hence the app's data won't be accessible to it
         because I'm using a content provided **/
        /*cursor = context.getContentResolver().query(QuoteProvider.Quotes.CONTENT_URI,
                new String[]{QuoteColumns._ID, QuoteColumns.SYMBOL, QuoteColumns.BIDPRICE,
                        QuoteColumns.PERCENT_CHANGE, QuoteColumns.CHANGE, QuoteColumns.ISUP},
                QuoteColumns.ISCURRENT + " = ?",
                new String[]{"1"},null);
        Binder.restoreCallingIdentity(identityToken);


         */


    }

    @Override
    public void onCreate() {
        //initCursor();
        /*

        initCursor();
        if (cursor != null) {
            cursor.moveToFirst();
        }

         */
    }

    @Override
    public void onDataSetChanged() {
        /** Listen for data changes and initialize the cursor again **/
        initCursor();
    }

    @Override
    public void onDestroy() {
        //cursor.close();
    }

    @Override
    public int getCount() {
        return 9;//tasklist.length;
    }

    @Override
    public RemoteViews getViewAt(int i) {
        /** Populate your widget's single list item **/
        RemoteViews remoteViews = new RemoteViews(context.getPackageName(), R.layout.widgettask);

        remoteViews.setTextViewText(R.id.widgtitle,"bob "+i);// tasklist[i].name);
        //remoteViews.setTextViewText(R.id.widgdead,MainActivity.handler.DaysString(tasklist[i].deadline));
        remoteViews.setProgressBar(R.id.widgprog,100,i*5+20,false);//,tasklist[i].completion,false);

        return remoteViews;
    }

    @Override
    public RemoteViews getLoadingView() {
        return null;
    }

    @Override
    public int getViewTypeCount() {
        return 1;
    }

    @Override
    public long getItemId(int i) {
        return i;
    }

    @Override
    public boolean hasStableIds() {
        return true;
    }
}
