package com.prakruthi.ui.ui.profile.myorders;

public class TrackingOrdersModel {

    private Boolean placed;
    private Boolean confirmed;
    private Boolean dispatched;
    private Boolean arrived;

    private Boolean Outfordelivery;


    public TrackingOrdersModel(Boolean placed, Boolean confirmed, Boolean dispatched, Boolean arrived, Boolean Outfordelivery) {
        this.placed = placed;
        this.confirmed = confirmed;
        this.dispatched = dispatched;
        this.arrived = arrived;
        this.Outfordelivery = Outfordelivery;
    }

    public Boolean getPlaced() {
        return placed;
    }

    public void setPlaced(Boolean placed) {
        this.placed = placed;
    }

    public Boolean getConfirmed() {
        return confirmed;
    }

    public void setConfirmed(Boolean confirmed) {
        this.confirmed = confirmed;
    }

    public Boolean getDispatched() {
        return dispatched;
    }

    public void setDispatched(Boolean dispatched) {
        this.dispatched = dispatched;
    }

    public Boolean getArrived() {
        return arrived;
    }

    public void setArrived(Boolean arrived) {
        this.arrived = arrived;
    }
}
