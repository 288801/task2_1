package ru.vsu.rogachev_egor;
import java.io.*;
import java.nio.charset.StandardCharsets;
import java.util.ArrayList;
import java.util.Arrays;

public class Tariffs {

    public static ArrayList<String[]> readFromFile(String fileName) throws IOException {
        BufferedReader reader = null;
        String line = "";
        ArrayList<String[]> list = new ArrayList<String[]>();

        reader = new BufferedReader(new FileReader(fileName));
        while((line = reader.readLine()) != null) {
            list.add(line.split(","));
        }

        return list;
    }

    public static void writeToFile(ArrayList<String[]> list, String fileName) throws IOException {
        FileWriter fw = new FileWriter(fileName, true);
        BufferedWriter bw = new BufferedWriter(fw);
        PrintWriter pw = new PrintWriter(bw);

        for (String[] row: list) {
            pw.println(row[0] + "," + row[1] + "," + row[2]);
        }
        pw.close();
    }

    public static void emptyFile(String fileName) throws IOException {
        File file = new File(fileName);
        FileOutputStream fileStream = new FileOutputStream(file,false);

        byte[] myBytes = "".getBytes(StandardCharsets.UTF_8);
        fileStream.write(myBytes);
    }

    public static void addToFile(String fileName, String[] newStr) throws IOException {
        File file = new File(fileName);
        FileWriter fw = new FileWriter(fileName, true);
        BufferedWriter bw = new BufferedWriter(fw);
        PrintWriter pw = new PrintWriter(bw);

        pw.println("");
        for (int i = 0; i < newStr.length; i++) {
            pw.print(newStr[i] + ",");
        }
        pw.println("");
        pw.close();
    }

    public static void changeFile(String fileName, int row, int col, String newValue) throws IOException {
        File file = new File(fileName);
        ArrayList<String[]> list = readFromFile(fileName);

        String[] arr = list.get(row-1);
        arr[col-1] = newValue;
        list.set(row-1,arr);

        FileOutputStream fileStream = new FileOutputStream(file, false);
        for (String[] str: list) {
            byte[] myBytes = (str[0] + "," + str[1] + "," + str[2] + "\n").getBytes(StandardCharsets.UTF_8);
            fileStream.write(myBytes);
        }
    }

    public static double calculatePrice(String fileName, String number, double secondsCount) throws IOException {
        File file = new File(fileName);
        ArrayList<String[]> list = readFromFile(fileName);
        int maxPrefixLen = 0;
        double price = 0;
        double minuteCount = 0;
        if(secondsCount >= 6){
            minuteCount = Math.ceil(secondsCount/60);
        }


        for (String[] arr:list) {
            String[] prefix = arr[0].split("");
            int len = prefix.length;
            if(len>maxPrefixLen) {
                String[] num = number.split("");
                if (Arrays.equals(prefix, Arrays.copyOfRange(num, 0, len))) {
                    maxPrefixLen = len;
                    try {
                        price = Double.parseDouble(arr[2]) * minuteCount;
                    } catch (Exception e) {
                        System.out.println("Стоимость звонка указана некорректно");
                    }
                }
            }
        }
        return price;
    }
}
