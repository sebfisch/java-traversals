package sebfisch.util;

import java.util.function.IntSupplier;

/** Represents a counter that starts at zero and can be incremented by one repeatedly. */
public class Counter implements IntSupplier {
  private int count = 0;

  @Override
  public int getAsInt() {
    return count;
  }

  /**
   * Increments the counter.
   *
   * @return this counter, mutated
   */
  public Counter increment() {
    count++;
    return this;
  }
}
