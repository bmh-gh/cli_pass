package com.github.bmhgh.models;

import java.io.Serial;
import java.io.Serializable;

public class Entry implements Serializable {
    @Serial
    private static final long serialVersionUID = 1L;
    private String title;
    private String url;
    private String password;
    public Entry(String title, String url, String password) {
        this.title = title;
        this.url = url;
        this.password = password;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getUrl() {
        return url;
    }

    public void setUrl(String url) {
        this.url = url;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }
}
