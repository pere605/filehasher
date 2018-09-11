package com.pere.utils;

import org.apache.commons.cli.CommandLine;
import org.apache.commons.cli.CommandLineParser;
import org.apache.commons.cli.DefaultParser;
import org.apache.commons.cli.HelpFormatter;
import org.apache.commons.cli.Option;
import org.apache.commons.cli.Options;
import org.apache.commons.cli.ParseException;

import java.io.File;
import java.util.Map;

public class App {
    private static Options options;
    private static CommandLine commandLine;

    public static void main(String[] args) throws Exception {
        buildOptions();
        initializeCommandLine(args);

        if (commandLine.hasOption("h")) {
            HelpFormatter formatter = new HelpFormatter();
            formatter.printHelp("filehasher", options);

            return;
        }

        Map<File, FileInfo> files = FileLoader.loadFiles(commandLine.getOptionValue("d"), commandLine.getOptionValue("e"));

        if (files == null) {
            System.out.println("Directory not found");

            return;
        }

        if (commandLine.hasOption("c")) {
            FileHasher.hashFiles(files, commandLine.getOptionValue("c"));
            System.out.println(String.format("Modified and copied files to %s", commandLine.getOptionValue("c")));
        } else {
            FileHasher.hashFiles(files);
            files.forEach((file, fileInfo) -> {
                System.out.println(String.format("%s: %s", file.getAbsolutePath(), fileInfo.getFileHash()));
            });
        }
    }

    private static void initializeCommandLine(String[] args) throws ParseException {
        if (commandLine != null) {
            return;
        }

        final CommandLineParser parser = new DefaultParser();
        commandLine = parser.parse(options, args);
    }

    private static void buildOptions() {
        if (options != null) {
            return;
        }

        Option directoryOption = Option.builder("d")
                .hasArg()
                .longOpt("dir")
                .desc("Directory containing the files to hash.")
                .build();

        Option extensionOption = Option.builder("e")
                .hasArg()
                .longOpt("ext")
                .desc("Extension of the files to hash.")
                .build();

        Option helpOption = Option.builder("h")
                .longOpt("help")
                .desc("Prints help.")
                .build();

        Option copyOption = Option.builder("c")
                .hasArg()
                .longOpt("copy")
                .desc("Copy to folder.")
                .build();

        options = new Options();
        options.addOption(directoryOption)
                .addOption(extensionOption)
                .addOption(helpOption)
                .addOption(copyOption);
    }
}
