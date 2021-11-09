package io.github.dathin.jhusky;

import org.apache.maven.plugin.MojoExecutionException;
import org.apache.maven.plugins.annotations.Mojo;
import org.apache.maven.plugins.annotations.Parameter;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;

@Mojo(name = "install")
public class Install extends HuskyCommand {

	public static final String HUSKY_SH_DIR = "_";

	@Parameter(property = "directory", defaultValue = ".husky")
	private String directory;

	private ProcessUtils processUtils;

	public Install() {
		this.processUtils = new ProcessUtils(getLog());
	}

	public Install(ProcessUtils processUtils) {
		this.processUtils = processUtils;
	}

	@Override
	public void command() throws MojoExecutionException, IOException, InterruptedException {
		processUtils.runAndHandleProcess(directory, "git", "rev-parse");

		prepareEnvironment(directory);

		installHuskyFiles(directory);

		processUtils.runAndHandleProcess(directory, "git", "config", "core.hooksPath", directory);

		getLog().info("Git hooks installed");
	}

	private void prepareEnvironment(String directory) throws MojoExecutionException, IOException {
		String customDirHelpUrl = "https://git.io/Jc3F9";

		if (directory.contains("..")) {
			throw new MojoExecutionException(String.format(".. not allowed (see %s)", customDirHelpUrl));
		}

		Files.createDirectories(Paths.get(directory));
		Files.deleteIfExists(Paths.get(directory, HUSKY_SH_DIR, ".gitignore"));
		Files.deleteIfExists(Paths.get(directory, HUSKY_SH_DIR, "husky.sh"));
		Files.createDirectories(Paths.get(directory, HUSKY_SH_DIR));
	}

	private void installHuskyFiles(String directory) throws IOException {
		Path huskySh = Files.createFile(Paths.get(directory, HUSKY_SH_DIR, "husky.sh"));
		huskySh.toFile().setExecutable(true);
		Files.write(huskySh,
				("#!/bin/sh\n" + "if [ -z \"$husky_skip_init\" ]; then\n" + "  debug () {\n"
						+ "    if [ \"$HUSKY_DEBUG\" = \"1\" ]; then\n" + "      echo \"husky (debug) - $1\"\n"
						+ "    fi\n" + "  }\n" + "\n" + "  readonly hook_name=\"$(basename \"$0\")\"\n"
						+ "  debug \"starting $hook_name...\"\n" + "\n" + "  if [ \"$HUSKY\" = \"0\" ]; then\n"
						+ "    debug \"HUSKY env variable is set to 0, skipping hook\"\n" + "    exit 0\n" + "  fi\n"
						+ "\n" + "  if [ -f ~/.huskyrc ]; then\n" + "    debug \"sourcing ~/.huskyrc\"\n"
						+ "    . ~/.huskyrc\n" + "  fi\n" + "\n" + "  export readonly husky_skip_init=1\n"
						+ "  sh -e \"$0\" \"$@\"\n" + "  exitCode=\"$?\"\n" + "\n" + "  if [ $exitCode != 0 ]; then\n"
						+ "    echo \"husky - $hook_name hook exited with code $exitCode (error)\"\n" + "  fi\n" + "\n"
						+ "  exit $exitCode\n" + "fi\n").getBytes());

		Path gitignore = Files.createFile(Paths.get(directory, HUSKY_SH_DIR, ".gitignore"));
		Files.write(gitignore, "*".getBytes());
	}

	@Override
	String getCommandName() {
		return "Install";
	}

}
