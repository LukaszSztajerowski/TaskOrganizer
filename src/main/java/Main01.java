import org.apache.commons.lang3.ArrayUtils;

import java.io.*;
import java.util.Arrays;
import java.util.Scanner;

public class Main01 {
    public static void main(String[] args) {
        start();
    }

    public static void start() {
        Scanner sc = new Scanner(System.in);
        System.out.println(ConsoleColors.BLUE + "please select an option:" + ConsoleColors.RESET + "\n add \n remove \n list \n exit");
        String input = sc.nextLine();
        switch (input) {
            case "add":
                addTask();
                break;
            case "remove":
                removeTask();
                break;
            case "list":
                taskList();
                break;
            case "exit":
                exit();
                break;
            default:
                System.out.println("Please select a correct option.");
                start();
        }
    }

    public static void addTask() {
        Scanner sc = new Scanner(System.in);
        System.out.println("Give task name: ");
        String taskName = sc.nextLine();
        System.out.println("task date: ");
        String taskDate = sc.nextLine();
        System.out.println("is it important: true/false");
        String taskImportance = sc.nextLine();

        try (FileWriter fw = new FileWriter("tasks.csv", true)) {

            fw.append(taskName);
            fw.append(", ");
            fw.append(taskDate);
            fw.append(", ");
            fw.append(taskImportance);
            fw.append("\n");

        } catch (IOException e) {
            e.printStackTrace();
        }

        start();
    }

    public static void removeTask() {
        String[][] multiarray = listToMultiArray();
        Scanner sc = new Scanner(System.in);
        System.out.println("witch one You want to remove");
        int number = sc.nextInt();

        if (number < multiarray.length) {
            for (int i = number; i < multiarray.length; i++) {
                if (i != multiarray.length - 1) {
                    multiarray[i] = multiarray[i + 1];      //przepisanie tablicy o jedno miejsce do tylu
                }
            }
        } else {
            System.out.println("incorrect input, try again");
            removeTask();
        }

        multiarray = Arrays.copyOf(multiarray, multiarray.length - 1);      //skrócenie tablicy o jedno miejsce


        try (PrintWriter pw = new PrintWriter("tasks.csv")) {
            for (String[] array : multiarray) {
                String tempString = ArrayUtils.toString(array);
                tempString = tempString.substring(1, tempString.length() - 1);
                pw.println(tempString);

            }
        } catch (IOException e) {
            e.printStackTrace();
        }

        start();
    }


    public static void exit() {
        System.out.println(ConsoleColors.RED + "bye");
    }


    public static void taskList() {
        String[][] multiArray = listToMultiArray();
        int licznik = 0;

        for (String[] array : multiArray) {
            String tempString = ArrayUtils.toString(array);
            tempString = tempString.substring(1, tempString.length() - 1);      //bardzo nie podobały mi sie klamerki po wydrukowaniu, dlatego je obciąlem
            System.out.println(licznik + ", " + tempString);
            licznik++;
        }

        start();
    }

    public static String[][] listToMultiArray() {
        String[][] multiArray = new String[0][3];
        File file = new File("tasks.csv");
        try {
            Scanner sc = new Scanner(file);
            while (sc.hasNextLine()) {
                String[] array = sc.nextLine().split(",");
                multiArray = addElementToArray(multiArray, array);
            }
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        }

        return multiArray;
    }

    public static String[][] addElementToArray(String[][] array, String[] element) {    //metoda powiekszająca tablice orazd dodająca doniej element
        String[][] tempArr = Arrays.copyOf(array, array.length + 1);
        tempArr[tempArr.length - 1] = element;
        return tempArr;
    }
}

