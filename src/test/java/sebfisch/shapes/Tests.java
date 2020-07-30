package sebfisch.shapes;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static sebfisch.test.Assertions.assertStreamEquals;

import java.util.stream.Stream;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.Arguments;
import org.junit.jupiter.params.provider.ArgumentsSource;
import org.junit.jupiter.params.provider.MethodSource;
import sebfisch.util.Traversal;

/** This class demonstrates the use of traversals using images containing geometric shapes. */
public class Tests {

  /**
   * Tests that a traversal using {@link sebfisch.util.Traversal#map} traverses the element returned
   * by the given function.
   *
   * @param circle instantiated with random circles
   */
  @ParameterizedTest
  @ArgumentsSource(Circle.Gen.class)
  public void testMapContainsResult(final Circle circle) {
    assertEquals(circle.getCenter(), Circle.center().partsOf(circle).findFirst().orElseThrow());
  }

  /**
   * Tests more complicated traversal for shape centers.
   *
   * @param shape instantiated with random shapes
   */
  @ParameterizedTest
  @ArgumentsSource(Shape.Gen.class)
  public void testShapeCenter(final Shape shape) {
    assertEquals(shape.getCenter(), Shape.center().partsOf(shape).findFirst().orElseThrow());
  }

  /**
   * Tests that a traversal using {@link sebfisch.util.Traversal#flatMap} traverses all elements
   * returned by the given function.
   *
   * @param image instantiated with random images
   */
  @ParameterizedTest
  @ArgumentsSource(Image.Gen.class)
  public void testFlatMapCount(final Image image) {
    assertEquals(image.getShapes().size(), Image.shapes().partsOf(image).count());
  }

  /**
   * Tests that methods on traversals correspond to respective stream methods.
   *
   * @param image instantiated with random images
   */
  @ParameterizedTest
  @ArgumentsSource(Image.Gen.class)
  public void testStreamCorrespondence(final Image image) {
    Stream<Point> stream =
        Stream.of(image)
            .flatMap(i -> i.getShapes().stream())
            .filter(Square.class::isInstance)
            .map(Shape::getCenter);
    Traversal<Image, Point> traversal =
        new Traversal.For<Image>()
            .flatMap(Image::getShapes)
            .filter(Square.class::isInstance)
            .map(Shape::getCenter);
    assertStreamEquals(stream, traversal.partsOf(image));
  }

  /**
   * Tests that the center can be updated with a traversal regardless of whether it is a stored or a
   * computed property.
   *
   * @param shape instantiated with random shapes
   * @param newCenter instantiated with random points
   */
  @ParameterizedTest
  @MethodSource("rndShapeAndPointProvider")
  public void testCenterUpdate(final Shape shape, final Point newCenter) {
    Shape.center()
        .traverse(
            shape,
            center -> {
              center.setX(newCenter.getX());
              center.setY(newCenter.getY());
            });
    assertEquals(newCenter, shape.getCenter());
  }

  static Stream<Arguments> rndShapeAndPointProvider() {
    final int count = 100;
    final Shape.Gen shapeGen = new Shape.Gen();
    final Point.Gen pointGen = new Point.Gen();
    return Stream.generate(() -> Arguments.of(shapeGen.get(), pointGen.get())).limit(count);
  }
}
