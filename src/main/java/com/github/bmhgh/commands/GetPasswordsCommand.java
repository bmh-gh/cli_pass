package com.github.bmhgh.commands;

import picocli.CommandLine;

@CommandLine.Command(name = "get",
        mixinStandardHelpOptions = true,
        header = "Retrieve the passwords from the storage",
        optionListHeading = "%nOptions are:%n",
        description = "")
public class GetPasswordsCommand {
}
