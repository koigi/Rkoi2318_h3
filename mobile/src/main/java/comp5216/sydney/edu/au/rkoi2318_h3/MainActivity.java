package comp5216.sydney.edu.au.rkoi2318_h3;

import android.arch.persistence.room.Room;
import android.content.Intent;
import android.icu.text.RelativeDateTimeFormatter;
import android.os.AsyncTask;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.util.Log;

import java.util.Calendar;
import java.util.Date;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.ThreadLocalRandom;
import java.util.ArrayList;
import java.util.List;
import java.util.TimeZone;

import comp5216.sydney.edu.au.rkoi2318_h3.AppDB;
import comp5216.sydney.edu.au.rkoi2318_h3.RunEntry;


public class MainActivity extends AppCompatActivity {
    private final static String LOG_TAG = "MOBILE_MAINACTIVITY";

    protected AsyncTask<Void, Void, List<RunEntry>> totalRunsTask;
    protected List<RunEntry> totalRuns;
    protected AppDB appDB;

    private class RunDBTask extends AsyncTask<Void, Void, List<RunEntry>>{
        @Override
        protected List<RunEntry> doInBackground(Void... voids) {
            List<RunEntry> response;

            response=  appDB.runDao().fetchAllRunEntries();
            Log.i("THREAD_LOG","This is the size of the all entries fetched" + Integer.toString(response.size()) );
            return  response;
        }

        @Override
        protected void onPostExecute(List<RunEntry> runEntries) {
            super.onPostExecute(runEntries);
        }
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        appDB = AppDB.getDatabase(this.getApplicationContext());
        //Database already has sample information in it.
        //populateDBSample();
        RunDBTask aTask =new RunDBTask();
        this.totalRunsTask = aTask.execute();
        this.totalRuns = new RunDBTask().doInBackground();
        Log.i("DB_LOG","THINGS IN DB BELOW HEHE");

        if(null == totalRuns){
            Log.i("DB_LOG","MEH it's empty lol ");
        }else{

            for(int i =0; i < totalRuns.size(); i++){
                Log.i("DB_LOG",this.returnLogLine(totalRuns.get(i)));
            }
        }


    }

    @Override
    protected void onDestroy(){
        super.onDestroy();
    }

    public void LaunchPaceCaclulator(View view){
        Log.d(LOG_TAG,"You've clicked on the Lauch Pace button");
        Intent anIntent = new Intent(this, PaceCalculator.class);
        this.startActivity(anIntent);
    }

    public void LaunchRunningLog(View view){
        Log.d(LOG_TAG,"You've clicked on the Lauch Running Log Button");
    }

    public void LaunchMusicPlayer(View view){
        Log.d(LOG_TAG,"Launch Music Player button");
    }

    public void LaunchPedometer(View view){
        Log.d(LOG_TAG,"You've clicked on the Lauch pedometer button");
    }

    public void populateDBSample(){
        List<RunEntry> runs = new ArrayList<RunEntry>();
        for(int i = 0; i < 2; i++){
            Date startD = getRandomEventDate(true);
            Date endD = getRandomEventDate(false);
            int dist = ThreadLocalRandom.current().nextInt(3000, 20000);

            final RunEntry aRunEntry = new RunEntry(startD, endD, dist);

            new Thread(new Runnable() {
                @Override
                public void run() {
                    appDB.runDao().insertRun(aRunEntry);
                    Log.d(LOG_TAG, "I've entered into the DB yay!");
                }
            }).start();

            Log.d(LOG_TAG,this.returnLogLine(aRunEntry));
        }


    }


    private Date getRandomEventDate(Boolean starting){
        int minuteEntry;
        if(starting){
            minuteEntry = ThreadLocalRandom.current().nextInt(12, 26);
        }else{
            minuteEntry = ThreadLocalRandom.current().nextInt(30, 50);
        }
        int secondEntry = ThreadLocalRandom.current().nextInt(11, 59);
        //Initialise a start calendar and get the Date Object
        TimeZone tz = TimeZone.getTimeZone("Australia/Sydney");
        Calendar calReturn = Calendar.getInstance(tz);

        calReturn.set(2018, 8, 13, 12, minuteEntry,secondEntry );
        Date start = calReturn.getTime();
        //Log.d(LOG_TAG, "This is the date that I've created " + start.toString());
        return start;
    }

    private String returnLogLine(RunEntry a){
        String  logLine = "This is the Run ID " + a.getRunId();
        logLine += "\n This is the Start time " + a.getStartTime().toString();
        logLine += "\n This is the End time " + a.getEndTime().toString();
        logLine += "\n This is the Duration " + Integer.toString(a.duration);
        logLine += "\n This is the Distance Covered in Meters " + Integer.toString(a.getDistance());
        logLine += "\n This is the pace measured in Minutes per Kilometer " + RunEntry.formatToString(a.getPace());
        logLine += "\n This is the Speed Measured in Meters per second " + RunEntry.formatToString(a.getSpeed());

        return  logLine;
    }

}
