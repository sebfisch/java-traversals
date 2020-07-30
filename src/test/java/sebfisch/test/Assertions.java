package sebfisch.test;

import static org.junit.jupiter.api.Assertions.assertIterableEquals;

import java.util.stream.Stream;

/** Defines additional assertions. */
public interface Assertions {

  /**
   * Assert that two streams contain equal elements.
   *
   * @param <A> type of elements in first stream
   * @param <B> type of elements in second stream
   * @param as first stream
   * @param bs second stream
   */
  static <A, B> void assertStreamEquals(final Stream<A> as, final Stream<B> bs) {
    final Iterable<A> iterAs = () -> as.iterator();
    final Iterable<B> iterBs = () -> bs.iterator();
    assertIterableEquals(iterAs, iterBs);
  }
}
