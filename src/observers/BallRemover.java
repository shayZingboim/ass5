//Shay Zingboim 208497255, Yair Kupershtock 322889015

package observers;

import collisions.collisionObject.Block;
import game.Game;
import listenerInterface.HitListener;
import sprites.spritesObject.Ball;

/**
 * A BallRemover class.
 * A BallRemover is in charge of removing balls from the game, as well as keeping count
 */
public class BallRemover implements HitListener {
    private Game game;
    private Counter remainingBalls;

    /**
     * Constructs a BallRemover object.
     *
     * @param game          The game the ball is in.
     * @param remainingBalls The number of balls in the game.
     */
    public BallRemover(Game game, Counter remainingBalls) {
        this.game = game;
        this.remainingBalls = remainingBalls;
    }

    @Override
    public void hitEvent(Block beingHit, Ball hitter) {
        game.removeSprite(hitter);
        this.remainingBalls.decrease(1);
    }
}
