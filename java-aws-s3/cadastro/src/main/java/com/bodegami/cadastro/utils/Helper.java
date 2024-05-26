package com.bodegami.cadastro.utils;

import org.springframework.stereotype.Component;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.UUID;

@Component
public class Helper {

    public String toFileName(String name) {
        return new StringBuffer()
                .append(name)
                .append("-")
                .append(UUID.randomUUID().toString().toLowerCase())
                .append(".txt")
                .toString();
    }

    public File toFile(Object object, String name) throws IOException {
        return Files.write(
                        Paths.get(name),
                        object.toString().getBytes())
                .toFile();
    }

}
