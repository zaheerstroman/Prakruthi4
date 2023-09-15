package com.prakruthi.ui.ui.productPage;

public class SavePaymentDetails_Address_BottomSheet_Recycler_Adaptor_Model {

    private String name;
    private String address;
    private int Defualt;

    public SavePaymentDetails_Address_BottomSheet_Recycler_Adaptor_Model() {

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

    public SavePaymentDetails_Address_BottomSheet_Recycler_Adaptor_Model(String name, String address, int Defualt,int id) {
        this.name = name;
        this.address = address;
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

}
