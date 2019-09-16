package com.example.collinhardash.openroom;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.content.res.Configuration;
import android.os.Bundle;
import android.support.v4.app.FragmentTransaction;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;

/**
 * Created by Matthew on 11/26/2017.
 */

public class MainActivity extends AppCompatActivity implements RoomListFragment.OnFragmentInteractionListener, View.OnClickListener, AdapterView.OnItemSelectedListener, AdapterView.OnItemClickListener {
    private RoomFragment roomFragment;
    private RoomListFragment roomListFragment;
    private String orderBy;
    private Room currentRoom;
    private DatabaseManager manager;
    ArrayList<Room> rooms;

    private ResponseReceiver receiver;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        rooms = getBuildingInfo();
        if(rooms.size() > 0) {
            currentRoom = rooms.get(0);
        }
        orderBy = "distance";
        orderRooms();
        roomFragment = new RoomFragment();
        roomListFragment = new RoomListFragment();

        if (getResources().getConfiguration().orientation == Configuration.ORIENTATION_PORTRAIT) {
            getSupportFragmentManager().beginTransaction().replace(R.id.fragment_container, roomListFragment).commit();
        }
        else {
            if (savedInstanceState != null) {
                currentRoom = (Room) savedInstanceState.getSerializable("currentRoom");

            }
            if(currentRoom != null) {
                roomFragment.setRoom(currentRoom);
            }
            getSupportFragmentManager().beginTransaction().replace(R.id.fragment_container_list, roomListFragment).commit();
            getSupportFragmentManager().beginTransaction().replace(R.id.fragment_container_room, roomFragment).commit();
        }
    }

    @Override
    public void onSaveInstanceState(Bundle outState) {
        super.onSaveInstanceState(outState);
        Log.d("MainActivity", "SaveInstanceState");
        outState.putSerializable("currentRoom", currentRoom);
    }

    @Override
    public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {
        orderBy = adapterView.getItemAtPosition(i).toString();
        orderRooms();
        roomListFragment.setList(rooms);
    }

    @Override
    public void onNothingSelected(AdapterView<?> adapterView) {
        //Do nothing
    }

    @Override
    public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
        Object item = adapterView.getItemAtPosition(i);

        if (item instanceof Room) {
            currentRoom = (Room) item;
            roomFragment.setRoom(currentRoom);

            FragmentTransaction transaction = getSupportFragmentManager().beginTransaction();
            if (getResources().getConfiguration().orientation == Configuration.ORIENTATION_PORTRAIT) {
                transaction.replace(R.id.fragment_container, roomFragment);
                transaction.addToBackStack(null);
            }
            else {
                roomFragment.updateRoomFragment(currentRoom);
            }

            transaction.commit();
        }
    }

    @Override
    public void onClick(View view) {
        //rooms = getBuildingInfo();
        //orderRooms();
        //roomListFragment.setList(rooms);

        IntentFilter filter = new IntentFilter(ResponseReceiver.ACTION_RESP);
        filter.addCategory(Intent.CATEGORY_DEFAULT);
        receiver = new ResponseReceiver();
        registerReceiver(receiver, filter);


        Intent msgIntent = new Intent(this, DownloadJSON.class);
        msgIntent.setAction(DownloadJSON.ACTION_DOWNLOAD);
        startService(msgIntent);
    }

    //Call service to get the building info
    public ArrayList<Room> getBuildingInfo() {
        //rooms = new ArrayList<Room>();
        //Collin & Andy get the info, create "Room" objects and return
        manager = new DatabaseManager(getApplicationContext());
        manager.open();
        rooms = manager.getAllRecords();
        manager.close();
        //Test data------------------------------
        //for (int i = 1; i <= 10; i++) {
            //int size = (int) Math.floor(Math.random() * 1000);
            //int capacity = size + 20;
            //double distance = Math.floor(Math.random() * 1000);
            //rooms.add(new Room("Room " + i, capacity, size, distance));
        //}
        return rooms;
    }

    /**
     * Puts rooms in order of "order" spinner selection
     */
    public void orderRooms() {
        switch (orderBy) {
            case "Room Number":
                Collections.sort(rooms, new Comparator<Room>() {
                    @Override
                    public int compare(Room room, Room t1) {
                        return room.getName().compareTo(t1.getName());
                    }
                });
                break;
            case "Capacity":
                Collections.sort(rooms, new Comparator<Room>() {
                    @Override
                    public int compare(Room room, Room t1) {
                        return Integer.compare(room.getCapacity(), t1.getCapacity());
                    }
                });
                break;
            case "Open Seats":
                Collections.sort(rooms, new Comparator<Room>() {
                    @Override
                    public int compare(Room room, Room t1) {
                        return Integer.compare(room.getSeatsOpen(), t1.getSeatsOpen());
                    }
                });
                break;
            default:    //Distance
                Collections.sort(rooms, new Comparator<Room>() {
                    @Override
                    public int compare(Room room, Room t1) {
                        return Double.compare(room.getDistance(), t1.getDistance());
                    }
                });
                break;
        }
    }

    public class ResponseReceiver extends BroadcastReceiver {
        public static final String ACTION_RESP =
                "movies_fetched";

        @Override
        public void onReceive(Context context, Intent intent) {
            //Log.d("info received");
            rooms = getBuildingInfo();
            orderRooms();
            roomListFragment.setList(rooms);
        }
    }

}
