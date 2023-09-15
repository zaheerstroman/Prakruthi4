package com.prakruthi.ui.ui.home.category;

public class HomeCategoryModal {

    private int id;
    private String name;
    private boolean isSelected;
    private String attachment;

    public HomeCategoryModal(int id, String name, String attachment) {
        this.id = id;
        this.name = name;
        this.attachment = attachment;
    }


    public boolean isSelected() {
        return isSelected;
    }

    public void setSelected(boolean selected) {
        isSelected = selected;
    }

    public int getId() {
        return id;
    }

    public String getName() {
        return name;
    }

    public String getAttachment() {
        return attachment;
    }

    public void setId(int id) {
        this.id = id;
    }

    public void setName(String name) {
        this.name = name;
    }

    public void setAttachment(String attachment) {
        this.attachment = attachment;
    }
}
