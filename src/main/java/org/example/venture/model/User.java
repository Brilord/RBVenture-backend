package org.example.venture.model;

import java.util.List;

public class User {

    private String username;
    private String password;

    private String email;

    List<String> settingsConfig;


    public User() {

    }


    public User(String username, String password, String email, List<String> settingsConfig) {
        this.username = username;
        this.password = password;
        this.email = email;
        this.settingsConfig = settingsConfig;

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

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public List<String> getSettingsConfig() {
        return settingsConfig;
    }

    public void setSettingsConfig(List<String> settingsConfig) {
        this.settingsConfig = settingsConfig;
    }
}

