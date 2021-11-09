package io.github.dathin.jhusky;

import org.apache.maven.plugin.MojoExecutionException;
import org.apache.maven.plugins.annotations.Mojo;
import org.apache.maven.plugins.annotations.Parameter;

import java.io.IOException;

@Mojo(name = "uninstall")
public class Uninstall extends HuskyCommand {

	@Parameter(property = "directory", defaultValue = ".husky")
	private String directory;

	private final ProcessUtils processUtils;

	public Uninstall() {
		this.processUtils = new ProcessUtils(getLog());
	}

	public Uninstall(ProcessUtils processUtils) {
		this.processUtils = processUtils;
	}

	@Override
	void command() throws InterruptedException, IOException, MojoExecutionException {
		processUtils.runAndHandleProcess(directory, "git", "config", "--unset", "core.hooksPath");
	}

	@Override
	String getCommandName() {
		return "Uninstall";
	}

}
