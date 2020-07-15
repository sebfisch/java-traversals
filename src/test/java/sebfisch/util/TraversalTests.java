package sebfisch.util;

import static org.junit.jupiter.api.Assertions.assertEquals;

import org.junit.jupiter.api.Test;

/**
 * This class demonstrates the use of {@link sebfisch.util.Traversal}s by documenting the expected
 * behavior of their methods.
 */
public class TraversalTests {

  /**
   * Tests that the {@link sebfisch.util.Traversal#partsOf} method returns exactly one element when
   * applied on a traversal constructed with {@link sebfisch.util.Traversal.For}.
   */
  @Test
  public void testPartsOfOnForHasSizeOne() {
    assertEquals(1, new Traversal.For<Integer>().partsOf(42).count());
  }

  /**
   * Tests that the {@link sebfisch.util.Traversal#partsOf} method returns a stream containing its
   * argument when applied on a traversal constructed with {@link sebfisch.util.Traversal.For}.
   */
  @Test
  public void testPartsOfOnForContainsRoot() {
    assertEquals(42, new Traversal.For<Integer>().partsOf(42).findFirst().orElseThrow());
  }
}
