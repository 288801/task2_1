package ru.vsu.rogachev_egor;
import java.io.*;
import java.util.ArrayList;
import java.util.Scanner;

public class Main {

    public static void main(String[] args) throws IOException {
        Scanner scanner = new Scanner(System.in);

        System.out.println("Выберите действие\n" + "printFile - чтобы напечатать содержимое файла \n" +
                "writeToFile - чтобы записать данные в файл \n" + "changeFile - чтобы внести изменения в файл\n" +
                "emptyFile - чтобы удалить содержимое файла\n" +
                "calculatePrice - чтобы посчитать стоимость разговора" );

        String command = scanner.nextLine();
        switch (command){
            case("printFile"):
                System.out.println("Введите название файла, содержимое которого хотите вывести");
                String fileName = scanner.nextLine();
                try {
                    ArrayList<String[]> list = Tariffs.readFromFile(fileName);
                    for (String[] arr : list) {
                        System.out.println(arr[0] + "," + arr[1] + "," + arr[2]);
                    }
                }
                catch(Exception e){
                    System.out.println("Не существует такого файла");
                }
                break;
            case("writeToFile"):
                System.out.println("Введите название файла, откуда берутся данные, и название файла, куда они будут записываться");
                String fileName1 = scanner.nextLine();
                String fileName2 = scanner.nextLine();
                try {
                    Tariffs.emptyFile(fileName2);
                    Tariffs.writeToFile(Tariffs.readFromFile(fileName1), fileName2);
                }
                catch(Exception e){
                    File f1 = new File(fileName1);
                    File f2 = new File(fileName2);
                    f2.createNewFile();
                    if(f1.createNewFile()){
                        f1.delete();
                        System.out.println("Не существует такого файла");
                        break;
                    }
                    Tariffs.emptyFile(fileName2);
                    Tariffs.writeToFile(Tariffs.readFromFile(fileName1), fileName2);
                }
                break;
            case("changeFile"):
                System.out.println("Введите название файла в который вы хотите внести изменения");
                String cFileName = scanner.nextLine();
                File file = new File(cFileName);
                if(file.createNewFile()){
                    file.delete();
                    System.out.println("Не существует такого файла");
                    break;
                }
                System.out.println("Введите координаты ячейки(строку и столбец), которую хотите изменить");
                int row = scanner.nextInt();
                int col = scanner.nextInt();
                System.out.println("Введите значение, на которое хотите изменить содержимое ячейки");
                String newValue = scanner.next();
                try{
                    Tariffs.changeFile(cFileName, row, col, newValue);
                }catch(Exception e){
                    System.out.println("Введены неверные координаты");
                }
                break;
            case("emptyFile"):
                System.out.println("Введите название файла, содержимое которого хотите очистить");
                String eFileName = scanner.nextLine();
                try{
                    Tariffs.emptyFile(eFileName);
                }catch(Exception e){
                    System.out.println("Не существует такого файла");
                }
                break;
            case("addToFile"):
                try {
                    System.out.println("Введите название файла");
                    String nameFile = scanner.nextLine();
                    System.out.println("Введите новую строку");
                    String addStr = scanner.nextLine();

                    String[] newStr = addStr.split(",");
                    Tariffs.addToFile(nameFile, newStr);
                }catch (Exception e){
                    System.out.println("Не существует такого файла");
                }
                break;
            case("calculatePrice"):
                System.out.println("Введите название файла");
                String name = scanner.nextLine();
                System.out.println("Введите номер телефона");
                String number = scanner.nextLine();
                System.out.println("Введите количество секунд разговора");
                double secondsCount = scanner.nextDouble();
                try {
                    System.out.println(Tariffs.calculatePrice(name, number, secondsCount));
                }catch (Exception e){
                    System.out.println("Указаны некорректные данные");
                }
                break;
            default:
                System.out.println("Введена неверная команда");
                break;
        }
    }
}
