//Shay Zingboim 208497255, Yair Kupershtock 322889015

package game;

import biuoop.DrawSurface;
import biuoop.GUI;
import biuoop.Sleeper;
import collisions.collisionHelp.Collidable;
import collisions.collisionHelp.GameEnvironment;
import collisions.collisionObject.Block;
import collisions.collisionObject.Paddle;
import geometry.Point;
import geometry.Rectangle;
import observers.BallRemover;
import observers.BlockRemover;
import observers.Counter;
import observers.ScoreTrackingListener;
import sprites.spritesHelp.Sprite;
import sprites.spritesHelp.SpriteCollection;
import sprites.spritesObject.Ball;
import sprites.spritesObject.ScoreIndicator;

import java.awt.Color;

/**
 * initializes the game.
 */
public class Game {
    private SpriteCollection sprites;
    private GameEnvironment environment;
    private GUI gui;
    private final Rectangle rectangle;
    private final Counter remainingBlocks = new Counter();
    private final BlockRemover blockRemover = new BlockRemover(this, remainingBlocks);
    private final Counter remainingBalls = new Counter();
    private final BallRemover ballRemover = new BallRemover(this, remainingBalls);
    private final Counter score = new Counter();
    private final ScoreTrackingListener scoreTrackingListener = new ScoreTrackingListener(score);

    /**
     * Constructs a new Game object.
     */
    public Game() {
        this.sprites = new SpriteCollection();
        this.environment = new GameEnvironment();
        this.rectangle = new Rectangle(new Point(0, 0), 800, 600, Color.DARK_GRAY);
        this.environment.addCollidable(new Block(rectangle));
    }


    /**
     * Adds a sprite to the game.
     *
     * @param s the sprite to add
     */
    public void addSprite(Sprite s) {
        this.sprites.addSprite(s);
    }

    /**
     * Adds a collidable object to the game environment.
     *
     * @param c the collidable object to add
     */
    public void addCollidable(Collidable c) {
        this.environment.addCollidable(c);
    }

    /**
     * Removes a collidable object from the game environment.
     *
     * @param c the collidable object to remove
     */
    public void removeCollidable(Collidable c) {
        this.environment.removeCollidable(c);
    }

    /**
     * Removes a sprite from the game.
     *
     * @param s the sprite to remove
     */
    public void removeSprite(Sprite s) {
        this.sprites.removeSprite(s);
    }

    /**
     * initializes the game.
     */
    public void initialize() {
        this.gui = new GUI("Arkanoid", 800, 600);
        // create and add a paddle to the game
        Paddle paddle = new Paddle(this.gui.getKeyboardSensor(),
                new Block(new Rectangle(new Point(400, 550), 100, 20, Color.YELLOW)));
        paddle.addToGame(this);
        // create and add two balls to the game
        Ball[] balls = new Ball[3];
        for (int i = 0; i < balls.length; i++) {
            balls[i] = new Ball(new Point(700 + i * 10, 100), 6, Color.MAGENTA, this.environment);
            balls[i].setVelocity(4 + i, 4 + i);
            balls[i].addToGame(this);
        }
        // create and add bounds blocks to the game
        int defaultBoundSize = 25;
        // creat and add a death region to the game
        Block deathRegion = new Block(new Rectangle(new Point(0, 600 - defaultBoundSize),
                800, defaultBoundSize, Color.GRAY));
        remainingBalls.increase(balls.length);
        deathRegion.addHitListener(ballRemover);
        deathRegion.addToGame(this);
        Block[] bounds = new Block[3];
        bounds[0] = new Block(new Rectangle(new Point(0, 0), 800, defaultBoundSize, Color.GRAY));
        bounds[1] = new Block(new Rectangle(new Point(0, 0), defaultBoundSize, 600, Color.GRAY));
        bounds[2] = new Block(new Rectangle(new Point(800 - defaultBoundSize, 0), defaultBoundSize, 600, Color.GRAY));
        for (Block b : bounds) {
            b.addToGame(this);
        }
        // create and add blocks to the game
        Color[] colors = {Color.RED, Color.ORANGE, Color.YELLOW, Color.GREEN, Color.BLUE, Color.PINK, Color.CYAN};
        for (int i = 0; i < 6; i++) {
            for (int j = 0; j < 12 - i; j++) {
                //increase the number of blocks
                remainingBlocks.increase(1);
                // Start from the right side of the screen and move leftwards
                Block block = new Block(new Rectangle(new Point(800 - 25 - (j + 1) * 50, 100 + i * 25 + 25),
                        50, 25, colors[i]));
                block.addHitListener(blockRemover);
                block.addHitListener(scoreTrackingListener);
                block.addToGame(this);
            }
        }
        // create and add a score indicator to the game
        ScoreIndicator scoreIndicator = new ScoreIndicator(score,
                (new Rectangle(new Point(0, 0), 800, defaultBoundSize, Color.WHITE)));
        scoreIndicator.addToGame(this);

    }

    /**
     * Runs the game.
     */
    public void run() {
        Sleeper sleeper = new Sleeper();
        int framesPerSecond = 60;
        int millisecondsPerFrame = 1000 / framesPerSecond;
        while (true) {
            long startTime = System.currentTimeMillis(); // timing
            DrawSurface d = gui.getDrawSurface();
            this.rectangle.drawOn(d);
            this.sprites.drawAllOn(d);
            gui.show(d);
            this.sprites.notifyAllTimePassed();
            long usedTime = System.currentTimeMillis() - startTime;
            long milliSecondLeftToSleep = millisecondsPerFrame - usedTime;
            if (milliSecondLeftToSleep > 0) {
                sleeper.sleepFor(milliSecondLeftToSleep);
            }
            // check if the game should end
            if (remainingBalls.getValue() == 0) {
                gui.close();
                return;
            }
            //
            if (remainingBlocks.getValue() == 0) {
                scoreTrackingListener.winScore();
                gui.close();
                return;
            }
        }
    }


    /**
     * The main method.
     *
     * @param args the command line arguments
     */
    public static void main(String[] args) {
        Game game = new Game();
        game.initialize();
        game.run();
    }
}