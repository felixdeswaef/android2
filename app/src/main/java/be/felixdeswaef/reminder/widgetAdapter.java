package be.felixdeswaef.reminder;

import android.app.PendingIntent;
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
import android.widget.Toast;

import com.google.gson.Gson;

import java.util.ArrayList;
import java.util.List;

public class widgetAdapter implements RemoteViewsService.RemoteViewsFactory {
    private Context context;
    private task[] tasklist;
    private Intent intent;

    //For obtaining the activity's context and intent
    public widgetAdapter(Context context,Intent intent) {
        this.context = context;
        this.intent = intent;

    }

    private void initCursor(){ //TODO get/update data
        //Exception str = null ;
        //try {
            //context.getApplicationContext().getClass();
            //getTData();
        //}catch (Exception exeption){
            //str = exeption;
        //}
        task a = new task("a");
        task b = new task("b");
        task c = new task("c");
        tasklist = getTData();

        /*
        Log.e("WDGT","init data");



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
        Toast.makeText(context,"got created",Toast.LENGTH_LONG).show();
        initCursor();
        Toast.makeText(context,"init complete",Toast.LENGTH_LONG).show();


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
        //Toast.makeText(context,"dataset changed",Toast.LENGTH_LONG).show();
        initCursor();
    }

    @Override
    public void onDestroy() {
        //cursor.close();
    }

    @Override
    public int getCount() {
        return tasklist.length;
    }

    @Override
    public RemoteViews getViewAt(int i) {
        //Toast.makeText(context,"get vieuw at called ",Toast.LENGTH_LONG).show();
        /** Populate your widget's single list item **/
        String msg = "";
        RemoteViews remoteViews = new RemoteViews(context.getPackageName(), R.layout.widgettask);

        //Toast.makeText(context,"got vieuw at"+i,Toast.LENGTH_LONG).show();
        remoteViews.setTextViewText(R.id.widgtitle, tasklist[i].name);
        //remoteViews.setTextViewText(R.id.widgdead,MainActivity.handler.DaysString(tasklist[i].deadline));
        try{
            remoteViews.setProgressBar(R.id.widgprog,100,tasklist[i].completion,false);//tasklist[i].completion
            msg = "gs";
        }catch (Exception e){
            remoteViews.setProgressBar(R.id.widgprog,100,50,true);//tasklist[i].completion
            msg = "ex";
        }
        remoteViews.setTextViewText(R.id.widgdead,msg);

        Intent launchMain = new Intent(context, MainActivity.class);
        PendingIntent pendingMainIntent = PendingIntent.getActivity(context, 0, launchMain, 0);
        remoteViews.setOnClickPendingIntent(R.id.widgetcont, pendingMainIntent);



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

    public task[] getTData(){
        try {

            Cursor cur = null;
            if (cur != null) {
                cur.close();
            }
            Uri uri = LocalTaskProvider.CONTENT_URI;
            final long identityToken = Binder.clearCallingIdentity();
            /**This is done because the widget runs as a separate thread
             when compared to the current app and hence the app's data won't be accessible to it
             because I'm using a content provided **/

            List<task> taskList = new ArrayList<task>();


            String[] projection = {"value", "name", "_id"};
            String selection = "";
            Gson gs = new Gson();
            ContentResolver cr = this.context.getContentResolver();
            cur = cr.query(uri, projection, selection, null, null);
            cur.moveToFirst();
            while (!cur.isAfterLast()) {

                String data = cur.getString(0);
                int id = cur.getInt(2);
                try {
                    task t = gs.fromJson(data, task.class);
                    t.id = id;
                    taskList.add(t);
                } catch (Exception e) {// also catches illegal items in database //TODO create cleanup routine
                    //Log.d("DBDB", "exepted ::" + data);

                }

                cur.moveToNext();
            }
            cur.close();
            Binder.restoreCallingIdentity(identityToken);

            //Log.e("WGT","db read" +taskList.size() + "items");

            return ((task[]) taskList.toArray(new task[taskList.size()]));





        }catch (Exception e){
            task a = new task("an error ocured");

            return new task[] {a};
        }


    }

}
