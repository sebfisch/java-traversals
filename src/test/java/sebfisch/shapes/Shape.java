package sebfisch.shapes;

import sebfisch.test.gen.random.RandomGenerator;
import sebfisch.util.Traversal;

/** A geometric shape is placed in an image at a specific location. */
public interface Shape {

  /**
   * Provides access to the center point of a geometric shape.
   *
   * @return center point
   */
  Point getCenter();

  /**
   * A traversal for the center location of a shape. Works for stored as well as computed centers.
   *
   * @return center traversal
   */
  public static Traversal<Shape, Point> center() {
    return traversalFor(Circle.class)
        .compose(Circle.center())
        .andAlso(traversalFor(Square.class).compose(Square.center()));
  }

  private static <S extends Shape> Traversal<Shape, S> traversalFor(final Class<S> clazz) {
    return new Traversal.For<Shape>().filter(clazz::isInstance).map(clazz::cast);
  }

  /** Random generator for shape arguments of unit tests. */
  public static class Gen extends RandomGenerator<Shape> {
    private final Circle.Gen circleGen = new Circle.Gen();
    private final Square.Gen squareGen = new Square.Gen();

    @Override
    public Shape get() {
      return (random.nextBoolean() ? circleGen : squareGen).get();
    }
  }
}
