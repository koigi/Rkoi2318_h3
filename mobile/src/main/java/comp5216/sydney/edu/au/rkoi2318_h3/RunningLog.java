package comp5216.sydney.edu.au.rkoi2318_h3;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.LayoutInflater;
import android.widget.ArrayAdapter;
import android.widget.BaseAdapter;
import android.widget.EditText;
import android.widget.ListAdapter;
import android.widget.ListView;
import android.widget.TextView;
import android.view.ViewGroup;
import android.view.View;

import org.w3c.dom.Text;

import java.util.List;

public class RunningLog extends AppCompatActivity {
    protected RunAdapter runsAdapter;

    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.running_log);
        updateListOfRuns();
    }

    @Override
    protected void onResume() {
        super.onResume();
        this.updateListOfRuns();
    }

    @Override
    public void onWindowFocusChanged (boolean hasFocus){
        super.onWindowFocusChanged(true);
        if(hasFocus)this.updateListOfRuns();
    }

    private void updateListOfRuns(){
        List<RunEntry> allRuns = MainActivity.totalRuns;
        ListView runList = (ListView) findViewById(R.id.TotalRunsListView);
        if(allRuns.isEmpty()){
            Log.d("RUNNING_LOG", "all runnis empty :(");
        }else{
            runsAdapter = new RunAdapter(this);
            runList.setAdapter(runsAdapter);
        }
    }

    public void goBackToMain(View v){
        this.finish();
    }

    public void createNewRun(View v){
        Log.d("RUNNING_LOG","You've clicked on the Create Run");
        Intent anIntent = new Intent(this, NewManualRun.class);
        this.startActivity(anIntent);
    }

    protected class RunAdapter extends BaseAdapter{
        public String[] runViewRow = new String[2];
        public Context aContext;

        public RunAdapter(Context myContext){
            this.aContext = myContext;
        }

        @Override
        public String[] getItem(int position) {

            RunEntry aRun = MainActivity.totalRuns.get(position);
            this.runViewRow[0] = "Start Date:"+ aRun.getStartTime().toString();
            this.runViewRow[1] = "Distance: "+Integer.toString(aRun.getDistance())+
                    "(m)\nDuration: "+ Integer.toString(aRun.duration)+
                    "(s)\nPace: "+ RunEntry.formatToString(aRun.getPace()) +
                    "(Min/Km)\n Speed:"+ RunEntry.formatToString(aRun.getSpeed()) +"(m/s)";

            return this.runViewRow;
        }

        @Override
        public int getCount() {
            return MainActivity.totalRuns.size();
        }
        //We're not using normal IDs for the runs so no need to have this in there.
        @Override
        public long  getItemId(int position) {
            return 0;
        }

        public View getView(int position, View convertView, ViewGroup container){
            if (convertView == null) {
                //convertView = getLayoutInflater().inflate(R.layout.run_entry_item, container, false);
                convertView = LayoutInflater.from(aContext).inflate(R.layout.run_entry_item, container, false);
            }
            TextView runDateTV = convertView.findViewById(R.id.runDate);
            runDateTV.setText(this.getItem(position)[0]);

            TextView statsViewTV = convertView.findViewById(R.id.statsRow);
            statsViewTV.setText(this.getItem(position)[1]);

            return convertView;
        }
    }

}
