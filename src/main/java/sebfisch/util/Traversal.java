package sebfisch.util;

import java.util.function.Consumer;
import java.util.function.Function;
import java.util.stream.Stream;

/**
 * A Traversal can be used to traverse parts of structured data.
 *
 * @param <S> type of a data structure
 * @param <P> type of traversed parts
 */
public abstract class Traversal<S, P> implements Function<Consumer<P>, Consumer<S>> {

  /**
   * Creates a traversal for a specified type that traverses the root of given data.
   *
   * @param <T> type of traversed data
   */
  public static class For<T> extends Traversal<T, T> {
    @Override
    public Consumer<T> apply(final Consumer<T> ct) {
      return ct;
    }
  }

  /**
   * Provides access to traversed parts with a {@link Consumer}.
   *
   * @param s structured data
   * @param cp consumer of parts
   */
  public void traverse(final S s, final Consumer<P> cp) {
    apply(cp).accept(s);
  }

  /**
   * Provides access to a {@link Stream} of traversed parts.
   *
   * @param s structured data
   * @return stream of traversed parts
   */
  public Stream<P> partsOf(final S s) {
    final Stream.Builder<P> sb = Stream.builder();
    traverse(s, sb);
    return sb.build();
  }
}
