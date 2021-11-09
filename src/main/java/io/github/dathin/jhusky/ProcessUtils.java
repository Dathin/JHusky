package io.github.dathin.jhusky;

import org.apache.maven.plugin.MojoExecutionException;
import org.apache.maven.plugin.logging.Log;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.nio.file.Files;
import java.nio.file.Paths;

public class ProcessUtils {

	private Log log;

	public ProcessUtils(Log log) {
		this.log = log;
	}

	public void runAndHandleProcess(String executionDirectory, String... command)
			throws IOException, InterruptedException, MojoExecutionException {
		ProcessBuilder processBuilder = new ProcessBuilder(command);
		processBuilder.directory(Files.createDirectories(Paths.get(executionDirectory)).toFile());
		processBuilder.inheritIO().redirectOutput(ProcessBuilder.Redirect.PIPE);

		Process process = processBuilder.start();
		process.waitFor();

		if (process.exitValue() != 0) {
			BufferedReader buf = new BufferedReader(new InputStreamReader(process.getInputStream()));
			String line = "";
			while ((line = buf.readLine()) != null) {
				log.info(line);
			}
			throw new MojoExecutionException(String.format("Process exit value: %s", process.exitValue()));
		}
	}

}
