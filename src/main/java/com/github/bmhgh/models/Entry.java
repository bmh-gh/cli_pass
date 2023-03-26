package com.github.bmhgh.models;

import java.io.*;
import java.util.Base64;

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

    public static String serialize(Entry entry) throws IOException {
        try (ByteArrayOutputStream bos = new ByteArrayOutputStream();
             ObjectOutputStream out = new ObjectOutputStream(bos)) {
            out.writeObject(entry);
            out.flush();
            return Base64.getEncoder().encodeToString(bos.toByteArray());
        } catch (IOException e) {
            throw new IOException(e);
        }
    }

    public static Entry deserialize(String entry) {
        byte[] decodedInput = Base64.getDecoder().decode(entry);
        ByteArrayInputStream bis = new ByteArrayInputStream(decodedInput);
        try (ObjectInput in = new ObjectInputStream(bis)) {
            Object o = in.readObject();
            if (o instanceof Entry) {
                return (Entry) o;
            }
            throw new Exception("This Entry is not valid");
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }

    @Override
    public String toString() {
        return String.format("[%s|%s|%s]", getTitle(), getUrl(), getPassword());
    }
}
