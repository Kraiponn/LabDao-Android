package com.ksn.kraiponn.labdao.manager;

public class IcExpChildItem {
    private int _id;
    private int icon;
    private String textTitle;
    private String textAmount;

    public IcExpChildItem(int _id, int icon, String textTitle, String textAmount) {
        this._id = _id;
        this.icon = icon;
        this.textTitle = textTitle;
        this.textAmount = textAmount;
    }

    public int get_id() {
        return _id;
    }

    public void set_id(int _id) {
        this._id = _id;
    }

    public int getIcon() {
        return icon;
    }

    public void setIcon(int icon) {
        this.icon = icon;
    }

    public String getTextTitle() {
        return textTitle;
    }

    public void setTextTitle(String textTitle) {
        this.textTitle = textTitle;
    }

    public String getTextAmount() {
        return textAmount;
    }

    public void setTextAmount(String textAmount) {
        this.textAmount = textAmount;
    }

}
