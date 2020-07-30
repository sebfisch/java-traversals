package sebfisch.text;

/** Represents an operation of a single char-valued operand that produces a char-valued result. */
@FunctionalInterface
public interface CharUnaryOperator {

  /**
   * Applies this operator to the given operand.
   *
   * @param c the operand
   * @return the result
   */
  char applyAsChar(char c);

  /**
   * Returns a composed operator that first applies the given operator to its input, and then
   * applies this operator to the result.
   *
   * @param before operator to apply before this one
   * @return composed operator
   */
  default CharUnaryOperator compose(final CharUnaryOperator before) {
    return c -> applyAsChar(before.applyAsChar(c));
  }

  /**
   * Returns a composed operator that first applies this operator to its input, and then applies the
   * given operator to the result.
   *
   * @param after operator to apply after this one
   * @return composed operator
   */
  default CharUnaryOperator andThen(final CharUnaryOperator after) {
    return c -> after.applyAsChar(applyAsChar(c));
  }

  /**
   * Returns an operator that returns its operand.
   *
   * @return identity operator
   */
  static CharUnaryOperator identity() {
    return c -> c;
  }
}
