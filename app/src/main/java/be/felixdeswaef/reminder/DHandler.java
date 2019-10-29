package be.felixdeswaef.reminder;

import android.content.ContentResolver;
import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.DatabaseUtils;
import android.net.Uri;
import android.util.Log;

import com.google.gson.Gson;

import java.util.ArrayList;
import java.util.List;

public class DHandler {
ContentResolver cr;

public DHandler(Context ct){
    cr = ct.getContentResolver();
}
    public void pushQuick(String name){
        task newtask = new task(name);
        Gson gs = new Gson();

        ContentValues cval = new ContentValues();//TODO
        cval.put("value",gs.toJson(newtask));
        cval.put("name",newtask.name);
        Uri uri =LocalTaskProvider.CONTENT_URI;
        String[] projection  = {"value","name","_id"} ;
        String selection  = "" ;

        cr.insert(uri,cval);
        debugall();
    }
    public void debugall(){


        Cursor cur = null;
        Uri uri = LocalTaskProvider.CONTENT_URI;
        String[] projection  = {"value","name","_id"} ;
        cur = cr.query(uri,projection,null,null,null);
        Log.d("DBDB", DatabaseUtils.dumpCursorToString(cur));
    }
    public task getData(int c){
        debugall();
        Cursor cur = null;
        Uri uri = Uri.parse(LocalTaskProvider.CONTENT_URI+"/"+c);
        String[] projection  = {"value","name","_id"} ;
        String selection  = "_id = ?" ;

        String[] values  = {Integer.toString(c)} ;

        cur = cr.query(uri,projection,"_id = ?",values,null);

        cur.moveToLast();
        String data = cur.getString(0);
        cur.getColumnNames();
        try
        {
            return (task) new Gson().fromJson(data,task.class);
        }
        catch (Exception e)

        {
            Log.d("DBDB","exepted ::" + data);
            return null;
        }




    }
    public task[] getData(){
        Cursor cur = null;
        List<task> taskList = new ArrayList<task>();

        Uri uri =LocalTaskProvider.CONTENT_URI;
        String[] projection  = {"value","name","_id"} ;
        String selection  = "" ;
        Gson gs = new Gson();
        cur = cr.query(uri,projection,selection,null,null);
        cur.moveToFirst();
        while (!cur.isAfterLast()){
            debugall();
            String data = cur.getString(0);
            try {
                taskList.add(gs.fromJson(data,task.class));
            }
            catch (Exception e) {
                Log.d("DBDB","exepted ::" + data);
            }

            cur.moveToNext();
        }

        return (task[]) taskList.toArray(new task[taskList.size()]);


    }
}
