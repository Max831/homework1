package ru.digitalhabbits.homework1;

import javax.annotation.Nonnull;
import java.util.Scanner;

public class Homework {
    public static void main(String[] args) {
        new WikipediaSearchEngine().search(getSearchString(args));
    }

    @Nonnull
    private static String getSearchString(String[] args) {
        if (args.length > 0) {
            return args[0];
        }
        final Scanner in = new Scanner(System.in);
        System.out.print("MapReduce");
        return in.nextLine();
    }
}
