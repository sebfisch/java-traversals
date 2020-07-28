package sebfisch.test.gen.random;

import java.util.NoSuchElementException;
import java.util.Random;
import java.util.function.Supplier;
import sebfisch.test.gen.Generator;

/**
 * A random generator provides 100 random arguments for unit tests. Specific generators can be
 * defined as sub-classes overriding the get method of the {@link java.util.function.Supplier}
 * interface by using the protected {@link java.util.Random} instance.
 *
 * @param <T> type of generated arguments
 */
public abstract class RandomGenerator<T> extends Generator<T> implements Supplier<T> {
  private int size = 100;

  /** This instance can be used in sub-classes to generate random values. */
  protected Random random = new Random();

  @Override
  public int size() {
    return size;
  }

  @Override
  public boolean hasNext() {
    return size > 0;
  }

  @Override
  public T next() throws NoSuchElementException {
    if (--size < 0) {
      throw new NoSuchElementException();
    }
    return get();
  }
}
