package com.example.collinhardash.openroom;

import android.support.v4.app.Fragment;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

/**
 * Created by Matthew on 12/11/2017.
 */

public class RoomFragment extends Fragment {
    private String seatsTaken;
    private String capacity;
    private String roomNumber;
    private String floorNumber;
    private TextView txtSeatsTaken;
    private TextView txtCapacity;
    private TextView txtRoomNumber;
    private TextView txtFloorNumber;
    public RoomFragment() {
        //Required empty constructor
    }

    public static RoomFragment newInstance(String roomNumber, String capacity, String seatsTaken, String floorNumber) {
        RoomFragment fragment = new RoomFragment();
        Bundle args = new Bundle();
        args.putString("roomNumber", roomNumber);
        args.putString("capacity", capacity);
        args.putString("seatsTaken", seatsTaken);
        args.putString("floorNumber",floorNumber);
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getArguments() != null) {
            roomNumber = getArguments().getString("roomNumber");
            capacity = getArguments().getString("capacity");
            seatsTaken = getArguments().getString("seatsTaken");
            floorNumber = getArguments().getString("floorNumber");
        }
    }

    /**
     * Creates the view. MUST CALL setRoom(room) FIRST
     * @param inflater
     * @param container
     * @param savedInstanceState
     * @return
     */
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_room, container, false);

        txtRoomNumber = (TextView) view.findViewById(R.id.txtRoomNumber);
        txtCapacity = (TextView) view.findViewById(R.id.txtCapacity);
        txtSeatsTaken = (TextView) view.findViewById(R.id.txtSeatsTaken);
        txtFloorNumber = (TextView) view.findViewById(R.id.txtFloor);

        //Sets room data
        txtRoomNumber.setText(roomNumber);
        txtCapacity.setText(capacity);
        txtSeatsTaken.setText(seatsTaken);
        txtFloorNumber.setText(floorNumber);
        return view;
    }

    @Override
    public void onSaveInstanceState(Bundle outState) {
        super.onSaveInstanceState(outState);
        outState.putString("roomNumber", roomNumber);
        outState.putString("capacity", capacity);
        outState.putString("seatsTaken", seatsTaken);
        outState.putString("floorNumber",floorNumber);
    }

    /**
     * This is how to set up the fragment before it's been inflated
     * @param room is the room to set the fragment with
     */
    public void setRoom(Room room) {
        seatsTaken = Integer.toString(room.getSeatsTaken());
        capacity = Integer.toString(room.getCapacity());
        roomNumber = room.getName();
    }

    /**
     * This is how to update the fragment after it's been inflated
     * @param room is the room data to update the fragment with
     */
    public void updateRoomFragment(Room room) {
        seatsTaken = Integer.toString(room.getSeatsTaken());
        capacity = Integer.toString(room.getCapacity());
        roomNumber = room.getName();
        floorNumber = Integer.toString(room.getFloorNumber());
        txtSeatsTaken.setText(seatsTaken);
        txtCapacity.setText(capacity);
        txtRoomNumber.setText(roomNumber);
        txtFloorNumber.setText(floorNumber);

    }
}

