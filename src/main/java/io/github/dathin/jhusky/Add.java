package io.github.dathin.jhusky;

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
public class Add extends HuskyCommand {

	@Parameter(property = "hookPath")
	private String hookPath;

	@Parameter(property = "command")
	private String command;

	@Override
	public void command() throws MojoExecutionException, IOException {
		Path commandPath = Paths.get(hookPath);

		checkParentDirectoryExists(commandPath);
		createCommand(commandPath, command);
	}

	private void checkParentDirectoryExists(Path huskyCommandPath) throws MojoExecutionException {
		if (!Files.exists(huskyCommandPath.getParent())) {
			throw new MojoExecutionException(
					String.format("Can't create hook, %s directory doesn't exist (try running install goal 'mvn jhusky:install')",
							huskyCommandPath.getParent().getFileName().toString()));
		}
	}

	private void createCommand(Path commandPath, String command) throws IOException {
		if (!Files.exists(commandPath)) {
			Files.createFile(commandPath);
		}
		commandPath.toFile().setExecutable(true);
		Files.write(commandPath, Collections.singletonList(command));
		getLog().info("Created");
	}

	@Override
	String getCommandName() {
		return "Add";
	}

}