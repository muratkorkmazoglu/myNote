package com.krkmz.mynote;


public class TodoModel {

    private String listTitle;
    private String listItemsName;
    private String checked;

    public TodoModel(){}

    public TodoModel(String listTitle, String listItemsName, String checked) {
        this.listTitle = listTitle;
        this.checked=checked;
        this.listItemsName=listItemsName;
    }

    public String getListTitle() {
        return listTitle;
    }

    public void setListTitle(String listTitle) {
        this.listTitle = listTitle;
    }

    public String getListItemsName() {
        return listItemsName;
    }

    public void setListItemsName(String listItemsName) {
        this.listItemsName = listItemsName;
    }

    public String getChecked() {
        return checked;
    }

    public void setChecked(String checked) {
        this.checked = checked;
    }
}
