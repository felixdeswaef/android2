package be.felixdeswaef.reminder;

import android.content.ContentValues;
import android.content.Intent;
import android.net.Uri;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;

import com.google.gson.Gson;

import java.sql.Timestamp;

public class EditTask extends AppCompatActivity {


    boolean updating = false ;
    @Override

    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_edit_task);
        Intent intent = getIntent();
        if (intent.hasExtra("data")){
            updating = true;
            task data = (new Gson().fromJson(intent.getStringExtra("data"),task.class));

        }


    }

}
