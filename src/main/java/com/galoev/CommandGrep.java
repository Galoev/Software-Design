package com.galoev;

import com.beust.jcommander.JCommander;
import com.beust.jcommander.Parameter;
import com.beust.jcommander.ParameterException;

import java.io.ByteArrayInputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;
import java.util.regex.Pattern;

/**
 * Grep, a command that searches for a substring in a file or input stream.
 * Supports the following keys:
 * • -i - case insensitive;
 * • -w - search only whole words;
 * • -A n - print n lines after the line with the match.
 */
public class CommandGrep implements Command {

    @Parameter(names = "-i")
    private boolean caseKey = false;

    @Parameter(names = "-w")
    private boolean onlyWordKey = false;

    @Parameter(names = "-A")
    private int countLinesAfter = 0;

    @Parameter
    private List<String> parameters = new ArrayList<>();

    @Override
    public InputStream execute(List<String> args, InputStream input) throws Exception {
        try {
            new JCommander(this, args.toArray(new String[args.size()]));
        } catch (ParameterException exception) {

            throw new Exception("ParameterException: " + exception.getMessage());
        }

        if (parameters.size() == 0) {
            throw new Exception("Not enough arguments");
        }

        if (countLinesAfter < 0) {
            throw new Exception("Context argument must be non-negative");
        }

        String regex = parameters.get(0);
        if (onlyWordKey) {
            regex = "\\b" + regex + "\\b";
        }

        Pattern pattern;
        if (caseKey) {
            pattern = Pattern.compile(regex, Pattern.CASE_INSENSITIVE);
        } else {
            pattern = Pattern.compile(regex);
        }

        StringBuilder result = new StringBuilder();
        if (parameters.size() == 1) {
            result.append(findMatch(input, pattern));
        } else {
            for (int i = 1; i < parameters.size(); i++) {
                result.append(findMatch(new FileInputStream(new File(parameters.get(i))), pattern));
            }
        }
        caseKey = false;
        onlyWordKey = false;
        countLinesAfter = 0;
        parameters.clear();
        return new ByteArrayInputStream(result.toString().getBytes());
    }

    private String findMatch(InputStream input, Pattern pattern) {
        Scanner scanner = new Scanner(input);
        StringBuilder result = new StringBuilder();
        int linePrintIndex = -1;
        int i = 0;
        while (scanner.hasNextLine()) {
            String line = scanner.nextLine();
            if (pattern.matcher(line).find()) {
                linePrintIndex = i + countLinesAfter;
            }
            if (i <= linePrintIndex) {
                result.append(line + "\n");
            }
            i++;
        }
        return result.toString();
    }
}