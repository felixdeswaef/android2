package be.felixdeswaef.reminder;

import android.content.Context;
import android.net.Uri;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ProgressBar;
import android.widget.TextView;


/**
 * A simple {@link Fragment} subclass.
 * Activities that contain this fragment must implement the
 * {@link TaskDetailFragment.OnFragmentInteractionListener} interface
 * to handle interaction events.
 * Use the {@link TaskDetailFragment#newInstance} factory method to
 * create an instance of this fragment.
 */
public class TaskDetailFragment extends Fragment {
    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER

    task data ;

    // TODO: Rename and change types of parameters
    public String mParam1;


    private OnFragmentInteractionListener mListener;

    public TaskDetailFragment() {
        // Required empty public constructor
    }

    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * //@param param1 Parameter 1.
     *
     * @return A new instance of fragment TaskDetailFragment.
     */
    // TODO: Rename and change types and number of parameters
    public static TaskDetailFragment newInstance() {
        TaskDetailFragment fragment = new TaskDetailFragment();

        return fragment;
    }
    @Override
    public void onResume() {
        super.onResume();
        this.setData(MainActivity.handler.getData(this.data.id));
        this.reprint(getView());
        //Code to refresh listview
    }
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getArguments() != null) {


        }
    }
       void reprint(View view){
        TextView tv1 = view.findViewById(R.id.tasktitle);
        tv1.setText(data.name);
        ((ProgressBar)view.findViewById(R.id.DETAILPROGRESS)).setProgress(data.completion);
        ((TextView)view.findViewById(R.id.DESCRIPTION)).setText(data.description);
        ((TextView)view.findViewById(R.id.COMPLETION)).setText(data.completion + "% completed");
        ((TextView)view.findViewById(R.id.DEADLINE)).setText(MainActivity.handler.DaysString(data.deadline));
    }
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_task_detail, container, false);
        reprint(view);
        final int id = data.id;
        Log.d("dbg",id + " i d ."+data.name);
        ((Button) view.findViewById(R.id.edittaskbuton)).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                MainActivity.handler.inf.EditDetails(id);
            }
        });

        return view;
    }

    // TODO: Rename method, update argument and hook method into UI event


    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
        if (context instanceof OnFragmentInteractionListener) {
            mListener = (OnFragmentInteractionListener) context;
        } else {
            throw new RuntimeException(context.toString()
                    + " must implement OnFragmentInteractionListener");
        }
    }

    @Override
    public void onDetach() {
        super.onDetach();
        mListener = null;
    }

    /**
     * This interface must be implemented by activities that contain this
     * fragment to allow an interaction in this fragment to be communicated
     * to the activity and potentially other fragments contained in that
     * activity.
     * <p>
     * See the Android Training lesson <a href=
     * "http://developer.android.com/training/basics/fragments/communicating.html"
     * >Communicating with Other Fragments</a> for more information.
     */
    public interface OnFragmentInteractionListener {
        // TODO: Update argument type and name
        void onFragmentInteraction(Uri uri);
    }
    public void setData(task sdata){

        data=sdata;
        //Log.e("SETDATA",(sdata==null)? "true":"false");
    }

}
