package io.github.dathin.jhusky.components;

import org.apache.maven.plugin.MojoExecutionException;
import org.apache.maven.plugin.logging.Log;

import java.io.BufferedReader;
import java.io.File;
import java.io.IOException;
import java.io.InputStreamReader;

public class GitValidator {

	private GitValidator() {
		throw new RuntimeException("Utility class");
	}

	public static int isGitRepository(String directory, Log logger)
			throws IOException, InterruptedException, MojoExecutionException {
		ProcessBuilder processBuilder = new ProcessBuilder("git", "rev-parse");
		if (directory != null) {
			processBuilder.directory(new File(directory));
		}
		processBuilder.inheritIO().redirectOutput(ProcessBuilder.Redirect.PIPE);

		Process process = processBuilder.start();
		process.waitFor();
		if (process.exitValue() != 0) {
			BufferedReader buf = new BufferedReader(new InputStreamReader(process.getInputStream()));
			String line = "";
			while ((line = buf.readLine()) != null) {
				logger.info(line);
			}
			throw new MojoExecutionException(String.format("Process exit value: %s", process.exitValue()));
		}
		return process.exitValue();
	}

}
