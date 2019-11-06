package be.felixdeswaef.reminder;

import android.arch.lifecycle.ViewModelProviders;
import android.content.ContentResolver;
import android.database.Cursor;
import android.net.Uri;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.google.gson.Gson;
import com.google.gson.JsonParseException;

import java.util.ArrayList;
import java.util.List;

import static be.felixdeswaef.reminder.MainActivity.handler;

public class tasklist extends Fragment {

    private TasklistViewModel mViewModel;
    RecyclerView recyclerView;
    RecyclerView.LayoutManager layoutManager;
    taskadapter mAdapter;
    ContentResolver cr;

    public static tasklist newInstance() {
        return new tasklist();
    }

    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container,
                             @Nullable Bundle savedInstanceState) {
        return inflater.inflate(R.layout.tasklist_fragment, container, false);

    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        mViewModel = ViewModelProviders.of(this).get(TasklistViewModel.class);
        // TODO: Use the ViewModel
        recyclerView = (RecyclerView) getView().findViewById(R.id.RECYCLER);
        // use this setting to improve performance if you know that changes
        // in content do not change the layout size of the RecyclerView
        //recyclerView.setHasFixedSize(true);

        // use a linear layout manager
        layoutManager = new LinearLayoutManager(this.getContext());
        recyclerView.setLayoutManager(layoutManager);
        cr = getContext().getContentResolver();

        // specify an adapter (see also next example)


        mAdapter = new taskadapter(handler.getData());

        recyclerView.setAdapter(mAdapter);
        final SwipeRefreshLayout pullToRefresh = getView().findViewById(R.id.pullToRefresh);
        pullToRefresh.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener() {
            @Override
            public void onRefresh() {
                reload(); // your code
                pullToRefresh.setRefreshing(false);
            }
        });




    }

    public void reload (){


        mAdapter.Update(handler.getData());

    }

}
