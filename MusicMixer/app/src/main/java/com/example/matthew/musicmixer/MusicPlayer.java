package com.example.matthew.musicmixer;

import android.content.Context;
import android.media.MediaPlayer;

/**
 * Created by Matthew on 10/24/2017.
 */

public class MusicPlayer implements MediaPlayer.OnCompletionListener{
    private MusicService musicService;
    MediaPlayer player;
    EffectPlayer effect1Player;
    EffectPlayer effect2Player;
    EffectPlayer effect3Player;
    Context context;
    Mix mix;

    // seek possition
    int currentPosition = 0;
    //0: before starts 1: playing 2: paused
    private int musicStatus = 0;

    public MusicPlayer(MusicService musicService, Mix mix, Context context) {
        this.musicService = musicService;
        this.context = context;
        this.mix = mix;

        effect1Player = new EffectPlayer(1, mix, context, musicService);
        effect2Player = new EffectPlayer(2, mix, context, musicService);
        effect3Player = new EffectPlayer(3, mix, context, musicService);

        //build the media player
        setSong(mix.song);
        effect1Player.setEffect1();
        effect2Player.setEffect2();
        effect3Player.setEffect3();
    }

    //starts playing
    public void playMusic() {
        musicService.updatePicture(mix.song);

        //setting up a listener so we can control and see what is going on with the player
        player.setOnCompletionListener(this);

        player.start();
        effect1Player.start();
        effect2Player.start();
        effect3Player.start();
        musicStatus = 1;
    }

    public void pauseMusic() {
        if (player != null && player.isPlaying()) {
            player.pause();

            currentPosition = player.getCurrentPosition();
            effect1Player.pause(mix.effect1Time - currentPosition);
            effect2Player.pause(mix.effect2Time - currentPosition);
            effect3Player.pause(mix.effect3Time - currentPosition);

            musicStatus = 2;
        }
    }

    public void resumeMusic() {
        if (player != null) {
            //resume to the saved position
            player.seekTo(currentPosition);

            //start player
            effect1Player.resume();
            effect2Player.resume();
            effect3Player.resume();
            player.start();

            //udpate status
            musicStatus = 1;
        }
    }

    public void restartMusic() {
        effect1Player.restart();
        effect2Player.restart();
        effect3Player.restart();
        pauseMusic();
        player.seekTo(0);
        currentPosition = 0;
    }

    @Override
    public void onCompletion(MediaPlayer mediaPlayer) {
        //clean out the music player
        player.release();
        player = null;
    }

    public int getMusicStatus() {
        return musicStatus;
    }

    public void setSong(String song) {
        switch(song) {
            case "Hokey Pokey":
                player = MediaPlayer.create(context, R.raw.hokeypokey);
                break;
            case "Enter Sandman":
                player = MediaPlayer.create(context, R.raw.entersandman);
                break;
            default:
                player = MediaPlayer.create(context, R.raw.gotechgo);
                break;
        }
    }
}
