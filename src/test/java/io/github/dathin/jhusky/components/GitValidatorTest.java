package io.github.dathin.jhusky.components;

import org.apache.maven.plugin.MojoExecutionException;
import org.apache.maven.plugin.logging.SystemStreamLog;
import org.eclipse.jgit.lib.Repository;
import org.eclipse.jgit.storage.file.FileRepositoryBuilder;
import org.junit.Before;
import org.junit.Rule;
import org.junit.Test;
import org.junit.rules.TemporaryFolder;

import java.io.File;
import java.io.IOException;

import static org.junit.Assert.assertEquals;

public class GitValidatorTest {

    public int EXIT_SUCCESS = 0;

    GitValidator gitValidator;

    @Rule
    public TemporaryFolder folder = new TemporaryFolder();

    Repository newlyCreatedRepo;

    @Before
    public void setUp() {
        gitValidator = new GitValidator(new SystemStreamLog());
    }

    @Test
    public void shouldReturnSuccess() throws MojoExecutionException, IOException, InterruptedException {
        newlyCreatedRepo = FileRepositoryBuilder.create(new File(folder.getRoot().getAbsolutePath().concat("/.git")));
        newlyCreatedRepo.create();

        int exitValue = gitValidator.isGitRepository(folder.getRoot().getAbsolutePath());
        assertEquals(EXIT_SUCCESS, exitValue);
    }

    @Test(expected = MojoExecutionException.class)
    public void shouldReturnFailure() throws MojoExecutionException, IOException, InterruptedException {
        gitValidator.isGitRepository(folder.getRoot().getAbsolutePath());
    }

}