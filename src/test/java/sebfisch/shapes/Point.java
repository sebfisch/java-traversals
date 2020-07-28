package sebfisch.shapes;

import java.util.Collection;
import java.util.Optional;
import sebfisch.test.gen.random.RandomGenerator;

/** A point in 2D space with double precision coordinates. */
public class Point {

  /** X coordinate. */
  public double x;

  /** Y coordinate */
  public double y;

  /**
   * Creates a new point at the given coordinates.
   *
   * @param x x coordinate
   * @param y y coordinate
   */
  public Point(final double x, final double y) {
    this.x = x;
    this.y = y;
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
    return x == that.x && y == that.y;
  }

  /**
   * Adds the coordinates of another point to this points coordinates.
   *
   * @param that another point
   */
  public void add(final Point that) {
    x += that.x;
    y += that.y;
  }

  /**
   * Scales the coordinates of this point by a given factor.
   *
   * @param factor factor to scale with
   */
  public void scale(final double factor) {
    x *= factor;
    y *= factor;
  }

  /**
   * Computes the geometric center as arithmetic mean of all given points. If no points are given,
   * the result is empty.
   *
   * @param points points to compute centroid of
   * @return geometric center of given points if at least one is given
   */
  public static Optional<Point> centroid(final Collection<Point> points) {
    if (points.isEmpty()) {
      return Optional.empty();
    }

    final Point result = new Point(0, 0);
    for (final Point point : points) {
      result.add(point);
    }
    result.scale(1.0 / points.size());
    return Optional.of(result);
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
