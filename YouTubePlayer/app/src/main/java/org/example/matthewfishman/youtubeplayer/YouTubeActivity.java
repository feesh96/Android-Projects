package org.example.matthewfishman.youtubeplayer;

import android.os.Bundle;
import android.support.constraint.ConstraintLayout;
import android.util.Log;
import android.view.ViewGroup;
import android.widget.Toast;

import com.google.android.youtube.player.YouTubeBaseActivity;
import com.google.android.youtube.player.YouTubeInitializationResult;
import com.google.android.youtube.player.YouTubePlayer;
import com.google.android.youtube.player.YouTubePlayerView;

public class YouTubeActivity extends YouTubeBaseActivity
        implements YouTubePlayer.OnInitializedListener {
    private static final String TAG = "YouTubeActivity";
    static final String GOOGLE_API_KEY = "AIzaSyA051831k2s8mZD6omrIxou0bKuirY87u4";
    static final String YOUTUBE_VIDEO_ID = "mh9B5UJbbRg";
    static final String YOUTUBE_PLAYLIST = "PLJ8cMiYb3G5dHStEexqZ0UaNcVsFwHbb3";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

//        setContentView(R.layout.activity_you_tube);
//        ConstraintLayout constraintLayout = (ConstraintLayout) findViewById(R.id.activity_you_tube);
        ConstraintLayout layout = (ConstraintLayout) getLayoutInflater().inflate(
                R.layout.activity_you_tube, null);
        Log.d(TAG, "onCreate: setting content view");
        setContentView(layout);

//        Button button1 = new Button(this);
//        button1.setLayoutParams(new ConstraintLayout.LayoutParams(300, 80));
//        button1.setText("Dynamic Button!");
//        layout.addView(button1);
        YouTubePlayerView playerView = new YouTubePlayerView(this);
        playerView.setLayoutParams(new ConstraintLayout.LayoutParams(
                ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.MATCH_PARENT));
        layout.addView(playerView);
        playerView.initialize(GOOGLE_API_KEY, this);
    }

    @Override
    public void onInitializationSuccess(YouTubePlayer.Provider provider, YouTubePlayer youTubePlayer, boolean wasRestored) {
        Log.d(TAG, "onInitializationSuccess: provide is: " + provider.getClass().toString());
        Toast.makeText(this, "Initialized Youtube Player successfully", Toast.LENGTH_LONG).show();

        youTubePlayer.setPlaybackEventListener(playbackEventListener);
        youTubePlayer.setPlayerStateChangeListener(playerStateChangeListener);

        if (!wasRestored) {
            youTubePlayer.cueVideo(YOUTUBE_VIDEO_ID);
        }
        youTubePlayer.play();
    }

    @Override
    public void onInitializationFailure(YouTubePlayer.Provider provider,
                                        YouTubeInitializationResult youTubeInitializationResult) {
        final int REQUEST_CODE = 1;

        if (youTubeInitializationResult.isUserRecoverableError()) {
                youTubeInitializationResult.getErrorDialog(this, REQUEST_CODE).show();
        } else {
            String errorMessage = String.format(
                    "There was an error initializing the YoutubePlayer (%1$s)",
                    youTubeInitializationResult.toString());
            Toast.makeText(this, errorMessage, Toast.LENGTH_LONG).show(); //Toast messages appear then fade away
        }
    }

    YouTubePlayer.PlaybackEventListener playbackEventListener = new YouTubePlayer.PlaybackEventListener() {
        @Override
        public void onPlaying() {
            Toast.makeText(YouTubeActivity.this, "Good, video playing ok", Toast.LENGTH_SHORT).show();
        }

        @Override
        public void onPaused() {
            Toast.makeText(YouTubeActivity.this, "Video has paused", Toast.LENGTH_SHORT).show();
        }

        @Override
        public void onStopped() {
            Toast.makeText(YouTubeActivity.this, "Video has stopped", Toast.LENGTH_SHORT).show();
        }

        @Override
        public void onBuffering(boolean b) {

        }

        @Override
        public void onSeekTo(int i) {

        }
    };

    private YouTubePlayer.PlayerStateChangeListener playerStateChangeListener = new YouTubePlayer.PlayerStateChangeListener() {
        @Override
        public void onLoading() {

        }

        @Override
        public void onLoaded(String s) {

        }

        @Override
        public void onAdStarted() {
            Toast.makeText(YouTubeActivity.this, "Click Ad now to make creator rich!", Toast.LENGTH_SHORT).show();
        }

        @Override
        public void onVideoStarted() {
            Toast.makeText(YouTubeActivity.this, "Video has started", Toast.LENGTH_SHORT).show();
        }

        @Override
        public void onVideoEnded() {
            Toast.makeText(YouTubeActivity.this, "Video has ended", Toast.LENGTH_SHORT).show();
        }

        @Override
        public void onError(YouTubePlayer.ErrorReason errorReason) {

        }
    };
}
