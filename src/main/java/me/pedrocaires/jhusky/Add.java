package me.pedrocaires.jhusky;

<<<<<<< HEAD
import org.apache.maven.plugin.AbstractMojo;
import org.apache.maven.plugin.MojoExecutionException;
import org.apache.maven.plugins.annotations.Mojo;
import org.apache.maven.plugins.annotations.Parameter;

=======
>>>>>>> fff0d8b2f06ff0899d2dce50b695cc5e6de62644
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.nio.file.StandardOpenOption;
<<<<<<< HEAD
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
=======

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
>>>>>>> fff0d8b2f06ff0899d2dce50b695cc5e6de62644
        }
    }
}
