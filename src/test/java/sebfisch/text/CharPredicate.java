package sebfisch.text;

/** Represents a predicate with a character argument. */
@FunctionalInterface
public interface CharPredicate {

  /**
   * Evaluates this predicate on the given argument.
   *
   * @param c the input argument
   * @return true if the input argument matches the predicate, otherwise false
   */
  boolean test(char c);

  /**
   * Returns a predicate that represents the logical negation of this predicate.
   *
   * @return new predicate
   */
  default CharPredicate negate() {
    return c -> !test(c);
  }

  /**
   * Returns a composed predicate that represents the short-circuiting logical AND of this predicate
   * and another.
   *
   * @param pred another predicate
   * @return composed predicate
   */
  default CharPredicate and(final CharPredicate pred) {
    return c -> test(c) && pred.test(c);
  }

  /**
   * Returns a composed predicate that represents the short-circuiting logical OR of this predicate
   * and another.
   *
   * @param pred another predicate
   * @return composed predicate
   */
  default CharPredicate or(final CharPredicate pred) {
    return c -> test(c) || pred.test(c);
  }
}
