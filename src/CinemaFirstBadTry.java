import java.util.Scanner;

public class CinemaFirstBadTry {
    public static void main(String[] args) {
        Scanner scan = new Scanner(System.in);
        //Узнаем сколько рядом и мест
        System.out.println("Enter the number of rows:");
        int rows = scan.nextInt();
        System.out.println("Enter the number of seats in each row:");
        int seats = scan.nextInt();

        // Из-за того, что картинка по рядам сдвинута на 1 вправо, имеем элементов в массиве на 1 больше
        int realSeats = seats + 1;

        // Создаем массив для мест в зале
        String[][] cinema = new String[rows][realSeats];
        for (int i = 0; i < rows; i++) {
            cinema[i][0] = Integer.toString(i + 1);
            for (int j = 1; j < realSeats; j++) {
                cinema[i][j] = "S";
            }
        }

        int choice;
        int cnt = 0;
        int personRow;
        int personSeat;
        int sum = 0;
        do {
            System.out.println("1. Show the seats");
            System.out.println("2. Buy a ticket");
            System.out.println("3. Statistics");
            System.out.println("0. Exit");
            choice = scan.nextInt();
            switch (choice) {
                case 1:
                    printCinema(cinema, rows, realSeats);
                    break;
                case 2:
                    //Узнаем какое место хочет наш пользователь
                    System.out.println("Enter a row number:");
                    personRow = scan.nextInt();
                    System.out.println("Enter a seat number in that row:");
                    personSeat = scan.nextInt();
                    // Меняем массив зала
                    // Проверяем находится ли ввод в диапазоне кресел кинотеатра
                    if (personRow > 0 && personRow <= cinema.length && personSeat > 0 && personSeat < cinema[0].length) {
                        // Проверяем занято место или нет
                        if (cinema[personRow - 1][personSeat].equals("B")) {
                            // Повторяем просьбу ввода, пока не введено свободное место
                            do {
                                System.out.println("That ticket has already been purchased! Insert another place");
                                do {
                                    System.out.println("Enter a row number:");
                                    personRow = scan.nextInt();
                                    System.out.println("Enter a seat number in that row:");
                                    personSeat = scan.nextInt();
                                    if (!(personRow > 0 && personRow <= cinema.length && personSeat > 0 && personSeat <= cinema[0].length)) {
                                        System.out.println("Wrong input!");
                                    }
                                } while (!(personRow > 0 && personRow <= cinema.length && personSeat > 0 && personSeat <= cinema[0].length));
                            } while (cinema[personRow - 1][personSeat].equals("B"));
                        }
                    } else {  // Просим вводить пока не будет в диапазоне
                        do {
                            System.out.println("Wrong input!");
                            System.out.println("Enter a row number:");
                            personRow = scan.nextInt();
                            System.out.println("Enter a seat number in that row:");
                            personSeat = scan.nextInt();
                            if (personRow > 0 && personRow <= cinema.length && personSeat > 0 && personSeat < cinema[0].length) {
                                if (cinema[personRow - 1][personSeat].equals("B")) {
                                    System.out.println("That ticket has already been purchased! Insert another place");
                                    do {
                                        System.out.println("Enter a row number:");
                                        personRow = scan.nextInt();
                                        System.out.println("Enter a seat number in that row:");
                                        personSeat = scan.nextInt();
                                        if (!(personRow > 0 && personRow <= cinema.length && personSeat > 0 && personSeat < cinema[0].length)) {
                                            System.out.println("Wrong input!");
                                        }
                                    } while (cinema[personRow - 1][personSeat].equals("B"));
                                }
                            }
                        } while (!(personRow > 0 && personRow <= cinema.length && personSeat > 0 && personSeat < cinema[0].length));
                    }
                    cinema[personRow - 1][personSeat] = "B";

                    // Считаем стоимость билетов
                    ticketPrice(rows, seats, personRow);
                    // Добавляем к счетчику купленных билетов 1
                    cnt++;
                    // Добавляем сумму к общему заработку
                    if (cnt > 0) {
                        if (rows * seats <= 60) {
                            sum += 10;
                        }
                        if (personRow <= rows / 2) {
                            sum += 10;
                        } else {
                            sum += 8;
                        }
                    } else {
                        sum = 0;
                    }
                    break;
                case 3:
                    // Печатаем количество купленных билетов + проценты от всех билетов
                    statistic(rows, seats, cnt);
                    // Печатаем текущие деньги
                    System.out.println("Current income:" + "$" + sum);
                    // Печатаем максимум денег с зала
                    allMoney(rows, seats);
                    break;
                default:
                    break;
            }


        } while (choice != 0);
    }


    // Метод, чтобы рисовать кинозал
    public static void printCinema(String[][] arr, int rows, int seats) {
        System.out.println("Cinema:");
        System.out.print("  ");

        //Печатаем сидения (те, что в самом верху)
        for (int i = 1; i < seats; i++) {
            System.out.print(i + " ");
        }

        //Печатаем ряды сверху вниз
        System.out.println("");
        for (int i = 0; i < rows; i++) {
            for (int j = 0; j < seats; j++) {
                System.out.print(arr[i][j] + " ");
            }
            System.out.println("");
        }
    }


    // Метод, считающий стоимость билета
    public static void ticketPrice(int rows, int seats, int personRow) {
        if (rows * seats <= 60) {
            System.out.println("Ticket price: $10" + "\n");
        } else {
            if (personRow <= rows / 2) {
                System.out.println("Ticket price: $10" + "\n");
            } else {
                System.out.println("Ticket price: $8" + "\n");
            }
        }
    }

    // Метод, показывающий статистику
    public static void statistic(int rows, int seats, int cnt) {
        //Пишем количество купленных билетов
        System.out.println("Number of purchased tickets: " + cnt);
        //Пишем количество купленных билетов в % до 2 знаков после запятой
        float numSeats = rows * seats;
        float boughtSeatsPercents = (cnt / numSeats) * 100;
        String formattedDouble = String.format("%.2f", boughtSeatsPercents);
        System.out.println("Percentage: " + formattedDouble + "%");
    }

    public static void currentMoney(int rows, int seats, int personRow, int cnt) {
        int sum = 0;


    }


    public static void allMoney(int rows, int seats) {
        int sum = 0;
        if (rows * seats <= 60) {
            sum = rows * seats * 10;
        } else {
            if (rows % 2 != 0) {
                sum += (rows / 2) * 10 * seats;
                sum += (rows / 2 + 1) * 8 * seats;
            } else {
                sum += (rows / 2) * 10 * seats;
                sum += (rows / 2) * 8 * seats;
            }
        }
        System.out.println("Total income: $" + sum + "\n");


    }
}