package comp5216.sydney.edu.au.rkoi2318_h3;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.util.Log;


public class MainActivity extends AppCompatActivity {
    private final static String LOG_TAG = "MOBILE_MAINACTIVITY";
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
    }

    protected void LaunchPaceCaclulator(View view){
        Log.d(LOG_TAG,"You've clicked on the Lauch Pace button");
    }

    protected void LaunchRunningLog(View view){
        Log.d(LOG_TAG,"You've clicked on the Lauch Running Log Button");
    }

    protected void LaunchMusicPlayer(View view){
        Log.d(LOG_TAG,"Launch Music Player button");
    }

    public void LaunchPedometer(View view){
        Log.d(LOG_TAG,"You've clicked on the Lauch pedometer button");
    }
}
