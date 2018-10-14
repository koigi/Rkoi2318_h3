package comp5216.sydney.edu.au.rkoi2318_h3;

import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
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

    public void goBackToMain(View v){
        //Reset the Run entry
        this.finish();
    }

    public void updateViewCalculations(View v){
        this.finish(); // FOr now until Ive developed more of the application
    }
}
