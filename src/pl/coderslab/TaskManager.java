package pl.coderslab;

import org.apache.commons.lang3.ArrayUtils;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileWriter;
import java.io.IOException;
import java.util.Arrays;
import java.util.Scanner;

public class TaskManager {


    public static void main(String[] args) {
        File tasksFile = new File("./src/pl/coderslab/tasks.csv");
        String[][] tasks = tasks(tasksFile);
        Scanner scn = new Scanner(System.in);
        String userInput = null;
        menu();
        userInput = scn.nextLine();
        while (!userInput.toLowerCase().equals("exit")) {
            switch (userInput) {
                case "add":
                    tasks = Arrays.copyOf(tasks, tasks.length + 1);
                    tasks[tasks.length - 1] = addTask();
                    menu();
                    userInput = scn.nextLine();
                    break;
                case "remove":
                    tasks = Arrays.copyOf(removeTask(tasks), tasks.length - 1);
                    menu();
                    userInput = scn.nextLine();
                    break;
                case "list":
                    listTasks(tasks);
                    menu();
                    userInput = scn.nextLine();
                    break;
                default:
                    System.out.println("Please select a correct option.");
                    userInput = scn.nextLine();
                    break;
            }
        }
        System.out.println(Arrays.toString(tasks[0]));
        updateTasks(tasksFile, tasks);
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
        newTask[2] = scnAdd.nextLine();
        return newTask;
    }

    public static void listTasks(String[][] tasksTab) {
        StringBuilder taskList = new StringBuilder();
        for (int i = 0; i < tasksTab.length; i++) {
            taskList.append(i).append(" : ");
            for (int j = 0; j < tasksTab[i].length; j++) {
                taskList.append(tasksTab[i][j]).append(" ");
            }
            taskList.append("\n");
        }
        System.out.print(taskList.toString());
    }

    public static String[][] removeTask(String[][] tasks) {
        Scanner scn = new Scanner(System.in);
        System.out.println("Pleas select row number to remove:");
        int recordToRemove = scn.nextInt();
        String[][] toReturn = ArrayUtils.remove(tasks, recordToRemove);
        System.out.println("Record was successfully removed");
        return toReturn;
    }

    public static void updateTasks(File tasksFile, String[][] tasksTab) {

        try {
            FileWriter fileWriter = new FileWriter(tasksFile, false);
            for (int i = 0; i < tasksTab.length; i++) {
                for (int j = 0; j < tasksTab[i].length; j++) {
                    fileWriter.append(tasksTab[i][j]).append(";");
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

    private static String[][] tasks(File tasksFile) {

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
