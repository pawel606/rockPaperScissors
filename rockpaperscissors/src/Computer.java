import java.util.Random;

public class Computer {
    private String computerChoice;

    public String getComputerChoice() {
        return computerChoice;
    }

    public int generateRandom() {
        Random random = new Random();
        return random.nextInt(Game.getOptions().length);
    }

    public void generateComputerChoice() {
        this.computerChoice = Game.getOptions()[generateRandom()];
    }
}
