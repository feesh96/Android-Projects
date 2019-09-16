package org.example.matthewfishman.youtubeplayer;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;

import com.google.android.youtube.player.YouTubeStandalonePlayer;

/**
 * Created by Matthew on 1/5/2017.
 */

public class StandaloneActivity extends AppCompatActivity implements View.OnClickListener {
    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_standalone);

        Button btnPlayVideo = (Button) findViewById(R.id.btnPlayVideo);
        Button btnPlayPlaylist = (Button) findViewById(R.id.btnPlayList);

        btnPlayPlaylist.setOnClickListener(this);     //For two other ways to do this, see section
        btnPlayVideo.setOnClickListener(this);        //8, lecture #120 of the Udemy course
    }

    @Override
    public void onClick(View view) {
        Intent intent = null;

        switch (view.getId()) {
            case R.id.btnPlayVideo:
                intent = YouTubeStandalonePlayer.createVideoIntent(
                        this, YouTubeActivity.GOOGLE_API_KEY, YouTubeActivity.YOUTUBE_VIDEO_ID, 0, true, false);
                break;
            case R.id.btnPlayList:
                intent = YouTubeStandalonePlayer.createPlaylistIntent(
                        this, YouTubeActivity.GOOGLE_API_KEY, YouTubeActivity.YOUTUBE_PLAYLIST, 0, 0, true, false);
                break;
            default:
        }

        if (intent != null) {
            startActivity(intent);
        }
    }
}
