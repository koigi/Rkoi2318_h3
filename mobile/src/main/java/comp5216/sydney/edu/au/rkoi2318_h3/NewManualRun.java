package comp5216.sydney.edu.au.rkoi2318_h3;

import android.annotation.TargetApi;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;

import android.app.DatePickerDialog;
import android.app.TimePickerDialog;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.TimePicker;

import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.TimeZone;

@TargetApi(24)
public class NewManualRun extends AppCompatActivity implements View.OnClickListener{

    Button btnRunStartDate, btnRunStartTime;
    EditText runStartTime, runStartDate , manualDistance,manualDuration ;
    RunEntry aRun;
    protected AppDB appDB;

    protected int runYear, runMonth, runDay, runHour, runMinute;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_new_manual_run);
        aRun = new RunEntry();
        appDB = AppDB.getDatabase(this.getApplicationContext());
        btnRunStartDate = (Button)findViewById(R.id.btnRunStartDate);
        btnRunStartTime = (Button)findViewById(R.id.btnRunStartTime);

        runStartTime = (EditText)findViewById(R.id.runStartTime);
        runStartDate = (EditText)findViewById(R.id.runStartDate);
        btnRunStartDate.setOnClickListener(this);
        btnRunStartTime.setOnClickListener(this);


    }



    public void onClick(View v){
        TimeZone tz = TimeZone.getTimeZone("Australia/Sydney");

        if(v == btnRunStartDate){

            final Calendar c = Calendar.getInstance(tz);
            runYear = c.get(Calendar.YEAR);
            runMonth = c.get(Calendar.MONTH);
            runDay = c.get(Calendar.DAY_OF_MONTH);
            runHour = c.get(Calendar.HOUR);
            runMinute = c.get(Calendar.MINUTE);

            DatePickerDialog datePickerDialog = new DatePickerDialog(this,
                    new DatePickerDialog.OnDateSetListener() {
                        @Override
                        public void onDateSet(DatePicker view, int year, int month, int day) {

                            String aDateString = Integer.toString(day) + "/" + Integer.toString(month+1) + "/" +Integer.toString(year);
                            runStartDate.setText(aDateString);
                        }
                    }, runYear, runMonth, runDay);
            datePickerDialog.show();
        }else if(v== btnRunStartTime){
            final Calendar c = Calendar.getInstance(tz);
            runYear = c.get(Calendar.YEAR);
            runMonth = c.get(Calendar.MONTH);
            runDay = c.get(Calendar.DAY_OF_MONTH);
            runHour = c.get(Calendar.HOUR);
            runMinute = c.get(Calendar.MINUTE);


            TimePickerDialog timePickerDialog = new TimePickerDialog(this,
                    new TimePickerDialog.OnTimeSetListener() {

                        @Override
                        public void onTimeSet(TimePicker view, int hour,
                                              int minute) {

                            runStartTime.setText(hour + ":" + minute);
                        }
                    }, runHour, runMinute ,false);
            timePickerDialog.show();
        }
    }


    //Could have gone into the onclick above but Meh
    public void btnSaveEntry(View v){
        manualDistance = (EditText)findViewById(R.id.manualDistance);
        manualDuration = (EditText)findViewById(R.id.manualDuration);

        String enteredDistance, enteredDuration, startDate, startTime;
        enteredDistance = manualDistance.getText().toString();
        enteredDuration = manualDuration.getText().toString();
        startDate = runStartDate.getText().toString();
        startTime = runStartTime.getText().toString();

        if(startTime.isEmpty() || enteredDistance.isEmpty() || startDate.isEmpty() || enteredDuration.isEmpty()){
            Log.e("MANUAL_RUN","You haven't entered some text");
        }else{
            DateFormat dateFormat = new SimpleDateFormat("dd/mm/yyyy hh:mm");
            String fullDate = startDate + " "+ startTime;
            try {
                aRun.setStartTime(dateFormat.parse(fullDate));
                aRun.setDistance(Integer.parseInt(enteredDistance));
                aRun.setDuration(Integer.parseInt(enteredDuration));
                aRun.getSpeed(); aRun.getPace(); //Just to ensure that they're calculated and not null
            }catch(ParseException e){
                Log.e("MANUAL_RUN","e.getMessage()");
            }

            new Thread(new Runnable() {
                @Override
                public void run() {
                    appDB.runDao().insertRun(aRun);
                    Log.d("MANUAL_RUN", "Sample Data Entered into Database "+ aRun.startTime.toString());
                }
            }).start();
        }
        this.finish();

    }

    //Could have gone into the onclick above but Meh
    public void goBackToMain(View v){
        //Reset the Run entry
        this.finish();
    }
}
