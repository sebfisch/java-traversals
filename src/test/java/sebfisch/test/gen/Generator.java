package sebfisch.test.gen;

import java.util.Iterator;
import java.util.Spliterator;
import java.util.Spliterators;
import java.util.stream.Stream;
import java.util.stream.StreamSupport;
import org.junit.jupiter.api.extension.ExtensionContext;
import org.junit.jupiter.params.provider.Arguments;
import org.junit.jupiter.params.provider.ArgumentsProvider;

public abstract class Generator<T> implements Iterator<T>, ArgumentsProvider {
  public abstract int size();

  @Override
  public Stream<? extends Arguments> provideArguments(ExtensionContext context) {
    return StreamSupport.stream(
            Spliterators.spliterator(this, size(), Spliterator.CONCURRENT), false)
        .map(Arguments::of);
  }
}
