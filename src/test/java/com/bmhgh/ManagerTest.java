package com.bmhgh;

import org.junit.jupiter.api.Test;


import java.io.IOException;
import java.nio.file.Paths;

class ManagerTest {

    void setup() throws IOException {
        String name = "new.json";
        String path = String.join("", Paths.get("").toAbsolutePath().toString(), "\\src\\test\\java\\com\\bmhgh\\test_config\\");
        Manager.create_json(path, name);
    }

    void end() throws IOException {
        String name = "new.json";
        String path = String.join("", Paths.get("").toAbsolutePath().toString(), "\\src\\test\\java\\com\\bmhgh\\test_config\\");
        Manager.delete_json(path + name);
    }

    @Test
    void create_json() throws IOException {
        String name = "new.json";
        String path = String.join("", Paths.get("").toAbsolutePath().toString(), "\\src\\test\\java\\com\\bmhgh\\test_config\\");
        Manager.create_json(path, name);
        Manager.delete_json(path + name);
    }

    @Test
    void parse_json() {

    }
}