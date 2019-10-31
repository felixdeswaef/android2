package be.felixdeswaef.reminder;

import android.content.Context;
import android.net.Uri;
import android.support.constraint.ConstraintLayout;
import android.support.v7.widget.RecyclerView;
import android.view.KeyEvent;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.widget.CompoundButton;
import android.widget.TextView;
import android.widget.Toast;

import com.google.gson.Gson;


public class taskadapter extends RecyclerView.Adapter<taskadapter.MyViewHolder> {
    private task[] mDataset;

    // Provide a reference to the views for each data item
    // Complex data items may need more than one view per item, and
    // you provide access to all the views for a data item in a view holder
    public static class MyViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener, CompoundButton.OnCheckedChangeListener , RecyclerView.OnItemTouchListener {
        // each data item is just a string in this case
        public ConstraintLayout taskvieuw;

        public MyViewHolder(ConstraintLayout v) {
            super(v);
            taskvieuw = v;
        }

        @Override
        public void onClick(View view) {

        }
        public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
            // deal with catStatus change
        }


        @Override
        public boolean onInterceptTouchEvent(RecyclerView rv, MotionEvent e) {
            return false;
        }

        @Override
        public void onTouchEvent(RecyclerView rv, MotionEvent e) {

        }

        @Override
        public void onRequestDisallowInterceptTouchEvent(boolean disallowIntercept) {

        }
    }

    // Provide a suitable constructor (depends on the kind of dataset)
    public taskadapter(task[] myDataset) {
        mDataset = myDataset;
    }

    // Create new views (invoked by the layout manager)
    @Override
    public taskadapter.MyViewHolder onCreateViewHolder(ViewGroup parent,
                                                       int viewType) {
        // create a new view
        ConstraintLayout v = (ConstraintLayout) LayoutInflater.from(parent.getContext())
                .inflate(R.layout.tasklayout, parent, false);
        //...

        //TODO

        MyViewHolder vh = new MyViewHolder(v);

        return vh ;
    }
    public void Update(task[] incoming){
            mDataset = incoming;
            this.notifyDataSetChanged();
    }
    // Replace the contents of a view (invoked by the layout manager)
    @Override
    public void onBindViewHolder(MyViewHolder holder, int position) {
        // - get element from your dataset at this position
        // - replace the contents of the view with that element
        if (position!=mDataset.length) {
            ((TextView) holder.taskvieuw.getViewById(R.id.tasktext)).setText(mDataset[position].name + " " + position);
            ( holder.taskvieuw.getViewById(R.id.tasktext)).setVisibility(View.VISIBLE);
            ( holder.taskvieuw.getViewById(R.id.checkBox)).setVisibility(View.VISIBLE);
            ( holder.taskvieuw.getViewById(R.id.deadline)).setVisibility(View.VISIBLE);
            ( holder.taskvieuw.getViewById(R.id.sub)).setVisibility(View.VISIBLE);
            ( holder.taskvieuw.getViewById(R.id.orderbuton)).setVisibility(View.VISIBLE);
            ( holder.taskvieuw.getViewById(R.id.newfield)).setVisibility(View.INVISIBLE);
        }
        else{
            ( holder.taskvieuw.getViewById(R.id.tasktext)).setVisibility(View.INVISIBLE);
            ( holder.taskvieuw.getViewById(R.id.checkBox)).setVisibility(View.INVISIBLE);
            ( holder.taskvieuw.getViewById(R.id.deadline)).setVisibility(View.INVISIBLE);
            ( holder.taskvieuw.getViewById(R.id.sub)).setVisibility(View.INVISIBLE);
            ( holder.taskvieuw.getViewById(R.id.orderbuton)).setVisibility(View.INVISIBLE);
            ( holder.taskvieuw.getViewById(R.id.newfield)).setVisibility(View.VISIBLE);

            final TextView tv = ((TextView) holder.taskvieuw.getViewById(R.id.newfield));
                    tv.setOnKeyListener(new View.OnKeyListener() {
                public boolean onKey(View v, int keyCode, KeyEvent event) {
                    // If the event is a key-down event on the "enter" button
                    if ((event.getAction() == KeyEvent.ACTION_DOWN) &&
                            (keyCode == KeyEvent.KEYCODE_ENTER)) {
                            MainActivity.handler.pushQuick(tv.getText().toString());
                            Update(MainActivity.handler.getData());




                        return true;
                    }
                    return false;
                }
            });



        }

    }

    // Return the size of your dataset (invoked by the layout manager)
    @Override
    public int getItemCount() {
        return mDataset.length+1;
    }
}