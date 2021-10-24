package io.github.dathin.jhusky.components;

import org.apache.maven.plugin.MojoExecutionException;
import org.apache.maven.plugin.logging.SystemStreamLog;
import org.junit.Before;
import org.junit.Rule;
import org.junit.Test;
import org.junit.rules.TemporaryFolder;
import org.mockito.MockedStatic;
import org.mockito.Mockito;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;

import static org.junit.Assert.assertTrue;
import static org.mockito.ArgumentMatchers.any;

public class HuskyInstallTest {

    HuskyInstall huskyInstall;

    @Rule
    public TemporaryFolder folder = new TemporaryFolder();

    @Before
    public void setUp() {
        huskyInstall = new HuskyInstall(new SystemStreamLog());
    }

    @Test
    public void shouldCreateDirectories() throws MojoExecutionException, IOException, InterruptedException {
        try (MockedStatic<GitValidator> utilities = Mockito.mockStatic(GitValidator.class)) {
            utilities.when(() -> GitValidator.isGitRepository(any(), any())).thenReturn(0);
            huskyInstall.prepareEnvironment(folder.getRoot().getAbsolutePath().concat("/.husky"));

            Path huskyRoot = Paths.get(folder.getRoot().getAbsolutePath().concat("/.husky"));
            Path subFolder = Paths.get(folder.getRoot().getAbsolutePath().concat("/.husky/_"));
            boolean existsHuskyRoot = Files.exists(huskyRoot);
            boolean existsSubFolder = Files.exists(subFolder);

            assertTrue(existsHuskyRoot);
            assertTrue(existsSubFolder);
        }
    }

    @Test(expected = MojoExecutionException.class)
    public void givenDirectoryInvalid_shouldReturnException() throws MojoExecutionException, IOException, InterruptedException {
        try (MockedStatic<GitValidator> utilities = Mockito.mockStatic(GitValidator.class)) {
            utilities.when(() -> GitValidator.isGitRepository(any(), any())).thenReturn(0);
            huskyInstall.prepareEnvironment(folder.getRoot().getAbsolutePath().concat("/..husky"));
        }
    }

}