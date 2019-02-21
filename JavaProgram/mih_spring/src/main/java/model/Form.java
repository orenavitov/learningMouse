package model;

import com.fasterxml.jackson.databind.annotation.JsonSerialize;
import config.JsonConfig;
//@JsonSerialize(using = JsonConfig.SpecialSpringSerializer.class)
public class Form {

    private String formNumber;

    private String user;

    private String sourceAddress;

    private String targetAddress;

    private boolean charged;

    public String getFormNumber() {
        return formNumber;
    }

    public void setFormNumber(String formNumber) {
        this.formNumber = formNumber;
    }

    public String getUser() {
        return user;
    }

    public void setUser(String user) {
        this.user = user;
    }

    public String getSourceAddress() {
        return sourceAddress;
    }

    public void setSourceAddress(String sourceAddress) {
        this.sourceAddress = sourceAddress;
    }

    public String getTargetAddress() {
        return targetAddress;
    }

    public void setTargetAddress(String targetAddress) {
        this.targetAddress = targetAddress;
    }

    public boolean isCharged() {
        return charged;
    }

    public void setCharged(boolean charged) {
        this.charged = charged;
    }
}
