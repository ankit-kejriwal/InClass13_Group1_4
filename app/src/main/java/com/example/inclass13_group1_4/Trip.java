package com.example.inclass13_group1_4;

public class Trip {
    String tripname;
    City city;

    public Trip() {
    }

    public Trip(String tripname, City city) {
        this.tripname = tripname;
        this.city = city;
    }

    public String getTripname() {
        return tripname;
    }

    public void setTripname(String tripname) {
        this.tripname = tripname;
    }

    public City getCity() {
        return city;
    }

    public void setCity(City city) {
        this.city = city;
    }

    @Override
    public String toString() {
        return "Trip{" +
                "tripname='" + tripname + '\'' +
                ", city=" + city +
                '}';
    }
}
