import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.Scanner;

public class Game {
    private Player player;
    private final Computer computer;
    private final Scanner scanner;
    private final File scoreBoard;
    private Player[] playerList;
    private static String[] options;

    public Game() {
        this.scanner = new Scanner(System.in);
        this.player = new Player(scanner);
        this.computer = new Computer();
        this.scoreBoard = new File("src\\rating.txt");
    }


    public boolean startGame() {
        int option = player.readPlayerChoice();
        switch (option) {
            case 0 -> {
                System.out.println("Bye!");
                return false;
            }
            case 1 -> {
                computer.generateComputerChoice();
                CheckResults();
                return true;
            }
            case 2 -> {
                System.out.println("Invalid input");
                return true;
            }
            case 3 -> {
                System.out.println("Your rating: " + player.getScore());
                return true;
            }
        }
        return false;
    }

    public void continueGame() {
        System.out.println("Enter your name: > ");
        player.setName();
        playerExist(player.getName());
        System.out.println("Hello, " + player.getName());
        options = options(this.scanner.nextLine());
        System.out.println("Okay, let's start");
        boolean next = true;
        while (next) {
            next = startGame();
            save();
        }
    }

    private void CheckResults() {
        switch (winner()) {
            case -1 -> {
                System.out.println("Sorry, but the computer chose " + computer.getComputerChoice());
            }
            case 1 -> {
                System.out.println("Well done. The computer chose " + computer.getComputerChoice() + " and failed");
                player.addScore(100);
            }
            case 0 -> {
                System.out.println("There is a draw (" + computer.getComputerChoice() + ")");
                player.addScore(50);
            }
        }
        //for(int i = 0; i < options.length/2; i++) {
        //}
        //if (player.getPlayerChoice().equals(computer.getComputerChoice())) {
        //    System.out.println("There is a draw (" + player.getPlayerChoice() + ")");
        //    player.addScore(50);
        //} else if (player.getPlayerChoice().equals(Shape.rock) && computer.getComputerChoice().equals(Shape.paper)) {
        //    System.out.println("Sorry, but the computer chose " + computer.getComputerChoice());
        //} else if (player.getPlayerChoice().equals(Shape.paper) && computer.getComputerChoice().equals(Shape.scissors)) {
        //    System.out.println("Sorry, but the computer chose " + computer.getComputerChoice());
        //} else if (player.getPlayerChoice().equals(Shape.scissors) && computer.getComputerChoice().equals(Shape.rock)) {
        //    System.out.println("Sorry, but the computer chose " + computer.getComputerChoice());
        //} else {
        //    System.out.println("Well done. The computer chose " + computer.getComputerChoice() + " and failed");
        //    player.addScore(100);
        //}
    }

    private String readFile() {
        try {
            return new String(Files.readAllBytes(Paths.get(scoreBoard.getPath())));
        } catch (IOException e) {
            System.out.println("Cannot read file: " + e.getMessage());
        }
        return null;
    }

    private Player addPlayer(String name, int score) {
        try (FileWriter fileWriter = new FileWriter(scoreBoard, true)) {
            fileWriter.write(name + " " + score + "\n");
        } catch (IOException e) {
            System.out.println("An exception occurred " + e.getMessage());
        }
        return new Player(this.scanner, name, score);
    }

    private Player[] playerList() {
        String[] tmp = readFile().split("\\R");
        Player[] playerList = new Player[tmp.length];
        int j = 0;
        for (String s : tmp) {
            String[] tmp1 = s.split(" ");
            if (tmp1.length == 2) {
                playerList[j] = new Player(this.scanner, tmp1[0], Integer.parseInt(tmp1[1]));
                j++;
            }
        }
        this.playerList = playerList;
        return playerList;
    }

    private boolean playerExist(String name) { //true if player exist
        Player[] players = playerList();
        for (Player p : players) {
            try {
                if (p.getName().equals(name)) {
                    this.player = p;
                    return true;
                }
            } catch (NullPointerException e) {
                this.player = addPlayer(name, 350);
                return false;
            }
        }
        this.player = addPlayer(name, 350);
        return false;
    }

    private void save() {
        try (FileWriter fileWriter = new FileWriter(scoreBoard)) {
            for (Player p : this.playerList) {
                try {
                    fileWriter.write(p.getName() + " " + p.getScore() + "\n");
                } catch (NullPointerException e) {
                    fileWriter.write(this.player.getName() + " " + this.player.getScore() + "\n");
                }
            }
        } catch (IOException e) {
            System.out.println("An exception occurred " + e.getMessage());
        }
    }

    private String[] options(String input) {
        if(input.equals("")) {
            return new String[]{"rock","scissors","paper"};
        }
        return input.split(",");
    }

    public static String[] getOptions() {
        return options;
    }

    private int choiceToIndex(String choice) {
        for(int i = 0; i < options.length; i++) {
            if(choice.equals(options[i])) {
                return i;
            }
        }
        return -1;
    }
    public int winner() { // example rock -> scissors -> paper , array -> [rock,scissors,paper]
        int tmp = choiceToIndex(player.getPlayerChoice()) + 1;
        int end = 0;
        if(choiceToIndex(player.getPlayerChoice()) == choiceToIndex(computer.getComputerChoice())) {
            return 0;
        }
        while(end < options.length/2) {
            if(tmp >= options.length) {
                tmp = 0;
                continue;
            }
            if(choiceToIndex(computer.getComputerChoice()) == tmp) {
                return 1;
            }
            end++;
            tmp++;
        }
        return -1;
    }
}

