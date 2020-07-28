package sebfisch.util;

import java.util.function.BiConsumer;
import java.util.function.Consumer;
import java.util.function.Function;
import java.util.function.Predicate;
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
   * Compute a new traversal traversing the element returned by the given function applied to all
   * parts. The second argument is used to update the original part based on a changed new part.
   *
   * @param <Q> type of new parts
   * @param get function computing a new part from old ones
   * @param put bi-consumer updating the old part based on a changed new part
   * @return new traversal for new parts
   */
  default <Q> Traversal<R, Q> map(final Function<P, Q> get, final BiConsumer<P, Q> put) {
    return cq ->
        apply(
            p -> {
              final Q q = get.apply(p);
              cq.accept(q);
              put.accept(p, q);
            });
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

  /**
   * Compute a new traversal traversing only those parts that satisfy the given predicate.
   *
   * @param pred part predicate
   * @return restricted traversal
   */
  default Traversal<R, P> filter(final Predicate<P> pred) {
    return cp ->
        apply(
            p -> {
              if (pred.test(p)) {
                cp.accept(p);
              }
            });
  }

  /**
   * Computes a composed traversal traversing each of this traversal's parts with the given
   * traversal.
   *
   * @param traversal traversal with this traversal's parts as roots
   * @return composed traversal
   */
  default <Q> Traversal<R, Q> compose(final Traversal<P, Q> traversal) {
    return cq -> apply(traversal.apply(cq));
  }

  /**
   * Computes a new traversal first traversing all parts of this traversal and then all parts of the
   * given traversal.
   *
   * @param traversal another traversal
   * @return combined traversal
   */
  default Traversal<R, P> andAlso(final Traversal<R, P> traversal) {
    return cp -> apply(cp).andThen(traversal.apply(cp));
  }
}
