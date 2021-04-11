package com.knaas.thetravelmate;

public class Userinformation {

    public String name;
    public String bio;
    public String phoneno;

    public Userinformation(){
    }

    public Userinformation(String name,String surname, String phoneno){
        this.name = name;
        this.bio = surname;
        this.phoneno = phoneno;
    }
    public String getUserName() {
        return name;
    }
    public String getUserSurname() {
        return bio;
    }
    public String getUserPhoneno() {
        return phoneno;
    }
}