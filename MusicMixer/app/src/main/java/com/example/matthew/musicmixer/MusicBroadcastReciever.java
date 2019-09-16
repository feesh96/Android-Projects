package com.example.matthew.musicmixer;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;

/**
 * Created by Matthew on 10/29/2017.
 */

public class MusicBroadcastReciever extends BroadcastReceiver {
    PlayActivity playActivity;

    public MusicBroadcastReciever(PlayActivity playActivity) {
            this.playActivity = playActivity;
        }

    @Override
    public void onReceive(Context context, Intent intent) {
        //...extract the song name
        String picture = intent.getStringExtra("newSound");
        //...pass it to main activity
        playActivity.updatePicture(picture);
    }
}
