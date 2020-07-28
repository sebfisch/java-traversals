package sebfisch.util;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertIterableEquals;

import java.util.stream.Stream;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.ArgumentsSource;
import org.junit.jupiter.params.provider.NullSource;
import sebfisch.shapes.Circle;
import sebfisch.shapes.Image;
import sebfisch.shapes.Point;
import sebfisch.shapes.Shape;
import sebfisch.test.gen.random.RndIntGen;

/**
 * This class demonstrates the use of {@link sebfisch.util.Traversal}s by documenting the expected
 * behavior of their methods.
 */
public class TraversalTests {

  private static <T> void assertStreamEquals(final Stream<T> one, final Stream<T> two) {
    final Iterable<T> iter1 = () -> one.iterator();
    final Iterable<T> iter2 = () -> two.iterator();
    assertIterableEquals(iter1, iter2);
  }

  /**
   * Tests that the {@link sebfisch.util.Traversal#partsOf} method returns exactly one element when
   * applied on a traversal constructed with {@link sebfisch.util.Traversal.For}.
   *
   * @param root instatiated with null
   */
  @ParameterizedTest
  @NullSource
  public void testRootCountIsOne(final Object root) {
    assertEquals(1, new Traversal.For<Object>().partsOf(root).count());
  }

  /**
   * Tests that the {@link sebfisch.util.Traversal#partsOf} method returns a stream containing its
   * argument when applied on a traversal constructed with {@link sebfisch.util.Traversal.For}.
   *
   * @param root instantiated with random integers
   */
  @ParameterizedTest
  @ArgumentsSource(RndIntGen.class)
  public void testRootPartsContainRoot(final int root) {
    assertEquals(root, new Traversal.For<Integer>().partsOf(root).findFirst().orElseThrow());
  }

  /**
   * Tests that a traversal using {@link sebfisch.util.Traversal#map} traverses the element returned
   * by the given function.
   *
   * @param circle instantiated with random circles
   */
  @ParameterizedTest
  @ArgumentsSource(Circle.Gen.class)
  public void testMapContainsResult(final Circle circle) {
    // Circle.CENTER = new Traversal.For<Circle>().map(Circle::getCenter);
    assertEquals(circle.getCenter(), Circle.CENTER.partsOf(circle).findFirst().orElseThrow());
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
    // Image.SHAPES = new Traversal.For<Image>().flatMap(Image::getShapes);
    assertEquals(image.getShapes().size(), Image.SHAPES.partsOf(image).count());
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
        Stream.of(image) //
            .flatMap(i -> i.getShapes().stream()) //
            .map(Shape::getCenter);
    Traversal<Image, Point> traversal =
        new Traversal.For<Image>() //
            .flatMap(Image::getShapes) //
            .map(Shape::getCenter);
    assertStreamEquals(stream, traversal.partsOf(image));
  }
}
