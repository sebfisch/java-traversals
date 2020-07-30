package sebfisch.text;

/**
 * Represents a function that accepts a char-valued argument and produces a result of type R.
 *
 * @param <R> result type
 */
@FunctionalInterface
public interface CharFunction<R> {

  /**
   * Applies this function to the given argument.
   *
   * @param c character to apply this function to
   * @return result of applying this function
   */
  R apply(char c);
}
