//Shay Zingboim 208497255, Yair Kupershtock 322889015

import game.Game;

/**
 * Ass3Game class is the main class that runs the game.
 * It creates a Game object and runs the game.
 */
public class Ass5Game {
    private Game myGame;

    /**
     * Constructs a new Ass3Game object.
     */
    public Ass5Game() {
        myGame = new Game();
    }

    /**
     * Initializes and runs the game.
     */
    public void runMyGame() {
        myGame.initialize();
        myGame.run();
    }

    /**
     * The main method that runs the game.
     *
     * @param args command line arguments
     */
    public static void main(String[] args) {
        Ass5Game game = new Ass5Game();
        game.runMyGame();
    }

}