package model;

import java.util.List;

public class Goods {

    private List<Good> good;

    private String form;


    public List<Good> getGood() {
        return good;
    }

    public void setGood(List<Good> good) {
        this.good = good;
    }

    public String getForm() {
        return form;
    }

    public void setForm(String form) {
        this.form = form;
    }
}
