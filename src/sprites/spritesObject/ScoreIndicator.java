//Shay Zingboim 208497255, Yair Kupershtock 322889015

package sprites.spritesObject;

import biuoop.DrawSurface;
import game.Game;
import geometry.Rectangle;
import observers.Counter;
import sprites.spritesHelp.Sprite;
import java.awt.Color;

/**
 * A score indicator.
 */
public class ScoreIndicator implements Sprite {
    private Counter score;
    private geometry.Rectangle rectangle;

    /**
     * Constructs a new score indicator.
     *
     * @param score     the score to display
     * @param rectangle the rectangle to display the score in
     */
    public ScoreIndicator(Counter score, Rectangle rectangle) {
        this.score = score;
        this.rectangle = rectangle;
    }

    @Override
    public void drawOn(DrawSurface d) {
        d.setColor(Color.BLACK);
        d.drawText(350, 15, "Score: " + this.score.getValue(), 15);
    }

    @Override
    public void timePassed() {
    }

    /**
     * Adds the score indicator to the game.
     *
     * @param game the game to add the score indicator to
     */
    public void addToGame(Game game) {
        game.addSprite(this);
    }
}
