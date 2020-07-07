package sebfisch.util;

import static org.junit.jupiter.api.Assertions.assertEquals;
import org.junit.jupiter.api.Test;

class TraversalTest {
  @Test
  public void testStreaming() {
    assertEquals(42, new Traversal.For<Integer>().traverse(42).findFirst().orElse(0));
  }
}
