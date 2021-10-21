package io.github.dathin.jhusky;

import org.apache.maven.plugin.AbstractMojo;
import org.apache.maven.plugin.MojoExecutionException;
import org.apache.maven.plugins.annotations.Mojo;

import java.io.IOException;


@Mojo(name = "uninstall")
public class Uninstall extends AbstractMojo {

    @Override
    public void execute() throws MojoExecutionException {
        ProcessBuilder builder2 = new ProcessBuilder("git", "config", "--unset", "core.hooksPath");
        try {
            builder2.start();
        } catch (IOException ex) {
            throw new MojoExecutionException("Unable to uninstall: " + ex.getMessage());
        }
    }
}
