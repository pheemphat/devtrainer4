package com.example.barber;

public class LoginReq {
    private String Cus_Username;
    private String Cus_Password;

    public String getUser_email() {
        return  Cus_Username;
    }

    public void setUser_email(String cus_username) {
        this. Cus_Username = cus_username;
    }

    public String getUser_pass() {
        return Cus_Password;
    }

    public void setUser_pass(String cus_password) {
        this.Cus_Password = cus_password;
    }
}

