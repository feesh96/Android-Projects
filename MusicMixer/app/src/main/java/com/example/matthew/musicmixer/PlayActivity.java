package com.example.matthew.musicmixer;

import android.content.ComponentName;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.content.ServiceConnection;
import android.os.Bundle;
import android.os.IBinder;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

/**
 * Created by Matthew on 10/23/2017.
 */

public class PlayActivity extends AppCompatActivity implements View.OnClickListener, ServiceConnection {
    Mix mix;
    String lastPlayed;

    TextView txtSongTitle;
    Button btnRestart;
    Button btnPlayPause;
    ImageView image;

    MusicService musicService;
    MusicBroadcastReciever musicBroadCastReceiver;
    Intent startMusicServiceIntent;
    boolean isInitialized = false;
    boolean isBound = false;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_play);

        Intent intent = getIntent();
        mix = intent.getParcelableExtra("Mix");

        txtSongTitle = (TextView) findViewById(R.id.txtSongTitle);
        btnRestart = (Button) findViewById(R.id.btnRestart);
        btnPlayPause = (Button) findViewById(R.id.btnPlayPause);
        image = (ImageView) findViewById(R.id.image);

        btnPlayPause.setOnClickListener(this);
        btnRestart.setOnClickListener(this);
        txtSongTitle.setText(mix.song);

        //restoring info for the boolean and the 'song' label
        if (savedInstanceState != null) {
            isInitialized = savedInstanceState.getBoolean("isInitialized");
            mix = savedInstanceState.getParcelable("Mix");
            txtSongTitle.setText(mix.song);
            lastPlayed = savedInstanceState.getString("Last Played");
        }

        startMusicServiceIntent = new Intent(this, MusicService.class);

        // if not started we go ahead and start it
        if (!isInitialized) {
            startService(startMusicServiceIntent);
            isInitialized = true;
        }

        //also registering the broadcast receiver
        musicBroadCastReceiver = new MusicBroadcastReciever(this);

        if (lastPlayed == null)
            lastPlayed = mix.song;
        updatePicture(lastPlayed);
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
    }

    @Override
    protected void onResume() {
        super.onResume();

        //is service is initialized and not bound we bind to it
        if (isInitialized && !isBound) {
            bindService(startMusicServiceIntent, this, Context.BIND_AUTO_CREATE);
        }

        // registering the broadcast receiver
        registerReceiver(musicBroadCastReceiver, new IntentFilter("newSound"));
    }

    @Override
    protected void onSaveInstanceState(Bundle outState) {
        super.onSaveInstanceState(outState);

        outState.putBoolean("isInitialized", isInitialized);
        outState.putParcelable("Mix", mix);
        outState.putString("Last Played", lastPlayed);
        outState.putBoolean("isBound", isBound);
    }

    @Override
    public void onClick(View view) {
        if (isBound) {
            if (view.getId() == btnPlayPause.getId()) {
                switch (musicService.getMusicStatus()) {
                    // 0 - means not playing, so we start it then label the button 'pause'
                    case 0:
                        musicService.startMusic();
                        btnPlayPause.setText("Pause");
                        break;
                    // 1 - means playing, we pause it and then label the button 'resume'
                    case 1:
                        musicService.pauseMusic();
                        btnPlayPause.setText("Resume");
                        break;
                    case 2:
                        // 2 - means paused, we resume it and then label the button 'pause'
                        musicService.resumeMusic();
                        btnPlayPause.setText("Pause");
                        updatePicture(lastPlayed);
                        break;
                }
            }
            else if (view.getId() == btnRestart.getId()) {
                musicService.restartMusic();
                btnPlayPause.setText("Start");
                updatePicture(mix.song);
            }
        }
    }

    //Get the service and set status from service data
    @Override
    public void onServiceConnected(ComponentName name, IBinder binder_to_service) {
        Log.d("PlayActivity", "onServiceConnected()");

        // the binder object gets us an object that we use to extract a reference to service
        MusicService.MyBinder binder = (MusicService.MyBinder) binder_to_service;
        musicService = binder.getService();
        if (musicService.musicPlayer == null) {
            musicService.setMusicPlayer(this, mix);
        }
        isBound = true;

        //depending on what it is doing (start, pause, resume) we set the label on the button
        switch (musicService.getMusicStatus()) {
            case 0:
                btnPlayPause.setText("Start");
                break;
            case 1:
                btnPlayPause.setText("Pause");
                break;
            case 2:
                btnPlayPause.setText("Resume");
                break;
        }
    }

    // once disconnected we set the reference to null
    @Override
    public void onServiceDisconnected(ComponentName name) {
        Log.d("PlayActivity", "onServiceDisconnected()");
        musicService = null;
        isBound = false;
    }

    public void updatePicture(String picture) {
        lastPlayed = picture;

        switch (picture) {
            case "Cheering":
                //Not loading... pic too big?
                image.setBackgroundResource(R.drawable.cheering);
                break;
            case "Clapping":
                image.setBackgroundResource(R.drawable.clapping);
                break;
            case "Enter Sandman":
                image.setBackgroundResource(R.drawable.enter_sandman);
                break;
            case "Go Tech Go!":
                image.setBackgroundResource(R.drawable.go_tech_go);
                break;
            case "Hokey Pokey":
                image.setBackgroundResource(R.drawable.hokie_pokie);
                break;
            default: //lets go hokies
                image.setBackgroundResource(R.drawable.lets_go_hokies);
                break;
        }
    }
}
