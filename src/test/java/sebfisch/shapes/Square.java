package sebfisch.shapes;

import sebfisch.test.gen.random.RandomGenerator;
import sebfisch.util.Traversal;

/** A square has a computed center based on top-left corner and size. */
public class Square implements Shape {
  private Point topLeft;
  private double size;

  /**
   * Creates a square with given top-left corner and size.
   *
   * @param topLeft coordinates of top-left corner
   * @param size size of the created square
   */
  public Square(final Point topLeft, final double size) {
    this.topLeft = topLeft;
    this.size = size;
  }

  /**
   * Provides access to the coordinates of the top-left corner.
   *
   * @return coordinates of the top-left corner of this square
   */
  public Point getTopLeft() {
    return topLeft;
  }

  /**
   * Provides access to this square's size.
   *
   * @return size of this square
   */
  public double getSize() {
    return size;
  }

  @Override
  public Point getCenter() {
    return new Point(size, size).scale(0.5).add(topLeft);
  }

  /**
   * Change this square's location to reflect the given new center.
   *
   * @param newCenter new center
   */
  public void setCenter(final Point newCenter) {
    topLeft.add(getCenter().scale(-1).add(newCenter));
  }

  /**
   * Traversal for the center of a square. The location of the square is updated based on a possibly
   * updated center.
   *
   * @return center traversal
   */
  public static Traversal<Square, Point> center() {
    return new Traversal.For<Square>().map(Square::getCenter, (sq, c) -> sq.setCenter(c));
  }

  /** Random generator for square arguments of unit tests. */
  public static class Gen extends RandomGenerator<Square> {
    private final Point.Gen pointGen = new Point.Gen();
    private final double maxSize = 10;

    @Override
    public Square get() {
      return new Square(pointGen.get(), random.nextDouble() * maxSize);
    }
  }
}
