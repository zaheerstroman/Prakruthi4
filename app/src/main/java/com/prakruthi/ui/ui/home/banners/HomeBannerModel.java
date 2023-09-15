package com.prakruthi.ui.ui.home.banners;

public class HomeBannerModel {
    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getAttachment() {
        return attachment;
    }

    public void setAttachment(String attachment) {
        this.attachment = attachment;
    }

    private int id;
    private String attachment;

    public HomeBannerModel(int id,String attachment)
    {
        this.id = id;
        this.attachment = attachment;
    }
}
