package com.example.collinhardash.openroom;

/**
 * Created by Collin Hardash on 12/14/2017.
 */

import java.io.Serializable;

/**
 * Created by Matthew on 12/11/2017.
 */

/**
 * Holds info about the rooms
 */
public class Room implements Serializable {
    private String name;
    private String building;
    private String room;
    private int capacity;
    private int floorNumber;
    private int seatsTaken;
    private int seatsOpen;
    private double distance;
    private boolean isOccupied;



    public Room(String room, String building, int capacity, int currentSize, double distance) {
        this.name = building + ' ' + room;
        this.capacity = capacity;
        this.seatsTaken = currentSize;
        this.seatsOpen = (capacity - currentSize);
        this.isOccupied = true;
        this.distance = distance;
        this.floorNumber = Integer.parseInt(room.substring(0, 1));
    }

    //Setters and getters ------------------------------------------------------------
    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public int getCapacity() {
        return capacity;
    }

    public void setCapacity(int capacity) {
        this.capacity = capacity;
    }

    public int getSeatsTaken() {
        return seatsTaken;
    }

    public void setSeatsTaken(int seatsTaken) {
        this.seatsTaken = seatsTaken;
    }

    public int getSeatsOpen() {
        return seatsOpen;
    }

    public String getBuilding() {
        return building;
    }

    public String getRoom() {
        return room;
    }

    public void setSeatsOpen(int seatsOpen) {
        this.seatsOpen = seatsOpen;
    }

    public boolean isOccupied() {
        return isOccupied;
    }

    public void setOccupied(boolean occupied) {
        isOccupied = occupied;
    }

    public double getDistance() {
        return distance;
    }

    public void setDistance(double distance) {
        this.distance = distance;
    }

    public void setBuilding(String building) {
        this.building = building;
    }

    public void setRoom(String room) {
        this.room = room;
    }

    public int getFloorNumber() {
        return floorNumber;
    }

    public void setFloorNumber(int floorNumber) {
        this.floorNumber = Integer.parseInt(this.room.substring(0, 1));

    }
}
