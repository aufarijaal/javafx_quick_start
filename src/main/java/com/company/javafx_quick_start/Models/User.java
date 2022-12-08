package com.company.javafx_quick_start.Models;

public class User {
    private int id;
    private String username;
    private String password;
    private String access_level;

    public User(int id, String username, String password, String access_level) {
        this.id = id;
        this.username = username;
        this.password = password;
        this.access_level = access_level;
    }
    public User(String username, String password, String access_level) {
        this.username = username;
        this.password = password;
        this.access_level = access_level;
    }

    public User() {
    }

    @Override
    public String toString() {
        return "User{" +
                "id=" + id +
                ", username='" + username + '\'' +
                ", password='" + password + '\'' +
                ", access_level='" + access_level + '\'' +
                '}';
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getAccess_level() {
        return access_level;
    }

    public void setAccess_level(String access_level) {
        this.access_level = access_level;
    }
}
