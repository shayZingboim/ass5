//Shay Zingboim 208497255, Yair Kupershtock 322889015

package sprites.spritesHelp;

import biuoop.DrawSurface;

import java.util.ArrayList;
import java.util.List;

/**
 * Represents a collection of sprites.
 */
public class SpriteCollection {
    private List<Sprite> sprites;

    // constructors

    /**
     * Constructs a new sprite collection.
     */
    public SpriteCollection() {
        this.sprites = new ArrayList<Sprite>();
    }

    /**
     * Adds a sprite to the collection.
     *
     * @param s the sprite to add
     */
    public void addSprite(Sprite s) {
        this.sprites.add(s);
    }

    /**
     * Calls timePassed() on all sprites.
     */
    public void notifyAllTimePassed() {
        List<Sprite> sprites = new ArrayList<Sprite>(this.sprites);
        for (Sprite s : sprites) {
            s.timePassed();
        }
    }

    /**
     * Calls drawOn() on all sprites.
     *
     * @param d the DrawSurface to draw on
     */
    public void drawAllOn(DrawSurface d) {
        for (Sprite s : this.sprites) {
            s.drawOn(d);
        }
    }

    /**
     * Removes a sprite from the collection.
     *
     * @param s the sprite to remove
     */
    public void removeSprite(Sprite s) {
        this.sprites.remove(s);
    }
}
