import java.util.Random;
import java.util.Scanner;

public class TicTacToe {

    public static void main(String[] args) {
        // display welcome message
        welcomeMessage();
        // run game
        run();
    }

    public static void welcomeMessage() {
        // display game instructions
        System.out.println("Welcome to Tic Tac Toe Game!");
        System.out.println("\n\nYou are playing against the computer.");
        System.out.println("Enter square number (1-9) to place your mark.");
    }

    public static void run() {
        Scanner scanner = new Scanner(System.in);
        TicTacToeGame game = new TicTacToeGame();
        Random random = new Random();
        boolean userTurn = true;

        // display board
        game.printBoard();

        while (true) {
            // display turn
            if (userTurn) {
                System.out.println("\n----------YOUR TURN----------\n");

            } else {
                System.out.println("\n----------COMPUTER'S TURN----------\n");
                // wait for 1 second
                try {
                    Thread.sleep(1500);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            }

            if (userTurn) {
                System.out.print("Enter square number (1-9) to place your mark: ");
                int square = scanner.nextInt();
                int row = (square - 1) / 3;
                int col = (square - 1) % 3;

                while (row < 0 || row > 2 || col < 0 || col > 2 || !game.move(row, col, 1)) {
                    System.out.println("Invalid move. Try again.\n");
                    System.out.print("Enter square number (1-9) to place your mark: ");
                    square = scanner.nextInt();
                    row = (square - 1) / 3;
                    col = (square - 1) % 3;
                }
                game.printBoard();
                if (game.checkWinner(1)) {
                    System.out.println("Congratulations! You won!\n");
                    break;
                }
                userTurn = false;
            } else {
                int row = random.nextInt(3);
                int col = random.nextInt(3);

                // computer will keep trying to make a move until it finds an empty cell
                while (!game.move(row, col, 2)) {
                    row = random.nextInt(3);
                    col = random.nextInt(3);
                }
                // display computer's move
                System.out.println("Computer placed mark at square " + (row * 3 + col + 1) + ".");
                // display board
                game.printBoard();

                // check if computer won
                if (game.checkWinner(2)) {
                    System.out.println("Computer won!\n");
                    System.out.println("\nBetter luck next time!");

                    // wanna play again?
                    System.out.println("\n\nDo you want to play again? (y/n)");
                    String playAgain = scanner.next();
                    if (playAgain.equals("y")) {
                        // clear console
                        System.out.print("\033[H\033[2J");
                        game = new TicTacToeGame();
                        userTurn = true;
                        continue;
                    }
                    break;
                }
                userTurn = true;
            }
        }
    }
}

// solve technique
class TicTacToeGame {
    final int N = 3;
    int[][] board = new int[N][N];

    public TicTacToeGame() {
        for (int i = 0; i < N; i++) {
            for (int j = 0; j < N; j++) {
                board[i][j] = 0;
            }
        }
    }

    public boolean move(int row, int col, int player) {
        if (board[row][col] != 0) {
            return false;
        }
        board[row][col] = player;
        return true;
    }

    public boolean checkWinner(int player) {
        // check rows
        for (int i = 0; i < N; i++) {
            boolean isWin = true;
            for (int j = 0; j < N; j++) {
                if (board[i][j] != player) {
                    isWin = false;
                    break;
                }
            }
            if (isWin) {
                return true;
            }
        }

        // check columns
        for (int i = 0; i < N; i++) {
            boolean isWin = true;
            for (int j = 0; j < N; j++) {
                if (board[j][i] != player) {
                    isWin = false;
                    break;
                }
            }
            if (isWin) {
                return true;
            }
        }

        // check diagonals
        boolean isWin = true;
        for (int i = 0; i < N; i++) {
            if (board[i][i] != player) {
                isWin = false;
                break;
            }
        }
        if (isWin) {
            return true;
        }

        isWin = true;
        for (int i = 0; i < N; i++) {
            if (board[i][N - i - 1] != player) {
                isWin = false;
                break;
            }
        }
        if (isWin) {
            return true;
        }

        return false;
    }

    public void printBoard() {
        // using - | + characters print board
        System.out.println("----------");
        for (int i = 0; i < N; i++) {
            System.out.print("| ");
            for (int j = 0; j < N; j++) {
                if (board[i][j] == 1) {
                    System.out.print("X ");
                } else if (board[i][j] == 2) {
                    System.out.print("O ");
                } else {
                    // display square number
                    System.out.print((i * 3 + j + 1) + " ");
                }
            }
            System.out.println("|");
        }
        System.out.println("----------");
        System.out.println();
    }

}