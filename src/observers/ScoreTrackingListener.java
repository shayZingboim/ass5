package observers;

import collisions.collisionObject.Block;
import listenerInterface.HitListener;
import sprites.spritesObject.Ball;

public class ScoreTrackingListener implements HitListener {
    private Counter currentScore;

    public ScoreTrackingListener(Counter scoreCounter) {
        this.currentScore = scoreCounter;
    }
    public void hitEvent(Block beingHit, Ball hitter) {
        this.currentScore.increase(5);
    }

    public void winScore() {
        this.currentScore.increase(100);
    }
}
