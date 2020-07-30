package sebfisch.text;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static sebfisch.test.Assertions.assertStreamEquals;

import java.util.stream.Collectors;
import java.util.stream.Stream;
import org.junit.jupiter.api.Test;
import sebfisch.util.Traversal;

/** Demonstrates the use of traversals using text processing as an example. */
public class Tests {

  private static final Text HELLO = new Text("hello");

  /** Tests that querying words for a single word returns that word. */
  @Test
  public void testWordsQueryForSingleWord() {
    assertStreamEquals(Stream.of(HELLO), Text.words().partsOf(HELLO));
  }

  private static final Text HELLO_INSIDE_WHITESPACE = new Text(" \t  hello \r\n ");

  /** Tests that querying words for a single word inside whitespace returns the word. */
  @Test
  public void testWordsQueryForSingleWordInsideWhitespace() {
    assertStreamEquals(Stream.of(HELLO), Text.words().partsOf(HELLO_INSIDE_WHITESPACE));
  }

  private static final Text LOWER_CASE_WORDS = new Text("hello world");
  private static final Text TITLE_CASE_WORDS = new Text("Hello World");

  /** Tests converting every word to title case. */
  @Test
  public void testTitleCaseConversion() {
    final Text text = LOWER_CASE_WORDS.clone();
    Text.words().traverse(text, word -> word.map(0, Character::toTitleCase));
    assertEquals(TITLE_CASE_WORDS, text);
  }

  private static final Text LOWER_CASE_WORDS_AMONG_COMPLEX_WHITESPACE =
      new Text("hello world\nthis\tis\r\na      title");
  private static final Text TITLE_CASE_WORDS_AMONG_COMPLEX_WHITESPACE =
      new Text("Hello World\nThis\tIs\r\nA      Title");

  /** Tests converting every word to title case with different kinds of whitespace. */
  @Test
  public void testTitleCaseConversionWithComplexWhitespace() {
    final Text text = LOWER_CASE_WORDS_AMONG_COMPLEX_WHITESPACE.clone();
    Text.words().traverse(text, word -> word.map(0, Character::toTitleCase));
    assertEquals(TITLE_CASE_WORDS_AMONG_COMPLEX_WHITESPACE, text);
  }

  /** Tests that collecting words is the same as filtering out whitespace. */
  @Test
  public void testThatCollectingWordsIsFilteringOutWhitespace() {
    final Text text = TITLE_CASE_WORDS_AMONG_COMPLEX_WHITESPACE.clone();
    final String collectedWords = Text.words().partsOf(text).collect(Collectors.joining());
    assertEquals(text.filter(c -> !Character.isWhitespace(c)), collectedWords);
  }

  /** Tests that lines traversal traverses the correct number of lines. */
  @Test
  public void testLinesCount() {
    assertEquals(3, Text.lines().partsOf(TITLE_CASE_WORDS_AMONG_COMPLEX_WHITESPACE).count());
  }

  private static final Text CSV_TABLE = new Text("id,name\n47,Jane Doe\n11,Joe Bloggs");

  /** Tests that a correct number of table cells are found when traversing CSV data. */
  @Test
  public void testCsvTableCellCount() {
    final Traversal<Text, Text> tableCells = Text.lines().compose(Text.groups(","));
    assertEquals(6, tableCells.partsOf(CSV_TABLE).count());
  }

  /** Tests a query of CSV data based on row and column indices. */
  @Test
  public void testIndexedCsvQuery() {
    final Traversal<Text, Integer> idVals =
        Text.lines()
            .exceptAt(0)
            .compose(Text.groups(",").onlyAt(0))
            .map(Text::toString)
            .map(Integer::valueOf);
    assertStreamEquals(Stream.of(47, 11), idVals.partsOf(CSV_TABLE));
  }

  private static final Text LOWER_CASE_CSV_TABLE = new Text("id,name\n47,jane doe\n11,joe bloggs");

  /** Tests an update of CSV data based on row and colums indices. */
  @Test
  public void testIndexedCsvUpdate() {
    final Traversal<Text, Text> nameWords =
        Text.lines().exceptAt(0).compose(Text.groups(",").onlyAt(1)).compose(Text.words());
    final Text text = LOWER_CASE_CSV_TABLE.clone();
    nameWords.traverse(text, word -> word.map(0, Character::toTitleCase));
    assertEquals(CSV_TABLE, text);
  }
}
