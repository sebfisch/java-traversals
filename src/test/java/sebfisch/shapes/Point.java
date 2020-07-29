package sebfisch.shapes;

import sebfisch.test.gen.random.RandomGenerator;

/** A point in 2D space with double precision coordinates. */
public class Point {

  private double thisX;
  private double thisY;

  /**
   * Creates a new point at the given coordinates.
   *
   * @param x X coordinate
   * @param y Y coordinate
   */
  public Point(final double x, final double y) {
    thisX = x;
    thisY = y;
  }

  /**
   * Provides access to the X coordinate of this point.
   *
   * @return the X coordinate
   */
  public double getX() {
    return thisX;
  }

  /**
   * Provides access to the Y coordinate of this point.
   *
   * @return the Y coordinate
   */
  public double getY() {
    return thisY;
  }

  /**
   * Modifies the X coordinate of this point.
   *
   * @param x new X coordinate
   * @return this point, mutated
   */
  public Point setX(final double x) {
    thisX = x;

    return this;
  }

  /**
   * Modifies the Y coordinate of this point.
   *
   * @param y new Y coordinate
   * @return this point, mutated
   */
  public Point setY(final double y) {
    thisY = y;

    return this;
  }

  @Override
  public String toString() {
    return "(" + thisX + "," + thisY + ")";
  }

  @Override
  public boolean equals(Object that) {
    return (that instanceof Point) && equals((Point) that);
  }

  /**
   * Equality check restricted to points.
   *
   * @param that another point
   * @return if the given point has the same coordinates
   */
  public boolean equals(Point that) {
    return closeDouble(getX(), that.getX()) && closeDouble(getY(), that.getY());
  }

  private static final double PRECISION = 1e-10;

  private static boolean closeDouble(final double a, final double b) {
    return Math.abs(a - b) < PRECISION;
  }

  /**
   * Adds the coordinates of another point to this points coordinates.
   *
   * @param that another point
   * @return this point, mutated
   */
  public Point add(final Point that) {
    return setX(getX() + that.getX()).setY(getY() + that.getY());
  }

  /**
   * Scales the coordinates of this point by a given factor.
   *
   * @param factor factor to scale with
   * @return this point, mutated
   */
  public Point scale(final double factor) {
    return setX(getX() * factor).setY(getY() * factor);
  }

  /** Random generator for arguments of unit tests. */
  public static class Gen extends RandomGenerator<Point> {
    @Override
    public Point get() {
      final double max = 100;
      return new Point(random.nextDouble() * max, random.nextDouble() * max);
    }
  }
}
