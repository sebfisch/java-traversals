package sebfisch.util;

import java.util.function.Consumer;
import java.util.function.Function;
import java.util.stream.Stream;

/**
 * A Traversal can be used to traverse parts of structured data.
 *
 * @param <R> type of the root structure
 * @param <P> type of traversed parts
 */
public interface Traversal<R, P> extends Function<Consumer<P>, Consumer<R>> {

  /**
   * Creates a traversal for a specified type that traverses the root of given data.
   *
   * @param <T> type of traversed data
   */
  static class For<T> implements Traversal<T, T> {
    @Override
    public Consumer<T> apply(final Consumer<T> ct) {
      return ct;
    }
  }

  /**
   * Provides access to traversed parts with a {@link Consumer}.
   *
   * @param root structured data
   * @param partConsumer consumer of parts
   */
  default void traverse(final R root, final Consumer<P> partConsumer) {
    apply(partConsumer).accept(root);
  }

  /**
   * Provides access to a {@link Stream} of traversed parts.
   *
   * @param root structured data
   * @return stream of traversed parts
   */
  default Stream<P> partsOf(final R root) {
    final Stream.Builder<P> sb = Stream.builder();
    traverse(root, sb);
    return sb.build();
  }

  /**
   * Compute a new traversal traversing the element returned by the given function applied to all
   * parts.
   *
   * @param <Q> type of new parts
   * @param get function computing a new part from old ones
   * @return new traversal for new parts
   */
  default <Q> Traversal<R, Q> map(final Function<P, Q> get) {
    return cq -> apply(p -> cq.accept(get.apply(p)));
  }

  /**
   * Compute a new traversal traversing each element returned by the given function applied to all
   * parts.
   *
   * @param <Q> type of new parts
   * @param get function computing an arbitrary number of new parts from old ones
   * @return new traversal for new parts
   */
  default <Q> Traversal<R, Q> flatMap(final Function<P, Iterable<Q>> get) {
    return cq -> apply(p -> get.apply(p).forEach(cq));
  }
}
