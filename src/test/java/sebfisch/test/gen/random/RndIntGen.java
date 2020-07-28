package sebfisch.test.gen.random;

/** Random generator for integer arguments of unit tests. */
public class RndIntGen extends RandomGenerator<Integer> {
  @Override
  public Integer get() {
    return random.nextInt();
  }
}
