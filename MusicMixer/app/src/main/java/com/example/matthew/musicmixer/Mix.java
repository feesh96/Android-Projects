package com.example.matthew.musicmixer;

import android.os.Parcel;
import android.os.Parcelable;

/**
 * Created by Matthew on 10/23/2017.
 */

public class Mix implements Parcelable{
    int data;

    String song;
    String effect1;
    String effect2;
    String effect3;
    int effect1Time;    //in milliseconds
    int effect2Time;
    int effect3Time;

    public Mix() {
        this.song = "Go Tech Go!";
        this.effect1 = "Cheering";
        this.effect2 = "Cheering";
        this.effect3 = "Cheering";
        this.effect1Time = 0;
        this.effect2Time = 0;
        this.effect3Time = 0;
    }

    public Mix(String song, String effect1, String effect2, String effect3,
               int effect1Time, int effectTime2, int effect3Time) {
        this.song = song;
        this.effect1 = effect1;
        this.effect2 = effect2;
        this.effect3 = effect3;
        this.effect1Time = effect1Time;
        this.effect2Time = effectTime2;
        this.effect3Time = effect3Time;
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel parcel, int i) {
        parcel.writeString(song);
        parcel.writeString(effect1);
        parcel.writeString(effect2);
        parcel.writeString(effect3);
        parcel.writeInt(effect1Time);
        parcel.writeInt(effect2Time);
        parcel.writeInt(effect3Time);
    }

    // this is used to regenerate your object. All Parcelables must have a CREATOR that implements these two methods
    public static final Parcelable.Creator<Mix> CREATOR = new Parcelable.Creator<Mix>() {
        public Mix createFromParcel(Parcel in) {
            return new Mix(in);
        }

        public Mix[] newArray(int size) {
            return new Mix[size];
        }
    };

    // example constructor that takes a Parcel and gives you an object populated with it's values
    private Mix(Parcel in) {
        song = in.readString();
        effect1 = in.readString();
        effect2 = in.readString();
        effect3 = in.readString();
        effect1Time = in.readInt();
        effect2Time = in.readInt();
        effect3Time = in.readInt();
    }

}
