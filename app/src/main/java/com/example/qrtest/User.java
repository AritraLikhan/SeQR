package com.example.qrtest;

public class User {
  public String firstName;
    public String lastName;
    public String email;
    public String password;
    public String text;
    public String dateOfBirth;

    public User() {
        // Default constructor required for Firebase
    }

//    public User(String firstName, String lastName, String email,String password) {
//        this.firstName = firstName;
//        this.lastName = lastName;
//        this.email = email;
//        this.password = password;
//    }

    public void AccInfo(String firstName,String lastName,String dateOfBirth,String email,String password)
    {
        this.firstName = firstName;
        this.lastName = lastName;
        this.dateOfBirth = dateOfBirth;
        this.email = email;
        this.password = password;

    }

    public void TextFiles(String text)
    {
        this.text = text;
    }

    public String getFirstName() {
        return firstName;
    }

    public void setFirstName(String firstName) {
        this.firstName = firstName;
    }

    public String getLastName() {
        return lastName;
    }

    public void setLastName(String lastName) {
        this.lastName = lastName;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String firstName) {
        this.password = password;
    }
}

