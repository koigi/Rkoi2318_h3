package comp5216.sydney.edu.au.rkoi2318_h3;

import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.widget.EditText;
import android.widget.TextView;
import android.view.View;

public class PaceCalculator extends AppCompatActivity {
    protected RunEntry aRun;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_pace_calculator);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        this.aRun = new RunEntry(); //Initialise a Temp run object to use for the calculations

    }

    protected void updateViewText(){
        String paceResultsText = null;
        EditText durationET = (EditText) findViewById(R.id.durationInput);
        EditText distanceET = (EditText) findViewById(R.id.distanceInput);

        //If there's no distance or duration calculations then return the default message
        if(0 == this.aRun.distance || 0 == this.aRun.duration){
            paceResultsText = "Distance: 0 meters\nDuration: 0 seconds \nPace: 0 minutes per kilometer\nSpeed: 0 ";
        }else{
            paceResultsText = "Distance: "+ this.aRun.distance +" meters\nDuration: "+ this.aRun.duration +
                    " seconds \nPace: "+ this.aRun.getPace() +" minutes per kilometer\nSpeed: "+ this.aRun.getSpeed() +
                    " meters per second";
        }
        TextView resultsText = (TextView) findViewById(R.id.paceResultsText);
        resultsText.setText(paceResultsText);
    }

    public void goBackToMain(View v){
        //Reset the Run entry
        this.finish();
    }

    public void updateViewCalculations(View v){
        this.updateViewText();
    }
}
