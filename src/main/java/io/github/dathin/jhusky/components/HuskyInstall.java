package io.github.dathin.jhusky.components;

import org.apache.maven.plugin.MojoExecutionException;
import org.apache.maven.plugin.logging.Log;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;

public class HuskyInstall {

	public static final String HUSKY_SH_DIR = "_";

	private Log log;

	public HuskyInstall(Log log) {
		this.log = log;
	}

	public void prepareEnvironment(String directory) throws MojoExecutionException, IOException, InterruptedException {
		GitValidator.isGitRepository(directory, this.log);

		String customDirHelp = "https://git.io/Jc3F9";
		if (directory.contains("..")) {
			throw new MojoExecutionException(".. not allowed (see" + customDirHelp + ")");
		}

		try {
			Files.createDirectories(Paths.get(directory));
			Files.deleteIfExists(Paths.get(directory, HUSKY_SH_DIR, ".gitignore"));
			Files.deleteIfExists(Paths.get(directory, HUSKY_SH_DIR, "husky.sh"));
			Files.createDirectories(Paths.get(directory, HUSKY_SH_DIR));
			log.info(String.format("Husky configuration in: %s", directory));
		}
		catch (IOException e) {
			log.error(String.format("error = %s", e.getMessage()));
			throw new MojoExecutionException(String.format("Error installing Husky, error = %s", e.getMessage()));
		}
	}

}
