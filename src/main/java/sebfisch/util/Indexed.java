package sebfisch.util;

/**
 * Represents values with a n associated integer index.
 *
 * @param <T> type of indexed value
 */
public class Indexed<T> {

  private final int index;
  private final T value;

  /**
   * Creates an indexed value.
   *
   * @param index the index
   * @param value the value
   */
  public Indexed(final int index, final T value) {
    this.index = index;
    this.value = value;
  }

  /**
   * Provides access to the index.
   *
   * @return the index
   */
  public int getIndex() {
    return index;
  }

  /**
   * Provides access to the value.
   *
   * @return the value
   */
  public T getValue() {
    return value;
  }
}
