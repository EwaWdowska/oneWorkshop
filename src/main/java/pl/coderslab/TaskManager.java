package pl.coderslab;


import org.apache.commons.lang3.ArrayUtils;
import org.apache.commons.lang3.math.NumberUtils;

import java.io.*;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.Arrays;
import java.util.List;
import java.util.Scanner;

import static org.apache.commons.lang3.StringUtils.split;
import static pl.coderslab.ConsoleColors.*;

public class TaskManager {
    private static final String[] MENU = {"add", "remove", "list", "exit"};
    static final String FILE_NAME = "task.cvs";
    static String[][] tasks;

    public static void printOptions(String[] tab) {
        System.out.println(BLUE);
        System.out.println("Please select an option: " + RESET);
        for (String option : tab) {
            System.out.println(option);
        }
    }

    public static void main(String[] args) {
        tasks = task(FILE_NAME);
        printOptions(MENU);
        Scanner scanner = new Scanner(System.in);
        while (scanner.hasNext()) {
            String choice = scanner.nextLine();
            switch (choice) {
                case "exit":
                    System.out.println(RED);
                    System.out.println("Do zobaczenia! " + RESET);
                    break;

                case "add":
                    add1(tasks);
                    print(tasks);
                    break;

                case "list":
                    print(tasks);

                    break;

                case "remove":
                    print(tasks);
                    removeTask(tasks, getNumber());
                    System.out.println("Zadanie zostało usunięte");
                    break;

                default:
                    System.out.println("Please select a correct option.");
                    printOptions(MENU);
            }

        }

    }

    public static String[][] task(String arg) {

        String[][] tasks = new String[0][];

        try (Scanner scanner = new Scanner(new File("tasks.csv"))) {

            while (scanner.hasNextLine()) {
                String line = scanner.nextLine();

                tasks = Arrays.copyOf(tasks, tasks.length + 1);
                tasks[tasks.length - 1] = line.split(",");
            }
            System.out.println("Test");
        } catch (FileNotFoundException e) {
            System.out.println("Nie udało się wczytać pliku");
        }
        return tasks;
    }




    public static String[][] add1(String[][] tab) {
        Scanner scanner = new Scanner(System.in);
        System.out.println("Please add task description");
        String description = scanner.nextLine();
        System.out.println("Please add task due date: (rok-miesiąc-data");
        String dueDate = scanner.nextLine();
        System.out.println("Is your task important: true/false");
        String isImportant = scanner.nextLine();


        tasks = Arrays.copyOf(tasks, tasks.length + 1);
        tasks[tasks.length - 1] = new String[3];
        tasks[tasks.length - 1][0] = description;
        tasks[tasks.length - 1][1] = dueDate;
        tasks[tasks.length - 1][2] = isImportant;
        return tasks;
    }
    public static void print(String[][] tab) {
        for (int i = 0; i < tab.length; i++) {
            System.out.print("\n" + i + ":");
            for (int j = 0; j < tab[i].length; j++) {
                System.out.print((tab[i][j]) + " ");
            }
        }
        System.out.println();
    }

    public static int getNumber() {

        System.out.println("Please select number to remove.");

        String index = " ";
        try (Scanner scan = new Scanner(System.in);) {
            index = scan.nextLine();

            while (!isZero(index)) {
                scan.next();
                System.out.println("podaj dane większe od zera");
            }
        } catch (ArrayIndexOutOfBoundsException ex) {
            System.out.println("Element not exist in tab");
        }
        return Integer.parseInt(index);
    }

    public static boolean isZero(String index) {
        if (NumberUtils.isParsable(index)) {
            return Integer.parseInt(index) >= 0;
        }
        return false;
    }

    public static void removeTask(String[][] tab, int index) {
        if (index < tab.length && index > 0) {
            tasks = ArrayUtils.remove(tab, index);
        }
    }

    public static void save(String fileName, String[][] tab) {

        Path path = Paths.get(fileName);
        String[] lines = new String[tasks.length];

        for (int i = 0; i < tab.length; i++) {
            lines[i] = String.join(",", tab[i]);
        }

        try {
            Files.write(path, List.of(lines));
        } catch (IOException ex) {
            System.out.print("błąd pliku");
        }
    }


}

