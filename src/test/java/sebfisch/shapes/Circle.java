package sebfisch.shapes;

import sebfisch.test.gen.random.RandomGenerator;
import sebfisch.util.Traversal;

/** Represents a circle around a specific point with a specific radius. */
public class Circle implements Shape {
  private Point center;
  private double radius;

  /**
   * Creates a circle at a given point with given radius.
   *
   * @param center center of created circle
   * @param radius radius of created circle
   */
  public Circle(final Point center, final double radius) {
    this.center = center;
    this.radius = radius;
  }

  @Override
  public Point getCenter() {
    return center;
  }

  /**
   * Provides access to the radius of a circle.
   *
   * @return radius of circle
   */
  public double getRadius() {
    return radius;
  }

  /**
   * Traversal for the center point of a circle.
   *
   * @return center traversal
   */
  public static Traversal<Circle, Point> center() {
    return new Traversal.For<Circle>().map(Circle::getCenter);
  }

  /** Random generator for circle arguments of unit tests. */
  public static class Gen extends RandomGenerator<Circle> {
    private final Point.Gen pointGen = new Point.Gen();
    private final double maxRadius = 10;

    @Override
    public Circle get() {
      return new Circle(pointGen.get(), random.nextDouble() * maxRadius);
    }
  }
}
