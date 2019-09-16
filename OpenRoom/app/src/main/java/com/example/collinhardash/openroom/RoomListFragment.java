package com.example.collinhardash.openroom;

import android.content.Context;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ListView;
import android.widget.Spinner;
import android.widget.TextView;

import java.util.ArrayList;

/**
 * Created by Matthew on 12/11/2017.
 */

public class RoomListFragment extends Fragment implements AdapterView.OnItemSelectedListener, AdapterView.OnItemClickListener, View.OnClickListener {
    private Spinner spnOrder;
    private Button btnRefresh;
    private ListView lstRooms;
    private String orderBy;
    private ArrayList<Room> rooms;
    private OnFragmentInteractionListener mListener;

    public RoomListFragment() {
        // Required empty public constructor
    }

    public static RoomListFragment newInstance() {
        RoomListFragment fragment = new RoomListFragment();
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_room_list, container, false);

        btnRefresh = (Button) view.findViewById(R.id.btnRefresh);
        btnRefresh.setOnClickListener(this);


        spnOrder = (Spinner) view.findViewById(R.id.spnOrder);
        lstRooms = (ListView) view.findViewById(R.id.lstRooms);
        spnOrder.setOnItemSelectedListener(this);
        lstRooms.setOnItemClickListener(this);

        return view;
    }

    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
        if (context instanceof OnFragmentInteractionListener) {
            mListener = (OnFragmentInteractionListener) context;
        } else {
            throw new RuntimeException(context.toString()
                    + " must implement OnFragmentInteractionListener");
        }
    }

    @Override
    public void onDetach() {
        super.onDetach();
        mListener = null;
    }

    @Override
    public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {
        mListener.onItemSelected(adapterView, view, i, l);
    }

    @Override
    public void onNothingSelected(AdapterView<?> adapterView) {
        //Empty - nothing to do
    }

    @Override
    public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
        mListener.onItemClick(adapterView, view, i, l);
    }

    @Override
    public void onClick(View view) {
        if (view.getId() == btnRefresh.getId()) {
            mListener.onClick(view);
        }
    }

    /**
     * These are the methods we want our parent activity to have
     */
    public interface OnFragmentInteractionListener {
        void onItemSelected(AdapterView<?> adapterView, View view, int i, long l);
        void onItemClick(AdapterView<?> adapterView, View view, int i, long l);
        void onClick(View view);
    }

    /**
     * Use this to set the room list. Uses an adapter to convert each room to a "room_list_item"
     * @param rooms list of rooms
     */
    public void setList(ArrayList<Room> rooms) {
        RoomAdapter adapter = new RoomAdapter(getActivity(), rooms);
        lstRooms.setAdapter(adapter);
    }

    // this adapter takes an ArrayList of movies and outputs it into a ListView
    public class RoomAdapter extends ArrayAdapter<Room> {

        public RoomAdapter(Context context, ArrayList<Room> rooms) {
            super(context, 0, rooms);
        }

        @Override
        public View getView(int position, View convertView, ViewGroup parent) {
            // Check if an existing view is being reused, otherwise inflate the view
            if (convertView == null) {
                convertView = LayoutInflater.from(getContext()).inflate(R.layout.room_list_item, parent, false);
            }

            Room room = getItem(position);
            TextView roomNumber = (TextView) convertView.findViewById(R.id.txtSeatsTaken);
            TextView openSeats = (TextView) convertView.findViewById(R.id.txtOpenSeats);
            roomNumber.setText(room.getName());
            openSeats.setText(Integer.toString(room.getSeatsTaken()));

            return convertView;
        }
    }
}
