package com.bmhgh;

public class Entry {

    int id;
    String password;
    String username;
    String url;

    public Entry(int id, String pw, String username, String url) {
        this.id = id;
        this.password = pw;
        this.username = username;
        this.url = url;
    }

    public int getId() {
        return id;
    }

    public String getPassword() {
        return password;
    }

    public String getUsername() {
        return username;
    }

    public String getUrl() {
        return url;
    }
}
