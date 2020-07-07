package sebfisch.util;

import java.util.function.Function;
import java.util.function.Consumer;

interface Traversal<S,P> extends Function<Consumer<P>, Consumer<S>> {
  public class For<T> implements Traversal<T,T> {
    @Override
    public Consumer<T> apply(final Consumer<T> ct) {
      return ct;
    }
  }
}
