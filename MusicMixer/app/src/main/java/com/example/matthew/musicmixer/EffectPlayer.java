package com.example.matthew.musicmixer;

import android.content.Context;
import android.media.MediaPlayer;
import android.util.Log;
import android.os.Handler;

/**
 * Created by Matthew on 10/26/2017.
 */

public class EffectPlayer implements MediaPlayer.OnCompletionListener {
    int effectTime;
    int effectNum;
    int currentPosition;
    String effect;
    Mix mix;
    Context context;
    MediaPlayer effectPlayer;
    Runnable effectRunnable;
    Handler handler;
    MusicService musicService;

    public EffectPlayer(int effectNum, Mix givenMix, Context givenContext, MusicService service) {
        mix = givenMix;
        context = givenContext;
        handler = new Handler();
        musicService = service;
        switch (effectNum) {
            case 1:
                this.effectNum = 1;
                effectTime = mix.effect1Time;
                this.effect = mix.effect1;
                break;
            case 2:
                this.effectNum = 2;
                effectTime = mix.effect2Time;
                this.effect = mix.effect2;
                break;
            case 3:
                this.effectNum = 3;
                effectTime = mix.effect3Time;
                this.effect = mix.effect3;
                break;
            default:
                break;
        }
    }

    public void start() {
        effectRunnable = new Runnable() {
            @Override
            public void run() {
                Log.d("EffectPlayer", "Start()");

                effectPlayer.start();
                musicService.updatePicture(effect);
            }
        };

        if (effectTime > 0)
            handler.postDelayed(effectRunnable, effectTime);
        else
            handler.post(effectRunnable);
    }

    public void setEffect1() {
        switch (mix.effect1) {
            case "Clapping":
                effectPlayer = MediaPlayer.create(context, R.raw.clapping);
                break;
            case "Cheering":
                effectPlayer = MediaPlayer.create(context, R.raw.cheering);
                break;
            default:
                effectPlayer = MediaPlayer.create(context, R.raw.lestgohokies);
                break;
        }

        effectPlayer.setOnCompletionListener(this);
    }

    public void setEffect2() {
        switch (mix.effect2) {
            case "Clapping":
                effectPlayer = MediaPlayer.create(context, R.raw.clapping);
                break;
            case "Cheering":
                effectPlayer = MediaPlayer.create(context, R.raw.cheering);
                break;
            default:
                effectPlayer = MediaPlayer.create(context, R.raw.lestgohokies);
                break;
        }

        effectPlayer.setOnCompletionListener(this);
    }

    public void setEffect3() {
        switch (mix.effect3) {
            case "Clapping":
                effectPlayer = MediaPlayer.create(context, R.raw.clapping);
                break;
            case "Cheering":
                effectPlayer = MediaPlayer.create(context, R.raw.cheering);
                break;
            default:
                effectPlayer = MediaPlayer.create(context, R.raw.lestgohokies);
                break;
        }

        effectPlayer.setOnCompletionListener(this);
    }

    public void pause(int ms) {
        Log.d("EffectPlayer", "Pause()");
        handler.removeCallbacks(effectRunnable);
        handler.post(new Runnable() {
            @Override
            public void run() {
                effectPlayer.pause();
                currentPosition = effectPlayer.getCurrentPosition();
            }
        });
        effectTime = ms;
    }

    public void resume() {
        if (currentPosition < (effectTime + effectPlayer.getDuration())) {
            effectPlayer.seekTo(currentPosition);
            start();
        }
    }

    public void restart() { //finish, hit restart without pause
        Log.d("EffectPlayer", "OnRestart()");

        switch (effectNum) {
            case 1:
                pause(mix.effect1Time);
                break;
            case 2:
                pause(mix.effect2Time);
                break;
            case 3:
                pause(mix.effect3Time);
                break;
            default:
                break;
        }

        effectPlayer.seekTo(0);
    }

    @Override
    public void onCompletion(MediaPlayer mediaPlayer) {
        Log.d("EffectPlayer", "OnCompletion()");
        musicService.updatePicture(mix.song);
    }
}
