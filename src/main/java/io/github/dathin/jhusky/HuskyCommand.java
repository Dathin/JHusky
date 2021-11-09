package io.github.dathin.jhusky;

import org.apache.maven.plugin.AbstractMojo;
import org.apache.maven.plugin.MojoExecutionException;

import java.io.IOException;

public abstract class HuskyCommand extends AbstractMojo {

	public void execute() throws MojoExecutionException {
		try {
			command();
		}
		catch (InterruptedException | IOException | MojoExecutionException ex) {
			getLog().error(ex);
			throw new MojoExecutionException(String.format("Unable to run %s goal", getCommandName()));
		}
	}

	abstract void command() throws InterruptedException, IOException, MojoExecutionException;

	abstract String getCommandName();

}
