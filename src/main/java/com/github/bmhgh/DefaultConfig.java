package com.github.bmhgh;

import java.nio.file.Path;
import java.nio.file.Paths;

public class DefaultConfig {
    public final static Path DEFAULT_PATH = Paths.get(".").toAbsolutePath().normalize();
    public final static Path DEFAULT_FILE_PATH = DEFAULT_PATH.resolve("passwords");
}
