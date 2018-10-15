package comp5216.sydney.edu.au.rkoi2318_h3;


import android.content.ComponentName;
import android.content.Context;
import android.content.Intent;
import android.content.ServiceConnection;
import android.os.IBinder;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import java.util.ArrayList;

import java.util.Collections;
import java.util.Comparator;
import android.net.Uri;
import android.content.ContentResolver;
import android.database.Cursor;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.TextView;
import android.view.MenuItem;
import android.widget.MediaController.MediaPlayerControl;


public class MusicPlayer extends AppCompatActivity implements MediaPlayerControl {
    private ArrayList<Tune> songList;
    private ListView songView;
    private TuneService musicSrv;
    private Intent playIntent;
    private boolean musicBound=false;
    private MusicController controller;


    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.music_player);
        songView = (ListView)findViewById(R.id.song_list);
        songList = new ArrayList<Tune>();
        getSongList();

        Collections.sort(songList, new Comparator<Tune>(){
            public int compare(Tune a, Tune b){
                return a.getTitle().compareTo(b.getTitle());
            }
        });

        SongAdapter songAdt = new SongAdapter(this, songList);
        songView.setAdapter(songAdt);
        setController();
    }

    private ServiceConnection musicConnection = new ServiceConnection(){

        @Override
        public void onServiceConnected(ComponentName name, IBinder service) {
            TuneService.MusicBinder binder = (TuneService.MusicBinder)service;
            //get service
            musicSrv = binder.getService();
            //pass list
            musicSrv.setList(songList);
            musicBound = true;
        }

        @Override
        public void onServiceDisconnected(ComponentName name) {
            musicBound = false;
        }
    };

    public void getSongList() {
        //retrieve song info
        ContentResolver musicResolver = getContentResolver();
        Uri musicUri = android.provider.MediaStore.Audio.Media.EXTERNAL_CONTENT_URI;
        Cursor musicCursor = musicResolver.query(musicUri, null, null, null, null);

        if(musicCursor!=null && musicCursor.moveToFirst()){
            //get columns
            int titleColumn = musicCursor.getColumnIndex
                    (android.provider.MediaStore.Audio.Media.TITLE);
            int idColumn = musicCursor.getColumnIndex
                    (android.provider.MediaStore.Audio.Media._ID);
            int artistColumn = musicCursor.getColumnIndex
                    (android.provider.MediaStore.Audio.Media.ARTIST);
            //add songs to list
            do {
                long thisId = musicCursor.getLong(idColumn);
                String thisTitle = musicCursor.getString(titleColumn);
                String thisArtist = musicCursor.getString(artistColumn);
                songList.add(new Tune(thisId, thisTitle, thisArtist));
            }
            while (musicCursor.moveToNext());
        }
    }

    @Override
    protected void onStart() {
        super.onStart();
        if(playIntent==null){
            playIntent = new Intent(this, TuneService.class);
            bindService(playIntent, musicConnection, Context.BIND_AUTO_CREATE);
            startService(playIntent);
        }
    }

    public void songPicked(View view){
        musicSrv.setSong(Integer.parseInt(view.getTag().toString()));
        musicSrv.playSong();
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case R.id.action_shuffle:
                //shuffle
                break;
            case R.id.action_end:
                stopService(playIntent);
                musicSrv=null;
                System.exit(0);
                break;
        }
        return super.onOptionsItemSelected(item);
    }

    @Override
    protected void onDestroy() {
        stopService(playIntent);
        musicSrv=null;
        super.onDestroy();
    }

    private void setController(){
        controller = new MusicController(this);
        controller.setPrevNextListeners(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                playNext();
            }
        }, new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                playPrev();
            }
        });
    }

    //play next
    private void playNext(){
        musicSrv.playNext();
        controller.show(0);
    }

    //play previous
    private void playPrev(){
        musicSrv.playPrev();
        controller.show(0);
    }

    @Override
    public boolean canSeekBackward() {
        return true;
    }
    @Override
    public boolean canSeekForward() {
        return true;
    }
    @Override
    public boolean canPause(){
        return true;
    }
    @Override
    public void pause() {
        musicSrv.pausePlayer();
    }
    @Override
    public void seekTo(int pos) {
        musicSrv.seek(pos);
    }
    @Override
    public void start() {
        musicSrv.go();
    }
    @Override
    public boolean isPlaying() {
        if(musicSrv!=null && musicBound)
        return musicSrv.isPng();
        return false;
    }
    @Override
    public int getAudioSessionId(){
        return 0;
    }
    @Override
    public int getBufferPercentage(){
        return 0;
    }
    @Override
    public int getCurrentPosition() {
        if(musicSrv!=null && musicBound && musicSrv.isPng())
        return musicSrv.getPosn();
  else return 0;
    }
    @Override
    public int getDuration() {
        if(musicSrv!=null && musicBound && musicSrv.isPng())
        return musicSrv.getDur();
        else return 0;
    }


    public class SongAdapter extends BaseAdapter {
        private ArrayList<Tune> songs;
        private LayoutInflater songInf;

        public SongAdapter(Context c, ArrayList<Tune> theSongs){
            songs=theSongs;
            songInf=LayoutInflater.from(c);
        }
        @Override
        public int getCount() {
            return songs.size();
        }

        @Override
        public Object getItem(int arg0) {
            // TODO Auto-generated method stub
            return null;
        }

        @Override
        public long getItemId(int arg0) {
            // TODO Auto-generated method stub
            return 0;
        }

        @Override
        public View getView(int arg0, View arg1, ViewGroup arg2) {
            //map to song layout
            LinearLayout songLay = (LinearLayout)songInf.inflate
                    (R.layout.tune, arg2, false);
            //get title and artist views
            TextView songView = (TextView)songLay.findViewById(R.id.song_title);
            TextView artistView = (TextView)songLay.findViewById(R.id.song_artist);
            //get song using position
            Tune currSong = songs.get(arg0);
            //get title and artist strings
            songView.setText(currSong.getTitle());
            artistView.setText(currSong.getArtist());
            //set position as tag
            songLay.setTag(arg0);
            return songLay;
        }

    }

}
