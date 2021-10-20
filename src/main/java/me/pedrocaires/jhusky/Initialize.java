package me.pedrocaires.jhusky;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.Collections;

public class Initialize {
    public static void main(String[] args) throws InterruptedException, IOException {
        // Check if it is a git repo
        ProcessBuilder builder = new ProcessBuilder("git", "rev-parse");

        builder.inheritIO().redirectOutput(ProcessBuilder.Redirect.PIPE);
        Process process = builder.start();
        if (process.exitValue() != 0){
            BufferedReader buf = new BufferedReader(new InputStreamReader(process.getInputStream()));
            String line = "";
            while ((line=buf.readLine())!=null) {
                System.out.println(line);
            }
            return;
        }

        //File creations
        String scriptsPath = ".jhusky";

        Files.createDirectories(Paths.get(scriptsPath, "_"));
        Path createdFile = Files.createFile(Paths.get(scriptsPath, "_", "husky.sh"));
        createdFile.toFile().setExecutable(true);
        Files.write(createdFile, Collections.singleton("#!/bin/sh\n" +
                "if [ -z \"$husky_skip_init\" ]; then\n" +
                "  debug () {\n" +
                "    if [ \"$HUSKY_DEBUG\" = \"1\" ]; then\n" +
                "      echo \"husky (debug) - $1\"\n" +
                "    fi\n" +
                "  }\n" +
                "\n" +
                "  readonly hook_name=\"$(basename \"$0\")\"\n" +
                "  debug \"starting $hook_name...\"\n" +
                "\n" +
                "  if [ \"$HUSKY\" = \"0\" ]; then\n" +
                "    debug \"HUSKY env variable is set to 0, skipping hook\"\n" +
                "    exit 0\n" +
                "  fi\n" +
                "\n" +
                "  if [ -f ~/.huskyrc ]; then\n" +
                "    debug \"sourcing ~/.huskyrc\"\n" +
                "    . ~/.huskyrc\n" +
                "  fi\n" +
                "\n" +
                "  export readonly husky_skip_init=1\n" +
                "  sh -e \"$0\" \"$@\"\n" +
                "  exitCode=\"$?\"\n" +
                "\n" +
                "  if [ $exitCode != 0 ]; then\n" +
                "    echo \"husky - $hook_name hook exited with code $exitCode (error)\"\n" +
                "  fi\n" +
                "\n" +
                "  exit $exitCode\n" +
                "fi\n"));
        Path createdFile2 = Files.createFile(Paths.get(scriptsPath, "_", ".gitignore"));
        createdFile2.toFile().setExecutable(true);
        Files.write(createdFile2, Collections.singleton("*"));


        // Change dir of hooks
        ProcessBuilder builder2 = new ProcessBuilder("git", "config", "core.hooksPath", ".jhusky");
        builder2.start();

    }
}
