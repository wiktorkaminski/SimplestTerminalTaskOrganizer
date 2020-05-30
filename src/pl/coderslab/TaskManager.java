package pl.coderslab;

import org.apache.commons.lang3.ArrayUtils;
import org.apache.commons.lang3.math.NumberUtils;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileWriter;
import java.io.IOException;
import java.util.Arrays;
import java.util.Scanner;

public class TaskManager {

    private static String[][] tasks;

    public static void main(String[] args) {
        File tasksFile = new File("./src/pl/coderslab/tasks.csv");
        tasks = tasks(tasksFile);
        Scanner scn = new Scanner(System.in);
        String userInput;
        menu();
        userInput = scn.nextLine();
        while (!userInput.toLowerCase().equals("exit")) {
            switch (userInput.toLowerCase()) {
                case "add":
                    tasks = Arrays.copyOf(tasks, tasks.length + 1);
                    tasks[tasks.length - 1] = addTask();
                    menu();
                    break;
                case "remove":
                    tasks = Arrays.copyOf(removeTask(), tasks.length - 1);
                    menu();
                    break;
                case "list":
                    listTasks();
                    menu();
                    break;
                default:
                    System.out.println("Please select a correct option.");
                    break;
            }
            userInput = scn.nextLine();
        }
        updateTasks(tasksFile);
        System.out.println(ConsoleColors.RED + "Bye, bye.");
    }

    public static String[] addTask() {
        Scanner scnAdd = new Scanner(System.in);
        String[] newTask = new String[3];
        System.out.println("Please add task description:");
        newTask[0] = scnAdd.nextLine();
        System.out.println("Please add task due date:");
        newTask[1] = scnAdd.nextLine();
        System.out.println("Is your task important: true/false");
        String inputTF = scnAdd.next();
        while (!(inputTF.toLowerCase().equals("true") || inputTF.toLowerCase().equals("false"))) {
            System.out.println("Correct phrase is true or false. Choose correct option.");
            inputTF = scnAdd.next();
        }
        newTask[2] = inputTF;
        return newTask;
    }

    public static void listTasks() {
        StringBuilder taskList = new StringBuilder();
        for (int i = 0; i < tasks.length; i++) {
            taskList.append(i).append(" : ");
            for (int j = 0; j < tasks[i].length; j++) {
                taskList.append(tasks[i][j]).append(" ");
            }
            taskList.append("\n");
        }
        System.out.print(taskList.toString());
    }

    public static String[][] removeTask() {
        Scanner scn = new Scanner(System.in);
        System.out.println("Pleas select row number to remove:");
        String input = scn.next();

        while (validateTaskNumber(input)) {
            System.out.println("Incorrect argument passed. Please give number greater or equal 0:");
            input = scn.next();
        }

        String[][] toReturn = ArrayUtils.remove(tasks, Integer.parseInt(input));
        System.out.println("Record was successfully removed");
        return toReturn;
    }

    public static void updateTasks(File tasksFile) {

        try {
            FileWriter fileWriter = new FileWriter(tasksFile, false);
            for (int i = 0; i < tasks.length; i++) {
                for (int j = 0; j < tasks[i].length; j++) {
                    fileWriter.append(tasks[i][j]).append(";");
                }
                fileWriter.append("\n");
            }
            fileWriter.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public static void menu() {
        String[] menu = {
                ConsoleColors.BLUE + "Please select an option:" + ConsoleColors.RESET,
                "add",
                "remove",
                "list",
                "exit"};
        for (String s : menu) {
            System.out.println(s);
        }
    }

    public static String[][] tasks(File tasksFile) {

        String[][] tasksTab = new String[rowCounter(tasksFile)][3];
        try {
            Scanner scn = new Scanner(tasksFile);
            for (int i = 0; i < tasksTab.length; i++) {
                tasksTab[i] = scn.nextLine().split(";");
            }
            scn.close();
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        }
        return tasksTab;
    }

    public static boolean validateTaskNumber(String string) {
        boolean toReturn = true;
        if (NumberUtils.isDigits(string) && NumberUtils.isParsable(string) && Integer.parseInt(string) >= 0 && (Integer.parseInt(string) < tasks.length)) {
            toReturn = false;
        }
        return toReturn;
    }

    public static int rowCounter(File tasksFile) {
        int counter = 0;
        try {
            Scanner scn = new Scanner(tasksFile);
            while (scn.hasNextLine()) {
                scn.nextLine();
                counter++;
            }
            scn.close();
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        }
        return counter;
    }

}
