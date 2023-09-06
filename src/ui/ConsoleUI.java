package ui;

import java.io.IOException;
import java.time.LocalDate;
import java.util.Scanner;
import core.SaveDoc;
public class ConsoleUI implements BaseUI {
    private Scanner scanner;
    private boolean workOn;

    public ConsoleUI(){
        scanner = new Scanner(System.in);
        workOn = true;
    }

    @Override
    public void start() {
        intro();
        while(workOn){
            System.out.print("> ");
            String choice = scanner.nextLine();
            String[] arr = choice.split(",");
            String[] docLine = new String[4];
            String filename = "";
            if (arr.length == 4){
                for (String value: arr) {
                    if (value.trim().matches("([A-ZА-Я][a-zа-я]+)(\\s+)([A-ZА-Я][a-zа-я]+)(\\s+)([A-ZА-Я][a-zа-я]+)")){
                        docLine[0] = value;
                        System.out.println("Fullname: " + docLine[0]);
                    } else if(value.trim().matches("(\\+)?([- _():=+]?\\d[- _():=+]?){10,14}")){
                        docLine[2] = value;
                        System.out.println("Phone: " + docLine[2]);
                    } else if(value.trim().matches("^(0?[1-9]|[12][0-9]|3[01])[.](0?[1-9]|1[012])[.]\\d{4}$")){
                        if (checkYear(value)){
                            docLine[1] = value;
                            System.out.println("Birth date: " + docLine[1]);
                        } else {
                            System.out.println("Ошибка! Несуществующая дата. Дата не может быть после сегодняшней.");
                        }
                    } else if(value.trim().matches("([m|f|M|F|м|М|ж|Ж])")){
                        if(value.trim().matches("([m|M|м|М])")){
                            docLine[3] = "M";
                        } else {
                            docLine[3] = "F";
                        }
                        System.out.println("Gender: " + docLine[3]);
                    }
                }

                String fullline = getFillLine(docLine);

                if (fullline != null){
                    try{
                        String[] nameArr = docLine[0].trim().split("\\s");
                        filename = nameArr[0] + ".lst";
                       SaveDoc writeLine = new SaveDoc(filename,fullline);
                    } catch (IOException e) {
                        System.out.println(e.getMessage());
                    }
                }
            } else if (arr.length == 1 && (arr[0].equals("Q") || arr[0].equals("Exit") || arr[0].equals("exit"))) {
                finish();
            } else {
                errorInput(arr.length-4);
            }
        }
    }

    public void finish() {
        printAnswer("----------------\nFinished. Bye");
        workOn = false;
        System.exit(0);
    }
    private void errorInput(int err) {
        System.out.println("------------------------\nWrong data. Try again.\n------------------------");
        if (err > 0 ){
            printAnswer("More data then needed.\nВведены лишние данные.");
        } else {
            printAnswer("Less data then needed.\nВведено недостаточно данных.");
        }
    }

    @Override
    public void printAnswer(String text) {
        System.out.println(text);
    }

    private String repeatString(String txt, int num){
        String result = "";
        for (int i = 0; i < num; i++){
            result +=txt;
        }
        return result;
    }

    private String getFillLine(String[] arr){
        String result = "";
        String[] line = {"ФИО", "Дата рождения", "Телефон", "Пол"};
        for(int i = 0; i < arr.length; i++){
            if (arr[i] == null || arr[i].equals("")){
                printAnswer("Некорректный ввод данных: " + line[i]);
                result = null;
                return result;
            } else {
                result += "<" + arr[i].trim() + ">";
            }
        }
        return result;
    }

    private Boolean checkYear(String data){
        String[] dataArr = data.trim().split("\\.");
        if(dataArr.length == 3) {
            int year = Integer.parseInt(dataArr[2]);
            int month = Integer.parseInt(dataArr[1]);
            int day = Integer.parseInt(dataArr[0]);
            if (LocalDate.of(year, month, day).isAfter(LocalDate.now())) {
                return false;
            }
        } else {return false;}
        return true;
    }
    private void intro(){
        String task = "Введите через запятую Фамилию Имя Отчество, дату рождения(dd.mm.yyyy), номер телефона(89876543210), пол(M/F)";
        String exitMsg = "Для выхода из программы введите Q или Exit";
        System.out.println(" ------" + repeatString("-",task.length()) + "------");
        System.out.println(" --=== " + task + " ===-- ");
        int titleSpaceVal = (task.length()- exitMsg.length())/2;
        System.out.println(" --=== " + repeatString(" ",titleSpaceVal) + exitMsg + repeatString(" ",titleSpaceVal) + " ===--");
        System.out.println(" ------" + repeatString("-",task.length()) + "------");
    }
}
