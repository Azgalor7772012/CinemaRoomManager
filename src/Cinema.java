import java.util.Scanner;

public class Cinema {
    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);
        // Спрашиваем какая размерность зала.
        System.out.println("Enter the number of rows:");
        int numRows = scanner.nextInt();
        System.out.println("Enter the number of seats in each row:");
        int numSeats = scanner.nextInt();

        // Создаем и заполняем массив с 'S'
        char[][] seats = new char[numRows][numSeats];
        initializeSeats(seats);

        // Создаем и заполняем массив с ценами
        int[][] prizes = new int[numRows][numSeats];
        initializePrices(prizes);

        int choice;
        do {
            printMenu();
            choice = scanner.nextInt();
            switch (choice) {
                case 1:
                    printSeats(seats);
                    break;
                case 2:
                    buyTicket(scanner, seats, prizes);
                    break;
                case 3:
                    statistic(seats, prizes);
            }
        } while (choice != 0);
    }



    // Метод заполнения массива элементами S
    public static void initializeSeats(char[][] arr) {
        int numRows = arr.length;
        int numSeats = arr[0].length;
        for (int i = 0; i < numRows; i++) {
            for (int j = 0; j < numSeats; j++) {
                arr[i][j] = 'S';
            }
        }
    }

    // Метод заполнения массива ценами за места
    public static void initializePrices(int[][] arr) {
        int numRows = arr.length;
        int numSeats = arr[0].length;

        // Проверяем на первую половину зала и маленький зал
        boolean smallHall = numRows * numSeats <= 60;
        int forwardHalf = numRows / 2;

        for (int i = 0; i < numRows; i++) {
            for (int j = 0; j < numSeats; j++) {
                if (i < forwardHalf || smallHall) {
                    arr[i][j] = 10;
                } else {
                    arr[i][j] = 8;
                }
            }
        }
    }

    //Метод рисует зал
    public static void printSeats(char[][] arr) {
        int numRows = arr.length;
        int numSeats = arr[0].length;

        //Рисуем ряды(шапку)
        System.out.println();
        System.out.println("Cinema");
        System.out.print(" ");
        for (int i = 1; i <= numSeats; i++) {
            System.out.print(" " + i);
        }
        System.out.println();

        //Рисуем сиденья
        for (int i = 0; i < numRows; i++) {
            System.out.print(i + 1);
            for (int j = 0; j < numSeats; j++) {
                System.out.print(" " + arr[i][j]);
            }
            System.out.println();
        }
        System.out.println();

    }

    // Метод печатает меню
    public static void printMenu() {
        System.out.println("1. Show the seats");
        System.out.println("2. Buy a ticket");
        System.out.println("3. Statistics");
        System.out.println("0. Exit");
    }
    // Проверяем на купленность билета и что билет в диапозоне возможной покупки
    public static void buyTicket(Scanner scanner, char[][] seats, int[][] prizes) {
        int numRows = seats.length;
        int numSeats = seats[0].length;
        while (true) {
            // Просим ввести ряд и место
            System.out.println("Enter a row number:");
            int boughtRows = scanner.nextInt() - 1;
            System.out.println("Enter a seat number in that row:");
            int boughtSeats = scanner.nextInt() - 1;

            if (boughtRows < 0 || boughtSeats < 0 || boughtRows >= numRows || boughtSeats >= numSeats) {
                System.out.println("Wrong input!");
            } else if (seats[boughtRows][boughtSeats] == 'B') {
                System.out.println("That ticket has already been purchased!");

            } else {
                seats[boughtRows][boughtSeats] = 'B';
                System.out.println("Ticket price: $" + prizes[boughtRows][boughtSeats]);
                return;
            }
        }
    }


    public static void statistic(char[][] seats, int[][] prizes) {
        //Находим все необходимые величины
        int numRows = seats.length;
        int numSeats = seats[0].length;
        float purchased = 0;
        float allSeats = numRows * numSeats;
        int currentIncome = 0;
        int totalIncome = 0;
        for(int i = 0; i < numRows; i++) {
            for (int j = 0; j < numSeats; j++) {
                if (seats[i][j] == 'B') {
                    purchased++;
                    currentIncome += prizes[i][j];
                }
                totalIncome += prizes[i][j];
            }
        }
        float percents = (purchased / allSeats) * 100;
        String formatPercents = String.format("%.2f", percents);

        // Печатаем
        System.out.println("Number of purchased tickets: " + purchased);
        System.out.println("Percentage: " + formatPercents + "%");
        System.out.println("Current income: $" + currentIncome);
        System.out.println("Total income: $" + totalIncome);
    }

}
