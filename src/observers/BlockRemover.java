package observers;

import collisions.collisionObject.Block;
import listenerInterface.HitListener;
import sprites.spritesObject.Ball;
import game.Game;

/**
 * A BlockRemover class.
 */
public class BlockRemover implements HitListener {
    private Game game;
    private Counter remainingBlocks;

    public BlockRemover(Game game, Counter remainingBlocks) {
        this.game = game;
        this.remainingBlocks = remainingBlocks;
    }

    @Override
    public void hitEvent(Block beingHit, Ball hitter) {
        hitter.setColor(beingHit.getCollisionRectangle().getColor());
        game.removeCollidable(beingHit);
        game.removeSprite(beingHit);
        this.remainingBlocks.decrease(1);
    }
}


