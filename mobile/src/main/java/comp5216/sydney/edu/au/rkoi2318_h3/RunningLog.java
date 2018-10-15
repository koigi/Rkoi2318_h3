package comp5216.sydney.edu.au.rkoi2318_h3;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.widget.ArrayAdapter;
import android.widget.BaseAdapter;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.TextView;
import android.view.ViewGroup;
import android.view.View;
import java.util.List;

public class RunningLog extends AppCompatActivity {
    protected ArrayAdapter<RunEntry> runsAdapter;

    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.running_log);
        updateListOfRuns();
    }

    private void updateListOfRuns(){
        List<RunEntry> allRuns = MainActivity.totalRuns;
        ListView runList = (ListView) findViewById(R.id.TotalRunsListView);
        if(allRuns.isEmpty()){
            Log.d("RUNNING_LOG", "all runnis empty :(");
        }else{
            runsAdapter = new ArrayAdapter<RunEntry>(this,android.R.layout.simple_list_item_1, allRuns);
            runList.setAdapter(runsAdapter);
        }
    }

    public void goBackToMain(View v){
        this.finish();
    }

    protected class RunAdapter extends BaseAdapter{
        @Override
        public String getItem(int position) {
            //TODO Convert the Run Entry into a String
            RunEntry aRun = MainActivity.totalRuns.get(position);
            String pageLog = "Start Date:";
            return pageLog;
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
                convertView = getLayoutInflater().inflate(android.R.layout.simple_list_item_1, container, false);
            }
            ((TextView) convertView.findViewById(android.R.id.text1)).setText(this.getItem(position));
            return convertView;
        }
    }
}
