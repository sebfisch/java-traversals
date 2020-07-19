package sebfisch.util;

import static org.junit.jupiter.api.Assertions.assertEquals;

import java.util.stream.IntStream;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.MethodSource;
import org.junit.jupiter.params.provider.NullSource;

/**
 * This class demonstrates the use of {@link sebfisch.util.Traversal}s by documenting the expected
 * behavior of their methods.
 */
public class TraversalTests {

  private static IntStream smallInt() {
    return IntStream.range(-3, 3);
  }

  /**
   * Tests that the {@link sebfisch.util.Traversal#partsOf} method returns exactly one element when
   * applied on a traversal constructed with {@link sebfisch.util.Traversal.For}.
   */
  @ParameterizedTest
  @NullSource
  public void testRootCountIsOne(final Object root) {
    assertEquals(1, new Traversal.For<Object>().partsOf(root).count());
  }

  /**
   * Tests that the {@link sebfisch.util.Traversal#partsOf} method returns a stream containing its
   * argument when applied on a traversal constructed with {@link sebfisch.util.Traversal.For}.
   */
  @ParameterizedTest
  @MethodSource("smallInt")
  public void testRootPartsContainRoot(final int root) {
    assertEquals(root, new Traversal.For<Integer>().partsOf(root).findFirst().orElseThrow());
  }
}
