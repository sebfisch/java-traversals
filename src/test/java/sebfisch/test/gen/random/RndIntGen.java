package sebfisch.test.gen.random;

public class RndIntGen extends RandomGenerator<Integer> {
  @Override
  public Integer get() {
    return random.nextInt();
  }
}
