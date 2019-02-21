package model;

import org.springframework.beans.factory.annotation.Value;

public class User {

    private String userName;

    private String password;

    private String emailAddress;

    private String telephoneNumber;

    private String[] interests;

    private String sex;

    public String getUserName() {
        return userName;
    }

    public void setUserName(String userName) {
        this.userName = userName;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getEmailAddress() {
        return emailAddress;
    }

    public void setEmailAddress(String emailAddress) {
        this.emailAddress = emailAddress;
    }

    public String getTelephoneNumber() {
        return telephoneNumber;
    }

    public void setTelephoneNumber(String telephoneNumber) {
        this.telephoneNumber = telephoneNumber;
    }

//    public Interests getInterests() {
//        return interests;
//    }
//
//    public void setInterests(Interests interests) {
//        this.interests = interests;
//    }

//    public Sex getSex() {
//        return sex;
//    }
//
//    public void setSex(Sex sex) {
//        this.sex = sex;
//    }

    public String[] getInterests() {
        return interests;
    }

    public void setInterests(String[] interests) {
        this.interests = interests;
    }

    public String getSex() {
        return sex;
    }

    public void setSex(String sex) {
        this.sex = sex;
    }

    private enum Interests {
        GAME("game"),

        MUSIC("music"),

        SPORT("sport"),

        SCIENCE("science");

        private String value;
        Interests(String value) {
            this.value = value;
        }


    }

    private enum Sex {
        MAN("man"),

        WOMAN("woman");

        private String value;

        Sex(String value) {
            this.value = value;
        }
    }
}
