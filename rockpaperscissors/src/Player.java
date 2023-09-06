import java.util.Scanner;

public class Player {
    private String playerChoice;
    private Scanner scanner;
    private String name;
    private int score;

    public Player(Scanner scanner) {
        this.scanner = scanner;
    }
    public Player(Scanner scanner, String name, int score) {
        this.scanner = scanner;
        this.name = name;
        this.score = score;
    }

    public int readPlayerChoice() {
        String input = scanner.nextLine();
        if(input.equals("!exit")) {
            return 0;
        }else if(input.equals("!rating")) {
            return 3;
        }
        this.playerChoice = input;
        for (String s : Game.getOptions()) {
            if (this.playerChoice.equals(s)) {
                return 1;
            }
        }
        return 2;
       // try {
       //     this.playerChoice = Shape.valueOf(input);
       //     return 1;
       // }catch (Exception e) {
       //     return 2;
       // }
    }
    public String getPlayerChoice() {
        return this.playerChoice;
    }

    public String getName() {
        return name;
    }

    public void setName() {
        this.name = scanner.nextLine();
    }
    public int getScore() {
        return score;
    }
    public void addScore(int score) {
        this.score += score;
    }
}
