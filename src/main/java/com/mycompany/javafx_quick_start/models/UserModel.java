package com.mycompany.javafx_quick_start.models;

public class UserModel {
    private Long id;
    private String username;
    private String password;
    private String access_level;

    public UserModel() {
    }

    public UserModel(Long id, String username, String password, String access_level) {
        this.id = id;
        this.username = username;
        this.password = password;
        this.access_level = access_level;
    }

    public UserModel(String username, String password, String access_level) {
        this.username = username;
        this.password = password;
        this.access_level = access_level;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
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

    public String getAccessLevel() {
        return access_level;
    }

    public void setAccessLevel(String access_level) {
        this.access_level = access_level;
    }

    @Override
    public String toString() {
        return "UserModel{" +
                "id=" + id +
                ", username='" + username + '\'' +
                ", password='" + password + '\'' +
                ", access_level='" + access_level + '\'' +
                '}';
    }
}
