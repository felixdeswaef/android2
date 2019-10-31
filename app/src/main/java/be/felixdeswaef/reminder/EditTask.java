package be.felixdeswaef.reminder;

import android.app.DatePickerDialog;
import android.app.TimePickerDialog;
import android.content.ContentValues;
import android.content.Intent;
import android.icu.util.Calendar;
import android.net.Uri;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.DragEvent;
import android.view.View;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.ProgressBar;
import android.widget.SeekBar;
import android.widget.TextView;
import android.widget.TimePicker;

import com.google.gson.Gson;

import java.sql.Timestamp;
import java.text.SimpleDateFormat;
import java.util.Locale;

public class EditTask extends AppCompatActivity {


    boolean updating = false ;
    //Timestamp seltime =
    @Override

    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_edit_task);
        final TextView deadline = findViewById(R.id.deadline);
        final Calendar myCalendar = Calendar.getInstance();
        final TimePickerDialog.OnTimeSetListener time = new TimePickerDialog.OnTimeSetListener() {
            @Override
            public void onTimeSet(TimePicker timePicker, int hours, int mins) {
                myCalendar.set(Calendar.HOUR_OF_DAY, hours);
                myCalendar.set(Calendar.MINUTE, mins);
                updateLabel(deadline,myCalendar);
            }
        };
        final DatePickerDialog.OnDateSetListener date = new DatePickerDialog.OnDateSetListener() {

            @Override
            public void onDateSet(DatePicker view, int year, int monthOfYear,
                                  int dayOfMonth) {
                // TODO Auto-generated method stub
                myCalendar.set(Calendar.YEAR, year);
                myCalendar.set(Calendar.MONTH, monthOfYear);
                myCalendar.set(Calendar.DAY_OF_MONTH, dayOfMonth);
                updateLabel(deadline,myCalendar);
                new TimePickerDialog(EditTask.this,time,8,0,true).show();

            }

        };
        ((SeekBar)findViewById(R.id.progress)).setOnSeekBarChangeListener(new SeekBar.OnSeekBarChangeListener() {
            @Override
            public void onProgressChanged(SeekBar seekBar, int i, boolean b) {
                ((TextView)findViewById(R.id.proglabel)).setText(seekBar.getProgress()+"% completed");
            }

            @Override
            public void onStartTrackingTouch(SeekBar seekBar) {

            }

            @Override
            public void onStopTrackingTouch(SeekBar seekBar) {

            }


        });




        deadline.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View v) {
                // TODO Auto-generated method stub
                new DatePickerDialog(EditTask.this, date, myCalendar
                        .get(Calendar.YEAR), myCalendar.get(Calendar.MONTH),
                        myCalendar.get(Calendar.DAY_OF_MONTH)).show();
            }
        });



        Intent intent = getIntent();
        if (intent.hasExtra("data")){
            updating = true;
            task data = (new Gson().fromJson(intent.getStringExtra("data"),task.class));
            ((TextView) findViewById(R.id.name)).setText(data.name);
            ((TextView) findViewById(R.id.editpagetitle)).setText("edit task");
            //(TextView) findViewById(R.id.name)).setText(data.name);
        }



    }

    private void updateLabel(TextView deadline,Calendar myCalendar) {
        String myFormat = "dd/MM/yy 'at' hh:mm"; //In which you need put here
        SimpleDateFormat sdf = new SimpleDateFormat(myFormat, Locale.UK);

        deadline.setText(sdf.format(myCalendar.getTime()));
    }


    public task read(){
        task d = new task("name");
        d.name = ((TextView) findViewById(R.id.name)).getText().toString();
        //d.deadline
        return d;
    }
    public void commit(){
        task d = read();
        if (updating){
           // MainActivity.handler.pushLong(d)
        }
        else{

        }
    }

}
