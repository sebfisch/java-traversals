package sebfisch.util;

import java.util.function.Function;
import java.util.function.Consumer;
import java.util.stream.Stream;

interface Traversal<S,P> extends Function<Consumer<P>, Consumer<S>> {
  public class For<T> implements Traversal<T,T> {
    @Override
    public Consumer<T> apply(final Consumer<T> ct) {
      return ct;
    }
  }

  default Stream<P> traverse(final S s) {
    final Stream.Builder<P> sb = Stream.builder();
    traverse(s, sb::add);
    return sb.build();
  }

  default void traverse(final S s, final Consumer<P> cp) {
    apply(cp).accept(s);
  }
}
