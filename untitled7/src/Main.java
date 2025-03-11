import java.util.Scanner;

public class Main {
    private static int fieldSize = 3;
    private static char[][] board;
    private static boolean[][] occupied;
    private static Scanner scanner = new Scanner(System.in);

    public static void main(String[] args) {
        while (true) {
            showMenu();
            int choice = getUserInput();

            switch (choice) {
                case 1:
                    startGame();
                    break;
                case 2:
                    configureSettings();
                    break;
                case 3:
                    showRules();
                    break;
                case 4:
                    System.out.println("Вихід з гри.");
                    scanner.close();
                    return;
                default:
                    System.out.println("Зробіть вибір цифрою)");
            }
        }
    }

    private static void showMenu() {
        System.out.println("\nМеню");
        System.out.println("1. Розпочати гру");
        System.out.println("2. Налаштування гри");
        System.out.println("3. Правила гри");
        System.out.println("4. Вихід");
        System.out.print("Пункт меню гри: ");
    }

    private static int getUserInput() {
        while (!scanner.hasNextInt()) {
            System.out.println("Оберіть ціле число");
            scanner.next();
        }
        return scanner.nextInt();
    }

    private static void startGame() {
        board = new char[fieldSize][fieldSize];
        occupied = new boolean[fieldSize][fieldSize];
        char currentPlayer = 'X';
        initializeBoard();

        while (true) {
            displayBoard();
            int[] move = getPlayerMove(currentPlayer);
            board[move[0]][move[1]] = currentPlayer;
            occupied[move[0]][move[1]] = true;

            if (checkWin(currentPlayer)) {
                displayBoard();
                System.out.println("Вітаю! Гравець " + currentPlayer + " виграв!");
                break;
            }

            if (isBoardFull()) {
                System.out.println("Нічия! Повертайтесь до головного меню");
                break;
            }
            currentPlayer = (currentPlayer == 'X') ? 'O' : 'X';
        }
    }

    private static void initializeBoard() {
        for (int i = 0; i < fieldSize; i++) {
            for (int j = 0; j < fieldSize; j++) {
                board[i][j] = ' ';
                occupied[i][j] = false;
            }
        }
    }

    private static void displayBoard() {
        System.out.println();
        for (int i = 0; i < fieldSize; i++) {
            System.out.print((i + 1) + " | ");
            for (int j = 0; j < fieldSize; j++) {
                System.out.print(board[i][j] + " | ");
            }
            System.out.println();
        }
    }

    private static int[] getPlayerMove(char player) {
        int row, col;
        while (true) {
            System.out.println("Гравець " + player + ", введіть номер рядка (1-" + fieldSize + "):");
            row = getUserInput() - 1;
            System.out.println("Гравець " + player + ", введіть номер стовпця (1-" + fieldSize + "):");
            col = getUserInput() - 1;

            if (row >= 0 && row < fieldSize && col >= 0 && col < fieldSize && !occupied[row][col]) {
                return new int[]{row, col};
            }
            System.out.println("Неправильний хід, спробуйте ще раз.");
        }
    }

    private static boolean checkWin(char player) {
        for (int i = 0; i < fieldSize; i++) {
            if (checkRow(i, player) || checkColumn(i, player)) return true;
        }
        return checkDiagonals(player);
    }

    private static boolean checkRow(int row, char player) {
        for (int j = 0; j < fieldSize; j++) {
            if (board[row][j] != player) return false;
        }
        return true;
    }

    private static boolean checkColumn(int col, char player) {
        for (int i = 0; i < fieldSize; i++) {
            if (board[i][col] != player) return false;
        }
        return true;
    }

    private static boolean checkDiagonals(char player) {
        boolean diagonal1 = true, diagonal2 = true;
        for (int i = 0; i < fieldSize; i++) {
            if (board[i][i] != player) diagonal1 = false;
            if (board[i][fieldSize - 1 - i] != player) diagonal2 = false;
        }
        return diagonal1 || diagonal2;
    }

    private static boolean isBoardFull() {
        for (int i = 0; i < fieldSize; i++) {
            for (int j = 0; j < fieldSize; j++) {
                if (!occupied[i][j]) return false;
            }
        }
        return true;
    }

    private static void configureSettings() {
        System.out.println("Налаштування гри");
        System.out.println("1. Вибір розміру поля");
        System.out.println("0. Повернутися до меню");
        System.out.print("Оберіть пункт: ");
        int choice = getUserInput();
        if (choice == 1) {
            System.out.println("Оберіть розмір поля (3-9):");
            int size = getUserInput();
            if (size >= 3 && size <= 9) {
                fieldSize = size;
            } else {
                System.out.println("Неправильний вибір. Використовується розмір за замовчуванням 3x3.");
                fieldSize = 3;
            }
        }
    }

    private static void showRules() {
        System.out.println("Правила гри: 1) Кожен гравець ходить по черзі. 2) Хрестик ходить першим. 3) Щоб виграти потрібно, щоб у гравця " +
                "всі фігури були в лінію по горизонталі, вертикалі або діагоналі.");
        System.out.println("Натисніть будь-яку клавішу, щоб повернутися в меню.");
        scanner.next();
    }
}
