package be.felixdeswaef.reminder;

import android.content.ContentResolver;
import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.DatabaseUtils;
import android.net.Uri;
import android.util.Log;

import com.google.gson.Gson;

import java.net.URI;
import java.sql.Time;
import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.List;

public class DHandler {
    ContentResolver cr;
    calls inf;
    calls rootinterface;

    public DHandler(Context ct, calls tasks) {
        cr = ct.getContentResolver();
        inf = tasks;


    }

    public void pushQuick(String name) {
        task newtask = new task(name);
        Gson gs = new Gson();

        ContentValues cval = new ContentValues();//TODO
        cval.put("value", gs.toJson(newtask));
        cval.put("name", newtask.name);
        Uri uri = LocalTaskProvider.CONTENT_URI;
        String[] projection = {"value", "name", "_id"};
        String selection = "";

        cr.insert(uri, cval);
        debugall();
    }

    public void pushFull(task t) {

        Gson gs = new Gson();

        ContentValues cval = new ContentValues();//TODO
        cval.put("value", gs.toJson(t));
        cval.put("name", t.name);
        Uri uri = LocalTaskProvider.CONTENT_URI;
        String[] projection = {"value", "name", "_id"};
        String selection = "";

        cr.insert(uri, cval);
        debugall();
    }

    public void deleteTask(int id) {
        Uri uri = LocalTaskProvider.CONTENT_URI;
        String[] projection = {"value", "name", "_id"};
        String selection = "_id = ?";
        String[] values = {Integer.toString(id)};
        cr.delete(uri, selection, values);
    }

    public void DELETEALL(String CHECK) {
        Uri uri = LocalTaskProvider.CONTENT_URI;
        if (CHECK == "IAMCOMPLETELYCRAZY") cr.delete(uri, null, null);
    }

    public void editTask(task t, int id) {

        Gson gs = new Gson();

        ContentValues cval = new ContentValues();//TODO
        cval.put("value", gs.toJson(t));
        cval.put("name", t.name);
        Uri uri = LocalTaskProvider.CONTENT_URI;
        String[] projection = {"value", "name", "_id"};
        String selection = "_id = ?";
        String[] values = {Integer.toString(id)};
        cr.update(uri, cval, selection, values);
        debugall();
    }

    public void debugall() {


        Cursor cur = null;
        Uri uri = LocalTaskProvider.CONTENT_URI;
        String[] projection = {"value", "name", "_id"};
        cur = cr.query(uri, projection, null, null, null);
        Log.d("DBDB", DatabaseUtils.dumpCursorToString(cur));
    }
    public void checkstate(int id,Boolean state){
        task t = getData(id);
        t.checked = state;
        editTask(t,id);
    }


    public task getData(int c) {
        debugall();
        Cursor cur = null;
        Uri uri = Uri.parse(LocalTaskProvider.CONTENT_URI + "/" + c);
        String[] projection = {"value", "name", "_id"};
        String selection = "_id = ?";

        String[] values = {Integer.toString(c)};

        cur = cr.query(uri, projection, "_id = ?", values, null);
        Log.d("DBDB", DatabaseUtils.dumpCursorToString(cur));
        Log.d("DBDB", values[0]);
        cur.moveToLast();
        String data = cur.getString(0);
        cur.getColumnNames();
        try {
            task t = new Gson().fromJson(data, task.class);
            t.id = cur.getInt(2);
            return t;
        } catch (Exception e) {
            Log.d("DBDB", "exepted ::" + data + values[0]);
            Log.e("DBDBEE", e.toString());
            return null;
        }


    }

    public task[] getData() {
        Cursor cur = null;
        List<task> taskList = new ArrayList<task>();

        Uri uri = LocalTaskProvider.CONTENT_URI;
        String[] projection = {"value", "name", "_id"};
        String selection = "";
        Gson gs = new Gson();
        cur = cr.query(uri, projection, selection, null, null);
        cur.moveToFirst();
        while (!cur.isAfterLast()) {
            debugall();
            String data = cur.getString(0);
            int id = cur.getInt(2);
            try {
                task t = gs.fromJson(data, task.class);
                t.id = id;
                taskList.add(t);
            } catch (Exception e) {// also catches illegal items in database //TODO create cleanup routine
                Log.d("DBDB", "exepted ::" + data);
            }

            cur.moveToNext();
        }

        return (task[]) taskList.toArray(new task[taskList.size()]);


    }

    public String DaysString(Timestamp deadline) {
        if (deadline == null) return "";
        long time = (System.currentTimeMillis());
        Timestamp tsTemp = new Timestamp(time);
        String lo;
        if (tsTemp.after(deadline)) {
            //overdate
            lo = " over time";
        } else {
            //timeleft
            lo = " left";
        }
        long delta = Math.abs(tsTemp.getTime() - deadline.getTime());

        //1000 is 1s
        //100 000 is about a minute
        //60 60 000 hours
        //60 60 000 * 24   days
        //60 60 000 * 24 *7  weeks
        long minute = 60 * 1000;
        long about = 100 * 1000;
        long hour = 60 * 60 * 1000;
        long day = 60 * 60 * 1000 * 24;
        long week = 60 * 60 * 1000 * 24 * 7;
        String timeS;
        if (delta > (week)) {
            timeS = Math.floor(delta / week) + " week(s) " + Math.floor((delta % week) / day) + " day(s)";
        } else if (delta > (day)) {
            timeS = Math.floor(delta / day) + " day(s) " + Math.floor((delta % day) / hour) + " hour(s)";
        } else if (delta > (hour)) {
            timeS = Math.floor(delta / hour) + " hour(s) " + Math.floor((delta % hour) / minute) + " min(s)";
        } else if (delta > (about)) {
            timeS = Math.floor(delta / minute) + " minute(s)";
        } else {
            timeS = "about a minute";
        }
        return timeS + lo ;

    }
}
