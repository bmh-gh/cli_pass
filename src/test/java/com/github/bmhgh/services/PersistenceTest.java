package com.github.bmhgh.services;

import com.github.bmhgh.config.Config;
import com.github.bmhgh.models.Entry;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.io.IOException;
import java.nio.file.Path;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;

class PersistenceTest {

    private final Path file = Config.file;
    private final char[] password = Config.password;

    @BeforeEach
    void setup() throws IOException {
        Config.setup();
    }

    @AfterEach
    void end() throws IOException {
        Config.end();
    }

    @Test
    void test_setup() {
        assert true;
    }

    @Test
    void init() throws Exception {
        new Persistence(file, password);
    }

    @Test
    void getPasswords() throws Exception {
        Persistence persistence = new Persistence(file, password);
        List<Entry> should = List.of(
                new Entry("Github", "github.com", "123qwe"),
                new Entry("Google", "google.com", "1234qwer")
        );
        assertEquals(should.toString(), persistence.getPasswords().toString());
    }

    @Test
    void setPasswords() throws Exception {
        Persistence persistence = new Persistence(file, password);
        List<Entry> should = List.of(
                new Entry("Github", "github.com", "123qwe"),
                new Entry("Google", "google.com", "1234qwer"),
                new Entry("YouTube", "youtube.com", "password")
        );
        List<Entry> out = persistence.setPasswords(should);
        assertEquals(should.toString(), out.toString());
    }

    @Test
    void addPassword() throws Exception {
        Persistence persistence = new Persistence(file, password);
        Entry newEntry = new Entry("YouTube", "youtube.com", "password");
        List<Entry> should = List.of(
                new Entry("Github", "github.com", "123qwe"),
                new Entry("Google", "google.com", "1234qwer"),
                newEntry
        );
        persistence.addPassword(newEntry);
        assertEquals(should.toString(), persistence.getPasswords().toString());
    }

    @Test
    void filterPasswords() throws Exception {
        Persistence persistence = new Persistence(file, password);
        List<Entry> should = List.of(new Entry("Github", "github.com", "123qwe"));
        List<Entry> actual = persistence.filterPasswords(List.of("Github"));
        assertEquals(should.toString(), actual.toString());
    }

    @Test
    void deleteAll() throws Exception {
        Persistence persistence = new Persistence(file, password);
        List<Entry> should = List.of(new Entry("Github", "github.com", "123qwe"));
        List<Entry> actual = persistence.deleteAll(List.of("Google"));
        assertEquals(should.toString(), actual.toString());
        persistence.deleteAll(List.of("Github"));
        assert persistence.getPasswords().isEmpty();

    }
}