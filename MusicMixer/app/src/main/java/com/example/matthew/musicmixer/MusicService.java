package com.example.matthew.musicmixer;

import android.app.Service;
import android.content.Context;
import android.content.Intent;
import android.os.Binder;
import android.os.Handler;
import android.os.IBinder;
import android.support.annotation.Nullable;
import android.util.Log;

/**
 * Created by Matthew on 10/24/2017.
 */

public class MusicService extends Service {
    // private reference to the private class that return reference to the service
    private final IBinder iBinder = new MyBinder();

    // we delegate all of the playback related aspects in a separate class
    MusicPlayer musicPlayer;

    // once the service is built...
    @Override
    public void onCreate() {
        super.onCreate();
    }

    public void setMusicPlayer(Context context, Mix mix) {
        musicPlayer = new MusicPlayer(this, mix, context);
    }

    public void startMusic() {
        if (musicPlayer != null)
            musicPlayer.playMusic();
    }

    public void pauseMusic() {
        if (musicPlayer != null)
            musicPlayer.pauseMusic();
    }

    public void resumeMusic() {
        if (musicPlayer != null)
            musicPlayer.resumeMusic();
    }

    public void restartMusic() { if (musicPlayer != null)
        musicPlayer.restartMusic(); }

    public int getMusicStatus() {
        if (musicPlayer != null) {
            Log.d("MusicService", "Main Song Status: " + musicPlayer.getMusicStatus());
            return musicPlayer.getMusicStatus();
        }
        else
            return -1;
    }

    public void updatePicture(String sound) {
        Intent intent = new Intent("newSound");
        intent.putExtra("newSound", sound);

        // this broadcast will eventually deliver the updated label to MainActivity
        sendBroadcast(intent);
    }

    @Nullable
    @Override
    public IBinder onBind(Intent intent) {
        Log.d("MusicService", "onBind() inside service");
        return iBinder;
    }

    //service learns that it is unbound.
    // We pause it with a 3 second delay
    @Override
    public boolean onUnbind(Intent intent) {
        // we use Handler as a way to run a delayed operation
        Handler handler = new Handler();
        handler.post(new Runnable() {
            @Override
            public void run() {
                //we pause playback
                pauseMusic();
                //stop the service...
                stopSelf();
            }

            //after 3000ms (3 seconds)
        });
        return super.onUnbind(intent);
    }

    // need to create a small private class that can return reference to the service
    public class MyBinder extends Binder {
        MusicService getService() {
            return MusicService.this;
        }
    }
}
