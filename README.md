# JHusky

> Modern native Git hooks made easy for java environments

JHusky improves your commits and more 🐶 *Jwoof!*

# Install

Include it to your project as a plugin

```xml
    <build>
        <plugins>
            <!--  ... Other plugins   -->
            <plugin>
                <groupId>io.github.dathin</groupId>
                <artifactId>jhusky</artifactId>
                <version>1.0.1</version> <!--  This is the currently latest version  -->
            </plugin>
            <!--  ... Other plugins  -->
        </plugins>
        <!--  ... Other build configs  -->
    </build>
```

# Usage

Run install goal once:

```sh
mvn jhusky:install -Ddirectory=.husky
```

Add a hook:

```sh
mvn jhusky:add -DhookPath=.husky/pre-commit -Dcommand="mvn clean test"
git add .husky/pre-commit
```

Make a commit:

```sh
git commit -m "Keep calm and commit"
# `mvn clean test` will run
```

# Hooks list
- Check the list with all [hooks](https://git-scm.com/docs/githooks#_hooks)

# Uninstall

If you didn't like it you can just run uninstall goal and remove the plugin

```sh
mvn jhusky:uninstall
```

# Inspiration
JHusky is a Java version based on [Husky](https://github.com/typicode/husky) for those who don't want/can't use node to setup git hooks in an intuitive and easy way
