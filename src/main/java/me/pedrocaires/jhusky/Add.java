package me.pedrocaires.jhusky;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.nio.file.StandardOpenOption;

public class Add {
    public static void main(String[] args) throws IOException {
        System.out.println(args[0]);
        System.out.println(args[1]);

        Path huskyPath = Paths.get(args[0]);
        if (Files.exists(huskyPath)) {
            Files.writeString(huskyPath, "\n" + args[1], StandardOpenOption.APPEND);
        } else {
            Path createdFile2 = Files.createFile(Paths.get(args[0]));
            createdFile2.toFile().setExecutable(true);
            Files.writeString(createdFile2, args[1]);
        }
    }
}
