package com.example.barber;

public class AdminReq {
    private String SF_Username;
    private String SF_Password;

    public String getCus_Username() {
        return  SF_Username;
    }

    public void setAdmin_username(String sf_username) {
        this. SF_Username = sf_username;
    }

    public String getCus_Password() {
        return SF_Password;
    }

    public void setAdmin_password(String sf_password) {
        this.SF_Password = sf_password;
    }
}
