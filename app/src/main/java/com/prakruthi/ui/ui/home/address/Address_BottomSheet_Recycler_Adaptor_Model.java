package com.prakruthi.ui.ui.home.address;

public class Address_BottomSheet_Recycler_Adaptor_Model {
    private String name;
    private String address;
    private String state;
    private String city;
    private int Defualt;

    public Address_BottomSheet_Recycler_Adaptor_Model() {

    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    private int id;

    public int getDefualt() {
        return Defualt;
    }

    public void setDefualt(int defualt) {
        Defualt = defualt;
    }

    public Address_BottomSheet_Recycler_Adaptor_Model(String name, String address, String state, String city, int Defualt,int id) {
        this.name = name;
        this.address = address;
        this.state = state;
        this.city = city;
        this.Defualt = Defualt;
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public String getState() {
        return state;
    }

    public void setState(String state) {
        this.state = state;
    }

    public String getCity() {
        return city;
    }

    public void setCity(String city) {
        this.city = city;
    }
}