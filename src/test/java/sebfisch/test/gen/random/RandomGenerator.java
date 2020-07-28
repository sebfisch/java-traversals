package sebfisch.test.gen.random;

import java.util.NoSuchElementException;
import java.util.Random;
import java.util.function.Supplier;
import sebfisch.test.gen.Generator;

public abstract class RandomGenerator<T> extends Generator<T> implements Supplier<T> {
  private int size = 100;

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
