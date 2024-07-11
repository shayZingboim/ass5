//Shay Zingboim 208497255, Yair Kupershtock 322889015

package collisions.collisionObject;

import game.Game;
import geometry.Line;
import geometry.Point;
import geometry.Rectangle;
import listenerInterface.HitListener;
import sprites.movement.Velocity;
import sprites.spritesHelp.Sprite;
import collisions.collisionHelp.Collidable;
import biuoop.DrawSurface;
import sprites.spritesObject.Ball;
import listenerInterface.HitNotifier;

import java.awt.Color;
import java.util.ArrayList;
import java.util.List;


/**
 * A Block class.
 */
public class Block implements Collidable, Sprite, HitNotifier {
    private final Rectangle rectangle;
    private final Color boundsColor;
    private Color color;
    private List<HitListener> hitListeners;

    /**
     * Constructs a Block object with the specified rectangle and color.
     *
     * @param rectangle The rectangle representing the block.
     * @param color     The color of the block.
     */
    public Block(Rectangle rectangle, Color color) {
        this.rectangle = rectangle;
        this.boundsColor = Color.BLACK;
        this.color = color;
        this.hitListeners = new ArrayList<>();
    }

    /**
     * Constructs a Block object with the specified rectangle and color.
     *
     * @param rectangle The rectangle representing the block.
     */
    public Block(Rectangle rectangle) {
        this(rectangle, Color.BLACK);
    }

    /**
     * Constructs a Block object with the specified position, width, height, and color.
     *
     * @param upperLeft The upper-left corner of the block.
     * @param width     The width of the block.
     * @param height    The height of the block.
     */
    public Block(Point upperLeft, double width, double height) {
        this(new Rectangle(upperLeft, width, height));
    }


    /**
     * Draws the block on the given surface.
     *
     * @param d The surface to draw the block on.
     */
    public void drawOn(DrawSurface d) {
        d.setColor(this.rectangle.getColor());
        d.fillRectangle((int) rectangle.getUpperLeft().getX(), (int) rectangle.getUpperLeft().getY(),
                (int) rectangle.getWidth(), (int) rectangle.getHeight());
        d.setColor(this.boundsColor);
        d.drawRectangle((int) rectangle.getUpperLeft().getX(), (int) rectangle.getUpperLeft().getY(),
                (int) rectangle.getWidth(), (int) rectangle.getHeight());
    }

    @Override
    public Rectangle getCollisionRectangle() {
        return rectangle;
    }

    @Override
    public Velocity hit(Ball hitter, Point collisionPoint, Velocity currentVelocity) {
        //get the lines of the rectangle
        Line[] lines = rectangle.getLinesArr();
        //lines parallel to Y
        if (lines[0].isPointOnLine(collisionPoint, lines[0]) || lines[2].isPointOnLine(collisionPoint, lines[2])) {
            currentVelocity.setDx(-currentVelocity.getDx());
        }
        //lines parallel to X
        if (lines[1].isPointOnLine(collisionPoint, lines[1]) || lines[3].isPointOnLine(collisionPoint, lines[3])) {
            currentVelocity.setDy(-currentVelocity.getDy());
        }
        if (!ballColorMatch(hitter)) {
            this.notifyHit(hitter);
        }
        return currentVelocity;
    }

    @Override
    public Boolean isPaddle() {
        return false;
    }

    @Override
    public void timePassed() {
        // do nothing
    }

    /**
     * Adds the block to the game.
     *
     * @param game The game to add the block to.
     */
    public void addToGame(Game game) {
        game.addCollidable(this);
        game.addSprite(this);
    }

    /**
     * Removes the block from the game.
     *
     * @param game The game to remove the block from.
     */
    public void removeFromGame(Game game) {
        game.removeCollidable(this);
        game.removeSprite(this);
    }

    /**
     * Check if the color of the block matches the color of the ball.
     *
     * @param ball The ball to check the color of.
     * @return True if the colors match, false otherwise.
     */
    public boolean ballColorMatch(Ball ball) {
        return this.getCollisionRectangle().getColor().equals(ball.getColor());
    }

    @Override
    public void addHitListener(HitListener hl) {
        this.hitListeners.add(hl);
    }

    @Override
    public void removeHitListener(HitListener hl) {
        List<HitListener> listeners = new ArrayList<HitListener>(this.hitListeners);
        listeners.remove(hl);
        this.hitListeners = listeners;
    }

    /**
     * Notify all listeners about a hit event.
     *
     * @param hitter The ball that hit the block.
     */
    private void notifyHit(Ball hitter) {
        // Make a copy of the hitListeners before iterating over them.
        List<HitListener> listeners = new ArrayList<HitListener>(this.hitListeners);
        // Notify all listeners about a hit event:
        for (HitListener hl : listeners) {
            hl.hitEvent(this, hitter);
        }
    }

}
