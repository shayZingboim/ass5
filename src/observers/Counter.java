//Shay Zingboim 208497255, Yair Kupershtock 322889015

package observers;

/**
 * A Counter class.
 * A simple class that can increase and decrease a value.
 */
public class Counter {
    private int value;

    /**
     * Constructs a new Counter object.
     */
    public Counter() {
        this.value = 0;
    }
    /**
     * Increase the counter by a given number.
     *
     * @param number the number to increase the counter by.
     */
    public void increase(int number) {
        this.value += number;
    }

    /**
     * Decrease the counter by a given number.
     *
     * @param number the number to decrease the counter by.
     */
    public void decrease(int number) {
        this.value -= number;
    }

    /**
     * Get the current value of the counter.
     *
     * @return the current value of the counter.
     */
    public int getValue() {
        return this.value;
    }

}
