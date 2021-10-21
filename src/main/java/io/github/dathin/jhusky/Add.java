package io.github.dathin.jhusky;

import org.apache.maven.plugin.AbstractMojo;
import org.apache.maven.plugin.MojoExecutionException;
import org.apache.maven.plugins.annotations.Mojo;
import org.apache.maven.plugins.annotations.Parameter;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.nio.file.StandardOpenOption;
import java.util.Arrays;
import java.util.Collections;


@Mojo(name = "add")
public class Add extends AbstractMojo {

    @Parameter(property = "jHuskyFolderPath", defaultValue = ".husky/post-commit")
    private String jHuskyFolderPath;

    @Parameter(property = "add.command", defaultValue = "echo xD")
    private String command;

    @Override
    public void execute() throws MojoExecutionException {
        try {
            Path huskyCommandPath = Paths.get(jHuskyFolderPath);
            if (!Files.exists(huskyCommandPath.getParent())) {
                throw new MojoExecutionException("Can't create hook, " + huskyCommandPath.getParent().getFileName().toString() + " directory doesn't exist (try running install goal)");
            }
            if (Files.exists(huskyCommandPath)) {
                Files.write(huskyCommandPath, Arrays.asList("\n", command), StandardOpenOption.APPEND);
                getLog().info("Updated");
            } else {
                Path createdFile2 = Files.createFile(huskyCommandPath);
                createdFile2.toFile().setExecutable(true);
                Files.write(createdFile2, Collections.singletonList(command));
                getLog().info("Created");
            }
        } catch (IOException ex) {
            throw new MojoExecutionException("Unable to add: " + ex.getMessage());
        }
    }
}