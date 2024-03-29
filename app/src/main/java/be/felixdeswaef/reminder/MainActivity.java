package be.felixdeswaef.reminder;


import android.content.ClipData;
import android.content.ContentResolver;
import android.content.ContentValues;
import android.content.Context;
import android.content.Intent;
import android.database.Cursor;
import android.database.DatabaseUtils;
import android.net.Uri;
import android.provider.CalendarContract;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.View;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.TextView;

import com.google.gson.Gson;

public class MainActivity extends AppCompatActivity implements TaskDetailFragment.OnFragmentInteractionListener {

    ContentResolver cr;
    static DHandler handler;
    FragmentManager fragmag;

    @Override

    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        Toolbar toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        FloatingActionButton fab = findViewById(R.id.fab);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                EditTask();

            }
        });
        fragmag = getSupportFragmentManager();
        cr = getContentResolver();
        calls intf = new calls() {
            @Override
            public void ShowDetails(int id) {
                loaddetails(id);
            }

            @Override
            public void EditDetails(int id) {
                EditTask(id);
            }
        };
        handler = new DHandler(this, intf);


    }

    public void EditTask() {
        EditTask(-1);
    }

    public void EditTask(int id) {
        Intent editIntent = new Intent(this, EditTask.class);
        //Log.d("dbg",id + "num");
        if (id != -1) {
            editIntent.putExtra("data", new Gson().toJson(handler.getData(id)));
            Log.d("dbg", "added stuff in there");
        }
        startActivity(editIntent);
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_main, menu);



        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_settings) {
            return true;
        }
        if (id == R.id.DBDEL){
            handler.DELETEALL("IAMCOMPLETELYCRAZY");

        }

        return super.onOptionsItemSelected(item);
    }

    public void loaddetails(int id) {
        Log.e("SETDATA", "isnull : " + ((handler.getData(id) == null) ? "true" : "false"));


        if (findViewById(R.id.fram1) == null) {
            Log.e("DATA FROM HANDLER", handler.getData(id).name);
            Intent detailIntent = new Intent(this, DetailActivity.class);
            detailIntent.putExtra("data", new Gson().toJson(handler.getData(id)));
            startActivity(detailIntent);


        } else {
            Log.e("DATA FROM HANDLER", handler.getData(id).name);
            TaskDetailFragment detailfragment = new TaskDetailFragment();
            detailfragment.setData(handler.getData(id));
            fragmag.beginTransaction().replace(R.id.fram1, detailfragment).commit();
        }


    }

    @Override
    public void onFragmentInteraction(Uri uri) {

    }


}
