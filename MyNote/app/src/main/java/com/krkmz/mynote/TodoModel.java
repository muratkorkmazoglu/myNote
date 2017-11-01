package com.krkmz.mynote;


public class TodoModel {


    private String listItemsName;
    private String checked;

    public TodoModel(){}

    public TodoModel(String listItemsName, String checked) {

        this.checked=checked;
        this.listItemsName=listItemsName;
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
