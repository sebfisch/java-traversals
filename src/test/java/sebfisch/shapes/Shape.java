package sebfisch.shapes;

import sebfisch.test.gen.random.RandomGenerator;

/** A geometric shape is placed in an image at a specific location. */
public interface Shape {

  /**
   * Provides access to the center point of a geometric shape.
   *
   * @return center point
   */
  Point getCenter();

  /** Random generator for shape arguments of unit tests. */
  public static class Gen extends RandomGenerator<Shape> {
    private final Circle.Gen circleGen = new Circle.Gen();

    @Override
    public Shape get() {
      return circleGen.get();
    }
  }
}
