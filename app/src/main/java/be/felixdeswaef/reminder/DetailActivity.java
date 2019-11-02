package be.felixdeswaef.reminder;

import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.support.v4.app.FragmentManager;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.View;

import com.google.gson.Gson;

public class DetailActivity extends AppCompatActivity implements TaskDetailFragment.OnFragmentInteractionListener {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_detail);
        Toolbar toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        Intent intent = getIntent();
        task data = (new Gson().fromJson(intent.getStringExtra("data"),task.class));
        TaskDetailFragment detailfragment = new TaskDetailFragment();
        Log.e("SETDATA","isnull : "+ ((data==null)? "true":"false"));
        detailfragment.setData(data);
        getSupportFragmentManager().beginTransaction().replace(R.id.fram, detailfragment).commit();

    }

    @Override
    public void onFragmentInteraction(Uri uri) {

    }
}
