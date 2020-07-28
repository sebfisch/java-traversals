package sebfisch.test.gen;

import java.util.Iterator;
import java.util.Spliterator;
import java.util.Spliterators;
import java.util.stream.Stream;
import java.util.stream.StreamSupport;
import org.junit.jupiter.api.extension.ExtensionContext;
import org.junit.jupiter.params.provider.Arguments;
import org.junit.jupiter.params.provider.ArgumentsProvider;

/**
 * A generator provides arguments for unit tests. New generators can be defined as sub-classes by
 * implementing the methods of the {@link java.util.Iterator} interface as well as the size method
 * which specifies the number of generated elements.
 *
 * @param <T> type of provided arguments
 */
public abstract class Generator<T> implements Iterator<T>, ArgumentsProvider {

  /**
   * Specifies the number of generated arguments.
   *
   * @return number of generated arguments
   */
  public abstract int size();

  @Override
  public Stream<? extends Arguments> provideArguments(ExtensionContext context) {
    return StreamSupport.stream(
            Spliterators.spliterator(this, size(), Spliterator.CONCURRENT), false)
        .map(Arguments::of);
  }
}
